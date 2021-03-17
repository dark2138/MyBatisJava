package kr.co.softsoldesk.config;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringConfigClass implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		// (web.xml에서 <servlet> 구현부와 같음 )
		// 프로젝트 구현을 위한 클래스 객체 생성
		// xml 에서의 appServlet와 같다
		// controller 등록
		AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
		servletAppContext.register(ServletAppContext.class);
		
		//요청 정보를 분석해서 컨트롤러를 선택하는 서블릿을 지정한다
		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		
		// 매개변수로 선언된 servletContext객체를 이용하여 servlet에 추가
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispacher", dispatcherServlet);
		
		// 부가 설정
		servlet.setLoadOnStartup(1); //추가 후 loading을 어떤 경우라도 제일 먼저 실행한다
		servlet.addMapping("/"); // 모든 경로에 적용하겠다
		// 최상위 아래 계층 다 먼저 실행
		
		//==========================================================================================
		
		// Bean 등록
		//(web.xml에서 <context-param> 구현부와 같음)
		// Bean을 정의할 xml 파일을 지원한다
		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		// (web.xml에서 <listener> 구현부와 같음)
		ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
		
		// ==========================================================================================
		
		// filter 등록
		//(web.xml에서 <filter> 구현부와 같음)
		// 파라미터 인코딩 설정
		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", (Class<? extends Filter>) CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
	    //dispatcher에 의해서 추가된 Servlet에 UTF-8로 encoding하겠다는 구현부
		filter.addMappingForServletNames(null, false, "dispatcher");

		
		
		
	}

}
