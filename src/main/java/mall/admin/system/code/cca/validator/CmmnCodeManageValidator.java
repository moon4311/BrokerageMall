package mall.admin.system.code.cca.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import mall.admin.system.code.cca.service.CmmnCodeVO;
import mall.com.validator.AbstractValidator;

/**
 * @Class Name : CmmnCodeManageValidator.java
 * @Description :  Validator
 * @Modification Information
 * 
 * @author
 * @since 2020. 10. 20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@SuppressWarnings("unused")
@Component("cmmnCodeManageValidator")
public class CmmnCodeManageValidator extends AbstractValidator implements Validator {
	//validate체크할  field들
	private static final String clCode = "clCode";
	private static final String codeId = "codeId";
	private static final String codeIdNm = "codeIdNm";

	private static final String FIELDNAME_PRFIX = "comSymCcmCca.cmmnCodeVO";
	@Override
	protected String getTablePrefix() {return FIELDNAME_PRFIX;}
	
	@Override
	public boolean supports(final Class<?> clazz) {

		return false;
	}

	@Override
	public void validate(final Object obj, final Errors errors) {

		final CmmnCodeVO command = (CmmnCodeVO) obj;
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,clCode, REQUIRED_FIELD, makeMessage(clCode, REQUIRED_FIELD));
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,codeId, REQUIRED_FIELD, makeMessage(codeId, REQUIRED_FIELD));
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,codeIdNm, REQUIRED_FIELD, makeMessage(codeIdNm, REQUIRED_FIELD));
	}

}
