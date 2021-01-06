package mall.com.validator;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import injnsobang.com.service.ComIndvlzService;
import injnsobang.com.vo.ComIndvlzVO;


/**
 * @Class Name : DefaultValidator.java
 * @Description : 기본 밸리데이터
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
public abstract class AbstractValidator {

	@Resource(name = "comIndvlzService")
	protected ComIndvlzService comIndvlzService;

	protected static final String REQUIRED_FIELD = "com.msg.required";
	protected static final String REQUIRED_SELECT_FIELD = "com.msg.requiredSelect";
	protected static final String REQUIRED_DATE_FIELD = "com.msg.requiredDate";
	
	protected static final String INVALID_NUMBER_FIELD = "com.msg.onlyNumber";
	protected static final String INVALID_FIELD = "com.error.invalid";
	protected static final String INVALID_DATE_FIELD = "com.error.invalid.date";
	protected static final String INVALID_DATEPERIOD_FIELD = "com.error.invalid.dateperiod";
	protected static final String BEFORETODAY_DATE_FIELD = "com.error.beforetoday.date";
	protected static final String INVALID_PHONE_FIELD = "com.error.invalid.phone";
	protected static final String INVALID_URL_FIELD = "com.error.invalid.url";


	protected static final String INVALID_CODE_NAME_FIELD = "com.error.invlid.codeNm";

	
	/**
	 * 각 FIELD가 선언된 message프로퍼티의 prefix
	 * @return
	 */
	protected abstract String getTablePrefix(); 
	/**
	 * 해당 필드의 message프로퍼티 값 가져오기
	 * @param fieldKey
	 * @return
	 */
	protected String getFieldPrefix(String fieldKey){
		return getTablePrefix() + "." + fieldKey;
	}
	
	@Resource(name = "messageSource")
	protected MessageSource messageSource;

	
	protected String makeMessage(String errorField, String errorCode){
		String fieldName = getMessage(getFieldPrefix(errorField),null);
		return getMessage(errorCode, new String[]{fieldName});
	}
	protected String makeMessage(String[] errorField, String errorCode){
		String[] fieldNames = new String[errorField.length];
		for(int i = 0; i < errorField.length; i++){
			fieldNames[i] = getMessage(getFieldPrefix(errorField[i]),null);
		}
		return getMessage(errorCode, fieldNames);
	}
	
	protected String getMessage(String code){
		return getMessage(code, null);
	}
	protected String getMessage(String code, String[] args){
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());	
	}


	/**
	 * 코드명으로 코드값 가져오기
	 * @param list
	 * @param cdNm
	 * @return
	 */
	protected String getCd(List<ComIndvlzVO> list, String cdNm) {
		for(ComIndvlzVO data:list) {
			if(data.getCdNm().equals(cdNm))return data.getIndvlzCd();
		}
		return "";
	}
}
