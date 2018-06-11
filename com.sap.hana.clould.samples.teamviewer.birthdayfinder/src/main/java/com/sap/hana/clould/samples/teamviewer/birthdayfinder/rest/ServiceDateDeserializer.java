package com.sap.hana.clould.samples.teamviewer.birthdayfinder.rest;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class ServiceDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String text = jp.getText();
		long dateMillis = Long.parseLong(text.replaceAll("\\D+(\\d+)\\D+", "$1"));
		return new Date(dateMillis);
	}

}
