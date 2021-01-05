package mall.com.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.enums.EnumGrpCd;
import mall.com.service.ComCacheService;
import mall.com.service.ComIndvlzService;
import mall.com.util.NullUtil;
import mall.com.vo.ComIndvlzVO;

/**
 * @Class Name : ComIndvlzServiceImpl.java
 * @Description : 그룹 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Service("comIndvlzService")
public class ComIndvlzServiceImpl extends AbstractServiceImpl implements ComIndvlzService {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	@Resource(name = "comIndvlzDao")
	private ComIndvlzDao comIndvlzDao;
	@Resource(name = "comCacheService")
	private ComCacheService comCacheService;
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzVO> selectComIndvlzList(ComIndvlzVO searchVO) {
		List<ComIndvlzVO> list = (List<ComIndvlzVO>)comIndvlzDao.selectComIndvlzList(searchVO);
//		if(list != null){
//			for(ComIndvlzVO result:list){
//				
//			}
//		}
		return list;
	}

	/**
	 * 총갯수
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public Integer selectComIndvlzTot(ComIndvlzVO searchVO) {
		return comIndvlzDao.selectComIndvlzTot(searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComIndvlzVO selectComIndvlz(ComIndvlzVO searchVO) {
		ComIndvlzVO result = comIndvlzDao.selectComIndvlz(searchVO);
		return result;
	}

	/**
	 * 추가 / 수정
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public void writeComIndvlz(ComIndvlzVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);

		validate(searchVO);
		
		String id = NullUtil.nullString(searchVO.getIndvlzCd());
		if("".equals(id)){			
			comIndvlzDao.insertComIndvlz(searchVO);
			id = searchVO.getIndvlzCd();
		}else{
			ComIndvlzVO result = selectComIndvlz(searchVO);
			if(!isModifiable(result))throwBizException("com.error.noauth.modify");
			id = result.getIndvlzCd();
			
			comIndvlzDao.updateComIndvlz(searchVO);
		}

		//캐시 리로드
		comCacheService.reloadIndvlzList();
	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComIndvlz(ComIndvlzVO searchVO) throws EgovBizException {
		String userId = getLoginID();
		//권한 체크
		ComIndvlzVO result = selectComIndvlz(searchVO);
		if(!isModifiable(result))throwBizException("com.error.noauth.delete");
				
		searchVO.setRegstrId(userId);
		searchVO.setUpdrId(userId);
		comIndvlzDao.deleteComIndvlz(searchVO);
		//캐시 리로드
		comCacheService.reloadIndvlzList();
	}

	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isViewable(ComIndvlzVO searchVO){
		return super.isViewable(searchVO);
	}

	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isModifiable(ComIndvlzVO searchVO){
		return super.isModifiable(searchVO);
	}


	/**
	 * 전체코드 리스트 가져오기
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzVO> getAllIndvlz(){ 
		return comCacheService.getAllIndvlzList();		
	}


	/**
	 * 그룹코드로 개별코드 리스트 가져오기
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzVO> getIndvlzByGrpCd(EnumGrpCd grpCd){
//		return comIndvlzDao.selectComIndvlzListSimpleByGrpCd(grpCd.getCode());
		//캐시에서 가져오도록 처리
		List<ComIndvlzVO> resultList = new ArrayList<ComIndvlzVO>();
		List<ComIndvlzVO> dataList = comCacheService.getAllIndvlzList();
		if(dataList != null) {
			for(ComIndvlzVO data:dataList) {
				if(grpCd.getCode().equals(data.getGrpCd()))resultList.add(data);
			}
		}
		return resultList;
		
	}


	/**
	 * 그룹코드및 상위코드로 개별코드 리스트 가져오기
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzVO> getIndvlzByGrpCd(EnumGrpCd grpCd, String uprCd){
//		return comIndvlzDao.selectComIndvlzListSimpleByGrpCd(grpCd.getCode());
		//캐시에서 가져오도록 처리
		List<ComIndvlzVO> resultList = new ArrayList<ComIndvlzVO>();
		List<ComIndvlzVO> dataList = comCacheService.getAllIndvlzList();
		if(dataList != null) {
			for(ComIndvlzVO data:dataList) {
				if(grpCd.getCode().equals(data.getGrpCd()) && uprCd.equals(data.getUprIndvlzCd()))resultList.add(data);
			}
		}
		return resultList;
		
	}

	/**
	 * 상위코드로 개별코드 리스트 가져오기
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzVO> getIndvlzByUprCd(String uprCd){
//		return comIndvlzDao.selectComIndvlzListSimpleByUprCd(uprCd);
		//캐시에서 가져오도록 처리
		List<ComIndvlzVO> resultList = new ArrayList<ComIndvlzVO>();
		List<ComIndvlzVO> dataList = comCacheService.getAllIndvlzList();
		if(dataList != null) {
			for(ComIndvlzVO data:dataList) {
				if(uprCd.equals(data.getUprIndvlzCd()))resultList.add(data);
			}
		}
		return resultList;
	}

	/**
	 * 해당 코드가 그룹에 해당하는지 체크
	 * 
	 * @param grpCd
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	public boolean isCdInGrp(EnumGrpCd grpCd, String cd) {
		return !"".equals(getCdNmInGrp(grpCd, cd));
	}

	/**
	 * 코드값으로 이름 가져오기
	 * 
	 * @param grpCd
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	public String getCdNmInGrp(EnumGrpCd grpCd, String cd) {
		List<ComIndvlzVO> dataList = getIndvlzByGrpCd(grpCd);
		if(dataList != null) {
			for(ComIndvlzVO data:dataList) {
				if(data.getIndvlzCd().equals(cd))return data.getCdNm();
			}
		}
		return "";
	}

	/**
	 * 이름으로 코드값 가져오기
	 * 
	 * @param grpCd
	 * @param cdNm
	 * @return
	 * @throws Exception
	 */
	public String getCdInGrp(EnumGrpCd grpCd, String cdNm) {
		List<ComIndvlzVO> dataList = getIndvlzByGrpCd(grpCd);
		if(dataList != null) {
			for(ComIndvlzVO data:dataList) {
				if(data.getCdNm().equals(cdNm))return data.getIndvlzCd();
			}
		}
		return "";
	}
	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역
	// ///////////////////////////////////////////////////////////////////
	/**
	 * DB에 입력하기 위한 데이터 처리.
	 * @param searchVO
	 * @throws EgovBizException
	 */
	private void validate(ComIndvlzVO searchVO){
		
	}
	// /////////////////////private,protected 메소드 선언 영역 끝
	// ///////////////////////////////////////////////////////////////////
}
