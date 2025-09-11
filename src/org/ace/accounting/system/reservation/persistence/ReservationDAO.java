package org.ace.accounting.system.reservation.persistence;

import java.util.List;


import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.CarStatus;
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
            if (reservation.getCar() != null) {
                reservation.setCar(em.getReference(reservation.getCar().getClass(), reservation.getCar().getId()));
                Car car = reservation.getCar();
                car.setCarStatus(CarStatus.RENTED);
                em.merge(car);
            }
            if (reservation.getRenter() != null) {
                reservation.setRenter(em.getReference(reservation.getRenter().getClass(), reservation.getRenter().getId()));
            }

            em.persist(reservation);

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
        try {
            reservation = em.merge(reservation);
            em.remove(reservation);
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
}
