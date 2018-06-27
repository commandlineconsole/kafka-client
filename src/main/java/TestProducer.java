import java.util.Properties;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class TestProducer {
    public static void main(String[] args) throws Exception {

//     checking to see if something was passed in
        if (args.length == 0) {
            System.out.println("Enter a topic name");//TODO write a method to handle this
            return;
        }

//        assign topicName to a string variable
        String topicName = args[0];

//        create Properties object
        Properties properties = new Properties();
//        assign localhost id, put(key, value)
        properties.put("bootstrap.servers", "localhost:9092");
/*        set acknowledgements for producer requests, acks config controls criteria under which requests are considered
          complete, 'all' -> result in blocking on the full commit of the record (slowest, most durable setting)
 */
        properties.put("acks", "all");
//        if attempt fails, producer can automatically retry, is set to zero so won't retry edit: changing to 3
        properties.put("retries", 3);
//      specify producer buffer size in batch.size config, increasing this number results in more batching, requires more memory

        properties.put("batch.size", 16384);
/*        to reduce the number of requests, set linger.ms greater than zero, this will instruct producer to wait up to
          that number of milliseconds before sending a request to wait and see if more records arrive to fill up unused
          space in the same batch
 */
        properties.put("linger.ms", 1);
//        buffer.memory controls total amount of memory available to the producer for buffering
        properties.put("buffer.memory", 33554432);
/*        the key.serializer and value.serializer instruct how to turn the key/value objects the user provides with
          their ProducerRecord into bytes, StringSerializer is for simple strings
 */
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

//        create a new producer by setting appropriate key and value types and passing in Properties object
        Producer<String, String> producer = new KafkaProducer<>(properties);
//        send 21 records

        Event[] events = {
                new Event("Steven", 13),
                new Event("Ana", 8),
                new Event("Lorraine", 2)
        };

        Gson gson = new Gson();

        for (int i = 0; i < events.length; i++) {

            Event event = events[i];
            System.out.println("Sending event to topic [" + topicName + "] event [" + event.getName() + ", " + event.getNumCats() + "]");

            String value = gson.toJson(event);
            System.out.println("Created JSON [" + value + "]");


            producer.send(new ProducerRecord<>(topicName, Integer.toString(i), value));
        }

        producer.close();
    }
}
