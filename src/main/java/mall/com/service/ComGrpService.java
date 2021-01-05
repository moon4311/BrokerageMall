package mall.com.service;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.vo.ComGrpVO;

/**
 * @Class Name : ComGrpService.java
 * @Description : 그룹코드 Service
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
public interface ComGrpService {
    
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComGrpVO> selectComGrpList(ComGrpVO searchVO);

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	Integer selectComGrpTot(ComGrpVO searchVO);

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	ComGrpVO selectComGrp(ComGrpVO searchVO);

	/**
	 * 추가/수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void writeComGrp(ComGrpVO searchVO) throws EgovBizException;

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void deleteComGrp(ComGrpVO searchVO) throws EgovBizException;


	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	boolean isViewable(ComGrpVO searchVO);


	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	boolean isModifiable(ComGrpVO searchVO);


	/**
	 * 해당 데이터를 삭제할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	boolean isDeletable(ComGrpVO searchVO);
}
