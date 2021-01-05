package mall.admin.system.code.cde.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import mall.admin.system.code.cca.service.CmmnCodeManageService;
import mall.admin.system.code.cca.service.CmmnCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import mall.admin.system.code.ccc.service.CmmnClCodeManageService;
import mall.admin.system.code.ccc.service.CmmnClCodeVO;
import mall.admin.system.code.cde.service.CmmnDetailCodeManageService;
import mall.admin.system.code.cde.service.CmmnDetailCodeVO;
import mall.admin.system.code.cde.validator.CmmnDetailCodeManageValidator;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import mall.com.enums.EnumMenuCode;
import mall.com.exception.ValidatorException;
import mall.com.util.NullUtil;
import mall.com.web.AbstractController;

/**
*
* 공통상세코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
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
*   2009.04.01  이중호       최초 생성
*   2011.08.26	정진오	IncludedInfo annotation 추가
*   2017.08.08	이정은	표준프레임워크 v3.7 개선
*
* </pre>
*/

@Controller
@RequestMapping(value="/injnsobang/ccm/cde/")
public class CmmnDetailCodeManageController extends AbstractController {

	protected String getPkg(){return "/injnsobang/ccm/cde/";}
	
	@Resource(name = "CmmnDetailCodeManageService")
   private CmmnDetailCodeManageService cmmnDetailCodeManageService;

	@Resource(name = "CmmnClCodeManageService")
   private CmmnClCodeManageService cmmnClCodeManageService;

	@Resource(name = "CmmnCodeManageService")
   private CmmnCodeManageService cmmnCodeManageService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

//	@Autowired
//	private DefaultBeanValidator beanValidator;

	@Resource(name = "cmmnDetailCodeManageValidator")
	private CmmnDetailCodeManageValidator validator;
	
