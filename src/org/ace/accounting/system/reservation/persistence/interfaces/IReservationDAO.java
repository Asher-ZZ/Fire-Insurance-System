package org.ace.accounting.system.reservation.persistence.interfaces;

import java.util.Date;
import java.util.List;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.java.component.persistence.exception.DAOException;

public interface IReservationDAO {
    void insert(Reservation reservation) throws DAOException;

    void update(Reservation reservation) throws DAOException;

    void delete(Reservation reservation) throws DAOException;
    
    Reservation findById(String id) throws DAOException;
    

    List<Reservation> findAll() throws DAOException;
}
