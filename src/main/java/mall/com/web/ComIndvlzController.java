package injnsobang.com.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import injnsobang.com.enums.EnumMenuCode;
import injnsobang.com.exception.ValidatorException;
import injnsobang.com.service.ComGrpService;
import injnsobang.com.service.ComIndvlzService;
import injnsobang.com.util.NullUtil;
import injnsobang.com.validator.ComIndvlzValidator;
import injnsobang.com.vo.ComGrpVO;
import injnsobang.com.vo.ComIndvlzVO;


/**
 * @Class Name : ComIndvlzController.java
 * @Description : 개별코드 Controller
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Controller
public class ComIndvlzController extends AbstractController{
	
	protected String getPkg(){return "injnsobang/com/indvlz";}
	
	@Resource(name = "comGrpService")
	private ComGrpService comGrpService;
	
	@Resource(name = "comIndvlzService")
	private ComIndvlzService comIndvlzService;

	@Resource(name = "comIndvlzValidator")
	private ComIndvlzValidator comIndvlzValidator;
	/**
	 * 분기문
	 */
	@RequestMapping(value="/injnsobang/com/indvlz/index.do")
	public String index(
			@ModelAttribute	ComIndvlzVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCiMode())))searchVO.setCiMode("manage");		//기본 manage로 포워딩		
		setIndexProcess(EnumMenuCode.COM_SYS_COD, request, searchVO.getCiMode());				//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, "/injnsobang/com/indvlz/index.do");
		
		StringBuilder sb = new StringBuilder("forward:");
		sb.append("/injnsobang/com/indvlz/" + searchVO.getCiMode() + ".do");
		log.debug(sb);
		return sb.toString();
	}



	/**
	 * 관리화면
	 */
	@RequestMapping(value="/injnsobang/com/indvlz/manage.do")
	public String manage(
			@ModelAttribute	ComIndvlzVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		//그룹코드 가져오기
		ComGrpVO grpVO = new ComGrpVO();
		grpVO.setChngPosblYn("Y");
		grpVO.setRecordCountPerPage(0);
		model.addAttribute("resultList",comGrpService.selectComGrpList(grpVO));

		return "injnsobang/com/indvlz/manage";
	}
	

	/**
	 * 리스트
	 */
	@RequestMapping(value="/injnsobang/com/indvlz/list.do")
	public String list(
			@ModelAttribute("searchVO")	ComIndvlzVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		//기본값으로 스프링빈에 설정된 값 로드
		if(searchVO.getPageUnit() == 0)searchVO.setPageUnit(getDefaultPageUnit());
		if(searchVO.getPageSize() == 0)searchVO.setPageSize(getDefaultPageSize());
	
		//총갯수
		int totalRecordCount = comIndvlzService.selectComIndvlzTot(searchVO);

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
		model.addAttribute("resultList",comIndvlzService.selectComIndvlzList(searchVO));
		
		
		return "injnsobang/com/indvlz/list";
	}

	/**
	 * 보기
	 */
	@RequestMapping(value="/injnsobang/com/indvlz/view.do")
	public String view(
			@ModelAttribute("searchVO")	ComIndvlzVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		ComIndvlzVO result = comIndvlzService.selectComIndvlz(searchVO);
		//읽기 권한 체크
		if(!comIndvlzService.isViewable(result))throwBizException("com.error.noauth.view");
		
		model.addAttribute("result",result);
		model.addAttribute("isModifiable",comIndvlzService.isModifiable(result));
		return "injnsobang/com/indvlz/view";
	}


	/**
	 * 삭제
	 */
	@RequestMapping(value="/injnsobang/com/indvlz/deleteActionJson.do")
	public String deleteActionJson(
			@ModelAttribute("searchVO")	ComIndvlzVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		boolean isSuccess = false;
		String msg = "";
		try{
			comIndvlzService.deleteComIndvlz(searchVO);
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
	 * 추가/수정
	 */
	@RequestMapping(value="/injnsobang/com/indvlz/write.do")
	public String write(
			@ModelAttribute("searchVO")	ComIndvlzVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		if(!"".equals(NullUtil.nullString(searchVO.getIndvlzCd()))) {
			ComIndvlzVO result = comIndvlzService.selectComIndvlz(searchVO);
			//읽기 권한 체크
			if(!comIndvlzService.isViewable(result))throwBizException("com.error.noauth.view");
			model.addAttribute("result",result);
			model.addAttribute("isModifiable",comIndvlzService.isModifiable(result));
		}		
		
		return "injnsobang/com/indvlz/write";
	}


	/**
	 * 추가 / 수정 처리
	 */
	@RequestMapping(value="/injnsobang/com/indvlz/writeActionJson.do")
	public String writeActionJson(
			@ModelAttribute("searchVO")	ComIndvlzVO searchVO,
			BindingResult errors,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		

		boolean isSuccess = false;
		String msg = "";
		try{
			comIndvlzValidator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
						
			comIndvlzService.writeComIndvlz(searchVO);
			
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
}
