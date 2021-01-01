package injnsobang.com.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * @Class Name : ComLoginMenuVO.java
 * @Description : 로그인 처리된 실제 메뉴 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Getter
@Setter
public class ComLoginMenuVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String menuSno;
	private String menuNm;
	private String menuUrl;
	private String uprMenuSno;
	private String menuLvlVl;
	private String menuSeq;
	private String menuExplnt;
	
	//추가데이터
	private List<ComLoginMenuVO> menuChildList;

	private boolean isStreAuth;//저장권한
	private boolean isRedngAuth;//읽기권한
	private boolean isDelAuth;//삭제권한
	private boolean isPrntgAuth;//출력권한
	
	@Override
	public String toString() {
		return "ComLoginMenuVO [menuSno=" + menuSno + ", menuNm=" + menuNm + ", menuUrl=" + menuUrl + ", uprMenuSno="
				+ uprMenuSno + ", menuLvlVl=" + menuLvlVl + ", menuSeq=" + menuSeq + ", menuExplnt=" + menuExplnt
				+ ", menuChildList=" + menuChildList + ", isStreAuth=" + isStreAuth + ", isRedngAuth=" + isRedngAuth
				+ ", isDelAuth=" + isDelAuth + ", isPrntgAuth=" + isPrntgAuth + "]";
	}
	
}