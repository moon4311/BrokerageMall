package injnsobang.com.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComFileVO.java
 * @Description : 파일정보 처리를 위한 VO 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 25.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 25.
 * @version
 * @see
 *
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class ComFileVO implements Serializable {
	private String fileId;               //파일ID(사용안함)
	private int fileSn;               	 //파일순번
	private String fileStoreCours;       //실제저장되는파일저장경로
	private String fileStoreName;        //실제저장되는파일명
	private String fileOriginalName;     //업로드한 파일명
	private String fileExt;              //파일확장자
	private String fileSize;             //파일크기
	private String contentType;			 //콘텐츠 타입
	
	//기타
	private boolean passExt;				//확장자체크통과여부(true,false 문자열)
	private String fileInputName;		//file.getName()
	


}
