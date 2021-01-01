package injnsobang.com.service;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.vo.ComMenuVO;

/**
 * @Class Name : ComMenuService.java
 * @Description : 메뉴 Service
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
public interface ComMenuService {
	public static final String TOP_PARENT_ID = "_TOP";//최상위 ID
    
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ComMenuVO> selectMenuList(ComMenuVO searchVO);

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	Integer selectMenuTot(ComMenuVO searchVO);

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	ComMenuVO selectMenu(ComMenuVO searchVO);

	/**
	 * 추가/수정
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void writeMenu(ComMenuVO searchVO) throws EgovBizException;

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	void deleteMenu(ComMenuVO searchVO) throws EgovBizException;
}
