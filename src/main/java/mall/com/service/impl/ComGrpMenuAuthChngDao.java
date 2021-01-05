package mall.com.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import mall.com.vo.ComGrpMenuAuthChngVO;

/**
 * @Class Name : ComGrpMenuAuthChngDAO.java
 * @Description : 그룹메뉴권한변경이력 dao
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Repository("comGrpMenuAuthChngDao")
public class ComGrpMenuAuthChngDao extends AbstractDao {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	private final String namespace = "injnsobang.mapper.com.ComGrpMenuAuthChng.";
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComGrpMenuAuthChngVO> selectComGrpMenuAuthChngList(ComGrpMenuAuthChngVO searchVO) {
		return selectList(namespace + "selectList", searchVO);
	}

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectComGrpMenuAuthChngTot(ComGrpMenuAuthChngVO searchVO) {
		return (Integer) select(namespace + "selectTot", searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComGrpMenuAuthChngVO selectComGrpMenuAuthChng(ComGrpMenuAuthChngVO searchVO) {
		return (ComGrpMenuAuthChngVO) select(namespace + "select", searchVO);
	}

	/**
	 * 추가
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void insertComGrpMenuAuthChng(ComGrpMenuAuthChngVO searchVO) {
		update(namespace + "insert", searchVO);
	}

	/**
	 * 수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateComGrpMenuAuthChng(ComGrpMenuAuthChngVO searchVO) {
		update(namespace + "update", searchVO);
	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComGrpMenuAuthChng(ComGrpMenuAuthChngVO searchVO) {
		update(namespace + "delete", searchVO);
	}

	/**
	 * 일괄추가
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void insertAllComGrpMenuAuthChng(ComGrpMenuAuthChngVO searchVO) {
		update(namespace + "insertAll", searchVO);
	}
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역 /////////////////////////////////////////////
	// /////////////////////private,protected 메소드 선언 영역 끝 //////////////////////////////////////////
}
