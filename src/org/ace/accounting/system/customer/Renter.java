package org.ace.accounting.system.customer;

import java.io.Serializable;

import java.time.LocalDate;

import javax.persistence.*;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.common.Gender;
import org.ace.accounting.common.TableName;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name=TableName.CarRenter)
@TableGenerator(
        name = "RENTER_GEN",
        table = "ID_GEN",
        pkColumnName = "GEN_NAME",
        valueColumnName = "GEN_VAL",
        pkColumnValue = "RENTER_GEN",
        allocationSize = 10
    )
@EntityListeners(IDInterceptor.class)
public class Renter implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	 @GeneratedValue(strategy = GenerationType.TABLE, generator = "RENTER_GEN")
	 @Column(name="RenterID")
	 private Long renterId;
	 
	 @Column(name="Name")
	    private String name;
	 
	 @Column(name="PhoneNumber")
	    private String phoneNumber;
	 
	 @Column(name="DriverLicence")
	    private String driverLicence;
	    
	 @Column(name="Address")
	    private String address;
	    
	 @Column(name="Email")
	    private String email;
	    
	 @Enumerated(EnumType.STRING)
	 @Column(name="Gender")
	    private Gender gender;

	
	@Column(name="ID_Number")
	    private String idNumber;

	 @Column(name="ID_Type")  
	    private String idType;

	 @Column(name="Date_Of_Birth") 
	    private LocalDate dateOfBirth;

	 @Embedded
		private BasicEntity basicEntity;
	 
	 	@Version
		@Column(name = "VERSION") 
	    private Integer version;
		public Long getRenterId() {
			return renterId;
		}
		public void setRenterId(Long renterId) {
			this.renterId = renterId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getDriverLicence() {
			return driverLicence;
		}
		public void setDriverLicence(String driverLicence) {
			this.driverLicence = driverLicence;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		
		public String getIdNumber() {
			return idNumber;
		}
		public void setIdNumber(String idNumber) {
			this.idNumber = idNumber;
		}
		public String getIdType() {
			return idType;
		}
		public void setIdType(String idType) {
			this.idType = idType;
		}
		public LocalDate getDateOfBirth() {
			return dateOfBirth;
		}
		public void setDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
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
		 public Gender getGender() {
				return gender;
			}
			public void setGender(Gender gender) {
				this.gender = gender;
			}
		
	 	
}
