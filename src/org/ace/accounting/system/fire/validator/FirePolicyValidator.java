package org.ace.accounting.system.fire.validator;


import org.ace.accounting.common.validation.IDataValidator;
import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.system.fire.FireProposal;
import org.springframework.stereotype.Service;

@Service(value = "FirePolicyValidator")
public class FirePolicyValidator implements IDataValidator<FireProposal> {
    @Override
    public ValidationResult validate(FireProposal fireProposal, boolean transaction) {
        ValidationResult result = new ValidationResult();
        String formId = "manageFireMigrate";

        String policyNo = fireProposal.getPolicyNumber();
        String regPattern = "^FM-\\d{2}-\\d{6}$"; // Format: MTR-25-000123

        if (policyNo == null || !policyNo.matches(regPattern)) {
            result.addErrorMessage(
                formId + ":policyNo",
                "Invalid Policy No. Format. Expected: FM-25-000123"
            );
        }

        return result;
    }
}