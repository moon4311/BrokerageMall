package injnsobang.com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.service.Globals;
import injnsobang.com.vo.ComFileVO;


/**
 * @Class Name  : FileMngUtil.java
 * @Description : 메시지 처리 관련 유틸리티
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   2009.02.13       이삼섭                  최초 생성
 *   2011.08.09       서준식                  utl.fcc패키지와 Dependency제거를 위해 getTimeStamp()메서드 추가
 *   2014.05.22       김문기                  파일 확장자 체크
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 02. 13
 * @version 1.0
 * @see
 *
 */
public class FileMngUtil {

    public static final int BUFF_SIZE = 2048;

	protected final static Log log = LogFactory.getLog(FileMngUtil.class);

    /**
     * 첨부파일에 대한 목록 정보를 취득한다. 
     * @param files
     * @param filePrefix 실제 생성될 파일 prefix
     * @param fileKeyParam
     * @param storePath 기본경로 이 외 추가 경로
     * @param allowExt 허용확장자
     * @param allowNotExt 허용금지확장자
     * @param fileNamePrefix 파일명 prefix(prefix가 일치하는 파일만 리턴)
     * @param maxSize 최대 용량(byte)
     * @param fileBasePath 기본경로
     * @return 첨부파일목록
     * @throws Exception
     */
    public static List<ComFileVO> parseFileInf(Map<String, MultipartFile> files, String filePrefix, String storePath, String allowExt, String allowNotExt, String fileNamePrefix, double maxSize, String fileBasePath) throws Exception {
    	int fileKey = 1;

		String storePathString = fileBasePath;//기본경로
		//String atchFileIdString = "";
	
		if (storePath != null && !"".equals(storePath)) {
		    storePathString += System.getProperty("file.separator") + storePath;
		}
		
		//현재 날짜폴더 추가.(파일이 많지 않으므로 생성 안함.)
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		storePathString += System.getProperty("file.separator") + sdf.format(new Date());
	
		storePathString = EgovWebUtil.filePathBlackList(storePathString);
		File saveFolder = new File(storePathString);
	
		if (!saveFolder.exists() || saveFolder.isFile()) {
		    saveFolder.mkdirs();
		}
	
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<ComFileVO> result  = new ArrayList<ComFileVO>();
		ComFileVO fvo;
	
		while (itr.hasNext()) {
		    Entry<String, MultipartFile> entry = itr.next();
	
		    file = entry.getValue();
		    String orginFileName = file.getOriginalFilename();
	
		    //--------------------------------------
		    // 원 파일명이 없는 경우 처리
		    // (첨부가 되지 않은 input file type)
		    //--------------------------------------
		    if ("".equals(orginFileName)) continue;
		    ////------------------------------------
		    //파일명prefix 체크
		    if(!fileNamePrefix.equals("") && (file.getName().length() < fileNamePrefix.length() || !file.getName().substring(0,fileNamePrefix.length()).equals(fileNamePrefix)))continue;
	
		    int index = orginFileName.lastIndexOf(".");
		    //String fileName = orginFileName.substring(0, index);
		    String fileExt = orginFileName.substring(index + 1);
		    String newName = filePrefix + getTimeStamp() + fileKey + "." + fileExt;
		    long _size = file.getSize();
		    
		    if(_size > maxSize)continue;
	
		    if (!"".equals(orginFileName)) {
		    	filePath = storePathString + File.separator + newName;
		    }
		    fvo = new ComFileVO();
		    fvo.setFileInputName(file.getName());
		    fvo.setFileExt(fileExt);
		    fvo.setFileStoreCours(File.separator + storePath + File.separator);
		    fvo.setFileSize(Long.toString(_size));
		    fvo.setFileOriginalName(orginFileName);
		    fvo.setFileStoreName(newName);
		    fvo.setContentType(file.getContentType());
		    //fvo.setFileId(atchFileIdString);
		    fvo.setFileSn(fileKey);
	
	    	//확장자 체크
		    boolean isAllowFile = false;
		    //허용확장자
	    	if(allowExt != null && !"".equals(allowExt)){
	    		String[] arrAllowExt = allowExt.split(",");
	    		for(String ext:arrAllowExt){
	    			if(ext.equals(fvo.getFileExt().toLowerCase())){
	    				isAllowFile = true;
	    				break;
	    			}
	    		}
	    	}else{
	    		isAllowFile = true;
	    	}
	    	
	    	//비허용확장자
	    	if(allowNotExt != null && !"".equals(allowNotExt)){
	    		String[] arrAllowNotExt = allowNotExt.split(",");
	    		for(String extNot:arrAllowNotExt){
	    			if(extNot.equals(fvo.getFileExt().toLowerCase())){
	    				isAllowFile = false;
	    				break;
	    			}
	    		}
	    	}
	
		    //확장자 체크 후 저장.
		    fvo.setPassExt(isAllowFile);
	    	if(isAllowFile) {
	    		file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
			    result.add(fvo);
			    fileKey++;
	    	}
		}
	
		return result;
    }
    public static List<ComFileVO> parseFileInf(Map<String, MultipartFile> files, String filePrefix, String storePath, String allowExt, String allowNotExt, String fileNamePrefix, double maxSize) throws Exception {
    	return parseFileInf(files, filePrefix, storePath, allowExt, allowNotExt, fileNamePrefix, maxSize, Globals.UPLOAD_PATH);
    }

    
    /**
     * 서버의 파일을 다운로드한다.
     * @param request
     * @param response
     * @param fileStoreCours
     * @param fileStoreName
     * @param fileOriginalName
     * @throws Exception
     */
    public static void downFile(HttpServletRequest request, HttpServletResponse response, String fileStoreCours, String fileStoreName, String fileOriginalName) throws Exception {

    	File file = new File(EgovWebUtil.filePathBlackList(fileStoreCours),EgovWebUtil.filePathBlackList(fileStoreName));
    	log.debug(file.getPath());
		//파일존재여부
		if (!file.exists()) throw new Exception("no file");
		//파일인지여부
		if (!file.isFile()) throw new Exception("not file"); 
		

	    File uFile = new File(fileStoreCours, fileStoreName);
	    int fSize = (int)uFile.length();

	    if (fSize > 0) {
			String mimetype = "application/x-msdownload";
	
			//response.setBufferSize(fSize);	// OutOfMemeory 발생
			response.setContentType(mimetype);
			//response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fvo.getOrignlFileNm(), "utf-8") + "\"");
			String browser = EncodingUtil.getBrowser(request);
			String encodeFileName = EncodingUtil.getDisposition(fileOriginalName, browser);
			///response.setHeader("Content-Disposition", "attachment;filename=\"" + encodeFileName + "\";");
//			DownloadUtil.setDisposition(fVO.getFileOriginalName(), request, response);
			///response.setContentLength(fSize);
	
			response.setContentType(mimetype+";");
	    	response.setHeader("Content-Disposition", "attachment; filename=" + encodeFileName + ";");
	    	response.setHeader("Content-Transfer-Encoding", "binary");
	    	response.setHeader("Pragma", "no-cache");
	    	response.setHeader("Expires", "0");
			
			/*
			 * FileCopyUtils.copy(in, response.getOutputStream());
			 * in.close(); 
			 * response.getOutputStream().flush();
			 * response.getOutputStream().close();
			 */
			BufferedInputStream in = null;
			BufferedOutputStream out = null;
	
			try {
			    in = new BufferedInputStream(new FileInputStream(uFile));
			    out = new BufferedOutputStream(response.getOutputStream());
	
			    FileCopyUtils.copy(in, out);
			    out.flush();
			} catch (Exception ex) {
			    //ex.printStackTrace();
			    // 다음 Exception 무시 처리
			    // Connection reset by peer: socket write error
				log.error("IGNORED: " + ex.getMessage());
			} finally {
			    if (in != null) {
					try {in.close();} catch (Exception ignore) {log.error("IGNORED: " + ignore.getMessage());}
			    }
			    if (out != null) {
					try {out.close();} catch (Exception ignore) {log.error("IGNORED: " + ignore.getMessage());}
			    }
			}

	    } else {
			response.setContentType("application/x-msdownload");
	
			PrintWriter printwriter = response.getWriter();
			printwriter.println("<html>");
			printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fileOriginalName + "</h2>");
			printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
	    }
    }

    /**
     * 기본파일저장경로를 포함한 파일다운로드처리.(DB컬럼에 저장하는 경로가 없는경우)
     */
    public static void downFile(HttpServletRequest request, HttpServletResponse response, String fileStoreCours, String fileStoreName, String fileOriginalName, String fileBasePath) throws Exception {
    	String storePathString = fileBasePath;//기본경로	
		if (fileStoreCours != null && !"".equals(fileStoreCours)) {
		    storePathString += System.getProperty("file.separator") + fileStoreCours;
		}
		downFile(request, response, storePathString, fileStoreName, fileOriginalName);
    }
    
    /**
     * 서버의 파일을 보여준다(이미지,동영상)
     * @param request
     * @param response
     * @param fileStoreCours
     * @param fileStoreName
     * @throws Exception
     */
    public static void showFile(HttpServletRequest request, HttpServletResponse response, String fileStoreCours, String fileStoreName) throws Exception {

    	
		File file = null;
		FileInputStream fis = null;
	
		BufferedInputStream in = null;
		ByteArrayOutputStream bStream = null;

		try {
		    int index = fileStoreName.lastIndexOf(".");
		    String fileExt = fileStoreName.substring(index + 1).toLowerCase();
		    
		    file = new File(EgovWebUtil.filePathBlackList(fileStoreCours), EgovWebUtil.filePathBlackList(fileStoreName));
		    fis = new FileInputStream(file);
	
		    in = new BufferedInputStream(fis);
		    bStream = new ByteArrayOutputStream();
	
		    int imgByte;
		    while ((imgByte = in.read()) != -1) {
			bStream.write(imgByte);
		    }
	
			String type = "";
		
			if (fileExt != null && !"".equals(fileExt)) {
			    if ("jpg".equals(fileExt)) {
				type = "image/jpeg";
			    } else {
				type = "image/" + fileExt;
			    }
			    type = "image/" + fileExt;

			    //동영상,swf추가
			    if ("wmv".equals(fileExt)) {
			    	type = "video/x-ms-wmv";
			    }else if ("mp3".equals(fileExt)) {
			    	type = "audio/x-mpeg";
			    }else if ("mp4".equals(fileExt)) {
			    	type = "video/mp4";
			    }else if ("mov".equals(fileExt)) {
			    	type = "video/quicktime";
			    }else if ("mpeg".equals(fileExt)) {
			    	type = "video/mpeg";
			    }else if ("swf".equals(fileExt)) {
			    	type = "application/x-shockwave-flash";
			    }
			} else {
			    log.error("Image fileType is null.");
			}
		
			response.setHeader("Content-Type", type);
			response.setContentLength(bStream.size());
		
			bStream.writeTo(response.getOutputStream());
		
			response.getOutputStream().flush();
			response.getOutputStream().close();
	
			// 2011.10.10 보안점검 후속조치 끝
		} finally {
			if (bStream != null) {
				try {
					bStream.close();
				} catch (Exception ignore) {
					log.debug("IGNORE: " + ignore.getMessage());
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception ignore) {
					log.debug("IGNORE: " + ignore.getMessage());
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception ignore) {
					log.debug("IGNORE: " + ignore.getMessage());
				}
			}
		}
    }
    /**
     * 기본파일저장경로를 포함한 이미지보여주기.(DB컬럼에 저장하는 경로가 없는경우)
     */
    public static void showFile(HttpServletRequest request, HttpServletResponse response, String fileStoreCours, String fileStoreName, String fileBasePath) throws Exception {
    	String storePathString = fileBasePath;//기본경로	
		if (fileStoreCours != null && !"".equals(fileStoreCours)) {
		    storePathString += System.getProperty("file.separator") + fileStoreCours;
		}
		showFile(request, response, storePathString, fileStoreName);
    }


    /**
     * 2011.08.09
     * 공통 컴포넌트 utl.fcc 패키지와 Dependency제거를 위해 내부 메서드로 추가 정의함
     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
     *
     * @param
     * @return Timestamp 값
     * @exception MyException
     * @see
     */
    private static String getTimeStamp() {

		String rtnStr = null;
	
		// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";
	
		try {
		    SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		    Timestamp ts = new Timestamp(System.currentTimeMillis());
	
		    rtnStr = sdfCurrent.format(ts.getTime());
		} catch (Exception e) {
		    //e.printStackTrace();
			
		    //throw new RuntimeException(e);	// 보안점검 후속조치
		    log.debug("IGNORED: " + e.getMessage());
		}
	
		return rtnStr;
    }
    
    /**
     * byte 단위 변환
     * @param bytes
     * @return
     */
    public static String byteToString(double bytes) {
        String retFormat = "0";

        String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };
        

        if (bytes != 0) {
              int idx = (int) Math.floor(Math.log(bytes) / Math.log(1024));
              DecimalFormat df = new DecimalFormat("#,###.##");
              double ret = ((bytes / Math.pow(1024, Math.floor(idx))));
              retFormat = df.format(ret) + " " + s[idx];
         } else {
              retFormat += " " + s[0];
         }

         return retFormat;
    }
    
    /**
     * 파일에 접근해서 파일사이즈 가져오기
     * @param filePath
     * @return
     */
    public static long getFileSize(String filePath) {
    	return getFileSize(new File(NullUtil.nullString(filePath)));
    }
    
    /**
     * 파일에 접근해서 파일사이즈 가져오기
     * @param filePath
     * @return
     */
    public static long getFileSize(File file) {
    	long size = 0;
    	if(file != null) {
    		if(file.exists()) size = file.length();
    	}
    	return size;
    }
}
