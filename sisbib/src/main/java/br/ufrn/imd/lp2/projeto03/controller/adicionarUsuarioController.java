package br.ufrn.imd.lp2.projeto03.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.Bibliotecario;
import br.ufrn.imd.lp2.projeto03.models.Estudante;
import br.ufrn.imd.lp2.projeto03.models.Professor;
import br.ufrn.imd.lp2.projeto03.utils.TipoUsuario;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class adicionarUsuarioController {

    static BancoDAO biblioteca = BancoDAO.getInstance();

    @FXML
    private void mudarTela_voltar() throws IOException {
        App.trocarRoot("view/menuPrincipal.fxml");
    }

    @FXML
    private Label adicionarUsuarioMsgErr, adicionarUsuarioMsgSuc;

    @FXML
    private ToggleGroup tgTipoUsuario;

    @FXML
    private RadioButton rbBibliotecario, rbEstudante, rbProfessor;

    @FXML
    private TextField tfNome, tfCpf, tfMatricula;

    @FXML
    private DatePicker dpDataNascimento;

    @FXML
    private TextField tfDepartamento, tfCurso;

    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private HBox hbBibliotecario, hbEstudante, hbProfessor;

    @FXML
    private Button btnEnviar;

    private TipoUsuario tipoUsuario;

    void limparMensagens(){
        adicionarUsuarioMsgErr.setText("");
        adicionarUsuarioMsgErr.setVisible(false);
        adicionarUsuarioMsgSuc.setText("");
        adicionarUsuarioMsgSuc.setVisible(false);
    }

    void mensagemResposta(Label mensagem, String texto){
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

    @FXML
    private void mudarEstado() {

        if (!hbEstudante.isDisable()) hbEstudante.setDisable(true);
        if (!hbProfessor.isDisable()) hbProfessor.setDisable(true);
        if (!hbBibliotecario.isDisable()) hbBibliotecario.setDisable(true);
        if (btnEnviar.isDisable()) btnEnviar.setDisable(false);

        if (rbBibliotecario.isSelected()) {

            tipoUsuario = TipoUsuario.BIBLIOTECARIO;

            hbBibliotecario.setDisable(false);

        } else if (rbEstudante.isSelected()) {

            tipoUsuario = TipoUsuario.ESTUDANTE;

            hbEstudante.setDisable(false);

        } else if (rbProfessor.isSelected()) {

            tipoUsuario = TipoUsuario.PROFESSOR;

            hbProfessor.setDisable(false);
        }

    }

    @FXML
    void btnEnviarOnAction(ActionEvent event) {

        String nome = tfNome.getText();
        String cpf = tfCpf.getText();
        String matricula = tfMatricula.getText();
        LocalDate dataNascimento = dpDataNascimento.getValue();

        // Verificar campos vazios
        if (nome.isEmpty() || cpf.isEmpty() || matricula.isEmpty() || dataNascimento == null) {
            mensagemResposta(adicionarUsuarioMsgErr , "Por favor, insira todos os campos disponíveis.");
            return;
        }

        boolean usuarioCadastrado = false;
        boolean usuarioExistente = false;

        switch (tipoUsuario) {

            case BIBLIOTECARIO:
                ArrayList<Bibliotecario> bibliotecarios = biblioteca.getBibliotecarios();

                String usuario = tfUsuario.getText();
                String senha = pfSenha.getText();

                if (usuario.isBlank() || senha.isBlank()){
                    mensagemResposta(adicionarUsuarioMsgErr, "Insira Usuário e senha");
                    return;
                }

                Bibliotecario novoBibliotecario = new Bibliotecario(nome, cpf, matricula, dataNascimento, 0, usuario, senha);

                for (Bibliotecario bibliotecario : bibliotecarios) {
                    if (Objects.equals(novoBibliotecario.getCpf(), bibliotecario.getCpf())) {
                        usuarioExistente = true;
                        break;
                    }
                }

                if (!usuarioExistente) {
                    biblioteca.getBibliotecarios().add(novoBibliotecario);
                    usuarioCadastrado = true;
                }
                break;

            case ESTUDANTE:
                ArrayList<Estudante> estudantes = biblioteca.getEstudantes();

                String curso = tfCurso.getText();
                
                if (curso.isBlank()){
                    mensagemResposta(adicionarUsuarioMsgErr, "Insira o Curso");
                    return;
                }

                Estudante novoEstudante = new Estudante(nome, cpf, matricula, dataNascimento, 0, curso);

                for (Estudante estudante : estudantes) {
                    if (Objects.equals(novoEstudante.getCpf(), estudante.getCpf())) {
                        usuarioExistente = true;
                        break;
                    }
                }

                if (!usuarioExistente) {
                    biblioteca.getEstudantes().add(novoEstudante);
                    usuarioCadastrado = true;
                }
                break;

            case PROFESSOR:
                ArrayList<Professor> professores = biblioteca.getProfessores();

                String departamento = tfDepartamento.getText();

                Professor novoProfessor = new Professor(nome, cpf, matricula, dataNascimento, 0, departamento);

                if (departamento.isBlank()){
                    mensagemResposta(adicionarUsuarioMsgErr, "Insira o Departamento");
                    return;
                }

                for (Professor professor : professores) {
                    if (Objects.equals(novoProfessor.getCpf(), professor.getCpf())) {
                        usuarioExistente = true;
                        break;
                    }
                }

                if (!usuarioExistente) {
                    biblioteca.getProfessores().add(novoProfessor);
                    usuarioCadastrado = true;
                }
                break;
            default:
                break;
        }

        if (usuarioCadastrado) {
            btnEnviar.setDisable(true);
            mensagemResposta(adicionarUsuarioMsgSuc, "Usuário adicionado com sucesso");
            redirecionar();
        } else if (usuarioExistente) {
            mensagemResposta(adicionarUsuarioMsgErr, "Usuário já cadastrado");
        }
    }

}
