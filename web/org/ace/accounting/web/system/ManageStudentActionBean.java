package org.ace.accounting.web.system;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.accounting.common.Course;
import org.ace.accounting.common.validation.MessageId;
import org.ace.accounting.student.Student;
import org.ace.accounting.student.service.interfaces.IStudentService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "ManageStudentActionBean")
@ViewScoped
public class ManageStudentActionBean extends BaseBean {

	@ManagedProperty(value = "#{StudentService}")
	private IStudentService studentService;

	public void setStudentService(IStudentService studentService) {
		this.studentService = studentService;
	}

	private boolean createNew;
	private Student student;
	private List<Student> studentList;
	private Student selectStudent;
	
	//mindate maxdate
	LocalDate today = LocalDate.now();		
	private Date minDate = toDate(LocalDate.of(1990, 1, 1));
	private Date maxDate = toDate(today);

	private Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	@PostConstruct
	public void init() {
		createNewStudent();
		rebindData();
		
	}

	public Course[] getCoursesValue() {
		return Course.values();
	}

	public void createNewStudent() {
		createNew = true;
		student = new Student();
	}

	public void rebindData() {
		studentList = studentService.findAllStudent();
	}

	public void prepareUpdateStudent(Student student) {
		createNew = false;
		this.student = student;   
	}

	public void addNewStudent() {
		try {
			studentService.addNewStudent(student);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, student.getName());
			createNewStudent();
			rebindData();
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public void updateStudent() {
		try {
			studentService.updateStudent(student);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, student.getName());
			createNewStudent();
			rebindData();
		} catch (SystemException ex) {
			handleSysException(ex);
		}
	}

	public String deleteStudent(Student student) {

		try {
			studentService.deleteStudent(student);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, student.getName());
		} catch (SystemException ex) {
			handleSysException(ex);
		}

		createNewStudent();
		rebindData();
		return null;
	}	

	public void calculateAge() {
		if (student.getDateOfBirth() == null) {
			student.setAge(0);
		}
		LocalDate dob = student.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		int age = Period.between(dob, LocalDate.now()).getYears();
		student.setAge(age);
	}

	public void studentDatail(Student student) {
		this.selectStudent = student;
	}

//	public void studentDetailClose(AjaxBehaviorEvent event) {
//		selectStudent = null;
//	}

	public boolean isCreateNew() {
		return createNew;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Student getSelectStudent() {
		return selectStudent;
	}

	public void setSelectStudent(Student selectStudent) {
		this.selectStudent = selectStudent;
	}
}
