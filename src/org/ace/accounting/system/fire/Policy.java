/*
 * package org.ace.accounting.system.fire;
 * 
 * import java.io.Serializable;
 * 
 * import javax.persistence.*;
 * 
 * @Entity
 * 
 * @Table(name = "POLICY")
 * 
 * @TableGenerator( name = "POLICY_GEN", table = "ID_GEN", pkColumnName =
 * "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "POLICY_GEN",
 * allocationSize = 10 ) public class Policy implements Serializable, Cloneable
 * {
 * 
 * private static final long serialVersionUID = 1L;
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.TABLE, generator = "POLICY_GEN")
 * 
 * @Column(name = "PolicyID") private String id;
 * 
 * @Column(name = "PolicyNo", length = 50) private String policyNo;
 * 
 * @Column(name = "ProposalNo", length = 50) private String proposalNo;
 * 
 * @Column(name = "SaleChannel", length = 100) private String saleChannel;
 * 
 * @Column(name = "SalePerson", length = 100) private String salePerson;
 * 
 * @Column(name = "Customer", length = 100) private String customer;
 * 
 * @Column(name = "Branch", length = 100) private String branch;
 * 
 * @Column(name = "TotalPremium") private Double totalPremium;
 * 
 * @Column(name = "TotalSumInsured") private Double totalSumInsured;
 * 
 * @Column(name = "PaymentType", length = 100) private String paymentType;
 * 
 * // --- Constructors --- public Policy() {}
 * 
 * // --- Getters & Setters --- public String getId() { return id != null ? id :
 * ""; }
 * 
 * public void setId(String id) { this.id = id; }
 * 
 * public String getPolicyNo() { return policyNo != null ? policyNo : ""; }
 * 
 * public void setPolicyNo(String policyNo) { this.policyNo = policyNo; }
 * 
 * public String getProposalNo() { return proposalNo != null ? proposalNo : "";
 * }
 * 
 * public void setProposalNo(String proposalNo) { this.proposalNo = proposalNo;
 * }
 * 
 * public String getSaleChannel() { return saleChannel != null ? saleChannel :
 * ""; }
 * 
 * public void setSaleChannel(String saleChannel) { this.saleChannel =
 * saleChannel; }
 * 
 * public String getSalePerson() { return salePerson != null ? salePerson : "";
 * }
 * 
 * public void setSalePerson(String salePerson) { this.salePerson = salePerson;
 * }
 * 
 * public String getCustomer() { return customer != null ? customer : ""; }
 * 
 * public void setCustomer(String customer) { this.customer = customer; }
 * 
 * public String getBranch() { return branch != null ? branch : ""; }
 * 
 * public void setBranch(String branch) { this.branch = branch; }
 * 
 * public Double getTotalPremium() { return totalPremium != null ? totalPremium
 * : 0.0; }
 * 
 * public void setTotalPremium(Double totalPremium) { this.totalPremium =
 * totalPremium; }
 * 
 * public Double getTotalSumInsured() { return totalSumInsured != null ?
 * totalSumInsured : 0.0; }
 * 
 * public void setTotalSumInsured(Double totalSumInsured) { this.totalSumInsured
 * = totalSumInsured; }
 * 
 * public String getPaymentType() { return paymentType != null ? paymentType :
 * ""; }
 * 
 * public void setPaymentType(String paymentType) { this.paymentType =
 * paymentType; }
 * 
 * @Override public Policy clone() { try { return (Policy) super.clone(); }
 * catch (CloneNotSupportedException e) { throw new
 * RuntimeException("Cloning failed", e); } } }
 */