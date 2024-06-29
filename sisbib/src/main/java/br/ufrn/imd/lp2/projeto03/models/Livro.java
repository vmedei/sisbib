package br.ufrn.imd.lp2.projeto03.models;

import br.ufrn.imd.lp2.projeto03.utils.EstadoLivro;

import java.io.Serializable;

public class Livro implements Serializable {
    private String titulo;
    private String autor;
    private String assunto;
    private int anoLancamento;
    private EstadoLivro estado;
    private int qtdEstoque;
    private int patrimonio;

    public Livro(){
    }

    @SuppressWarnings("exports")
    public Livro(String titulo, String autor, String assunto, int anoLancamento, EstadoLivro estado, int qtdEstoque, int patrimonio) {
        setTitulo(titulo);
        setAutor(autor);
        setAssunto(assunto);
        setAnoLancamento(anoLancamento);
        setEstado(estado);
        setQtdEstoque(qtdEstoque);
        setPatrimonio(patrimonio);
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAssunto() {
        return this.assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public int getAnoLancamento() {
        return this.anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    @SuppressWarnings("exports")
    public EstadoLivro getEstado() {
        return this.estado;
    }

    @SuppressWarnings("exports")
    public void setEstado(EstadoLivro estado) {
        this.estado = estado;
    }

    public int getQtdEstoque() {
        return this.qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public int getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(int patrimonio) {
        this.patrimonio = patrimonio;
    }
}
