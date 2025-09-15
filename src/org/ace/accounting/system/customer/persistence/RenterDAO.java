package org.ace.accounting.system.customer.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.ace.accounting.system.customer.Renter;
import org.ace.accounting.system.customer.persistence.interfaces.IRenterDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("RenterDAO")
public class RenterDAO extends BasicDAO implements IRenterDAO {

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(Renter renter) throws DAOException {
        try {
            em.persist(renter);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to insert renter", pe);
        }
    } 

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Renter renter) throws DAOException {
        try {
            em.merge(renter);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to update renter", pe);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Renter renter) throws DAOException {
        try {
            renter = em.merge(renter);
            em.remove(renter);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to delete renter", pe);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Renter findById(String id) throws DAOException {
        Renter result = null;
        try {
            result = em.find(Renter.class, id);
            em.flush();
        } catch (NoResultException ne) {
            return null;
        } catch (PersistenceException pe) {
            throw translate("Failed to find Renter by ID " + id, pe);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Renter> findAll() throws DAOException {
        List<Renter> result = null;
        try {
            Query q = em.createQuery("SELECT c FROM Renter c");
            result = q.getResultList();
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to find all Renter", pe);
        }
        return result;
    }
}
