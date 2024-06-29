package br.ufrn.imd.lp2.projeto03.controller;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.Livro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class exibirLivrosController implements Initializable {

    static BancoDAO biblioteca = BancoDAO.getInstance();

    @FXML
    private TableView<Livro> tabela;

    @FXML
    private TableColumn<Livro, String> colTitulo, colAutor, colAssunto;

    @FXML
    private TableColumn<Livro, Integer> colAnoLancamento, colQntEstoque;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private Label exibirLivroMsgErr, exibirLivroMsgSuc;

    @FXML
    private ChoiceBox<String> cbCriterio;

    private String[] criterios = { "Titulo", "Autor", "Assunto", "Ano Lancamento" };

    @FXML
    private void mudarTela_voltar() throws IOException {
        App.trocarRoot("view/menuPrincipal.fxml");
    }

    @FXML
    private ObservableList<Livro> lista = FXCollections.observableArrayList();

    void limparMensagens() {
        exibirLivroMsgErr.setVisible(false);
        exibirLivroMsgErr.setText("");
        exibirLivroMsgSuc.setVisible(false);
        exibirLivroMsgSuc.setText("");
    }

    void mensagemResposta(Label mensagem, String texto) {
        limparMensagens();
        mensagem.setText(texto);
        mensagem.setPadding(new Insets(5, 10, 5, 10));
        mensagem.setVisible(true);
    }

    @FXML
    private void btnExibirOnAction() {

        lista.clear(); // Limpa a lista existente

        ArrayList<Livro> livros = biblioteca.getLivros();

        for (Livro livro : livros) {
            lista.add(new Livro(
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getAssunto(),
                    livro.getAnoLancamento(),
                    livro.getEstado(),
                    livro.getQtdEstoque(),
                    livro.getPatrimonio()));
        }

        mensagemResposta(exibirLivroMsgSuc, "Exibindo todos os livros");

        tabela.setItems(lista);
    }

    @FXML
    private void btnPesquisarOnAction() {

        lista.clear(); // Limpa a lista existente

        ArrayList<Livro> livros = biblioteca.getLivros();

        String criterio = cbCriterio.getValue();

        String pesquisa = tfPesquisar.getText().trim();

        if (criterio == null || pesquisa.isEmpty()) {
            mensagemResposta(exibirLivroMsgErr, "Insira um critério de pesquisa");
            return;
        }

        pesquisa = removerAcentos(pesquisa.toLowerCase());

        for (Livro livro : livros) {
            String comparacao = "";

            switch (criterio) {
                case "Titulo":
                    comparacao = removerAcentos(livro.getTitulo().toLowerCase());
                    break;
                case "Autor":
                    comparacao = removerAcentos(livro.getAutor().toLowerCase());
                    break;
                case "Assunto":
                    comparacao = removerAcentos(livro.getAssunto().toLowerCase());
                    break;
                case "Ano Lancamento":
                    comparacao = String.valueOf(livro.getAnoLancamento());
                    break;
            }

            if (comparacao.contains(pesquisa)) {
                mensagemResposta(exibirLivroMsgSuc, "Livro Encontrado");

                lista.add(new Livro(
                        livro.getTitulo(),
                        livro.getAutor(),
                        livro.getAssunto(),
                        livro.getAnoLancamento(),
                        livro.getEstado(),
                        livro.getQtdEstoque(),
                        livro.getPatrimonio()));
            }
        }

        if (lista.isEmpty()) {
            mensagemResposta(exibirLivroMsgErr, "Nenhum Livro Encontrado");
        }

        tabela.setItems(lista);
    }

    private String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        colTitulo.setCellValueFactory(new PropertyValueFactory<Livro, String>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Livro, String>("autor"));
        colAssunto.setCellValueFactory(new PropertyValueFactory<Livro, String>("assunto"));
        colAnoLancamento.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("anoLancamento"));
        colQntEstoque.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("qtdEstoque"));

        cbCriterio.getItems().addAll(criterios);
    }

}
