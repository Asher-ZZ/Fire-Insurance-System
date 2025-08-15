package org.ace.accounting.system.fire.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.system.fire.FireProposal;
import org.ace.accounting.system.fire.persistence.interfaces.IFireProposalDAO;
import org.ace.accounting.system.fire.service.interfaces.IFireProposalService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "FireProposalService")
public class FireProposalService extends BaseService implements IFireProposalService {

    @Resource(name = "FireProposalDAO")
    private IFireProposalDAO fireProposalDAO;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<FireProposal> findAllFireProposals() throws SystemException {
        try {
            return fireProposalDAO.findAll();
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find all fire proposals", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewFireProposal(FireProposal fireProposal) throws SystemException {
        try {
            fireProposalDAO.insert(fireProposal);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to add new fire proposal", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateFireProposal(FireProposal fireProposal) throws SystemException {
        try {
            fireProposalDAO.update(fireProposal);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to update fire proposal", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFireProposal(FireProposal fireProposal) throws SystemException {
        try {
            fireProposalDAO.delete(fireProposal);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to delete fire proposal", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public FireProposal findById(String id) throws SystemException {
        try {
            return fireProposalDAO.findById(id);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find fire proposal by id: " + id, e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public FireProposal findFireProposalByPolicyNo(String policyNo) throws SystemException {
        try {
            return fireProposalDAO.findByPolicyNo(policyNo);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find fire proposal by policy number: " + policyNo, e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<FireProposal> findFireProposalsByDateRange(Date startDate, Date endDate) throws SystemException {
        try {
            return fireProposalDAO.findByDateRange(startDate, endDate);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find fire proposals by date range: " + startDate + " to " + endDate, e);
        }
    }
	
    
    @Override
   
    public String generateProposalNo() throws SystemException {
        LocalDate now = LocalDate.now();
        String monthYear = String.format("%02d-%d", now.getMonthValue(), now.getYear());
        String prefix = "FM/PO/";

        String lastProposalNo = fireProposalDAO.findLastProposalNoByMonthYear(monthYear);

        int nextNumber = 1;
        if (lastProposalNo != null && lastProposalNo.startsWith(prefix)) {
            String[] parts = lastProposalNo.split("/");
            if (parts.length == 4) {
                nextNumber = Integer.parseInt(parts[2]) + 1;
            }
        }

        return String.format("%s%06d/%s", prefix, nextNumber, monthYear);
    }

	
    
    
    
	/*
	 * @Transactional(propagation = Propagation.REQUIRED, readOnly = true) public
	 * FireProposal findById(String id) throws SystemException { try { return
	 * fireProposalDAO.findById(id); } catch (DAOException e) { throw new
	 * SystemException(e.getErrorCode(), "Failed to find fire proposal by id: " +
	 * id, e); } }
	 */
}
