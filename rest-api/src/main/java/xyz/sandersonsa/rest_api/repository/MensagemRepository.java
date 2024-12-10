package xyz.sandersonsa.rest_api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.sandersonsa.rest_api.model.Mensagem;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
}
