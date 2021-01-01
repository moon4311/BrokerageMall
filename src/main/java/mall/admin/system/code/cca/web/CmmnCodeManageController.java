package egovframework.com.ccm.cca.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.ccm.cca.service.CmmnCodeManageService;
import egovframework.com.ccm.cca.service.CmmnCodeVO;
import egovframework.com.ccm.cca.validator.CmmnCodeManageValidator;
import egovframework.com.ccm.ccc.service.CmmnClCodeManageService;
import egovframework.com.ccm.ccc.service.CmmnClCodeVO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import injnsobang.com.enums.EnumMenuCode;
import injnsobang.com.exception.ValidatorException;
import injnsobang.com.util.NullUtil;
import injnsobang.com.web.AbstractController;

/**
*
* 공통코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
* @author 공통서비스 개발팀 이중호
* @since 2009.04.01
* @version 1.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*   수정일      수정자           수정내용
*  -------    --------    ---------------------------
*   2009.04.01  이중호          최초 생성
*   2011.8.26	정진오			IncludedInfo annotation 추가
*   2017.08.16	이정은	표준프레임워크 v3.7 개선
*
* </pre>
*/

@Controller
@RequestMapping(value="/injnsobang/ccm/cca/")
public class CmmnCodeManageController extends AbstractController{
	
	protected String getPkg(){return "/injnsobang/ccm/cca/";}
	
	@Resource(name = "CmmnCodeManageService")
    private CmmnCodeManageService cmmnCodeManageService;

	@Resource(name = "CmmnClCodeManageService")
    private CmmnClCodeManageService cmmnClCodeManageService;
	
	
	@Resource(name = "cmmnCodeManageValidator")
	private CmmnCodeManageValidator validator;
	
//	@Autowired
//	private DefaultBeanValidator beanValidator;
	

	/**
	 * 분기문
	 */
	@RequestMapping(value="index.do")
	public String index(
			@ModelAttribute	CmmnCodeVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCommMode())))searchVO.setCommMode("list");		//기본 manage로 포워딩		
		setIndexProcess(EnumMenuCode.CCM_CCA, request, searchVO.getCommMode());			//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, getPkg()+"index.do");
		
		StringBuilder sb = new StringBuilder("forward:");
		sb.append(getPkg()).append(searchVO.getCommMode()).append(".do");
		log.debug(sb);
		return sb.toString();
	}
	
	
	/**
	 * 공통분류코드 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @param model
	 * @return "injnsobang/com/ccm/cca/EgovCcmCmmnCodeList"
	 * @throws Exception
	 */
	@RequestMapping(value = "list.do")
	public String list(@ModelAttribute("searchVO") CmmnCodeVO searchVO, ModelMap model)
			throws Exception {
		
		if(searchVO.getPageUnit() == 0)searchVO.setPageUnit(getDefaultPageUnit());
		if(searchVO.getPageSize() == 0)searchVO.setPageSize(getDefaultPageSize());

		int totCnt = cmmnCodeManageService.selectCmmnCodeListTotCnt(searchVO);
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		paginationInfo.setTotalRecordCount(totCnt);
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultCnt",totCnt);
		model.addAttribute("resultList", cmmnCodeManageService.selectCmmnCodeList(searchVO));


		return "injnsobang/com/ccm/cca/EgovCcmCmmnCodeList";
	}
	
	/**
	 * 공통코드 상세항목을 조회한다.
	 * 
	 * @param loginVO
	 * @param cmmnCodeVO
	 * @param model
	 * @return "injnsobang/com/ccm/cca/EgovCcmCmmnCodeDetail"
	 * @throws Exception
	 */
	@RequestMapping(value = "view.do")
	public String view(@ModelAttribute("searchVO") CmmnCodeVO cmmnCodeVO, ModelMap model) throws Exception {
		
		CmmnCodeVO vo = cmmnCodeManageService.selectCmmnCodeDetail(cmmnCodeVO);
		
		model.addAttribute("result", vo);
		model.addAttribute("isModifiable",cmmnCodeManageService.isModifiable(vo));
		
		return "injnsobang/com/ccm/cca/EgovCcmCmmnCodeDetail";
	}
	
	/**
	 * 공통코드 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param cmmnCodeVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "write.do")
	public String write(@ModelAttribute("searchVO")CmmnCodeVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception {
		throwDirect(request);
		
		
		if(!"".equals(NullUtil.nullString(searchVO.getCodeId()))) {
			CmmnCodeVO result = cmmnCodeManageService.selectCmmnCodeDetail(searchVO);
			//읽기 권한 체크
			if(!cmmnCodeManageService.isViewable(result))throwBizException("com.error.noauth.view");
			model.addAttribute("result",result);
			model.addAttribute("isModifiable",cmmnCodeManageService.isModifiable(result));
		}	
		
		CmmnClCodeVO clCodeVO = new CmmnClCodeVO();
		clCodeVO.setFirstIndex(0);
        List<?> CmmnCodeList = cmmnClCodeManageService.selectCmmnClCodeList(clCodeVO);
        model.addAttribute("clCodeList", CmmnCodeList);

		return "injnsobang/com/ccm/cca/EgovCcmCmmnCodeRegist";
	}
	
	/**
     * 공통코드를 등록한다.
     * 
     * @param CmmnCodeVO
     * @param CmmnCodeVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("writeActionJson.do")
    public String insertCmmnCode(@ModelAttribute("cmmnCodeVO") CmmnCodeVO searchVO,
	    BindingResult errors,
	    HttpServletRequest request,
	    ModelMap model) throws Exception {
    	
    	throwDirect(request);
    	
	
		boolean isSuccess = false;
		String msg = "";
		try{
			validator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
			
			cmmnCodeManageService.upsertCmmnCode(searchVO);
			isSuccess = true;
		}catch(ValidatorException e){
			return getResponseValidatorErrorJsonView(model, errors);
		}catch(EgovBizException e){
			msg = e.getMessage();
		}catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			msg = "알 수 없는 에러";
		}
    	
    	
		return getResponseJsonView(model, isSuccess, msg);
		
    }
        
    /**
     * 공통코드를 삭제한다.
     * 
     * @param cmmnCodeVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("deleteActionJson.do")
    public String deleteCmmnCode(@ModelAttribute("cmmnCodeVO") CmmnCodeVO cmmnCodeVO,
    		HttpServletRequest request,
    		ModelMap model) throws Exception {

    		throwDirect(request);
    		
    		boolean isSuccess = false;
    		String msg = "";
    		try{
    			cmmnCodeManageService.deleteCmmnCode(cmmnCodeVO);
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