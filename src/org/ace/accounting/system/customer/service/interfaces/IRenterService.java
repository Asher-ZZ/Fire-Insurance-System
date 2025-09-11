package org.ace.accounting.system.customer.service.interfaces;

import java.util.List;

import org.ace.accounting.system.customer.Renter;
import org.ace.java.component.persistence.exception.DAOException;

public interface IRenterService {

    void addNewRenter(Renter renter) throws DAOException;

    void updateRenter(Renter renter) throws DAOException;

    void deleteRenter(Renter renter) throws DAOException;

    Renter findById(String id) throws DAOException;

    List<Renter> findAll() throws DAOException;
}
