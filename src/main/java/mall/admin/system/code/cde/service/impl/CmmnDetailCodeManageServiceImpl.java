package mall.admin.system.code.cde.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import mall.admin.system.code.cde.service.CmmnDetailCodeManageService;
import mall.admin.system.code.cde.service.CmmnDetailCodeVO;
import mall.com.service.impl.AbstractServiceImpl;

/**
*
* 공통상세코드에 대한 서비스 구현클래스를 정의한다
* @author 공통서비스 개발팀 이중호
* @since 2009.04.01
* @version 1.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*   수정일      수정자           수정내용
*  -------    --------    ---------------------------
*   2009.04.01  이중호          최초 생성
*
* </pre>
*/
@Service("CmmnDetailCodeManageService")
public class CmmnDetailCodeManageServiceImpl extends AbstractServiceImpl implements CmmnDetailCodeManageService{
	
    @Resource(name="CmmnDetailCodeManageDAO")
    private CmmnDetailCodeManageDAO cmmnDetailCodeManageDAO;
    
	/**
	 * 공통상세코드 총 갯수를 조회한다.
	 */
	@Override
	public int selectCmmnDetailCodeListTotCnt(CmmnDetailCodeVO searchVO) throws Exception {
        return cmmnDetailCodeManageDAO.selectCmmnDetailCodeListTotCnt(searchVO);
	}
	
	/**
	 * 공통상세코드 목록을 조회한다.
	 */
	@Override
	public List<CmmnDetailCodeVO> selectCmmnDetailCodeList(CmmnDetailCodeVO searchVO) throws Exception {
        return cmmnDetailCodeManageDAO.selectCmmnDetailCodeList(searchVO);
	}
	

	/**
	 * 공통상세코드 상세항목을 조회한다.
	 * @throws Exception 
	 */
	@Override
	public CmmnDetailCodeVO selectCmmnDetailCodeDetail(CmmnDetailCodeVO cmmnDetailCodeVO) throws Exception {
		CmmnDetailCodeVO ret = cmmnDetailCodeManageDAO.selectCmmnDetailCodeDetail(cmmnDetailCodeVO);
    	return ret;
	}

	/**
	 * 공통상세코드를 삭제한다.
	 * @throws Exception 
	 */
	@Override
	public void deleteCmmnDetailCode(CmmnDetailCodeVO cmmnDetailCodeVO) throws Exception {
		cmmnDetailCodeManageDAO.deleteCmmnDetailCode(cmmnDetailCodeVO);
		
	}

	/**
	 * 공통상세코드를 등록/수정한다.
	 */
	@Override
	public void upsertCmmnDetailCode(CmmnDetailCodeVO cmmnDetailCodeVO) throws Exception {
		String mbrId = getLoginID();
		String iuMode = cmmnDetailCodeVO.getIuMode();
		
		cmmnDetailCodeVO.setRegstrId(mbrId);
		cmmnDetailCodeVO.setUpdrId(mbrId);
		
		if("U".equals(iuMode)){
			cmmnDetailCodeManageDAO.updateCmmnDetailCode(cmmnDetailCodeVO);
		}else {
			cmmnDetailCodeManageDAO.insertCmmnDetailCode(cmmnDetailCodeVO);
		}
	}
	
}
