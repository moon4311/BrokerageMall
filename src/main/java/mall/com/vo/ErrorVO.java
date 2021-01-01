package injnsobang.com.vo;

import lombok.Data;

/**
 *  ERROR 모델 클래스
 * 
 * @author 
 * @since 2020.07.21
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2020.07.21            최초 생성
 *
 * </pre>
 */
@Data
public class ErrorVO {

	private String errorField;
	private String errorCode;
	private String errorMessage;
}
