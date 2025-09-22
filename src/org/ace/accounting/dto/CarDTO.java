package org.ace.accounting.dto;

import org.ace.accounting.system.car.enumTypes.ReserveStatus;
import org.ace.accounting.system.reservation.Reservation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CarDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
	private String customerName;
    private String email;
    private String phoneNumber;
    private String carType;
    private Date startDate;
    private Date endDate;
    private ReserveStatus reserveStatus;
    private Double totalCost;
    private String reason;
    
    public CarDTO() {
    }

    public CarDTO(
    		String id,
            String customerName,
            String email,
            String phoneNumber,
            String carType,
            Date startDate,
            Date endDate,
            ReserveStatus reserveStatus,
            Double totalCost,
            String reason) {
    	this.id=id;
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.carType = carType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reserveStatus = reserveStatus;
        this.totalCost = totalCost;
        this.reason=reason;
        }

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

    public ReserveStatus getreserveStatus() {
        return reserveStatus;
    }

    public void setStatus(ReserveStatus reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
    
    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}
}

