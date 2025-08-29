package org.ace.accounting.system.fire;

import java.io.Serializable;	
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.common.CurrencyType1;
import org.ace.accounting.common.PaymentType;
import org.ace.accounting.common.SaleChannel;
import org.ace.accounting.common.TableName;
import org.ace.accounting.system.branch.Branch;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.FIREPROPOSAL)
@TableGenerator(name = "FIREPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "FIREPROPOSAL_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class FireProposal implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "FIREPROPOSAL_GEN")
	@Column(name = "PROPOSALID")
	private String id;

	@Column(name = "CUSTOMERTYPE")
	private String customerType;

	@Column(name = "CUSTOMER")
	private String customer;

	@Column(name = "PROPERTYINTEREST")
	private String propertyInterest;

	@Column(name = "PROPERTYLOCATION")
	private String propertyLocation;

	@Column(name = "TOWNSHIP")
	private String township;

	@Column(name = "POLICYNUMBER")
	private String policyNumber;

	@Column(name = "PROPOSALNO")
	private String proposalNo; // New field for sequential number

	@Temporal(TemporalType.DATE)
	@Column(name = "POLICYSTARTDATE")
	private Date policyStartDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "SaleChannel", length = 100)
	private SaleChannel saleChannel;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENTTYPE")
	private PaymentType paymentType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@Temporal(TemporalType.DATE)
	@Column(name = "SUBMITTEDDATE")
	private Date submittedDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCYTYPE")
	private CurrencyType1 currencyType;

	@Column(name = "INSURANCEPERIODDAYS")
	private Integer insurancePeriodDays;

	@OneToMany(mappedBy = "fireProposal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BuildingInfo> buildingList = new ArrayList<>();

	/*
	 * @Temporal(TemporalType.DATE)
	 * @Column(name = "STARTDATEFROM") private Date startDateFrom;
	 *
	 * @Temporal(TemporalType.DATE)
	 * @Column(name = "STARTDATETO") private Date startDateTo;
	 */
	@Version
	@Column(name = "VERSION")
	private int version;

	@Temporal(TemporalType.DATE)
	@Column(name = "POLICYENDDATE")
	private Date policyEndDate;

	@Embedded
	private BasicEntity basicEntity;

	@Column(name = "INSURANCEPERIODUNIT")
	private String insurancePeriodUnit;
	/*
	 * @Column(name = "TOTALSUMINSURED", precision = 15, scale = 2)
	 * private Double totalSumInsured = 0.0;
	 */
	@Column(name = "TOTAL", precision = 15, scale = 2)
	private Double totalPremiumPeriod = 0.0;

	// Constructors
	public FireProposal() {
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerType() {
		return customerType != null ? customerType : "";
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomer() {
		return customer != null ? customer : "";
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPropertyInterest() {
		return propertyInterest != null ? propertyInterest : "";
	}

	public void setPropertyInterest(String propertyInterest) {
		this.propertyInterest = propertyInterest;
	}

	public String getPropertyLocation() {
		return propertyLocation != null ? propertyLocation : "";
	}

	public void setPropertyLocation(String propertyLocation) {
		this.propertyLocation = propertyLocation;
	}

	public String getTownship() {
		return township != null ? township : "";
	}

	public void setTownship(String township) {
		this.township = township;
	}

	public String getPolicyNumber() {
		/*
		 * if (policyNumber == null && proposalNo != null && policyStartDate != null) {
		 * Calendar cal = Calendar.getInstance(); cal.setTime(policyStartDate); int year
		 * = cal.get(Calendar.YEAR); return String.format("FM/PO/%s/FM-%d", proposalNo,
		 * year); }
		 */
		return policyNumber != null ? policyNumber : "";
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getProposalNo() {
		return proposalNo != null ? proposalNo : id;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public Date getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public SaleChannel getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(SaleChannel saleChannel) {
		this.saleChannel = saleChannel;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public CurrencyType1 getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType1 currencyType) {
		this.currencyType = currencyType;
	}

	public Integer getInsurancePeriodDays() {
		return insurancePeriodDays != null ? insurancePeriodDays : 0;
	}

	public void setInsurancePeriodDays(Integer insurancePeriodDays) {
		this.insurancePeriodDays = insurancePeriodDays;
	}

	public List<BuildingInfo> getBuildingList() {
		return buildingList;
	}

	public void setBuildingList(List<BuildingInfo> buildingList) {
		this.buildingList = buildingList;
		if (buildingList != null) {
			for (BuildingInfo b : buildingList) {
				b.setFireProposal(this);
			}
		}
	}
	/*
	 * public Date getStartDateFrom() { return startDateFrom; }
	 * 
	 * public void setStartDateFrom(Date startDateFrom) { this.startDateFrom =
	 * startDateFrom; }
	 * 
	 * public Date getStartDateTo() { return startDateTo; }
	 * 
	 * public void setStartDateTo(Date startDateTo) { this.startDateTo =
	 * startDateTo; }
	 */

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	public BasicEntity getBasicEntity() {
		return basicEntity != null ? basicEntity : (basicEntity = new BasicEntity());
	}

	public void setBasicEntity(BasicEntity basicEntity) {
		this.basicEntity = basicEntity;
	}

	public String getInsurancePeriodUnit() {
		return insurancePeriodUnit != null ? insurancePeriodUnit : "DAY";
	}

	public void setInsurancePeriodUnit(String insurancePeriodUnit) {
		this.insurancePeriodUnit = insurancePeriodUnit;
	}

	
	
	/*
	 * public Double getTotalSumInsured() { return totalSumInsured != null ?
	 * totalSumInsured : 0.0; }
	 * 
	 * public void setTotalSumInsured(Double totalSumInsured) { this.totalSumInsured
	 * = totalSumInsured; }
	 */
	  
	  public Double getTotalPremiumPeriod() { return totalPremiumPeriod != null ?
	  totalPremiumPeriod : 0.0; }
	 
	  public void setTotalPremiumPeriod(Double totalPremiumPeriod) {
	  this.totalPremiumPeriod = totalPremiumPeriod; }
	 
	public double calculateTotalSumInsured() {
		return buildingList.stream().mapToDouble(b -> b.getSumInsured() != null ? b.getSumInsured() : 0.0).sum();
	}

	@Override
	public FireProposal clone() {
		FireProposal clone = new FireProposal();
		clone.setId(this.id);
		clone.setCustomerType(this.customerType);
		clone.setCustomer(this.customer);
		clone.setPropertyInterest(this.propertyInterest);
		clone.setPropertyLocation(this.propertyLocation);
		clone.setTownship(this.township);
		clone.setPolicyNumber(this.policyNumber);
		clone.setProposalNo(this.proposalNo);
		clone.setPolicyStartDate(this.policyStartDate);
		clone.setSaleChannel(this.saleChannel);
		clone.setPaymentType(this.paymentType);
		clone.setBranch(this.branch);
		clone.setSubmittedDate(this.submittedDate);
		clone.setCurrencyType(this.currencyType);
		clone.setInsurancePeriodDays(this.insurancePeriodDays);
		clone.setBuildingList(new ArrayList<>(this.buildingList));
		/*
		 * clone.setStartDateFrom(this.startDateFrom);
		 * clone.setStartDateTo(this.startDateTo);
		 */
		clone.setVersion(this.version);
		clone.setPolicyEndDate(this.policyEndDate);
		clone.setInsurancePeriodUnit(this.insurancePeriodUnit);
		
		/* clone.setTotalSumInsured(this.totalSumInsured); */
		  clone.setTotalPremiumPeriod(this.totalPremiumPeriod);
		 
		return clone;
	}

	public void setTotalSumInsured(double totalSumInsured) {
		// TODO Auto-generated method stub
		
	}
}