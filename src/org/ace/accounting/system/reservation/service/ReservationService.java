package org.ace.accounting.system.reservation.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.ace.accounting.dto.ReservationDTO;
import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.CarStatus;
import org.ace.accounting.system.car.enumTypes.ReserveStatus;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.accounting.system.reservation.persistence.interfaces.IReservationDAO;
import org.ace.accounting.system.reservation.service.interfaces.IReservationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("ReservationService")
public class ReservationService extends BaseService implements IReservationService {

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
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void approveReservation(String id) throws SystemException {
        Reservation res = findById(id);
        if (res == null) throw new SystemException("404", "Reservation not found");

        res.setReserveStatus(ReserveStatus.APPROVED);

        Car car = res.getCar();
        if (car != null) {
            car.setCarStatus(CarStatus.RENTED);
        }
        updateReservation(res);
    }

    public void rejectReservation(String id) throws SystemException {
        Reservation res = findById(id);
        if (res == null) throw new SystemException("404", "Reservation not found");

        res.setReserveStatus(ReserveStatus.REJECTED);

        Car car = res.getCar();
        if (car != null) {
            car.setCarStatus(CarStatus.AVAILABLE);
        }
        updateReservation(res);
    }
    
    
    @Transactional(readOnly = true)
    public List<ReservationDTO> findByCriteria(Date start, Date end,
            String name,
            String carType,
            String status) {
List<Reservation> entities =
reservationDAO.findByCriteria(start, end, name, carType, status);

return entities.stream()
.map(res -> new ReservationDTO(
res.getRenter() != null ? res.getRenter().getName() : "",
res.getRenter() != null ? res.getRenter().getEmail() : "",
res.getRenter() != null ? res.getRenter().getPhoneNumber() : "",
res.getCar() != null ? res.getCar().getType() : "",
res.getStartDate(),
res.getEndDate(),
res.getReserveStatus(),
res.getTotalCost() != null
? BigDecimal.valueOf(res.getTotalCost())
: BigDecimal.ZERO
))
.collect(Collectors.toList());
}



}
