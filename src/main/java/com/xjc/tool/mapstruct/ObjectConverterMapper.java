package com.xjc.tool.mapstruct;

import org.mapstruct.Mapper;

import java.util.List;

/**
 * @Version 1.0
 * @ClassName ObjectConverterMapper
 * @Author jiachenXu
 * @Date 2021/3/27
 * @Description 领域模型转换
 */
public interface ObjectConverterMapper<SOURCE, TARGET> {

    TARGET to(SOURCE source);

    SOURCE from(TARGET target);

    List<TARGET> toList(List<SOURCE> source);

    List<SOURCE> fromList(List<TARGET> target);
}
