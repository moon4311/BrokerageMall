package mall.com.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComMbrVO.java
 * @Description : 회원 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Getter
@Setter
public class ComMbrVO extends ComAuthGrpVO {

	private String commMode;
	
	private String mbrId;                    //회원ID
	private String psitnCd;                  //소속코드
	private String pswd;                     //비밀번호
	private String mbrNm;                    //회원명
	private String mbrIpAddr;                //회원IP주소
	private String confmYn;                  //승인여부
	private String confmBgnDate;             //승인시작일자
	private String confmEndDate;             //승인종료일자
	private String telno;                    //전화번호
	private String faxno;                    //팩스번호
	private String email;                    //이메일
	private Date pswdChngDt;               //비밀번호변경일시
	private String loginFailrNmb;            //로그인실패횟수
	private Date lastLoginDt;               //최종로그인일시
	
	private String uprPsitnCd; //소속분류
	
	//검색용
	private String notmbrId;
	

}