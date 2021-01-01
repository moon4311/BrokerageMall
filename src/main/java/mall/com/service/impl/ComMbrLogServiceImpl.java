package injnsobang.com.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import injnsobang.com.service.ComMbrLogService;
import injnsobang.com.vo.ComMbrLogVO;

/**
 * @Class Name : ComMbrLogServiceImpl.java
 * @Description : 로그 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020.07.21
 * @version 1.0
 * @see Copyright (C) by KMK All right reserved.
 */
@Service("comMbrLogService")
public class ComMbrLogServiceImpl extends AbstractServiceImpl implements ComMbrLogService {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	@Resource(name = "comMbrLogDao")
	private ComMbrLogDao comMbrLogDao;
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	/**
	 * 리스트
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<ComMbrLogVO> selectComMbrLogList(ComMbrLogVO searchVO) {
		List<ComMbrLogVO> list = (List<ComMbrLogVO>)comMbrLogDao.selectComMbrLogList(searchVO);
//		if(list != null){
//			for(ComMbrLogVO result:list){
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
	public Integer selectComMbrLogTot(ComMbrLogVO searchVO) {
		return comMbrLogDao.selectComMbrLogTot(searchVO);
	}

	/**
	 * Pk데이터
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public ComMbrLogVO selectComMbrLog(ComMbrLogVO searchVO) {
		ComMbrLogVO result = comMbrLogDao.selectComMbrLog(searchVO);
		return result;
	}

	/**
	 * 추가 
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public void insertComMbrLog(ComMbrLogVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);

//		validate(searchVO);

		comMbrLogDao.insertComMbrLog(searchVO);
//		String id = NullUtil.nullString(searchVO.getCsId());
//		if("".equals(id)){
//			id = selectKey();
//			searchVO.setCsId(id);
//			comMbrLogDao.insertComMbrLog(searchVO);
//		}else{
//			ComMbrLogVO result = selectComMbrLog(searchVO);
//			if(!isModifiable(result))throwBizException("com.error.noauth.modify");
//			id = result.getCsId();
//			comMbrLogDao.updateComMbrLog(searchVO);
//		}

	}

	/**
	 * 내용 수정
	 * 
	 * @param searchVO
	 * @param detailList
	 * @throws Exception
	 */
	public void updateComMbrLogCnts(ComMbrLogVO searchVO) throws EgovBizException {
		comMbrLogDao.updateComMbrLogCnts(searchVO);
	}


	/**
	 * 삭제
	 * 
	 * @param searchVO
	 * @throws Exception
	 */
	public void deleteComMbrLog(ComMbrLogVO searchVO) throws EgovBizException {
		String mbrId = getLoginID();
		//권한 체크
		ComMbrLogVO result = selectComMbrLog(searchVO);
		if(!super.isModifiable(result))throwBizException("com.error.noauth.delete");
				
		searchVO.setRegstrId(mbrId);
		searchVO.setUpdrId(mbrId);
		comMbrLogDao.deleteComMbrLog(searchVO);
	}

	/**
	 * 메뉴코드 제외 추가.
	 * 
	 * @param act
	 */
	public void insertComMbrLogEtc(HttpServletRequest request, String act)  {
		
		try{
			ComMbrLogVO searchVO = new ComMbrLogVO();
			searchVO.setMbrId(getLoginID());
			searchVO.setLogCnts(act);
			comMbrLogDao.insertComMbrLog(searchVO);
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}

	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////

	// /////////////////////private,protected 메소드 선언 영역
	// ///////////////////////////////////////////////////////////////////
	/**
	 * 벨리데이션 체크
	 * @param searchVO
	 * @throws EgovBizException
	private void validate(ComMbrLogVO searchVO) throws JobValidationException{
		if("".equals(NullUtil.nullString(searchVO.getCsName())))throw validationException("csName", "com.msg.required", new String[]{getMessage("job.cm.item.sample.csName")});
	}
	 */
	// /////////////////////private,protected 메소드 선언 영역 끝
	// ///////////////////////////////////////////////////////////////////
}
