package org.ace.accounting.system.customer.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.system.customer.Renter;
import org.ace.accounting.system.customer.persistence.interfaces.IRenterDAO;
import org.ace.accounting.system.customer.service.interfaces.IRenterService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("RenterService")
public class RenterService extends BaseService implements IRenterService {

    @Resource(name = "RenterDAO")
    private IRenterDAO renterDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewRenter(Renter renter) throws DAOException {
        try {
            renterDAO.insert(renter);
        } catch (DAOException e) {
        	throw new SystemException(e.getErrorCode(), "Failed to add new renter)", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRenter(Renter renter) throws DAOException {
        try {
        	renterDAO.update(renter);
        } catch (DAOException e) {
        	throw new SystemException(e.getErrorCode(), "Failed to update renter)", e);
            
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRenter(Renter renter) throws DAOException {
        try {
            renterDAO.delete(renter);
        } catch (DAOException e) {
        	throw new SystemException(e.getErrorCode(), "Failed to delete renter)", e);
          
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Renter findById(String id) throws DAOException {
        try {
            return renterDAO.findById(id);
        } catch (DAOException e) {
        	throw new SystemException(e.getErrorCode(), "Failed to find renter by ID)", e);
        }
        
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Renter> findAll() throws DAOException {
        try {
            return renterDAO.findAll();
        } catch (DAOException e) {
        	throw new SystemException(e.getErrorCode(), "Failed to find all Renters)", e);
        }
    }
    
    
}
