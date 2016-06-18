package info.entity.key;

import java.io.Serializable;

public class Atleta_ModalidadePK implements Serializable{
    private Integer atleta;
    private String modalidade;

    public Atleta_ModalidadePK() {

    }

    public Integer getAtleta() {
        return atleta;
    }

    public void setAtleta(Integer atleta) {
        this.atleta = atleta;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }
}
