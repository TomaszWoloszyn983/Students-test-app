package com.amigoscode.spring_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCourseApplication.class, args);


		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
		DataSource ds = (DataSource)applicationContext.getBean("dataSource");
		JdbcTemplate jt = new JdbcTemplate(ds);

//		jt.execute("create table student2 (id int, name varchar)");
		System.out.println("Nie dodajemy więcej studentów");
//		jt.execute("insert into student2 (id, name) values (1, 'student_5')");
//		jt.execute("insert into student2 (id, name) values (2, 'student_4')");
		jt.execute("select * from student2 LIMIT 100");


		int counter = 0;
		Object[] parameters = new Object[] {new Integer(1)};
		System.out.println("Próbujemy odczytać "+parameters);
		if(counter < parameters.length){
			System.out.println("Pętla dla counter "+counter);
//			Object o = jt.queryForObject("select '*' from student2 where id=1",
//					parameters, String.class);
//			Object o = jt.queryForObject("select '*' from student2", String.class);
			System.out.println("Object ");
			counter++;
		}
//		System.out.println((String)o);
	}
}
