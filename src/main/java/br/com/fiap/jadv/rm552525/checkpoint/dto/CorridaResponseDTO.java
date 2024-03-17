package br.com.fiap.jadv.rm552525.checkpoint.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CorridaResponseDTO {

    private Long id;

    private LocalDateTime dtSolicitacao;

    private LocalDateTime dtFinalizacao;

    private String cpfCliente;

    private String nomeCliente;

    private Double latitudeOrigem;

    private Double longitudeOrigem;

    private Double latitudeDestino;

    private Double longitudeDestino;

    private Double latitudeAtual;

    private Double longitudeAtual;

    private String situacaoCorrida;

    private String placaVeiculo;

    private String nomeMotorista;

    private String veiculo;

    private String corVeiculo;
}
