package br.com.control.students.dto;

import java.util.List;

import lombok.Data;

@Data
public class StudentDTO {

	private String id; 
	private String name;
	private long studentNumber;
	private String email; 
	private List<String> courseList;
	private float gpa; 
	
}
