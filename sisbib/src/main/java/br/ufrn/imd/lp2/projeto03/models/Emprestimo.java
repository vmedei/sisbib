package br.ufrn.imd.lp2.projeto03.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Emprestimo implements Serializable {
    private Estudante estudanteEmprestimo;
    private Professor professorEmprestimo;
    private Bibliotecario bibliotecarioEmprestimo;
    private Livro livroEmprestimo;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;

    public Emprestimo(Estudante estudante, Livro livro, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        setEstudanteEmprestimo(estudante);
        setLivroEmprestimo(livro);
        setDataEmprestimo(dataEmprestimo);
        setDataDevolucaoPrevista(dataDevolucaoPrevista);
    }

    public Emprestimo(Professor professor, Livro livro, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        setProfessorEmprestimo(professor);
        setLivroEmprestimo(livro);
        setDataEmprestimo(dataEmprestimo);
        setDataDevolucaoPrevista(dataDevolucaoPrevista);
    }

    public Emprestimo(Bibliotecario bibliotecario, Livro livro, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        setBibliotecarioEmprestimo(bibliotecario);
        setLivroEmprestimo(livro);
        setDataEmprestimo(dataEmprestimo);
        setDataDevolucaoPrevista(dataDevolucaoPrevista);
    }

    public Estudante getEstudanteEmprestimo() {
        return estudanteEmprestimo;
    }

    public void setEstudanteEmprestimo(Estudante estudanteEmprestimo) {
        this.estudanteEmprestimo = estudanteEmprestimo;
    }

    public Professor getProfessorEmprestimo() {
        return professorEmprestimo;
    }

    public void setProfessorEmprestimo(Professor professorEmprestimo) {
        this.professorEmprestimo = professorEmprestimo;
    }

    public Bibliotecario getBibliotecarioEmprestimo() {
        return bibliotecarioEmprestimo;
    }

    public void setBibliotecarioEmprestimo(Bibliotecario bibliotecarioEmprestimo) {
        this.bibliotecarioEmprestimo = bibliotecarioEmprestimo;
    }

    public Livro getLivroEmprestimo() {
        return this.livroEmprestimo;
    }

    public void setLivroEmprestimo(Livro livroEmprestimo) {
        this.livroEmprestimo = livroEmprestimo;
    }

    public LocalDate getDataEmprestimo() {
        return this.dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return this.dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public String getUsuarioNome() {
        if (this.estudanteEmprestimo != null) return estudanteEmprestimo.getNome();
        if (this.professorEmprestimo != null) return professorEmprestimo.getNome();
        if (this.bibliotecarioEmprestimo != null) return bibliotecarioEmprestimo.getNome();
        return "";
    }

    public String getTipoUsuario() {
        if (this.estudanteEmprestimo != null) return "Estudante";
        if (this.professorEmprestimo != null) return "Professor";
        if (this.bibliotecarioEmprestimo != null) return "Bibliotecario";
        return "";
    }

    public String getUsuarioMatricula() {
        if (this.estudanteEmprestimo != null) return estudanteEmprestimo.getMatricula();
        if (this.professorEmprestimo != null) return professorEmprestimo.getMatricula();
        if (this.bibliotecarioEmprestimo != null) return bibliotecarioEmprestimo.getMatricula();
        return "";
    }
}