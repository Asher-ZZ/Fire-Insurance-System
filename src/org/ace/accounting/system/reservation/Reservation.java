package org.ace.accounting.system.reservation;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.common.TableName;
import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.customer.Renter;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.Reservation)
@TableGenerator(
    name = "RESERVATION_GEN",
    table = "ID_GEN",
    pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL",
    pkColumnValue = "RESERVATION_GEN",
    allocationSize = 10
)
@EntityListeners(IDInterceptor.class)
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "RESERVATION_GEN")
    @Column(name = "ReserveID")
    private Long reserveId;

    @ManyToOne
    @JoinColumn(name = "RenterID")
    private Renter renter;

    @ManyToOne
    @JoinColumn(name = "CarID")
    private Car car;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "EndDate")
    private LocalDateTime endDate;

    public Renter getRenter() {
		return renter;
	}

	public void setRenter(Renter renter) {
		this.renter = renter;
	}

	@Column(name = "DailyRate")
    private Double dailyRate;

    @Column(name = "TotalCost")
    private Double totalCost;

    
    @Column(name = "Status")
    private String status; // store enum as string

    
    @Column(name = "RentalType")
    private String rentalType; // store enum as string

    @Embedded
    private BasicEntity basicEntity;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    // Getters and setters

    public Long getReserveId() {
        return reserveId;
    }

    public void setReserveId(Long reserveId) {
        this.reserveId = reserveId;
    }

    public Renter getCustomer() {
        return renter;
    }

    public void setCustomer(Renter renter) {
        this.renter = renter;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(Double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
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
