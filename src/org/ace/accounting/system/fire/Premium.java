/*
 * package org.ace.accounting.system.fire;
 * 
 * import java.io.Serializable; import javax.persistence.*;
 * 
 * @Entity
 * 
 * @Table(name = "PREMIUM")
 * 
 * @TableGenerator( name = "PREMIUM_GEN", table = "ID_GEN", pkColumnName =
 * "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PREMIUM_GEN",
 * allocationSize = 10 ) public class Premium implements Serializable, Cloneable
 * {
 * 
 * private static final long serialVersionUID = 1L;
 * 
 * // -------------------- ID --------------------
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.TABLE, generator = "PREMIUM_GEN")
 * 
 * @Column(name = "PremiumID") private Long id;
 * 
 * // -------------------- Relationship --------------------
 * 
 * @ManyToOne
 * 
 * @JoinColumn(name = "ProposalID") private FireProposal fireProposal;
 * 
 * // -------------------- Fields --------------------
 * 
 * @Column(name = "RowNum") private int rowNum;
 * 
 * @Column(name = "BuildingName", length = 100) private String buildingName;
 * 
 * @Column(name = "SumInsured") private Double sumInsured;
 * 
 * @Column(name = "PremiumRate") private Double premiumRate;
 * 
 * @Column(name = "BasicPremiumPeriod") private Double basicPremiumPeriod;
 * 
 * @Column(name = "BasicPremiumTerm") private Double basicPremiumTerm;
 * 
 * @Column(name = "AddOnPremiumPeriod") private Double addOnPremiumPeriod;
 * 
 * @Column(name = "AddOnPremiumTerm") private Double addOnPremiumTerm;
 * 
 * @Column(name = "TotalPremiumPeriod") private Double totalPremiumPeriod;
 * 
 * // -------------------- Constructors -------------------- public Premium() {
 * }
 * 
 * // -------------------- Getters & Setters -------------------- public Long
 * getId() { return id; }
 * 
 * public void setId(Long id) { this.id = id; }
 * 
 * public FireProposal getFireProposal() { return fireProposal; }
 * 
 * public void setFireProposal(FireProposal fireProposal) { this.fireProposal =
 * fireProposal; }
 * 
 * public int getRowNum() { return rowNum; }
 * 
 * public void setRowNum(int rowNum) { this.rowNum = rowNum; }
 * 
 * public String getBuildingName() { return buildingName; }
 * 
 * public void setBuildingName(String buildingName) { this.buildingName =
 * buildingName; }
 * 
 * public Double getSumInsured() { return sumInsured != null ? sumInsured : 0.0;
 * }
 * 
 * public void setSumInsured(Double sumInsured) { this.sumInsured = sumInsured;
 * calculateTotalPremiumPeriod(); }
 * 
 * public Double getPremiumRate() { return premiumRate != null ? premiumRate :
 * 0.0; }
 * 
 * public void setPremiumRate(Double premiumRate) { this.premiumRate =
 * premiumRate; calculateTotalPremiumPeriod(); }
 * 
 * public Double getBasicPremiumPeriod() { return basicPremiumPeriod != null ?
 * basicPremiumPeriod : 0.0; }
 * 
 * public void setBasicPremiumPeriod(Double basicPremiumPeriod) {
 * this.basicPremiumPeriod = basicPremiumPeriod; }
 * 
 * public Double getBasicPremiumTerm() { return basicPremiumTerm != null ?
 * basicPremiumTerm : 0.0; }
 * 
 * public void setBasicPremiumTerm(Double basicPremiumTerm) {
 * this.basicPremiumTerm = basicPremiumTerm; }
 * 
 * public Double getAddOnPremiumPeriod() { return addOnPremiumPeriod != null ?
 * addOnPremiumPeriod : 0.0; }
 * 
 * public void setAddOnPremiumPeriod(Double addOnPremiumPeriod) {
 * this.addOnPremiumPeriod = addOnPremiumPeriod; }
 * 
 * public Double getAddOnPremiumTerm() { return addOnPremiumTerm != null ?
 * addOnPremiumTerm : 0.0; }
 * 
 * public void setAddOnPremiumTerm(Double addOnPremiumTerm) {
 * this.addOnPremiumTerm = addOnPremiumTerm; }
 * 
 * public Double getTotalPremiumPeriod() { return totalPremiumPeriod != null ?
 * totalPremiumPeriod : 0.0; }
 * 
 * public void setTotalPremiumPeriod(Double totalPremiumPeriod) {
 * this.totalPremiumPeriod = totalPremiumPeriod; }
 * 
 * // -------------------- Calculation -------------------- private void
 * calculateTotalPremiumPeriod() { if (sumInsured != null && premiumRate !=
 * null) { this.totalPremiumPeriod = sumInsured * (premiumRate / 100.0); } }
 * 
 * // -------------------- Clone --------------------
 * 
 * @Override public Premium clone() { try { return (Premium) super.clone(); }
 * catch (CloneNotSupportedException e) { throw new
 * RuntimeException("Clone not supported", e); } } }
 */
