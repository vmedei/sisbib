package br.ufrn.imd.lp2.projeto03.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.Livro;
import br.ufrn.imd.lp2.projeto03.utils.EstadoLivro;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;

public class adicionarLivroController implements Initializable {

    static BancoDAO biblioteca = BancoDAO.getInstance();

    @FXML
    private Label adicionarLivroMsgErr, adicionarLivroMsgSuc;

    @FXML
    private TextField tfTitulo, tfAutor, tfAssunto;

    @FXML
    private Spinner<Integer> spAno, spQuantidade;

    @FXML
    private RadioButton rbNovo, rbBom, rbRuim;

    @FXML
    private Button btnEnviar;
    
    @FXML
    private ToggleGroup tgEstado;

    EstadoLivro estado;

    @FXML
    private void mudarEstado(ActionEvent event){

        if (rbNovo.isSelected()) {
            estado = EstadoLivro.NOVO;
        } else if (rbBom.isSelected()) {
            estado = EstadoLivro.BOM;
        } else if (rbRuim.isSelected()) {
            estado = EstadoLivro.RUIM;
        }

    }

    void limparMensagens(){
        adicionarLivroMsgErr.setText("");
        adicionarLivroMsgErr.setVisible(false);
        adicionarLivroMsgSuc.setText("");
        adicionarLivroMsgSuc.setVisible(false);
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
    private void btnEnviarOnAction(){

        ArrayList<Livro> livros = biblioteca.getLivros();
        Livro ultimoLivro = livros.get(livros.size()-1);

        String titulo = tfTitulo.getText();
        String autor = tfAutor.getText();
        String assunto = tfAssunto.getText();
        Integer anoLancamento = spAno.getValue();
        Integer qtdEstoque = spQuantidade.getValue();
        Integer patrimonio = ultimoLivro.getPatrimonio() + 1;

        if ( titulo.isBlank() || autor.isBlank() || assunto.isBlank() ){
            mensagemResposta(adicionarLivroMsgErr, "Campo(s) vazio(s). favor, inserir todos os campos");
            return;
        }

        Livro novoLivro = new Livro (
            titulo,
            autor,
            assunto,
            anoLancamento,
            estado,
            qtdEstoque,
            patrimonio)
        ;
        
        boolean livroExistente = false;
        
        for (Livro livro : livros) {
            if (Objects.equals(livro.getTitulo(), novoLivro.getTitulo()) &&
                Objects.equals(livro.getAutor(), novoLivro.getAutor()) &&
                Objects.equals(livro.getAssunto(), novoLivro.getAssunto()))
            {
                livroExistente = true;
                livro.setQtdEstoque( livro.getQtdEstoque() + novoLivro.getQtdEstoque() );

                mensagemResposta(adicionarLivroMsgSuc, "Livro já existente, quantidade incrementada ao estoque");

                btnEnviar.setDisable(true);
                redirecionar();

                break;
            }
        }

        if (!livroExistente) {
            biblioteca.getLivros().add(novoLivro);

            mensagemResposta(adicionarLivroMsgSuc, "Livro adicionado com sucesso");

            btnEnviar.setDisable(true);
            redirecionar();
        }
    }
    
    @FXML
    private void mudarTela_voltar() throws IOException {
        App.trocarRoot("view/menuPrincipal.fxml");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        SpinnerValueFactory<Integer> valorQuantidade = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        SpinnerValueFactory<Integer> valorAnoLancamento = new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2099);

        valorQuantidade.setValue(1);
        valorAnoLancamento.setValue(2024);

        spQuantidade.setValueFactory(valorQuantidade);
        spAno.setValueFactory(valorAnoLancamento);

    }
    
}
