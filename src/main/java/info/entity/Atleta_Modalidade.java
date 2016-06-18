package info.entity;

import info.entity.key.Atleta_ModalidadePK;
import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(Atleta_ModalidadePK.class)
public class Atleta_Modalidade {

    @Id
    @ViewColumn("Atleta")
    public Integer atleta;

    @Id
    @ViewColumn("Modalidade")
    public String modalidade;

    @ViewColumn("Categoria")
    public String categoria;

    @ViewColumn("Medalha")
    public String medalhaGanha;

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMedalhaGanha() {
        return medalhaGanha;
    }

    public void setMedalhaGanha(String medalhaganha) {
        this.medalhaGanha = medalhaganha;
    }

}
