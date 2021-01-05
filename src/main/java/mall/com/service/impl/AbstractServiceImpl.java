package mall.com.service.impl;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.exception.ValidationException;
import mall.com.exception.ValidationException.ERROR_TYPE;
import mall.com.service.ComMbrService;
import mall.com.util.NullUtil;
import mall.com.util.SessionUtil;
import mall.com.util.SpringHelperUtil;
import mall.com.vo.ComMbrVO;
import mall.com.vo.AbstractVO;
import mall.com.web.AbstractController;

/**
 *  기본 ServiceImpl 클래스
 * 
 * @author 
 * @since 2020.07.21
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *	수정일		수정자		수정내용
 *	-------		--------	---------------------------
 *  2020.07.21		 최초 생성
 *
 * </pre>
 */
public abstract class AbstractServiceImpl{
	
	protected final Log log = LogFactory.getLog(getClass());


	@Resource(name = "messageSource")
	protected MessageSource messageSource;

	/**
	 * 로그인 여부
	 * 
	 * @return
	 */
	public boolean isLogin() {
		return !getLoginID().equals("");
	}

	/**
	 * 로그인 아이디가져오기
	 * 
	 * @return
	 */
	public String getLoginID() {
		String ret = "";
		ComMbrVO m = getLoginVO();
		if (m != null && !"".equals(NullUtil.nullString(m.getMbrId())))
			ret = m.getMbrId();
		return ret;
	}

	/**
	 * 로그인 그룹ID가져오기
	 * 
	 * @return
	 */
	public String getLoginGroupID() {
		String ret = "";		
		ComMbrVO m = getLoginVO();
		if (m != null && !"".equals(NullUtil.nullString(m.getAuthGrpId())))
			ret = m.getAuthGrpId();
		return ret;
	}
	
