package mall.com.util;

import java.math.BigDecimal;

import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * EgovMapUtil
 * @author kmk
 *
 */
public class EgovMapUtil {

	/**
	 * EgovMap 변환
	 * @param field
	 * @return
	 */
	public static String getString(EgovMap map, String fieldName){
		Object field = map.get(fieldName);
		if(field == null)return "";
		String ret = "";
		String className = field.getClass().getName();
		if("java.math.BigDecimal".equals(className)){
			ret = String.valueOf((BigDecimal)field);
		}else{
			ret = (String)field;
		}
		return ret;
	}

	/**
	 * EgovMap 변환
	 * @param field
	 * @return
	 */
	public static Integer getInt(EgovMap map, String fieldName){
		Object field = map.get(fieldName);
		if(field == null)return null;
		Integer ret = new Integer(0);
		String className = field.getClass().getName();
		if("java.math.BigDecimal".equals(className)){
			ret = ((BigDecimal)field).intValue();
		}else{
			ret = (Integer)field;
		}
		return ret;
	}
}
