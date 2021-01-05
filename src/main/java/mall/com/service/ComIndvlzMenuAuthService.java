package mall.com.service;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.vo.ComIndvlzMenuAuthVO;

/**
 * @Class Name : ComIndvlzMenuAuthService.java
 * @Description : 사용자별메뉴관리 Service
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
public interface ComIndvlzMenuAuthService {
    
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComIndvlzMenuAuthVO> selectIndvlzMenuAuthList(ComIndvlzMenuAuthVO searchVO);

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	Integer selectIndvlzMenuAuthTot(ComIndvlzMenuAuthVO searchVO);

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	ComIndvlzMenuAuthVO selectIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO);

	/**
	 * 키가져오기
	 * 
	 * @return
	 * @throws Exception
	 */
	String selectKey();

	/**
	 * 추가/수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void writeIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO) throws EgovBizException;

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void deleteIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO) throws EgovBizException;
}
