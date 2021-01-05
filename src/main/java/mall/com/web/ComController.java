package mall.com.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mall.com.enums.EnumGrpCd;
import mall.com.service.ComCacheService;
import mall.com.util.JsonUtil;
import mall.com.vo.ComIndvlzVO;

/**
 *  공통 컨트롤러 클래스
 * @author 
 * @since 2017.11.24
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2020.07.28            최초 생성
 *
 * </pre>
 */
@Controller
public class ComController extends AbstractController{

	protected String getPkg(){return "injnsobang/com";}

	@Resource(name = "comCacheService")
	private ComCacheService comCacheService;

	
	/**
	 * 주소찾기
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/injnsobang/com/addressFind.do")
	public String newaddrJibun(HttpServletRequest request) throws Exception{
		return "injnsobang/com/addressFind/jusoPopup";
	}
	

	/**
	 * 그룹코드로 공통코드 가져오기
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/injnsobang/com/getCodeListByGrpCdJson.do")
	public String getCodeListGrpCdJson(
			@RequestParam(required = true)String grpCd,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		List<ComIndvlzVO> resultList = null;
		try {
			EnumGrpCd cd = null;
			for(EnumGrpCd enumGrpCd: EnumGrpCd.values()) {
				if(enumGrpCd.getCode().equals(grpCd))cd = enumGrpCd;
			}
			resultList = getCodeListByGrpCd(cd);
		}catch(IllegalArgumentException e) {
			log.debug(e.getMessage());
		}
		return getResponseJsonView(model, resultList);
	}

	/**
	 * 상위코드로 공통코드 가져오기
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/injnsobang/com/getCodeListByUprCdJson.do")
	public String getCodeListByUprJson(
			@RequestParam(required = true)String uprCd,
			HttpServletRequest request,
			ModelMap model) throws Exception{
		List<ComIndvlzVO> resultList = null;
		try {/*
			EnumUprIndvlzCd cd = null;
			for(EnumUprIndvlzCd enumUprCd: EnumUprIndvlzCd.values()) {
				if(enumUprCd.getCode().equals(uprCd))cd = enumUprCd;
			}
			*/
			//코드가 많아서 String으로 변경.
			resultList = getCodeListByUprCd(uprCd);
		}catch(IllegalArgumentException e) {
			log.debug(e.getMessage());
		}
		return getResponseJsonView(model, resultList);
	}
	

	/**
	 * 자바스크립트용 코드값 정의
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/injnsobang/com/codeListForJavascript.do")
	public String codeListForJavascript(
			HttpServletRequest request,
			ModelMap model) throws Exception{
		
		model.addAttribute("resultList",JsonUtil.convertObjectToJson(comCacheService.getAllIndvlzList()));
		return "injnsobang/com/codeListForJavascript";
	}
}
