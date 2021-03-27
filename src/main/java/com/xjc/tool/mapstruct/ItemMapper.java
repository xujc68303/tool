package com.xjc.tool.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Version 1.0
 * @ClassName ItemMapper
 * @Author jiachenXu
 * @Date 2021/3/27
 * @Description 领域模型转换
 */
@Mapper
public interface ItemMapper extends ObjectConverterMapper<Item1, Item2> {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Override
    Item2 to(Item1 item1);

    @Override
    Item1 from(Item2 source);

    @Override
    default List<Item2> toList(List<Item1> source) {
        return source.stream().map(ItemMapper.INSTANCE::to).collect(Collectors.toList());
    }

    @Override
    default List<Item1> fromList(List<Item2> target) {
        return target.stream().map(ItemMapper.INSTANCE::from).collect(Collectors.toList());
    }
}
