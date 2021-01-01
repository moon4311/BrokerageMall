package injnsobang.com.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.service.Globals;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import injnsobang.com.enums.EnumMenuCode;
import injnsobang.com.exception.ValidatorException;
import injnsobang.com.service.ComAuthGrpService;
import injnsobang.com.service.ComMbrLogService;
import injnsobang.com.service.ComMbrService;
import injnsobang.com.util.NullUtil;
import injnsobang.com.util.PatternUtil;
import injnsobang.com.util.SessionUtil;
import injnsobang.com.validator.ComLoginProcessValidator;
import injnsobang.com.validator.ComLoginPwChangeValidator;
import injnsobang.com.validator.ComMbrValidator;
import injnsobang.com.vo.ComAuthGrpVO;
import injnsobang.com.vo.ComMbrVO;


/**
 * @Class Name : ComMbrController.java
 * @Description : 회원 Controller
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Controller
public class ComMbrController extends AbstractController{
	
	protected String getPkg(){return "injnsobang/com/mbr";}
	
	@Resource(name = "comMbrLogService")
	private ComMbrLogService comMbrLogService;
	
	@Resource(name = "comMbrService")
	private ComMbrService comMbrService;

	@Resource(name = "comLoginProcessValidator")
	private ComLoginProcessValidator comLoginProcessValidator;

	@Resource(name = "comMbrValidator")
	private ComMbrValidator comMbrValidator;
	
	@Resource(name = "comLoginPwChangeValidator")
	private ComLoginPwChangeValidator comLoginPwChangeValidator;
	
	@Resource(name = "comAuthGrpService")
	private ComAuthGrpService comAuthGrpService;
	
	/**
	 * 로그인폼
	 */
	@RequestMapping(value="/injnsobang/com/mbr/login.do")
	public String login(
			@ModelAttribute("searchVO")	ComMbrVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		if(isLoginned())return "redirect:" + Globals.MAIN_PAGE;
		return "injnsobang/com/mbr/login";
	}


	/**
	 * 로그인 처리
	 */
	@RequestMapping(value="/injnsobang/com/mbr/loginActionJson.do")
	public String loginActionJson(
			ComMbrVO searchVO,
			BindingResult errors,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		boolean isSuccess = false;
		String msg = "";
		try{
			comLoginProcessValidator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
			searchVO.setMbrIpAddr(request.getRemoteAddr());
			msg = comMbrService.login(searchVO);//로그인 후 리턴페이지가 없을 경우 세션의 최초 url셋팅
			if("".equals(msg)){
				String retUrl = NullUtil.nullString(request.getParameter("retUrl"));
				if("".equals(retUrl))msg = Globals.MAIN_PAGE;
			}
			isSuccess = true;
			
			comMbrLogService.insertComMbrLogEtc(request, "로그인");//로그인 로그
		}catch(ValidatorException e){
			return getResponseValidatorErrorJsonView(model, errors);
		}catch(EgovBizException e){
			if("pass3month".equals(e.getMessage())) {//3개월이상 비밀번호 변경필요
				SessionUtil.setAttribute("passwdChangeId", searchVO.getMbrId());
			}
			msg = e.getMessage();
		}catch(Exception e){
			log.error(e.getMessage());
			msg = "알 수 없는 에러";
		}
		
		return getResponseJsonView(model, isSuccess, msg);
	}
	
	/**
	 * 3개월 이상 비밀번호 변경 폼
	 */
	@RequestMapping(value="/injnsobang/com/mbr/passwdChange.do")
	public String passwdChange(
			@ModelAttribute("searchVO")	ComMbrVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		
		return "injnsobang/com/mbr/passwdChange";
	}
	
	/**
	 * 3개월 이상 비밀번호 변경 처리
	 */
	@RequestMapping(value="/injnsobang/com/mbr/passwdChangeActionJson.do")
	public String passwdChangeActionJson(
			@ModelAttribute("searchVO")	ComMbrVO searchVO,
			BindingResult errors,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		

		boolean isSuccess = false;
		String msg = "";
		try{
			//로그인시 3개월 이상일 경우 저장한 id값
			String id = (String)SessionUtil.getAttribute("passwdChangeId");
			if(id == null)id = "==========";
			searchVO.setMbrId(id);
			
			comLoginPwChangeValidator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
			
			comMbrService.changePw(searchVO, request.getParameter("pswdOrgin"));
			
			SessionUtil.setAttribute("passwdChangeId", null);
			isSuccess = true;
		}catch(ValidatorException e){
			return getResponseValidatorErrorJsonView(model, errors);
		}catch(EgovBizException e){
			msg = e.getMessage();
		}catch(Exception e){
			log.error(e.getMessage());
			msg = "알 수 없는 에러";
		}
		
		return getResponseJsonView(model, isSuccess, msg);
	}

	/**
	 * 로그아웃 처리
	 */
	@RequestMapping(value="/injnsobang/com/mbr/logout.do")
	public String logout(
			HttpServletRequest request,
			ModelMap model) throws Exception{

		comMbrLogService.insertComMbrLogEtc(request, "로그아웃");//로그인 로그
		comMbrService.logout();
		
		return "injnsobang/com/mbr/logout";
	}

	/**
	 * 비밀번호틀린횟수 초기화
	 */
	@RequestMapping(value="/injnsobang/com/mbr/initWrongPwCnt.do")
	public String initWrongPwCnt(
			@ModelAttribute("searchVO")	ComMbrVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		comMbrService.initWrongPwCnt(searchVO);
		return "injnsobang/com/mbr/login";
	}
	
	/**
	 * 분기문
	 */
	@RequestMapping(value="/injnsobang/com/mbr/index.do")
	public String index(
			@ModelAttribute	ComMbrVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCommMode())))searchVO.setCommMode("list");		//기본 list로 포워딩		
		setIndexProcess(EnumMenuCode.COM_MBR, request, searchVO.getCommMode());				//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, "/injnsobang/com/mbr/index.do");
		
		StringBuilder sb = new StringBuilder("forward:");
		sb.append("/injnsobang/com/mbr/" + searchVO.getCommMode() + ".do");
		
		return sb.toString();
	}


	/**
	 * 리스트
	 */
	@RequestMapping(value="/injnsobang/com/mbr/list.do")
	public String list(
			@ModelAttribute("searchVO")	ComMbrVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		//검색값에 pk값이 있어서 셋팅
		searchVO.setMbrId(request.getParameter("searchMbrId"));
		
		//기본값으로 스프링빈에 설정된 값 로드
		if(searchVO.getPageUnit() == 0)searchVO.setPageUnit(getDefaultPageUnit());
		if(searchVO.getPageSize() == 0)searchVO.setPageSize(getDefaultPageSize());
	
		//총갯수
		int totalRecordCount = comMbrService.selectComMbrTot(searchVO);

    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
    	paginationInfo.setTotalRecordCount(totalRecordCount);
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		//전체가져올때
		if(searchVO.getPageUnit() == -1)searchVO.setRecordCountPerPage(0);
		
		model.addAttribute("paginationInfo",paginationInfo);
		model.addAttribute("resultCnt",totalRecordCount);
		model.addAttribute("resultList",comMbrService.selectComMbrList(searchVO));
		
		ComAuthGrpVO groupVO = new ComAuthGrpVO();
		groupVO.setRecordCountPerPage(0);
		model.addAttribute("groupList", comAuthGrpService.selectComAuthGrpList(groupVO));
		
		return "injnsobang/com/mbr/list";
	}

	/**
	 * 뷰
	 */
	@RequestMapping(value="/injnsobang/com/mbr/view.do")
	public String view(
			@ModelAttribute("searchVO")	ComMbrVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		ComMbrVO result = comMbrService.selectComMbr(searchVO);
		//읽기 권한 체크
		if(!comMbrService.isViewable(result))throwBizException("com.error.noauth.view");
		
		model.addAttribute("result",result);
		model.addAttribute("isModifiable",comMbrService.isModifiable(result));

		ComAuthGrpVO groupVO = new ComAuthGrpVO();
		groupVO.setRecordCountPerPage(0);
		model.addAttribute("groupList", comAuthGrpService.selectComAuthGrpList(groupVO));
		
		
		return "injnsobang/com/mbr/view";
	}


	/**
	 * 삭제
	 */
	@RequestMapping(value="/injnsobang/com/mbr/deleteActionJson.do")
	public String deleteActionJson(
			@ModelAttribute("searchVO")	ComMbrVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		boolean isSuccess = false;
		String msg = "";
		try{
			comMbrService.deleteComMbr(searchVO);
			isSuccess = true;
		}catch(EgovBizException e){
			msg = e.getMessage();
		}catch(Exception e){
			log.error(e.getMessage());
			msg = "알 수 없는 에러";
		}
		
		return getResponseJsonView(model, isSuccess, msg);
	}


	/**
	 * 추가 / 수정
	 */
	@RequestMapping(value="/injnsobang/com/mbr/write.do")
	public String write(
			@ModelAttribute("searchVO")	ComMbrVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		if(!"".equals(NullUtil.nullString(searchVO.getMbrId()))){
			ComMbrVO result = comMbrService.selectComMbr(searchVO);
			model.addAttribute("result",result);
		}
		
		ComAuthGrpVO groupVO = new ComAuthGrpVO();
		groupVO.setRecordCountPerPage(0);
		model.addAttribute("groupList", comAuthGrpService.selectComAuthGrpList(groupVO));
		
		return "injnsobang/com/mbr/write";
	}
	

	/**
	 * 추가 / 수정 처리
	 */
	@RequestMapping(value="/injnsobang/com/mbr/writeActionJson.do")
	public String writeActionJson(
			@ModelAttribute("searchVO")	ComMbrVO searchVO,
			BindingResult errors,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		

		boolean isSuccess = false;
		String msg = "";
		try{
			comMbrValidator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
						
			comMbrService.writeComMbr(searchVO);
			
			isSuccess = true;
		}catch(ValidatorException e){
			return getResponseValidatorErrorJsonView(model, errors);
		}catch(EgovBizException e){
			msg = e.getMessage();
		}catch(Exception e){
			log.error(e.getMessage());
			msg = "알 수 없는 에러";
		}
		
		return getResponseJsonView(model, isSuccess, msg);
	}
	

    /**
     * 아이디 중복확인
     */
    @RequestMapping(value = "/injnsobang/com/mbr/idcheckActionJson.do")
    public String idcheckActionJson(
    		@RequestParam String id,
    		HttpServletRequest request, ModelMap model) throws Exception {

		boolean isSuccess = false;
		String msg = "";
		try{			
			if(!PatternUtil.idRegularExpressionChk(id))throwBizException("com.error.login.idpattern");
			
			isSuccess = comMbrService.idCheck(id);		
		}catch(EgovBizException e){
			msg = e.getMessage();
		}catch(Exception e){
			log.error(e.getMessage());
			msg = "알 수 없는 에러";
		}
		
		return getResponseJsonView(model, isSuccess, msg);
    }
    
	
}
