package mall.com.enums;

/**
 * 공통코드 관련 테이블 조회용 코드
 * @author KMK
 *
 */
public enum EnumGrpCd {
	
	PSITN("PSITN","소속코드"),

	;
	
	private String code;//DB의 grpCd값
	private String name;//DB의 grpCd명
	private EnumGrpCd(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String geName() {
		return name;
	}
	
}
