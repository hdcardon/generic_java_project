package com.sap.hana.clould.samples.teamviewer.birthdayfinder.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<Throwable> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Context
	private MessageContext messgeContext;

	@Override
	public Response toResponse(Throwable throwable) {
		HttpServletRequest request = messgeContext.getHttpServletRequest();
		logger.error("Exception while processing service request [" + request.getMethod() + "] " + request.getRequestURI(), throwable);

		return throwable instanceof WebApplicationException ? ((WebApplicationException) throwable).getResponse() : Response.serverError().build();
	}
}