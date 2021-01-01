package injnsobang.com.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Cookie secure, httponly 필터
 * @author meddogi
 *
 */
public class CookieFilter implements Filter {
	
	@Override 
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException { 
			HttpServletResponse res = ((HttpServletResponse) servletResponse);
	        HttpServletRequest req = (HttpServletRequest) servletRequest;  
	        Cookie[] cookies = req.getCookies();  
	  
	        if (cookies != null) {  
	                Cookie cookie = cookies[0];  
	                if (cookie != null) {  
	                      
	                    //Servlet 2.5
	                    String value = cookie.getValue();  
	                    StringBuilder builder = new StringBuilder();  
	                    builder.append("JSESSIONID=" + value + "; ");  
	                    builder.append("Secure; ");  
	                    builder.append("HttpOnly; ");  
	                    Calendar cal = Calendar.getInstance();  
	                    cal.add(Calendar.HOUR, 1);  
	                    Date date = cal.getTime();    
	                    SimpleDateFormat sdf =   
	                            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
	                    builder.append("Expires=" + sdf.format(date));  
	                    res.setHeader("Set-Cookie", builder.toString());  

	                }  
	        }  
			filterChain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
