package br.com.control.students.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.control.students.domain.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
	
	Student findByStudentNumber(long studentNumber);
	
	Student findByEmail(String email);
	
	List<Student> findAllByOrderByGpaDesc();
	
}
