package org.ace.accounting.system.reservation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.common.TableName;
import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.ReserveStatus;
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
    private String id;

   
	@ManyToOne
    @JoinColumn(name = "RenterID")
    private Renter renter;

    @ManyToOne
    @JoinColumn(name = "CarID")
    private Car car;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "StartDate")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "EndDate")
    private Date endDate;

	@Column(name = "DailyRate")
    private Double dailyRate;

    @Column(name = "TotalCost")
    private Double totalCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private ReserveStatus reserveStatus; // store enum as string
  

	@Column(name = "RentalType")
    private String rentalType; // store enum as string

    @Embedded
    private BasicEntity basicEntity;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    // Getters and setters


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
 

    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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

    public ReserveStatus getReserveStatus() {
		return reserveStatus;
	}

	public void setReserveStatus(ReserveStatus reserveStatus) {
		this.reserveStatus = reserveStatus;
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
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	 public Renter getRenter() {
			return renter;
		}

		public void setRenter(Renter renter) {
			this.renter = renter;
		}
}