	/**
	 * 분기문
	 */
	@RequestMapping(value="index.do")
	public String index(
			@ModelAttribute	CmmnDetailCodeVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCommMode())))searchVO.setCommMode("list");		//기본 manage로 포워딩		
		setIndexProcess(EnumMenuCode.CCM_CDE, request, searchVO.getCommMode());			//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, getPkg()+"index.do");
		StringBuilder sb = new StringBuilder("forward:");
		sb.append(getPkg()).append(searchVO.getCommMode()).append(".do");
		log.debug(sb);
		return sb.toString();
	}
	
	   /**
		 * 공통상세코드 목록을 조회한다.
	     * @param loginVO
	     * @param searchVO
	     * @param model
	     * @return "injnsobang/com/ccm/cde/EgovCcmCmmnDetailCodeList"
	     * @throws Exception
	     */
	    @RequestMapping(value="list.do")
		public String selectCmmnDetailCodeList (@ModelAttribute("searchVO") CmmnDetailCodeVO searchVO
				, ModelMap model
				) throws Exception {
			
	    	if(searchVO.getPageUnit() == 0)searchVO.setPageUnit(getDefaultPageUnit());
			if(searchVO.getPageSize() == 0)searchVO.setPageSize(getDefaultPageSize());

	    	/** pageing */
	    	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());

			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	        List<?> CmmnCodeList = cmmnDetailCodeManageService.selectCmmnDetailCodeList(searchVO);
	        model.addAttribute("resultList", CmmnCodeList);

	        int totCnt = cmmnDetailCodeManageService.selectCmmnDetailCodeListTotCnt(searchVO);
			paginationInfo.setTotalRecordCount(totCnt);
	        model.addAttribute("paginationInfo", paginationInfo);

	        return "injnsobang/com/ccm/cde/EgovCcmCmmnDetailCodeList";
		}
		
		/**
		 * 공통상세코드 상세항목을 조회한다.
		 * @param loginVO
		 * @param cmmnDetailCodeVO
		 * @param model
		 * @return "injnsobang/com/ccm/cde/EgovCcmCmmnDetailCodeDetail"
		 * @throws Exception
		 */
		@RequestMapping(value="view.do")
	 	public String selectCmmnDetailCodeDetail (@ModelAttribute("searchVO") CmmnDetailCodeVO cmmnDetailCodeVO,	ModelMap model
	 			)	throws Exception {
			CmmnDetailCodeVO vo = cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(cmmnDetailCodeVO);
			model.addAttribute("result", vo);

			return "injnsobang/com/ccm/cde/EgovCcmCmmnDetailCodeDetail";
		}

	    
		/**
		 * 공통상세코드 등록을 위한 등록페이지로 이동한다.
		 * 
		 * @param cmmnDetailCodeVO
		 * @param model
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("write.do")
		public String insertCmmnDetailCodeView(@ModelAttribute("cmmnCodeVO") CmmnCodeVO cmmnCodeVO,
				@ModelAttribute("cmmnDetailCodeVO") CmmnDetailCodeVO cmmnDetailCodeVO
				,ModelMap model) throws Exception {
			
			CmmnClCodeVO searchClCodeVO = new CmmnClCodeVO();
            searchClCodeVO.setFirstIndex(0);
	        List<?> CmmnClCodeList = cmmnClCodeManageService.selectCmmnClCodeList(searchClCodeVO);
	        model.addAttribute("clCodeList", CmmnClCodeList);

	    
	        CmmnCodeVO clCode = new CmmnCodeVO(); 
	        clCode.setClCode(cmmnCodeVO.getClCode());
	        
	        if (cmmnCodeVO.getClCode().equals("")) {
	        	
	        }else{
            
            	CmmnCodeVO searchCodeVO = new CmmnCodeVO();
            	searchCodeVO.setRecordCountPerPage(999999);
                searchCodeVO.setFirstIndex(0);
            	searchCodeVO.setSearchCondition("clCode");
                searchCodeVO.setSearchKeyword(cmmnCodeVO.getClCode());
                
    	        List<?> CmmnCodeList = cmmnCodeManageService.selectCmmnCodeList(searchCodeVO);
    	        model.addAttribute("codeList", CmmnCodeList);
	        } 	
	        CmmnDetailCodeVO vo = cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(cmmnDetailCodeVO);
	        model.addAttribute("clCode",cmmnCodeVO.getClCode());
			model.addAttribute("result", vo);
	        
			return "injnsobang/com/ccm/cde/EgovCcmCmmnDetailCodeRegist";
		}
		
		/**
	     * 공통상세코드를 등록한다.
	     * 
	     * @param CmmnDetailCodeVO
	     * @param CmmnDetailCodeVO
	     * @param status
	     * @param model
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping("writeActionJson.do")
	    public String insertCmmnDetailCode(@ModelAttribute("cmmnDetailCodeVO") CmmnDetailCodeVO cmmnDetailCodeVO,
	    		BindingResult errors,
	    		HttpServletRequest request,
	    		ModelMap model) throws Exception {
	    	throwDirect(request);
	    	
	    	
	    	boolean isSuccess = false;
			String msg = "";
			try{
				validator.validate(cmmnDetailCodeVO, errors);
				if(errors.hasErrors())throw new ValidatorException("");
				
				cmmnDetailCodeManageService.upsertCmmnDetailCode(cmmnDetailCodeVO);
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
		 * 공통상세코드를 삭제한다.
		 * @param loginVO
		 * @param cmmnDetailCodeVO
		 * @param model
		 * @return "forward:/injnsobang/ccm/cde/EgovCcmCmmnDetailCodeList.do"
		 * @throws Exception
		 */
	    @RequestMapping(value="deleteActionJson.do")
		public String deleteActionJson (@ModelAttribute("searchVO") CmmnDetailCodeVO cmmnDetailCodeVO,
				HttpServletRequest request,
				ModelMap model
				) throws Exception {
	    	throwDirect(request);
    		
    		boolean isSuccess = false;
    		String msg = "";
    		try{
    			cmmnDetailCodeManageService.deleteCmmnDetailCode(cmmnDetailCodeVO);
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
