package br.ufrn.imd.lp2.projeto03.controller;

import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.*;
import br.ufrn.imd.lp2.projeto03.services.OperacoesDados;
import br.ufrn.imd.lp2.projeto03.utils.EstadoLivro;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class dadosController implements OperacoesDados {

    private static final String CAMINHO_ARQUIVO = "sisbib\\src\\main\\java\\br\\ufrn\\imd\\lp2\\projeto03\\DAO\\dados.bin";

    @SuppressWarnings("unchecked")
    public void inicializarDados() {

        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists() ) {
            System.out.println("Arquivo de dados não encontrado.");
            return;
        } else if (arquivo.length() == 0) {
            System.out.println("Arquivo de dados vazio.");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO))) {
            ArrayList<Estudante> estudantes = (ArrayList<Estudante>) ois.readObject();
            ArrayList<Professor> professores = (ArrayList<Professor>) ois.readObject();
            ArrayList<Bibliotecario> bibliotecarios = (ArrayList<Bibliotecario>) ois.readObject();
            ArrayList<Livro> livros = (ArrayList<Livro>) ois.readObject();
            ArrayList<Emprestimo> emprestimos = (ArrayList<Emprestimo>) ois.readObject();

            BancoDAO bancoDAO = BancoDAO.getInstance();
            bancoDAO.getEstudantes().addAll(estudantes);
            bancoDAO.getProfessores().addAll(professores);
            bancoDAO.getBibliotecarios().addAll(bibliotecarios);
            bancoDAO.getLivros().addAll(livros);
            bancoDAO.getEmprestimosAtivos().addAll(emprestimos);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler o arquivo de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            BancoDAO bancoDAO = BancoDAO.getInstance();
            oos.writeObject(bancoDAO.getEstudantes());
            oos.writeObject(bancoDAO.getProfessores());
            oos.writeObject(bancoDAO.getBibliotecarios());
            oos.writeObject(bancoDAO.getLivros());
            oos.writeObject(bancoDAO.getEmprestimosAtivos());

            exibirMensagemPopup("Dados salvos com sucesso!", AlertType.INFORMATION);
            return true;
        } catch (IOException e) {
            exibirMensagemPopup("Erro ao salvar os dados: " + e.getMessage(), AlertType.ERROR);
            return false;
        }
    }

    private void exibirMensagemPopup(String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Resultado da Operação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void serializarDados(){
        ArrayList<Estudante> estudantes = new ArrayList<>();
        ArrayList<Professor> professores = new ArrayList<>();
        ArrayList<Bibliotecario> bibliotecarios = new ArrayList<>();
        ArrayList<Livro> livros = new ArrayList<>();
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();

        // Criar objetos a partir dos dados fornecidos
        bibliotecarios.add(new Bibliotecario("João Paulo", "123456789", "BIB001", LocalDate.of(1980, 11, 12), 0, "admin", "admin"));
        bibliotecarios.add(new Bibliotecario("Maria Clara", "234567890", "BIB002", LocalDate.of(1980, 12, 13), 2, "", ""));

        estudantes.add(new Estudante("Vinicius Alves", "987654321", "EST001", LocalDate.of(1997, 1, 2), 2, "Computação"));
        estudantes.add(new Estudante("Pericles Andrade", "654321987", "EST002", LocalDate.of(2000, 3, 4), 3, "Engenharia"));
        estudantes.add(new Estudante("Arnaud Gabriel", "321987654", "EST003", LocalDate.of(2000, 5, 6), 3, "Engenharia"));

        professores.add(new Professor("Janiheryson Felipe", "456789123", "PROF001", LocalDate.of(1990, 7, 8), 0, "Matemática"));
        professores.add(new Professor("Professor1", "789123456", "PROF002", LocalDate.of(1970, 9, 10), 0, "Matemática"));

        livros.add(new Livro("O Senhor dos Anéis", "Tolkien", "Fantasia", 1954, EstadoLivro.BOM, 10, 202401));
        livros.add(new Livro("1984", "George Orwell", "Distopia", 1949, EstadoLivro.NOVO, 5, 202402));
        livros.add(new Livro("Duna", "Frank Herbert", "Ficção Científica", 1965, EstadoLivro.RUIM, 3, 202403));
        livros.add(new Livro("Cem Anos de Solidão", "Gabriel García Márquez", "Realismo", 1967, EstadoLivro.NOVO, 1, 202404));

        emprestimos.add(new Emprestimo(bibliotecarios.get(0), livros.get(0), LocalDate.of(2024,6,10), LocalDate.of(2024,7,10)));
        emprestimos.add(new Emprestimo(estudantes.get(0), livros.get(1), LocalDate.of(2024,5,10), LocalDate.of(2024,6,10)));
        emprestimos.add(new Emprestimo(professores.get(0), livros.get(2), LocalDate.of(2024,6,10), LocalDate.of(2024,7,25)));

        // Serializar os objetos para um arquivo binário
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(estudantes);
            oos.writeObject(professores);
            oos.writeObject(bibliotecarios);
            oos.writeObject(livros);
            oos.writeObject(emprestimos);
            System.out.println("Arquivos Serializados");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
