package mall.admin.system.mbr.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import mall.com.enums.EnumMenuCode;
import mall.com.service.ComAuthGrpService;
import mall.com.service.ComMbrLogService;
import mall.com.service.ComMenuService;
import mall.com.util.NullUtil;
import mall.com.vo.ComMbrLogVO;
import mall.com.vo.ComMenuVO;
import mall.com.web.AbstractController;


/**
 * @Class Name : ComMbrLogController.java
 * @Description : 접속기록로그 Controller
 * @Modification Information
 * 
 * @author
 * @since 2020.07.21
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Controller
public class ComMbrLogController extends AbstractController{

	protected String getPkg(){return "injnsobang/com/log";}
	
	@Resource(name = "comMenuService")
	private ComMenuService comMenuService;
	
	@Resource(name = "comMbrLogService")
	private ComMbrLogService comMbrLogService;
	
	@Resource(name = "comAuthGrpService")
	private ComAuthGrpService comAuthGrpService;
	
	/**
	 * 분기문
	 */
	@RequestMapping(value="/injnsobang/com/mbrLog/index.do")
	public String index(
			@ModelAttribute	ComMbrLogVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{

		//공통 처리부		
		if("".equals(NullUtil.nullString(searchVO.getCmlMode())))searchVO.setCmlMode("list");//기본 list로 포워딩		
//		setIndexProcess(EnumMenuCode.COM_SYS_MBR_LOG, request, searchVO.getCmlMode());//분기공통처리
		request.setAttribute(REQUEST_ACTION_URL, "/injnsobang/com/mbrLog/index.do");
		
		StringBuilder sb = new StringBuilder("forward:");
		sb.append("/injnsobang/com/mbrLog/" + searchVO.getCmlMode() + ".do");
		
		return sb.toString();
	}



	/**
	 * 리스트
	 */
	@RequestMapping(value="/injnsobang/com/mbrLog/list.do")
	public String list(
			@ModelAttribute("searchVO") ComMbrLogVO searchVO,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		throwDirect(request);
		
		//메뉴sno검색용
		searchVO.setSearchMenuSno(searchVO.getUprMenuSno());
		if(!NullUtil.nullString(searchVO.getMenuSno()).equals("")) {//하위메뉴를 선택했을때는 하위메뉴로만 검색.
			searchVO.setSearchMenuSno(searchVO.getMenuSno());
		}
		
		//그룹리스트
//		ComAuthGrpVO groupVO = new ComAuthGrpVO();
//		groupVO.setRecordCountPerPage(0);
//		model.addAttribute("groupList",comAuthGrpService.selectComAuthGrpList(groupVO));

		//메뉴리스트
		ComMenuVO menuVO = new ComMenuVO();
		menuVO.setRecordCountPerPage(0);
		menuVO.setMenuLvlVl("1");
		model.addAttribute("menuList",comMenuService.selectMenuList(menuVO));
		if("Y".equals(request.getParameter(REQUEST_EXCEL_YN))){
			
			searchVO.setRecordCountPerPage(0);
			List<ComMbrLogVO> resultList = comMbrLogService.selectComMbrLogList(searchVO);
			//엑셀에서 데이터처리를 위한 변환
			if(resultList != null) {
			}
			model.addAttribute("resultList", resultList);
			return getResponseExcelView(model, "mbrLog", "시스템사용이력");
			//데이터가 많으므로 jsp로
//			return "injnsobang/com/mbrLog/listExcel";
		}else{

			//기본값으로 스프링빈에 설정된 값 로드
			if(searchVO.getPageUnit() == 0)searchVO.setPageUnit(getDefaultPageUnit());
			if(searchVO.getPageSize() == 0)searchVO.setPageSize(getDefaultPageSize());
		
			//총갯수
			int totalRecordCount = comMbrLogService.selectComMbrLogTot(searchVO);

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
			model.addAttribute("resultList",comMbrLogService.selectComMbrLogList(searchVO));
			
		}
		
		
		return "injnsobang/com/mbrLog/list";
	}
}
