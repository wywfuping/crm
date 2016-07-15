package com.yawei.util;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

public class Strings {
    public static String toUtf8(String str){
        if(StringUtils.isNotEmpty(str)){
            try {
                return new String(str.getBytes("ISO8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("字符串转换异常！");
            }
        }
        return "";
    }

    public static String toPinyin(String str) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);//设置拼音为小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//设置拼音不要音调
        format.setVCharType(HanyuPinyinVCharType.WITH_V);//设置u使用v来表示
        try {
            return PinyinHelper.toHanYuPinyinString(str,format,"",true);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
