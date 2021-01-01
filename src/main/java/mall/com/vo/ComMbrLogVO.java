package mall.com.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComMbrLogVO.java
 * @Description : 로그 VO
 * @Modification Information
 * 
 * @author
 * @since 2020.07.21
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Getter
@Setter
public class ComMbrLogVO extends AbstractVO {

	private String mbrId;              //회원ID
	private String mbrLogMngmNo;       //회원로그관리번호
	private String logCnts;            //로그내용
	private String menuSno;            //메뉴번호
	
	//추가데이터
	private String mbrNm;
	private String mbrIpAddr;
	private String uprMenuSno;
	private String menuNm;
	
	//분기
	private String cmlMode;
	
	//검색용
	private String searchStart;
	private String searchEnd;
	private String searchMenuSno;
	
}