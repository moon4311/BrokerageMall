package injnsobang.com.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComGrpMenuAuthVO.java
 * @Description : 그룹별메뉴관리관리 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Getter
@Setter
public class ComGrpMenuAuthVO extends AbstractVO {

	private String authGrpId;         //권한그룹ID
	private String menuSno;           //메뉴순번
	private String streAuthYn;        //저장권한여부
	private String redngAuthYn;       //읽기권한여부
	private String delAuthYn;         //삭제권한여부
	private String prntgAuthYn;       //인쇄권한여부
	
	//분기
	private String cmgmaMode;
	
	//추가데이터
	private List<ComGrpMenuAuthVO> authList;
}