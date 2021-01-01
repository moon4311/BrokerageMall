package egovframework.com.ccm.ccc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import egovframework.com.ccm.ccc.service.CmmnClCodeVO;
import injnsobang.com.validator.AbstractValidator;
import injnsobang.psnn.vo.PsnnSmplVO;

/**
 * @Class Name : CmmnClCodeManageValidator.java
 * @Description :  Validator
 * @Modification Information
 * 
 * @author
 * @since 2020. 10. 20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@SuppressWarnings("unused")
@Component("cmmnClCodeManageValidator")
public class CmmnClCodeManageValidator extends AbstractValidator implements Validator {
	//validate체크할  field들
	private static final String clCode = "clCode";

	private static final String FIELDNAME_PRFIX = "comSymCcmCcc.cmmnClCodeVO";
	@Override
	protected String getTablePrefix() {return FIELDNAME_PRFIX;}
	
	@Override
	public boolean supports(final Class<?> clazz) {

		return PsnnSmplVO.class.equals(clazz);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {

		final CmmnClCodeVO command = (CmmnClCodeVO) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,clCode, REQUIRED_FIELD, makeMessage(clCode, REQUIRED_FIELD));
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,smplId, REQUIRED_FIELD, makeMessage(smplId, REQUIRED_FIELD));
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,smplNm, REQUIRED_FIELD, makeMessage(smplNm, REQUIRED_FIELD));
	}

}
