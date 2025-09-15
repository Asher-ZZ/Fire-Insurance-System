package org.ace.accounting.system.reservation.service;


import javax.annotation.Resource;

import java.util.Date;
import java.util.List;
import java.util.*;

import org.ace.accounting.dto.ReservationDTO;
import org.ace.accounting.system.car.enumTypes.ReserveStatus;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.accounting.system.reservation.persistence.interfaces.IEnquiryReservationDAO;
import org.ace.accounting.system.reservation.persistence.interfaces.IReservationDAO;
import org.ace.accounting.system.reservation.service.interfaces.IEnquiryReservationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("EnquiryReservationService")
public class EnquiryReservationService extends BaseService implements IEnquiryReservationService {

    @Resource(name = "ReservationDAO")
    private IReservationDAO reservationDAO;

    @Resource(name = "EnquiryReservationDAO")
    private IEnquiryReservationDAO enquiryReservationDAO;
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<ReservationDTO> findByCriteria(Date startDateFrom, Date endDateTo,
                                               String customerName,
                                               String carType,
                                               String status) throws SystemException {
        try {
            return enquiryReservationDAO.findByCriteria(startDateFrom, endDateTo, customerName, carType, status);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to search reservations", e);
        }
    }


    
   



}
