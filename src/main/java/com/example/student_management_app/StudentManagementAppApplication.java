package com.example.student_management_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class StudentManagementAppApplication implements CommandLineRunner {

	@Autowired
	private StudentRepository studentRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(StudentManagementAppApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Student s1 = new Student();
		s1.setName("Sameera");
		s1.setGender("Female");
		s1.setEmail("sameera12@gmail.com");
		s1.setAge(12);
		s1.setDepartment("Biology");
		s1.setRollNo("201921");
		studentRepo.save(s1);
		
		Student s2 = new Student();
		s2.setName("Sagar");
		s2.setGender("Male");
		s2.setEmail("sagar24@gmail.com");
		s2.setAge(14);
		s2.setDepartment("Mathematics");
		s2.setRollNo("201927");
		studentRepo.save(s2);
		
		Student s3 = new Student();
		s3.setName("Sandy");
		s3.setGender("Male");
		s3.setEmail("malesigma82@gmail.com");
		s3.setAge(13);
		s3.setDepartment("Economics");
		s3.setRollNo("201925");
		studentRepo.save(s3);
	}

}

