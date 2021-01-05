package mall.com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import mall.com.service.ComIndvlzMenuAuthService;
import mall.com.vo.ComIndvlzMenuAuthVO;

/**
 * @Class Name : ComIndvlzMenuAuthServiceImpl.java
 * @Description : 사용자별메뉴관리 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Service("comIndvlzMenuAuthService")
public class ComIndvlzMenuAuthServiceImpl extends AbstractServiceImpl implements ComIndvlzMenuAuthService {

	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	@Resource(name = "comIndvlzMenuAuthDao")
	private ComIndvlzMenuAuthDao comIndvlzMenuAuthDao;
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComIndvlzMenuAuthVO> selectIndvlzMenuAuthList(ComIndvlzMenuAuthVO searchVO) {
		List<ComIndvlzMenuAuthVO> list = (List<ComIndvlzMenuAuthVO>)comIndvlzMenuAuthDao.selectIndvlzMenuAuthList(searchVO);
//		if(list != null){
//			for(ComIndvlzMenuAuthVO result:list){
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
	public Integer selectIndvlzMenuAuthTot(ComIndvlzMenuAuthVO searchVO) {
		return comIndvlzMenuAuthDao.selectIndvlzMenuAuthTot(searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComIndvlzMenuAuthVO selectIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO) {
		ComIndvlzMenuAuthVO result = comIndvlzMenuAuthDao.selectIndvlzMenuAuth(searchVO);
		return result;
	}

	/**
	 * 키가져오기
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectKey() {
		return comIndvlzMenuAuthDao.selectKey();
	}

	/**
	 * 추가 / 수정
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public void writeIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);
		
		//전체 삭제 후 추가.
		comIndvlzMenuAuthDao.deleteIndvlzMenuAuth(searchVO);		
		if(searchVO.getAuthList() != null){
			for(ComIndvlzMenuAuthVO authVO:searchVO.getAuthList()){
				authVO.setMbrId(searchVO.getMbrId());
				authVO.setRegstrId(searchVO.getRegstrId());
				authVO.setUpdrId(searchVO.getUpdrId());
				comIndvlzMenuAuthDao.insertIndvlzMenuAuth(authVO);
			}
		}

	}

	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteIndvlzMenuAuth(ComIndvlzMenuAuthVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);
//		comIndvlzMenuAuthDao.deleteIndvlzMenuAuth(searchVO);
		
		comIndvlzMenuAuthDao.deleteIndvlzMenuAuth(searchVO);
	}

	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역
	// ///////////////////////////////////////////////////////////////////
	/**
	 * 벨리데이션 체크
	 * @param searchVO
	 * @throws EgovBizException
	private void validate(ComIndvlzMenuAuthVO searchVO) throws ValidationException{
		if("".equals(NullUtil.nullString(searchVO.getCmName())))throw validationException("cmName", "com.msg.required", new String[]{getMessage("job.cm.item.menu.cmName")});
		
	}
	 */
	// /////////////////////private,protected 메소드 선언 영역 끝
	// ///////////////////////////////////////////////////////////////////
}
