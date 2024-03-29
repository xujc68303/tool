package com.xjc.tool.yeepay.converter;

import com.xjc.tool.yeepay.exception.YeePayException;
import com.xjc.tool.yeepay.utils.QueryFormUtils;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.commons.beanutils.PropertyUtils;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Map;

/**
 * ConverterFactory.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
public class ConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ResponseBodyConverter(type, annotations);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new RequestBodyConverter(type, parameterAnnotations, methodAnnotations);
    }

    public static class RequestBodyConverter<T> implements Converter<T, RequestBody> {

        private final Type type;

        private final Annotation[] parameterAnnotations;

        private final Annotation[] methodAnnotations;

        public RequestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations) {
            this.type = type;
            this.parameterAnnotations = parameterAnnotations;
            this.methodAnnotations = methodAnnotations;
        }

        @Override
        public RequestBody convert(T value) throws IOException {

            FormBody.Builder builder = new FormBody.Builder();

            Map<String, String> queryParams = QueryFormUtils.getEncodedQueryParams(value);

            queryParams.forEach(builder::add);

            return builder.build();
        }
    }

    public static class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {

        private final Type type;

        private final Annotation[] annotations;

        public ResponseBodyConverter(Type type, Annotation[] annotations) {
            this.type = type;
            this.annotations = annotations;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String body = value.string();
            if (body.startsWith("<")) {
                return null;
            }
            try {
                T t = (T) ((Class) type).newInstance();
                String[] strings = body.split("\n");
                for (String line : strings) {
                    String[] split = line.split("=");
                    String k = split[0];
                    String v = split.length > 1 ? URLDecoder.decode(split[1], "GBK") : null;

                    if (!PropertyUtils.isWriteable(t, k)) {
                        System.out.println(((Class) type).getName() + " 缺少接收属性 : [" + k + "]");
                    } else {
                        if (v != null) {
                            PropertyUtils.setProperty(t, k, v);
                        }
                    }
                }
                return t;
            } catch (Exception e) {
                throw new YeePayException(e.getMessage(), e, "CONVERT_ERROR");
            }
        }
    }

}
