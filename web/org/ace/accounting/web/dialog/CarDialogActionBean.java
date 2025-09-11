package org.ace.accounting.web.dialog;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.service.interfaces.ICarService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "CarDialogActionBean")
@ViewScoped
public class CarDialogActionBean extends BaseBean {

	@ManagedProperty(value = "#{CarService}")
	protected ICarService carService;

	public void setCarService(ICarService carService) {
		this.carService = carService;
	}

	private List<Car> carList;

	@PostConstruct
	public void init() {
		carList =carService.findAll();
	}

	public List<Car> getCarList() {
		return carList;
	}
	
	public void selectCar(Car car) {
		PrimeFaces.current().dialog().closeDynamic(car);
		/* RequestContext.getCurrentInstance().closeDialog(branch); */
	}

}