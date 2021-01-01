package injnsobang.com.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import erss.com.service.impl.ErssDao;
import erss.com.vo.ErssViewVO;
import hrs.com.service.impl.HrsSmplDao;
import hrs.com.vo.HrsSmplVO;

/**
 *  개발 컨트롤러 클래스
 *  TODO 개발 완료 후 삭제
 * @author 
 * @since 2020.08.24
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
public class ComDevController extends AbstractController{

	protected String getPkg(){return "injnsobang/com/dev";}

	//서비스로 호출해야 되지만 테스트용이므로 dao직접호출
	@Resource(name = "hrsSmplDao")
	private HrsSmplDao hrsSmplDao;
	@Resource(name = "erssDao")
	private ErssDao erssDao;
	
	/**
	 * 개발 공통페이지
	 */
	@RequestMapping(value="/dev/dev.do")
	public String dev(
			HttpServletRequest request,
			ModelMap model) throws Exception{
		
		//상위코드로 공통코드값 가져오기
		model.addAttribute("areaCodeList", getCodeListByUprCd("41001"));//남한지역코드
		
		return "injnsobang/com/dev/dev";
	}

	/**
	 * 개발 공통페이지 팝업샘플
	 */
	@RequestMapping(value="/dev/devPopup.do")
	public String devPopup(
			HttpServletRequest request,
			ModelMap model) throws Exception{
		
		return "injnsobang/com/dev/devPopup";
	}
	


	/**
	 * 개발 다른 DB 접속 테스트
	 */
	@RequestMapping(value="/dev/otherdb.do")
	public String otherdb(
			HttpServletRequest request,
			ModelMap model) throws Exception{
		//서비스로 호출해야 되지만 테스트용이므로 dao직접호출
		model.addAttribute("hrsSmplList", hrsSmplDao.selectHrsSmplList(new HrsSmplVO()));
//		model.addAttribute("erssSmplList", erssSmplDao.selectLatest(new ErssViewVO()));
		
		
		return "injnsobang/com/dev/otherdb";
	}
	
	
}
