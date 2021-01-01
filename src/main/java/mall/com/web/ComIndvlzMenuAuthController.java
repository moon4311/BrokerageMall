package injnsobang.com.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.enums.EnumMenuCode;
import injnsobang.com.exception.ValidatorException;
import injnsobang.com.service.ComAuthGrpService;
import injnsobang.com.service.ComIndvlzMenuAuthService;
import injnsobang.com.service.ComMbrService;
import injnsobang.com.service.ComMenuService;
import injnsobang.com.util.NullUtil;
import injnsobang.com.validator.ComIndvlzMenuAuthValidator;
import injnsobang.com.vo.ComAuthGrpVO;
import injnsobang.com.vo.ComIndvlzMenuAuthVO;
import injnsobang.com.vo.ComMbrVO;
import injnsobang.com.vo.ComMenuVO;


/**
 * @Class Name : ComIndvlzMenuAuthController.java
 * @Description : 사용자별메뉴관리 Controller
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Controller
public class ComIndvlzMenuAuthController extends AbstractController{

	protected String getPkg(){return "injnsobang/com/indvlzMenuAuth";}
	
	@Resource(name = "comMenuService")
	private ComMenuService comMenuService;
	
	@Resource(name = "comIndvlzMenuAuthService")
	private ComIndvlzMenuAuthService comIndvlzMenuAuthService;
	
	@Resource(name = "comIndvlzMenuAuthValidator")
	private ComIndvlzMenuAuthValidator comIndvlzMenuAuthValidator;
	
	@Resource(name = "comMbrService")
	private ComMbrService comMbrService;
	
	@Resource(name = "comAuthGrpService")
	private ComAuthGrpService comAuthGrpService;
	
	/**
	 * 분기문
	 */
	@RequestMapping(value="/injnsobang/com/indvlzMenuAuth/index.do")
	public String indexLarge(
			@ModelAttribute	ComIndvlzMenuAuthVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCmumaMode())))searchVO.setCmumaMode("list");//기본 list로 포워딩		
		setIndexProcess(EnumMenuCode.COM_USER_MENU_AUTH, request, searchVO.getCmumaMode());//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, "/injnsobang/com/indvlzMenuAuth/index.do");
		
		StringBuilder sb = new StringBuilder("forward:");
		sb.append("/injnsobang/com/indvlzMenuAuth/" + searchVO.getCmumaMode() + ".do");
		
		return sb.toString();
	}



	/**
	 * 리스트
	 */
	@RequestMapping(value="/injnsobang/com/indvlzMenuAuth/list.do")
	public String list(
			@ModelAttribute("searchVO") ComIndvlzMenuAuthVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);

		//그룹리스트
		ComAuthGrpVO groupVO = new ComAuthGrpVO();
		groupVO.setRecordCountPerPage(0);
		groupVO.setSearchCondition("1");
		model.addAttribute("groupList",comAuthGrpService.selectComAuthGrpList(groupVO));
		
		//사용자리스트
		ComMbrVO userVO = new ComMbrVO();
		userVO.setRecordCountPerPage(0);
		userVO.setSearchCondition("1");
		userVO.setSearchKeyword(request.getParameter("searchKeyword"));
		model.addAttribute("userList",comMbrService.selectComMbrList(userVO));
		
		ComMenuVO menuVO = new ComMenuVO();
		menuVO.setRecordCountPerPage(0);
		model.addAttribute("menuList",comMenuService.selectMenuList(menuVO));
		
		
		return "injnsobang/com/indvlzMenuAuth/list";
	}

	/**
	 * 뷰 Json
	 */
	@RequestMapping(value="/injnsobang/com/indvlzMenuAuth/viewJson.do")
	public String viewJson(
			ComIndvlzMenuAuthVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);

		if("".equals(NullUtil.nullString(searchVO.getMbrId()))){
			searchVO.setMbrId("===========");
		}
		searchVO.setRecordCountPerPage(0);
		return getResponseJsonView(model, comIndvlzMenuAuthService.selectIndvlzMenuAuthList(searchVO));
	}


	/**
	 * 삭제
	 */
	@RequestMapping(value="/injnsobang/com/indvlzMenuAuth/deleteActionJson.do")
	public String deleteActionJson(
			ComIndvlzMenuAuthVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		boolean isSuccess = false;
		String msg = "";
		try{
			
			comIndvlzMenuAuthService.deleteIndvlzMenuAuth(searchVO);
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
	@RequestMapping(value="/injnsobang/com/indvlzMenuAuth/write.do")
	public String write(
			ComIndvlzMenuAuthVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		if(!"".equals(NullUtil.nullString(searchVO.getMbrId()))){
			ComIndvlzMenuAuthVO result = comIndvlzMenuAuthService.selectIndvlzMenuAuth(searchVO);
			model.addAttribute("result",result);
		}
		
		return "injnsobang/com/indvlzMenuAuth/write";
	}
	

	/**
	 * 추가 / 수정 처리
	 */
	@RequestMapping(value="/injnsobang/com/indvlzMenuAuth/writeActionJson.do")
	public String writeActionJson(
			ComIndvlzMenuAuthVO searchVO,
			BindingResult errors,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);		
		

		boolean isSuccess = false;
		String msg = "";
		try{
			comIndvlzMenuAuthValidator.validate(searchVO, errors);
			if(errors.hasErrors())throw new ValidatorException("");
						

			List<ComIndvlzMenuAuthVO> authList = new ArrayList<ComIndvlzMenuAuthVO>();
			
			ComMenuVO menuVO = new ComMenuVO();
			menuVO.setRecordCountPerPage(0);
			List<ComMenuVO> menuList = comMenuService.selectMenuList(menuVO);
			if(menuList != null){
				for(ComMenuVO menu:menuList){
					String pre = menu.getMenuSno() + "_";
					String cmumaStoreAuth = NullUtil.nullString(request.getParameter(pre+"cmumaStrore"));
					String cmumaReadAuth = NullUtil.nullString(request.getParameter(pre+"cmumaRead"));
					String cmumaDeleteAuth = NullUtil.nullString(request.getParameter(pre+"cmumaDelete"));
					String cmumaPrintAuth = NullUtil.nullString(request.getParameter(pre+"cmumaPrint"));
					if(!"".equals(cmumaStoreAuth) || !"".equals(cmumaReadAuth) || !"".equals(cmumaDeleteAuth) || !"".equals(cmumaPrintAuth)){
						ComIndvlzMenuAuthVO authVO = new ComIndvlzMenuAuthVO();
						authVO.setMenuSno(menu.getMenuSno());
						authVO.setStreAuthYn(cmumaStoreAuth);
						authVO.setRedngAuthYn(cmumaReadAuth);
						authVO.setDelAuthYn(cmumaDeleteAuth);
						authVO.setPrntgAuthYn(cmumaPrintAuth);
						authList.add(authVO);
					}
				}
			}
			searchVO.setAuthList(authList);
			
			comIndvlzMenuAuthService.writeIndvlzMenuAuth(searchVO);
			
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
