package batch.e3.student_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import batch.e3.student_crud.dto.Student;
import batch.e3.student_crud.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {
	@Autowired
	StudentService service;
    //Adding single student record
	@PostMapping("/students")
	public ResponseEntity<Object> addStudent(@RequestBody Student student) {
		return service.add(student);
	}

	// Adding Multiple Student Record
	@PostMapping("/students/multiple")
	public ResponseEntity<Object> saveStudent(@RequestBody List<Student> students) {
		return service.save(students);
	}

	@GetMapping("/students")
	public ResponseEntity<Object> fetchAllRecords() {
		return service.fetchAllRecords();
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Object> fetchById(@PathVariable int id) {
		return service.fetchById(id);
	}

	@GetMapping("/students/name/{name}")
	public ResponseEntity<Object> fetchByName(@PathVariable String name) {
		return service.fetchByName(name);

	}

	@GetMapping("/students/mobile/{mobile}")
	public ResponseEntity<Object> fetchByMobile(@PathVariable long mobile) {
		return service.fetchByMobile(mobile);

	}

	@GetMapping("/students/result/{result}")
	public ResponseEntity<Object> fetchByResult(@PathVariable String result) {
		return service.fetchByResult(result);

	}

	// Delete By Id
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Object> delete(@PathVariable int id) {
		return service.delete(id);
	}

	// Update - PUT
	@PutMapping("/students")
	public ResponseEntity<Object> updateStudent(@RequestBody Student student) {
		return service.update(student);
	}

	// Update - Patch
	@PatchMapping("/students/{id}")
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Student student) {
		return service.update(student, id);
	}

}
