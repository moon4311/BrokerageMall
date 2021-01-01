package egovframework.com.ccm.cca.service;

import java.io.Serializable;

import injnsobang.com.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

/**
*
* 공통코드 VO 클래스
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
public class CmmnCodeVO extends AbstractVO implements Serializable {

	private static final long serialVersionUID = -4184057693049713450L;

	/*
	 * 코드ID
	 */
	private String codeId = "";

	/*
	 * 코드ID명
	 */
	private String codeIdNm = "";

	/*
	 * 코드ID설명
	 */
	private String codeIdDc = "";

	/*
	 * 분류코드
	 */
	private String clCode = "";

	/*
	 * 분류코드명
	 */
	private String clCodeNm = "";
	

	//분기용
    private String commMode;

	
}
