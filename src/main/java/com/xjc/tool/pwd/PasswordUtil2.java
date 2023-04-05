package com.xjc.tool.pwd;

import com.google.common.collect.Lists;
import com.xjc.tool.enums.PasswordTypeEnum;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

public class  PasswordUtil2 {

    /**
     * 根据密码规则条件组合正则表达式
     * @param digitsMin
     * @param digitsMax
     * @param contentTypeMin
     * @param contentTypes
     * @return
     */
    public String getPattern(Integer digitsMin, Integer digitsMax,  Integer contentTypeMin, List<Integer> contentTypes){
        List<String> list = Lists.newArrayList();
        for(Integer contentType : contentTypes){
            PasswordTypeEnum passwordTypeEnum = PasswordTypeEnum.valueOfByCode(contentType);
            if(Objects.nonNull(passwordTypeEnum)){
                list.add(passwordTypeEnum.getType());
            }
        }
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return RegUtil.combine(digitsMin, digitsMax, list ,contentTypeMin);
    }
}
