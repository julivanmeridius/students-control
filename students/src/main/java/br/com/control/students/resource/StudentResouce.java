package br.com.control.students.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.control.students.domain.Student;
import br.com.control.students.dto.StudentDTO;
import br.com.control.students.service.StudentService;
import br.com.control.students.util.ObjectMapperUtils;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/students")
public class StudentResouce {

	@Autowired
	private StudentService service;

	@ApiOperation(
			value = "${swagger.api.consulta.todos.alunos.value}",
			notes = "${swagger.api.consulta.todos.alunos.notes}",
			tags = { "Estudantes, Controle" })
	@GetMapping(value = "/")
	public List<StudentDTO> getAllStudents() {
		return ObjectMapperUtils.mapAll(service.findAll(), StudentDTO.class);
	}

	@ApiOperation(
			value = "${swagger.api.consulta.aluno.numeroaluno.value}",
			notes = "${swagger.api.consulta.aluno.numeroaluno.notes}",
			tags = { "Estudantes, Controle" })
	@GetMapping(value = "/byStudentNumber/{studentNumber}")
	public StudentDTO getStudentByStudentNumber(@PathVariable("studentNumber") Long studentNumber) {
		return ObjectMapperUtils.map(service.findByStudentNumber(studentNumber), StudentDTO.class);
	}

	@ApiOperation(
			value = "${swagger.api.consulta.aluno.email.value}",
			notes = "${swagger.api.consulta.aluno.email.notes}",
			tags = { "Estudantes, Controle" })
	@GetMapping(value = "/byEmail/{email}")
	public StudentDTO getStudentByEmail(@PathVariable("email") String email) {
		return ObjectMapperUtils.map(service.findByEmail(email), StudentDTO.class);
	}

	@ApiOperation(
			value = "${swagger.api.consulta.aluno.gpa.value}",
			notes = "${swagger.api.consulta.aluno.gpa.notes}",
			tags = { "Estudantes, Controle" })
	@GetMapping(value = "/orderByGpa")
	public List<StudentDTO> findAllByOrderByGpaDesc() {
		return ObjectMapperUtils.mapAll(service.findAllByOrderByGpaDesc(), StudentDTO.class);
	}

	@ApiOperation(
			value = "${swagger.api.criar.aluno.value}",
			notes = "${swagger.api.criar.aluno.notes}",
			tags = { "Estudantes, Controle" })
	@PostMapping(value = "/save")
	public ResponseEntity<StudentDTO> saveOrUpdateStudent(@RequestBody StudentDTO studentDTO) {
		return new ResponseEntity<StudentDTO>(ObjectMapperUtils
				.map(service.saveOrUpdateStudent(ObjectMapperUtils.map(studentDTO, Student.class)), StudentDTO.class),
				new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiOperation(
			value = "${swagger.api.apagar.aluno.value}",
			notes = "${swagger.api.apagar.aluno.notes}",
			tags = { "Estudantes, Controle" })
	@DeleteMapping(value = "/delete/{studentNumber}")
	public ResponseEntity<String> deleteStudentByStudentNumber(@PathVariable long studentNumber) {
		service.deleteStudentById(service.findByStudentNumber(studentNumber).getId());
		return ResponseEntity.status(HttpStatus.OK).body("Student Deleted from the System Successfully!");
	}
}
