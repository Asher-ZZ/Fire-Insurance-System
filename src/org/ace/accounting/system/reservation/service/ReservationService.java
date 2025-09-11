package org.ace.accounting.system.reservation.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.system.reservation.Reservation;
import org.ace.accounting.system.reservation.persistence.interfaces.IReservationDAO;
import org.ace.accounting.system.reservation.service.interfaces.IReservationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("ReservationService")
public class ReservationService implements IReservationService {

    @Resource(name = "ReservationDAO")
    private IReservationDAO reservationDAO;

    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewReservation(Reservation reservation) throws SystemException{
        try {
            reservationDAO.insert(reservation);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to insert Reservation", e);
        }
    }

    
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateReservation(Reservation reservation) throws SystemException{
        try {
            reservationDAO.update(reservation);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to update Reservation", e);
        } 
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteReservation(Reservation reservation) throws SystemException{
        try {
            reservationDAO.delete(reservation);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to delete Reservation", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Reservation findById(String id) throws SystemException{
        try {
            return reservationDAO.findById(id);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find Reservation by ID", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Reservation> findAll() throws SystemException{
        try {
            return reservationDAO.findAll();
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find all Reservations", e);
        }
    }
    
    
}
