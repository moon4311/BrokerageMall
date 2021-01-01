package mall.com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	/**
	 * 객체를 json변경
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String convertObjectToJson(Object obj) throws JsonProcessingException{
		if(obj == null)return null;
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
	/**
	 * json을 객체로 변경 (-Dfile.encoding=UTF-8 옵션필요)
	 * @param jsonString
	 * @param valueType
	 * @return
	 * @throws IOException
	 */
	public static <T> T convertJsonToObject(String jsonString, Class<T> valueType) throws IOException{
		if(jsonString == null || "".equals(jsonString))return null;
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString.getBytes("UTF-8"), valueType);
	}
	
	public static String readFileToString(File file, String encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            String str = IOUtils.toString(in, encoding);
            in.close();
            return str;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }
    
}
