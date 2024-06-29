# Atividade Avaliativa 03

```
INTEGRANTES:    VINICIUS ALVES MEDEIROS
                PÉRICLES ANDRADE
                ARNAUD GABRIEL
                
DISCIPLINA:     LINGUAGEM DE PROGRAMAÇÃO II
PROFESSOR:      JANIHERYSON FELIPE DE OLIVEIRA MARTINS
```

# SisBib - Sistema de Gerenciamento Bibliotecário - JavaFX

## Descrição do Projeto

Este projeto é um sistema de biblioteca desenvolvido em JavaFX. Ele permite a gestão de livros, usuários (estudantes, professores e bibliotecários) e empréstimos. O sistema oferece uma interface gráfica para as operações de adicionar, remover e exibir livros e usuários, bem como exibir, realizar e devolver empréstimos.

Estrutura de Pastas e Arquivos
.vscode
Contém configurações específicas do Visual Studio Code.

settings.json: Arquivo de configuração para o editor, contendo preferências e extensões recomendadas.
sisbib
Pasta raiz do projeto.

pom.xml: Arquivo de configuração do Maven, utilizado para gerenciar dependências e build do projeto.
readme.md: Este arquivo, que contém a descrição do projeto.
tree.txt: Arquivo contendo a estrutura do projeto.
src/main/java
Contém o código-fonte do projeto.

module-info.java: Define o módulo do projeto, especificando as dependências e pacotes exportados.
br/ufrn/imd/lp2/projeto03
Pacote principal do projeto.

App.java: Classe principal que inicia a aplicação JavaFX.
br/ufrn/imd/lp2/projeto03/controller
Contém os controladores da interface gráfica, responsáveis por gerenciar a interação do usuário com a aplicação.

adicionarLivroController.java: Controlador para adicionar livros.
adicionarUsuarioController.java: Controlador para adicionar usuários.
dadosController.java: Controlador para gerenciar dados gerais.
exibirEmprestimosController.java: Controlador para exibir empréstimos.
exibirLivrosController.java: Controlador para exibir livros.
loginController.java: Controlador para a tela de login.
menuController.java: Controlador para o menu principal.
realizarDevolucaoController.java: Controlador para realizar devoluções.
realizarEmprestimoController.java: Controlador para realizar empréstimos.
removerLivroController.java: Controlador para remover livros.
removerUsuarioController.java: Controlador para remover usuários.
verificarUsuarioController.java: Controlador para verificar usuários.
br/ufrn/imd/lp2/projeto03/DAO
Contém classes para acesso a dados.

BancoDAO.java: Classe que gerencia a conexão com o banco de dados.
dados.bin: Arquivo binário de dados.
DadosMock.txt: Arquivo de texto contendo dados fictícios para testes.
br/ufrn/imd/lp2/projeto03/models
Contém as classes de modelo do projeto.

Bibliotecario.java: Classe que representa um bibliotecário.
Emprestimo.java: Classe que representa um empréstimo.
Estudante.java: Classe que representa um estudante.
Livro.java: Classe que representa um livro.
Professor.java: Classe que representa um professor.
Usuario.java: Classe que representa um usuário.
br/ufrn/imd/lp2/projeto03/services
Contém classes de serviço que implementam a lógica de negócios do sistema.

OperacoesDados.java: Classe para operações gerais com dados.
OperacoesEmprestimos.java: Classe para operações relacionadas a empréstimos.
OperacoesLivros.java: Classe para operações relacionadas a livros.
OperacoesUsuarios.java: Classe para operações relacionadas a usuários.
br/ufrn/imd/lp2/projeto03/utils
Contém classes utilitárias.

EstadoLivro.java: Enumeração que define os estados possíveis de um livro.
TipoUsuario.java: Enumeração que define os tipos possíveis de usuários.
src/main/resources
Contém os recursos do projeto, como arquivos FXML e imagens.

br/ufrn/imd/lp2/projeto03/hold
Contém arquivos FXML que ainda não foram alocados a uma funcionalidade específica.

EmBranco.fxml: Template em branco.
Exibir_Livros.fxml: Layout para exibir livros.
br/ufrn/imd/lp2/projeto03/images
Contém imagens utilizadas na interface gráfica.

back.png: Ícone de voltar.
book.png: Ícone de livro.
borrow.png: Ícone de empréstimo.
logo1.png: Primeira versão do logotipo.
logo2.png: Segunda versão do logotipo.
logout.png: Ícone de logout.
user.png: Ícone de usuário.
br/ufrn/imd/lp2/projeto03/view
Contém arquivos FXML que definem a estrutura da interface gráfica.

adicionarLivro.fxml: Layout para adicionar livros.
adicionarUsuario.fxml: Layout para adicionar usuários.
exibirEmprestimos.fxml: Layout para exibir empréstimos.
exibirLivros.fxml: Layout para exibir livros.
login.fxml: Layout para a tela de login.
menuPrincipal.fxml: Layout para o menu principal.
realizarDevolucao.fxml: Layout para realizar devoluções.
realizarEmprestimo.fxml: Layout para realizar empréstimos.
removerLivro.fxml: Layout para remover livros.
removerUsuario.fxml: Layout para remover usuários.
verificarUsuario.fxml: Layout para verificar usuários.
target
Contém os arquivos compilados do projeto.

