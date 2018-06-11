package com.sap.hana.clould.samples.teamviewer.birthdayfinder.date;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateUtil {

	public static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone("GMT");

	public SimpleDateFormat getUtcTimestampFormat() {
		return getUtcSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	}

	public SimpleDateFormat getUtcSimpleDateFormat(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setTimeZone(GMT_TIME_ZONE);
		return dateFormat;
	}
}
