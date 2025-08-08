package org.ace.accounting.web.system;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.service.interfaces.IBuildingInfoService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "ManageFireBuildingActionBean")
@ViewScoped
public class ManageFireBuildingActionBean extends BaseBean {

	@ManagedProperty(value = "#{BuildingInfoService}")
	private IBuildingInfoService buildingInfoService;

	private BuildingInfo buildingInfo;

	// --- Constructors ---
	public ManageFireBuildingActionBean() {
	}

	// --- Initialization ---
	public void createNewBuildingInfo() {
		buildingInfo = new BuildingInfo();
		// Initialize default values if needed
	}

	// --- Getters and Setters ---
	public void setBuildingInfoService(IBuildingInfoService buildingInfoService) {
		this.buildingInfoService = buildingInfoService;
	}

	public BuildingInfo getBuildingInfo() {
		if (buildingInfo == null) {
			buildingInfo = new BuildingInfo();
		}
		return buildingInfo;
	}

	public void setBuildingInfo(BuildingInfo buildingInfo) {
		this.buildingInfo = buildingInfo;
	}

	// --- Business Logic ---
	public void saveBuildingInfo() {
		try {
			if (buildingInfo.getId() == null) {
				buildingInfoService.addNewBuildingInfo(buildingInfo);
				addInfoMessage(null, "INSERT_SUCCESS", "Building Info");
			} else {
				buildingInfoService.updateBuildingInfo(buildingInfo);
				addInfoMessage(null, "UPDATE_SUCCESS", "Building Info");
			}
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public void resetBuildingInfo() {
		createNewBuildingInfo();
	}

	public void syncWithFireProposal(BuildingInfo buildingInfo) {
		this.buildingInfo = buildingInfo != null ? buildingInfo.clone() : new BuildingInfo();
	}
}