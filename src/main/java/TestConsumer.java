import java.util.Collections;
import java.util.Properties;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class TestConsumer {
    public static void main(String[] args) throws Exception{
        if(args.length == 0){
            System.out.println("Enter a topic name");
            return;
        }

        String topicName = args[0];
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "test");
//        setting enable.auto.commit means offsets committed automatically, controlled by config auto.commit.interval.ms
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
//        subscribe to a topic
        System.out.println("Subscribing to topic [" + topicName+ "]");

        Gson gson = new Gson();

        try {
            consumer.subscribe(Collections.singletonList(topicName));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);

//                System.out.println("Received [" + records.count() + "] records");

                for (ConsumerRecord<String, String> record : records) {

                    String json = record.value();

                    System.out.println("Received JSON [" + json + "]");

                    Event event = gson.fromJson(json, Event.class);

                    System.out.println("Received event from topic [" + topicName + "] event [" + event.getName() + ", " + event.getNumCats() + "]");

                }
            }
        } finally {
            consumer.close();
        }
    }

}
