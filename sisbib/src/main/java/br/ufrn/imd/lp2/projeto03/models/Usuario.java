package br.ufrn.imd.lp2.projeto03.models;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Usuario implements Serializable {
    private String nome;
    private String cpf;
    private String matricula;
    private LocalDate dataNascimento;
    private int qntEmprestimos;

    public Usuario() {

    }

    public Usuario(String nome, String cpf, String matricula, LocalDate dataNascimento, int qntEmprestimos) {
        setNome(nome);
        setCpf(cpf);
        setMatricula(matricula);
        setDataNascimento(dataNascimento);
        setQntEmprestimos(qntEmprestimos);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getQntEmprestimos() { return this.qntEmprestimos; }

    public void setQntEmprestimos(int qntEmprestimos) { this.qntEmprestimos = qntEmprestimos; }

    public abstract boolean authenticate(String login, String senha);

    public abstract boolean authenticar(String login, String senha);
}
