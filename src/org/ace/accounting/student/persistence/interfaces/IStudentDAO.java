package org.ace.accounting.student.persistence.interfaces;

import java.util.List;

import org.ace.accounting.student.Student;
import org.ace.java.component.persistence.exception.DAOException;

public interface IStudentDAO {
	
	public List<Student> findAll() throws DAOException;
	
    public void insert(Student student) throws DAOException;
	
	public Student update(Student student) throws DAOException;
	
	public void delete(Student student) throws DAOException;


	

}
