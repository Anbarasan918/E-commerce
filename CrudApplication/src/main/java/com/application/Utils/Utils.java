package com.application.Utils;

public class Utils {

	public static Boolean isNotEmpty(String data) {
		Boolean isVaild = false;
		if (data != null && data != "null" && !data.isEmpty()) {
			isVaild = true;
		}
		return isVaild;
	}
}
