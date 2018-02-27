package cn.evun.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by ccy on 2016/9/12.
 */
public class PinyinUtil {
	/**
	 *
	 * @param chineseWord
	 */
	public static String chineseWordToPinyin(String chineseWord) {
		//汉语拼音格式化:处理拼音的格式化结果
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		//输出为大写
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		//去除升调
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		//1,将汉字字符串转化为汉字的字符数组
		char[] chars = chineseWord.toCharArray();
		StringBuilder sb=new StringBuilder();
		for (char c : chars) {
			//判断如果是空格,则跨过
			if (Character.isWhitespace(c)) {
				continue;
			}
			//判断是否是汉字
			//匹配汉字的正则表达式
			if (Character.toString(c).matches("[\\u4E00-\\u9FA5]")) {
				try {
					String[] results = PinyinHelper.toHanyuPinyinStringArray(c, format);
					String result = results[0];
//					Log.i("test", "result:" + result);
					sb.append(result);
				} catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
					badHanyuPinyinOutputFormatCombination.printStackTrace();
				}
			}else{
				//如果是字母
				if(Character.isLetter(c)){
					//直接使用
					sb.append(c);
				}else{
					//将火星文变为#
					sb.append("#");
				}
			}
		}

		return sb.toString();
	}
}