	/**
	 * 로그인 정보가져오기
	 * 
	 * @return
	 */
	public ComMbrVO getLoginVO() {
		ComMbrVO ret = null;
		try{
			ret = (ComMbrVO)SessionUtil.getAttribute(ComMbrService.SESSION_VO);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return ret;
	}

	/**
	 * 관리자 여부
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		if("1".equals(getLoginGroupID()))return true;
		return false;
	}

	/**
	 * 저장권한
	 * @return
	 */
	public boolean isStreAuth(){
		return (Boolean)SpringHelperUtil.getRequest().getAttribute(AbstractController.REQUEST_IS_STRE_AUTH);
	}
	/**
	 * 읽기권한
	 * @return
	 */
	public boolean isRedngAuth(){
		return (Boolean)SpringHelperUtil.getRequest().getAttribute(AbstractController.REQUEST_IS_REDNG_AUTH);
	}
	/**
	 * 삭제권한
	 * @return
	 */
	public boolean isDelAuth(){
		return (Boolean)SpringHelperUtil.getRequest().getAttribute(AbstractController.REQUEST_IS_DEL_AUTH);
	}
	/**
	 * 출력권한
	 * @return
	 */
	public boolean isPrntgAuth(){
		return (Boolean)SpringHelperUtil.getRequest().getAttribute(AbstractController.REQUEST_IS_PRNTG_AUTH);
	}


	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isViewable(AbstractVO searchVO){
		if(!isRedngAuth())return false;
		
		if(searchVO == null)return false;
		if(isAdmin())return true;
//		if(isFoundationGroup())return true;
		
		return true;
//		return NullUtil.nullString(searchVO.getRegstrId()).equals(getLoginID());
	}
	
	/**
	 * 기본 수정권한
	 * @param searchVO
	 * @return
	 */
	protected boolean isModifiable(AbstractVO searchVO){
		if(!isStreAuth())return false;
		
		if(searchVO == null)return false;
		if(isAdmin())return true;


		return true;
//		return NullUtil.nullString(searchVO.getRegstrId()).equals(getLoginID());
	}
	
	/**
	 * 기본 삭제권한
	 * @param searchVO
	 * @return
	 */
	protected boolean isDeletable(AbstractVO searchVO){
		if(!isDelAuth())return false;
		
		if(searchVO == null)return false;
		if(isAdmin())return true;


		return true;
//		return NullUtil.nullString(searchVO.getRegstrId()).equals(getLoginID());
	}
	

	/**
	 * 기본 출력권한
	 * @param searchVO
	 * @return
	 */
	protected boolean isPrintable(AbstractVO searchVO){
		if(!isPrntgAuth())return false;
		
		if(searchVO == null)return false;
		if(isAdmin())return true;


		return true;
//		return NullUtil.nullString(searchVO.getRegstrId()).equals(getLoginID());
	}
	
	/**
	 * msgKey를 통한 메세지 가져오기
	 * 
	 * @param msgKey
	 * @return
	 */
	protected String getMessage(final String msgKey) {
		return getMessage(msgKey, null);
	}

	/**
	 * msgKey를 통한 메세지 가져오기
	 * 
	 * @param msgKey
	 * @param msgArgs
	 * @return
	 */
	protected String getMessage(final String msgKey, String[] msgArgs) {
		return getMessage(msgKey, msgArgs, Locale.getDefault());
	}

	/**
	 * msgKey를 통한 메세지 가져오기
	 * 
	 * @param msgKey
	 * @param msgArgs
	 * @param locale
	 * @return
	 */
	protected String getMessage(final String msgKey, String[] msgArgs, Locale locale) {
		return messageSource.getMessage(msgKey, msgArgs, locale);
	}

	/**
	 * msgKey를 통한 메세지 가져오기
	 * 
	 * @param msgKey
	 * @param msgArgs
	 * @param defaultMessage
	 * @param locale
	 * @return
	 */
	protected String getMessage(final String msgKey, String[] msgArgs, String defaultMessage, Locale locale) {
		return messageSource.getMessage(msgKey, msgArgs, defaultMessage, locale);
	}

	/**
	 * EgovBizException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @return Exception EgovBizException 객체
	 */
	protected EgovBizException processException(final String msgKey) {
		return processException(msgKey, new String[] {});
	}

	/**
	 * EgovBizException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @param exception 발생한 Exception(내부적으로 취하고 있다가 에러핸들링시 사용)
	 * @return Exception EgovBizException 객체
	 */
	protected EgovBizException processException(final String msgKey, Exception e) {
		return processException(msgKey, new String[] {}, e);
	}

	/**
	 * EgovBizException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @param msgArgs msgKey의 메세지에서 변수에 취환되는 값들
	 * @return Exception EgovBizException 객체
	 */
	protected EgovBizException processException(final String msgKey, final String[] msgArgs) {
		return processException(msgKey, msgArgs, null);
	}

	/**
	 * EgovBizException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @param msgArgs msgKey의 메세지에서 변수에 취환되는 값들
	 * @param exception 발생한 Exception(내부적으로 취하고 있다가 에러핸들링시 사용)
	 * @return Exception EgovBizException 객체
	 */
	protected EgovBizException processException(final String msgKey, final String[] msgArgs, final Exception e) {
		return processException(msgKey, msgArgs, e, LocaleContextHolder.getLocale());
	}

	/**
	 * EgovBizException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @param msgArgs msgKey의 메세지에서 변수에 취환되는 값들
	 * @param exception 발생한 Exception(내부적으로 취하고 있다가 에러핸들링시 사용)
	 * @param locale 명시적 국가/언어지정
	 * @return Exception EgovBizException 객체
	 */
	protected EgovBizException processException(final String msgKey, final String[] msgArgs, final Exception e, Locale locale) {
		return processException(msgKey, msgArgs, e, locale, null);
	}

	/**
	 * EgovBizException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @param msgArgs msgKey의 메세지에서 변수에 취환되는 값들
	 * @param exception 발생한 Exception(내부적으로 취하고 있다가 에러핸들링시 사용)
	 * @param locale 명시적 국가/언어지정
	 * @param exceptionCreator 외부에서 별도의 Exception 생성기 지정
	 * @return Exception EgovBizException 객체
	 */
	protected EgovBizException processException(final String msgKey, final String[] msgArgs, final Exception e,
			final Locale locale, ExceptionCreator exceptionCreator) {
		ExceptionCreator eC = null;
		if (exceptionCreator == null) {
			eC = new ExceptionCreator() {

				public EgovBizException createBizException(MessageSource messageSource) {
					return new EgovBizException(messageSource, msgKey, msgArgs, locale, e);
				}
			};
		}
		return eC.createBizException(messageSource);
	}

	protected interface ExceptionCreator {

		EgovBizException createBizException(MessageSource messageSource);
	}


	
	/**
	 * 비지니스 로직에러 발생(꽁통)
	 * @param msg
	 * @throws BizException
	 */
	protected void throwBizException(final String msgKey)throws EgovBizException{
		throwBizException(msgKey,null);
	}
	protected void throwBizException(final String msgKey,String[] args)throws EgovBizException{
		throw processException(msgKey,args);
	}
	

	/**
	 * ValidationException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @return Exception EgovBizException 객체
	 */
	protected ValidationException validationException(final String errorKey, final String msgKey) {
		return validationException(errorKey, ERROR_TYPE.NOT_NULL, msgKey);
	}
	protected ValidationException validationException(final String errorKey, final String msgKey, final String[] args) {
		return validationException(errorKey, ERROR_TYPE.NOT_NULL, msgKey, args);
	}
	protected ValidationException validationException(final String errorKey, final ERROR_TYPE errorType, final String msgKey) {
		return validationException(errorKey, errorType, msgKey, new String[] {});
	}
	protected ValidationException validationException(final String errorKey, final ERROR_TYPE errorType, final String msgKey, final String[] msgArgs) {
		return validationException(errorKey, errorType, msgKey, msgArgs, null);
	}

	/**
	 * ValidationException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @param msgArgs msgKey의 메세지에서 변수에 취환되는 값들
	 * @param exception 발생한 Exception(내부적으로 취하고 있다가 에러핸들링시 사용)
	 * @return Exception EgovBizException 객체
	 */
	private ValidationException validationException(final String errorKey, final ERROR_TYPE errorType, final String msgKey, final String[] msgArgs, final Exception e) {
		return validationException(errorKey, errorType,msgKey, msgArgs, e, LocaleContextHolder.getLocale());
	}

	/**
	 * ValidationException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @param msgArgs msgKey의 메세지에서 변수에 취환되는 값들
	 * @param exception 발생한 Exception(내부적으로 취하고 있다가 에러핸들링시 사용)
	 * @param locale 명시적 국가/언어지정
	 * @return Exception EgovBizException 객체
	 */
	private ValidationException validationException(final String errorKey, final ERROR_TYPE errorType, final String msgKey, final String[] msgArgs, final Exception e, Locale locale) {
		return validationException(errorKey, errorType, msgKey, msgArgs, e, locale, null);
	}

	/**
	 * ValidationException 발생을 위한 메소드
	 * 
	 * @param msgKey 메세지리소스에서 제공되는 메세지의 키값
	 * @param msgArgs msgKey의 메세지에서 변수에 취환되는 값들
	 * @param exception 발생한 Exception(내부적으로 취하고 있다가 에러핸들링시 사용)
	 * @param locale 명시적 국가/언어지정
	 * @param exceptionCreator 외부에서 별도의 Exception 생성기 지정
	 * @return Exception EgovBizException 객체
	 */
	private ValidationException validationException(final String errorKey, final ERROR_TYPE errorType, final String msgKey, final String[] msgArgs, final Exception e,
			final Locale locale, ValidationExceptionCreator exceptionCreator) {
		ValidationExceptionCreator eC = null;
		if (exceptionCreator == null) {
			eC = new ValidationExceptionCreator() {

				public ValidationException createValidationException(MessageSource messageSource) {
					return new ValidationException(messageSource, errorKey, errorType, msgKey, msgArgs, locale, e);
				}
			};
		}
		return eC.createValidationException(messageSource);
	}

	private interface ValidationExceptionCreator {

		ValidationException createValidationException(MessageSource messageSource);
	}
	

	
	/**
	 * 밸리데이션 로직에러 발생(꽁통)
	 * @param msg
	 * @throws BizException
	 */
	protected void throwValidationException(final String errorKey, final String msgKey)throws ValidationException{
		throwValidationException(errorKey, msgKey,null);
	}
	protected void throwValidationException(final String errorKey, final String msgKey,String[] args)throws ValidationException{
		throw validationException(errorKey, msgKey,args);
	}
}
