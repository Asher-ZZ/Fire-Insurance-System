package org.ace.accounting.system.fire.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.persistence.interfaces.IBuildingInfoDAO;
import org.ace.accounting.system.fire.service.interfaces.IBuildingInfoService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "BuildingInfoService")
public class FireBuildingService extends BaseService implements IBuildingInfoService {

    @Resource(name = "BuildingInfoDAO")
    private IBuildingInfoDAO buildingInfoDAO;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<BuildingInfo> findAllBuildingInfos() throws SystemException {
        try {
            return buildingInfoDAO.findAll();
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find all building infos", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewBuildingInfo(BuildingInfo buildingInfo) throws SystemException {
        try {
            buildingInfoDAO.insert(buildingInfo);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to add new building info", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateBuildingInfo(BuildingInfo buildingInfo) throws SystemException {
        try {
            buildingInfoDAO.update(buildingInfo);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to update building info", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteBuildingInfo(BuildingInfo buildingInfo) throws SystemException {
        try {
            buildingInfoDAO.delete(buildingInfo);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to delete building info", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BuildingInfo findById(String id) throws SystemException {
        try {
            return buildingInfoDAO.findById(id);
        } catch (DAOException e) {
            throw new SystemException(e.getErrorCode(), "Failed to find building info by id: " + id, e);
        }
    }
}