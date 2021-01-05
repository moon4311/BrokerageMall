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
import mall.com.service.ComSmplService;
import mall.com.util.NullUtil;
import mall.com.validator.ComSmplValidator;
import mall.com.vo.ComSmplVO;


/**
 * @Class Name : ComSmplController.java
 * @Description : 샘플 Controller
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Controller
public class ComSmplController extends AbstractController{
	
	protected String getPkg(){return "injnsobang/com/smpl";}
	
	@Resource(name = "comSmplService")
	private ComSmplService comSmplService;

	@Resource(name = "comSmplValidator")
	private ComSmplValidator comSmplValidator;
	/**
	 * 분기문
	 */
	@RequestMapping(value="/injnsobang/com/smpl/index.do")
	public String index(
			@ModelAttribute	ComSmplVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCsMode())))searchVO.setCsMode("list");		//기본 list로 포워딩		
		setIndexProcess(EnumMenuCode.COM_SMPL, request, searchVO.getCsMode());				//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, "/injnsobang/com/smpl/index.do");
		
		StringBuilder sb = new StringBuilder("forward:");
		sb.append("/injnsobang/com/smpl/" + searchVO.getCsMode() + ".do");
		
		return sb.toString();
	}


	/**
	 * 리스트
	 */
	@RequestMapping(value="/injnsobang/com/smpl/list.do")
	public String list(
			@ModelAttribute("searchVO")	ComSmplVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		if("Y".equals(request.getParameter(REQUEST_EXCEL_YN))){
			
			searchVO.setRecordCountPerPage(0);
			model.addAttribute("resultList",comSmplService.selectComSmplList(searchVO));
			return getResponseExcelView(model, "smpl", "샘플");
		}else {

			//기본값으로 스프링빈에 설정된 값 로드
			if(searchVO.getPageUnit() == 0)searchVO.setPageUnit(getDefaultPageUnit());
			if(searchVO.getPageSize() == 0)searchVO.setPageSize(getDefaultPageSize());
		
			//총갯수
			int totalRecordCount = comSmplService.selectComSmplTot(searchVO);

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
			model.addAttribute("resultList",comSmplService.selectComSmplList(searchVO));
		}
		
		
		return "injnsobang/com/smpl/list";
	}

	/**
	 * 보기
	 */
	@RequestMapping(value="/injnsobang/com/smpl/view.do")
	public String view(
			@ModelAttribute("searchVO")	ComSmplVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		ComSmplVO result = comSmplService.selectComSmpl(searchVO);
		//읽기 권한 체크
		if(!comSmplService.isViewable(result))throwBizException("com.error.noauth.view");
		
		model.addAttribute("result",result);
		model.addAttribute("isModifiable",comSmplService.isModifiable(result));
		return "injnsobang/com/smpl/view";
	}


	/**
	 * 삭제
	 */
	@RequestMapping(value="/injnsobang/com/smpl/deleteActionJson.do")
	public String deleteActionJson(
			@ModelAttribute("searchVO")	ComSmplVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		boolean isSuccess = false;
		String msg = "";
		try{
			comSmplService.deleteComSmpl(searchVO);
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
	@RequestMapping(value="/injnsobang/com/smpl/write.do")
	public String write(
			@ModelAttribute("searchVO")	ComSmplVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		if(!"".equals(NullUtil.nullString(searchVO.getSmplSno()))) {
			ComSmplVO result = comSmplService.selectComSmpl(searchVO);
			//읽기 권한 체크
			if(!comSmplService.isViewable(result))throwBizException("com.error.noauth.view");
			model.addAttribute("result",result);
			model.addAttribute("isModifiable",comSmplService.isModifiable(result));
		}		
		
		return "injnsobang/com/smpl/write";
	}


	/**
	 * 추가 / 수정 처리
	 */
	@RequestMapping(value="/injnsobang/com/smpl/writeActionJson.do")
	public String writeActionJson(
			@ModelAttribute("searchVO")	ComSmplVO searchVO,
			BindingResult errors,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		

		boolean isSuccess = false;
		String msg = "";
		try{
			comSmplValidator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
						
			comSmplService.writeComSmpl(searchVO);
			
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
