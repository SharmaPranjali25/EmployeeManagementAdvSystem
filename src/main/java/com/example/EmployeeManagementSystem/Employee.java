package com.example.EmployeeManagementSystem; 
//
//import jakarta.persistence.*; 
//
//@Entity 
//
//@Table(name = "Employees") 
//
//public class Employee { 
//
//@Id 
//
//@GeneratedValue(strategy = GenerationType.IDENTITY) 
//
//private Long id; 
//
//private String name; 
//
//private String department; 
//
//private double salary; 
//
// Constructors 
//
//public Employee() {} 
//
//public Employee(Long id, String name, String department, double salary) {
//	super();
//	this.id = id;
//	this.name = name;
//	this.department = department;
//	this.salary = salary;
//}
// Getters and Setters 
//public String getName() {
//	return name;
//}
//public Long getId() {
//	return id;
//}
//public void setId(Long id) {
//	this.id = id;
//}
//public void setName(String name) {
//	this.name = name;
//}
//public String getDepartment() { return department; } 
//
//public void setDepartment(String department) { this.department = department; } 
//
//public double getSalary() { return salary; } 
//
//public void setSalary(double salary) { this.salary = salary; } 
//
//} 
import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;
    private double salary;
}
