package br.ufrn.imd.lp2.projeto03.models;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class Estudante extends Usuario implements Serializable {
    private String curso;

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Estudante(String nome, String cpf, String matricula, LocalDate dataNascimento, int qntEmprestimo, String curso) {
        super(nome, cpf, matricula, dataNascimento, qntEmprestimo);
        setCurso(curso);
    }

    public boolean authenticate(String login, String senha) {
        return false;
    }

    public boolean authenticar(String login, String senha) {
        return false;
    }
}

