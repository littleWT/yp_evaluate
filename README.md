# yp_evaluate
kafka_consumer_pgsql

an simple demo that a kafka consumer get json message and store in postgres

(1)install kafka,postgres in your PC
(2)execute:mvn package and get jar in target
(3)start kalka service,including zookeeper
(4)execute:java -cp yp_evaluate-1.0-SNAPSHOT-jar-with-dependencies.jar  kafka.consumer.teewon.SimpleHLConsumer zookeeperIP:zookeeperPORT  testgroup ${topic in your kafka} start
(5)start a producer in a new ssh: bin/kafka-console-producer.sh --broker-list 192.168.112.240:9092 --topic ${topic in your kafka}
(6)send a json message in the terminate of the producer
(7)you will see the message was stored in pgsql