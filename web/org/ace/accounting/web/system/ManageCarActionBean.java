package org.ace.accounting.web.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
	private List<String> carTypes; // dynamic car types from DB
	private String selectedCarType;
	private Date startDate;
	private Date endDate;
	private Car selectedCar;
	private List<Car> carList;
	private List<Car> selectedCarList;
	private List<Car> availableCars;
	private List<Car> filteredCars;
	private CarBranch branch;
	private List<Car> availableCar;
	private List<Reservation> reservations;

	private boolean createNew = true;

	@ManagedProperty(value = "#{CarService}")
	private ICarService carService;

	@ManagedProperty(value = "#{RenterService}")
	private IRenterService renterService;

	@ManagedProperty(value = "#{ReservationService}")
	private IReservationService reservationService;


	@ManagedProperty("#{ManageReservationActionBean}")
    private ManageReservationActionBean reservationBean;


	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		createNewCar();
		rebindData();
		availableCars = carService.findAvailableCars();
		prepareCarTypes();
		filteredCars = availableCars;
		reservations = reservationService.findAll();

	}

	private void prepareCarTypes() {
		carTypes = new ArrayList<>();
		for (Car c : carList) {
			if (!carTypes.contains(c.getType())) {
				carTypes.add(c.getType());
			}
		}
	}

	public String prepareRent(Car selectedCar) {
	    if (selectedCar != null) {
	        System.out.println("Prepare rent: selectedCar type = " + selectedCar);
	        FacesContext.getCurrentInstance().getExternalContext()
	                    .getFlash().put("selectedCar", selectedCar);
	        return "ManageCustomerReservation.xhtml?faces-redirect=true";
	    }
	    return null;
	}

	public void searchCars() {
		availableCar = carService.searchAvailableCars(car.getCarBranch(), selectedCarType, startDate, endDate);
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

	public List<Car> getFilteredCars() {
		return filteredCars;
	}

	public void setFilteredCars(List<Car> filteredCars) {
		this.filteredCars = filteredCars;
	}

	public List<Car> getAvailableCar() {
		return availableCar;
	}

	public void setAvailableCar(List<Car> availableCar) {
		this.availableCar = availableCar;
	}

	public CarBranch getBranch() {
		return branch;
	}

	public void setBranch(CarBranch branch) {
		this.branch = branch;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	

	public Car getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}

	public void setReservationBean(ManageReservationActionBean reservationBean) {
        this.reservationBean = reservationBean;
    }
}
