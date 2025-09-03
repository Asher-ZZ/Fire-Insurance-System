package org.ace.accounting.web.system;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.service.interfaces.ICarService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "ManageCarActionBean")
@ViewScoped
public class ManageCarActionBean extends BaseBean {
	
    private Car car;
    private boolean createNew = true;

    @ManagedProperty(value = "#{CarService}")
    private ICarService carService;
    
	private static final long serialVersionUID = 1L;
	
	 @PostConstruct
	    public void init() {
	        createNewCar();
	    }

	    public void createNewCar() {
	        car = new Car();
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

}
