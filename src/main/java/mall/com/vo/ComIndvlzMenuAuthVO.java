package injnsobang.com.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComIndvlzMenuAuthVO.java
 * @Description : 사용자별메뉴관리 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Getter
@Setter
public class ComIndvlzMenuAuthVO extends AbstractVO {

	private String mbrId;
	private String menuSno;
	private String streAuthYn;
	private String redngAuthYn;
	private String delAuthYn;
	private String prntgAuthYn;
	
	//분기
	private String cmumaMode;
	
	//추가데이터
	private List<ComIndvlzMenuAuthVO> authList;
	
}