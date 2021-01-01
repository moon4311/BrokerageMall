package injnsobang.com.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import egovframework.rte.fdl.cryptography.impl.EgovEnvCryptoServiceImpl;



/**
 *  공통 암/복호화 Service 클래스
 * @author 
 * @since 2020.07.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *	수정일		수정자		수정내용
 *	-------		--------	---------------------------
 *   2020.07.21            최초 생성
 *
 * </pre>
 */
public class DatasecretUtil{
	
	static ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:/egovframework/com/crypto/context-crypto-injnsobang.xml"});
	static EgovEnvCryptoService cryptoService = context.getBean(EgovEnvCryptoServiceImpl.class);
	
	/**
	 * 양방향복호화(ARIA 512bit)
	 * @param source
	 * @return
	 */
	public static String decrypt(String source) {
		if(source == null)return null;
		if("".equals(source))return "";
		String ret = "";
		try {
//			ret = DataSecret.decrypt("aria", source, Globals.SECURITY_KEY);
//			source = URLEncoder.encode(source, "UTF-8");
			ret = cryptoService.decryptNone(source);
		}catch(Exception e) {
			e.getMessage();
		}
		return ret;
	}
	
	/**
	 * 양방향암호화(ARIA 512bit)
	 * @param source
	 * @return
	 */
	public static String encrypt(String source) {
		if(source == null)return null;
		if("".equals(source))return "";
		String ret = "";
		try {
//			ret = DataSecret.encrypt("aria", source, Globals.SECURITY_KEY);
			ret = cryptoService.encrypt(source);
		}catch(Exception e) {
			e.getMessage();
		}
		return ret;
	}

	/**
	 * 단방향 암호화(SHA512)
	 * @param source
	 * @return
	 */
	public static String password(String source) {
		if(source == null)return null;
		if("".equals(source))return "";
		String ret = "";
		try {
//			ret = DataSecret.encryptPassword("sha512", source);
		}catch(Exception e) {
			e.getMessage();
		}
		return ret;
	}
}//End class