package mall.com.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import injnsobang.com.vo.ComMenuVO;

/**
 * @Class Name : ComMenuDao.java
 * @Description : 메뉴 dao
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Repository("comMenuDao")
public class ComMenuDao extends AbstractDao {

	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	private final String namespace = "injnsobang.mapper.com.ComMenu.";
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComMenuVO> selectMenuList(ComMenuVO searchVO) {
		return selectList(namespace + "selectList", searchVO);
	}

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectMenuTot(ComMenuVO searchVO) {
		return (Integer) select(namespace + "selectTot", searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComMenuVO selectMenu(ComMenuVO searchVO) {
		return (ComMenuVO) select(namespace + "select", searchVO);
	}

	/**
	 * 추가
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void insertMenu(ComMenuVO searchVO) {
		update(namespace + "insert", searchVO);
	}

	/**
	 * 수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateMenu(ComMenuVO searchVO) {
		update(namespace + "update", searchVO);
	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteMenu(ComMenuVO searchVO) {
		update(namespace + "delete", searchVO);
	}

	/**
	 * id값들로 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteMenuByIds(ComMenuVO searchVO) {
		update(namespace + "deleteByIds", searchVO);
	}
	

	/**
	 * 해당 id값들의 하위메뉴 존재여부 
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectTotChildrenByIds(ComMenuVO searchVO) {
		return (Integer) select(namespace + "selectTotChildrenByIds", searchVO);
	}
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역 /////////////////////////////////////////////
	// /////////////////////private,protected 메소드 선언 영역 끝 //////////////////////////////////////////
}
