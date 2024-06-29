package br.ufrn.imd.lp2.projeto03.services;

import br.ufrn.imd.lp2.projeto03.models.Livro;

public interface OperacoesLivros {

    void adicionarLivro(Livro livro);
    void removerLivro(Livro livro);
    Livro pesquisarLivro();Livro pesquisarLivroPatrimonio();
    void imprimirLivro(Livro livro);
}
