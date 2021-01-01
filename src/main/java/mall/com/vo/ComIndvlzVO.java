package injnsobang.com.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : ComIndvlzVO.java
 * @Description : 개별코드 VO
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.17
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Getter
@Setter
public class ComIndvlzVO extends AbstractVO {

	private String indvlzCd;        //개별코드
	private String cdNm;            //코드명
	private String cdExplnt;        //코드설명
	private String adtnCd1Vl;       //추가코드1값
	private String adtnCd2Vl;       //추가코드2값
	private String grpCd;           //그룹코드
	private String uprIndvlzCd;     //상위개별코드
	private String cdSortSeq;       //코드정렬순서
	
	//분기용
	private String ciMode;
}