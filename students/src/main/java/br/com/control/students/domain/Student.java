package br.com.control.students.domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "students")
public class Student {
	
	private String id; 
	private String name;
	private long studentNumber;
	private String email; 
	private List<String> courseList;
	private float gpa;
	
}
