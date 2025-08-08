package org.ace.accounting.student;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.common.Course;
import org.ace.java.component.idgen.service.IDInterceptor;


@Entity
@Table(name= "STUDENT")
@TableGenerator(name = "STUDENT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "STUDENT_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
		@NamedQuery(name = "Student.findStudentId", query = "SELECT s.id FROM Student s ") })
@EntityListeners(IDInterceptor.class)
public class Student implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "STUDENT_GEN")
	private String id;		
	private String name;
	private Integer age;
	
	@Enumerated(EnumType.STRING)
	private Course course;
	
	private String gender;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	private String address;
	
	@Version
	private int version;

	@Embedded
	private BasicEntity basicEntity;
	
	
	public Student() {
		
	}
	
	public Student(String id, String name, Integer age, Course course, String gender, Date dateOfBirth, String address,
			int version, BasicEntity basicEntity) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.course = course;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.version = version;
		this.basicEntity = basicEntity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) { 
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public BasicEntity getBasicEntity() {
		return basicEntity;
	}

	public void setBasicEntity(BasicEntity basicEntity) {
		this.basicEntity = basicEntity;
	}
}
