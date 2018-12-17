package leiren.haozhaojob.common.kafka;

import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * kafka 数据下发topic消费者
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Component
public class KafkaCustomer {
    /*private static final Logger LOGGER = LoggerFactory.getLogger(KafkaCustomer.class);

    private static final AtomicLong RECEIVE_COUNT = new AtomicLong(0);

    private static final String TOPIC = "log_service";

    private static final String GROUP_ID = "community-admin";

    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(groupId = GROUP_ID,
            topics = TOPIC,
            containerFactory = "kafkaListenerContainerFactory")
    public void onDataChangeMessage(List<Bytes> messageList, Acknowledgment ack) {
        if (CollectionUtils.isEmpty(messageList)) {
            ack.acknowledge();
            return;
        }

        messageList.forEach(message -> {
            try {
                LOGGER.info(RECEIVE_COUNT.incrementAndGet() + ".message====== " + message.toString());
                System.out.println(message.toString());
            } catch (Exception e) {
                LOGGER.error("topic[" + TOPIC + "], message[" + message + "].", e);
            }
        });
        ack.acknowledge();
    }*/
}
