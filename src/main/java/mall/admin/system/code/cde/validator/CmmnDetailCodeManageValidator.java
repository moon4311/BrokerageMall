package mall.admin.system.code.cde.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import mall.admin.system.code.cde.service.CmmnDetailCodeVO;
import mall.com.validator.AbstractValidator;
import mall.psnn.vo.PsnnSmplVO;

/**
 * @Class Name : CmmnDetailCodeManageValidator.java
 * @Description :  Validator
 * @Modification Information
 * 
 * @author
 * @since 2020. 10. 20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@SuppressWarnings("unused")
@Component("cmmnDetailCodeManageValidator")
public class CmmnDetailCodeManageValidator extends AbstractValidator implements Validator {
	//validate체크할  field들
	private static final String smplId = "smplId";
	private static final String smplNm = "smplNm";

	private static final String FIELDNAME_PRFIX = "psnn.item.smpl";
	@Override
	protected String getTablePrefix() {return FIELDNAME_PRFIX;}
	
	@Override
	public boolean supports(final Class<?> clazz) {

		return PsnnSmplVO.class.equals(clazz);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {

		final CmmnDetailCodeVO command = (CmmnDetailCodeVO) obj;
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,smplId, REQUIRED_FIELD, makeMessage(smplId, REQUIRED_FIELD));
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,smplNm, REQUIRED_FIELD, makeMessage(smplNm, REQUIRED_FIELD));
	}

}
