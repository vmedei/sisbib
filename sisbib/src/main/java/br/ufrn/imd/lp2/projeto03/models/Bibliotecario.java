package br.ufrn.imd.lp2.projeto03.models;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class Bibliotecario extends Usuario implements Serializable {
    private String login;
    private String senha;

    public Bibliotecario() {

    }

    @Override
    public boolean authenticate(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    public Bibliotecario(String nome, String cpf, String matricula, LocalDate dataNascimento, int qntEmprestimo, String login, String senha) {
        super(nome, cpf, matricula, dataNascimento, qntEmprestimo);
        setLogin(login);
        setSenha(senha);
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean authenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

}
