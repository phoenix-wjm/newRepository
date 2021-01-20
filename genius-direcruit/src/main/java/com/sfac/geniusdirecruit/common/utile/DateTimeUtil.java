package com.sfac.geniusdirecruit.common.utile;

import java.time.format.DateTimeFormatter;

/**
 * @Description: Date Time Util
 * @author: HymanHu
 * @date: 2020年3月22日
 */
public class DateTimeUtil {
	
	public final static String CHINA_TIMEZONE = "GMT+8";
	
	public final static String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public final static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public final static String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	public final static String DATE_PATTERN_1 = "yyyyMMdd";
	public final static String DATE_TIME_PATTERN_1 = "yyyyMMddHHmmss";
	
	public final static DateTimeFormatter DEFAULT_DATE_TIME_FORMAT = 
			DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);
	public final static DateTimeFormatter DEFAULT_DATE_FORMAT = 
			DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
	public final static DateTimeFormatter DEFAULT_TIME_FORMAT = 
			DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN);
	public final static DateTimeFormatter DATE_FORMAT_1 = 
			DateTimeFormatter.ofPattern(DATE_PATTERN_1);
	public final static DateTimeFormatter DATE_TIME_FORMAT_1 = 
			DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_1);
	
	// 判断日期是否是yyyy-MM-dd HH:mm:ss格式，用于区分带时区的日期和不带时区的日期
	public static final String REGEX_DATE_TIME = "\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2}";
	// 判断日期是否是yyyy-MM-dd格式，用于区分带时区的日期和不带时区的日期
	public static final String REGEX_DATE = "\\d{4}(-\\d{2}){2}";
	// 判断日期是否是HH:mm:ss格式，用于区分带时区的日期和不带时区的日期
	public static final String REGEX_TIME = "\\d{2}(:\\d{2}){2}";
	
	public final static String CALENDAR_DAT_API = "http://timor.tech/api/holiday/info/";
}
