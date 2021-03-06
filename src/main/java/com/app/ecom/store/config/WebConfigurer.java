package com.app.ecom.store.config;

import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.app.ecom.store.dto.jaxb.ProductsType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class WebConfigurer {
	
	private JAXBContext jaxbContext;
	
	private JAXBContext getJaxbContext() throws JAXBException {
		if (jaxbContext == null)
			jaxbContext = JAXBContext.newInstance(ProductsType.class);
		return jaxbContext;
	}
	
	@Bean("unmarshaller")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Unmarshaller getUnmarshaller() throws JAXBException {
		return getJaxbContext().createUnmarshaller();
	}

	@Bean("marshaller")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Marshaller getMarshaller() throws JAXBException {
		Marshaller marshaller = getJaxbContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		return marshaller;
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(465);
	    mailSender.setUsername("**@gmail.com");
	    mailSender.setPassword("**");	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.socketFactory.port", 465);
	    props.put("mail.smtp.socketFactory.fallback", false);
	    props.put("mail.smtp.starttls.enable", false);
	    props.put("mail.debug", "true");
	    props.put("mail.smtp.ssl.enable", true);	    
	    return mailSender;
	}
}
