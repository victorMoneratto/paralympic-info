package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Localidade")
public class Local extends AbstractEntity{

    @Id
    @Column
    @ViewColumn("Nome")
    private String Nome;

    @Column
    @ViewColumn("Endereço")
    private String Endereco;

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

    @Override
    public String getFormattedPrimaryKey() {
        return Nome;
    }
}
