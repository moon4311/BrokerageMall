package mall.com.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.enums.EnumMenuCode;
import mall.com.exception.ValidatorException;
import mall.com.service.ComMenuService;
import mall.com.util.NullUtil;
import mall.com.validator.ComMenuValidator;
import mall.com.vo.ComMenuVO;


/**
 * @Class Name : ComMenuController.java
 * @Description : 메뉴 Controller
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Controller
public class ComMenuController extends AbstractController{

	protected String getPkg(){return "injnsobang/cm/menu";}
	
	@Resource(name = "comMenuService")
	private ComMenuService comMenuService;
	
	@Resource(name = "comMenuValidator")
	private ComMenuValidator comMenuValidator;

	
	/**
	 * 분기문
	 */
	@RequestMapping(value="/injnsobang/com/menu/index.do")
	public String indexLarge(
			@ModelAttribute	ComMenuVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCmmMode())))searchVO.setCmmMode("list");//기본 list로 포워딩		
		setIndexProcess(EnumMenuCode.COM_MENU, request, searchVO.getCmmMode());//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, "/injnsobang/com/menu/index.do");
		
		StringBuilder sb = new StringBuilder("forward:");
		sb.append("/injnsobang/com/menu/" + searchVO.getCmmMode() + ".do");
		
		return sb.toString();
	}

	/**
	 * 리스트
	 */
	@RequestMapping(value="/injnsobang/com/menu/list.do")
	public String list(
			@ModelAttribute("searchVO") ComMenuVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		/*
		//기본값으로 스프링빈에 설정된 값 로드
		if(searchVO.getPageUnit() == 0)searchVO.setPageUnit(getDefaultPageUnit());
		if(searchVO.getPageSize() == 0)searchVO.setPageSize(getDefaultPageSize());
	
		//총갯수
		int totalRecordCount = comMenuService.selectMenuTot(searchVO);

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
		*/
		searchVO.setRecordCountPerPage(0);
		model.addAttribute("resultList",comMenuService.selectMenuList(searchVO));
		
		return "injnsobang/com/menu/list";
	}

	/**
	 * 뷰 Json
	 */
	@RequestMapping(value="/injnsobang/com/menu/viewJson.do")
	public String viewJson(
			ComMenuVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);

		ComMenuVO result = comMenuService.selectMenu(searchVO);
		
		return getResponseJsonView(model, result);
	}


	/**
	 * 삭제
	 */
	@RequestMapping(value="/injnsobang/com/menu/deleteActionJson.do")
	public String deleteActionJson(
			ComMenuVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		boolean isSuccess = false;
		String msg = "";
		try{
			
			comMenuService.deleteMenu(searchVO);
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
	@RequestMapping(value="/injnsobang/com/menu/write.do")
	public String write(
			ComMenuVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		if(!"".equals(NullUtil.nullString(searchVO.getMenuSno()))){
			ComMenuVO result = comMenuService.selectMenu(searchVO);
			model.addAttribute("result",result);
		}
		
		return "injnsobang/com/menu/write";
	}
	

	/**
	 * 추가 / 수정 처리
	 */
	@RequestMapping(value="/injnsobang/com/menu/writeActionJson.do")
	public String writeActionJson(
			ComMenuVO searchVO,
			BindingResult errors,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);		
		
		boolean isSuccess = false;
		String msg = "";
		try{
			comMenuValidator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
			comMenuService.writeMenu(searchVO);
			
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
	 * 상위코드를 통한 전체 데이터 json
	 */
	@RequestMapping(value="/injnsobang/com/menu/allListJson.do")
	public String allListJson(
			ComMenuVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		setIndexProcess(EnumMenuCode.NONE, request, searchVO.getCmmMode());
		
		searchVO.setRecordCountPerPage(0);//기본 전체데이터 가져오기
		return getResponseJsonView(model, comMenuService.selectMenuList(searchVO));
	}
	
}
