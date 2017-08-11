package com.example.demo;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class NewApplication {

	//Second way to define CommandLineRunner
	@Bean
	CommandLineRunner commandLineRunner(EmployeeDao empl){
		return records-> Stream.of("Amit|1000","Rahul|2000","Kumal|3000","Kapil|4000","Willam|5000")
				.forEach(values->{
					empl.save(new Employee(values.split(Pattern.quote("|"))[0],new Long(values.split(Pattern.quote("|"))[1]).longValue()));
				});
	}
	
	public static void main(String[] args) {
		SpringApplication.run(NewApplication.class, args);
	}
}


@RefreshScope
@RestController
class MyController{
	
	@GetMapping("/message")
	public String getMesssage(){
		return "This is me";
	}
}

//This First Way to define commandLineRunner
/*
 It define as component so it will load you can define it as Bean in second Way
 * */
/*@Component
class dummy implements CommandLineRunner{
	@Autowired
	EmployeeDao empl;
	@Override
	public void run(String... arg0) throws Exception {
	Stream.of("Amit|1000","Rahul|2000","Kumal|3000","Kapil|4000","Willam|5000")
	.forEach(values->{
		empl.save(new Employee(values.split(Pattern.quote("|"))[0],new Long(values.split(Pattern.quote("|"))[1]).longValue()));
	});
	}
	
}*/

//No need of Service
/*@Service
class EmployeeService{
	@Autowired
	EmployeeDao empl;
	public void save(Employee e){
		empl.save(e);
	}
}
*/
@RepositoryRestResource
interface EmployeeDao extends CrudRepository<Employee, Long>{
	
}


@Entity
class Employee
{
	
	public Employee() {//for JPA
		
	}
	
	public Employee(String ename, long esal) {
		this.ename = ename;
		this.esal = esal;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long eid;
	private String ename;
	public long getEid() {
		return eid;
	}
	public void setEid(long eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public long getEsal() {
		return esal;
	}
	public void setEsal(long esal) {
		this.esal = esal;
	}
	private long esal;
	@Override
	public String toString() {
		return "Employee [eid=" + eid + ", ename=" + ename + ", esal=" + esal + "]";
	}
	
	
}