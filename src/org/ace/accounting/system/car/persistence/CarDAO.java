package org.ace.accounting.system.car.persistence;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.CarStatus;
import org.ace.accounting.system.car.persistence.interfaces.ICarDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.interfaces.IDataRepService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CarDAO")
public class CarDAO extends BasicDAO implements ICarDAO {

    @Resource(name = "DataRepService")
    private IDataRepService<Car> carDataRepService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(Car car) throws DAOException {
        try {
        	if (car.getCarStatus() == null) {
                car.setCarStatus(CarStatus.AVAILABLE); // default
            }
            em.persist(car);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to insert Car", pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Car car) throws DAOException {
        try {
            em.merge(car);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to update Car", pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Car car) throws DAOException {
        try {
            car = em.merge(car);
            em.remove(car);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to delete Car", pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Car findById(String id) throws DAOException {
        Car result = null;
        try {
            result = em.find(Car.class, id);
            em.flush();
        } catch (NoResultException nre) {
            return null;
        } catch (PersistenceException pe) {
            throw translate("Failed to find Car by ID : " + id, pe);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Car> findAll() throws DAOException {
        List<Car> result = null;
        try {
            Query q = em.createQuery("SELECT c FROM Car c");
            result = q.getResultList();
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to find all Cars", pe);
        }
        return result;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Car> findByStatus(CarStatus status) throws DAOException {
		 Query q = em.createQuery("SELECT c FROM Car c WHERE c.carStatus = :status");
		    q.setParameter("status", status);
		    return q.getResultList();
	}
}
