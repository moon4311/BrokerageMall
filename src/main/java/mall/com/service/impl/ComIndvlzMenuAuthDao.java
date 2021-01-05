package mall.com.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import mall.com.vo.ComIndvlzMenuAuthVO;

/**
 * @Class Name : ComIndvlzMenuAuthDao.java
 * @Description : 사용자별메뉴관리 dao
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Repository("comIndvlzMenuAuthDao")
public class ComIndvlzMenuAuthDao extends AbstractDao {

	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	private final String namespace = "injnsobang.mapper.com.ComIndvlzMenuAuth.";
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzMenuAuthVO> selectIndvlzMenuAuthList(ComIndvlzMenuAuthVO searchVO) {
		return selectList(namespace + "selectList", searchVO);
	}

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectIndvlzMenuAuthTot(ComIndvlzMenuAuthVO searchVO) {
		return (Integer) select(namespace + "selectTot", searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComIndvlzMenuAuthVO selectIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO) {
		return (ComIndvlzMenuAuthVO) select(namespace + "select", searchVO);
	}

	/**
	 * 키가져오기
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectKey() {
//		return idgenService.getNextStringId();
		return "";
	}

	/**
	 * 추가
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void insertIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO) {
		update(namespace + "insert", searchVO);
	}

	/**
	 * 수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO) {
		update(namespace + "update", searchVO);
	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO) {
		update(namespace + "delete", searchVO);
	}
	
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역 /////////////////////////////////////////////
	// /////////////////////private,protected 메소드 선언 영역 끝 //////////////////////////////////////////
}
