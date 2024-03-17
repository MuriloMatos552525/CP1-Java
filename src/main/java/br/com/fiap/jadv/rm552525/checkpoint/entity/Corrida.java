package br.com.fiap.jadv.rm552525.checkpoint.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_corrida_552525")
@Data
public class Corrida {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_tb_corrida_552525")
    @SequenceGenerator(name = "sq_tb_corrida_552525", sequenceName = "sq_tb_corrida_552525", allocationSize = 1)
    private Long id;

    @Column(name = "dt_solicitacao", nullable = false)
    private LocalDateTime dtSolicitacao;

    @Column(name = "dt_finalizacao")
    private LocalDateTime dtFinalizacao;

    @Column(name = "nr_cpf_cliente", nullable = false)
    private String cpfCliente;

    @Column(name = "ds_nome_cliente", nullable = false)
    private String nomeCliente;

    @Column(name = "nr_latitude_origem", nullable = false)
    private Double latitudeOrigem;

    @Column(name = "nr_longitude_origem", nullable = false)
    private Double longitudeOrigem;

    @Column(name = "nr_latitude_destino", nullable = false)
    private Double latitudeDestino;

    @Column(name = "nr_longitude_destino", nullable = false)
    private Double longitudeDestino;

    @Column(name = "nr_latitude_atual", nullable = false)
    private Double latitudeAtual;

    @Column(name = "nr_longitude_atual", nullable = false)
    private Double longitudeAtual;

    @Column(name = "cd_situacao_corrida", nullable = false)
    private Character situacaoCorrida;

    @Column(name = "nr_placa_veiculo", nullable = false)
    private String placaVeiculo;

    @Column(name = "ds_nome_motorista", nullable = false)
    private String nomeMotorista;

    @Column(name = "ds_veiculo", nullable = false)
    private String veiculo;

    @Column(name = "ds_cor_veiculo", nullable = false)
    private String corVeiculo;
}
