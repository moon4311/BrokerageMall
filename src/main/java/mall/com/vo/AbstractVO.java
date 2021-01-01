package injnsobang.com.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;

import injnsobang.com.util.JsonUtil;
import lombok.Data;

/**
 *  기본 모델 클래스
 * 
 * @author 
 * @since 2020.07.21
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2020.07.21            최초 생성
 *
 * </pre>
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(value={"searchCondition","searchKeyword","pageIndex","pageUnit","pageSize","firstIndex","lastIndex"
		,"recordCountPerPage","orderSql","crtrDt","updrDt","retUrl"})
@Data
public abstract class AbstractVO {

	/** 검색조건 */
	private String searchCondition;
	
	/** 검색Keyword */
	private String searchKeyword;
	
	/** 상세검색여부 */
	private String searchDetailOpen;
	
	/** 현재페이지 */
	private int pageIndex = 1;

	/** 페이지갯수 */
	private int pageUnit = 10;

	/** 페이지사이즈 */
	private int pageSize = 10;

	/** firstIndex */
	private int firstIndex = 0;

	/** lastIndex */
	private int lastIndex = 1;

	/** recordCountPerPage */
	private int recordCountPerPage = 10;


	/** 검색 기간-시작 */
	private String searchStartDt;
	/** 검색 기간-종료 */
	private String searchEndDt;
	
	
	private String orderSql;

	private String regstrId;
	private Date regDt;
	private String updrId;
	private Date updrDt;
	private String useYn;

	private String retUrl;

	private String iuMode;

	@Override
	public String toString() {
		String ret = "";
		try {
			ret = JsonUtil.convertObjectToJson(this);			
		}catch(JsonProcessingException ex) {
			ret = ex.getMessage();
		}
		return ret;
	}
}
