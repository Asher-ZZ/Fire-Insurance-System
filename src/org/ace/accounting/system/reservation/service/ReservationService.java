package org.ace.accounting.system.reservation.service;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.dto.ReservationDTO;
import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.CarStatus;
import org.ace.accounting.system.car.enumTypes.ReserveStatus;
import org.ace.accounting.system.car.persistence.interfaces.ICarDAO;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.accounting.system.reservation.persistence.interfaces.IReservationDAO;
import org.ace.accounting.system.reservation.service.interfaces.IReservationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("ReservationService")
public class ReservationService extends BaseService implements IReservationService {

    @Resource(name = "ReservationDAO")
    private IReservationDAO reservationDAO;
    
    @Resource(name = "CarDAO")
    private ICarDAO carDAO;

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
    public void deleteReservation(Reservation reservation) throws SystemException {
        try {
            if (reservation.getCar() != null) {
                Car car = reservation.getCar();
                car.setCarStatus(CarStatus.AVAILABLE); 
                carDAO.update(car); 
            }

            // Step 2: Delete the reservation
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void rejectReservation(String id, String reason) throws SystemException {
        Reservation res = findById(id);
        if (res == null) throw new SystemException("404", "Reservation not found");
        res.setReserveStatus(ReserveStatus.REJECTED);
        res.setReason(reason);
        
           
        Car car = res.getCar();
        if (car != null) {
            car.setCarStatus(CarStatus.AVAILABLE);
        }
        updateReservation(res);
    }

    
    public ByteArrayInputStream exportToExcel(List<ReservationDTO> reservations) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Reservations");

            // Date formatter
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Header row
            String[] headers = {"Customer Name", "Email", "Phone", "Car Type",
                                "Start Date", "End Date", "Total Cost","Status", "Rejection Reason"};
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowIdx = 1;
            for (ReservationDTO r : reservations) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(r.getCustomerName() != null ? r.getCustomerName() : "");
                row.createCell(1).setCellValue(r.getEmail() != null ? r.getEmail() : "");
                row.createCell(2).setCellValue(r.getPhoneNumber() != null ? r.getPhoneNumber() : "");
                row.createCell(3).setCellValue(r.getCarType() != null ? r.getCarType() : "");
                row.createCell(4).setCellValue(r.getStartDate() != null ? sdf.format(r.getStartDate()) : "");
                row.createCell(5).setCellValue(r.getEndDate() != null ? sdf.format(r.getEndDate()) : "");
                row.createCell(6).setCellValue(r.getTotalCost() != null ? sdf.format(r.getTotalCost()) : "");
                row.createCell(7).setCellValue(r.getreserveStatus() != null ? r.getreserveStatus().toString() : "");
                row.createCell(8).setCellValue(r.getReason() != null ? r.getReason() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    

    }



}
