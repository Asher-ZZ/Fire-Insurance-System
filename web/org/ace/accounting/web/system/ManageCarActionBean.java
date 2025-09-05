package org.ace.accounting.web.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
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
    
    private boolean createNew = true;
    
    @ManagedProperty(value = "#{CarService}")
    private ICarService carService;
    
    @ManagedProperty(value = "#{RenterService}")
    private IRenterService renterService;

    @ManagedProperty(value = "#{ReservationService}")
    private IReservationService reservationService;

    private List<Car> carList;
    private List<Car> availableCars;
    
    
    
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

	private static final long serialVersionUID = 1L;
	
	 @PostConstruct
	    public void init() {
	        createNewCar();
	        createNewRenter();
	        createNewReservation();
	        rebindData();
	    }
	 
	 public void rebindData() {
			carList = carService.findAll();
		} 

	 
	    public void createNewCar() {
	        car = new Car();
	        createNew = true;
	    }
	    

	    public void createNewRenter() {
	        renter = new Renter();
	        createNew = true;
	    }
	    

	    public void createNewReservation() {
	        reservation = new Reservation();
	        createNew = true;
	    }
	    

	    public void saveCar() {
	        try {
	            if (createNew) {
	                carService.addNewCar(car);
	                FacesContext.getCurrentInstance().addMessage(null, 
	                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Car added successfully"));
	            } else {
	                carService.updateCar(car);
	                FacesContext.getCurrentInstance().addMessage(null, 
	                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Car updated successfully"));
	            }
	            createNewCar();
	        } catch (SystemException e) {
	            FacesContext.getCurrentInstance().addMessage(null, 
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
	        }
	    }

	    public void saveRenter() {
	        try {
	            renterService.addNewRenter(renter);
	            addInfoMessage(null, "Success", "Renter registered successfully");
	        } catch (SystemException e) {
	            addErrorMessage(null, e.getMessage());
	        }
	    }
	    
		/*
		 * public void saveReservation() { try { // Ensure renter is saved first if
		 * (renter.getId() == null) { saveRenter(); }
		 * 
		 * reservation.setRenter(renter);
		 * 
		 * // Set the selected car Car selectedCar =
		 * carService.findCarById(reservation.getCar().getCarId());
		 * reservation.setCar(selectedCar);
		 * 
		 * // Save reservation reservationService.addNewReservation(reservation);
		 * 
		 * // Mark car as unavailable selectedCar.setAvailable(false);
		 * carService.updateCar(selectedCar);
		 * 
		 * addInfoMessage(null, "Success", "Reservation created successfully");
		 * 
		 * createNewRenter(); createNewReservation(); ;
		 * 
		 * } catch (SystemException e) { addErrorMessage(null, e.getMessage()); } }
		 */

		
	    public String deleteCar(Car car) {
			
				try {
					carService.deleteCar(car);
					addInfoMessage(null, MessageId.DELETE_SUCCESS, car.getType());
				} catch (SystemException ex) {
					handleSysException(ex);
				}
			 
			createNewCar();
			rebindData();
			return null;
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
}
