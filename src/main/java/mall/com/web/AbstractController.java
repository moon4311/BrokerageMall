/**
 * @version 3.2.0.1
 */
package mall.com.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonProcessingException;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import mall.admin.system.code.cde.service.CmmnDetailCodeManageService;
import mall.admin.system.code.cde.service.CmmnDetailCodeVO;
import mall.com.enums.EnumGrpCd;
import mall.com.enums.EnumMenuCode;
import mall.com.enums.EnumModeType;
import mall.com.exception.MenuAuthRequiredException;
import mall.com.exception.MenuAuthRequiredJsonException;
import mall.com.exception.SessionRequiredException;
import mall.com.exception.SessionRequiredJsonException;
import mall.com.exception.ValidationException.ERROR_TYPE;
import mall.com.service.ComAuthGrpService;
import mall.com.service.ComIndvlzService;
import mall.com.service.ComMbrLogService;
import mall.com.service.ComMbrService;
import mall.com.service.ComService;
import mall.com.util.JsonUtil;
import mall.com.util.NullUtil;
import mall.com.util.SessionUtil;
import mall.com.vo.AbstractVO;
import mall.com.vo.ComAuthGrpVO;
import mall.com.vo.ComIndvlzVO;
import mall.com.vo.ComLoginMenuVO;
import mall.com.vo.ComMbrLogVO;
import mall.com.vo.ComMbrVO;
import mall.com.vo.ErrorVO;
import net.sf.json.JSONObject;

/**
 *  기본 Abstract 컨트롤러 클래스
 * @author 
 * @since 2020.07.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2020.07.21            최초 생성
 *
 * </pre>
 */

public abstract class AbstractController {
	
	protected final Log log = LogFactory.getLog(getClass());

	protected abstract String getPkg();
	
	@Resource(name="egovMessageSource")
    public EgovMessageSource egovMessageSource;
	
	@Resource(name="comService")
	protected ComService comService;
	
	@Resource(name="comIndvlzService")
	private ComIndvlzService comIndvlzService;
	
	@Resource(name = "comAuthGrpService")
	private ComAuthGrpService comAuthGrpService;
	
	@Resource(name="comMbrLogService")
	private ComMbrLogService comMbrLogService;

	@Resource(name = "CmmnDetailCodeManageService")
	private CmmnDetailCodeManageService cmmnDetailCodeManageService;
	
	
	
	public static final String REQUEST_IS_ADMIN = "isAdmin";//관리자여부 reqeust셋팅변수명
	
	public static final String REQUEST_IS_STRE_AUTH = "isStreAuth";//저장권한
	public static final String REQUEST_IS_REDNG_AUTH = "isRedngAuth";//읽기권한
	public static final String REQUEST_IS_DEL_AUTH = "isDelAuth";//삭제권한
	public static final String REQUEST_IS_PRNTG_AUTH = "isPrntgAuth";//프린트권한
	
	public static final String REQUEST_ACTION_URL = "actionUrl";//분기URL request셋팅변수명
	
	public static final String REQUEST_EXCEL_YN = "excelYn";//엑셀 처리용 변수값

	/**
	 * 로그인 정보 리턴.
	 * @return JnitcmsmbrVO
	 */
	protected ComMbrVO getLoginVO(){
		return comService.getLoginVO();
	}
	
	/**
	 * 로그인한 아이디가져오기
	 * @return 아이디
	 */
	protected String getLoginId(){
		return comService.getLoginID();
	}
	/**
	 * 로그인한 그룹ID 가져오기
	 * @return 그룹ID
	 */
	protected String getLoginGroupID(){
		return comService.getLoginGroupID();
	}
	/**
	 * 로그인되었는지 체크
	 * @return true|false
	 */
	protected boolean isLoginned(){
		return comService.isLogin();
	}
	
	/**
	 * 관리자인지 체크
	 * @return
	 */
	protected boolean isAdmin(){
		return comService.isAdmin();
	}
	
	/**
	 * json형태 View(성공,실패 응답)
	 * @param model
	 * @param isSuccess (true|false - 성공|실패)
	 * @param msg 메세지
	 * @return
	 */
	protected String getResponseJsonView(ModelMap model,boolean isSuccess,String msg){
		JSONObject json = new JSONObject();
		json.put("isSuccess", isSuccess);
		json.put("msg", msg);
		
		model.addAttribute("json",json.toString());
		
		return "injnsobang/com/json";
	}

