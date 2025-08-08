package org.ace.accounting.web.system;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.FireProposal;
/*import org.ace.accounting.system.fire.Premium;*/
import org.ace.accounting.system.fire.service.interfaces.IFireProposalService;
import org.ace.accounting.common.CurrencyType1;
import org.ace.accounting.common.PaymentType;
import org.ace.accounting.common.SaleChannel;
import org.ace.accounting.common.Branch;
import org.ace.accounting.common.validation.MessageId;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.FlowEvent;

@ManagedBean(name = "ManageFireProposalActionBean")
@ViewScoped
public class ManageFireProposalActionBean extends BaseBean {

	@ManagedProperty(value = "#{FireProposalService}") // Updated to match Spring bean name
	private IFireProposalService fireProposalService;

	@ManagedProperty(value = "#{ManageFireBuildingActionBean}")
	private ManageFireBuildingActionBean manageFireBuildingActionBean;

	private boolean createNew;
	private FireProposal fireProposal;
	private List<FireProposal> fireProposalList;
	private FireProposal selectedFireProposal;

	private Date minDate = toDate(LocalDate.of(1990, 1, 1));
	private Date maxDate = toDate(LocalDate.now());


	private int insurancePeriodDays = 0;
	private String insurancePeriodUnit = "DAY";

	private List<PaymentType> paymentTypes;


	
	// Wizard step tracking
	private String currentStep = "proposalInfo";

	private Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	@PostConstruct
	public void init() {
		createNewFireProposal();
		rebindData();
		 paymentTypes = PaymentType.getAllTypes();
	}
	public List<PaymentType> getPaymentTypes() {
	    return paymentTypes;
	}
	
	
	public void createNewFireProposal() {
		createNew = true;
		fireProposal = new FireProposal();
		fireProposal.setBuildingInfo(new BuildingInfo());
		if (manageFireBuildingActionBean != null) {
			manageFireBuildingActionBean.syncWithFireProposal(fireProposal.getBuildingInfo());
		}
	}

	public void rebindData() {
		if (fireProposalService != null) {
			fireProposalList = fireProposalService.findAllFireProposals();
		} else {
			fireProposalList = new ArrayList<>();
		}
	}

	public void addNewFireProposal() {
		try {
			if (fireProposalService != null && manageFireBuildingActionBean != null) {
				fireProposal.setBuildingInfo(manageFireBuildingActionBean.getBuildingInfo().clone());
				fireProposalService.addNewFireProposal(fireProposal);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, fireProposal.getCustomer());
				createNewFireProposal();
				rebindData();
			}
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public void updateFireProposal() {
		try {
			if (fireProposalService != null && manageFireBuildingActionBean != null) {
				fireProposal.setBuildingInfo(manageFireBuildingActionBean.getBuildingInfo().clone());
				fireProposalService.updateFireProposal(fireProposal);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, fireProposal.getCustomer());
				createNewFireProposal();
				rebindData();
			}
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public String deleteFireProposal(FireProposal proposal) {
		try {
			if (fireProposalService != null) {
				fireProposalService.deleteFireProposal(proposal);
				addInfoMessage(null, MessageId.DELETE_SUCCESS, proposal.getCustomer());
			}
		} catch (SystemException ex) {
			handleSysException(ex);
		}
		createNewFireProposal();
		rebindData();
		return null;
	}

	public String saveFireProposal() {
		if (createNew) {
			addNewFireProposal();
		} else {
			updateFireProposal();
		}
		return null;
	}

	public void fireProposalDetail(FireProposal proposal) {
		this.selectedFireProposal = proposal;
	}

	public String onFlowProcess(FlowEvent event) {
		if (skipStep(event)) {
			return event.getNewStep();
		}
		if (event.getNewStep().equals("summary")) {
			if (manageFireBuildingActionBean != null) {
				fireProposal.setBuildingInfo(manageFireBuildingActionBean.getBuildingInfo().clone());
			}
			saveFireProposal();
		}
		currentStep = event.getNewStep();
		return event.getNewStep();
	}

	private boolean skipStep(FlowEvent event) {
		return false;
	}

	public String cancel() {
		createNewFireProposal();
		if (manageFireBuildingActionBean != null) {
			manageFireBuildingActionBean.resetBuildingInfo();
		}
		return null;
	}


	// Getters and Setters
	public int getInsurancePeriodDays() {
	    return insurancePeriodDays;
	}
	public void setInsurancePeriodDays(int days) {
	    this.insurancePeriodDays = days;
	}

	public String getInsurancePeriodUnit() {
	    return insurancePeriodUnit;
	}
	public void setInsurancePeriodUnit(String unit) {
	    this.insurancePeriodUnit = unit;
	}

	
	
	
	
	public boolean isCreateNew() {
		return createNew;
	}

	public FireProposal getFireProposal() {
		return fireProposal;
	}

	public void setFireProposal(FireProposal fireProposal) {
		this.fireProposal = fireProposal;
	}

	public List<FireProposal> getFireProposalList() {
		return fireProposalList;
	}

	public FireProposal getSelectedFireProposal() {
		return selectedFireProposal;
	}

	public void setSelectedFireProposal(FireProposal selectedFireProposal) {
		this.selectedFireProposal = selectedFireProposal;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public SaleChannel[] getSaleChannels() {
		return SaleChannel.values();
	}

	public Branch[] getBranches() {
		return Branch.values();
	}

	public CurrencyType1[] getCurrencyTypes() {
		return CurrencyType1.values();
	}

	public IFireProposalService getFireProposalService() {
		return fireProposalService;
	}

	public void setFireProposalService(IFireProposalService fireProposalService) {
		this.fireProposalService = fireProposalService;
	}

	public ManageFireBuildingActionBean getManageFireBuildingActionBean() {
		return manageFireBuildingActionBean;
	}

	public void setManageFireBuildingActionBean(ManageFireBuildingActionBean manageFireBuildingActionBean) {
		this.manageFireBuildingActionBean = manageFireBuildingActionBean;
	}
	
	public String getCustomerPlaceholder() {
	    if ("Person".equals(fireProposal.getCustomerType())) {
	        return "Select The Person";
	    } else if ("Organization".equals(fireProposal.getCustomerType())) {
	        return "Select The Organization";
	    }
	    return "";
	}

	
}