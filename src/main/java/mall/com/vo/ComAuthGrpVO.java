package injnsobang.com.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComAuthGrpVO.java
 * @Description : 권한 그룹 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.17
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Getter
@Setter
public class ComAuthGrpVO extends AbstractVO {

	private String authGrpId;
	private String grpNm;
	private String grpExplnt;

	private String coagMode;
}