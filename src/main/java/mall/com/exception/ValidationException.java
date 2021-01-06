package mall.com.exception;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.context.MessageSource;

import egovframework.rte.fdl.cmmn.exception.BaseException;


/**
 * SessionValidationException
 *
 * @author 
 * @since 1.0
 */
@SuppressWarnings("serial")
public class ValidationException extends BaseException {
	
	/**
	 * ValidationException 생성자.
	 */
	public ValidationException() {
		this("BaseException without message", null, null);
	}
	

	/**
	 * ValidationException 생성자.
	 * 
	 * @param defaultMessage 메세지 지정
	 */
	public ValidationException(String errorKey, ERROR_TYPE errorType, String defaultMessage) {
		this(errorKey, errorType, defaultMessage, null, null);
	}



	/**
	 * ValidationException 생성자.
	 * 
	 * @param defaultMessage 메세지 지정
	 * @param wrappedException 원인 Exception
	 */
	public ValidationException(String errorKey, ERROR_TYPE errorType, String defaultMessage, Exception wrappedException) {
		this(errorKey, errorType, defaultMessage, null, wrappedException);
	}

	/**
	 * ValidationException 생성자.
	 * 
	 * @param defaultMessage 메세지 지정(변수지정)
	 * @param messageParameters 치환될 메세지 리스트
	 * @param wrappedException 원인 Exception
	 */
	public ValidationException(String errorKey, ERROR_TYPE errorType, String defaultMessage, Object[] messageParameters, Exception wrappedException) {
		String userMessage = defaultMessage;
		
		if (messageParameters != null) {
			userMessage = MessageFormat.format(defaultMessage, messageParameters);
		}
		
		this.message = userMessage;
		this.wrappedException = wrappedException;
	}

	/**
	 * ValidationException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 */
	public ValidationException(MessageSource messageSource, String errorKey, ERROR_TYPE errorType, String messageKey) {
		this(messageSource, errorKey, errorType, messageKey, null, null, Locale.getDefault(), null);
	}

	/**
	 * ValidationException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param wrappedException 원인 Exception
	 */
	public ValidationException(MessageSource messageSource, String errorKey, ERROR_TYPE errorType, String messageKey, Exception wrappedException) {
		this(messageSource, errorKey, errorType, messageKey, null, null, Locale.getDefault(), wrappedException);
	}

	/**
	 * ValidationException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param locale 국가/언어지정
	 * @param wrappedException 원인 Exception
	 */
	public ValidationException(MessageSource messageSource, String errorKey, ERROR_TYPE errorType, String messageKey, Locale locale, Exception wrappedException) {
		this(messageSource, errorKey, errorType, messageKey, null, null, locale, wrappedException);
	}

	/**
	 * ValidationException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param locale 국가/언어지정
	 * @param wrappedException 원인 Exception
	 */
	public ValidationException(MessageSource messageSource, String errorKey, ERROR_TYPE errorType, String messageKey, Object[] messageParameters, Locale locale, Exception wrappedException) {
		this(messageSource, errorKey, errorType, messageKey, messageParameters, null, locale, wrappedException);
	}

	/**
	 * ValidationException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param wrappedException 원인 Exception
	 */
	public ValidationException(MessageSource messageSource, String errorKey, ERROR_TYPE errorType, String messageKey, Object[] messageParameters, Exception wrappedException) {
		this(messageSource, errorKey, errorType, messageKey, messageParameters, null, Locale.getDefault(), wrappedException);
	}

	/**
	 * ValidationException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param defaultMessage 기본 메시지
	 * @param wrappedException 원인 Exception
	 */
	public ValidationException(MessageSource messageSource, String errorKey, ERROR_TYPE errorType, String messageKey, Object[] messageParameters, String defaultMessage, Exception wrappedException) {
		this(messageSource, errorKey, errorType, messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
	}

	/**
	 *  ValidationException 생성자.
	 *  
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param defaultMessage 기본 메시지
	 * @param locale 국가/언어지정
	 * @param wrappedException 원인 Exception
	 */
	public ValidationException(MessageSource messageSource, String errorKey, ERROR_TYPE errorType, String messageKey, Object[] messageParameters, String defaultMessage, Locale locale, Exception wrappedException) {
		this.errorKey = errorKey;
		this.errorType = errorType;
		this.messageKey = messageKey;
		this.messageParameters = messageParameters;
		this.message = messageSource.getMessage(messageKey, messageParameters, defaultMessage, locale);
		this.wrappedException = wrappedException;
	}
	
	private String errorKey;
	private ERROR_TYPE errorType;
	
	public String getErrorKey(){
		return this.errorKey;
	}
	public ERROR_TYPE getErrorType(){
		return this.errorType;
	}
	
	//validation 에러 타입
	public enum ERROR_TYPE {
		NOT_NULL,
		NOT_DATE,
		NOT_NUMBER,
		NOT_KOREAN,
		NOT_ENGLISH,
		NOT_SPECIAL
	}
}
