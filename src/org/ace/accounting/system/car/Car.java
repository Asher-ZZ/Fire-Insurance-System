package org.ace.accounting.system.car;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.common.TableName;
import org.ace.accounting.system.car.enumTypes.CarBranch;
import org.ace.accounting.system.car.enumTypes.CarStatus;
import org.ace.accounting.system.car.enumTypes.Category;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.Car)
@TableGenerator(name = "CAR_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CAR_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class Car implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CAR_GEN")
	@Column(name = "CarID") 
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "Type")
	private String type;

	@Enumerated(EnumType.STRING)
	@Column(name = "Status")
	private CarStatus carStatus = CarStatus.AVAILABLE; // default

	

	@OneToMany(mappedBy = "Car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();
	
	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

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

	@Enumerated(EnumType.STRING)
	@Column(name = "CarBranch")
	private CarBranch carBranch;

	@Enumerated(EnumType.STRING)
	@Column(name = "Category")
	private Category category;

	/*
	 * @Column(name = "PhotoPath") private String photoPath;
	 */

	@Embedded
	private BasicEntity basicEntity;

	@Version
	@Column(name = "VERSION")
	private Integer version;

	// Getters and setters


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public CarBranch getCarBranch() {
		return carBranch;
	}

	public void setCarBranch(CarBranch carBranch) {
		this.carBranch = carBranch;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BasicEntity getBasicEntity() {
		return basicEntity;
	}

	

	public CarStatus getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(CarStatus carStatus) {
		this.carStatus = carStatus;
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

	

	public void setCarStatus(String string) {
		
	}


//	public String getCarId() {
//		return carId;
//	}
	/*
	 * public void setCarId(String carId) { this.carId = carId; }
	 * 
	 * public String getPhotoPath() { return photoPath; } public void
	 * setPhotoPath(String photoPath) { this.photoPath = photoPath; }
	 */
}
