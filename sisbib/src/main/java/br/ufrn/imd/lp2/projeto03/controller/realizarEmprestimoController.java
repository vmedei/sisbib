package br.ufrn.imd.lp2.projeto03.controller;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import br.ufrn.imd.lp2.projeto03.App;
import br.ufrn.imd.lp2.projeto03.DAO.BancoDAO;
import br.ufrn.imd.lp2.projeto03.models.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class realizarEmprestimoController implements Initializable {

    static BancoDAO biblioteca = BancoDAO.getInstance();

    @FXML
    private void mudarTela_voltar() throws IOException {
        App.trocarRoot("view/menuPrincipal.fxml");
    }

    @FXML
    private Button btnPesquisarLivro, btnPesquisarUsuario, btnEnviar;

    @FXML
    private ChoiceBox<String> cbCriterio, cbTipoUsuario;

    private String[] criterios = { "Titulo", "Autor", "Assunto", "Ano Lancamento" };
    private String[] tipoUsuarios = { "Bibliotecario", "Estudante", "Professor" };

    @FXML
    private DatePicker dpDataEmprestimo, dpDataDevolucao;

    @FXML
    private Label lbNome, lbCpf, lbMatricula, lbQtdEmprestimos;

    @FXML
    private Label lbTitulo, lbAutor, lbAssunto, lbAno, lbEstoque;

    @FXML
    private Label pesquisarLivroMsgErr, pesquisarLivroMsgSuc, pesquisarUsuarioMsgErr, pesquisarUsuarioMsgSuc,
            realizarEmprestimoMsgErr, realizarEmprestimoMsgSuc;

    @FXML
    private TextField tfLivro, tfUsuario;

    @FXML
    private HBox hbData, hbPesquisarUsuario;

    Livro livroEncontrado = null;

    private void limparMensagens() {
        pesquisarLivroMsgErr.setText("");
        pesquisarLivroMsgErr.setVisible(false);
        pesquisarLivroMsgSuc.setText("");
        pesquisarLivroMsgSuc.setVisible(false);
        pesquisarUsuarioMsgErr.setText("");
        pesquisarUsuarioMsgErr.setVisible(false);
        pesquisarUsuarioMsgSuc.setText("");
        pesquisarUsuarioMsgSuc.setVisible(false);
        realizarEmprestimoMsgErr.setText("");
        realizarEmprestimoMsgErr.setVisible(false);
        realizarEmprestimoMsgSuc.setText("");
        realizarEmprestimoMsgSuc.setVisible(false);
    }

    private void mensagemResposta(Label mensagem, String texto) {
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

    private String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    @FXML
    void btbPesquisarLivroOnAction(ActionEvent event) {

        ArrayList<Livro> livros = biblioteca.getLivros();
        String criterio = cbCriterio.getValue();
        String pesquisa = tfLivro.getText();
        pesquisa = removerAcentos(pesquisa).toLowerCase(); // Remover acentos e converter para minúsculas

        if (criterio == null || pesquisa.isBlank()) {
            mensagemResposta(pesquisarLivroMsgErr, "Insira um critério de pesquisa");
            return;
        }

        livroEncontrado = buscarLivro(livros, criterio, pesquisa);

        if (livroEncontrado != null) {
            if (livroEncontrado.getQtdEstoque() <= 0) {
                exibirInformacoesLivro(livroEncontrado);
                mensagemResposta(pesquisarLivroMsgErr, "Livro fora de estoque");
            } else {
                mensagemResposta(pesquisarLivroMsgSuc, "Livro Encontrado. Escolha o usuário");
                exibirInformacoesLivro(livroEncontrado);
                hbPesquisarUsuario.setDisable(false);
            }
        } else {
            mensagemResposta(pesquisarLivroMsgErr, "Nenhum Livro Encontrado");
        }
    }

    private Livro buscarLivro(List<Livro> livros, String criterio, String pesquisa) {
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
                return livro;
            }
        }
        return null;
    }

    private void exibirInformacoesLivro(Livro livro) {
        lbTitulo.setText(livro.getTitulo());
        lbAutor.setText(livro.getAutor());
        lbAssunto.setText(livro.getAssunto());
        lbAno.setText(String.valueOf(livro.getAnoLancamento()));
        lbEstoque.setText(String.valueOf(livro.getQtdEstoque()));
    }

    private Bibliotecario bibliotecarioEncontrado = null;
    private Estudante estudanteEncontrado = null;
    private Professor professorEncontrado = null;

    boolean limiteEmprestimo = false;

    @FXML
    void btnPesquisarUsuarioOnAction(ActionEvent event) {

        String tipoUsuario = cbTipoUsuario.getValue();
        String pesquisa = tfUsuario.getText();

        if (tipoUsuario == null || pesquisa.isBlank()) {
            mensagemResposta(pesquisarUsuarioMsgErr, "Escolha o tipo de usuário e insira o CPF");
            return;
        }

        limiteEmprestimo = false;

        switch (tipoUsuario) {
            case "Bibliotecario":
                bibliotecarioEncontrado = buscarUsuario(biblioteca.getBibliotecarios(), pesquisa, 5);
                break;
            case "Professor":
                professorEncontrado = buscarUsuario(biblioteca.getProfessores(), pesquisa, 5);
                break;
            case "Estudante":
                estudanteEncontrado = buscarUsuario(biblioteca.getEstudantes(), pesquisa, 3);
                break;
        }

        if (limiteEmprestimo) {
            mensagemResposta(pesquisarUsuarioMsgErr, "Limite de empréstimos atingido");
            return;
        } else if (bibliotecarioEncontrado != null || professorEncontrado != null || estudanteEncontrado != null) {
            mensagemResposta(pesquisarUsuarioMsgSuc, "Usuario encontrado. Escolha a data de empréstimo");
            btnEnviar.setDisable(false);
            hbData.setDisable(false);
        } else {
            mensagemResposta(pesquisarUsuarioMsgErr, "Usuario não encontrado");
            return;
        }
    }

    private <T extends Usuario> T buscarUsuario(List<T> usuarios, String cpf, int limiteEmprestimos) {
        for (T usuario : usuarios) {
            if (Objects.equals(usuario.getCpf(), cpf)) {
                exibirInformacoesUsuario(usuario);
                if (usuario.getQntEmprestimos() >= limiteEmprestimos) {
                    limiteEmprestimo = true;
                }
                return usuario;
            }
        }
        return null;
    }

    private void exibirInformacoesUsuario(Usuario usuario) {
        lbNome.setText(usuario.getNome());
        lbCpf.setText(usuario.getCpf());
        lbMatricula.setText(usuario.getMatricula());
        lbQtdEmprestimos.setText(String.valueOf(usuario.getQntEmprestimos()));
    }

    @FXML
    private void dpDataOnAction() {
        LocalDate data = dpDataEmprestimo.getValue();
        dpDataDevolucao.setValue(data.plusDays(15));
    }

    @FXML
    void btnEnviarOnAction(ActionEvent event) {

        Emprestimo emprestimo = null;

        LocalDate dataEmprestimo = dpDataEmprestimo.getValue();
        LocalDate dataDevolucao = dpDataDevolucao.getValue();

        if (bibliotecarioEncontrado != null) {
            bibliotecarioEncontrado.setQntEmprestimos(bibliotecarioEncontrado.getQntEmprestimos() + 1);
            emprestimo = new Emprestimo(bibliotecarioEncontrado, livroEncontrado, dataEmprestimo, dataDevolucao);
        } else if (professorEncontrado != null) {
            professorEncontrado.setQntEmprestimos(professorEncontrado.getQntEmprestimos() + 1);
            emprestimo = new Emprestimo(professorEncontrado, livroEncontrado, dataEmprestimo, dataDevolucao);
        } else if (estudanteEncontrado != null) {
            estudanteEncontrado.setQntEmprestimos(estudanteEncontrado.getQntEmprestimos() + 1);
            emprestimo = new Emprestimo(estudanteEncontrado, livroEncontrado, dataEmprestimo, dataDevolucao);
        }

        livroEncontrado.setQtdEstoque(livroEncontrado.getQtdEstoque() - 1);

        try {
            if (emprestimo != null) {
                biblioteca.getEmprestimosAtivos().add(emprestimo);
                mensagemResposta(realizarEmprestimoMsgSuc, "Empréstimo realizado com sucesso");
                btnEnviar.setDisable(true);
                redirecionar();
            } else {
                mensagemResposta(realizarEmprestimoMsgErr, "Erro ao criar empréstimo. Usuário não encontrado.");
            }
        } catch (Exception e) {
            mensagemResposta(realizarEmprestimoMsgErr, "Erro ao realizar empréstimo. Erro: " + e);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbCriterio.getItems().addAll(criterios);
        cbTipoUsuario.getItems().addAll(tipoUsuarios);

        LocalDate dataHoje = LocalDate.now();
        dpDataEmprestimo.setValue(dataHoje);
        dpDataDevolucao.setValue(dataHoje.plusDays(15));
    }
}
