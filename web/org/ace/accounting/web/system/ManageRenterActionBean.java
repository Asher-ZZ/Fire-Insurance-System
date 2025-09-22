package org.ace.accounting.web.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.accounting.common.Gender;
import org.ace.accounting.common.validation.MessageId;
import org.ace.accounting.system.customer.Renter;
import org.ace.accounting.system.customer.service.interfaces.IRenterService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "ManageRenterActionBean")
@ViewScoped
public class ManageRenterActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Renter renter;
	private boolean createNew;
	private Gender gender;
	private List<Renter> renterList;
	private List<Renter> selectedRenterList;
	private Map<String, List<String>> stateTownshipMap;
	private List<String> states;
	private List<String> townships;
	private boolean hasDriverLicence;
	private String selectedState;
	private String selectedTownship;
	private String selectedType;
	private String nrcNumber;
	private String finalNrc;
	private String passportNumber;

	@ManagedProperty(value = "#{RenterService}")
	private IRenterService renterService;

	public void setRenterService(IRenterService renterService) {
		this.renterService = renterService;
	}

	@PostConstruct
	public void init() {
		createNewRenter();
		rebindData();
		stateTownshipMap = new HashMap<>();
		stateTownshipMap.put("1", Arrays.asList("yangon", "thanlyin"));
		stateTownshipMap.put("2", Arrays.asList("mandalay", "pyinOoLwin"));
		stateTownshipMap.put("3", Arrays.asList("taunggyi", "kalaw"));
		states = new ArrayList<>(stateTownshipMap.keySet());
	}

	public void rebindData() {
		renterList = renterService.findAll();
	}

	public void createNewRenter() {
		renter = new Renter();
		createNew = true;
	}

	public void addRenter() {
		try {
			generateFinalIDNumber();
			
			if (!isIdUnique(renter.getIdNumber())) {
		        FacesContext.getCurrentInstance().addMessage(null,
		            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "This NRC/Passport already exists!"));
		        return; 
		    }
			renterService.addNewRenter(renter);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, renter.getName());
			createNewRenter();
			rebindData();
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public void deleteRenter(Renter renter) {
		System.out.println("DEBUG >> renter before delete: " + renter);

		try {
			renterService.deleteRenter(renter);
			;
			addInfoMessage(null, MessageId.DELETE_SUCCESS, renter.getName());
			createNewRenter();
			rebindData();
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public void onStateChange() {
		if (selectedState != null && !selectedState.isEmpty()) {
			townships = stateTownshipMap.get(selectedState);
		} else {
			townships = new ArrayList<>();
		}
	}

	public void generateFinalIDNumber() {
		if ("NRC".equalsIgnoreCase(renter.getIdType())) {
			if (selectedState != null && selectedTownship != null && selectedType != null && nrcNumber != null) {
				renter.setIdNumber(selectedState + "/" + selectedTownship + "(" + selectedType + ")" + nrcNumber);
			} else {
				renter.setIdNumber(""); 
			}
		} else if ("PASSPORT".equalsIgnoreCase(renter.getIdType())) {
			renter.setIdNumber(passportNumber != null ? passportNumber : "");
		} else {
			renter.setIdNumber(""); 
		}
	}

	public void setHasDriverLicence(boolean hasDriverLicence) {
		this.hasDriverLicence = hasDriverLicence;

		if (renter != null) {
			if (Boolean.TRUE.equals(hasDriverLicence)) {
				renter.setDriverLicence("");
			} else {
				renter.setDriverLicence("No");
			}
		}

	}

	public void updateRenter() {
		try {
			renterService.updateRenter(renter);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, renter.getName());
			createNewRenter();
			rebindData();
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public boolean isIdUnique(String idNumber) {
	    if (idNumber == null || idNumber.trim().isEmpty()) {
	        return true; 
	    }

	    if (renterList == null) {
	        return true; 
	    }

	    for (Renter r : renterList) {
	        
	        if (r.getIdNumber() != null 
	            && r.getIdNumber().equals(idNumber) 
	            && (createNew || !r.getId().equals(renter.getId()))) {
	            return false; 
	        }
	    }

	    return true; 
	}

	public void validateNRC() {
	    generateFinalIDNumber();
	    if (!isIdUnique(renter.getIdNumber())) {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "This NRC already exists!"));
	    }
	}
	
	public void validateEmail() {
	    String email = renter.getEmail();
	    
	    if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
	        FacesContext.getCurrentInstance().addMessage("CarRenterForm:email",
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid email format!"));
	        return;
	    }
	}

	public void validatePhone() {
	    String phone = renter.getPhoneNumber();

	    if (phone == null || !phone.matches("\\d{11}")) {
	        FacesContext.getCurrentInstance().addMessage("CarRenterForm:phone",
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid phone number! Must be 10 digits."));
	        return;
	    }
	}

	
	public boolean getHasDriverLicence() {
		return hasDriverLicence;
	}

	public void prepareUpdateRenter(Renter renter) {
		this.renter = renter;
		this.createNew = false;
	}

	// Getters and setters
	public Renter getRenter() {
		return renter;
	}

	public void setRenter(Renter renter) {
		this.renter = renter;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	public List<Renter> getRenterList() {
		return renterList;
	}

	public void setRenterList(List<Renter> renterList) {
		this.renterList = renterList;
	}

	public List<Renter> getSelectedRenterList() {
		return selectedRenterList;
	}

	public void setSelectedRenterList(List<Renter> selectedRenterList) {
		this.selectedRenterList = selectedRenterList;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Gender[] getGenders() {
		return Gender.values();
	}

	public Map<String, List<String>> getStateTownshipMap() {
		return stateTownshipMap;
	}

	public void setStateTownshipMap(Map<String, List<String>> stateTownshipMap) {
		this.stateTownshipMap = stateTownshipMap;
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public List<String> getTownships() {
		return townships;
	}

	public void setTownships(List<String> townships) {
		this.townships = townships;
	}

	public String getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}

	public String getSelectedTownship() {
		return selectedTownship;
	}

	public void setSelectedTownship(String selectedTownship) {
		this.selectedTownship = selectedTownship;
	}

	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}

	public String getNrcNumber() {
		return nrcNumber;
	}

	public void setNrcNumber(String nrcNumber) {
		this.nrcNumber = nrcNumber;
	}

	public String getFinalNrc() {
		return finalNrc;
	}

	public void setFinalNrc(String finalNrc) {
		this.finalNrc = finalNrc;
	}

	public IRenterService getRenterService() {
		return renterService;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
}
