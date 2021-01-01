package mall.com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  정규식 패턴 검사 UTIL
 * 
 * @author ohj
 *
 */
public class PatternUtil {

	
	
	public static final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{6,20}$"; // 영문,숫자,특수문자
	public static final String pattern2 = "^[A-Za-z[0-9]]{5,20}$"; // 영문, 숫자(5~20 자리)
	public static final String pattern3 = "^[[0-9]$@$!%*#?&]{10,20}$"; // 영문,특수문자
	public static final String pattern4 = "^[[A-Za-z]$@$!%*#?&]{10,20}$"; // 특수문자,숫자
	public static final String pattern5 = "(\\w)\\1\\1\\1"; // 같은 문자, 숫자
	public static final String patternId = "^[A-Za-z[0-9]]{8,12}$"; // 영문, 숫자(8~12 자리)
	public static final String patternPass = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$"; // 영문,특수문자,숫자(8~16자리)
	public static final String patternEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"; // email
	public static final String patternTel = "^\\d{2,3}-\\d{3,4}-\\d{4}$";//tel
	public static final String patternPhone = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";//phone
	public static final String patternNumber = "^[0-9]*$";//number
	public static final String patternDateDot = "^(19|20)\\d{2}\\.(0[1-9]|1[012])\\.(0[1-9]|[12][0-9]|3[0-1])$";//date(yyyy.MM.dd)
	public static final String patternIp = "^([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d?\\d|2[0-4]\\d|25[0-5])$";


	/**
	 * 숫자 정규식 체크
	 * @param newId
	 * @return
	 */
	public static boolean isNumber(String str) {
		if(str == null)return false;
		boolean chk = false;

		Matcher match = Pattern.compile(patternNumber).matcher(str);
		if (match.find()) {
			chk = true;
		}
		return chk;
	}
	/**
	 * 숫자 날짜형 문자 체크
	 * @param newId
	 * @return
	 */
	public static boolean isDate(String str) {
		if(str == null)return false;
		boolean chk = false;

		Matcher match = Pattern.compile(patternDateDot).matcher(str);
		if (match.find()) {
			chk = true;
		}
		return chk;
	}
	/**
	 * 전화번호 정규식 체크
	 * @param newId
	 * @return
	 */
	public static boolean isTel(String str) {
		if(str == null)return false;
		boolean chk = false;

		Matcher match = Pattern.compile(patternTel).matcher(str);
		if (match.find()) {
			chk = true;
		}
		return chk;
	}
	/**
	 * 전화번호 정규식 체크
	 * @param newId
	 * @return
	 */
	public static boolean isPhone(String str) {
		if(str == null)return false;
		boolean chk = false;

		Matcher match = Pattern.compile(patternPhone).matcher(str);
		if (match.find()) {
			chk = true;
		}
		return chk;
	}
	/**
	 * 이메일 정규식 체크
	 * @param newId
	 * @return
	 */
	public static boolean isEmail(String str) {
		if(str == null)return false;
		boolean chk = false;

		Matcher match = Pattern.compile(patternEmail).matcher(str);
		if (match.find()) {
			chk = true;
		}
		return chk;
	}
	/**
	 * IP 정규식 체크
	 * @param newId
	 * @return
	 */
	public static boolean isIp(String str) {
		if(str == null)return false;
		boolean chk = false;

		Matcher match = Pattern.compile(patternIp).matcher(str);
		if (match.find()) {
			chk = true;
		}
		return chk;
	}
	/**
	 * 아이디 정규식 체크
	 * @param newId
	 * @return
	 */
	public static boolean idRegularExpressionChk(String newId) {
		if(newId == null)return false;
		boolean chk = false;

		Matcher match = Pattern.compile(patternId).matcher(newId);
		if (match.find()) {
			chk = true;
		}
		return chk;
	}
	/**
	 * 비밀번호 정규식 체크
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean pwdRegularExpressionChk(String newPwd) {
		if(newPwd == null)return false;
		boolean chk = false;

		Matcher match = Pattern.compile(patternPass).matcher(newPwd);
		if (match.find()) {
			chk = true;
		}
		/*
		// 영문, 숫자 (10~20 자리)
		match = Pattern.compile(pattern2).matcher(newPwd);
		if (match.find()) {
			chk = true;
		}

		// 영문, 특수문자 (10~20 자리)
		match = Pattern.compile(pattern3).matcher(newPwd);
		if (match.find()) {
			chk = true;
		}

		// 특수문자, 숫자 (10~20 자리)
		match = Pattern.compile(pattern4).matcher(newPwd);
		if (match.find()) {
			chk = true;
		}

		if (chk) {
			// 연속문자 4자리
			if (samePwd(newPwd)) {
				return false;
			}

			// 같은문자 4자리
			if (continuousPwd(newPwd)) {
				return false;
			}
		}
*/
		return chk;
	}

	/**
	 * 같은 문자, 숫자 4자리 체크
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean samePwd(String pwd) {
		if(pwd == null)return false;
		Matcher match = Pattern.compile(pattern5).matcher(pwd);

		return match.find() ? true : false;
	}

	/**
	 * 연속 문자, 숫자 4자리 체크
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean continuousPwd(String pwd) {
		if(pwd == null)return false;
		int o = 0;
		int d = 0;
		int p = 0;
		int n = 0;
		int limit = 4;

		for (int i = 0; i < pwd.length(); i++) {
			char tempVal = pwd.charAt(i);
			if (i > 0 && (p = o - tempVal) > -2
					&& (n = p == d ? n + 1 : 0) > limit - 3) {
				return true;
			}
			d = p;
			o = tempVal;
		}

		return false;
	}

	/**
	 * 아이디와 동일 문자 4자리 체크
	 * 
	 * @param pwd
	 * @param id
	 * @return
	 */
	public static boolean sameId(String pwd, String id) {
		if(pwd == null || id == null)return false;
		for (int i = 0; i < pwd.length() - 3; i++) {
			if (id.contains(pwd.substring(i, i + 4))) {
				return true;
			}
		}

		return false;
	}
}