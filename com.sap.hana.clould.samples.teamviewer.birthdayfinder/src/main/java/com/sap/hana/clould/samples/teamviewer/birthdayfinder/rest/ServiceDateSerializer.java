package com.sap.hana.clould.samples.teamviewer.birthdayfinder.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.sap.hana.clould.samples.teamviewer.birthdayfinder.date.DateUtil;

public class ServiceDateSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat utcDateTimeFormat;

	public ServiceDateSerializer() {
		this(new DateUtil().getUtcTimestampFormat());
	}

	public ServiceDateSerializer(SimpleDateFormat utcDateTimeFormat) {
		this.utcDateTimeFormat = utcDateTimeFormat;
	}

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(utcDateTimeFormat.format(value));
	}

}
