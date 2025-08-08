package org.ace.accounting.system.fire.service.interfaces;

import java.util.List;

import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.java.component.SystemException;

public interface IBuildingInfoService {

    List<BuildingInfo> findAllBuildingInfos() throws SystemException;

    void addNewBuildingInfo(BuildingInfo buildingInfo) throws SystemException;

    void updateBuildingInfo(BuildingInfo buildingInfo) throws SystemException;

    void deleteBuildingInfo(BuildingInfo buildingInfo) throws SystemException;

    BuildingInfo findById(String id) throws SystemException;
}