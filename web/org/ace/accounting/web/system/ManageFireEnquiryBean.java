package org.ace.accounting.web.system;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ace.accounting.system.fire.FireProposal;
import org.ace.accounting.system.fire.Policy;
import org.ace.accounting.system.fire.service.interfaces.IFireProposalService;
import org.ace.java.component.SystemException;

@ManagedBean(name = "ManageFireEnquiryBean")
@ViewScoped
public class ManageFireEnquiryBean implements Serializable {

	private Date startDateFrom;
	private Date startDateTo;
	private String policyNo;
	private List<Policy> policies;

	@ManagedProperty(value = "#{FireProposalService}")
	private IFireProposalService fireProposalService;

	// Constructor
	public ManageFireEnquiryBean() {
		policies = new ArrayList<>();
	}

	@PostConstruct
	public void init() {
		setDefaultDates();
	}

	private void setDefaultDates() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		startDateTo = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		startDateFrom = cal.getTime();
	}

	// Getters and Setters
	public Date getStartDateFrom() {
		return startDateFrom;
	}

	public void setStartDateFrom(Date startDateFrom) {
		this.startDateFrom = startDateFrom;
	}

	public Date getStartDateTo() {
		return startDateTo;
	}

	public void setStartDateTo(Date startDateTo) {
		this.startDateTo = startDateTo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public List<Policy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<Policy> policies) {
		this.policies = policies;
	}

	public void setFireProposalService(IFireProposalService fireProposalService) {
		this.fireProposalService = fireProposalService;
	}

	// Search action
	public void search() {
		// Clear previous results
		policies.clear();

		try {
			// Validate search criteria and query database
			if (isValidSearchCriteria()) {
				List<FireProposal> fireProposals;
				if (policyNo != null && !policyNo.trim().isEmpty()) {
					FireProposal proposal = fireProposalService.findFireProposalByPolicyNo(policyNo);
					if (proposal != null) {
						policies.add(convertToPolicy(proposal));
					}
				} else {
					fireProposals = fireProposalService.findFireProposalsByDateRange(startDateFrom, startDateTo);
					for (FireProposal proposal : fireProposals) {
						policies.add(convertToPolicy(proposal));
					}
				}

				if (policies.isEmpty()) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "No records found", null));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Search completed", policies.size() + " records found"));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Invalid search criteria", "Please provide valid dates or policy number"));
			}
		} catch (SystemException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error during search", e.getMessage()));
		}
	}

	// Reset action
	public void reset() {
		policyNo = null;
		startDateFrom = null;
		startDateTo = null;
		policies.clear();
		setDefaultDates();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Form reset", null));
	}

	// Convert FireProposal to Policy for display
	private Policy convertToPolicy(FireProposal proposal) {
		return new Policy(proposal.getPolicyNumber(),
				proposal.getProposalNo() != null ? proposal.getProposalNo() : proposal.getId(),
				proposal.getSaleChannel() != null ? proposal.getSaleChannel().toString() : "", proposal.getCustomer(),
				proposal.getBranch() != null ? proposal.getBranch().getName() : "",	
				proposal.getPaymentType() != null ? proposal.getPaymentType().toString() : "", 0, 0);
	}

	// Validate search criteria
	private boolean isValidSearchCriteria() {
		if (policyNo != null && !policyNo.trim().isEmpty()) {
			return true;
		}
		if (startDateFrom != null && startDateTo != null) {
			return startDateFrom.before(startDateTo) || startDateFrom.equals(startDateTo);
		}
		return false;
	}
}