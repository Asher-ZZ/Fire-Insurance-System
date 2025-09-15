package org.ace.accounting.system.reservation.persistence.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ace.accounting.dto.ReservationDTO;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.java.component.persistence.exception.DAOException;

public interface IEnquiryReservationDAO {
	
	 public List<ReservationDTO> findByCriteria(Date startDateFrom, Date endDateTo,
             String customerName,
             String carType,
             String status)throws DAOException;
	 
}
