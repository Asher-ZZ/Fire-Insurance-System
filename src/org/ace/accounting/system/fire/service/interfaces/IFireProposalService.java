package org.ace.accounting.system.fire.service.interfaces;

import java.util.Date;
import java.util.List;
import org.ace.accounting.system.fire.FireProposal;
import org.ace.java.component.SystemException;

public interface IFireProposalService {

    void addNewFireProposal(FireProposal fireProposal) throws SystemException;

    void updateFireProposal(FireProposal fireProposal) throws SystemException;

    void deleteFireProposal(FireProposal fireProposal) throws SystemException;

    List<FireProposal> findAllFireProposals() throws SystemException;

    FireProposal findById(String id) throws SystemException;
    

	
	/*
	 * FireProposal findFireProposalByPolicyNo(String policyNo);
	 * 
	 * List<FireProposal> findFireProposalsByDateRange(Date startDate, Date
	 * endDate);
	 */

}
