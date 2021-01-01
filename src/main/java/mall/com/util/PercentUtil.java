package mall.com.util;



/**
 * @Class Name : PercentUtil.java
 * @Description : 퍼센트 관련 UTIL
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
public class PercentUtil {

	/**
	 * 백분율 소수점찍기
	 * @param cnt 분류
	 * @param tot 전체
	 * @param fCnt 소수점 자리수
	 * @return
	 */
	public static double ratioCal(double cnt, double tot,int fCnt){
		double ret = 0;
		if(cnt < 0 || tot <= 0 || fCnt < 0)return ret;
		try{
			ret = Double.parseDouble(String.format("%."+fCnt+"f", cnt / tot * 100));
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ret;
	}
}
