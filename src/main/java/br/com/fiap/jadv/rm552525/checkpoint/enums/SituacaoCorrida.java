package br.com.fiap.jadv.rm552525.checkpoint.enums;

import lombok.Getter;

@Getter
public enum SituacaoCorrida {
    AGUARDANDO('A'),
    EM_ANDAMENTO('E'),
    CONCLUIDA('C'),
    CANCELADA('X');

    private final char codigo;

    SituacaoCorrida(char codigo) {
        this.codigo = codigo;
    }

    public static SituacaoCorrida fromCodigo(char codigo) {
        for (SituacaoCorrida situacao : SituacaoCorrida.values()) {
            if (situacao.getCodigo() == codigo) {
                return situacao;
            }
        }
        throw new IllegalArgumentException("Código de situação de corrida inválido: " + codigo);
    }
}
