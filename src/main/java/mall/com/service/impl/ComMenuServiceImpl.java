package mall.com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.service.ComMenuService;
import mall.com.util.NullUtil;
import mall.com.vo.ComMenuVO;

/**
 * @Class Name : ComMenuServiceImpl.java
 * @Description : 메뉴 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Service("comMenuService")
public class ComMenuServiceImpl extends AbstractServiceImpl implements ComMenuService {

	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	@Resource(name = "comMenuDao")
	private ComMenuDao comMenuDao;
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
		List<ComMenuVO> list = (List<ComMenuVO>)comMenuDao.selectMenuList(searchVO);
//		if(list != null){
//			for(ComMenuVO result:list){
//				
//			}
//		}
		return list;
	}

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public Integer selectMenuTot(ComMenuVO searchVO) {
		return comMenuDao.selectMenuTot(searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComMenuVO selectMenu(ComMenuVO searchVO) {
		ComMenuVO result = comMenuDao.selectMenu(searchVO);
		return result;
	}

	/**
	 * 추가 / 수정
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public void writeMenu(ComMenuVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);
		
//		validate(searchVO);
		
		String id = NullUtil.nullString(searchVO.getMenuSno());
		//메뉴URL 중복체크
		/*
		ComMenuVO dupVO = new ComMenuVO();
		dupVO.setRecordCountPerPage(0);
		dupVO.setCmUrl(searchVO.getCmUrl());
		List<ComMenuVO> dupList = selectMenuList(dupVO);
		if(dupList != null){
			for(ComMenuVO dup : dupList){
				if(!id.equals(dup.getCmmId()))throwBizException("com.error.menu.existUrl");
			}
		}
		*/
		
		//상위 메뉴가져오기
		if(!NullUtil.nullString(searchVO.getUprMenuSno()).equals("")) {
			ComMenuVO uprResult = new ComMenuVO();
			uprResult.setMenuSno(searchVO.getUprMenuSno());
			uprResult = selectMenu(uprResult);
			if(uprResult == null || uprResult.getMenuSno() == null)throwBizException("com.error.nodata");
			searchVO.setMenuLvlVl((NullUtil.nullInt(uprResult.getMenuLvlVl()) + 1)+""); 
		}else {
			searchVO.setMenuLvlVl("1");
		}

		if("".equals(id)){
			String sno = searchVO.getUprMenuSno();
			if(sno.equals("")) searchVO.setUprMenuSno("0");
			comMenuDao.insertMenu(searchVO);
			id = searchVO.getMenuSno();
		}else{
			ComMenuVO result = selectMenu(searchVO);
			if(!isModifiable(result))throwBizException("com.error.noauth.modify");
			
			id = result.getMenuSno();
			comMenuDao.updateMenu(searchVO);
		}

	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteMenu(ComMenuVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);
		
		ComMenuVO result = selectMenu(searchVO);
		if(!isDeletable(result))throwBizException("com.error.noauth.delete");
		
		//삭제시 하위 메뉴 체크
		if(selectTotChildrenByIds(searchVO) > 0)throwBizException("com.error.menu.existChild");
		
		comMenuDao.deleteMenu(searchVO);
		
//		comMenuDao.deleteMenuByIds(searchVO);
	}


	/**
	 * 해당 id값들의 하위메뉴 존재여부 
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public Integer selectTotChildrenByIds(ComMenuVO searchVO) {
		return comMenuDao.selectTotChildrenByIds(searchVO);
	}
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역
	// ///////////////////////////////////////////////////////////////////
	/**
	 * 벨리데이션 체크
	 * @param searchVO
	 * @throws EgovBizException
	private void validate(ComMenuVO searchVO) throws ValidationException{
		
	}
	 */
	// /////////////////////private,protected 메소드 선언 영역 끝
	// ///////////////////////////////////////////////////////////////////
}
