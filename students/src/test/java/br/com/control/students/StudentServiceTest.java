package br.com.control.students;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.control.students.domain.Student;
import br.com.control.students.repository.StudentRepository;
import br.com.control.students.service.StudentService;
import br.com.control.students.service.impl.StudentServiceImpl;

@SpringBootTest
public class StudentServiceTest {

	@InjectMocks
	private StudentService studentService = new StudentServiceImpl();

	@Mock
	private StudentRepository studentRepository;

	// --Setup of Objects for the Tests
	private Student lazarus;
	private Student magnus;

	private final Long lazarusNumber = 10L;
	private final Long magnusNumber = 20L;
	private final String lazarusEmail = "lazarus@test.com";
	private final String magnusEmail = "magnus@test.com";
	private final List<Student> students = new ArrayList<>();

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		
		lazarus = new Student();
		lazarus.setId("id123");
		lazarus.setName("Lazarus Sinclair");
		lazarus.setEmail(lazarusEmail);
		lazarus.setStudentNumber(lazarusNumber);
		lazarus.setCourseList(Arrays.asList("Biologia", "Quimica"));
		lazarus.setGpa(3.37f);

		magnus = new Student();
		magnus.setId("id555");
		magnus.setName("Magnus Stone");
		magnus.setEmail(magnusEmail);
		magnus.setStudentNumber(magnusNumber);
		magnus.setCourseList(Arrays.asList("Matematica", "Ingles", "Frances", "Espanhol"));
		magnus.setGpa(3.58f);

		students.add(lazarus);
		students.add(magnus);

		Mockito
			.when(studentRepository.findAll())
			.thenReturn(students);

		Mockito
			.when(studentRepository.findByStudentNumber(lazarusNumber))
			.thenReturn(lazarus);

		Mockito
			.when(studentRepository.findByEmail(magnusEmail))
			.thenReturn(magnus);

		Mockito
			.when(studentRepository.findAllByOrderByGpaDesc())
			.thenReturn(students.stream()
				.sorted(Comparator.comparing(Student::getGpa)
						.reversed())
						.collect(Collectors.toList()));

		Mockito
			.when(studentRepository.save(magnus))
			.thenReturn(magnus);
	}

	@Test
    public void testFindAll_thenStudentListShouldBeReturned() {
        List<Student> foundStudents = studentService.findAll();

        Assertions.assertNotNull(foundStudents);
        Assertions.assertEquals(2, foundStudents.size());
    }
	
	@Test
    public void testFindByStudentNumber10_thenLazarusShouldBeReturned() {
        Student found = studentService.findByStudentNumber(lazarusNumber);

        assertNotNull(found);
        assertEquals(lazarus.getName(), found.getName());
        assertEquals(lazarus.getId(), found.getId());
    }
	
	@Test
    public void testFindByEmail_thenMagnusShouldBeReturned() {
        Student found = studentService.findByEmail(magnusEmail);

        assertNotNull(found);
        assertEquals(magnus.getName(), found.getName());
        assertEquals(magnus.getId(), found.getId());
    }
	
	@Test
    public void testFindAllByOrderByGpaDesc_thenStudentsShouldBeReturned_byGPADesc() {
        List<Student> foundStudents = studentService.findAllByOrderByGpaDesc();

        assertNotNull(foundStudents);
        assertEquals(2, foundStudents.size());
        assertEquals(magnus.getName(), foundStudents.get(0).getName());
        assertEquals(magnus.getId(), foundStudents.get(0).getId());
    }

    @Test
    public void testSaveOrUpdateStudent_thenStudentShouldBeReturned() {
        Student found = studentService.saveOrUpdateStudent(magnus);

        assertNotNull(found);
        assertEquals(magnus.getName(), found.getName());
        assertEquals(magnus.getId(), found.getId());
    }

    @Test
    public void testDeleteStudentById() {
        studentService.deleteStudentById(lazarus.getId());

        Mockito.verify(studentRepository, Mockito.times(1))
                .deleteById(lazarus.getId());
    }

}
