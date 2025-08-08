package org.ace.accounting.student.service;

import java.util.List;


import javax.annotation.Resource;

import org.ace.accounting.student.Student;
import org.ace.accounting.student.persistence.interfaces.IStudentDAO;
import org.ace.accounting.student.service.interfaces.IStudentService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "StudentService")
public class StudentService extends BaseService implements IStudentService {

	@Resource(name = "StudentDAO")
	private IStudentDAO studentDAO;


	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Student> findAllStudent() throws SystemException {
		List<Student> result = null;
		try {
			result = studentDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of Student)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewStudent(Student student) throws SystemException {
		try {
			studentDAO.insert(student);
			
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add new student", e);
		
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateStudent(Student student) throws SystemException {
		try {
			studentDAO.update(student);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update student", e);	
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteStudent(Student student) throws SystemException {
		try {
			studentDAO.delete(student);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete user", e);
		}
	}




}