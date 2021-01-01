package injnsobang.com.util;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class ValidationDateUtil {
	
	/**
	 * 날짜 체크(yyyy-MM-dd형태) 및 Datetime 리턴
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param defaultMessage
	 * @return
	 */
	public static DateTime rejectIfDatePattern(
			Errors errors, String field, String errorCode, String defaultMessage){
		return rejectIfDatePattern(errors, field, errorCode, null, defaultMessage);
	}
	
	public static DateTime rejectIfDatePattern (
			Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage){
		Object value = errors.getFieldValue(field);
		DateTime ret = null;
		if (value == null ||!StringUtils.hasText(value.toString())) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}else{
			boolean isError = true;
			try{
				ret = new DateTime(value.toString());
				isError = false; 
			}catch(IllegalFieldValueException e){
			}catch(IllegalArgumentException e) {
			}
			if(isError){
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			}
		}
		return ret;
		
	}
	
	/**
	 * date2이 date1보다 이상날짜 인지 체크.
	 * @param date1
	 * @param date2
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param defaultMessage
	 */
	public static void rejectIfDateGreaterThan(
			DateTime date1, DateTime date2,
			Errors errors, String field, String errorCode, String defaultMessage){
		rejectIfDateGreaterThan(date1, date2, errors, field, errorCode, null, defaultMessage);
	}
	public static void rejectIfDateGreaterThan(
			DateTime date1, DateTime date2,
			Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage){
		if(date1 == null || date2 == null){
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}else{
			if(!date2.isEqual(date1) && !date2.isAfter(date1)){
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			}
		}
		
	}
			
}
