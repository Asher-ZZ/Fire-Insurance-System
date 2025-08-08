/*
 * package org.ace.accounting.system.fire;
 * 
 * import java.io.Serializable;
 * 
 * import javax.persistence.*;
 * 
 * @Entity
 * 
 * @Table(name = "PRODUCT")
 * 
 * @TableGenerator( name = "PRODUCT_GEN", table = "ID_GEN", pkColumnName =
 * "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PRODUCT_GEN",
 * allocationSize = 10 ) public class Product implements Serializable {
 * 
 * private static final long serialVersionUID = 1L;
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.TABLE, generator = "PRODUCT_GEN")
 * 
 * @Column(name = "ProductID") private Long id; // change from String to Long
 * 
 * @Column(name = "Name", length = 100) private String name;
 * 
 * @Column(name = "PremiumRate", precision = 5, scale = 2) private Double
 * premiumRate;
 * 
 * public Product() {}
 * 
 * public Long getId() { return id; }
 * 
 * public void setId(Long id) { this.id = id; }
 * 
 * public String getName() { return name != null ? name : ""; }
 * 
 * public void setName(String name) { this.name = name; }
 * 
 * public Double getPremiumRate() { return premiumRate != null ? premiumRate :
 * 0.0; }
 * 
 * public void setPremiumRate(Double premiumRate) { this.premiumRate =
 * premiumRate; } }
 */
