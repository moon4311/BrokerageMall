package egovframework.com.cmm.service;

/**
 *  Class Name : Globals.java
 *  Description : 시스템 구동 시 프로퍼티를 통해 사용될 전역변수를 정의한다.
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.01.19    박지욱          최초 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 01. 19
 *  @version 1.0
 *  @see
 *
 */

public class Globals {
	//OS 유형
    public static final String OS_TYPE = EgovProperties.getProperty("Globals.OsType");
    //DB 유형
    public static final String DB_TYPE = EgovProperties.getProperty("Globals.DbType");
    //메인 페이지
    public static final String MAIN_PAGE = EgovProperties.getProperty("Globals.MainPage");

    //파일 업로드 원 파일명
	public static final String ORIGIN_FILE_NM = "originalFileName";
	//파일 확장자
	public static final String FILE_EXT = "fileExtension";
	//파일크기
	public static final String FILE_SIZE = "fileSize";
	//업로드된 파일명
	public static final String UPLOAD_FILE_NM = "uploadFileName";
	//파일경로
	public static final String FILE_PATH = "filePath";

	public static final String SECURITY_KEY = EgovProperties.getProperty("Globals.securityKey");
	
    //업로드 경로
    public static final String UPLOAD_PATH = EgovProperties.getProperty("Globals.fileStorePath");
    //업로드 경로(직인관리)
    public static final String UPLOAD_PATH_OFFCS = EgovProperties.getProperty("Globals.fileStorePath.offcs");
    //업로드 경로(게시판)
    public static final String UPLOAD_PATH_BBSTXT = EgovProperties.getProperty("Globals.fileStorePath.bbstxt");
    
    //업로드 최대 용량
    public static final String UPLOAD_MAX_SIZE = EgovProperties.getProperty("Globals.fileUpload.maxSize");
    
    //업로드 확장자명
    public static final String UPLOAD_EXT = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    //업로드 확장자명(직인관리)
    public static final String UPLOAD_EXT_OFFCS = EgovProperties.getProperty("Globals.fileUpload.Extensions.offcs");
    //업로드 확장자명(게시판)
    public static final String UPLOAD_EXT_BBSTXT = EgovProperties.getProperty("Globals.fileUpload.Extensions.bbstxt");
   
    //GPKI 모드
    public static final String GPKI_MODE = EgovProperties.getProperty("Globals.gpkiMode");
}
