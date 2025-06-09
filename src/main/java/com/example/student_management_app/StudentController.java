package com.example.student_management_app;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = {"http://127.0.0.1:5500/", "http://127.0.0.1:5500/submit.html"})


public class StudentController {
	
	@Autowired
	StudentRepository studentRepo;
	
	@PostMapping("create")
	public ResponseEntity<Map<String, String>> createStudent(@RequestBody() Student studentObj) {
		
		String msg = "";
		
		Map<String, String> map = new HashMap<String, String>();
		
		if ((studentRepo.findByName(studentObj.getName()) == null) && (studentRepo.findByRollNo(studentObj.getRollNo()) == null) && (studentRepo.findByEmail(studentObj.getEmail()) == null)) {
			studentRepo.save(studentObj);
			msg = "Student Record Created Successfully...";
			
			map.put("msg", msg);
			map.put("key", "1");
			
			ResponseEntity<Map<String, String>> response = new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
			return response;
		}
		
		msg = "Student Record Already found!!!";
		map.put("msg", msg);
		map.put("key", "2");
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.ALREADY_REPORTED);
		
		/*
		 	{
			    "rollNo": "201945",
			    "name": "Max",
			    "email": "max32@gmail.com",
			    "department": "Biology",
			    "age": 17,
			    "gender": "Female"
			}
		 */
	}
	
	
	@GetMapping("getAllRecords")
	public Iterable<Student> getAllStudents() {
		return studentRepo.findAll();
	}
	
	@GetMapping("getStudent/{rollNo}")	
	public ResponseEntity<Student> getStudentById(@PathVariable(name="rollNo") String rollNo ) {
		
		Student std = studentRepo.findByRollNo(rollNo);
//		Student std = studentRepo.HQLfindByRollNo(rollNo);
		
		ResponseEntity<Student> response = new ResponseEntity<Student>(std, HttpStatus.OK);
		
		return response;
	}
	
	
	@PutMapping("updateStudentRecord")
	public ResponseEntity<String> updateStudent(@RequestBody() Student std) {
		String msg = "";
		
		if(std.getAge() == null || std.getDepartment() == null || std.getEmail() == null || std.getName() == null || std.getRollNo() == null) {
			msg = "Requires all the entries of the student record!!!";
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		
//		studentRepo.updateByRollNo(std.getName(), std.getAge(), std.getEmail(), std.getDepartment(), std.getRollNo());	// save will create a row if the specified id is not present, else it will updates the present row with given column values
		
		studentRepo.save(std);
		
		msg = "Student Record is updated Successfully...";
		ResponseEntity<String> response = new ResponseEntity<String>(msg, HttpStatus.OK);
		return response;
	}
	
	
//	@PatchMapping("updateStudentDetails")
//	public ResponseEntity<String> updateStudentDetails(@RequestBody() Student std) {
//		
//		String msg = "Student Record is updated Successfully...";
//		ResponseEntity<String> response = new ResponseEntity<String>(msg, HttpStatus.OK);
//		return response;
//	}
	
	@PostMapping("uploadStudentImage")
	public ResponseEntity<Map<String, String>> imageUploader(@RequestParam("file") MultipartFile receivedFile,
															 @RequestParam("fileName") String fileName) {
		
		File destinationLoc = new File("D:\\lara-dev-feb-2025\\spring-data-jpa\\student-management-app\\student-management-app\\src\\main\\java\\com\\example\\student_management_app\\images\\");
		
		
		File file  = new File(destinationLoc, "" + fileName + ".jpg");	// creates if it is not present
		// if present it over rides the file content with the given content

		
		Map<String, String> map = new HashMap<String, String>();
		
		try(FileOutputStream fout = new FileOutputStream(file);
			BufferedOutputStream bout = new BufferedOutputStream(fout);) {
			
			if (receivedFile.isEmpty()) {
                throw new Exception("Please upload a file.");
            }

            String contentType = receivedFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
            	throw new Exception("Only image files are allowed.");            }
			
			byte[] bytes = receivedFile.getBytes();
			
			bout.write(bytes);
			
			
			map.put(fileName, "OK");
			
		} catch (Exception e) {
			map.put(fileName, "ERROR");
			e.printStackTrace();
		}
	
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	
	}
	
	@GetMapping("getStudentImage/{rollNo}")
	public ResponseEntity<byte[]> getImage(@PathVariable String rollNo) {

		File destinationLoc = new File("D:\\lara-dev-feb-2025\\spring-data-jpa\\student-management-system\\student-management-system\\src\\main\\java\\com\\example\\student_management_system\\images\\");
		
		File file = new File(destinationLoc, "" + rollNo + ".jpg");
		
		byte[] bytes = new byte[(int) file.length()];
		
		try(FileInputStream fin = new FileInputStream(file);
			BufferedInputStream bin = new BufferedInputStream(fin);) {
			
			bin.read(bytes);
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			byte[] bytesArr = new byte[2];
			return new ResponseEntity<byte[]>(bytesArr, HttpStatus.NOT_FOUND);
		
		} catch (Exception e) {
			System.out.println("File Not Found!!!");
		}
		
		return new ResponseEntity<byte[]>(bytes, HttpStatus.OK);
	}
}
