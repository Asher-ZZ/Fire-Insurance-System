package org.ace.accounting.system.reservation.persistence;

import java.util.Date;
import java.util.List;
import java.util.*;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ace.accounting.system.car.enumTypes.ReserveStatus;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.accounting.system.reservation.persistence.interfaces.IReservationDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ReservationDAO")
public class ReservationDAO extends BasicDAO implements IReservationDAO {

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(Reservation reservation) throws DAOException{
        
    	try {
    		if (reservation.getReserveStatus() == null) {
                reservation.setReserveStatus(ReserveStatus.SUBMITTED);
            }
                em.merge(reservation);
                em.flush();
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting reservation: " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Reservation reservation) throws DAOException {
        try {
            em.merge(reservation);
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to update Reservation", pe);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Reservation reservation) throws DAOException {
        if (reservation == null || reservation.getId() == null) {
            throw new DAOException("Reservation to delete is null or has no ID.", null, null);
        }

        try {
            Reservation managedReservation = em.find(Reservation.class, reservation.getId());
            if (managedReservation == null) {
                throw new DAOException("Reservation not found with ID: " + reservation.getId(), null, null);
            }
            // Remove reservation
            em.remove(managedReservation);
            em.flush();

        } catch (PersistenceException pe) {
            throw translate("Failed to delete Reservation", pe);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Reservation findById(String id) throws DAOException {
        Reservation result = null;
        try {
            result = em.find(Reservation.class, id);
            em.flush();
        } catch (NoResultException nre) {
            return null;
        } catch (PersistenceException pe) {
            throw translate("Failed to find Reservation by ID : " + id, pe);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Reservation> findAll() throws DAOException {
        List<Reservation> result = null;
        try {
            Query q = em.createQuery("SELECT r FROM Reservation r");
            result = q.getResultList();
            em.flush();
        } catch (PersistenceException pe) {
            throw translate("Failed to find all Reservations", pe);
        }
        return result;
    }
    
    public List<Reservation> findByCriteria(Date start, Date end, String name, String carType, String status) {
        StringBuilder jpql = new StringBuilder("SELECT r FROM Reservation r WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        if (start != null) {
            jpql.append(" AND r.startDate >= :start");
            params.put("start", start);
        }

        if (end != null) {
            jpql.append(" AND r.endDate <= :end");
            params.put("end", end);
        }

        if (name != null && !name.trim().isEmpty()) {
            jpql.append(" AND LOWER(r.renter.name) LIKE :name");
            params.put("name", "%" + name.trim().toLowerCase() + "%");
        }

        if (carType != null && !carType.trim().isEmpty()) {
            jpql.append(" AND LOWER(r.car.type) LIKE :carType");
            params.put("carType", "%" + carType.trim().toLowerCase() + "%");
        }

        if (status != null && !status.trim().isEmpty()) {
            jpql.append(" AND r.reserveStatus = :status");
            params.put("status", status);
        }

        TypedQuery<Reservation> query = em.createQuery(jpql.toString(), Reservation.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }

}
