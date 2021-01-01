package injnsobang.com.vo;

import java.util.Date;

import hrs.hr.HrsPiHrVO;
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
	private String pstnNm;                   //직위명
	private String deptNm;                   //부서명
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
	

	//인사행정시스템VO
	private HrsPiHrVO hrsPiHrVO;
	
	//추가정보 (TB_MBR_R)
	private String dptId,dptNm; //팀정보
	private String deptCd; 		//과 정보
	private String workStatus;	//근무정보
	private String task;		//담당업무
	
	//추가데이터
/*	private String psitnNm;//소속명
	private String regno;//인사행정시스템의 pk 값
	private String mrankCdNm; //계급명
	private String rdutyName;	//직위명
	private String workStatus;//근무상태
	private String photoPath; //사진 경로
	private String birthDt; //생년월일
	
	
	
	public void fillData(HrsPiHrVO hphVO) {
		this.psitnNm = hphVO.getDeptCdNm(); //부서
		this.mbrNm = hphVO.getName();		//성명
		this.mrankCdNm = hphVO.getMrankCdNm();//계급명
		this.rdutyName = hphVO.getRdutyName();//직위명
		this.telno = hphVO.getComTelno(); //회사전화번호
		this.birthDt = hphVO.getBirthDt();//생일
		this.photoPath = hphVO.getPhotoPath();//사진경로
		
	}*/
}