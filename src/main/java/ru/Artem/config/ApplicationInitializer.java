package ru.Artem.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

public class ApplicationInitializer implements WebApplicationInitializer {
    private static final String DISPATCHER = "dispatcher";

    @Override
    public void onStartup(ServletContext servletContext) {
        // 1) Регистрируем фильтр кодировки
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", encodingFilter);
        fr.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");

        // 2) Создаём контекст Spring и слушатель для него
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        servletContext.addListener(new ContextLoaderListener(context));

        // 3) Регистрируем DispatcherServlet на корень
        ServletRegistration.Dynamic servlet =
                servletContext.addServlet(DISPATCHER, new DispatcherServlet(context));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
}
