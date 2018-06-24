import java.util.Properties;
import java.util.Arrays;
import org.apache.kafka.clients.consumer.Consumer;
//KafkaConsumer class, implements Consumer Interface, this client consumes records from Kafka cluster
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class TestConsumer {
    public static void main(String[] args) throws Exception{
        if(args.length == 0){
            System.out.println("Enter a topic name");
            return;
        }

        String topicName = args[0].toString();
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "test");
//        setting enable.auto.commit means offsets committed automatically, controlled by config auto.commit.interval.ms
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }
}
