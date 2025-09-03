package org.ace.accounting.system.car.persistence.interfaces;

import java.util.List;

import org.ace.accounting.system.car.Car;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICarDAO {
    void insert(Car car) throws DAOException;

    void update(Car car) throws DAOException;

    void delete(Car car) throws DAOException;

    Car findById(Long id) throws DAOException;

    List<Car> findAll() throws DAOException;
}
