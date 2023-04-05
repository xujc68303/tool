package com.xjc.tool.pwd;

import java.util.ArrayList;
import java.util.List;

public class RegUtil {

    private static final String MIN_BRACKETS_LEFT = "(";

    private static final String MIN_BRACKETS_RIGHT = ")";

    private static final String MID_BRACKETS_LEFT = "[";

    private static final String MID_BRACKETS_RIGHT = "]";

    private static final String LINE_TAL = "_-";

    public static final String ALL_CHAR = "";

    private StringBuffer stringBuffer;

    public static RegUtil init(){
        return new RegUtil("");
    }

    public static RegUtil init(String str){
        return new RegUtil(str);
    }

    public RegUtil(String str) {
        this(new StringBuffer(str));
    }

    public RegUtil(StringBuffer stringBuffer) {
        this.stringBuffer = stringBuffer;
    }
    public RegUtil append(String str){
        this.stringBuffer.append(str);
        return this;
    }

    public RegUtil append(RegUtil regUtil){
        return append(regUtil.toString());
    }
    public RegUtil addMinBrackets(){
        this.stringBuffer = new StringBuffer(MIN_BRACKETS_LEFT).append(stringBuffer).append(MIN_BRACKETS_RIGHT);
        return this;
    }
    public RegUtil addMidBrackets(){
        this.stringBuffer = new StringBuffer(MID_BRACKETS_LEFT).append(stringBuffer).append(MID_BRACKETS_RIGHT);
        return this;
    }
    public RegUtil addMidBracketInfo(){
        if(this.stringBuffer.toString().startsWith(MID_BRACKETS_LEFT) && this.stringBuffer.toString().endsWith(MID_BRACKETS_RIGHT)){
            return addMidBrackets();
        }
        return this;
    }
    public RegUtil addMinBracketInfo(){
        if(this.stringBuffer.toString().startsWith(MIN_BRACKETS_LEFT) && this.stringBuffer.toString().endsWith(MIN_BRACKETS_RIGHT)){
            return addMinBrackets();
        }
        return this;
    }

    public RegUtil repeat(Integer times){
        this.stringBuffer.append("{").append(times).append("}");
        return this;
    }
    public RegUtil repeat(Integer min, Integer max){
        StringBuffer append = this.stringBuffer.append("{").append(min).append(",");
        append.append(max).append("}");
        return this;
    }
    public RegUtil repeatMin(Integer times){
        return repeat(times, null);
    }

    public RegUtil repeatZeroOrMore(){
        this.stringBuffer.append("*");
        return this;
    }

    public RegUtil repeatZeroOrOne(){
        this.stringBuffer.append("?");
        return this;
    }

    public RegUtil repeatOneOrMore(){
        this.stringBuffer.append("+");
        return this;
    }

    public RegUtil addRevAffPreview(){
        this.stringBuffer = new StringBuffer("(?<=").append(this.stringBuffer).append(MIN_BRACKETS_RIGHT);
        return this;
    }

    public RegUtil addRevNegPreview(){
        this.stringBuffer = new StringBuffer("(?<!").append(this.stringBuffer).append(MIN_BRACKETS_RIGHT);
        return this;
    }


    public RegUtil addPosNegPreview(){
        this.stringBuffer.append("(?!").append(this.stringBuffer).append(MIN_BRACKETS_RIGHT);
        return this;
    }

    public RegUtil addHeadAndTail(){
        this.stringBuffer = new StringBuffer("^").append(this.stringBuffer).append("$");
        return this;
    }

    public RegUtil or(String str){
        this.stringBuffer.append("|").append(str);
        return this;
    }

    public RegUtil or(RegUtil regUtil){
        return or(regUtil.toString());
    }

    public RegUtil not(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("{^").append(stringBuffer).append(MID_BRACKETS_RIGHT);
        this.stringBuffer = buffer;
        return this;
    }

    @Override
    public String toString() {
        return stringBuffer.toString();
    }

    /**
     * 数字，大写字母，小写字母，特殊字符且内容类型下限为Limit，总位数为
     * @param digitMin 最小位数
     * @param digitMax 最大位数
     * @param contentType 内容类型
     * @param limit 内容类型下限
     * @return 组合的正则列表
     */
    public static String combine(int digitMin, int digitMax, List<String> contentType, int limit){
        limit = contentType.size() + 1 - limit;
        List<List<Integer>> combine = combine(0, 0, limit, contentType.size(), new ArrayList<>());
        RegUtil regUtil = RegUtil.init();
        combine.forEach(list -> {
            RegUtil reg = RegUtil.init();
            list.forEach(index -> reg.append(contentType.get(index)));
            reg.not().repeatOneOrMore().append(LINE_TAL).addPosNegPreview();
            regUtil.append(reg);
        });
        regUtil.append(ALL_CHAR).repeat(digitMin, digitMax).addHeadAndTail();
        return regUtil.toString();
    }

    public static List<List<Integer>> combine(int index, int num, int limit, Integer size, List<Integer> val){
        List<List<Integer>> lists = new ArrayList<>();
        if (num >= limit && num <= size) {
            lists.add(new ArrayList<>(val));
        }
        if (num > size) {
            return lists;
        }
        for (int i = index;i < size; i++) {
            val.add(i);
            List<List<Integer>> combine = combine(i + 1, num + 1, limit, size, val);
            lists.addAll(combine);
            val.remove(num);
        }
        return lists;
    }
}
