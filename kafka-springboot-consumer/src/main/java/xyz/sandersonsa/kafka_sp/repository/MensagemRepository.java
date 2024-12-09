package xyz.sandersonsa.kafka_sp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.sandersonsa.kafka_sp.entity.Mensagem;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
}
