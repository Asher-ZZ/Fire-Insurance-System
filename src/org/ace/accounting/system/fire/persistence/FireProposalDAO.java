package org.ace.accounting.system.fire.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.accounting.system.fire.FireProposal;
import org.ace.accounting.system.fire.persistence.interfaces.IFireProposalDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("FireProposalDAO")
public class FireProposalDAO extends BasicDAO implements IFireProposalDAO {

    @SuppressWarnings("unchecked")
    public List<FireProposal> findAll() throws DAOException {
        List<FireProposal> result = null;
        try {
            Query q = em.createQuery("SELECT f FROM FireProposal f"); // JPQL query
            result = q.getResultList();
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to find all FireProposal", pe);
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(FireProposal fireProposal) throws DAOException {
        try {
            em.persist(fireProposal);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to insert " + fireProposal.getClass().getName(), pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FireProposal update(FireProposal fireProposal) throws DAOException {
        try {
            fireProposal = em.merge(fireProposal);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to update " + fireProposal.getClass().getName(), pe);
        }
        return fireProposal;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(FireProposal fireProposal) throws DAOException {
        try {
            fireProposal = em.merge(fireProposal);
            em.remove(fireProposal);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to delete " + fireProposal.getClass().getName(), pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public FireProposal findById(String id) throws DAOException {
        try {
            return em.find(FireProposal.class, id);
        } catch (PersistenceException pe) {
            throw translate("Failed to find FireProposal by id: " + id, pe);
        }
    }
}
