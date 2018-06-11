package com.sap.hana.clould.samples.teamviewer.birthdayfinder.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sap.hana.clould.samples.teamviewer.birthdayfinder.birthday.BirthdayService;

public class ServiceApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public ServiceApplication() {
		singletons.add(new BirthdayService());

		singletons.add(new JacksonJsonProvider());
		singletons.add(new ServiceContextResolver());
		singletons.add(new ServiceExceptionMapper());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
