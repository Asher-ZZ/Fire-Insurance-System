package org.ace.accounting.system.fire.persistence.interfaces;

import java.util.List;

import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.java.component.persistence.exception.DAOException;

public interface IBuildingInfoDAO {

    List<BuildingInfo> findAll() throws DAOException;

    void insert(BuildingInfo buildingInfo) throws DAOException;

    BuildingInfo update(BuildingInfo buildingInfo) throws DAOException;

    void delete(BuildingInfo buildingInfo) throws DAOException;

    BuildingInfo findById(String id) throws DAOException;
}