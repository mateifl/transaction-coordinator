package ro.zizicu.transaction.coordinator.utils;

import java.sql.Date;
import java.util.Calendar;

public class DateUtils {

	public static Date getSQLDateNow() {
		return new Date(Calendar.getInstance().getTimeInMillis());
	}
		
	
}
