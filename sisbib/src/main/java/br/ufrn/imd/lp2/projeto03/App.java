package br.ufrn.imd.lp2.projeto03;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import br.ufrn.imd.lp2.projeto03.controller.dadosController;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    @SuppressWarnings("exports")
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

        stage.setOnCloseRequest(event -> {
            event.consume(); // Consome o evento para impedir o fechamento imediato
            confirmarFechamento(stage);
        });

    }

    public static void main(String[] args) {

        dadosController operacoesDados = new dadosController();

        // operacoesDados.serializarDados();
        
        operacoesDados.inicializarDados();

        launch();
    }

    public static void trocarRoot(String root) throws IOException {
        Parent novaTela = FXMLLoader.load(App.class.getResource(root));
        scene.setRoot(novaTela);
    }

    private void confirmarFechamento(Stage stage) {

        dadosController operacoesDados = new dadosController();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Saída");
        alert.setHeaderText("Você está prestes a fechar a aplicação.");
        alert.setContentText("Deseja salvar os dados antes de sair?");

        ButtonType buttonSim = new ButtonType("Sim");
        ButtonType buttonNao = new ButtonType("Não");
        alert.getButtonTypes().setAll(buttonSim, buttonNao);

        alert.showAndWait().ifPresent(tipo -> {
            if (tipo == buttonSim) {
                if (operacoesDados.salvarDados()) {
                    Platform.exit(); // Fecha a aplicação após salvar os dados
                }
            } else if (tipo == buttonNao) {
                Platform.exit(); // Fecha a aplicação sem salvar os dados
            }
        });
    }

}