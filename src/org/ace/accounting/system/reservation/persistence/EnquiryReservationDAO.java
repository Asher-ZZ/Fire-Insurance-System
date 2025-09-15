package org.ace.accounting.system.reservation.persistence;


import java.util.*;
import javax.persistence.PersistenceException;

import javax.persistence.TypedQuery;

import org.ace.accounting.dto.ReservationDTO;
import org.ace.accounting.system.car.enumTypes.ReserveStatus;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.accounting.system.reservation.persistence.interfaces.IEnquiryReservationDAO;

import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository("EnquiryReservationDAO")
public class EnquiryReservationDAO extends BasicDAO implements IEnquiryReservationDAO {

	 @Transactional(propagation = Propagation.REQUIRED)
	    public List<ReservationDTO> findByCriteria(Date startDateFrom, Date endDateTo,
	                                               String customerName,
	                                               String carType,
	                                               String status) throws DAOException {
	        Map<String, Object> paramMap = new HashMap<>();

	        StringBuilder hql = new StringBuilder(
	            "SELECT new org.ace.accounting.dto.ReservationDTO(" +
	            "r.id, " +
	            "cr.name, " +
	            "cr.email, " +
	            "cr.phoneNumber, " +
	            "c.type, " +
	            "r.startDate, " +
	            "r.endDate, " +
	            "r.reserveStatus, " +
	            "r.totalCost) " +
	            "FROM Reservation r " +
	            "JOIN r.renter cr " +
	            "JOIN r.car c " +
	            "WHERE 1=1"
	        );

	        if (startDateFrom != null) {
	            hql.append(" AND r.startDate >= :startDateFrom");
	            paramMap.put("startDateFrom", startDateFrom);
	        }
	        if (endDateTo != null) {
	            hql.append(" AND r.endDate <= :endDateTo");
	            paramMap.put("endDateTo", endDateTo);
	        }
	        if (customerName != null && !customerName.trim().isEmpty()) {
	            hql.append(" AND LOWER(cr.name) LIKE :customerName");
	            paramMap.put("customerName", "%" + customerName.trim().toLowerCase() + "%");
	        }
	        if (carType != null && !carType.trim().isEmpty()) {
	            hql.append(" AND c.type = :carType");
	            paramMap.put("carType", carType);
	        }
	        if (status != null && !status.trim().isEmpty()) {
	            hql.append(" AND r.reserveStatus = :status");
	            paramMap.put("status", ReserveStatus.valueOf(status));
	        }

	        TypedQuery<ReservationDTO> query = em.createQuery(hql.toString(), ReservationDTO.class);
	        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
	            query.setParameter(entry.getKey(), entry.getValue());
	        }

	        return query.getResultList();
	    }

}
