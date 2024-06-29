package br.ufrn.imd.lp2.projeto03.DAO;

import br.ufrn.imd.lp2.projeto03.models.*;

import java.util.ArrayList;

public class BancoDAO {

    private static BancoDAO biblioteca;
    private ArrayList<Bibliotecario> bibliotecarios;
    private ArrayList<Estudante> estudantes;
    private ArrayList<Professor> professores;
    private ArrayList<Livro> livros;
    private ArrayList<Emprestimo> emprestimosAtivos;

    private BancoDAO() {
        bibliotecarios = new ArrayList<>();
        estudantes = new ArrayList<>();
        professores = new ArrayList<>();
        livros = new ArrayList<>();
        emprestimosAtivos = new ArrayList<>();
    }

    //Padrão singleton (Cria apenas uma instancia da biblioteca)
    public static synchronized BancoDAO getInstance() {
        if (biblioteca == null) {
            biblioteca = new BancoDAO();
        }
        return biblioteca;
    }

    public static BancoDAO getBiblioteca() {
        return biblioteca;
    }

    public static void setBiblioteca(BancoDAO biblioteca) {
        BancoDAO.biblioteca = biblioteca;
    }

    public ArrayList<Bibliotecario> getBibliotecarios() {
        return this.bibliotecarios;
    }

    public void setBibliotecarios(ArrayList<Bibliotecario> bibliotecarios) {
        this.bibliotecarios = bibliotecarios;
    }

    public ArrayList<Estudante> getEstudantes() {
        return this.estudantes;
    }

    public void setEstudantes(ArrayList<Estudante> estudantes) {
        this.estudantes = estudantes;
    }

    public ArrayList<Professor> getProfessores() {
        return this.professores;
    }

    public void setProfessores(ArrayList<Professor> professores) {
        this.professores = professores;
    }

    public ArrayList<Livro> getLivros() {
        return this.livros;
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    public ArrayList<Emprestimo> getEmprestimosAtivos() {
        return this.emprestimosAtivos;
    }

    public void setEmprestimosAtivos(ArrayList<Emprestimo> emprestimosAtivos) {
        this.emprestimosAtivos = emprestimosAtivos;
    }
}
