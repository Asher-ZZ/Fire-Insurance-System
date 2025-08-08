package org.ace.accounting.student.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.accounting.student.Student;
import org.ace.accounting.student.persistence.interfaces.IStudentDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("StudentDAO")
public class StudentDAO extends BasicDAO implements IStudentDAO {

	@SuppressWarnings("unchecked")
	public List<Student> findAll() throws DAOException {
		List<Student> result = null;
		try {
			Query q = em.createNamedQuery("Student.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Student", pe);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Student student) throws DAOException {
		try {
			em.persist(student);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert " + student.getClass().getName(), pe);
		}
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student update(Student student) throws DAOException {
		try {
			student = em.merge(student);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update " + student.getClass().getName(), pe);
		}
		return student;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Student student) throws DAOException {
		try {
			student = em.merge(student);
			em.remove(student);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete " + student.getClass().getName(), pe);
		}
	}

}
