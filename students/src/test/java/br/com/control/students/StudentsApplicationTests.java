package br.com.control.students;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mongodb.assertions.Assertions;

@SpringBootTest
class StudentsApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertTrue(Boolean.TRUE);
	}
}
