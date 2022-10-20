package br.com.control.students;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.control.students.domain.Student;
import br.com.control.students.service.StudentService;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class StudentResourceTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private StudentService studentService;

    private ObjectMapper objectMapper = new ObjectMapper();

 	private Student lazarus;
 	private Student magnus;
 	
	private final String lazarusEmail = "lazarus@test.com";
	private final String magnusEmail = "magnus@test.com";

 	private final Long lazarusNumber = 10L;
 	private final Long magnusNumber = 20L;
    
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
 	}
 	
 	@Test
    public void givenStudents_whenGetAllStudents_thenReturnJsonArray() throws Exception {
        given(studentService.findAll()).willReturn(Arrays.asList(lazarus));

        mvc.perform(get("/students/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(lazarus.getName())));
    }
 	
 	 @Test
     public void givenStudent_whenFindByStudentNumber_thenReturnJsonArray() throws Exception {
         given(studentService.findByStudentNumber(lazarusNumber)).willReturn(lazarus);

         mvc.perform(get("/students/byStudentNumber/{studentNumber}", lazarusNumber)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.name", is(lazarus.getName())));
     }

     @Test
     public void givenStudent_whenFindAllByOrderByGpaDesc_thenReturnJsonArray() throws Exception {
         given(studentService.findAllByOrderByGpaDesc()).willReturn(Arrays.asList(magnus, lazarus));

         mvc.perform(get("/students/orderByGpa/")
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$", hasSize(2)))
                 .andExpect(jsonPath("$[0].name", is(magnus.getName())));
     }

     @Test
     public void saveStudent_itShouldReturnStatusOk() throws Exception {
         given(studentService.saveOrUpdateStudent(any(Student.class))).willReturn(magnus);

         String jsonString = objectMapper.writeValueAsString(magnus);

         mvc.perform(post("/students/save/")
                 .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                 .andExpect(status().is2xxSuccessful());
     }

     @Test
     public void deleteStudentByStudentNumber_itShouldReturnStatusOk() throws Exception {
         given(studentService.findByStudentNumber(lazarusNumber)).willReturn(lazarus);
         Mockito.doNothing().when(studentService).deleteStudentById(any(String.class));

         mvc.perform(delete("/students/delete/{studentNumber}", lazarusNumber)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk());
     }
}
