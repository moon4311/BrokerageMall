package mall.com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.vo.ComMbrLogVO;

/**
 * @Class Name : ComMbrLogService.java
 * @Description : 로그 Service
 * @Modification Information
 * 
 * @author
 * @since 2020.07.21
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
public interface ComMbrLogService {
    
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComMbrLogVO> selectComMbrLogList(ComMbrLogVO searchVO);

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	Integer selectComMbrLogTot(ComMbrLogVO searchVO);

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	ComMbrLogVO selectComMbrLog(ComMbrLogVO searchVO);

	/**
	 * 추가
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void insertComMbrLog(ComMbrLogVO searchVO) throws EgovBizException;

	/**
	 * 내용 수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void updateComMbrLogCnts(ComMbrLogVO searchVO) throws EgovBizException;

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void deleteComMbrLog(ComMbrLogVO searchVO) throws EgovBizException;


	/**
	 * 메뉴코드 제외 추가.
	 * 
	 * @param act
	 * @throws Exception
	 */
	void insertComMbrLogEtc(HttpServletRequest request, String act);
}
