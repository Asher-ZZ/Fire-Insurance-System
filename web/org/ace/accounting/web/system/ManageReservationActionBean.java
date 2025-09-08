package org.ace.accounting.web.system;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.common.Gender;
import org.ace.accounting.system.branch.Branch;
import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.service.interfaces.ICarService;
import org.ace.accounting.system.customer.Renter;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "ManageReservationActionBean")
@ViewScoped
public class ManageReservationActionBean extends BaseBean {

	private Car car;
	private Renter renter;
	private Reservation reservation;
	private Car selectedCar;
	private List<Car> availableCars;
	
	@ManagedProperty(value = "#{CarService}")
	private ICarService carService;
	
	public void openVehicleDialog() {
		  availableCars = carService.findAvailableCars();
	}
	
	private boolean createNew=true;
	
	
	private Gender gender;
	
	@PostConstruct
	public void init() {
	    createNewRenter();
	}
	
	private void createNewRenter() {
		this.renter = new Renter();
		
	}
	
	public void returnVehicle(SelectEvent event) {
		Car selectedCar = (Car) event.getObject();
        reservation.setCar(selectedCar);
	}
	
	

	public ICarService getCarService() {
		return carService;
	}

	public void setCarService(ICarService carService) {
		this.carService = carService;
	}

	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public boolean isCreateNew() {
		return createNew;
	}
	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public Gender[] getGenders() {
	    return Gender.values();
	}

	public Renter getRenter() {
		return renter;
	}
	public void setRenter(Renter renter) {
		this.renter = renter;
	}
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Car getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}

	public List<Car> getAvailableCars() {
		return availableCars;
	}

	public void setAvailableCars(List<Car> availableCars) {
		this.availableCars = availableCars;
	}
}
