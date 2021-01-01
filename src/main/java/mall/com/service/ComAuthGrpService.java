package injnsobang.com.service;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.vo.ComAuthGrpVO;

/**
 * @Class Name : ComAuthGrpService.java
 * @Description : 그룹 Service
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
public interface ComAuthGrpService {
    
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComAuthGrpVO> selectComAuthGrpList(ComAuthGrpVO searchVO);

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	Integer selectComAuthGrpTot(ComAuthGrpVO searchVO);

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	ComAuthGrpVO selectComAuthGrp(ComAuthGrpVO searchVO);

	/**
	 * 추가/수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void writeComAuthGrp(ComAuthGrpVO searchVO) throws EgovBizException;

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void deleteComAuthGrp(ComAuthGrpVO searchVO) throws EgovBizException;


	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	boolean isViewable(ComAuthGrpVO searchVO);


	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	boolean isModifiable(ComAuthGrpVO searchVO);
}
