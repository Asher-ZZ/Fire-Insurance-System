package org.ace.accounting.system.car.service.interfaces;

import java.util.List;

import org.ace.accounting.system.car.Car;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICarService {
	
    Car addNewCar(Car car) throws DAOException;

    Car updateCar(Car car) throws DAOException;

    void deleteCar(Car car) throws DAOException;

    Car findById(String id) throws DAOException;

    List<Car> findAll() throws DAOException;
    
    List<Car> findAvailableCars() throws DAOException;
}
