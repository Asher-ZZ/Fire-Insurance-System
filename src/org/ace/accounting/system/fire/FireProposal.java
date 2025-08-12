package org.ace.accounting.system.fire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.common.Branch;
import org.ace.accounting.common.CurrencyType1;
import org.ace.accounting.common.PaymentType;
import org.ace.accounting.common.SaleChannel;
import org.ace.accounting.common.TableName;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.FIREPOLICY)
@TableGenerator(name = "FIREPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "FIREPROPOSAL_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class FireProposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "FIREPROPOSAL_GEN") // Changed to IDENTITY
    @Column(name = "ProposalID")
    private String id;

    // Remove @TableGenerator and @EntityListeners(IDInterceptor.class)

    // Existing fields, getters, and setters remain the same
    @Column(name = "CustomerType", length = 50)
    private String customerType;

    @Column(name = "Customer", length = 100)
    private String customer;

    @Column(name = "PropertyInterest", length = 255)
    private String propertyInterest;

    @Column(name = "PropertyLocation", length = 255)
    private String propertyLocation;

    @Column(name = "Township", length = 100)
    private String township;

    @Column(name = "PolicyNumber", length = 50)
    private String policyNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "PolicyStartDate")
    private Date policyStartDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "SaleChannel", length = 100)
    private SaleChannel saleChannel;

    @Enumerated(EnumType.STRING)
    @Column(name = "PaymentType", length = 100)
    private PaymentType paymentType; // Added mapping

    @Enumerated(EnumType.STRING)
    @Column(name = "Branch", length = 100)
    private Branch branch;

    @Temporal(TemporalType.DATE)
    @Column(name = "SubmittedDate")
    private Date submittedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "CurrencyType", length = 50)
    private CurrencyType1 currencyType;

    @Column(name = "InsurancePeriodDays")
    private Integer insurancePeriodDays;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "BuildingInfoID")
    private BuildingInfo buildingInfo;

    @OneToMany(mappedBy = "fireProposal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Premium> premiumList = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    @Column(name = "StartDateFrom")
    private Date startDateFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "StartDateTo")
    private Date startDateTo;

    @Version
    @Column(name = "Version")
    private int version;

    @Embedded
    private BasicEntity basicEntity;

    @Column(name = "InsurancePeriodUnit", length = 10)
    private String insurancePeriodUnit;

    @Column(name = "TotalSumInsured", precision = 15, scale = 2)
    private Double totalSumInsured = 0.0;

    @Column(name = "TotalPremiumPeriod", precision = 15, scale = 2)
    private Double totalPremiumPeriod = 0.0;

    // Getters and setters (as provided), including new fields


    // --- Constructors ---
    public FireProposal() {}

    // --- Getters & Setters ---
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
        return policyNumber != null ? policyNumber : "";
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
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

    public BuildingInfo getBuildingInfo() {
        if (buildingInfo == null) {
            buildingInfo = new BuildingInfo();
        }
        return buildingInfo;
    }

    public void setBuildingInfo(BuildingInfo buildingInfo) {
        this.buildingInfo = buildingInfo;
    }

    public List<Premium> getPremiumList() {
        return premiumList;
    }

    public void setPremiumList(List<Premium> premiumList) {
        this.premiumList = premiumList;
        if (premiumList != null) {
            for (Premium premium : premiumList) {
                premium.setFireProposal(this);
            }
        }
    }

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public BasicEntity getBasicEntity() {
        if (basicEntity == null) {
            basicEntity = new BasicEntity();
        }
        return basicEntity;
    }

    public void setBasicEntity(BasicEntity basicEntity) {
        this.basicEntity = basicEntity;
    }
    
    // You can add helper methods for calculating totals if needed
    public double calculateTotalSumInsured() {
        return premiumList.stream()
                .mapToDouble(p -> p.getSumInsured() != null ? p.getSumInsured() : 0.0)
                .sum();
    }
    
 // Add these fields inside your FireProposal class



    public Double getTotalSumInsured() {
        return totalSumInsured != null ? totalSumInsured : 0.0;
    }

    public void setTotalSumInsured(Double totalSumInsured) {
        this.totalSumInsured = totalSumInsured;
    }

    public Double getTotalPremiumPeriod() {
        return totalPremiumPeriod != null ? totalPremiumPeriod : 0.0;
    }

    public void setTotalPremiumPeriod(Double totalPremiumPeriod) {
        this.totalPremiumPeriod = totalPremiumPeriod;
    }



    // Getter
    public String getInsurancePeriodUnit() {
        return insurancePeriodUnit != null ? insurancePeriodUnit : "DAY"; // default value if you want
    }

    // Setter
    public void setInsurancePeriodUnit(String insurancePeriodUnit) {
        this.insurancePeriodUnit = insurancePeriodUnit;
    }

    
}
