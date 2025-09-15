package org.ace.accounting.system.reservation.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.accounting.dto.ReservationDTO;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;

public interface IReservationService {
    void addNewReservation(Reservation reservation) throws DAOException;

    void updateReservation(Reservation reservation) throws DAOException;

    void deleteReservation(Reservation reservation) throws DAOException;
    public void rejectReservation(String id) throws SystemException;
    public void approveReservation(String id) throws SystemException;
    Reservation findById(String id) throws DAOException;
    public List<ReservationDTO> findByCriteria(Date start, Date end, String name, String carType, String status); 
    List<Reservation> findAll() throws DAOException;
}
