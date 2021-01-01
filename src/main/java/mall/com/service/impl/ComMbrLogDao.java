package injnsobang.com.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import injnsobang.com.vo.ComMbrLogVO;

/**
 * @Class Name : ComMbrLogDao.java
 * @Description : 로그 dao
 * @Modification Information
 * 
 * @author
 * @since 2020.07.21
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Repository("comMbrLogDao")
public class ComMbrLogDao extends AbstractDao {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	private final String namespace = "injnsobang.mapper.com.ComMbrLog.";
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComMbrLogVO> selectComMbrLogList(ComMbrLogVO searchVO) {
		return selectList(namespace + "selectList", searchVO);
	}

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectComMbrLogTot(ComMbrLogVO searchVO) {
		return (Integer) select(namespace + "selectTot", searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComMbrLogVO selectComMbrLog(ComMbrLogVO searchVO) {
		return (ComMbrLogVO) select(namespace + "select", searchVO);
	}
	
	/**
	 * 추가
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void insertComMbrLog(ComMbrLogVO searchVO) {
		update(namespace + "insert", searchVO);
	}

	/**
	 * 내용수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateComMbrLogCnts(ComMbrLogVO searchVO) {
		update(namespace + "updateCnts", searchVO);
	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComMbrLog(ComMbrLogVO searchVO) {
		update(namespace + "delete", searchVO);
	}
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역 /////////////////////////////////////////////
	// /////////////////////private,protected 메소드 선언 영역 끝 //////////////////////////////////////////
}
