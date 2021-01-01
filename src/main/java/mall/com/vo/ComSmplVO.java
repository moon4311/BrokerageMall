package injnsobang.com.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComSmplVO.java
 * @Description : 샘플 그룹 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.17
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Getter
@Setter
public class ComSmplVO extends AbstractVO {

	private String smplSno;
	private String smplNm;

	private String csMode;
}