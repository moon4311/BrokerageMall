/**
 * @version 3.2.0.1
 */
package mall.com.tag;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import mall.com.util.StringUtil;
import mall.com.util.UrlUtil;
import mall.com.util.WebUtil;

public class JnitTag{

    public JnitTag()
    {
    }
    
    /**
     * xss 방지 처리
     * @param html
     * @return
     */
    public static String unscript(String html){
    	return WebUtil.unscript(html);
    }
    
    /**
     * 태그 없애기
     * @param html
     * @return
     */
    public static String removeHtmlTag(String html){
    	return WebUtil.removeTag(html);
    }

    /**
     * 태그를 HTML형태로 그대로 출력할때 변환
     * @param txtValue
     * @return
     */
    public static String dbToHtml(String txtValue){
    	return WebUtil.DbToHtml(txtValue, "");
    }
    /**
     * 태그를 HTML형태로 그대로 출력할때 엔터값을 제외하고 변환
     * @param txtValue
     * @return
     */
    public static String dbToHtmlExceptEnter(String txtValue){
    	return WebUtil.DbToHtmlExceptEnter(txtValue, "");
    }
    
    /**
     * 오늘날짜와 해당날짜의 차이 일수.
     * @param d 해당일
     * @return 차이 날짜수.
     */
    public static long getDayFromToday(Date d){
    	Date today = new Date();
	    long diff = today.getTime() - d.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000);
    	return diffDays;
    }
    
    /**
     * 글자수로 문자열을 체크하여 글자 자르기.
     * @param str
     * @param cutCount
     * @param addString
     * @return
     */
    public static String cutString(String str, int cutCount,String addString){
    	return StringUtil.cutString(str, cutCount, addString);
    }
    /**
     * Byte크기로 문자열을 체크하여 글자 자르기.
     * @param str
     * @param byteCount
     * @param addString
     * @return
     */
    public static String cutStringByByte(String str, int byteCount,String addString){
    	return StringUtil.cutStringByByte(str, byteCount, addString);
    }
    /**
     * url인코딩
     * @param url
     * @return
     */
    public static String encodeUrl(String url){
    	return UrlUtil.encodeUrl(url);
    }
    /**
     * 현재url인코딩
     * @param request
     * @return
     */
    public static String encodeCurrentUrl(HttpServletRequest request){
    	return UrlUtil.encodeCurrentUrl(request);
    }
    /**
     * 전체 URL가져오기.
     * @param request
     * @return
     */
    public static String currentAllUrl(HttpServletRequest request){
    	return UrlUtil.currentAllUrl(request);
    }
    /**
     * 8자리 형태 날짜를 구분자 추가하여 표현
     * @param str
     * @param strSplit
     * @return
     */
    public static String convertDateSplitString(String str, String strSplit) {
    	if(str == null)return str;
    	
    	if(str.length() == 4)return str;
    	if(str.length() == 6)return str.substring(0,4) + strSplit + str.substring(4,6);
    	if(str.length() != 8)return str;
    	return str.substring(0,4) + strSplit + str.substring(4,6) + strSplit + str.substring(6,8);
    }
    
    
/////////////////////////////  특정 사이트용 ///////////////////////////
    /**
     * 코드값으로 코드명 가져오기
     * @param list
     * @param cd
     * @return
     */
/*    public static String getCdNm(List<ComIndvlzVO> list, String cd) {
		if(list != null) {
			for(ComIndvlzVO data:list) {
				if(data.getIndvlzCd().equals(cd))return data.getCdNm();
			}
		}
		return "";
    }*/
}
