package mall.com.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import mall.com.enums.EnumMenuCode;
import mall.com.exception.ValidatorException;
import mall.com.service.ComAuthGrpService;
import mall.com.util.NullUtil;
import mall.com.validator.ComAuthGrpValidator;
import mall.com.vo.ComAuthGrpVO;


/**
 * @Class Name : ComAuthGrpController.java
 * @Description : 그룹 Controller
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Controller
public class ComAuthGrpController extends AbstractController{
	
	protected String getPkg(){return "injnsobang/com/authGrp";}
	
	@Resource(name = "comAuthGrpService")
	private ComAuthGrpService comAuthGrpService;

	@Resource(name = "comAuthGrpValidator")
	private ComAuthGrpValidator comAuthGrpValidator;
	/**
	 * 분기문
	 */
	@RequestMapping(value="/injnsobang/com/authGrp/index.do")
	public String index(
			@ModelAttribute	ComAuthGrpVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCoagMode())))searchVO.setCoagMode("list");		//기본 list로 포워딩		
		setIndexProcess(EnumMenuCode.COM_AUTH_GRP, request, searchVO.getCoagMode());				//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, "/injnsobang/com/authGrp/index.do");

		StringBuilder sb = new StringBuilder("forward:");
		sb.append("/injnsobang/com/authGrp/" + searchVO.getCoagMode() + ".do");
		
		return sb.toString();
	}


	/**
	 * 리스트
	 */
	@RequestMapping(value="/injnsobang/com/authGrp/list.do")
	public String list(
			@ModelAttribute("searchVO")	ComAuthGrpVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		//권한에 따른 데이터제한처리
//		setListDefaultCondition(searchVO);
		
		//기본값으로 스프링빈에 설정된 값 로드
		if(searchVO.getPageUnit() == 0)searchVO.setPageUnit(getDefaultPageUnit());
		if(searchVO.getPageSize() == 0)searchVO.setPageSize(getDefaultPageSize());
	
		//총갯수
		int totalRecordCount = comAuthGrpService.selectComAuthGrpTot(searchVO);

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
		model.addAttribute("resultList",comAuthGrpService.selectComAuthGrpList(searchVO));
		
		
		return "injnsobang/com/authGrp/list";
	}

	/**
	 * 뷰 Json
	 */
	@RequestMapping(value="/injnsobang/com/authGrp/viewJson.do")
	public String viewJson(
			@ModelAttribute("searchVO")	ComAuthGrpVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		ComAuthGrpVO result = comAuthGrpService.selectComAuthGrp(searchVO);
		//읽기 권한 체크
//		if(!comAuthGrpService.isViewable(result))throwBizException("com.error.noauth.view");
		
//		model.addAttribute("result",result);
//		model.addAttribute("isModifiable",comAuthGrpService.isModifiable(result));
		return getResponseJsonView(model, result);
	}


	/**
	 * 삭제
	 */
	@RequestMapping(value="/injnsobang/com/authGrp/deleteActionJson.do")
	public String deleteActionJson(
			@ModelAttribute("searchVO")	ComAuthGrpVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		boolean isSuccess = false;
		String msg = "";
		try{
			comAuthGrpService.deleteComAuthGrp(searchVO);
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
	 * 추가 / 수정 처리
	 */
	@RequestMapping(value="/injnsobang/com/authGrp/writeActionJson.do")
	public String writeActionJson(
			@ModelAttribute("searchVO")	ComAuthGrpVO searchVO,
			BindingResult errors,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		

		boolean isSuccess = false;
		String msg = "";
		try{
			comAuthGrpValidator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
						
			comAuthGrpService.writeComAuthGrp(searchVO);
			
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
	 * 리스트 JSON
	 */
	@RequestMapping(value="/injnsobang/com/authGrp/listJson.do")
	public String listJson(
			@ModelAttribute("searchVO")	ComAuthGrpVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		searchVO.setRecordCountPerPage(0);
		return getResponseJsonView(model, comAuthGrpService.selectComAuthGrpList(searchVO));
	}
}
