package mall.com.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComGrpVO.java
 * @Description : 그룹코드 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.17
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Getter
@Setter
public class ComGrpVO extends AbstractVO {

	private String grpCd;
	private String cdNm;
	private String cdExplnt;
	private String uprGrpCd;
	private String chngPosblYn;
}