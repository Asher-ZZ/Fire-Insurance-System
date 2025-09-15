package org.ace.accounting.system.car.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.CarStatus;
import org.ace.accounting.system.car.persistence.interfaces.ICarDAO;
import org.ace.accounting.system.car.service.interfaces.ICarService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("CarService")
public class CarService extends BaseService implements ICarService {

    @Resource(name = "CarDAO")
    private ICarDAO carDAO;

    @Transactional(propagation = Propagation.REQUIRED)
    public Car addNewCar(Car car) throws SystemException{
        try {
            carDAO.insert(car);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to insert Car", e);
        }
        return car;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Car updateCar(Car car) throws SystemException {
        try {
            carDAO.update(car);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to update Car", e);
        } 
        return car;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCar(Car car) throws SystemException{
        try {
            carDAO.delete(car);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to delete Car", e);
        }
        
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Car findById(String id)throws SystemException {
        try {
            return carDAO.findById(id);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find Car by ID", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Car> findAll()throws SystemException{
        try {
            return carDAO.findAll();
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find all Cars", e);
        }
    }

	@Override
	public List<Car> findAvailableCars() {
		return carDAO.findByStatus(CarStatus.AVAILABLE);
	}
	
	 @Transactional(readOnly = true)
	 public List<Car> searchAvailableCars(Date startDate, Date endDate, String branch, String carType) {
	        return carDAO.findAvailableCars(branch, carType, startDate, endDate);
	    }
}
