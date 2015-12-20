package br.puc.ronie.dbadapter;

import java.io.Serializable;

/**
 * Created by 58400 on 24/11/2015.
 */
public class ContatoModel implements Serializable {
    long id;

    String nome;

    String email;

    public ContatoModel() {
    }

    public ContatoModel(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
