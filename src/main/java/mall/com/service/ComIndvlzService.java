package mall.com.service;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.enums.EnumGrpCd;
import injnsobang.com.vo.ComIndvlzVO;

/**
 * @Class Name : ComIndvlzService.java
 * @Description : 개별코드 Service
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
public interface ComIndvlzService {
    
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComIndvlzVO> selectComIndvlzList(ComIndvlzVO searchVO);

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	Integer selectComIndvlzTot(ComIndvlzVO searchVO);

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	ComIndvlzVO selectComIndvlz(ComIndvlzVO searchVO);

	/**
	 * 추가/수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void writeComIndvlz(ComIndvlzVO searchVO) throws EgovBizException;

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void deleteComIndvlz(ComIndvlzVO searchVO) throws EgovBizException;


	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	boolean isViewable(ComIndvlzVO searchVO);


	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	boolean isModifiable(ComIndvlzVO searchVO);

	/**
	 * 전체코드 리스트 가져오기
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzVO> getAllIndvlz();
	
	/**
	 * 그룹코드로 개별코드 리스트 가져오기
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComIndvlzVO> getIndvlzByGrpCd(EnumGrpCd grpCd);

	/**
	 * 그룹코드 및 상위코드로 개별코드 리스트 가져오기
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComIndvlzVO> getIndvlzByGrpCd(EnumGrpCd grpCd, String uprCd);

	/**
	 * 상위코드로 개별코드 리스트 가져오기
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComIndvlzVO> getIndvlzByUprCd(String uprCd);


	/**
	 * 해당 코드가 그룹에 해당하는지 체크
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	boolean isCdInGrp(EnumGrpCd grpCd, String cd);



	/**
	 * 코드값으로 이름 가져오기
	 * 
	 * @param grpCd
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	String getCdNmInGrp(EnumGrpCd grpCd, String cd);

	/**
	 * 이름으로 코드값 가져오기
	 * 
	 * @param grpCd
	 * @param cdNm
	 * @return
	 * @throws Exception
	 */
	String getCdInGrp(EnumGrpCd grpCd, String cdNm);
}
