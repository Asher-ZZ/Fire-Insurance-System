package org.ace.accounting.web.system;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.accounting.common.Gender;
import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.service.interfaces.ICarService;
import org.ace.accounting.system.customer.Renter;
import org.ace.accounting.system.customer.service.interfaces.IRenterService;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.accounting.system.reservation.service.interfaces.IReservationService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "ManageReservationActionBean")
@ViewScoped
public class ManageReservationActionBean extends BaseBean {

	private List<Renter> renterList;

	@ManagedProperty(value = "#{RenterService}")
	private IRenterService renterService;

	@ManagedProperty(value = "#{CarService}")
	private ICarService carService;

	@ManagedProperty(value = "#{ReservationService}")
	private IReservationService reservationService;
	
	private boolean createNew = true;
	private Car car;
	private Renter renter;
	private Reservation reservation=new Reservation();
	private Car selectedCar = new Car();
	private Car selectCar;
	private List<Car> availableCars;
	private List<Car> carList;
	private List<Reservation> reservationList;
	private List<Reservation> reserveList;
	private Double totalBaseRate;
	
	 public void calculateTotalCost() {
	        Date startDate = reservation.getStartDate();
	        Date endDate = reservation.getEndDate();

	        if (selectedCar != null) {
	            reservation.setDailyRate(selectedCar.getBaseRate()); 
	        }

	        if (startDate != null && endDate != null && !endDate.before(startDate)) {
	            long diffInMillies = endDate.getTime() - startDate.getTime();
	            long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

	            // Inclusive of both start & end date
	            days = days + 1;

	            double total = days * reservation.getDailyRate();
	            reservation.setTotalCost(total);
	        } else {
	            reservation.setTotalCost(0.0);
	        }
	    }


	public void openVehicleDialog() {
		availableCars = carService.findAvailableCars();
	}

	@PostConstruct
	public void init() {
		
		createNewRenter();
		createNewReservation();
		Object obj = FacesContext.getCurrentInstance()
                .getExternalContext().getFlash().get("selectedCar");

   if (obj instanceof Car) {
       Car selectedCar = (Car) obj;
       reservation.setCar(selectedCar); // ✅ will not be overwritten now
       System.out.println("DEBUG - Car received from Flash: " + selectedCar.getType());
   } else {
       System.out.println("DEBUG - No car found in Flash.");
   }
		reservationList = new ArrayList<>();
		carList = carService.findAll();
		renterList=renterService.findAll();
        reserveList = reservationService.findAll();
        loadReservations();
	}
	
	public void loadReservations() {
	    reserveList = reservationService.findAll(); // load fresh from DB
	}

	private void createNewReservation() {
		createNew = true;
		reservation = new Reservation();

	}
	
	 public void setSelectedCarForReservation(Car car) {
	        reservation.setCar(car);
	    }

	private void createNewRenter() {
		this.renter = new Renter();
	}
	

	public void addReservation() {
		 if (reservation.getCar() == null || reservation.getRenter()== null) {
		        FacesContext.getCurrentInstance().addMessage(null,
		            new FacesMessage(FacesMessage.SEVERITY_WARN, "Please select a car and enter renter info!", ""));
		        return;
		    }
		Reservation temp = new Reservation();
		temp.setRenter(reservation.getRenter());
		temp.setCar(reservation.getCar());
		temp.setStartDate(reservation.getStartDate());
		temp.setEndDate(reservation.getEndDate());
		  if (reservation.getCar() != null) {
		        temp.setDailyRate(reservation.getCar().getBaseRate());
		    }

		    // ✅ Calculate totalCost
		    if (temp.getStartDate() != null && temp.getEndDate() != null && !temp.getEndDate().before(temp.getStartDate())) {
		        long diffInMillies = temp.getEndDate().getTime() - temp.getStartDate().getTime();
		        long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		        // inclusive
		        days = days + 1;

		        double total = days * temp.getDailyRate();
		        temp.setTotalCost(total);
		    } else {
		        temp.setTotalCost(0.0);
		    }
		reservationList.add(temp);
		createNewReservation();
			
	}

