package mall.admin.system.code.cca.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import mall.admin.system.code.cca.service.CmmnCodeManageService;
import mall.admin.system.code.cca.service.CmmnCodeVO;
import mall.com.service.impl.AbstractServiceImpl;

/**
*
* 공통코드에 대한 서비스 구현클래스를 정의한다
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

@Service("CmmnCodeManageService")
public class CmmnCodeManageServiceImpl extends AbstractServiceImpl implements CmmnCodeManageService{

    @Resource(name="CmmnCodeManageDAO")
    private CmmnCodeManageDAO cmmnCodeManageDAO;
    
	/**
	 * 공통코드 총 갯수를 조회한다.
	 */
	@Override
	public int selectCmmnCodeListTotCnt(CmmnCodeVO searchVO) throws Exception {
        return cmmnCodeManageDAO.selectCmmnCodeListTotCnt(searchVO);
	}

	/**
	 * 공통코드 목록을 조회한다.
	 */
	@Override
	public List<?> selectCmmnCodeList(CmmnCodeVO searchVO) throws Exception {
		return cmmnCodeManageDAO.selectCmmnCodeList(searchVO);
	}

	/**
	 * 공통코드 상세항목을 조회한다.
	 */
	@Override
	public CmmnCodeVO selectCmmnCodeDetail(CmmnCodeVO cmmnCodeVO) throws Exception{
		CmmnCodeVO ret = cmmnCodeManageDAO.selectCmmnCodeDetail(cmmnCodeVO);
    	return ret;
	}

	/**
	 * 공통코드를 등록/수정한다.
	 */
	@Override
	public void upsertCmmnCode(CmmnCodeVO cmmnCodeVO) throws Exception {
		String mbrId = getLoginID();
		cmmnCodeVO.setRegstrId(mbrId);
		cmmnCodeVO.setUpdrId(mbrId);
		
		if("U".equals(cmmnCodeVO.getIuMode())){
			cmmnCodeManageDAO.updateCmmnCode(cmmnCodeVO);
		}else {
			cmmnCodeManageDAO.insertCmmnCode(cmmnCodeVO);
		}
		
	}

	/**
	 * 공통코드를 삭제한다.
	 */
	@Override
	public void deleteCmmnCode(CmmnCodeVO cmmnCodeVO) throws Exception {
		cmmnCodeVO.setUpdrId(getLoginID());
		cmmnCodeManageDAO.deleteCmmnCode(cmmnCodeVO);
	}

	/**
	 * 해당 데이터를 볼 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isViewable(CmmnCodeVO searchVO){
		return super.isViewable(searchVO);
	}

	/**
	 * 해당 데이터를 수정할 수 있는 권한이 있는지 체크
	 * @param searchVO
	 * @return
	 */
	public boolean isModifiable(CmmnCodeVO searchVO){
		return super.isModifiable(searchVO);
	}
}
