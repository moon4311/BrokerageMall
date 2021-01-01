package injnsobang.com.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComSmplVO.java
 * @Description : 샘플 그룹 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.17
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Getter
@Setter
public class ComGrpMenuAuthChngVO extends AbstractVO {
	private String chngHistSno;       //변경일련번호
	private String authGrpId;         //권한그룹ID
	private String menuSno;           //메뉴순번
	private String streAuthYn;        //저장권한여부
	private String redngAuthYn;       //읽기권한여부
	private String delAuthYn;         //삭제권한여부
	private String prntgAuthYn;       //인쇄권한여부
	
	//분기
	private String cgmcMode;
	
	//추가데이터
	private String grpNm;
	private String menuNm;
	private String crtrNm;
}