	/**
	 * json형태 View(성공,실패 응답)
	 * @param model
	 * @param isSuccess (true|false - 성공|실패)
	 * @param msg 메세지
	 * @param data 응답 object
	 * @return
	 */
	protected String getResponseJsonView(ModelMap model,boolean isSuccess,String msg, Object data){
		JSONObject json = new JSONObject();
		json.put("isSuccess", isSuccess);
		json.put("msg", msg);
		json.put("data", data);
		
		model.addAttribute("json",json.toString());
		
		return "injnsobang/com/json";
	}

	/**
	 * json형태 View(성공,실패 응답)
	 * @param model
	 * @param isSuccess (true|false - 성공|실패)
	 * @param msg 메세지
	 * @param validation error가 발생한 속성
	 * @param validation error type
	 * @return
	 */
	protected String getResponseJsonView(ModelMap model,boolean isSuccess,String msg, String errorKey, ERROR_TYPE errorType){
		JSONObject json = new JSONObject();
		
		json.put("isSuccess", isSuccess);
		json.put("msg", msg);
		json.put("errorKey", errorKey);
		json.put("errorType", errorType);
		
		model.addAttribute("json",json.toString());
		
		return "injnsobang/com/json";
	}

	protected String getResponseValidatorErrorJsonView(ModelMap model, BindingResult errors)throws JsonProcessingException{
		
		JSONObject json = new JSONObject();	
		json.put("isSuccess", false);
		
		List<ErrorVO> errorList = new ArrayList<ErrorVO>();
		for (final FieldError error : errors.getFieldErrors()) {
			ErrorVO m = new ErrorVO();
			m.setErrorField(error.getField());
			m.setErrorCode(error.getCode());
			m.setErrorMessage(error.getDefaultMessage());
			errorList.add(m);
		}
		//for reject value
		for (final ObjectError error:errors.getAllErrors()) {
		   boolean isExistError = false;
		   for(ErrorVO ae:errorList){
		      if(ae.getErrorCode().equals(error.getCode())){
		         isExistError = true;
		         break;
		      }
		   }
		   if(!isExistError){
		  	 ErrorVO m = new ErrorVO();
		      m.setErrorField(error.getCode());
		      m.setErrorCode(error.getCode());
		      m.setErrorMessage(error.getDefaultMessage());
		      errorList.add(m);
		   }
		}
		json.put("errorList", errorList);
		model.addAttribute("json",json.toString());
		
		return "injnsobang/com/json";
	}
	/**
	 * json형태 View(return object 출력)
	 * @param model
	 * @return
	 */
	protected String getResponseJsonView(ModelMap model, Object returnObj)throws JsonProcessingException{
		
//		ObjectMapper objectMapper = new ObjectMapper();
//		model.addAttribute("json",objectMapper.writeValueAsString(returnObj).toString());
		model.addAttribute("json", JsonUtil.convertObjectToJson(returnObj));
		return "injnsobang/com/json";
	}

	/**
	 * excel View
	 * @param model
	 * @param excelTemplateName /WEB-INF/jsp/injnsobang/exceltemplate/에 있는 엑셀 파일명.(xlsx 제외)
	 * @param excelStoreName 저장될 엑셀 명
	 * @return
	 */
	protected String getResponseExcelView(ModelMap model, String excelTemplateName, String excelStoreName){

		model.addAttribute("sdfParse", new SimpleDateFormat("yyyyMMdd"));	//String to Date
		model.addAttribute("sdf", new SimpleDateFormat("YYYY.MM.dd"));//날짜 변환용
		model.addAttribute("sdfAll", new SimpleDateFormat("YYYY.MM.dd E HH:mm:ss"));//날짜 변환용
		model.addAttribute("excelTemplateName", excelTemplateName);
		model.addAttribute("excelStoreName", excelStoreName);
		
		return "excelView";
	}
	/**
	 * 분기에서각 메소드 직접호출을 막기위한 셋팅.
	 * @param request
	 */
//	protected void setDirectPkg(HttpServletRequest request){
//		request.setAttribute(getPkg(), getPkg());
//	}
	/**
	 * 각 메소드 직접접근 에러.
	 */
	protected void throwDirect(HttpServletRequest request)throws EgovBizException{
		if(request.getAttribute(getPkg()) == null)throwBizException("com.error.nodirect");
	}

