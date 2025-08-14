package org.ace.accounting.system.fire.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.accounting.system.fire.FireProposal;
import org.ace.java.component.persistence.exception.DAOException;

public interface IFireProposalDAO {

    public List<FireProposal> findAll() throws DAOException;

    public void insert(FireProposal fireProposal) throws DAOException;

    public FireProposal update(FireProposal fireProposal) throws DAOException;

    public void delete(FireProposal fireProposal) throws DAOException;

    FireProposal findById(String id) throws DAOException;

    FireProposal findByPolicyNo(String policyNo) throws DAOException;

    List<FireProposal> findByDateRange(Date startDate, Date endDate) throws DAOException;

}
