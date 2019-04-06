package com.bigdata.util.wwh;

import java.util.Date;

public class SelfUtil {

	/**
	 * 返回一个时间戳的字符串，没有冒号
	 * @return
	 */
	public String getTime() {
		Date date1 = new Date();
		String[] temp = date1.toString().split(" ");
		String result = "";
		for (String string : temp) {
			result += string;
		}
		result = result.replaceAll(":", "");
		return result;
	}
}
