package mx.com.felicia.libro_diario.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;

public class FloreteWebInitializer implements WebApplicationInitializer {
	
	private static final Class<?>[] configurationClasses = new Class<?>[] { FchLegalConfiguration.class};
		
	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {      
		registerListener(servletContext);	
		registerDispatcherServlet(servletContext);
		registerCxfServlet(servletContext);
    }
	
	private void registerListener(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext rootContext = createContext(configurationClasses);
		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.addListener(new RequestContextListener());		
		servletContext.addListener(new HttpSessionListener());
	}
		
	private void registerDispatcherServlet(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(FchLegalConfiguration.class);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"zkLoader", new DHtmlLayoutServlet());

		dispatcher.setInitParameter("update-uri", "/zkau");

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.zul");
		
		dispatcher = servletContext.addServlet(
				"auEngine", new DHtmlUpdateServlet());
		dispatcher.addMapping("/zkau/*");
		
		dispatcher.setInitParameter("class-resource", "true");
		dispatcher.addMapping("*.dsp");
		
		
	}

	private void registerCxfServlet(ServletContext servletContext){
		ServletRegistration.Dynamic dispatcher 
		  = servletContext.addServlet("dispatcher", new CXFServlet());
		dispatcher.addMapping("/services/*");
		
	}
	
	
	private AnnotationConfigWebApplicationContext createContext(
			final Class<?>... annotatedClasses) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

		context.register(annotatedClasses);
		return context;
	}

}