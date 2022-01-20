package json.schema.producer.example.application;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class ConsumerApplication {

	public static void main(String[] args) {
		
		final Properties props = new Properties();

		// Create topic if needed

		// Add additional properties.
		props.put("bootstrap.servers", "localhost:9092");
		// props.put("schema.registry.url", "http://localhost:8081");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
		// props.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, DataRecord.class);
		//props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5000);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
		// props.put("metadata.max.age.ms", "10000");
		// props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 52428800);

		props.put(ConsumerConfig.GROUP_ID_CONFIG, "demo-consumer-2");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		String topic = "testjsonschema";

		final Consumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);

		consumer.subscribe(Pattern.compile(topic));
		while(true) {
			ConsumerRecords<String,byte[]> result = consumer.poll(Duration.ofMillis(100));
			for(ConsumerRecord<String, byte[]> record: result) {
				
				System.out.println("Value="+record.value()[3]);
			}
		}
	}
}
