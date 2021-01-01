package mall.com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
/**
 * XSS 방어용 필터
 * @author meddogi
 *
 */
public class XSSProtectionFilter implements Filter {
	
	@Override 
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException { 
			HttpServletResponse res = ((HttpServletResponse) servletResponse);
			// 교차 프레임 스크립팅 방어 누락
			res.setHeader("X-Frame-Options", "SAMEORIGIN");
			// HTTP Strict-Transport-Security 헤더 누락
			res.setHeader("Strict-Transport-Security",
				"max-age=31536000; includeSubDomains; preload");
			// Missing "X-Content-Type-Options" header
			res.setHeader("X-Content-Type-Options", "nosniff");
			// Missing "X-XSS-Protection" header
			res.setHeader("X-XSS-Protection", "1");
			filterChain.doFilter(servletRequest, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