	/**
	 * 분기에서 공통으로 처리할 내용
	 * @param menuCode
	 * @param request
	 * @param mode
	 */
	@SuppressWarnings("unchecked")
	protected void setIndexProcess(EnumMenuCode menuCode, HttpServletRequest request, String mode)
			throws SessionRequiredException, SessionRequiredJsonException, MenuAuthRequiredJsonException,MenuAuthRequiredException{
		//메뉴Id 셋팅
		request.setAttribute("curMenuCode", menuCode.toString());
		request.setAttribute("curMenuSno", menuCode.getMenuSno());
		//SSO사용여부
		//request.setAttribute("useSSO",Globals.SSO_USE);
		
		ComMbrVO loginVO = getLoginVO();
		request.setAttribute("loginVO", loginVO);
		
		//로그인 체크
		String loginId = getLoginId();
		request.setAttribute("loginId", loginId);
		if("".equals(loginId)){
			if(NullUtil.nullString(mode).indexOf("Json") >= 0){
				throw new SessionRequiredJsonException("");
			}else{
				SessionUtil.setAttribute("urlAfterLogin", request.getRequestURL().toString());
				throw new SessionRequiredException("");
			}
		}
		//메소드 직접접근 방지
		request.setAttribute(getPkg(), getPkg());
		

		request.setAttribute("isAdmin", (isAdmin()));				
		
		if(EnumMenuCode.NONE != menuCode){

			//해당 메소드만 로그 남기기
			EnumModeType modeType = null;
			try{
				modeType = EnumModeType.valueOf(mode);
			}catch(Exception e){
				log.debug("NONE MODE TYPE : " + mode);
			}
			
			String curLmenuSno = "";
			String curLmenuNm = "";
			String curLmenuUrl = "";
			String curLuprMenuSno = "";
			String curMmenuSno = "";
			String curMmenuNm = "";
			String curMmenuUrl = "";
			String curMuprMenuSno = "";
			String curSmenuSno = "";
			String curSmenuNm = "";
			String curSmenuUrl = "";
			String curSuprMenuSno = "";
			String curTmenuSno = "";
			String curTmenuNm = "";
			String curTmenuUrl = "";
			String curTuprMenuSno = "";
			
			boolean isStreAuth = false;
			boolean isRedngAuth = false;
			boolean isDelAuth = false;
			boolean isPrntgAuth = false;
			
			List<ComLoginMenuVO> lmenuList = (List<ComLoginMenuVO>)SessionUtil.getAttribute(ComMbrService.SESSION_LMENU_LIST);
			List<ComLoginMenuVO> mmenuList = (List<ComLoginMenuVO>)SessionUtil.getAttribute(ComMbrService.SESSION_MMENU_LIST);
			List<ComLoginMenuVO> smenuList = (List<ComLoginMenuVO>)SessionUtil.getAttribute(ComMbrService.SESSION_SMENU_LIST);
			List<ComLoginMenuVO> tmenuList = (List<ComLoginMenuVO>)SessionUtil.getAttribute(ComMbrService.SESSION_TMENU_LIST);
			for(ComLoginMenuVO tmenu:tmenuList){
				if(tmenu.getMenuSno().equals(menuCode.getMenuSno())){
					curTmenuSno = tmenu.getMenuSno();
					curTmenuNm = tmenu.getMenuNm();
					curTmenuUrl = tmenu.getMenuUrl();
					curTuprMenuSno = tmenu.getUprMenuSno();
					
					isStreAuth = tmenu.isStreAuth();
					isRedngAuth = tmenu.isRedngAuth();
					isDelAuth = tmenu.isDelAuth();
					isPrntgAuth = tmenu.isPrntgAuth();
					break;
				}
			}
			for(ComLoginMenuVO smenu:smenuList){
				if(smenu.getMenuSno().equals(menuCode.getMenuSno()) || smenu.getMenuSno().equals(curTuprMenuSno)){
					curSmenuSno = smenu.getMenuSno();
					curSmenuNm = smenu.getMenuNm();
					curSmenuUrl = smenu.getMenuUrl();
					curSuprMenuSno = smenu.getUprMenuSno();

					if(smenu.getMenuSno().equals(menuCode.getMenuSno())){
						isStreAuth = smenu.isStreAuth();
						isRedngAuth = smenu.isRedngAuth();
						isDelAuth = smenu.isDelAuth();
						isPrntgAuth = smenu.isPrntgAuth();
					}
					break;
				}
			}
			for(ComLoginMenuVO mmenu:mmenuList){
				if(mmenu.getMenuSno().equals(menuCode.getMenuSno()) || mmenu.getMenuSno().equals(curSuprMenuSno)){
					curMmenuSno = mmenu.getMenuSno();
					curMmenuNm = mmenu.getMenuNm();
					curMmenuUrl = mmenu.getMenuUrl();
					curMuprMenuSno = mmenu.getUprMenuSno();
					if(mmenu.getMenuSno().equals(menuCode.getMenuSno())){
						isStreAuth = mmenu.isStreAuth();
						isRedngAuth = mmenu.isRedngAuth();
						isDelAuth = mmenu.isDelAuth();
						isPrntgAuth = mmenu.isPrntgAuth();
					}
					break;
				}
			}
			for(ComLoginMenuVO lmenu:lmenuList){
				if(lmenu.getMenuSno().equals(menuCode.getMenuSno()) || lmenu.getMenuSno().equals(curMuprMenuSno)){
					curLmenuSno = lmenu.getMenuSno();
					curLmenuNm = lmenu.getMenuNm();
					curLmenuUrl = lmenu.getMenuUrl();
					curLuprMenuSno = lmenu.getUprMenuSno();
					if(lmenu.getMenuSno().equals(menuCode.getMenuSno())){
						isStreAuth = lmenu.isStreAuth();
						isRedngAuth = lmenu.isRedngAuth();
						isDelAuth = lmenu.isDelAuth();
						isPrntgAuth = lmenu.isPrntgAuth();
					}
					break;
				}
			}

			
			//TODO:샘플용 추 후 삭제
			if(menuCode == EnumMenuCode.COM_SMPL) {
				isStreAuth = true;
				isRedngAuth = true;
				isDelAuth = true;
				isPrntgAuth = true;
			}
			//TODO:샘플용 추 후 삭제 끝
			
			
			if(menuCode == EnumMenuCode.MAIN) {
				isStreAuth = true;
				isRedngAuth = true;
				isDelAuth = true;
				isPrntgAuth = true;
			}
			
			//권한에 체크에 따른 exception
			if(modeType != null){
				boolean isAuth = false;
				
				if((EnumModeType.list == modeType || EnumModeType.view == modeType) && isRedngAuth)isAuth = true;
				if((EnumModeType.write == modeType || EnumModeType.writeActionJson == modeType)&& isStreAuth)isAuth = true;
				if((EnumModeType.deleteActionJson == modeType)&& isDelAuth)isAuth = true;
				
				if(!isAuth){
					if(NullUtil.nullString(mode).indexOf("Json") >= 0){
						throw new MenuAuthRequiredJsonException("No Auth");
					}else{
						throw new MenuAuthRequiredException("No Auth");
					}
				}
			}

			request.setAttribute("curLmenuSno",curLmenuSno);
			request.setAttribute("curLmenuNm",curLmenuNm);
			request.setAttribute("curLmenuUrl",curLmenuUrl);
			request.setAttribute("curLuprMenuSno",curLuprMenuSno);
			request.setAttribute("curMmenuSno",curMmenuSno);
			request.setAttribute("curMmenuNm",curMmenuNm);
			request.setAttribute("curMmenuUrl",curMmenuUrl);
			request.setAttribute("curMuprMenuSno",curMuprMenuSno);
			request.setAttribute("curSmenuSno",curSmenuSno);
			request.setAttribute("curSmenuNm",curSmenuNm);
			request.setAttribute("curSmenuUrl",curSmenuUrl);
			request.setAttribute("curSuprMenuSno",curSuprMenuSno);
			request.setAttribute("curTmenuSno",curTmenuSno);
			request.setAttribute("curTmenuNm",curTmenuNm);
			request.setAttribute("curTmenuUrl",curTmenuUrl);
			request.setAttribute("curTuprMenuSno",curTuprMenuSno);
			
			request.setAttribute(REQUEST_IS_STRE_AUTH,isStreAuth);
			request.setAttribute(REQUEST_IS_REDNG_AUTH,isRedngAuth);
			request.setAttribute(REQUEST_IS_DEL_AUTH,isDelAuth);
			request.setAttribute(REQUEST_IS_PRNTG_AUTH,isPrntgAuth);
			
			//로그 남기기
			try{
				if(modeType != null){
					ComMbrLogVO logVO = new ComMbrLogVO();
					logVO.setMbrId(loginVO.getMbrId());
					logVO.setLogCnts(modeType.getName());
					logVO.setMenuSno(menuCode.getMenuSno());
					comMbrLogService.insertComMbrLog(logVO);
					request.setAttribute(ComService.REQUEST_LOG_CNT_NO, logVO.getMbrLogMngmNo());
				}
			}catch(Exception e){
				log.error(e.getMessage());
			}
			
		}
		//전화번호앞자리
		request.setAttribute("frontOfTel", getFrontOfTel());
		//핸드폰번호앞자리
		request.setAttribute("frontOfPhone", getFrontOfPhone());
		
	}
	
