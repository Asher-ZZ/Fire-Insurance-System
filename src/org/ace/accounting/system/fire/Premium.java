/*
 * package org.ace.accounting.system.fire;
 * 
 * import java.io.Serializable;
 * 
 * import javax.persistence.*;
 * 
 * @Entity
 * 
 * @Table(name = "PREMIUM") public class Premium implements Serializable,
 * Cloneable {
 * 
 * private static final long serialVersionUID = 1L;
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed to IDENTITY
 * 
 * @Column(name = "PremiumID") private Long id;
 * 
 * // Remove @TableGenerator
 * 
 * @ManyToOne
 * 
 * @JoinColumn(name = "ProposalID") private FireProposal fireProposal;
 * 
 * @Column(name = "RowNum") private int rowNum;
 * 
 * @Column(name = "BuildingName", length = 100) private String buildingName;
 * 
 * @Column(name = "SumInsured", precision = 15, scale = 2) private Double
 * sumInsured;
 * 
 * @Column(name = "PremiumRate", precision = 5, scale = 2) private Double
 * premiumRate;
 * 
 * @Column(name = "BasicPremiumPeriod", precision = 15, scale = 2) private
 * Double basicPremiumPeriod;
 * 
 * @Column(name = "BasicPremiumTerm", precision = 15, scale = 2) private Double
 * basicPremiumTerm;
 * 
 * @Column(name = "AddOnPremiumPeriod", precision = 15, scale = 2) private
 * Double addOnPremiumPeriod;
 * 
 * @Column(name = "AddOnPremiumTerm", precision = 15, scale = 2) private Double
 * addOnPremiumTerm;
 * 
 * @Column(name = "TotalPremiumPeriod", precision = 15, scale = 2) private
 * Double totalPremiumPeriod;
 * 
 * // Getters and setters (as provided), with calculateTotalPremiumPeriod logic
 * 
 * 
 * // -------------------- Constructors -------------------- public Premium() {}
 * 
 * // -------------------- Getters & Setters -------------------- public Long
 * getId() { return id; } public void setId(Long id) { this.id = id; }
 * 
 * public FireProposal getFireProposal() { return fireProposal; } public void
 * setFireProposal(FireProposal fireProposal) { this.fireProposal =
 * fireProposal; }
 * 
 * public int getRowNum() { return rowNum; } public void setRowNum(int rowNum) {
 * this.rowNum = rowNum; }
 * 
 * public String getBuildingName() { return buildingName != null ? buildingName
 * : ""; } public void setBuildingName(String buildingName) { this.buildingName
 * = buildingName; }
 * 
 * public Double getSumInsured() { return sumInsured != null ? sumInsured : 0.0;
 * } public void setSumInsured(Double sumInsured) { this.sumInsured =
 * sumInsured; calculateTotalPremiumPeriod(); }
 * 
 * public Double getPremiumRate() { return premiumRate != null ? premiumRate :
 * 0.0; } public void setPremiumRate(Double premiumRate) { this.premiumRate =
 * premiumRate; calculateTotalPremiumPeriod(); }
 * 
 * public Double getBasicPremiumPeriod() { return basicPremiumPeriod != null ?
 * basicPremiumPeriod : 0.0; } public void setBasicPremiumPeriod(Double
 * basicPremiumPeriod) { this.basicPremiumPeriod = basicPremiumPeriod; }
 * 
 * public Double getBasicPremiumTerm() { return basicPremiumTerm != null ?
 * basicPremiumTerm : 0.0; } public void setBasicPremiumTerm(Double
 * basicPremiumTerm) { this.basicPremiumTerm = basicPremiumTerm; }
 * 
 * public Double getAddOnPremiumPeriod() { return addOnPremiumPeriod != null ?
 * addOnPremiumPeriod : 0.0; } public void setAddOnPremiumPeriod(Double
 * addOnPremiumPeriod) { this.addOnPremiumPeriod = addOnPremiumPeriod; }
 * 
 * public Double getAddOnPremiumTerm() { return addOnPremiumTerm != null ?
 * addOnPremiumTerm : 0.0; } public void setAddOnPremiumTerm(Double
 * addOnPremiumTerm) { this.addOnPremiumTerm = addOnPremiumTerm; }
 * 
 * public Double getTotalPremiumPeriod() { return totalPremiumPeriod != null ?
 * totalPremiumPeriod : 0.0; } public void setTotalPremiumPeriod(Double
 * totalPremiumPeriod) { this.totalPremiumPeriod = totalPremiumPeriod; }
 * 
 * // -------------------- Calculation -------------------- private void
 * calculateTotalPremiumPeriod() { if (sumInsured != null && premiumRate !=
 * null) { this.totalPremiumPeriod = sumInsured * (premiumRate / 100.0); //
 * Assuming premiumRate is in percentage } else { this.totalPremiumPeriod = 0.0;
 * } }
 * 
 * // -------------------- Clone --------------------
 * 
 * @Override public Premium clone() { try { return (Premium) super.clone(); }
 * catch (CloneNotSupportedException e) { throw new
 * RuntimeException("Clone not supported", e); } } }
 */