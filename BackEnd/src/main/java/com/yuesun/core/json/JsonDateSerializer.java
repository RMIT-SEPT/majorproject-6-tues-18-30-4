package com.yuesun.core.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateSerializer extends com.fasterxml.jackson.databind.JsonSerializer<Date> {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void serialize(Date date, com.fasterxml.jackson.core.JsonGenerator gen,
			com.fasterxml.jackson.databind.SerializerProvider provider)
			throws IOException, com.fasterxml.jackson.core.JsonProcessingException {
		 String formattedDate = dateFormat.format(date);
         gen.writeString(formattedDate);
	}
}
