package egovframework.com.ccm.ccc.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.ccm.ccc.service.CmmnClCodeManageService;
import egovframework.com.ccm.ccc.service.CmmnClCodeVO;
import egovframework.com.ccm.ccc.validator.CmmnClCodeManageValidator;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import injnsobang.com.enums.EnumMenuCode;
import injnsobang.com.exception.ValidatorException;
import injnsobang.com.util.NullUtil;
import injnsobang.com.web.AbstractController;

/**
 *
 * 공통분류코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
 * 
 * @author 공통서비스 개발팀 이중호
 * @since 2009.04.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *	     수정일		수정자          			 수정내용
 *  ---------  -------   ---------------------------
 *  2009.04.01	이중호         최초 생성
 *  2011.8.26	정진오	 IncludedInfo annotation 추가
 *  2017.06.08	이정은	 표준프레임워크 v3.7 개선
 *
 *      </pre>
 */

@Controller
@RequestMapping(value="/injnsobang/ccm/ccc/")
public class CmmnClCodeManageController extends AbstractController {
	
	protected String getPkg(){return "/injnsobang/ccm/ccc/";}
	
	@Resource(name = "CmmnClCodeManageService")
	private CmmnClCodeManageService cmmnClCodeManageService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	@Resource(name = "cmmnClCodeManageValidator")
	private CmmnClCodeManageValidator validator;

	/**
	 * 분기문
	 */
	@RequestMapping(value="index.do")
	public String index(
			@ModelAttribute	CmmnClCodeVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCommMode())))searchVO.setCommMode("list");		//기본 manage로 포워딩		
		setIndexProcess(EnumMenuCode.CCM_CCC, request, searchVO.getCommMode());			//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, getPkg()+"index.do");
		
		StringBuilder sb = new StringBuilder("forward:");
		sb.append(getPkg()).append(searchVO.getCommMode()).append(".do");
		log.debug(sb);
		return sb.toString();
	}
	
	
	/**
	 * 공통분류코드 목록을 조회한다.
	 * 
	 * @param loginVO
	 * @param searchVO
	 * @param model
	 * @return "injnsobang/com/ccm/ccc/SelectCcmCmmnClCodeList"
	 * @throws Exception
	 */
	@RequestMapping(value = "list.do")
	public String list(@ModelAttribute("searchVO") CmmnClCodeVO searchVO, ModelMap model) throws Exception {
		
		/** EgovPropertyService.sample */
		if(searchVO.getPageUnit() == 0)searchVO.setPageUnit(getDefaultPageUnit());
		if(searchVO.getPageSize() == 0)searchVO.setPageSize(getDefaultPageSize());

		
		int totCnt = cmmnClCodeManageService.selectCmmnClCodeListTotCnt(searchVO);
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		paginationInfo.setTotalRecordCount(totCnt);
		

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		//전체가져올때
		if(searchVO.getPageUnit() == -1)searchVO.setRecordCountPerPage(0);
		
		model.addAttribute("paginationInfo",paginationInfo);
		model.addAttribute("resultCnt",totCnt);
		model.addAttribute("resultList",cmmnClCodeManageService.selectCmmnClCodeList(searchVO));

		return "injnsobang/com/ccm/ccc/EgovCcmCmmnClCodeList";
	}

	/**
	 * 공통분류코드 상세항목을 조회한다.
	 * 
	 * @param loginVO
	 * @param cmmnClCode
	 * @param model
	 * @return "injnsobang/com/ccm/ccc/SelectCcmCmmnClCodeDetail.do"
	 * @throws Exception
	 */
	@RequestMapping(value = "view.do")
	public String view(@ModelAttribute("searchVO") CmmnClCodeVO cmmnClCodeVO,
			ModelMap model) throws Exception {

		CmmnClCodeVO vo = cmmnClCodeManageService.selectCmmnClCodeDetail(cmmnClCodeVO);

		model.addAttribute("result", vo);
		model.addAttribute("isModifiable",cmmnClCodeManageService.isModifiable(vo));
		
		return "injnsobang/com/ccm/ccc/EgovCcmCmmnClCodeDetail";
	}
	
	/**
	 * 공통분류코드 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param cmmnClCodeVO
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("write.do")
	public String write(@ModelAttribute("searchVO")CmmnClCodeVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception {
		throwDirect(request);
		
		if(!"".equals(NullUtil.nullString(searchVO.getClCode()))) {
			CmmnClCodeVO result = cmmnClCodeManageService.selectCmmnClCodeDetail(searchVO);
			//읽기 권한 체크
			if(!cmmnClCodeManageService.isViewable(result))throwBizException("com.error.noauth.view");
			model.addAttribute("result",result);
			model.addAttribute("isModifiable",cmmnClCodeManageService.isModifiable(result));
		}	
		
		return "injnsobang/com/ccm/ccc/EgovCcmCmmnClCodeRegist";
	}
	
	 /**
     * 공통분류코드를 등록/수정 한다.
     * 
     * @param CmmnClCodeVO
     * @param status
     * @param model
     * @return /ccm/ccc/SelectCcmCmmnClCodeList.do";
     * @throws Exception
     */
    @RequestMapping("writeActionJson.do")
    public String writeActionJson(@ModelAttribute("cmmnClCodeVO") CmmnClCodeVO searchVO,
	    BindingResult errors,
	    HttpServletRequest request,
	    ModelMap model) throws Exception {
    	throwDirect(request);
    	
    	
    	boolean isSuccess = false;
		String msg = "";
		try{
			validator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
			
			cmmnClCodeManageService.upsertCmmnClCode(searchVO);
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
     * 공통분류코드를 삭제한다.
     * 
     * @param cmmnClCodeVO
     * @param status
     * @param model
     * @return /ccm/ccc/SelectCcmCmmnClCodeList.do";
     * @throws Exception
     */
    @RequestMapping("deleteActionJson.do")
    public String deleteActionJson(
    		@ModelAttribute("cmmnClCodeVO") CmmnClCodeVO cmmnClCodeVO,
    		HttpServletRequest request,
    		ModelMap model) throws Exception {

    		throwDirect(request);
    		
    		boolean isSuccess = false;
    		String msg = "";
    		try{
    			cmmnClCodeManageService.deleteCmmnClCode(cmmnClCodeVO);
    			isSuccess = true;
    		}catch(EgovBizException e){
    			msg = e.getMessage();
    		}catch(Exception e){
    			log.error(e.getMessage());
    			msg = "알 수 없는 에러";
    		}
    		
    		return getResponseJsonView(model, isSuccess, msg);
        } 
    
}