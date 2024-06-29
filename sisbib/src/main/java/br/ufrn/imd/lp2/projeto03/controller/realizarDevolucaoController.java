package br.ufrn.imd.lp2.projeto03.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.*;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class realizarDevolucaoController implements Initializable {

    static BancoDAO biblioteca = BancoDAO.getInstance();

    @FXML
    private void mudarTela_voltar() throws IOException {
        App.trocarRoot("view/menuPrincipal.fxml");
    }
    
    @FXML
    private Button btnPesquisarUsuario, btnSelecionarEmprestimo, btnEnviar;

    @FXML
    private TextField tfCpf;

    @FXML
    private Spinner<Integer> spPatrimonio;

    @FXML
    private ChoiceBox<String> cbTipoUsuario;

    private String[] tipoUsuarios = { "Bibliotecario", "Estudante", "Professor" };

    @FXML
    private Label lbNome, lbCpf, lbMatricula, lbQtdEmprestimo;

    @FXML
    private TableView<Emprestimo> tabela;

    @FXML
    private TableColumn<Emprestimo, String> colDataDevolucao, colDataEmprestimo, colPatrimonio, colTitulo;

    @FXML
    private HBox hbEmprestimo;

    @FXML
    private Label pesquisarUsuarioMsgErr, pesquisarUsuarioMsgSuc, SelecionarEmprestimoErr, SelecionarEmprestimoSuc, realizarDevolucaoMsgErr, realizarDevolucaoMsgSuc;

    void limparMensagens() {
        pesquisarUsuarioMsgErr.setText("");
        pesquisarUsuarioMsgErr.setVisible(false);
        pesquisarUsuarioMsgSuc.setText("");
        pesquisarUsuarioMsgSuc.setVisible(false);
        SelecionarEmprestimoErr.setText("");
        SelecionarEmprestimoErr.setVisible(false);
        SelecionarEmprestimoSuc.setText("");
        SelecionarEmprestimoSuc.setVisible(false);
        realizarDevolucaoMsgErr.setText("");
        realizarDevolucaoMsgErr.setVisible(false);
        realizarDevolucaoMsgSuc.setText("");
        realizarDevolucaoMsgSuc.setVisible(false);
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

    private Usuario usuarioEncontrado = null;

    @FXML
    void btnPesquisarUsuarioOnAction(ActionEvent event) {

        // Limpa todos os campos visuais
        lbNome.setText("");
        lbCpf.setText("");
        lbMatricula.setText("");
        lbQtdEmprestimo.setText("");

        String tipoUsuario = cbTipoUsuario.getValue();
        String pesquisa = tfCpf.getText();

        if (tipoUsuario == null || pesquisa.isBlank()) {
            mensagemResposta(pesquisarUsuarioMsgErr, "Escolha o tipo de usuário e insira o CPF");
            return;
        }

        usuarioEncontrado = null;

        switch (tipoUsuario) {
            case "Bibliotecario":
                usuarioEncontrado = buscarUsuario(biblioteca.getBibliotecarios(), pesquisa);
                break;
            case "Professor":
                usuarioEncontrado = buscarUsuario(biblioteca.getProfessores(), pesquisa);
                break;
            case "Estudante":
                usuarioEncontrado = buscarUsuario(biblioteca.getEstudantes(), pesquisa);
                break;
        }

        if (usuarioEncontrado != null) {
            hbEmprestimo.setDisable(false);
            mensagemResposta(pesquisarUsuarioMsgSuc, "Usuario encontrado");
        } else {
            mensagemResposta(pesquisarUsuarioMsgErr, "Usuario não encontrado");
        }

    }

    private <T extends Usuario> T buscarUsuario(List<T> usuarios, String cpf) {
        for (T usuario : usuarios) {
            if (Objects.equals(usuario.getCpf(), cpf)) {
                exibirInformacoesUsuario(usuario);
                return usuario;
            }
        }
        return null;
    }

    private void exibirInformacoesUsuario(Usuario usuario) {
        lbNome.setText(usuario.getNome());
        lbCpf.setText(usuario.getCpf());
        lbMatricula.setText(usuario.getMatricula());
        lbQtdEmprestimo.setText(String.valueOf(usuario.getQntEmprestimos()));
        exibirEmprestimos(usuario);
    }

    private ObservableList<Emprestimo> lista = FXCollections.observableArrayList();

    private Emprestimo emprestimoEncontrado = null;

    private void exibirEmprestimos(Usuario usuario) {

        lista.clear(); // Limpa a lista existente

        ArrayList<Emprestimo> emprestimos = biblioteca.getEmprestimosAtivos();
        String cpfUsuario = usuario.getCpf();

        for (Emprestimo emprestimo : emprestimos) {
            Usuario responsavelEmprestimo = null;

            switch (cbTipoUsuario.getValue()) {
                case "Bibliotecario":
                    responsavelEmprestimo = emprestimo.getBibliotecarioEmprestimo();
                    break;
                case "Professor":
                    responsavelEmprestimo = emprestimo.getProfessorEmprestimo();
                    break;
                case "Estudante":
                    responsavelEmprestimo = emprestimo.getEstudanteEmprestimo();
                    break;
            }

            if (responsavelEmprestimo != null && Objects.equals(responsavelEmprestimo.getCpf(), cpfUsuario)) {
                lista.add(emprestimo);
            }
        }
    }

    private Livro livroEncontrado = null;

    @FXML
    void btnSelecionarEmprestimoOnAction(ActionEvent event) {
        
        ArrayList<Emprestimo> emprestimos = biblioteca.getEmprestimosAtivos();

        Integer patrimonio = spPatrimonio.getValue();

        for (Emprestimo emprestimo : emprestimos) {
            if (Objects.equals(emprestimo.getLivroEmprestimo().getPatrimonio(), patrimonio)){
                livroEncontrado = emprestimo.getLivroEmprestimo();
                mensagemResposta(SelecionarEmprestimoSuc, "Patrimônio encontrado. Livro: " + livroEncontrado.getTitulo());
                btnEnviar.setDisable(false);
                emprestimoEncontrado = emprestimo;
                return;
            }
        }

        mensagemResposta(SelecionarEmprestimoErr, "Patrimônio não encontrado. Favor, insira outro patrimônio");
    }

    @FXML
    void btnEnviarOnAction(ActionEvent event) {

        int qtdEmprestimos = usuarioEncontrado.getQntEmprestimos();
        usuarioEncontrado.setQntEmprestimos(qtdEmprestimos - 1);

        int estoque = livroEncontrado.getQtdEstoque();
        livroEncontrado.setQtdEstoque(estoque + 1);

        if (emprestimoEncontrado != null) {
            biblioteca.getEmprestimosAtivos().remove(emprestimoEncontrado);
            mensagemResposta(realizarDevolucaoMsgSuc, "Livro devolvido com sucesso");
        } else {
            mensagemResposta(realizarDevolucaoMsgErr, "Erro ao devolver livro: Emprestimo não encontrado");
        }

        btnEnviar.setDisable(true);

        redirecionar();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        SpinnerValueFactory<Integer> valorPatrimonio = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000);

        cbTipoUsuario.getItems().addAll(tipoUsuarios);
        spPatrimonio.setValueFactory(valorPatrimonio);
        valorPatrimonio.setValue(202400);

        colPatrimonio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLivroEmprestimo().getPatrimonio())));
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLivroEmprestimo().getTitulo()));
        colDataEmprestimo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDataEmprestimo())));
        colDataDevolucao.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDataDevolucaoPrevista())));

        tabela.setItems(lista);

    }

}
