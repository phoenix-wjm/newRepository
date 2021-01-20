package com.sfac.geniusdirecruit.config.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfac.geniusdirecruit.common.utile.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.*;

/**
 * @Description: Jackson Config
 * @date: 2020年4月17日
 */
@Configuration
public class JacksonConfig {
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getSerializerProvider().setNullValueSerializer(nullSerializer());
//		objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.DATE_PATTERN_1));
		objectMapper.registerModule(jacksonDatetimeModule());
		
		return objectMapper;
	}

	public JsonSerializer<Object> nullSerializer() {
		JsonSerializer<Object> nullSerializer = new JsonSerializer<Object>() {
			public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				jgen.writeString("");
			}
		};
		return nullSerializer;
	}
	
	public JavaTimeModule jacksonDatetimeModule() {
		// LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
		JavaTimeModule javaTimeModule = new JavaTimeModule();

		// LocalTime序列化和反序列化
		javaTimeModule.addSerializer(LocalTime.class, new JsonSerializer<LocalTime>() {
			public void serialize(LocalTime date, JsonGenerator jsonGenerator,
					SerializerProvider serializerProvider) throws IOException {
				String formattedDate = date.format(DateTimeUtil.DEFAULT_TIME_FORMAT);
				jsonGenerator.writeString(formattedDate);
			}

		});
		javaTimeModule.addDeserializer(LocalTime.class, new JsonDeserializer<LocalTime>() {
			@Override
			public LocalTime deserialize(JsonParser jsonParser,
                                         DeserializationContext deserializationContext)
					throws IOException, JsonProcessingException {
				String date = jsonParser.getText();
				if (StringUtils.isEmpty(date)) {
					return null;
				}
				date = date.trim();
				if (date.matches(DateTimeUtil.REGEX_TIME)) {
					return LocalTime.parse(date, DateTimeUtil.DEFAULT_TIME_FORMAT);
				} else {
					return ZonedDateTime.parse(date).toInstant()
							.atZone(ZoneId.systemDefault()).toLocalTime();
				}
			}
		});
		
		// LocalDate序列化和反序列化
		javaTimeModule.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
			public void serialize(LocalDate date, JsonGenerator jsonGenerator,
					SerializerProvider serializerProvider)
					throws IOException {
				String formattedDate = date.format(DateTimeUtil.DEFAULT_DATE_FORMAT);
				jsonGenerator.writeString(formattedDate);
			}

		});
		javaTimeModule.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
			@Override
			public LocalDate deserialize(JsonParser jsonParser,
                                         DeserializationContext deserializationContext)
					throws IOException, JsonProcessingException {
				String date = jsonParser.getText();
				if (StringUtils.isEmpty(date)) {
					return null;
				}
				date = date.trim();
				if (date.matches(DateTimeUtil.REGEX_DATE)) {
					return LocalDate.parse(date, DateTimeUtil.DEFAULT_DATE_FORMAT);
				} else {
					return ZonedDateTime.parse(date).toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDate();
				}

			}
		});
		
		// LocalDateTime序列化和反序列化
		javaTimeModule.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			public void serialize(LocalDateTime date, JsonGenerator jsonGenerator,
					SerializerProvider serializerProvider) throws IOException {
				String formattedDate = date.format(DateTimeUtil.DEFAULT_DATE_TIME_FORMAT);
				jsonGenerator.writeString(formattedDate);
			}

		});
		javaTimeModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonParser jsonParser,
                                             DeserializationContext deserializationContext)
					throws IOException, JsonProcessingException {
				String date = jsonParser.getText();
				if (StringUtils.isEmpty(date)) {
					return null;
				}
				date = date.trim();
				if (date.matches(DateTimeUtil.REGEX_DATE_TIME)) {
					return LocalDateTime.parse(date, DateTimeUtil.DEFAULT_DATE_TIME_FORMAT);
				} else {
					return ZonedDateTime.parse(date).toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDateTime();
				}
			}
		});
		return javaTimeModule;
	}
}
