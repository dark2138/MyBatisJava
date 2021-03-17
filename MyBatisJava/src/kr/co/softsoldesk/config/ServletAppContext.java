package kr.co.softsoldesk.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.softsoldesk.database.MapperInterface;

// (servlet-context에서 <annotation-driven/>와 같음)
@Configuration
// Controller이 어노테이션이 설정되어 있는 클래스를 등록하는 어노테이션
@EnableWebMvc
// 스캔할 패키지 등록
@ComponentScan("kr.co.softsoldesk.controller")
@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer {
	// xml에서의 servlet-context을 자바로 구현하기 위한 클래스
	
	// properties 파일 설정을 올리고 값들을 설정해준다
	@Value("${db.classname}")
	private String db_classname;
	
	@Value("${db.url}")
	private String db_url;
	
	@Value("${db.username}")
	private String db_username;
	
	@Value("${db.password}")
	private String db_password;
	

	// controller메서드 (home())에서 반환하는 문자열 앞(경로), 뒤(확장자)에 붙을 경로를 설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	// 정적데이터 (이미지, 사운드, 동영상, js, css) 경로 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");

	}
	
	// 데이터베이스 접속 정보 관리
		@Bean
		public BasicDataSource dataSource() {
			BasicDataSource source = new BasicDataSource();
			source.setDriverClassName(db_classname);
			source.setUrl(db_url);
			source.setUsername(db_username);
			source.setPassword(db_password);
			
			return source;
		}
		
		// 쿼리문과 접속 관리하는 객체(SqlSessionFactory 접속, 쿼리 관리 객체)
		@Bean
		public SqlSessionFactory factory(BasicDataSource source) throws Exception{
			SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
			factoryBean.setDataSource(source);
			SqlSessionFactory factory = factoryBean.getObject();
			return factory;
		}	
		// 쿼리문 실행을 위한 객체(쿼리문을 관리하는 Mapper를 정의)
		@Bean
		public MapperFactoryBean<MapperInterface> test_mapper(SqlSessionFactory factory) throws Exception{
			MapperFactoryBean<MapperInterface> factoryBean = new MapperFactoryBean<MapperInterface>(MapperInterface.class);
			factoryBean.setSqlSessionFactory(factory);
			return factoryBean;
		}

	
}
