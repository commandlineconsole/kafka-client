import java.util.Properties;

// import producer packages, interface for KafkaProducer class
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
// import ProducerRecord, key/value pair sent to Kafka, consists of topic name to which record is being sent, optional: partition number, key and value
import org.apache.kafka.clients.producer.ProducerRecord;


public class TestProducer {
    public static void main(String[] args) throws Exception{

//     checking to see if something was passed in
        if(args.length == 0){
            System.out.println("Enter a topic name");//TODO write a method to handle this
            return;
        }

//        assign topicName to a string variable *
        String topicName = args[0];
//        create Properties instance to access Producer configs
        Properties properties = new Properties();
//        assign localhost id, put(key, value) TODO look up differences between .put and .setProperty
        properties.put("bootstrap.servers", "localhost:9092");
/*        set acknowledgements for producer requests, acks config controls criteria under which requests are considered
          complete, 'all' -> result in blocking on the full commit of the record (slowest, most durable setting)
 */
        properties.put("acks", "all");
//        if attempt fails, producer can automatically retry, is set to zero so won't retry
        properties.put("retries", 0);
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

//        reference KafkaProducer(a Kafka client that publishes records to the Kafka cluster) via Producer interface
        Producer<String, String> producer = new KafkaProducer<>(properties);
//        produce 21 records
        for (int i = 0; i < 21; i++)
            producer.send(new ProducerRecord<String, String>(topicName, Integer.toString(i), Integer.toString(i)));
        producer.close();
    }
}
