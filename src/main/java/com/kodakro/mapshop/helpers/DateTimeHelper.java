package com.kodakro.mapshop.helpers;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeHelper {

	public static Timestamp timestampNow() {
		return Timestamp.from(Instant.now());
	}
	
	public static LocalDate stringToLocalDate(String yyyymmdd) {
		return LocalDate.parse(yyyymmdd, DateTimeFormatter.BASIC_ISO_DATE);
	}
	
	public static Date localDateToUtilDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date stringToUtilDate(String yyyymmdd) {
		final LocalDate localDate = stringToLocalDate(yyyymmdd);
		
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
