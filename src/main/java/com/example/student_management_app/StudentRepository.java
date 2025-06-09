package com.example.student_management_app;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
	// NORMAL SQL CMD
	public Student findByRollNo(String rollNo);
	public Student findByEmail(String email);
	public Student findByName(String name);
	
	// HQL
//	@Query("from Student s where s.rollNo = :rollNo")
//	public Student HQLfindByRollNo(String rollNo);
	// above and below both are same
	@Query("from Student where rollNo = :rollNo")
	public Student HQLfindByRollNo(String rollNo);
	
//	@Query(value = "insert into student_table(name, age, email, department) values(:name, :age, :email, :dept) where roll_no = :rollNo", nativeQuery = true)
//	public void updateByRollNo(String name, Integer age, String email, String dept, String rollNo);

	
}
