package mall.com.service;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.vo.ComGrpMenuAuthVO;

/**
 * @Class Name : ComGrpMenuAuthService.java
 * @Description : 그룹별메뉴관리 Service
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
public interface ComGrpMenuAuthService {
    
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComGrpMenuAuthVO> selectGrpMenuAuthList(ComGrpMenuAuthVO searchVO);

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	Integer selectGrpMenuAuthTot(ComGrpMenuAuthVO searchVO);

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	ComGrpMenuAuthVO selectGrpMenuAuth(ComGrpMenuAuthVO searchVO);

	/**
	 * 추가/수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void writeGrpMenuAuth(ComGrpMenuAuthVO searchVO) throws EgovBizException;

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void deleteGrpMenuAuth(ComGrpMenuAuthVO searchVO) throws EgovBizException;
}
