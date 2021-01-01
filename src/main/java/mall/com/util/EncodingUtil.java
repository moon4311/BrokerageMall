package injnsobang.com.util;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

/**
 * @Class Name : EncodingUtil.java
 * @Description : 한글 인코딩
 * @Modification Information
 * 
 * @author
 * @since 2017. 11.27
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
public class EncodingUtil {

	/**
	 * 브라우저 가져오기
	 * @param request
	 * @return browser
	 */
	public static String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
        if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        } else if(header.indexOf("rv:11.0") > -1){
        	return "IE11";
        }
        return "Firefox";
    }
	
	/**
	 * 한글 Encoding
	 * @param fileName
	 * @param browser
	 * @exception Exception
	 * @return disposition
	 */
	public static String getDisposition(String filename, String browser)throws Exception {
		String encodedFilename = null;        
        if (browser.equals("MSIE")) {
        	/*
            encodedFilename = URLEncoder.encode(filename, "UTF-8")
                    .replaceAll("\\+", "%20");
            */
        	filename = filename.replaceAll("\r", "").replaceAll("\n", "");
        	
        	filename =  java.net.URLDecoder.decode(filename, "8859_1");
        	filename =  java.net.URLEncoder.encode(filename, "UTF-8");
        	
        	encodedFilename = filename.replaceAll("\\+", " "); 
            
            
        } else if (browser.equals("Firefox")) {
            encodedFilename =
         "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Opera")) {
            encodedFilename =
         "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else if (browser.equals("IE11")) {
        	filename = filename.replaceAll("\r", "").replaceAll("\n", "");
        	
        	filename =  java.net.URLEncoder.encode(filename, "utf-8");
        	
        	encodedFilename = filename.replaceAll("\\+", " "); 
        }else {
            //throw new RuntimeException("Not supported browser");
        	//else가 들어와도 ie 처럼 처리
        	filename = filename.replaceAll("\r", "").replaceAll("\n", "");
        	
        	filename =  java.net.URLDecoder.decode(filename, "8859_1");
        	filename =  java.net.URLEncoder.encode(filename, "UTF-8");
        	
        	encodedFilename = filename.replaceAll("\\+", " "); 
        }
        return encodedFilename;
    }
	
}