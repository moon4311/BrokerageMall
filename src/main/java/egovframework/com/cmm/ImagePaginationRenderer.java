package egovframework.com.cmm;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
/**
 * ImagePaginationRenderer.java 클래스
 *
 * @author 서준식
 * @since 2011. 9. 16.
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2011. 9. 16.   서준식       이미지 경로에 ContextPath추가
 *   2016. 6. 17.   장동한       표준프레임워크 v3.6 리뉴얼
 *   2020. 8. 25.	김문기	디자인적용
 * </pre>
 */
public class ImagePaginationRenderer extends AbstractPaginationRenderer implements ServletContextAware{

	private ServletContext servletContext;

	public ImagePaginationRenderer() {

	}

	public void initVariables(){

		firstPageLabel    = "<li><a href=\"?pageIndex={1}\" onclick=\"{0}({1});return false; \" class=\"btn_first\">첫 페이지</a></li>";
		previousPageLabel = "<li><a href=\"?pageIndex={1}\" onclick=\"{0}({1});return false; \" class=\"btn_prev\">이전 페이지</a></li>";
		
        currentPageLabel  = "<li><a onClick=\"return false;\" class=\"active\">{0}</a></li>";
        otherPageLabel    = "<li><a href=\"?pageIndex={1}\" onclick=\"{0}({1});return false; \">{2}</a></li>";
        
        nextPageLabel    = "<li><a href=\"?pageIndex={1}\" onclick=\"{0}({1});return false; \" class=\"btn_next\">다음 페이지</a></li>";
        lastPageLabel    = "<li><a href=\"?pageIndex={1}\" onclick=\"{0}({1});return false; \" class=\"btn_last\">끝 페이지</a></li>";

	}



	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		initVariables();
	}

}
