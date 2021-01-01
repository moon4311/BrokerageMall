package mall.com.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 *  기본 DAO 클래스
 * @author 
 * @since 2020.07.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2020.07.21            최초 생성
 *
 * </pre>
 */
public abstract class AbstractDao extends EgovComAbstractDAO{
	protected final Log log = LogFactory.getLog(getClass());

	public Object select(String queryId, Object parameterObject) {
		return selectOne(queryId, parameterObject);
	}

}
