package info.entities;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Localidade")
public class Local implements Serializable {

    @Id
    @Column
    @ViewColumn("Nome")
    private String Nome;

    @Column
    @ViewColumn("Endereço")
    private String Endereco;

    public Local() {}

    public Local(String nome, String endereco) {
        Nome = nome;
        Endereco = endereco;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }
}
