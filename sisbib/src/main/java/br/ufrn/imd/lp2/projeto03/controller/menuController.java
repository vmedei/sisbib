package br.ufrn.imd.lp2.projeto03.controller;
import br.ufrn.imd.lp2.projeto03.App;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class menuController {

    @FXML
    private void mudarTela_logout() throws IOException {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmação de Logout");
    alert.setHeaderText("Você realmente deseja fazer logout?");
    alert.setContentText("Todas as alterações não salvas serão perdidas.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        App.trocarRoot("view/login.fxml");
    }
}

    // LIVROS

    @FXML
    private void mudarTela_exibirLivros() throws IOException {
        App.trocarRoot("view/exibirLivros.fxml");
    }

    @FXML
    private void mudarTela_adicionarLivro() throws IOException {
        App.trocarRoot("view/adicionarLivro.fxml");
    }

    @FXML
    private void mudarTela_removerLivro() throws IOException {
        App.trocarRoot("view/removerLivro.fxml");
    }

    // USUÁRIOS

    @FXML
    private void mudarTela_verificarUsuario() throws IOException {
        App.trocarRoot("view/verificarUsuario.fxml");
    }

    @FXML
    private void mudarTela_adicionarUsuario() throws IOException {
        App.trocarRoot("view/adicionarUsuario.fxml");
    }

    @FXML
    private void mudarTela_removerUsuario() throws IOException {
        App.trocarRoot("view/removerUsuario.fxml");
    }

    // EMPRÉSTIMOS

    @FXML
    private void mudarTela_exibirEmprestimos() throws IOException {
        App.trocarRoot("view/exibirEmprestimos.fxml");
    }

    @FXML
    private void mudarTela_realizarEmprestimo() throws IOException {
        App.trocarRoot("view/realizarEmprestimo.fxml");
    }

    @FXML
    private void mudarTela_realizarDevolucao() throws IOException {
        App.trocarRoot("view/realizarDevolucao.fxml");
    }
}