	/**
	 * 로그 내용 없데이트
	 * @param logCnts
	 */
	protected void updateLogContent(String logCnts) {
		comService.updateCurrentLog(logCnts);
	}
	
	/**
	 * 각 리스트용 기본 권한 셋팅
	 * @param searchVO
	 */
	protected void setListDefaultCondition(AbstractVO searchVO){
		if(isAdmin())return;
	}
	
	/**
	 * 비지니스 로직에러 발생(꽁통)
	 * @param msg
	 * @throws BizException
	 */
	protected void throwBizException(final String msgKey)throws EgovBizException{
		throwBizException(msgKey,null);
	}
	protected EgovBizException throwBizException(final String msgKey,String[] args)throws EgovBizException{
		throw comService.processException(msgKey,args);
	}

	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService; 
	
	//공통 페이지 크기
	protected int getDefaultPageUnit(){
		return propertiesService.getInt("pageUnit");
	}
	protected int getDefaultPageSize(){
		return propertiesService.getInt("pageSize");
	}
	//공통 팝업 페이지 크기
	protected int getDefaultPopupPageUnit(){
		return 6;
	}
	
	//최대년도 부터 최소년도까지 list형태로 리턴
	protected List<Integer> getYearList(){
		List<Integer> ret = new ArrayList<Integer>();
		for(int i = getMaxYear(); i >= getMinYear(); i--){
			ret.add(i);
		}
		return ret;
	}
	//년도 출력시 최소 년도값
	protected int getMinYear(){
		return 2018;
	}
	//년도 출력시 최대 년도값
	protected int getMaxYear(){
		return getCurrentYear();
	}
	//현재년도
	protected int getCurrentYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	
	/**
	 * 전체 공통코드 리스트 가져오기
	 * @return
	 */
	protected List<ComIndvlzVO> getAllIndvlz() throws Exception{
		return comIndvlzService.getAllIndvlz();
	}
	
	
	/**
	 * 그룹코드로 공통코드 리스트 가져오기
	 * @param grpCd
	 * @return
	 */
	protected List<ComIndvlzVO> getCodeListByGrpCd(EnumGrpCd grpCd) throws Exception{
		return comIndvlzService.getIndvlzByGrpCd(grpCd);
	}

