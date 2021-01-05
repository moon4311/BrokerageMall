package mall.com.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import mall.com.vo.ComGrpMenuAuthVO;

/**
 * @Class Name : ComGrpMenuAuthDao.java
 * @Description : 그룹별메뉴관리 dao
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Repository("comGrpMenuAuthDao")
public class ComGrpMenuAuthDao extends AbstractDao {

	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	private final String namespace = "injnsobang.mapper.com.ComGrpMenuAuth.";
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComGrpMenuAuthVO> selectGrpMenuAuthList(ComGrpMenuAuthVO searchVO) {
		return selectList(namespace + "selectList", searchVO);
	}

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectGrpMenuAuthTot(ComGrpMenuAuthVO searchVO) {
		return (Integer) select(namespace + "selectTot", searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComGrpMenuAuthVO selectGrpMenuAuth(ComGrpMenuAuthVO searchVO) {
		return (ComGrpMenuAuthVO) select(namespace + "select", searchVO);
	}
	
	/**
	 * 추가
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void insertGrpMenuAuth(ComGrpMenuAuthVO searchVO) {
		update(namespace + "insert", searchVO);
	}

	/**
	 * 수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateGrpMenuAuth(ComGrpMenuAuthVO searchVO) {
		update(namespace + "update", searchVO);
	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteGrpMenuAuth(ComGrpMenuAuthVO searchVO) {
		update(namespace + "delete", searchVO);
	}
	
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역 /////////////////////////////////////////////
	// /////////////////////private,protected 메소드 선언 영역 끝 //////////////////////////////////////////
}
