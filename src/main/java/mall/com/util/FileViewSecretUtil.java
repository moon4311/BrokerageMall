package injnsobang.com.util;

import java.util.UUID;

import egovframework.com.cmm.service.Globals;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.util.DatasecretUtil;
import injnsobang.com.util.NullUtil;
import injnsobang.com.vo.ComFileVO;

public class FileViewSecretUtil {

	/**
     * 파일뷰 암호화
     * @param fileStorePath
     * @param fileName
     * @return
     */
    public static String fileViewEncrypt(String fileStorePath, String fileName) {
    	return DatasecretUtil.encrypt(new StringBuffer().append(fileStorePath).append("|").append(fileName).toString());
    }
    
    /**
     * 파일뷰 복호화
     * @param encryptedStr
     * @return
     * @throws EgovBizException
     */
    public static ComFileVO fileViewDecrypt(String encryptedStr) throws EgovBizException {
    	ComFileVO fileVO = new ComFileVO();
		String[] str = DatasecretUtil.decrypt(encryptedStr).split("\\|");
		System.out.println(str.length);
		if(str.length < 2)throw new EgovBizException("fail to decrypt");
		
		String fileStoreCours = Globals.UPLOAD_PATH + NullUtil.nullString(str[0]);
		String fileStoreName = NullUtil.nullString(str[1]);
		
		fileVO.setFileStoreCours(fileStoreCours);
		fileVO.setFileStoreName(fileStoreName);
		return fileVO;
	}
    
    /**
     * bbstxtAtchFileId 값에 넣을 UUID 가져오기
     * @return
     */
    public static String getRandonUuid() {
    	return UUID.randomUUID().toString();
    }
}
