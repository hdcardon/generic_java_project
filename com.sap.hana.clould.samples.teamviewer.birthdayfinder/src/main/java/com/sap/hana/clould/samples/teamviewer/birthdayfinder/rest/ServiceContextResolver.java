package com.sap.hana.clould.samples.teamviewer.birthdayfinder.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ServiceContextResolver implements ContextResolver<ObjectMapper> {

	private ObjectMapper objectMapper;

	public ServiceContextResolver() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		this.objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public ObjectMapper getContext(Class<?> type) {
		return objectMapper;
	}

}