package org.ace.accounting.system.fire.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.persistence.interfaces.IBuildingInfoDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("BuildingInfoDAO")
public class BuildingInfoDAO extends BasicDAO implements IBuildingInfoDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<BuildingInfo> findAll() throws DAOException {
        List<BuildingInfo> result = null;
        try {
            Query q = em.createQuery("SELECT b FROM BuildingInfo b"); // JPQL query
            result = q.getResultList();
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to find all BuildingInfo", pe);
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insert(BuildingInfo buildingInfo) throws DAOException {
        try {
            em.persist(buildingInfo);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to insert " + buildingInfo.getClass().getName(), pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BuildingInfo update(BuildingInfo buildingInfo) throws DAOException {
        try {
            buildingInfo = em.merge(buildingInfo);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to update " + buildingInfo.getClass().getName(), pe);
        }
        return buildingInfo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(BuildingInfo buildingInfo) throws DAOException {
        try {
            buildingInfo = em.merge(buildingInfo);
            em.remove(buildingInfo);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to delete " + buildingInfo.getClass().getName(), pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BuildingInfo findById(String id) throws DAOException {
        try {
            return em.find(BuildingInfo.class, id);
        } catch (PersistenceException pe) {
            throw translate("Failed to find BuildingInfo by id: " + id, pe);
        }
    }
}