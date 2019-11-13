/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ido.rocketmq.quickstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("pGroupTest");

        producer.setNamesrvAddr("127.0.0.1:9876");

        producer.start();

        try {

            int loop = 500;

            while (loop-- > 0) {
                Message msg = new Message("TBJ" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + loop).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );

                msg.setKeys("yuren");

                SendResult sendResult = producer.send(msg);

                System.out.printf("%s%n", sendResult);

                try {

                    Thread.sleep(1000 * 5);
                } catch (Exception e) {

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            Thread.sleep(1000);
        }

        producer.shutdown();
    }
}
