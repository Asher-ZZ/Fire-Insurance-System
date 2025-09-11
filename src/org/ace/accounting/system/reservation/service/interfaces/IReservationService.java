package org.ace.accounting.system.reservation.service.interfaces;

import java.util.List;

import org.ace.accounting.system.reservation.Reservation;
import org.ace.java.component.persistence.exception.DAOException;

public interface IReservationService {
    void addNewReservation(Reservation reservation) throws DAOException;

    void updateReservation(Reservation reservation) throws DAOException;

    void deleteReservation(Reservation reservation) throws DAOException;

    Reservation findById(String id) throws DAOException;

    List<Reservation> findAll() throws DAOException;
}
