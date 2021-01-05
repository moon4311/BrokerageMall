package mall.com.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import mall.com.vo.ComMbrVO;

/**
 * @Class Name : ComMbrDao.java
 * @Description : 로그인 dao
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Repository("comMbrDao")
public class ComMbrDao extends AbstractDao {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	private final String namespace = "injnsobang.mapper.com.ComMbr.";
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComMbrVO> selectComMbrList(ComMbrVO searchVO) {
		return selectList(namespace + "selectList", searchVO);
	}

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectComMbrTot(ComMbrVO searchVO) {
		return (Integer) select(namespace + "selectTot", searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComMbrVO selectComMbr(ComMbrVO searchVO) {
		return (ComMbrVO) select(namespace + "select", searchVO);
	}
	
	public ComMbrVO selectAppend(ComMbrVO searchVO) {
		return (ComMbrVO) select(namespace + "selectAppend", searchVO);
	}

	/**
	 * 추가
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void insertComMbr(ComMbrVO searchVO) {
		update(namespace + "insert", searchVO);
	}

	/**
	 * 수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateComMbr(ComMbrVO searchVO) {
		update(namespace + "update", searchVO);
	}
	
	public void updateAppend(ComMbrVO searchVO) {
		update(namespace + "updateAppend", searchVO);
	}

	
	
	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComMbr(ComMbrVO searchVO) {
		update(namespace + "delete", searchVO);
	}

	/**
	 * 비밀번호 틀린 횟수 수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateComMbrWrongPwCnt(ComMbrVO searchVO) {
		update(namespace + "updateWrongPwCnt", searchVO);
	}
	
	/**
	 * 비밀번호 변경일 수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateComMbrPwModified(ComMbrVO searchVO) {
		update(namespace + "updatePwModified", searchVO);
	}

	/**
	 * 비밀번호 암호화
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public String selectComMbrEncodePw(String pw) {
		return (String) select(namespace + "selectEncodePw", pw);
	}

	/**
	 * 최종로그인정보 변경
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateComMbrLastLogin(ComMbrVO searchVO) {
		update(namespace + "updateLastLogin", searchVO);
	}
	
	/**
	 * dn 등록
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public void updateComMbrUpdateDn(ComMbrVO searchVO) {
		update(namespace + "updateDn", searchVO);
	}
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역 /////////////////////////////////////////////
	// /////////////////////private,protected 메소드 선언 영역 끝 //////////////////////////////////////////
}
