package com.example.matome.configs;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration
public class WebServiceConfig {


    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context){
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformSchemaLocations(true);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return  new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
    }


    @Bean(name ="shortest")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema shortestPathSchema){

        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("pathPort");
        definition.setTargetNamespace("http://ma2me.com/shortest");
        definition.setLocationUri("/ws");
        definition.setSchema(shortestPathSchema);
        return definition;
    }

    @Bean
    public XsdSchema shortestPathSchema(){
        return  new SimpleXsdSchema(new ClassPathResource("ShortestPath.xsd"));
    }
}
