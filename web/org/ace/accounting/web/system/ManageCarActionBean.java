package org.ace.accounting.web.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.ace.accounting.common.validation.ErrorMessage;
import org.ace.accounting.common.validation.MessageId;
import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.system.branch.Branch;
import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.CarBranch;
import org.ace.accounting.system.car.enumTypes.CarStatus;
import org.ace.accounting.system.car.enumTypes.Category;
import org.ace.accounting.system.car.service.interfaces.ICarService;
import org.ace.accounting.system.customer.Renter;
import org.ace.accounting.system.customer.service.interfaces.IRenterService;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.accounting.system.reservation.service.interfaces.IReservationService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "ManageCarActionBean")
@ViewScoped
public class ManageCarActionBean extends BaseBean {

	private Car car;
	private Renter renter;
	private Reservation reservation;
	private String location;
	private List<String> carTypes;   // dynamic car types from DB
	private String selectedCarType; 
	private java.util.Date pickupDate;
	private java.util.Date returnDate;

	private List<Car> filteredCars;

	
	private boolean createNew = true;

	@ManagedProperty(value = "#{CarService}")
	private ICarService carService;

	@ManagedProperty(value = "#{RenterService}")
	private IRenterService renterService;

	@ManagedProperty(value = "#{ReservationService}")
	private IReservationService reservationService;

	private List<Car> carList;
	private List<Car> selectedCarList;
	private List<Car> availableCars;



	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		createNewCar();
		rebindData();
        availableCars = carService.findAvailableCars();

		 prepareCarTypes();
		 filteredCars = availableCars;
	}
	
	private void prepareCarTypes() {
	    carTypes = new ArrayList<>();
	    for (Car c : carList) {
	        if (!carTypes.contains(c.getType())) {
	            carTypes.add(c.getType());
	        }
	    }
	}


	public void rebindData() {
		carList = carService.findAll();
		availableCars = carService.findAvailableCars();
	}

	public void createNewCar() {
		car = new Car();
		createNew = true;
	}

	public void addCar() {
		try {
			carService.addNewCar(car);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, car.getType());
			createNewCar();
			rebindData();
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public void deleteCar(Car car) {
		try {
			
			carService.deleteCar(car);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, car.getType());
			createNewCar();
			rebindData();
		} catch (SystemException ex) {
			handleSysException(ex);
		}

	}

	public void updateCar() {
		System.out.println("DEBUG >> Car before update: " + car);
		try {
			carService.updateCar(car);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, car.getType());
			createNewCar();
			rebindData();
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public void prepareUpdateCar(Car car) {
		this.car = car;
		this.createNew = false;
	}
	
	public void searchCars() {
	    filteredCars = new ArrayList<>();
	    for (Car c : availableCars) {
	        boolean matches = true;

	        // filter by type
	        if (selectedCarType != null && !selectedCarType.isEmpty()) {
	            if (!c.getType().equals(selectedCarType)) {
	                matches = false;
	            }
	        }

	        // filter by branch
	        if (car.getCarBranch() != null) {
	            if (!c.getCarBranch().equals(car.getCarBranch())) {
	                matches = false;
	            }
	        }

	        if (matches) {
	            filteredCars.add(c);
	        }
	    }
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

	public ICarService getCarService() {
		return carService;
	}

	public void setCarService(ICarService carService) {
		this.carService = carService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void resetCar() {
		createNewCar();
	}

	public List<Car> getCarList() {
		return carList;
	}

	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}

	public Renter gerRenter() {
		return renter;
	}

	public void setRenter(Renter renter) {
		this.renter = renter;
	}

	public CarBranch[] getCarBranches() {
		return CarBranch.values();
	}

	public Category[] getCategories() {
		return Category.values();
	}

	public CarStatus[] getCarStatuses() {
		return CarStatus.values();
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public List<Car> getAvailableCars() {
		return availableCars;
	}

	public void setAvailableCars(List<Car> availableCars) {
		this.availableCars = availableCars;
	}

	public List<Car> getSelectedCarList() {
		return selectedCarList;
	}

	public void setSelectedCarList(List<Car> selectedCarList) {
		this.selectedCarList = selectedCarList;
	}

	public List<String> getCarTypes() {
		return carTypes;
	}

	public void setCarTypes(List<String> carTypes) {
		this.carTypes = carTypes;
	}

	public String getSelectedCarType() {
		return selectedCarType;
	}

	public void setSelectedCarType(String selectedCarType) {
		this.selectedCarType = selectedCarType;
	}
	
	public IRenterService getRenterService() {
		return renterService;
	}

	public void setRenterService(IRenterService renterService) {
		this.renterService = renterService;
	}

	public IReservationService getReservationService() {
		return reservationService;
	}

	public void setReservationService(IReservationService reservationService) {
		this.reservationService = reservationService;
	}

	public Renter getRenter() {
		return renter;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public java.util.Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(java.util.Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public java.util.Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(java.util.Date returnDate) {
		this.returnDate = returnDate;
	}

	public List<Car> getFilteredCars() {
		return filteredCars;
	}

	public void setFilteredCars(List<Car> filteredCars) {
		this.filteredCars = filteredCars;
	}

}
