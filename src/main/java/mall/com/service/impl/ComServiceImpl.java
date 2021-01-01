package injnsobang.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.service.ComMbrLogService;
import injnsobang.com.service.ComService;
import injnsobang.com.util.SpringHelperUtil;
import injnsobang.com.vo.ComMbrLogVO;
import injnsobang.com.vo.ComMbrVO;

/**
 *  공통기능 ServiceImpl 클래스
 * @author JNIT
 * @since 2015.02.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *	수정일		수정자		수정내용
 *	-------		--------	---------------------------
 *  2015.02.18	meddogi1	 최초 생성
 *
 * </pre>
 */
@Service("comService")
public class ComServiceImpl extends AbstractServiceImpl implements ComService {
	
	@Resource(name="comMbrLogService")
	private ComMbrLogService comMbrLogService;
	
	public boolean isLogin(){return super.isLogin();}

	public String getLoginID(){return super.getLoginID();}
	
	public String getLoginGroupID(){return super.getLoginGroupID();}

	public ComMbrVO getLoginVO(){return super.getLoginVO();}

	public boolean isAdmin(){return super.isAdmin();}
	/**
	 * 현재 페이지 로그 내용 업데이트
	 */
	public void updateCurrentLog(String logCnts) {
		String mbrLogMngmNo = (String)SpringHelperUtil.getRequest().getAttribute(REQUEST_LOG_CNT_NO);//controller의 setIndex메소드에서 로그남길때 사용되었던 로그번호
		if(mbrLogMngmNo != null) {
			try{
				ComMbrLogVO logVO = new ComMbrLogVO();
				logVO.setMbrLogMngmNo(mbrLogMngmNo);
				logVO.setLogCnts(logCnts);
				comMbrLogService.updateComMbrLogCnts(logVO);
			}catch(EgovBizException e) {
				log.error(e.getMessage());
			}
		}
	}


	public String getMessage(String code) {return super.getMessage(code, null);}
	
	public String getMessage(String code, String[] args) {return super.getMessage(code, args);}
	
	public EgovBizException processException(final String msgKey){return processException(msgKey);}
	
	public EgovBizException processException(final String msgKey,  final String[] msgArgs){return super.processException(msgKey, msgArgs);}
}
