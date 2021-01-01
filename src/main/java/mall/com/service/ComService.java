package injnsobang.com.service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.vo.ComMbrVO;



/**
 *  공통 Service 클래스
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
 *   2020.07.21            최초 생성
 *
 * </pre>
 */
public interface ComService{
	public static final String REQUEST_LOG_CNT_NO = "LOG_CNT_NO";
	
	//로그인 여부
	boolean isLogin();
	//로그인 아이디 가져오기
	String getLoginID();
	//로그인 그룹아이디 가져오기
	String getLoginGroupID();
	//로그인 정보가져오기
	ComMbrVO getLoginVO() ;
	
	//관리자 여부
	boolean isAdmin();
	
	//현재 실행되는 페이지의 로그 내용 업데이트
	void updateCurrentLog(String logCnts);
	
	//메세지 properties에 있는 메세지 불러오기
	String getMessage(String code);
	String getMessage(String code, String[] args);
	
	//메시지 properties값으로 EgovBizException 발생
	EgovBizException processException(final String msgKey);
	EgovBizException processException(final String msgKey,  final String[] msgArgs);
}