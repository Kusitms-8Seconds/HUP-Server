package eightseconds;

import com.querydsl.jpa.impl.JPAQueryFactory;
import eightseconds.global.dto.PaginationDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;

@EnableJpaAuditing
@SpringBootApplication
public class HupApplication {

	public static void main(String[] args) {
		SpringApplication.run(HupApplication.class, args);}

	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
