package mall.com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.service.ComAuthGrpService;
import mall.com.util.NullUtil;
import mall.com.vo.ComAuthGrpVO;

/**
 * @Class Name : ComAuthGrpServiceImpl.java
 * @Description : 그룹 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Service("comAuthGrpService")
public class ComAuthGrpServiceImpl extends AbstractServiceImpl implements ComAuthGrpService {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	@Resource(name = "comAuthGrpDao")
	private ComAuthGrpDao comAuthGrpDao;
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComAuthGrpVO> selectComAuthGrpList(ComAuthGrpVO searchVO) {
		List<ComAuthGrpVO> list = (List<ComAuthGrpVO>)comAuthGrpDao.selectComAuthGrpList(searchVO);
//		if(list != null){
//			for(ComAuthGrpVO result:list){
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
	public Integer selectComAuthGrpTot(ComAuthGrpVO searchVO) {
		return comAuthGrpDao.selectComAuthGrpTot(searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComAuthGrpVO selectComAuthGrp(ComAuthGrpVO searchVO) {
		ComAuthGrpVO result = comAuthGrpDao.selectComAuthGrp(searchVO);
		return result;
	}

	/**
	 * 추가 / 수정
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public void writeComAuthGrp(ComAuthGrpVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);

		validate(searchVO);
		
		String id = NullUtil.nullString(searchVO.getAuthGrpId());
		if("".equals(id)){			
			comAuthGrpDao.insertComAuthGrp(searchVO);
			id = searchVO.getAuthGrpId();
		}else{
			ComAuthGrpVO result = selectComAuthGrp(searchVO);
			if(!isModifiable(result))throwBizException("com.error.noauth.modify");
			id = result.getAuthGrpId();
			
			comAuthGrpDao.updateComAuthGrp(searchVO);
		}

	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComAuthGrp(ComAuthGrpVO searchVO) throws EgovBizException {
		String userId = getLoginID();
		//권한 체크
		ComAuthGrpVO result = selectComAuthGrp(searchVO);
		if(!isModifiable(result))throwBizException("com.error.noauth.delete");
				
		searchVO.setRegstrId(userId);
		searchVO.setUpdrId(userId);
		comAuthGrpDao.deleteComAuthGrp(searchVO);
	}

	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isViewable(ComAuthGrpVO searchVO){
		return super.isViewable(searchVO);
	}

	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isModifiable(ComAuthGrpVO searchVO){
		return super.isModifiable(searchVO);
	}

	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역
	// ///////////////////////////////////////////////////////////////////
	/**
	 * DB에 입력하기 위한 데이터 처리.
	 * @param searchVO
	 * @throws EgovBizException
	 */
	private void validate(ComAuthGrpVO searchVO){
		
	}
	// /////////////////////private,protected 메소드 선언 영역 끝
	// ///////////////////////////////////////////////////////////////////
}
