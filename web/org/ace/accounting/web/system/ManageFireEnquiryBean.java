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
	public Date getStartDateFrom() { return startDateFrom; }
	public void setStartDateFrom(Date startDateFrom) { this.startDateFrom = startDateFrom; }
	public Date getStartDateTo() { return startDateTo; }
	public void setStartDateTo(Date startDateTo) { this.startDateTo = startDateTo; }
	public String getPolicyNo() { return policyNo; }
	public void setPolicyNo(String policyNo) { this.policyNo = policyNo; }
	public List<Policy> getPolicies() { return policies; }
	public void setPolicies(List<Policy> policies) { this.policies = policies; }
	public void setFireProposalService(IFireProposalService fireProposalService) { this.fireProposalService = fireProposalService; }

	// Search action
	public void search() {
		policies.clear();

		try {
			if (isValidSearchCriteria()) {
				List<FireProposal> fireProposals = new ArrayList<>();

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

				FacesContext context = FacesContext.getCurrentInstance();
				if (policies.isEmpty()) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No records found", null));
				} else {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Search completed", policies.size() + " records found"));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
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

	// Convert FireProposal to Policy for display (no totals)
	private Policy convertToPolicy(FireProposal proposal) {
		return new Policy(
				proposal.getPolicyNumber(),
				proposal.getProposalNo() != null ? proposal.getProposalNo() : proposal.getId(),
				proposal.getSaleChannel() != null ? proposal.getSaleChannel().toString() : "",
				proposal.getCustomer(),
				proposal.getBranch() != null ? proposal.getBranch().getName() : "",
				proposal.getPaymentType() != null ? proposal.getPaymentType().toString() : ""
		);
	}

	// Validate search criteria
	private boolean isValidSearchCriteria() {
	    if (policyNo != null && !policyNo.trim().isEmpty()) return true;
	    if (startDateFrom != null && startDateTo != null) return !startDateFrom.after(startDateTo);
	    if (startDateFrom != null && startDateTo == null) return true;
	    return false;
	}

	// Policy class to hold display data (without totals)
	public static class Policy {
		private String policyNo;
		private String proposalNo;
		private String saleChannel;
		private String customer;
		private String branch;
		private String paymentType;

		public Policy(String policyNo, String proposalNo, String saleChannel, String customer,
					  String branch, String paymentType) {
			this.policyNo = policyNo;
			this.proposalNo = proposalNo;
			this.saleChannel = saleChannel;
			this.customer = customer;
			this.branch = branch;
			this.paymentType = paymentType;
		}

		// Getters and Setters
		public String getPolicyNo() { return policyNo; }
		public void setPolicyNo(String policyNo) { this.policyNo = policyNo; }
		public String getProposalNo() { return proposalNo; }
		public void setProposalNo(String proposalNo) { this.proposalNo = proposalNo; }
		public String getSaleChannel() { return saleChannel; }
		public void setSaleChannel(String saleChannel) { this.saleChannel = saleChannel; }
		public String getCustomer() { return customer; }
		public void setCustomer(String customer) { this.customer = customer; }
		public String getBranch() { return branch; }
		public void setBranch(String branch) { this.branch = branch; }
		public String getPaymentType() { return paymentType; }
		public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
	}
}
