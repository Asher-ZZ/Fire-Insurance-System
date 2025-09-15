package org.ace.accounting.system.reservation.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.accounting.dto.ReservationDTO;
import org.ace.java.component.SystemException;


public interface IEnquiryReservationService {
	
	public List<ReservationDTO> findByCriteria(Date startDateFrom, Date endDateTo,
            String customerName,
            String carType,
            String status) throws SystemException;
}
