package br.ufrn.imd.lp2.projeto03.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class removerUsuarioController implements Initializable {

    static BancoDAO biblioteca = BancoDAO.getInstance();

    @FXML
    private void mudarTela_voltar() throws IOException {
        App.trocarRoot("view/menuPrincipal.fxml");
    }

    @FXML
    private ChoiceBox<String> cbTipoUsuario;

    private String[] tipoUsuarios = { "Bibliotecario", "Estudante", "Professor" };

    @FXML
    private TextField tfPesquisa;

    @FXML
    private Button btnPesquisar, btnRemover;

    @FXML
    private Label lbNome, lbCpf, lbMatricula, lbDataNascimento, lbQtdEmprestimo;

    @FXML
    private HBox hbDepartamento, hbCurso;

    @FXML
    private Label lbDepartmaento, lbCurso;

    @FXML
    private Label removerUsuarioMsgErr, removerUsuarioMsgSuc;

    void limparMensagens() {
        removerUsuarioMsgErr.setText("");
        removerUsuarioMsgErr.setVisible(false);
        removerUsuarioMsgSuc.setText("");
        removerUsuarioMsgSuc.setVisible(false);
    }

    void mensagemResposta(Label mensagem, String texto) {
        limparMensagens();
        mensagem.setText(texto);
        mensagem.setPadding(new Insets(5, 10, 5, 10));
        mensagem.setVisible(true);
    }

    void redirecionar() {
        PauseTransition delay = new PauseTransition(Duration.seconds(2)); // Delay de 2 segundos

        delay.setOnFinished(event -> {
            try {
                mudarTela_voltar();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        delay.play();
    }

    private Bibliotecario bibliotecarioEncontrado = null;
    private Estudante estudanteEncontrado = null;
    private Professor professorEncontrado = null;

    @FXML
    void btnPesquisarOnAction(ActionEvent event) {

        // Esconde as seções de Departamento e Curso
        hbDepartamento.setVisible(false);
        hbCurso.setVisible(false);

        // Limpa todos os campos visuais
        lbNome.setText("");
        lbCpf.setText("");
        lbMatricula.setText("");
        lbDataNascimento.setText("");
        lbQtdEmprestimo.setText("");
        lbDepartmaento.setText("");
        lbCurso.setText("");

        // Pega o tipo de usuário e a pesquisa
        String tipoUsuario = cbTipoUsuario.getValue();
        String pesquisa = tfPesquisa.getText();

        // Verifica se o tipo de usuário e a pesquisa foram preenchidos
        if (tipoUsuario == null || pesquisa.isBlank()) {
            mensagemResposta(removerUsuarioMsgErr, "Escolha o tipo de usuário e insira o CPF");
            return;
        }

        boolean usuarioEncontrado = false;

        switch (tipoUsuario) {

            case "Bibliotecario":

                ArrayList<Bibliotecario> bibliotecarios = biblioteca.getBibliotecarios();

                for (Bibliotecario bibliotecario : bibliotecarios) {

                    if (Objects.equals(bibliotecario.getCpf(), pesquisa)) {

                        lbNome.setText(bibliotecario.getNome());
                        lbCpf.setText(bibliotecario.getCpf());
                        lbMatricula.setText(bibliotecario.getMatricula());
                        lbDataNascimento.setText(String.valueOf(bibliotecario.getDataNascimento()));
                        lbQtdEmprestimo.setText(String.valueOf(bibliotecario.getQntEmprestimos()));

                        usuarioEncontrado = true;
                        bibliotecarioEncontrado = bibliotecario;
                        break;
                    }
                }
                break;

            case "Professor":

                ArrayList<Professor> professores = biblioteca.getProfessores();

                for (Professor professor : professores) {

                    if (Objects.equals(professor.getCpf(), pesquisa)) {

                        hbDepartamento.setVisible(true);
                        lbNome.setText(professor.getNome());
                        lbCpf.setText(professor.getCpf());
                        lbMatricula.setText(professor.getMatricula());
                        lbDataNascimento.setText(String.valueOf(professor.getDataNascimento()));
                        lbQtdEmprestimo.setText(String.valueOf(professor.getQntEmprestimos()));
                        lbDepartmaento.setText(professor.getDepartamento());

                        professorEncontrado = professor;
                        usuarioEncontrado = true;
                        break;
                    }
                }
                break;

            case "Estudante":

                ArrayList<Estudante> estudantes = biblioteca.getEstudantes();

                for (Estudante estudante : estudantes) {

                    if (Objects.equals(estudante.getCpf(), pesquisa)) {

                        hbCurso.setVisible(true);
                        lbNome.setText(estudante.getNome());
                        lbCpf.setText(estudante.getCpf());
                        lbMatricula.setText(estudante.getMatricula());
                        lbDataNascimento.setText(String.valueOf(estudante.getDataNascimento()));
                        lbQtdEmprestimo.setText(String.valueOf(estudante.getQntEmprestimos()));
                        lbCurso.setText(estudante.getCurso());

                        estudanteEncontrado = estudante;
                        usuarioEncontrado = true;
                        break;
                    }
                }
                break;
        }

        if (usuarioEncontrado) {
            btnRemover.setDisable(false);
            mensagemResposta(removerUsuarioMsgSuc, "Usuario encontrado");
        } else {
            mensagemResposta(removerUsuarioMsgErr, "Usuario não encontrado");
        }
    }

    public void btnRemoverUsuarioOnAction() {

        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Excluir Usuário");
        alert.setHeaderText("Confirmar Exclusão");
        alert.setContentText("Deseja Excluir o usuário selecionado?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            try {
                if (bibliotecarioEncontrado != null) {
                    biblioteca.getBibliotecarios().remove(bibliotecarioEncontrado);
                } else if (professorEncontrado != null) {
                    biblioteca.getProfessores().remove(professorEncontrado);
                } else if (estudanteEncontrado != null) {
                    biblioteca.getEstudantes().remove(estudanteEncontrado);
                }

                mensagemResposta(removerUsuarioMsgSuc, "Usuario removido com sucesso");

            } catch (Exception e) {
                mensagemResposta(removerUsuarioMsgErr, "Remoção falhou: " + e.getMessage());

            }

            btnRemover.setDisable(true);

            redirecionar();
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        cbTipoUsuario.getItems().addAll(tipoUsuarios);

    }
}
