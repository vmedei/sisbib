package br.ufrn.imd.lp2.projeto03.controller;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.Bibliotecario;

import java.io.IOException;
import java.util.Objects;
import javafx.util.Duration;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;

public class loginController {

    void limparMensagens(){
        loginMsgErro.setText("");
        loginMsgErro.setVisible(false);
        loginMsgSucesso.setText("");
        loginMsgSucesso.setVisible(false);
    }

    void mensagemResposta(Label mensagem, String texto){
        limparMensagens();
        mensagem.setText(texto);
        mensagem.setPadding(new Insets(5, 10, 5, 10));
        mensagem.setVisible(true);
    }

    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private Button btLogin, btSerializar;

    @FXML
    private Label loginMsgSucesso, loginMsgErro;

    @SuppressWarnings("exports")
    @FXML
    public void btLoginOnAction(ActionEvent e) throws IOException {

        BancoDAO bancoDAO = BancoDAO.getInstance();

        String login = tfUsuario.getText();
        String senha = pfSenha.getText();

        try {

            if (login.isBlank() || senha.isBlank()){
                throw new Exception("Insira Usuário e Senha");
            }

            // Verifica se há bibliotecários na lista
            if (bancoDAO.getBibliotecarios().isEmpty() || bancoDAO.getBibliotecarios().stream().allMatch(Objects::isNull)) {
                throw new Exception("Não foram encontrados bibliotecários.");
            }

            // Busca o primeiro bibliotecário não nulo na lista
            Bibliotecario bibliotecario = bancoDAO.getBibliotecarios().stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElseThrow(() -> new Exception("Não foram encontrados bibliotecários."));

            // Verifica as credenciais
            if (!bibliotecario.authenticate(login, senha)) {
                throw new Exception("Credenciais inválidas");
            }

            // Autenticação bem-sucedida
            mensagemResposta(loginMsgSucesso, "Login Bem Sucedido!");

            btLogin.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(2)); // Delay de 2 segundos
            delay.setOnFinished(event -> {
                try {
                    mudarTela_login();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            delay.play();

        } catch (Exception e2) {
            // Autenticação falhou
            mensagemResposta(loginMsgErro, "Autenticação falhou: " + e2.getMessage());
            if (e2.getMessage().contains("Não foram encontrados bibliotecários.")) {
                btSerializar.setVisible(true);
            }
        }
    }

    @FXML
    void btSerializarOnAction(ActionEvent event) {
        dadosController operacoesDados = new dadosController();

        try {
            operacoesDados.serializarDados();
            mensagemResposta(loginMsgSucesso, "Dados Serializados. Por favor, reinicie o programa e faça o login novamente");

            btLogin.setDisable(true);
            btSerializar.setDisable(true);
        } catch (Exception e) {
            mensagemResposta(loginMsgErro, "Falha na serialização de dados: " + e.getMessage());
        }
    }

    @FXML
    private void mudarTela_login() throws IOException {
        App.trocarRoot("view/menuPrincipal.fxml");
    }
}
