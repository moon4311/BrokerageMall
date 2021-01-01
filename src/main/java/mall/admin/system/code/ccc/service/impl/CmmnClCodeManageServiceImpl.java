package egovframework.com.ccm.ccc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.ccm.ccc.service.CmmnClCodeVO;
import egovframework.com.ccm.ccc.service.CmmnClCodeManageService;
import injnsobang.com.service.impl.AbstractServiceImpl;

/**
*
* 공통분류코드에 대한 서비스 구현클래스를 정의한다
* 
* @author 공통서비스 개발팀 이중호
* @since 2009.04.01
* @version 1.0
* @see
*
*      <pre>
* << 개정이력(Modification Information) >>
* 
*   수정일      수정자           수정내용
*  -------    --------    ---------------------------
*   2009.04.01  이중호          최초 생성
*
* </pre>
*/
@Service("CmmnClCodeManageService")
public class CmmnClCodeManageServiceImpl extends AbstractServiceImpl implements CmmnClCodeManageService {
	
	@Resource(name = "CmmnClCodeManageDAO")
	private CmmnClCodeManageDAO cmmnClCodeManageDAO;
	
	/**
	 * 공통분류코드 총 갯수를 조회한다.
	 */
	@Override
	public int selectCmmnClCodeListTotCnt(CmmnClCodeVO searchVO) throws Exception {
        return cmmnClCodeManageDAO.selectCmmnClCodeListTotCnt(searchVO);
	}
	
	/**
	 * 공통분류코드 목록을 조회한다.
	 */
	@Override
	public List<?> selectCmmnClCodeList(CmmnClCodeVO searchVO) throws Exception {
        return cmmnClCodeManageDAO.selectCmmnClCodeList(searchVO);
	}
	
	/**
	 * 공통분류코드 상세항목을 조회한다.
	 */
	@Override
	public CmmnClCodeVO selectCmmnClCodeDetail(CmmnClCodeVO cmmnClCodeVO) throws Exception {
		CmmnClCodeVO ret = cmmnClCodeManageDAO.selectCmmnClCodeDetail(cmmnClCodeVO);
    	return ret;
	}
	
	/**
	 * 공통분류코드를 등록/수정 한다.
	 */
	@Override
	public void upsertCmmnClCode(CmmnClCodeVO cmmnClCodeVO) throws Exception {
		String mbrId = getLoginID();
		cmmnClCodeVO.setRegstrId(mbrId);
		cmmnClCodeVO.setUpdrId(mbrId);

		if("U".equals(cmmnClCodeVO.getIuMode())){
			cmmnClCodeManageDAO.updateCmmnClCode(cmmnClCodeVO);
		}else {
			cmmnClCodeManageDAO.insertCmmnClCode(cmmnClCodeVO);
		}
	}
	
	/**
	 * 공통분류코드를 삭제한다.
	 */
	@Override
	public void deleteCmmnClCode(CmmnClCodeVO cmmnClCodeVO) throws Exception {
		cmmnClCodeVO.setUpdrId(getLoginID());
		cmmnClCodeManageDAO.deleteCmmnClCode(cmmnClCodeVO);
	}
	
	
	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isViewable(CmmnClCodeVO searchVO){
		return super.isViewable(searchVO);
	}

	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isModifiable(CmmnClCodeVO searchVO){
		return super.isModifiable(searchVO);
	}

}
