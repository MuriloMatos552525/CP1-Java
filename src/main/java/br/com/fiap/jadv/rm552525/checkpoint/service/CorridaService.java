// CorridaService.java
package br.com.fiap.jadv.rm552525.checkpoint.service;

import br.com.fiap.jadv.rm552525.checkpoint.dto.CorridaRequestDTO;
import br.com.fiap.jadv.rm552525.checkpoint.dto.CorridaResponseDTO;
import br.com.fiap.jadv.rm552525.checkpoint.entity.Corrida;
import br.com.fiap.jadv.rm552525.checkpoint.enums.SituacaoCorrida;
import br.com.fiap.jadv.rm552525.checkpoint.exception.NegocioException;
import br.com.fiap.jadv.rm552525.checkpoint.repository.CorridaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CorridaService {

    private final CorridaRepository corridaRepository;

    public CorridaService(CorridaRepository corridaRepository) {
        this.corridaRepository = corridaRepository;
    }

    @Transactional
    public CorridaResponseDTO criarCorrida(CorridaRequestDTO corridaRequestDTO) {
        validarCorrida(corridaRequestDTO);
        Corrida corrida = new Corrida();
        BeanUtils.copyProperties(corridaRequestDTO, corrida);
        corrida.setDtSolicitacao(LocalDateTime.now());
        corrida.setSituacaoCorrida(SituacaoCorrida.AGUARDANDO.getCodigo());
        corrida = corridaRepository.save(corrida);
        CorridaResponseDTO corridaResponseDTO = new CorridaResponseDTO();
        BeanUtils.copyProperties(corrida, corridaResponseDTO);
        return corridaResponseDTO;
    }

    public CorridaResponseDTO getCorridaById(Long id) {
        Optional<Corrida> optionalCorrida = corridaRepository.findById(id);
        if (optionalCorrida.isEmpty()) {
            throw new NegocioException("Corrida não encontrada com o ID: " + id);
        }
        Corrida corrida = optionalCorrida.get();
        CorridaResponseDTO corridaResponseDTO = new CorridaResponseDTO();
        BeanUtils.copyProperties(corrida, corridaResponseDTO);
        return corridaResponseDTO;
    }

    @Transactional
    public void atualizarSituacaoCorrida(Long id, char situacao) {
        Optional<Corrida> optionalCorrida = corridaRepository.findById(id);
        if (optionalCorrida.isEmpty()) {
            throw new NegocioException("Corrida não encontrada com o ID: " + id);
        }
        Corrida corrida = optionalCorrida.get();
        SituacaoCorrida novaSituacao = SituacaoCorrida.fromCodigo(situacao);
        validarTransicaoSituacao(corrida.getSituacaoCorrida(), novaSituacao);
        corrida.setSituacaoCorrida(novaSituacao.getCodigo());
        if (novaSituacao == SituacaoCorrida.CONCLUIDA || novaSituacao == SituacaoCorrida.CANCELADA) {
            corrida.setDtFinalizacao(LocalDateTime.now());
        }
    }

    @Transactional
    public void atualizarPosicaoAtual(Long id, CorridaRequestDTO corridaRequestDTO) {
        Optional<Corrida> optionalCorrida = corridaRepository.findById(id);
        if (optionalCorrida.isEmpty()) {
            throw new NegocioException("Corrida não encontrada com o ID: " + id);
        }
        Corrida corrida = optionalCorrida.get();
        if (!corrida.getLatitudeOrigem().equals(corridaRequestDTO.getLatitudeAtual()) ||
                !corrida.getLongitudeOrigem().equals(corridaRequestDTO.getLongitudeAtual())) {
            throw new NegocioException("A latitude e longitude atuais não correspondem à origem da corrida");
        }
        corrida.setLatitudeAtual(corridaRequestDTO.getLatitudeAtual());
        corrida.setLongitudeAtual(corridaRequestDTO.getLongitudeAtual());
    }

    private void validarCorrida(CorridaRequestDTO corridaRequestDTO) {
        if (corridaRequestDTO.getLatitudeOrigem().equals(corridaRequestDTO.getLatitudeDestino()) &&
                corridaRequestDTO.getLongitudeOrigem().equals(corridaRequestDTO.getLongitudeDestino())) {
            throw new NegocioException("A latitude e longitude de origem não podem ser iguais à latitude e longitude de destino");
        }
        Optional<Corrida> corridaEmAndamento = corridaRepository.findByCpfClienteAndSituacaoCorrida(corridaRequestDTO.getCpfCliente(), SituacaoCorrida.EM_ANDAMENTO.getCodigo());
        if (corridaEmAndamento.isPresent()) {
            throw new NegocioException("Já existe uma corrida em andamento para o cliente");
        }
    }

    private void validarTransicaoSituacao(char situacaoAtual, SituacaoCorrida novaSituacao) {
        SituacaoCorrida situacaoAtualEnum = SituacaoCorrida.fromCodigo(situacaoAtual);
        switch (situacaoAtualEnum) {
            case AGUARDANDO:
                if (novaSituacao != SituacaoCorrida.EM_ANDAMENTO && novaSituacao != SituacaoCorrida.CANCELADA) {
                    throw new NegocioException("Transição de situação inválida: AGUARDANDO -> " + novaSituacao.name());
                }
                break;
            case EM_ANDAMENTO:
                if (novaSituacao != SituacaoCorrida.CONCLUIDA && novaSituacao != SituacaoCorrida.CANCELADA) {
                    throw new NegocioException("Transição de situação inválida: EM_ANDAMENTO -> " + novaSituacao.name());
                }
                break;
            case CONCLUIDA:
                throw new NegocioException("A corrida já foi concluída");
            case CANCELADA:
                throw new NegocioException("A corrida já foi cancelada");
        }
    }
}
