package br.ufrn.imd.lp2.projeto03.controller;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.Livro;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;

public class removerLivroController implements Initializable {

    static BancoDAO biblioteca = BancoDAO.getInstance();

    @FXML
    private Button btnPesquisar, btnRemover;

    @FXML
    private ChoiceBox<String> cbCriterio;

    private String[] criterios = { "Titulo", "Autor", "Assunto", "Ano Lancamento" };

    @FXML
    private Label lbTitulo, lbAutor, lbAssunto, lbAno;

    @FXML
    private Label pesquisarLivroMsgErr, pesquisarLivroMsgSuc, removerLivroMsgErr, removerLivroMsgSuc;

    @FXML
    private TextField tfPesquisa;

    @FXML
    private Spinner<Integer> spQuantidade;

    @FXML
    private RadioButton rbUnidade, rbTodos;

    @FXML
    private void mudarQuantidade() {

        if (rbTodos.isSelected()) {
            spQuantidade.setDisable(true);
        } else if (rbUnidade.isSelected()) {
            spQuantidade.setDisable(false);
        }
    }

    @FXML
    private void mudarTela_voltar() throws IOException {
        App.trocarRoot("view/menuPrincipal.fxml");
    }

    private String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    void limparMensagens() {
        pesquisarLivroMsgErr.setText("");
        pesquisarLivroMsgErr.setVisible(false);
        pesquisarLivroMsgSuc.setText("");
        pesquisarLivroMsgSuc.setVisible(false);
        removerLivroMsgErr.setText("");
        removerLivroMsgErr.setVisible(false);
        removerLivroMsgSuc.setText("");
        removerLivroMsgSuc.setVisible(false);
    }

    void mensagemResposta(Label mensagem, String texto) {
        limparMensagens();
        mensagem.setText(texto);
        mensagem.setPadding(new Insets(5, 10, 5, 10));
        mensagem.setVisible(true);
    }

    void limparLabels() {
        lbTitulo.setText("");
        lbAutor.setText("");
        lbAssunto.setText("");
        lbAno.setText("");
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

    Livro livroEncontrado = null;

    @FXML
    private void btnPesquisarOnAction() {

        livroEncontrado = null;
        limparLabels();

        ArrayList<Livro> livros = biblioteca.getLivros();

        String criterio = cbCriterio.getValue();
        String pesquisa = tfPesquisa.getText();

        if (criterio == null || pesquisa.isEmpty()) {
            mensagemResposta(pesquisarLivroMsgErr, "Insira um critério de pesquisa");
            return;
        }

        pesquisa = removerAcentos(pesquisa).toLowerCase(); // Remover acentos e converter para minúsculas

        for (Livro livro : livros) {
            boolean encontrado = false;
            switch (criterio) {
                case "Titulo":
                    encontrado = removerAcentos(livro.getTitulo()).toLowerCase().contains(pesquisa);
                    break;
                case "Autor":
                    encontrado = removerAcentos(livro.getAutor()).toLowerCase().contains(pesquisa);
                    break;
                case "Assunto":
                    encontrado = removerAcentos(livro.getAssunto()).toLowerCase().contains(pesquisa);
                    break;
                case "Ano Lancamento":
                    encontrado = String.valueOf(livro.getAnoLancamento()).contains(pesquisa);
                    break;
            }

            if (encontrado) {
                livroEncontrado = livro;
                break;
            }
        }

        if (livroEncontrado != null) {
            mensagemResposta(pesquisarLivroMsgSuc, "Livro Encontrado");
            lbTitulo.setText(livroEncontrado.getTitulo());
            lbAutor.setText(livroEncontrado.getAutor());
            lbAssunto.setText(livroEncontrado.getAssunto());
            lbAno.setText(String.valueOf(livroEncontrado.getAnoLancamento()));

            btnRemover.setDisable(false);
            
        } else {
            mensagemResposta(pesquisarLivroMsgErr, "Nenhum Livro Encontrado");
        }
    }

    public void btnRemoverLivroOnAction() {

        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Excluir Livro");
        alert.setHeaderText("Confirmar Exclusão");
        alert.setContentText(
                "Deseja Excluir o(s) livro(s) selecionado(s? Livro: \"" + livroEncontrado.getTitulo() + "\"");

        if (alert.showAndWait().get() == ButtonType.OK) {

            if (rbTodos.isSelected()) {
                biblioteca.getLivros().remove(livroEncontrado);
            }

            if (rbUnidade.isSelected()) {

                int qtdAtual = livroEncontrado.getQtdEstoque();
                Integer qtdSelecionada = spQuantidade.getValue();
                int qtdTotal = (qtdAtual - qtdSelecionada);

                if (qtdTotal <= 0) {
                    biblioteca.getLivros().remove(livroEncontrado);
                } else {
                    livroEncontrado.setQtdEstoque(qtdTotal);
                }
            }

            mensagemResposta(removerLivroMsgSuc, "Livro(s) removido(s) com sucesso!");

            btnRemover.setDisable(true);

            redirecionar();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        cbCriterio.getItems().addAll(criterios);

        SpinnerValueFactory<Integer> valorQuantidade = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valorQuantidade.setValue(1);
        spQuantidade.setValueFactory(valorQuantidade);

    }

}
