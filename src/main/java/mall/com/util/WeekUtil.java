package injnsobang.com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @Class Name : WeekUtil.java
 * @Description : 주 관련 UTIL
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
public class WeekUtil {
	
	public enum WEEK {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}

	/**
	 * 년월기준요일에 따른 주차정보 가져오기
	 * @param strYear 년도
	 * @param strMonth 월
	 * @return
	 */
	public static ArrayList<WeekVO> getWeeks(String strYear, String strMonth){
		return getWeeks(strYear, strMonth, WEEK.MONDAY);
	}
	/**
	 * 년월기준요일에 따른 주차정보 가져오기
	 * @param strYear 년도
	 * @param strMonth 월
	 * @param week 기준요일
	 * @return
	 */
	public static ArrayList<WeekVO> getWeeks(String strYear, String strMonth, WEEK week){
		return getWeeks(strYear, strMonth, "yyyy-MM-dd", week);
	}
	
	/**
	 * 년월기준요일에 따른 주차정보 가져오기
	 * @param strYear 년도
	 * @param strMonth 월
	 * @param pattern 출력날짜패턴
	 * @param week 기준요일
	 * @return
	 */
	public static ArrayList<WeekVO> getWeeks(String strYear, String strMonth, String pattern, WEEK week){

		int year = Integer.parseInt(strYear);
		int month = Integer.parseInt(strMonth);
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		
		DateTime dt = new DateTime(year,month,1,0,0);
		DateTime firstDate = firstDateOfWeek(week, dt);
		boolean isSameMonth = true;
		ArrayList<WeekVO> weekList = new ArrayList<WeekVO>();
		while(isSameMonth){
			String startDate = fmt.print(firstDate);
			firstDate = firstDate.plusDays(6);
			String endDate = fmt.print(firstDate);
			WeekVO w = new WeekVO();
			w.setStartDate(startDate);
			w.setEndDate(endDate);
			weekList.add(w);
			firstDate = firstDate.plusDays(1);
			if(firstDate.getMonthOfYear() != dt.getMonthOfYear())isSameMonth = false;
		}
		
		if(dt.getMonthOfYear() != new DateTime(weekList.get(0).getStartDate()).getMonthOfYear())weekList.remove(0);

		int weekCnt = 1;
		for(WeekVO w:weekList){
			w.setWeekCnt(weekCnt++);
		}
		
		return weekList;
	}
	
	/**
	 * 기준요일에 해당하는 첫번째 날짜 가져오기
	 * @param week
	 * @param dt
	 * @return
	 */
	public static DateTime firstDateOfWeek(WEEK week, DateTime dt) {

		int dayOfWeek = dt.getDayOfWeek();
		int minusDay = 0;
		int startOfWeek = 0;
		switch (week) {
		case MONDAY:
			startOfWeek = 1;
			break;
		case TUESDAY:
			startOfWeek = 2;
			break;
		case WEDNESDAY:
			startOfWeek = 3;
			break;
		case THURSDAY:
			startOfWeek = 4;
			break;
		case FRIDAY:
			startOfWeek = 5;
			break;
		case SATURDAY:
			startOfWeek = 6;
			break;
		case SUNDAY:
			startOfWeek = 7;
			break;
		}
		minusDay = dayOfWeek - startOfWeek;

		return dt.minusDays(minusDay);
	}

	/**
	 * 
	 * @param dt
	 * @param pattern
	 * @return
	 */
	public static String getDateFormat(DateTime dt, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		return fmt.print(dt);
	}

	public static class WeekVO {
		private String startDate; // 시작일(yyyy-MM-dd)
		private String endDate; // 종료일(yyyy-MM-dd)
		private int weekCnt; // 주차

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public int getWeekCnt() {
			return weekCnt;
		}

		public void setWeekCnt(int weekCnt) {
			this.weekCnt = weekCnt;
		}

		@Override
		public String toString() {
			return "WeekVO [startDate=" + startDate + ", endDate=" + endDate
					+ ", weekCnt=" + weekCnt + "]";
		}

	}
	
	/**
	 * 
	 * @param isDate(yyyy-MM-dd)
	 * @param weekEnum
	 * @
	 * @param pattern
	 * @return
	 */
	public static HashMap<String, String> getMonthWeek(String date, WEEK weekEnum){
		return getMonthWeek(date, weekEnum, 0);
	}
	
	/**
	 * 
	 * @param date
	 * @param weekEnum
	 * @param plusWeek
	 * @return
	 */
	public static HashMap<String, String> getMonthWeek(String date, WEEK weekEnum, int plusWeek){
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		DateTime dateTime = new DateTime(date);
		DateTime resultDateTime = new DateTime();
		String year = dateTime.toString("yyyy");
		String month = dateTime.toString("MM");
		boolean isPassMonth = false;
		List<WeekVO> weekList = WeekUtil.getWeeks(year, month, weekEnum);
		for(WeekVO weekVO : weekList){
			if(date.compareTo(weekVO.getStartDate()) >= 0 && date.compareTo(weekVO.getEndDate()) < 1){
				resultDateTime = new DateTime(weekVO.getStartDate());
				resultMap.put("year", resultDateTime.toString("yyyy"));
				resultMap.put("month", resultDateTime.toString("MM"));
				resultMap.put("week", (weekVO.getWeekCnt()+plusWeek)+"");
				resultMap.put("startDate", weekVO.getStartDate());
				resultMap.put("endDate", weekVO.getEndDate());
				isPassMonth = true;
				break;
			}
		}
		if(!isPassMonth){
			dateTime = dateTime.minusDays(7);
			return getMonthWeek(dateTime.toString("yyyy-MM-dd"), weekEnum, ++plusWeek);
		}
		return resultMap;
	}
	
	/**
	 * 시작일, 종료일 사이의 일수(days) 가져오기
	 * @param startDate
	 * @param endDate
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static long getDaysBetween(String startDate, String endDate, String pattern) throws Exception{
		//시작일 설정
		DateTime startDateTime = new DateTime();
		startDateTime = DateTime.parse(startDate, DateTimeFormat.forPattern(pattern));
		//종료일 설정
		DateTime endDateTime = new DateTime();
		endDateTime = DateTime.parse(endDate, DateTimeFormat.forPattern(pattern));
		
		return Days.daysBetween(startDateTime, endDateTime).getDays();
	}		
}

