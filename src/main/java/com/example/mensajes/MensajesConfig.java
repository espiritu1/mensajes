package com.example.mensajes;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
@ComponentScan
public class MensajesConfig extends WsConfigurerAdapter{
  
  @Bean
  public XsdSchema mensajesSchema(){
    return new SimpleXsdSchema(new ClassPathResource("mensajes.xsd"));
  }

  @Bean
  public ServletRegistrationBean messageDispatcherservlet( ApplicationContext ApplicationContex ){
    MessageDispatcherServlet servlet = new  MessageDispatcherServlet();
    servlet.setApplicationContext(ApplicationContex);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(servlet, "/ws/*");
  }
 
  @Bean (name = "mensajes")
  public DefaultWsdl11Definition defaultWsdl11Definition (XsdSchema mensajesSchema){
    DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition ();
    wsdl.setPortTypeName("mensajesPort");
    wsdl.setLocationUri("/ws/mensajes");
    wsdl.setTargetNamespace("http://www.espiritu.me/mensajes");
    wsdl.setSchema(mensajesSchema);
    return wsdl;
  }
  @Bean
  public FilterRegistrationBean corsFilter(){
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config); 
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
      bean.setOrder(0);
      return bean;
  }
}