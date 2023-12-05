package cz.cvut.fel.ear.library;

import jakarta.faces.webapp.FacesServlet;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ServletContextAware;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.context.request.RequestScope;

@SpringBootApplication
public class LibraryApplication implements ServletContextAware {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
		System.out.println("hello");
	}

	@Bean
	public static CustomScopeConfigurer viewScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
//		configurer.setScopes(new ImmutableMap.Builder<String, Object>().put("view", new ViewScope()).build());
		configurer.setScopes(new ImmutableMap.Builder<String, Object>().put("view", new RequestScope()).build());
		return configurer;
	}

	@Bean
	public ServletRegistrationBean<FacesServlet> servletRegistrationBean() {
		ServletRegistrationBean<FacesServlet> servletRegistrationBean = new ServletRegistrationBean<>(
				new FacesServlet(), "*.xhtml");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// Iniciar el contexto de JSF
		// http://stackoverflow.com/a/25509937/1199132
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		servletContext.setInitParameter("jakarta.faces.FACELETS_SKIP_COMMENTS", "true");
	}

}
