package br.ufrn.imd.lp2.projeto03.services;

import br.ufrn.imd.lp2.projeto03.models.*;

public interface OperacoesUsuarios {
    void adicionarEstudante(Estudante estudante);
    void adicionarProfessor(Professor professor);
    void adicionarBibliotecario(Bibliotecario bibliotecario);
    void removerEstudante(String CPF);
    void removerProfessor(String CPF);
    void removerBibliotecario(String CPF);
}
