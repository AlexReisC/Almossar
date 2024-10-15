# Almossar
**Almossar** é um chatbot para Telegram que busca o cardápio do restaurante universitário da UFCA e envia para o usuário.

O cardapio do RU é disponibilizado no site da UFCA de forma semanal, em formato de PDF. Dessa forma, a proposta do bot é automatizar o processo de obtenção garantindo que o usuário receba o cardapio em formato de texto de forma diária, direto no Telegram.

## Use agora
- :white_check_mark: Receba o cardapio do almoço e do jantar todos os dias.
- :x: Sem perda de tempo, sem precisar baixar arquivos, sem a poluição visual de uma tabela.

:arrow_right: [Acesse aqui pelo Telegram](https://t.me/cardapioUFCA_bot)

## Funcionalidades

### Comandos
- `/start`: Inicia o bot e salva no banco o novo usuário.
- `/cardapio`: Exibe o cardápio do almoço (se ainda não são 14:00h) ou do jantar (se depois das 14:00h e antes de 0:00h).
- `/horarios`: Exibe o horário de funcionamento do Restaurante Universitário.
- `/ajuda`: Exibe os comandos disponíveis.
- `/sobre`: Exibe informações do bot.
- `/contato`: Exibe formas de entrar em contato comigo.

## Primeiros passos

### Pré-requisitos

- **Java 11** ou **superior**
- **Maven** para configurar dependências
- **PostgreSQL** para salvar os usuários
- **Telegram Bot API Token** (necessário se registrar no [BotFather](https://core.telegram.org/bots#botfather)).

### Instalação e execução
1. Clone o repositório
```bash
git clone https://github.com/alexreisc/Almossar.git
cd Almossar
```

2. Crie o banco de dados e a tabela de usuários
```sql
CREATE DATABASE cardapio_bot;
CREATE TABLE cardapio_bot.usuario (
    chatId BIGINT PRIMARY KEY
);
```

3. Na classe `Connection.java` edite *URL*, *USER* e *PASSWORD* para acessar seu banco.
4. Cire uma variavel de ambiente para guardar o *token* do seu bot
```console
SETX VARIABLE_NAME YOUR_BOT_TOKEN
```

5. Compile o código (isto vai gerar um arquivo `.jar`)
```console
mvn clean package
```

6. Execute o bot
```console
java -jar target/Almossar-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

## Estrutura do projeto
Optei por estruturar o projeto na forma "package by feature".

- `java/br/edu/ufca/chatbot_UFCA/`
  - `Main.java`: Classe que executa o código
  - `bot/`: Implementação do bot e seus comandos
  - `downloader/`: Baixar o PDF direto do site e excluir o antigo
  - `extractor/`: Extração do texto do PDF
  - `repository/`: Conexão com o banco de dados
  - `scheduler/`: Agendamento de tarefas
  - `utils/`: Verificar se PDF existe
- `resources/arquivos/`: Armazena o arquivo PDF baixado

## Contribuição
Sua contribuição é bem-vinda! Abra um pull request :) .

## Licença
Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.