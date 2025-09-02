package org.ace.accounting.system.car;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.common.TableName;
import org.ace.java.component.idgen.service.IDInterceptor;
@Entity
@Table(name=TableName.Customer)
@TableGenerator(
        name = "CUSTOMER_GEN",
        table = "ID_GEN",
        pkColumnName = "GEN_NAME",
        valueColumnName = "GEN_VAL",
        pkColumnValue = "CUSTOMER_GEN",
        allocationSize = 10
    )
@EntityListeners(IDInterceptor.class)
public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;
	 @GeneratedValue(strategy = GenerationType.TABLE, generator = "CUSTOMER_GEN")
	 @Column(name="UserID")
	 private Long userId;
	 
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
	    
	 @Column(name="Gender")
	    private String gender;

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
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
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
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
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
		
	 	
}
