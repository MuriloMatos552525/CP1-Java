package br.com.fiap.jadv.rm552525.checkpoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class CorridaRequestDTO {

    @NotNull
    private LocalDateTime dtSolicitacao;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpfCliente;

    @NotBlank
    private String nomeCliente;

    @NotNull
    private Double latitudeOrigem;

    @NotNull
    private Double longitudeOrigem;

    @NotNull
    private Double latitudeDestino;

    @NotNull
    private Double longitudeDestino;

    @NotNull
    private Double latitudeAtual;

    @NotNull
    private Double longitudeAtual;

    @NotBlank
    @Pattern(regexp = "[A-Z]{1}")
    private String situacaoCorrida;

    @NotBlank
    private String placaVeiculo;

    @NotBlank
    private String nomeMotorista;

    @NotBlank
    private String veiculo;

    @NotBlank
    private String corVeiculo;
}
