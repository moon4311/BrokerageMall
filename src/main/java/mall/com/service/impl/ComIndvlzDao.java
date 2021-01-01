package mall.com.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import injnsobang.com.vo.ComIndvlzVO;

/**
 * @Class Name : ComIndvlzDAO.java
 * @Description : 개별코드 dao
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Repository("comIndvlzDao")
public class ComIndvlzDao extends AbstractDao {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	private final String namespace = "injnsobang.mapper.com.ComIndvlz.";
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzVO> selectComIndvlzList(ComIndvlzVO searchVO) {
		return selectList(namespace + "selectList", searchVO);
	}

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectComIndvlzTot(ComIndvlzVO searchVO) {
		return (Integer) select(namespace + "selectTot", searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComIndvlzVO selectComIndvlz(ComIndvlzVO searchVO) {
		return (ComIndvlzVO) select(namespace + "select", searchVO);
	}

	/**
	 * 추가
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void insertComIndvlz(ComIndvlzVO searchVO) {
		update(namespace + "insert", searchVO);
	}

	/**
	 * 수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateComIndvlz(ComIndvlzVO searchVO) {
		update(namespace + "update", searchVO);
	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComIndvlz(ComIndvlzVO searchVO) {
		update(namespace + "delete", searchVO);
	}
	
	/**
	 * 리스트(코드값, 코드명) 그룹코드로 가져오기
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzVO> selectComIndvlzListSimpleByGrpCd(String code) {
		return selectList(namespace + "selectListSimpleByGrpCd", code);
	}
	
	/**
	 * 리스트(코드값, 코드명) 상위코드로 가져오기
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzVO> selectComIndvlzListSimpleByUprCd(String code) {
		return selectList(namespace + "selectListSimpleByUprCd", code);
	}
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역 /////////////////////////////////////////////
	// /////////////////////private,protected 메소드 선언 영역 끝 //////////////////////////////////////////
}