	public void saveReservations() {
		if (reservationList == null || reservationList.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No reservations to save!", ""));
			return;
		}
		
		try {
			for (Reservation r : reservationList) {
				reservationService.addNewReservation(r); // Renter will also persist
			}
			reservationList.clear();
			createNewReservation();
			loadReservations();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Reservations saved successfully!", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error saving reservations!", e.getMessage()));
			e.printStackTrace();
		}
	}

	public void resetForm() {
		renter = new Renter();
		reservation = new Reservation();
	}

	public void editReservation(Reservation r) {
		// Option 1: copy data to the form for editing
		this.reservation = r;
		this.renter = r.getRenter();
	}

	
	public void deleteReservation(Reservation r) {
		reservationList.remove(r);
		reservation = new Reservation();

	}
	
	public void deleteReservationFromDB(Reservation reservationToDelete) {
	    try {
	        reservationService.deleteReservation(reservationToDelete); // service layer handles JPA delete
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_INFO, "Reservation deleted successfully!", ""));
	        createNewReservation();
	        loadReservations();
	    } catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error deleting reservation!", e.getMessage()));
	        e.printStackTrace();
	    }
	   
	}
	
	public void returnCar(SelectEvent event) {
		Car car = (Car) event.getObject();
		reservation.setCar(car);
	}
	
	public void returnRenter(SelectEvent event) {
		Renter renter = (Renter) event.getObject();
		reservation.setRenter(renter);
	}
	
	public void approve(Reservation res) {
	    reservationService.approveReservation(res.getId());
	    FacesContext.getCurrentInstance().addMessage(null,
	        new FacesMessage("Reservation approved"));
	}

	public void reject(Reservation res) {
	    reservationService.rejectReservation(res.getId());
	    FacesContext.getCurrentInstance().addMessage(null,
	        new FacesMessage("Reservation rejected"));
	}


	public List<Reservation> getReserveList() {return reserveList;}
	public void setReserveList(List<Reservation> reserveList) {this.reserveList = reserveList;}
	public ICarService getCarService() {return carService;}
	public void setCarService(ICarService carService) {this.carService = carService;}
	public Car getCar() {return car;}
	public void setCar(Car car) {this.car = car;}
	public boolean isCreateNew() {return createNew;}
	public void setCreateNew(boolean createNew) {this.createNew = createNew;}
	public Renter getRenter() {return renter;}
	public void setRenter(Renter renter) {this.renter = renter;}
	public Reservation getReservation() {return reservation;}
	public void setReservation(Reservation reservation) {this.reservation = reservation;}
	public Car getSelectedCar() {return selectedCar;}
	public void setSelectedCar(Car selectedCar) {this.selectedCar = selectedCar;
	if (reservation == null) {
		reservation = new Reservation();
	}
	reservation.setCar(selectedCar);

	System.out.println("Selected Car in Reservation: " + selectedCar.getType());}
	public List<Car> getAvailableCars() {return availableCars;}
	public void setAvailableCars(List<Car> availableCars) {this.availableCars = availableCars;}
	public List<Car> getCarList() {return carList;}
	public void setCarList(List<Car> carList) {this.carList = carList;}
	public List<Reservation> getReservationList() {return reservationList;}
	public void setReservationList(List<Reservation> reservationList) {this.reservationList = reservationList;}
	public IRenterService getRenterService() {return renterService;}
	public void setRenterService(IRenterService renterService) {this.renterService = renterService;}
	public List<Renter> getRenterList() {return renterList;}
	public void setRenterList(List<Renter> renterList) {this.renterList = renterList;}
	public Double getTotalBaseRate() {return totalBaseRate;}
	public void setTotalBaseRate(Double totalBaseRate) {this.totalBaseRate = totalBaseRate;}
	public IReservationService getReservationService() {return reservationService;}
	public void setReservationService(IReservationService reservationService) {this.reservationService = reservationService;}

	public Car getSelectCar() {
		return selectCar;
	}

	public void setSelectCar(Car selectCar) {
		this.selectCar = selectCar;
	}
}
