package com.amigoscode.spring_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@SpringBootApplication
public class SpringCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCourseApplication.class, args);

			/*
			The code below create connection to my database using JDBC.
			Currently the application uses Hibernate to connect to the database.
			*/
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
//		DataSource ds = (DataSource)applicationContext.getBean("dataSource");
//		JdbcTemplate jt = new JdbcTemplate(ds);
//
//		jt.execute("create table student (id int, name varchar)");
//		System.out.println("Nie dodajemy więcej studentów");
//		jt.execute("insert into student2 (id, name) values (1, 'student_5')");
//		jt.execute("insert into student2 (id, name) values (2, 'student_4')");
//		jt.execute("select * from student2 LIMIT 100");
//
//
//		int counter = 0;
//		Object[] parameters = new Object[] {new Integer(1)};
//		System.out.println("Parameters length "+parameters.length);
//		System.out.println("Próbujemy odczytać ");
//		for(int i=0; i< parameters.length; i++){
//
//			System.out.println("Pętla dla counter "+i);
//			Object o = jt.queryForObject("select '*' from student2 where id=0",
//					parameters, String.class);
////			Student [] st = jt.queryForObject("select * from student", Student.class);
//			System.out.println("Object "+o);
//			counter++;
//		}


//		System.out.println((String)o);
	}
}
