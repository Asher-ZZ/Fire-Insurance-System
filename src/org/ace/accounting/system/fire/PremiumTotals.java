//package org.ace.accounting.system.fire;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//
//@Embeddable
//public class PremiumTotals implements Serializable, Cloneable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Column(name = "TotalSumInsured")
//    private Double sumInsured;
//
//    @Column(name = "TotalBasicPremiumPeriod")
//    private Double basicPremiumPeriod;
//
//    @Column(name = "TotalBasicPremiumTerm")
//    private Double basicPremiumTerm;
//
//    @Column(name = "TotalAddOnPremiumPeriod")
//    private Double addOnPremiumPeriod;
//
//    @Column(name = "TotalAddOnPremiumTerm")
//    private Double addOnPremiumTerm;
//
//    @Column(name = "TotalPremiumPeriod")
//    private Double totalPremiumPeriod;
//
//    // --- Constructors ---
//    public PremiumTotals() {}
//
//    // --- Getters & Setters ---
//    public Double getSumInsured() {
//        return sumInsured != null ? sumInsured : 0.0;
//    }
//
//    public void setSumInsured(Double sumInsured) {
//        this.sumInsured = sumInsured;
//    }
//
//    public Double getBasicPremiumPeriod() {
//        return basicPremiumPeriod != null ? basicPremiumPeriod : 0.0;
//    }
//
//    public void setBasicPremiumPeriod(Double basicPremiumPeriod) {
//        this.basicPremiumPeriod = basicPremiumPeriod;
//    }
//
//    public Double getBasicPremiumTerm() {
//        return basicPremiumTerm != null ? basicPremiumTerm : 0.0;
//    }
//
//    public void setBasicPremiumTerm(Double basicPremiumTerm) {
//        this.basicPremiumTerm = basicPremiumTerm;
//    }
//
//    public Double getAddOnPremiumPeriod() {
//        return addOnPremiumPeriod != null ? addOnPremiumPeriod : 0.0;
//    }
//
//    public void setAddOnPremiumPeriod(Double addOnPremiumPeriod) {
//        this.addOnPremiumPeriod = addOnPremiumPeriod;
//    }
//
//    public Double getAddOnPremiumTerm() {
//        return addOnPremiumTerm != null ? addOnPremiumTerm : 0.0;
//    }
//
//    public void setAddOnPremiumTerm(Double addOnPremiumTerm) {
//        this.addOnPremiumTerm = addOnPremiumTerm;
//    }
//
//    public Double getTotalPremiumPeriod() {
//        return totalPremiumPeriod != null ? totalPremiumPeriod : 0.0;
//    }
//
//    public void setTotalPremiumPeriod(Double totalPremiumPeriod) {
//        this.totalPremiumPeriod = totalPremiumPeriod;
//    }
//
//    @Override
//    public PremiumTotals clone() {
//        try {
//            return (PremiumTotals) super.clone();
//        } catch (CloneNotSupportedException e) {
//            throw new RuntimeException("Cloning failed", e);
//        }
//    }
//}