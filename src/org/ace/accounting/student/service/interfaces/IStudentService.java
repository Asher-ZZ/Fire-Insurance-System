package org.ace.accounting.student.service.interfaces;

import java.util.List;

import org.ace.accounting.student.Student;
import org.ace.java.component.SystemException;

public interface IStudentService {

	public List<Student> findAllStudent() throws SystemException;

	public void addNewStudent(Student student) throws SystemException;

	public void updateStudent(Student student) throws SystemException;

	public void deleteStudent(Student student) throws SystemException;

}
