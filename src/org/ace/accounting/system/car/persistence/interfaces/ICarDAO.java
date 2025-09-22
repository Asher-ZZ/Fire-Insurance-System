package org.ace.accounting.system.car.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.CarBranch;
import org.ace.accounting.system.car.enumTypes.CarStatus;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICarDAO {
	
    void insert(Car car) throws DAOException;

    void update(Car car) throws DAOException;

    void delete(Car car) throws DAOException;

    Car findById(String id) throws DAOException;

    List<Car> findAll() throws DAOException;
    public boolean registrationNoExists(String regNo);
	 public List<Car> findAvailableCars(CarBranch branch, String carType, Date startDate, Date endDate) throws DAOException ;    public List<Car> findByStatus(CarStatus status) throws DAOException;
    
}
