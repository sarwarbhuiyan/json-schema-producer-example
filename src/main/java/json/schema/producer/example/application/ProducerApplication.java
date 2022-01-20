package json.schema.producer.example.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.confluent.kafka.schemaregistry.json.JsonSchemaUtils;
import io.confluent.kafka.schemaregistry.json.jackson.Jackson;
import json.schema.producer.example.pojo.User;

public class ProducerApplication {
	
	public static void main(String[] args) {
		
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
		  "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
		  "io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer");
		props.put("schema.registry.url", "http://127.0.0.1:8081");
		
		props.put("use.latest.version", "true");
		props.put("auto.register.schemas", "false");
		
		props.put("json.fail.invalid.schema", "true"); // if you want the data validation to happen according to JSON schema
		

		Producer<String, User> producer = new KafkaProducer<String, User>(props);

		String topic = "testjsonschema";
		String key = "testkey";
		User user = new User("John", "Doe", 33);
		user.setEmail("user@example.com");
		System.out.println("JSONSchemaUtils.getValue() = "+JsonSchemaUtils.getValue(user));
		
//		JSONObject rawSchema;
//		ObjectMapper objectMapper =  Jackson.newObjectMapper();
//		try {
//			rawSchema = new JSONObject(new JSONTokener(new FileInputStream("src/main/resources/user.json")));
//			Schema schema = SchemaLoader.builder()
//					.useDefaults(true)
//					.schemaJson(rawSchema)
//					.build()
//					.load().build();
//			schema.validate(objectMapper.convertValue(JsonSchemaUtils.getValue(user), JSONObject.class));
//		} catch (Throwable e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		ProducerRecord<String, User> record
		      = new ProducerRecord<String, User>(topic, key, user);
		try {
			producer.send(record).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			producer.close();
			
		}
		
	}

}
