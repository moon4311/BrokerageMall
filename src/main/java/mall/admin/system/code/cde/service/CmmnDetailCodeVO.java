package mall.admin.system.code.cde.service;

import java.io.Serializable;

import mall.com.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

/**
*
* 공통상세코드 VO 클래스
* @author 공통서비스 개발팀 이중호
* @since 2009.04.01
* @version 1.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*   수정일      수정자           수정내용
*  -------    --------    ---------------------------
*   2009.04.01  이중호          최초 생성
*
* </pre>
*/
@Getter
@Setter
public class CmmnDetailCodeVO extends AbstractVO implements Serializable{

	private static final long serialVersionUID = 9137280036724974467L;

	
	private String clCode = "";
	
	/*
	 * 코드ID
	 */
    private String codeId = "";
    
    /*
     * 코드ID명
     */
    private String codeIdNm = "";
    
    /*
     * 코드
     */
	private String code = "";
	
	/*
	 * 코드명
	 */
    private String codeNm = "";
    
    /*
     * 코드설명
     */
    private String codeDc = "";
    
    //분기용
    private String commMode;
    
    private String iuMode;
}
