package injnsobang.com.enums;

/**
 * Mode유형
 * 
 * @author 
 *
 */
public enum EnumModeType {

	list("리스트조회","list"),
	view("상세조회","view"),
	write("작성","write"),
	writeActionJson("저장","writeActionJson"),
	deleteActionJson("삭제","deleteActionJson"),
	;
	
	
	private String name;
	private String code; //mis패키지에서 코드에 따른 select 처리
	
	private EnumModeType(String name,String code){
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}