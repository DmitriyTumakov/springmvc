package ru.netology.initializer;

import jakarta.servlet.ServletContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        var context = new AnnotationConfigWebApplicationContext();
        context.scan("ru.netology");
        context.refresh();

        var servlet = new DispatcherServlet(context);
        var registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
