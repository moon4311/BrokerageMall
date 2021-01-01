package injnsobang.com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.service.ComGrpMenuAuthChngService;
import injnsobang.com.vo.ComGrpMenuAuthChngVO;

/**
 * @Class Name : ComGrpMenuAuthChngServiceImpl.java
 * @Description : 그룹메뉴권한변경이력 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Service("comGrpMenuAuthChngService")
public class ComGrpMenuAuthChngServiceImpl extends AbstractServiceImpl implements ComGrpMenuAuthChngService {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	@Resource(name = "comGrpMenuAuthChngDao")
	private ComGrpMenuAuthChngDao comGrpMenuAuthChngDao;
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComGrpMenuAuthChngVO> selectComGrpMenuAuthChngList(ComGrpMenuAuthChngVO searchVO) {
		List<ComGrpMenuAuthChngVO> list = (List<ComGrpMenuAuthChngVO>)comGrpMenuAuthChngDao.selectComGrpMenuAuthChngList(searchVO);
//		if(list != null){
//			for(ComGrpMenuAuthChngVO result:list){
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
	public Integer selectComGrpMenuAuthChngTot(ComGrpMenuAuthChngVO searchVO) {
		return comGrpMenuAuthChngDao.selectComGrpMenuAuthChngTot(searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComGrpMenuAuthChngVO selectComGrpMenuAuthChng(ComGrpMenuAuthChngVO searchVO) {
		ComGrpMenuAuthChngVO result = comGrpMenuAuthChngDao.selectComGrpMenuAuthChng(searchVO);
		return result;
	}

	/**
	 * 추가 / 수정
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public void writeComGrpMenuAuthChng(ComGrpMenuAuthChngVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);

		validate(searchVO);
					
		comGrpMenuAuthChngDao.insertComGrpMenuAuthChng(searchVO);

	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComGrpMenuAuthChng(ComGrpMenuAuthChngVO searchVO) throws EgovBizException {
		String userId = getLoginID();
		//권한 체크
		ComGrpMenuAuthChngVO result = selectComGrpMenuAuthChng(searchVO);
		if(!isDeletable(result))throwBizException("com.error.noauth.delete");
				
		searchVO.setRegstrId(userId);
		searchVO.setUpdrId(userId);
		comGrpMenuAuthChngDao.deleteComGrpMenuAuthChng(searchVO);
	}

	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isViewable(ComGrpMenuAuthChngVO searchVO){
		return super.isViewable(searchVO);
	}

	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isModifiable(ComGrpMenuAuthChngVO searchVO){
		return super.isModifiable(searchVO);
	}

	/**
	 * 해당 데이터를 삭제할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isDeletable(ComGrpMenuAuthChngVO searchVO){
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
	private void validate(ComGrpMenuAuthChngVO searchVO){
		
	}
	// /////////////////////private,protected 메소드 선언 영역 끝
	// ///////////////////////////////////////////////////////////////////
}
