package mall.com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.service.ComGrpService;
import injnsobang.com.util.NullUtil;
import injnsobang.com.vo.ComGrpVO;

/**
 * @Class Name : ComGrpServiceImpl.java
 * @Description : 그룹코드 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Service("comGrpService")
public class ComGrpServiceImpl extends AbstractServiceImpl implements ComGrpService {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	@Resource(name = "comGrpDao")
	private ComGrpDao comGrpDao;
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComGrpVO> selectComGrpList(ComGrpVO searchVO) {
		List<ComGrpVO> list = (List<ComGrpVO>)comGrpDao.selectComGrpList(searchVO);
//		if(list != null){
//			for(ComGrpVO result:list){
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
	public Integer selectComGrpTot(ComGrpVO searchVO) {
		return comGrpDao.selectComGrpTot(searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComGrpVO selectComGrp(ComGrpVO searchVO) {
		ComGrpVO result = comGrpDao.selectComGrp(searchVO);
		return result;
	}

	/**
	 * 추가 / 수정
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public void writeComGrp(ComGrpVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);

		validate(searchVO);
		
		String id = NullUtil.nullString(searchVO.getGrpCd());
		if("".equals(id)){			
			comGrpDao.insertComGrp(searchVO);
			id = searchVO.getGrpCd();
		}else{
			ComGrpVO result = selectComGrp(searchVO);
			if(!isModifiable(result))throwBizException("com.error.noauth.modify");
			id = result.getGrpCd();
			
			comGrpDao.updateComGrp(searchVO);
		}

	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComGrp(ComGrpVO searchVO) throws EgovBizException {
		String userId = getLoginID();
		//권한 체크
		ComGrpVO result = selectComGrp(searchVO);
		if(!isDeletable(result))throwBizException("com.error.noauth.delete");
				
		searchVO.setRegstrId(userId);
		searchVO.setUpdrId(userId);
		comGrpDao.deleteComGrp(searchVO);
	}

	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isViewable(ComGrpVO searchVO){
		return super.isViewable(searchVO);
	}

	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isModifiable(ComGrpVO searchVO){
		return super.isModifiable(searchVO);
	}

	/**
	 * 해당 데이터를 삭제할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isDeletable(ComGrpVO searchVO){
		log.debug(searchVO);
		log.debug(isAdmin());
		log.debug(isDelAuth());
		if(searchVO == null)return false;
		if(isAdmin())return true;
		
		return isDelAuth();
	}

	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역
	// ///////////////////////////////////////////////////////////////////
	/**
	 * DB에 입력하기 위한 데이터 처리.
	 * @param searchVO
	 * @throws EgovBizException
	 */
	private void validate(ComGrpVO searchVO){
		
	}
	// /////////////////////private,protected 메소드 선언 영역 끝
	// ///////////////////////////////////////////////////////////////////
}
