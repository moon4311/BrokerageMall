package mall.com.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComMenuVO.java
 * @Description : 메뉴 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Getter
@Setter
public class ComMenuVO extends AbstractVO {

	private String menuSno;
	private String menuNm;
	private String menuUrl;
	private String uprMenuSno;
	private String menuLvlVl;
	private String menuSeq;
	private String menuExplnt;

	// 추가데이터

	// 추가데이터

	// 분기
	private String cmmMode;

	// 검색용


}