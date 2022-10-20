package br.com.control.students.service;

import java.util.List;

import br.com.control.students.domain.Student;

public interface StudentService {

	List<Student> findAll();
	
	Student findByStudentNumber(long studentNumber);
	
	Student findByEmail(String email);
	
	List<Student> findAllByOrderByGpaDesc();
	
	Student saveOrUpdateStudent(Student student);
	
	void deleteStudentById(String id);
}
