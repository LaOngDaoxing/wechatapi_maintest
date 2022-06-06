package com.ljx.wamtneln.util.stringutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ljx
 * @Description: 正则匹配工具类
 * @FR功能需求：
 * @ImportJar:
 * @ApiGrammer规则：
 * @Remark:
 * @AlibabaCodeStatuteScanError：
 * @CodeBug解决:
 * @Debug调试：
 * @date 2022/6/6 14:40
 */
public class RegexMatchUtil {

    /**
     *
     * @Description: 正则匹配方式3，遍历源完整字符串，依次正则匹配，若匹配成功则返回一个匹配结果
     * @param fatherStr				源完整字符串
     * @param myregStr				源子字符串（正则表达式字符串）
     * @param mgroupReplaceRegStr	匹配结果中要替换的正则字符串		"src="
     * @param mgroupReplaceTargStr	匹配结果中要替换的目标字符串		""
     * @return
     * @throws
     * @Remark
     */
    public static String matchOneByPatternCompileMatchGroup(String fatherStr,String myregStr,String mgroupReplaceRegStr,String mgroupReplaceTargStr) {
        /**########-正则匹配方式3，遍历源完整字符串，依次正则匹配，若匹配成功则返回一个匹配结果-########*/
        // 将一个String类型的正则表达式，封装到模式器Pattern中。
        Pattern p = Pattern.compile(myregStr);
        // 通过模式器对象p中的matcher方法，又获取到了一个匹配器对象m。
        Matcher m = p.matcher(fatherStr);
        // 定义一个List，用于存取正则匹配目标字符串
        String rstStr = "";
        // 遍历匹配结果方式1
        while(m.find()){
        	/*
				调用匹配器对象m的方法，将整个输入串，匹配正则表达式。
				m.find()	部分匹配\匹配到子串：
							此方法从匹配器Matcher区域的开头开始，如果该方法的上一次调用成功了，井且从那时开始匹配器没有被重置；则从上一次匹配操作没有匹配的第一个字符开始。
							即查找整个输入串中与正则表达式匹配的下一个子串，只要存在匹配的子串就返回true，否则返回false。
							如果matcher.find()返回true，则可以使用matcher.start()、matcher.end()、matcher.group()方法获取详细信息。
							实际上，只有执行了matcher.find()方法 后，状态机matcher才是真正开始进行匹配工作的！
        	 */
            // m.group()方法返回匹配到的子字符串
            if(m.group()!="" && !"".equals(m.group())) {
                // "src=http:baidu.com/test.jpg"
                String matchDataStr=m.group();
                // "http:baidu.com/test.jpg"
                if(mgroupReplaceRegStr!=null) {
                    matchDataStr=matchDataStr.replace(mgroupReplaceRegStr, mgroupReplaceTargStr);
                }
                rstStr=matchDataStr;
            }
        }
        return rstStr;
    }

    /**
     * 正则替换
     * @param fatherStr
     * @param myregStr
     * @return
     */
    public static String regReplaceAll(String fatherStr,String myregStr){
        //p为正则表达式
        Pattern p = Pattern.compile(myregStr);
        Matcher m = p.matcher(fatherStr);
        //将符合正则表达式的字符串，替换成""
        String dd= m.replaceAll("");
        System.out.println(dd);
        return dd;
    }
}
