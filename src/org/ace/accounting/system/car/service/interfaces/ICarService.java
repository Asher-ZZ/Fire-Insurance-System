package org.ace.accounting.system.car.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.CarBranch;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICarService {
	
    Car addNewCar(Car car) throws DAOException;

    Car updateCar(Car car) throws DAOException;

    void deleteCar(Car car) throws DAOException;

    Car findById(String id) throws DAOException;

    List<Car> findAll() throws DAOException;
	 public List<Car> searchAvailableCars(CarBranch branch, String carType,Date startDate, Date endDate); 
    List<Car> findAvailableCars() throws DAOException;
    public boolean existsByRegistrationNo( String regNo);
}
