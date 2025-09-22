package org.ace.accounting.system.customer.persistence.interfaces;

import java.util.List;

import org.ace.accounting.system.customer.Renter;
import org.ace.java.component.persistence.exception.DAOException;

public interface IRenterDAO {

    void insert(Renter renter) throws DAOException;

    void update(Renter renter) throws DAOException;

    void delete(Renter renter) throws DAOException;

    Renter findById(String id) throws DAOException;
    public boolean isIdNumberUnique(String idNumber) ;
    List<Renter> findAll() throws DAOException;
}
