package mall.com.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;


public class ExcelUtil {

	public static String getCellValue(Cell cell){
		String value = null;
		if(cell == null)return "";
		//2017.02.03 소스취약점 처리.. 툴에서 잡아내지 못하는 널포인트 역참조..
		if(cell.getCellType() < 0)return "";
		switch (cell.getCellType()) { 
			case XSSFCell.CELL_TYPE_FORMULA: 
				value = cell.getCellFormula(); 
			break; 
			case XSSFCell.CELL_TYPE_NUMERIC: 
				value = "" + doubleToString(cell.getNumericCellValue());
				//value = (value.endsWith(".0"))?value.replace(".0", ""):value;
			break; 
			case XSSFCell.CELL_TYPE_STRING: 
				value = "" + cell.getStringCellValue(); 
			break; 
			case XSSFCell.CELL_TYPE_BLANK: 
				value = ""; 
			break; 
			case XSSFCell.CELL_TYPE_ERROR: 
				value = "" + cell.getErrorCellValue(); 
			break; 
			default: 
		} 
		return value.trim();
	}
	
	/**
     * <p>
     * double 형의 셀 데이터를 String 형으로 변환하여 리턴한다.
     * </p>
     * @param d
     *        <code>double</code>
     * @return 결과 값
     */
    public static String doubleToString(double d) {
        long lValue = (long) d;
        return (lValue == d) ? Long.toString(lValue) : Double.toString(d);
    }
	
}
