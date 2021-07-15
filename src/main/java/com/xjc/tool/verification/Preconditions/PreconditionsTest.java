package com.xjc.tool.verification.Preconditions;

import com.google.common.base.Preconditions;
import org.springframework.util.StringUtils;

/**
 * @Version 1.0
 * @ClassName PreconditionsTest
 * @Author jiachenXu
 * @Date 2020/12/28
 * @Description
 */
public class PreconditionsTest {

    public static void main(String[] args) {
        String str = "message";
        Preconditions.checkArgument(!StringUtils.isEmpty(str), "param is null");
    }
}
