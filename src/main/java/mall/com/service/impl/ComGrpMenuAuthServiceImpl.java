package mall.com.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.service.ComGrpMenuAuthService;
import injnsobang.com.vo.ComGrpMenuAuthChngVO;
import injnsobang.com.vo.ComGrpMenuAuthVO;

/**
 * @Class Name : ComGrpMenuAuthServiceImpl.java
 * @Description : 그룹별메뉴관리 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Service("comGrpMenuAuthService")
public class ComGrpMenuAuthServiceImpl extends AbstractServiceImpl implements ComGrpMenuAuthService {

	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	@Resource(name = "comGrpMenuAuthDao")
	private ComGrpMenuAuthDao comGrpMenuAuthDao;
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
	public List<ComGrpMenuAuthVO> selectGrpMenuAuthList(ComGrpMenuAuthVO searchVO) {
		List<ComGrpMenuAuthVO> list = (List<ComGrpMenuAuthVO>)comGrpMenuAuthDao.selectGrpMenuAuthList(searchVO);
//		if(list != null){
//			for(ComGrpMenuAuthVO result:list){
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
	public Integer selectGrpMenuAuthTot(ComGrpMenuAuthVO searchVO) {
		return comGrpMenuAuthDao.selectGrpMenuAuthTot(searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComGrpMenuAuthVO selectGrpMenuAuth(ComGrpMenuAuthVO searchVO) {
		ComGrpMenuAuthVO result = comGrpMenuAuthDao.selectGrpMenuAuth(searchVO);
		return result;
	}

	/**
	 * 추가 / 수정
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	@Transactional
	public void writeGrpMenuAuth(ComGrpMenuAuthVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);
		//기존 데이터 이력으로 처리.(trigger는 문제가 생겼을때 대처가 안되므로 직접 입력)
		ComGrpMenuAuthChngVO chngVO = new ComGrpMenuAuthChngVO();
		chngVO.setAuthGrpId(searchVO.getAuthGrpId());
		comGrpMenuAuthChngDao.insertAllComGrpMenuAuthChng(chngVO);
		
		//전체 삭제 후 추가.
		comGrpMenuAuthDao.deleteGrpMenuAuth(searchVO);		
		if(searchVO.getAuthList() != null){
			for(ComGrpMenuAuthVO authVO:searchVO.getAuthList()){
				authVO.setAuthGrpId(searchVO.getAuthGrpId());
				authVO.setRegstrId(searchVO.getRegstrId());
				authVO.setUpdrId(searchVO.getUpdrId());
				comGrpMenuAuthDao.insertGrpMenuAuth(authVO);
			}
		}

	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteGrpMenuAuth(ComGrpMenuAuthVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);
		comGrpMenuAuthDao.deleteGrpMenuAuth(searchVO);
	}

	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역
	// ///////////////////////////////////////////////////////////////////
	/**
	 * 벨리데이션 체크
	 * @param searchVO
	 * @throws EgovBizException
	private void validate(ComGrpMenuAuthVO searchVO) throws ValidationException{
		if("".equals(NullUtil.nullString(searchVO.getCmName())))throw validationException("cmName", "com.msg.required", new String[]{getMessage("job.cm.item.menu.cmName")});
		
	}
	 */
	// /////////////////////private,protected 메소드 선언 영역 끝
	// ///////////////////////////////////////////////////////////////////
}
