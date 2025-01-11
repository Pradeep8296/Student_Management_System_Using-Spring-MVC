package batch.e3.student_crud.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import batch.e3.student_crud.dto.Student;
import batch.e3.student_crud.repository.StudentRepository;



@Service
public class StudentService {

	@Autowired
	StudentRepository repository;

	public ResponseEntity<Object> add(Student student) {
		double percentage = (student.getMaths() + student.getScience() + student.getEnglish()) / 3.0;
		student.setPercentage(percentage);
		if (!repository.existsByMobile(student.getMobile())) {
			repository.save(student);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "Data Created Success");
			map.put("statusCode", 201);
			map.put("data", student);
			return new ResponseEntity<Object>(map,HttpStatus.CREATED);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", "Mobile Number already exists");
			return new ResponseEntity<Object>(map,HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	public ResponseEntity<Object> save(List<Student> students) {
		for(Student student:students) {
			if (repository.existsByMobile(student.getMobile())) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("error", "Mobile Number Already Exists");
				return new ResponseEntity<Object>(map, HttpStatus.UNPROCESSABLE_ENTITY);
			} else {
				student.setPercentage((student.getScience() + student.getMaths() + student.getEnglish()) / 3.0);
			}
		}
		repository.saveAll(students);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Record Saved Success");
		map.put("data", students);
		return new ResponseEntity<Object>(map, HttpStatus.CREATED);
	}


	public ResponseEntity<Object> fetchById(int id) {
		Optional<Student> optional=repository.findById(id);
		if(optional.isEmpty()) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("error", "no data found with the id "+id);
			return  new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "Data found successfully");
			map.put("Student Data", optional);
			return new ResponseEntity<Object>(map,HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> fetchAllRecords() {
		List<Student>student=repository.findAll();
		if(student.isEmpty()) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("error", "no data found");
			return  new ResponseEntity<Object>(map,HttpStatus.UNPROCESSABLE_ENTITY);
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", " All Data found successfully");
			map.put("Student Data", student);
			return new ResponseEntity<Object>(map,HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> fetchByName(String name) {
		Optional<Student> optional=repository.findByName(name);
		if(optional.isEmpty()) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("error", "no data found with the name "+name);
			return  new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "Data found successfully");
			map.put("Student Data", optional);
			return new ResponseEntity<Object>(map,HttpStatus.OK);
		}
	}
	public ResponseEntity<Object> fetchByMobile(long mobile) {
		Optional<Student> optional=repository.findByMobile(mobile);
		if(optional.isEmpty()) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("error", "No Record Found with Mobile: "+mobile);
			return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		}else {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("message", "Record Found Success");
			map.put("data", optional.get());
			return new ResponseEntity<Object>(map,HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> fetchByResult(String result) {
		List<Student> students=new ArrayList<Student>();
		if(result.equalsIgnoreCase("distinction"))
			students=repository.findByPercentageGreaterThanEqualAndPercentageLessThan(85,100);
		else if(result.equalsIgnoreCase("firstclass"))
			students=repository.findByPercentageGreaterThanEqualAndPercentageLessThan(60,85);
		else if(result.equalsIgnoreCase("secondclass"))
			students=repository.findByPercentageGreaterThanEqualAndPercentageLessThan(35,60);
		else
			students=repository.findByPercentageGreaterThanEqualAndPercentageLessThan(0,35);
		
		if(students.isEmpty()) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("error", "Not Data Present with Result: "+result);
			return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		}else {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("message", "Record Found Success");
			map.put("data", students);
			return new ResponseEntity<Object>(map,HttpStatus.OK);
		}
		
	}
	
	public ResponseEntity<Object> delete(int id) {
		if (repository.findById(id).isEmpty()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", "No Data Present with Id: " + id);
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		} else {
			repository.deleteById(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "Record Deleted Success");
			return new ResponseEntity<Object>(map, HttpStatus.OK);
		}
	}


	public ResponseEntity<Object> update(Student student) {
		student.setPercentage((student.getScience() + student.getMaths() + student.getEnglish()) / 3.0);
		repository.save(student);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Record Updated Success");
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}


	public ResponseEntity<Object> update(Student student, int id) {
		if (repository.findById(id).isEmpty()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", "No Data Present with Id: " + id);
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		} else {
			Student student1=repository.findById(id).get();
			if(student.getName()!=null)
				student1.setName(student.getName());
			if(student.getScience()!=0)
				student1.setScience(student.getScience());
			if(student.getMaths()!=0)
				student1.setMaths(student.getMaths());
			if(student.getEnglish()!=0)
				student1.setEnglish(student.getEnglish());
			if(student.getStandard()!=0)
				student1.setStandard(student.getStandard());
			if(student.getMobile()!=0)
				student1.setMobile(student.getMobile());
			student.setPercentage((student.getScience() + student.getMaths() + student.getEnglish()) / 3.0);
			
			repository.save(student1);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "Record Updated Success");
			return new ResponseEntity<Object>(map, HttpStatus.OK);
		}
	}


}

