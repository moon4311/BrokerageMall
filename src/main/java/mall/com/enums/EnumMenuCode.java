package mall.com.enums;

public enum EnumMenuCode {
	
	/* 사용자 메뉴 목록 */
	/* 관리자 메뉴 목록 */
	MAIN ("MAIN"),					//메인
	
	COM("7000000"),				//시스템관리(/injnsobang/com/commonCode/indexLarge.do)
	COM_SYS("7010000"),			//시스템관리 > 시스템관리(공통기능)(/injnsobang/com/indvlz/index.do)
	COM_SYS_COD("7010100"),		//시스템관리 > 시스템관리(공통기능) > 코드관리(/injnsobang/com/indvlz/index.do)
	
	COM_MENU			("7010200"),//관리자 > 메뉴 관리				/injnsobang/com/menu/index.do
	
//	COM_SYS_ATH("7010300"),		//시스템관리 > 시스템관리(공통기능) > 권한관리(/injnsobang/com/grpMenuAuth/index.do)
	COM_GROUP_MENU_AUTH	("7010301"),//관리자 > 그룹별 메뉴권한 관리		/injnsobang/com/groupMenuAuth/index.do
	COM_USER_MENU_AUTH	("7010302"),//관리자 > 개인별 메뉴권한 관리		/injnsobang/com/indvlzMenuAuth/index.do
	
//	COM_SYS_GRP("7010400"),		//시스템관리 > 시스템관리(공통기능) > 권한그룹관리(/injnsobang/com/authGrp/index.do)
	COM_AUTH_GRP("7010400"),		//관리자 > 그룹 관리				/injnsobang/com/authGrp/index.do
	
//	COM_SYS_MBR("7010500"),		//시스템관리 > 시스템관리(공통기능) > 사용자관리(/injnsobang/com/mbr/index.do)
	COM_MBR	("7010500"),			//관리자 > 회원 관리				/injnsobang/com/mbr/
	
//	COM_SYS_HST("7010600"),		//시스템관리 > 시스템관리(공통기능) > 권한관리 변경이력(/injnsobang/com/grpAuthChng/index.do)
	COM_GROUP_MENU_HST("7010601"),		//시스템관리 > 시스템관리(공통기능) > 권한관리 변경이력 > 그룹별(/injnsobang/com/grpAuthChng/index.do)
	COM_USER_MENU_HST("7010602"),		//시스템관리 > 시스템관리(공통기능) > 권한관리 변경이력 > 사용자별 (/injnsobang/com/indvlzAuthChng/index.do)
	
//	COM_SYS_LOG("7010700"),		//시스템관리 > 시스템관리(공통기능) > 접속기록 로그(/injnsobang/com/log/index.do)
	COM_SYS_LOG("7010700"),			//관리자 > 로그 관리				/injnsobang/com/log/index.do
	COM_SYS_MBR_LOG("7010701"),			//관리자 > 접속기록 로그 > 시스템 사용이력			/injnsobang/com/log/index.do
	
	
	CNFCRM("7010704"),		//청사관리 > 회의실관리
	CNFCRM_RSRV("7010705"), //청사관리 > 회의실예약관리
//	KWRD("7010706"),		//청사관리 > 키워드관리
	
	
	/* 전자정부 기본 */
	CCM_CCC("7010708"),  // 시스템관리 > 코드관리 > 공통분류코드
	CCM_CCA("7010709"),  // 시스템관리 > 코드관리 > 공통코드
	CCM_CDE("7010710"),  // 시스템관리 > 코드관리 > 공통상세코드

	USS_DPT("7010726"),  // 시스템관리 > 부서관리
	
	
	
	
	
	
	/* 개인정보 관리 */
	USR_INFO("7010714"),
	/* 부서원 상태 관리*/
	USR_DEPT("7010715"),
	
	/* 키오스크 관리*/
	KSK_KKM("7010718"),
	KSK_KWRD("7010719"),
	
	/* 회의실 예약신청 */
	USS_MPM("7010712"),  // 시스템관리 > 회의관리 > 회의실관리
	USS_MPR("7010713"),  // 시스템관리 > 회의관리 > 회의실예약관리 ( 전자정부 임시 )

	/* 회의실 예약 신청내역 */
	MTG_HIST("7010730"),
	MTG_APPLY("7010729"),
	/* 회의실 예약관리 */
	MTG_RESVE("7010728"),
	
	/* 장비관리 */
	EQM_KSK("7010720"), //키오스크
	EQM_MPM("7010721"), //회의실
	EQM_DPT("7010722"), //부서
	EQM_EBD("7010723"), //전자칠판
	EQM_WPM("7010724"), //대기화면
	EQM_RMT("7010725"), //원격제어
	
	/* 샘플 */
	COM_SMPL(""),											//샘플 메뉴코드(Sample Java에서 사용중)
	NONE("") 												//메뉴의 종류가 아닐경우.
	;
	
	private String menuSno;//DB의 menuSno값
	private EnumMenuCode(String menuSno){
		this.menuSno = menuSno;
	}
	public String getMenuSno(){
		return this.menuSno;
	}
}
