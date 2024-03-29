package com.umadi.sachin.kafka.examples;


    /*
     * Copyright (c) 2018. Prashant Kumar Pandey
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * You may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     * http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing,
     * software distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and limitations under the License.
     */
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

    /**
     * A Kafka producer that sends numEvents (# of messages) to a given topicName
     * @author sachin.umadi
     */
    public class SimpleProducer {
        private static final Logger logger = LogManager.getLogger(SimpleProducer.class);

        public static void main(String[] args) {

            String topicName;
            int numEvents;
            if(args.length !=2){
                logger.info("Please Enter correct commandLine Args : <topicName> <numEvents>");
                System.exit(-1);
            }

            topicName = args[0];
            numEvents= Integer.valueOf(args[1]);
            logger.info("Starting SimpleProducer .... ");
            logger.info("topicName : " +topicName + " numEvents : "+numEvents);

            logger.info("Creating Kafka Producer...");
            Properties props = new Properties();
            props.put(ProducerConfig.CLIENT_ID_CONFIG, "SimpleProducer");
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

            KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);

            logger.info("Start sending messages...");
           try{
               for (int i = 1; i <= numEvents; i++) {
                   producer.send(new ProducerRecord<>(topicName, i, "Simple Message-" + i));
               }
           }catch (Exception e){
               logger.error("Exception Occurred : message "+e.getMessage());
               System.exit(-1);
           }finally {
               System.out.println("Finally {} Finished - Closing Kafka Producer.");
               logger.info("Finished - Closing Kafka Producer.");
               producer.close();
           }

        }
    }



