package kafka.consumer.teewon;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.consumer.bean.User;
import kafka.consumer.impl.DatabaseImpl;
import kafka.javaapi.consumer.ConsumerConnector;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xiaotao on 2017/8/17.
 */
public class SimpleHLConsumer {
    private final ConsumerConnector consumer;
    private final String topic;
    public SimpleHLConsumer(String zookeeper, String groupId, String topic) {
        consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(zookeeper, groupId));
        this.topic = topic;
    }
    private static ConsumerConfig createConsumerConfig(String zookeeper, String groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }
    public void testConsumer() {
        Map<String, Integer> topicMap = new HashMap<String, Integer>();
        // Define single thread for topic
        topicMap.put(topic, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreamsMap = consumer.createMessageStreams(topicMap);
        List<KafkaStream<byte[], byte[]>> streamList = consumerStreamsMap.get(topic);
        for (final KafkaStream<byte[], byte[]> stream : streamList) {
            ConsumerIterator<byte[], byte[]> consumerIte = stream.iterator();
            while (consumerIte.hasNext())
            {
                String getMsg = new String(consumerIte.next().message());
                System.out.println("Message from Single Topic :: " + getMsg);

                try {
                    ObjectMapper mapper = new ObjectMapper();
                    User user = mapper.readValue(getMsg, User.class);
                    System.out.println("Current user :"+ user.toString());
                    new DatabaseImpl().insert(user);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Current user :"+ e.getMessage().toString());
                }
            }
        }
        if (consumer != null)
            consumer.shutdown();
    }
    public static void main(String[] args) {
        String zooKeeper = args[0];
        String groupId = args[1];
        String topic = args[2];
        SimpleHLConsumer simpleHLConsumer = new SimpleHLConsumer(zooKeeper, groupId, topic);
        simpleHLConsumer.testConsumer();
    }
}