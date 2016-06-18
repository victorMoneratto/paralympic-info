package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Localidade")
public class Local {

    @Id
    @ViewColumn("Nome")
    public String Nome;

    @ViewColumn("Endereço")
    public String Endereco;

    public Local() {
    }

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
