package info.entity.key;

import java.io.Serializable;

public class Atleta_ModalidadePK implements Serializable{
    private String atleta;
    private String modalidade;

    public Atleta_ModalidadePK() {

    }

    public String getAtleta() {
        return atleta;
    }

    public void setAtleta(String atleta) {
        this.atleta = atleta;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }
}
