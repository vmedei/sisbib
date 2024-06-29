package br.ufrn.imd.lp2.projeto03.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class verificarUsuarioController implements Initializable {

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
    private Button btnPesquisar;

    @FXML
    private Label lbNome, lbCpf, lbMatricula, lbDataNascimento, lbQtdEmprestimo, lbSituacao;

    @FXML
    private HBox hbDepartamento, hbCurso;

    @FXML
    private Label lbDepartmaento, lbCurso;

    @FXML
    private Label verificarUsuarioMsgErr, verificarUsuarioMsgSuc;

    void limparMensagens(){
        verificarUsuarioMsgErr.setText("");
        verificarUsuarioMsgErr.setVisible(false);
        verificarUsuarioMsgSuc.setText("");
        verificarUsuarioMsgSuc.setVisible(false);
    }

    void mensagemResposta(Label mensagem, String texto){
        limparMensagens();
        mensagem.setText(texto);
        mensagem.setPadding(new Insets(5, 10, 5, 10));
        mensagem.setVisible(true);
    }

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
        lbSituacao.setText("");
        lbDepartmaento.setText("");
        lbCurso.setText("");

        // Pega o tipo de usuário e a pesquisa
        String tipoUsuario = cbTipoUsuario.getValue();
        String pesquisa = tfPesquisa.getText();

        // Verifica se o tipo de usuário e a pesquisa foram preenchidos
        if (tipoUsuario == null || pesquisa.isBlank()) {
            mensagemResposta(verificarUsuarioMsgErr, "Escolha o tipo de usuário e insira o CPF");
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

                        if (bibliotecario.getQntEmprestimos() >= 5) {
                            lbSituacao.setText("Usuário não pode realizar empréstimo");
                        } else {
                            lbSituacao.setText("Usuário pode realizar empréstimos");
                        }
                        usuarioEncontrado = true;
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

                        if (professor.getQntEmprestimos() >= 5) {
                            lbSituacao.setText("Usuário não pode realizar empréstimo");
                        } else {
                            lbSituacao.setText("Usuário pode realizar empréstimos");
                        }
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

                        if (estudante.getQntEmprestimos() >= 3) {
                            lbSituacao.setText("Usuário não pode realizar empréstimo");
                        } else {
                            lbSituacao.setText("Usuário pode realizar empréstimos");
                        }
                        usuarioEncontrado = true;
                        break;
                    }
                }
                break;
        }

        if (usuarioEncontrado) {
            mensagemResposta(verificarUsuarioMsgSuc, "Usuario encontrado");
        } else {
            mensagemResposta(verificarUsuarioMsgErr, "Usuario não encontrado");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        cbTipoUsuario.getItems().addAll(tipoUsuarios);

    }
}
