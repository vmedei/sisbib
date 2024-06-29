package br.ufrn.imd.lp2.projeto03.models;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class Professor extends Usuario implements Serializable {
    private String departamento;

    public String getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Professor(String nome, String cpf, String matricula, LocalDate dataNascimento, int qntEmprestimo, String curso) {
        super(nome, cpf, matricula, dataNascimento, qntEmprestimo);
        setDepartamento(curso);
    }

    @Override
    public boolean authenticate(String login, String senha) {
        return false;
    }

    @Override
    public boolean authenticar(String login, String senha) {
        return false;
    }
}
