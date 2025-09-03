package org.ace.accounting.system.car.service.interfaces;

import java.util.List;

import org.ace.accounting.system.car.Car;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICarService {
    void addNewCar(Car car) throws DAOException;

    void updateCar(Car car) throws DAOException;

    void deleteCar(Car car) throws DAOException;

    Car findById(Long id) throws DAOException;

    List<Car> findAll() throws DAOException;
}
