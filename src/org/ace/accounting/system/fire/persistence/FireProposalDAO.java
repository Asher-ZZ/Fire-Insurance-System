package org.ace.accounting.system.fire.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.FireProposal;
import org.ace.accounting.system.fire.persistence.interfaces.IFireProposalDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("FireProposalDAO")
public class FireProposalDAO extends BasicDAO implements IFireProposalDAO {

    private static final Logger logger = LoggerFactory.getLogger(FireProposalDAO.class);

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<FireProposal> findAll() throws DAOException {
        List<FireProposal> result = null;
        try {
            Query q = em.createQuery("SELECT f FROM FireProposal f");
            result = q.getResultList();
            em.flush();
            logger.debug("Found {} fire proposals", result.size());
        } catch (PersistenceException pe) {
            logger.error("Failed to find all FireProposal", pe);
            throw translate("Failed to find all FireProposal", pe);
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(FireProposal fireProposal) throws DAOException {
        try {
            if (fireProposal.getBuildingList() != null) {
                for (BuildingInfo building : fireProposal.getBuildingList()) {
                    if (!em.contains(building)) {
                        em.persist(building);
                        logger.debug("Persisted BuildingInfo with ID: {}", building.getId());
                    }
                }
            }
            em.persist(fireProposal);
            em.flush();
            logger.debug("Persisted FireProposal with ID: {}", fireProposal.getId());
        } catch (PersistenceException pe) {
            logger.error("Failed to insert FireProposal", pe);
            throw translate("Failed to insert " + fireProposal.getClass().getName(), pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FireProposal update(FireProposal fireProposal) throws DAOException {
        try {
            if (fireProposal.getBuildingList() != null) {
                for (BuildingInfo building : fireProposal.getBuildingList()) {
                    if (!em.contains(building)) {
                        em.merge(building);
                        logger.debug("Merged BuildingInfo with ID: {}", building.getId());
                    }
                }
            }
            fireProposal = em.merge(fireProposal);
            em.flush();
            logger.debug("Updated FireProposal with ID: {}", fireProposal.getId());
            return fireProposal;
        } catch (PersistenceException pe) {
            logger.error("Failed to update FireProposal", pe);
            throw translate("Failed to update " + fireProposal.getClass().getName(), pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(FireProposal fireProposal) throws DAOException {
        try {
            fireProposal = em.merge(fireProposal);
            em.remove(fireProposal);
            em.flush();
            logger.debug("Deleted FireProposal with ID: {}", fireProposal.getId());
        } catch (PersistenceException pe) {
            logger.error("Failed to delete FireProposal", pe);
            throw translate("Failed to delete " + fireProposal.getClass().getName(), pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public FireProposal findById(String id) throws DAOException {
        try {
            FireProposal result = em.find(FireProposal.class, id);
            logger.debug("Found FireProposal with ID: {}", id);
            return result;
        } catch (PersistenceException pe) {
            logger.error("Failed to find FireProposal by id: " + id, pe);
            throw translate("Failed to find FireProposal by id: " + id, pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public FireProposal findByPolicyNo(String policyNo) throws DAOException {
        try {
            Query query = em.createQuery("SELECT f FROM FireProposal f WHERE f.policyNumber = :policyNo");
            query.setParameter("policyNo", policyNo);
            FireProposal result = (FireProposal) query.getSingleResult();
            logger.debug("Found FireProposal with policy number: {}", policyNo);
            return result;
        } catch (NoResultException e) {
            logger.debug("No FireProposal found with policy number: {}", policyNo);
            return null;
        } catch (PersistenceException pe) {
            logger.error("Failed to find FireProposal by policy number: {}", policyNo, pe);
            throw translate("Failed to find FireProposal by policy number: " + policyNo, pe);
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<FireProposal> findByDateRange(Date startDate, Date endDate) throws DAOException {
        try {
            Query query = em.createQuery("SELECT f FROM FireProposal f WHERE f.policyStartDate BETWEEN :startDate AND :endDate");
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            List<FireProposal> result = query.getResultList();
            logger.debug("Found {} FireProposals between {} and {}", result.size(), startDate);
            return result;
        } catch (PersistenceException pe) {
            logger.error("Failed to find FireProposals by date range: {} to {}", startDate, endDate);
            throw translate("Failed to find FireProposals by date range: " + startDate + " to " + endDate, pe);
        }
    }
}