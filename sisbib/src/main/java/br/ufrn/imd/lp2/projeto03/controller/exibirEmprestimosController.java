package br.ufrn.imd.lp2.projeto03.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class exibirEmprestimosController implements Initializable {

    static BancoDAO biblioteca = BancoDAO.getInstance();

    @FXML
    private void mudarTela_voltar() throws IOException {
        App.trocarRoot("view/menuPrincipal.fxml");
    }

    @FXML
    private Button btnExibir;

    @FXML
    private TableView<Emprestimo> tabela;

    @FXML
    private TableColumn<Emprestimo, String> colDataDevolucao, colDataEmprestimo, colLivro, colNome, colUsuario, colMatricula;

    @FXML
    private Label exibirEmprestimosMsgErr, exibirEmprestimosMsgSuc;

    @FXML
    private ObservableList<Emprestimo> lista = FXCollections.observableArrayList();

    void limparMensagens(){
        exibirEmprestimosMsgErr.setText("");
        exibirEmprestimosMsgErr.setVisible(false);
        exibirEmprestimosMsgSuc.setText("");
        exibirEmprestimosMsgSuc.setVisible(false);
    }

    void mensagemResposta(Label mensagem, String texto){
        limparMensagens();
        mensagem.setText(texto);
        mensagem.setPadding(new Insets(5, 10, 5, 10));
        mensagem.setVisible(true);
    }

    @FXML
    void btnExibirOnAction(ActionEvent event) {

        lista.clear(); // Limpa a lista existente

        ArrayList<Emprestimo> emprestimos = biblioteca.getEmprestimosAtivos();

        if (emprestimos.isEmpty()) {
            mensagemResposta(exibirEmprestimosMsgErr, "Não há emprestimos cadastrados");
        } else {
            mensagemResposta(exibirEmprestimosMsgSuc, "Exibindo todos os empréstimos");
            lista.addAll(emprestimos);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        colDataEmprestimo.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        colDataDevolucao.setCellValueFactory(new PropertyValueFactory<>("dataDevolucaoPrevista"));
        colLivro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLivroEmprestimo().getTitulo()));
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuarioNome()));
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoUsuario()));
        colMatricula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuarioMatricula()));

        tabela.setItems(lista);
    }
}
