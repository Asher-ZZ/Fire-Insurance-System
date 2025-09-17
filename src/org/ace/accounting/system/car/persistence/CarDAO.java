package org.ace.accounting.system.car.persistence;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.*;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.CarBranch;
import org.ace.accounting.system.car.enumTypes.CarStatus;
import org.ace.accounting.system.car.enumTypes.ReserveStatus;
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
	
	 @Transactional(readOnly = true)
	 public List<Car> findAvailableCars(CarBranch branch, String carType, Date startDate, Date endDate) {
		    StringBuilder hql = new StringBuilder("SELECT c FROM Car c WHERE c.carStatus = :status");

		    if (branch != null) {
		        hql.append(" AND c.carBranch = :branch");
		    }

		    if (carType != null && !carType.trim().isEmpty()) {
		        hql.append(" AND c.type = :carType");
		    }

		    if (startDate != null && endDate != null) {
		        hql.append(" AND NOT EXISTS (");
		        hql.append("   SELECT r FROM Reservation r");
		        hql.append("   WHERE r.car = c");
		        hql.append("   AND r.reserveStatus IN :excludedStatuses");
		        hql.append("   AND r.startDate <= :endDate");
		        hql.append("   AND r.endDate >= :startDate");
		        hql.append(")");
		    } else if (startDate != null) {
		        hql.append(" AND NOT EXISTS (");
		        hql.append("   SELECT r FROM Reservation r");
		        hql.append("   WHERE r.car = c");
		        hql.append("   AND r.reserveStatus IN :excludedStatuses");
		        hql.append("   AND r.endDate >= :startDate");
		        hql.append(")");
		    } else if (endDate != null) {
		        hql.append(" AND NOT EXISTS (");
		        hql.append("   SELECT r FROM Reservation r");
		        hql.append("   WHERE r.car = c");
		        hql.append("   AND r.reserveStatus IN :excludedStatuses");
		        hql.append("   AND r.startDate <= :endDate");
		        hql.append(")");
		    }

		    TypedQuery<Car> query = em.createQuery(hql.toString(), Car.class);
		    query.setParameter("status", CarStatus.AVAILABLE);

		    if (branch != null) {
		        query.setParameter("branch", branch);
		    }

		    if (carType != null && !carType.trim().isEmpty()) {
		        query.setParameter("carType", carType);
		    }

		    if (startDate != null) {
		        query.setParameter("startDate", startDate);
		    }

		    if (endDate != null) {
		        query.setParameter("endDate", endDate);
		    }

		    if (startDate != null || endDate != null) {
		        List<ReserveStatus> excludedStatuses = Arrays.asList(
		            ReserveStatus.SUBMITTED,
		            ReserveStatus.RENTED
		        );
		        query.setParameter("excludedStatuses", excludedStatuses);
		    }

		    return query.getResultList();
		}





}
