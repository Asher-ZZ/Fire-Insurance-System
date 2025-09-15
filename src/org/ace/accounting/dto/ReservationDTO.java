package org.ace.accounting.dto;

import org.ace.accounting.system.car.enumTypes.ReserveStatus;
import org.ace.accounting.system.reservation.Reservation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReservationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerName;
    private String email;
    private String phoneNumber;
    private String carType;
    private Date startDate;
    private Date endDate;
    private ReserveStatus status;
    private BigDecimal totalCost;

    // --- No-arg constructor (needed for JSF / serialization) ---
    public ReservationDTO() {
    }

    // --- All-args constructor ---
    public ReservationDTO(
            String customerName,
            String email,
            String phoneNumber,
            String carType,
            Date startDate,
            Date endDate,
            ReserveStatus status,
            BigDecimal totalCost) {
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.carType = carType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.totalCost = totalCost;
    }

   

    // --- Getters & Setters ---
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ReserveStatus getStatus() {
        return status;
    }

    public void setStatus(ReserveStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
