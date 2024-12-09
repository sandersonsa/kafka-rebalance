package xyz.sandersonsa.kafka_sp.config;

import java.util.Collection;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
// import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Consumer;

public interface ConsumerAwareRebalanceListener extends ConsumerRebalanceListener {

    void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions);

    void onPartitionsRevokedAfterCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions);

    void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions);

    void onPartitionsLost(Consumer<?, ?> consumer, Collection<TopicPartition> partitions);

}