	/**
	 * 그룹코드 및 상위코드로 공통코드 리스트 가져오기
	 * @param grpCd
	 * @return
	 */
	protected List<ComIndvlzVO> getCodeListByGrpCd(EnumGrpCd grpCd, String uprCd) throws Exception{
		return comIndvlzService.getIndvlzByGrpCd(grpCd, uprCd);
	}
	/**
	 * 상위코드로 공통코드 리스트 가져오기
	 * @param grpCd
	 * @return
	 */
	protected List<ComIndvlzVO> getCodeListByUprCd(String uprCd) throws Exception{
		return comIndvlzService.getIndvlzByUprCd(uprCd);
	}

	/**
	 * 그룹 전체 리스트 가져오기
	 * @return
	 * @throws Exception
	 */
	protected List<ComAuthGrpVO> getAuthGrpList() throws Exception{
		ComAuthGrpVO groupVO = new ComAuthGrpVO();
		groupVO.setRecordCountPerPage(0);
		return comAuthGrpService.selectComAuthGrpList(groupVO);
	}


	/**
	 * 기타로그 남기기
	 * @param request
	 * @param act
	 */
	protected void insertComMbrLogEtc(HttpServletRequest request, String act){
		comMbrLogService.insertComMbrLogEtc(request, act);
	}

	/**
	 * 연락처 앞자리 배열가져오기
	 * @return
	 */
	protected String[] getFrontOfTel(){
		return "02,031,032,033,041,042,043,044,051,052,053,054,055,061,062,063,064,070".split(",");
	}

	/**
	 * 핸드폰 앞자리 배열가져오기
	 * @return
	 */
	protected String[] getFrontOfPhone(){
		return "010,011,016,017,018,019".split(",");
	}
	
	
	
	protected List<CmmnDetailCodeVO> getCDCList(String codeId){
		try {
			return cmmUseService.selectCmmCodeDetail(codeId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