classes: Contém os arquivos .class gerados após a compilação.
module-info.class: Arquivo .class do módulo.
br/ufrn/imd/lp2/projeto03: Estrutura similar à pasta src/main/java, mas com os arquivos compilados.
Como Executar o Projeto
Certifique-se de ter o Java Development Kit (JDK) e o Apache Maven instalados.
Clone o repositório para o seu ambiente local.
Navegue até a pasta raiz do projeto.
Execute o comando mvn clean install para compilar o projeto e baixar as dependências necessárias.
Execute o comando mvn javafx:run para iniciar a aplicação JavaFX.
Contribuição
Para contribuir com o projeto, siga os passos abaixo:

Faça um fork do repositório.
Crie uma branch para sua feature (git checkout -b feature/nova-feature).
Commit suas mudanças (git commit -m 'Adiciona nova feature').
Faça o push para a branch (git push origin feature/nova-feature).
Abra um Pull Request.
Licença
Este projeto é licenciado sob a MIT License.

## Packages

- **Model**: Arquivos de classes modelos (Estudante, Bibliotecario, Professor, Usuario, Livro, Emprestimo);
  - Estudante, Professor e Bibliotecario compartilham dados em comum, então herdam da classe Usuario (nome, CPF, matrícula, data de nascimento e quantidade de livros emprestados);
    - Foi adicionada à classe Usuario a variável qntEmprestimos para facilitar a contagem e verificação de quantos empréstimos cada usuário pode fazer.
    - Foi adicionada à classe Livro a variável patrimonio, que fornece um código distinto para cada empréstimo no ArrayList, facilitando as comparações.
  - Emprestimo recebe dados de todas as outras classes e possui vários construtores para cada tipo de usuário, juntamente com os dados do livro escolhido e as datas de recebimento e previsão de entrega.
- **Controller**: Arquivos com as funções de cada classe, mantendo juntos os arquivos relacionados;
  - ControladorDados: Funções de controle de dados no arquivo binário, que recebem e salvam os dados manipulados no início e no fim do código.
  - ControladorMenus: O código main possui um menu principal com as principais escolhas e alguns menus secundários, com escolhas específicas, esses submenus estão presentes nesse código, encaminhando para o controlador específico.
  - ControlerUsuarios: Funções de adição, remoção e verificação para cada tipo de usuario.
  - ControladorLivros: Funções de adição, remoção, pesquisa e exibição dos livros da biblioteca.
  - ControladorEmprestimos: Funções de realizar, devolver e exibir os empréstimos, alterando o o estoque dos livros e a quantidade de livros emprestados pelo usuario.
- **Service**: Arquivos interface para cada controlador.
- **utils**: Arquivos de Enums.
- **auth**: arquivo de interface para autenticação do bibliotecario logado.
- **DAO**: Banco de dados com as arraylists e funcoes de iniciação, **incluindo o construtor instanciável**, além do arquivo binario.

## Configuração e Execução

### Dados de Login do Bibliotecario

```
Login: admin
Senha: admin
```

### Menu Principal

O código itera por um menu principal, que levam à submenus respectivos:
```
Sistema de Gerenciamento de Biblioteca
1. Exibir Livros
2. Adicionar Livro
3. Remover Livro
4. Adicionar Usuário
5. Remover Usuário
6. Exibir Empréstimos
7. Realizar Empréstimos
8. Realizar Devolução
9. Verificar Situação de Usuário
10. Sair
Escolha uma opção: 
```

Exemplo de submenu: 

```
##### Realizar Empréstimo #####
Como gostaria de pesquisar o livro?
1. Título
2. Autor
3. Assunto
4. Ano de Lançamento
5. Patrimonio
6. Sair
Escolha uma opção:
```

## Itens do menu principal e lógica dos códigos:

1. Exibir livros
   - Permite escolha entre visualizar um livro especifico ou todos os livros;
2. Adicionar Livro
   - Checa se o livro já está cadastrado, se tiver, incrementa a variável "estoque";
3. Remover Livro
   - Checa se o livro existe antes de excluir;
4. Adicionar Usuario
   - Escolhe o tipo de usuario (Professor, Estudante ou Bibliotecario), checa se o usuário já tem CPF cadastrado;
5. Remover Usuario
   - Assim como o livro, checa se  o usuario existe antes de remover;
6. Exibir Empréstimos
7. Realizar Empréstimos
   1. O usuario escolhe qual o tipo de usuário vai fazer o emprestimo;
   2. Escolhe o livro que deseja fazer o empréstimo, se for encontrado, verifica se ele tem estoque > 0;
   3. De acordo com o tipo de usuario, verifica se a quantidade máxima de emprestimos foi atingida;
   4. Se tudo estiver OK, prosseguir para a adição ao ArrayList de empréstimos, incrementando a quantidade de emprestimos do usuario e decrementando a quantidade de estoque do livro.
8. Realizar Devoluções:
   1. Exibe todos os empréstimos e qual o tipo de usuario de cada emprestimo
   2. Informa qual livro deseja devolver, pelo critério do "patrimonio".
   3. Se o usuário e o livro estiverem corretos, realiza a devolução do livro selecionado.
9. Verificar Situação de Usuario
   1. Obtém o CPF e busca o tipo de usuário vinculado a ele;
   2. Imprime os dados principais e verifica se ele ainda pode fazer empréstimos.
10. Sair
