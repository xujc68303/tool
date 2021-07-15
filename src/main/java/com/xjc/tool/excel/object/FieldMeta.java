package com.xjc.tool.excel.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Version 1.0
 * @ClassName FieldMeta
 * @Author jiachenXu
 * @Date 2020/8/29
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldMeta implements Serializable {

    private Object comment;

    private Object type;

}
