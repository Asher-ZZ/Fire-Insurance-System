package org.ace.accounting.system.car;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.common.TableName;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.Car)
@TableGenerator(
    name = "CAR_GEN",
    table = "ID_GEN",
    pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL",
    pkColumnValue = "CAR_GEN",
    allocationSize = 10
)
@EntityListeners(IDInterceptor.class)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CAR_GEN")
    @Column(name = "CarID")
    private Long carId;

    @Column(name = "Type")
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private String status; 

    @Column(name = "Make")
    private String make;

    @Column(name = "Model")
    private String model;

    @Column(name = "Year")
    private Integer year;

    @Column(name = "RegistrationNo")
    private String registrationNo;

    @Column(name = "Passenger")
    private Integer passenger;

    @Column(name = "BaseRate")
    private Double baseRate;

    @Column(name = "Branch")
    private String branch;

    @Enumerated(EnumType.STRING)
    @Column(name = "Category")
    private String category; 

    @Embedded
    private BasicEntity basicEntity;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    // Getters and setters

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Integer getPassenger() {
        return passenger;
    }

    public void setPassenger(Integer passenger) {
        this.passenger = passenger;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BasicEntity getBasicEntity() {
        return basicEntity;
    }

    public void setBasicEntity(BasicEntity basicEntity) {
        this.basicEntity = basicEntity;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
