package br.com.fiap.jadv.rm552525.checkpoint.repository;

import br.com.fiap.jadv.rm552525.checkpoint.entity.Corrida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Long> {

    Optional<Corrida> findByCpfClienteAndSituacaoCorrida(String cpfCliente, char situacaoCorrida);
}
