package br.ufrn.imd.lp2.projeto03.services;

import br.ufrn.imd.lp2.projeto03.models.*;

public interface OperacoesEmprestimos {

    void realizarEmprestimo(int usuario, Livro livro);
    void exibirEmprestimos();
    void devolverEmprestimo(Emprestimo emprestimo);

}
