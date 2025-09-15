package org.ace.accounting.web.system;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ace.accounting.dto.ReservationDTO;
import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.service.interfaces.ICarService;
import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.FireProposal;
import org.ace.accounting.system.fire.service.interfaces.IFireProposalService;
import org.ace.accounting.system.reservation.service.interfaces.IReservationService;
import org.ace.java.component.SystemException;

@ManagedBean(name = "ReservationEnquiryBean")
@ViewScoped
public class ReservationEnquiryBean implements Serializable {

	  private static final long serialVersionUID = 1L;

	    // ---- Search criteria ----
	    private Date startDateFrom;
	    private Date endDateTo;
	    private String customerName;
	    private String selectedCarType;
	    private String selectedStatus;
	    private List<ReservationDTO> reservations;
	    private List<String> carTypes;
	    private List<Car> carList;
	@ManagedProperty(value = "#{ReservationService}")
	private IReservationService reservationService;

	@ManagedProperty(value = "#{CarService}")
	private ICarService carService;
	
	
	public ICarService getCarService() {
		return carService;
	}


	public void setCarService(ICarService carService) {
		this.carService = carService;
	}


	public IReservationService getReservationService() {
		return reservationService;
	}
	

	public void setReservationService(IReservationService reservationService) {
		this.reservationService = reservationService;
	}

	// Constructor
	public ReservationEnquiryBean() {
		
	}

	@PostConstruct
	public void init() {
		searchReservations();
		prepareCarTypes();
	}

	public List<Car> getCarList() {
		return carList;
	}

	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}

	private void prepareCarTypes() {
	    carTypes = new ArrayList<>();
	    for (Car c : carList) {
	        if (!carTypes.contains(c.getType())) {
	            carTypes.add(c.getType());
	        }
	    }
	}
	public void searchReservations() {
        reservations = reservationService.findByCriteria(
                startDateFrom,
                endDateTo,
                customerName,
                selectedCarType,
                selectedStatus
        );
    }
	
	public Date getStartDateFrom() {
		return startDateFrom;
	}

	public void setStartDateFrom(Date startDateFrom) {
		this.startDateFrom = startDateFrom;
	}

	public Date getEndDateTo() {
		return endDateTo;
	}

	public void setEndDateTo(Date endDateTo) {
		this.endDateTo = endDateTo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSelectedCarType() {
		return selectedCarType;
	}

	public void setSelectedCarType(String selectedCarType) {
		this.selectedCarType = selectedCarType;
	}

	public String getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public List<ReservationDTO> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationDTO> reservations) {
		this.reservations = reservations;
	}

	public List<String> getCarTypes() {
		return carTypes;
	}

	public void setCarTypes(List<String> carTypes) {
		this.carTypes = carTypes;
	}

	


    
}