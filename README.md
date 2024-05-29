# Pong Distribuído

Este é um jogo Pong distribuído desenvolvido para a matéria de Sistemas Distribuídos. O jogo permite que dois jogadores se conectem a partir de computadores diferentes na mesma rede e joguem Pong, cada um controlando seu próprio paddle usando as setas do teclado.

## Arquivos do Projeto

- **PongServer.java**: Classe principal do servidor que gerencia as conexões dos clientes, a lógica do jogo e a comunicação entre os jogadores.
- **PongServerThread.java**: Classe responsável por gerenciar a comunicação com cada cliente conectado.
- **PongServerForm.java**: Interface gráfica do servidor que permite iniciar e parar o servidor e exibe o estado atual do jogo.
- **PongClient.java**: Classe principal do cliente que permite que um jogador se conecte ao servidor e controle um paddle no jogo.

## Funcionalidades

- Controle distribuído dos paddles por dois jogadores.
- Detecção de colisões com as bordas do campo e com os paddles dos jogadores.
- Sistema de pontuação para os jogadores.
- Interface gráfica para o servidor e para os clientes.

## Requisitos

- Java Development Kit (JDK) 8 ou superior.
- Dois computadores conectados à mesma rede.

## Como Executar

### Servidor

1. Compile os arquivos Java:

   ```bash
   javac com/mycompany/pong/PongServer.java com/mycompany/pong/PongServerThread.java com/mycompany/pong/PongServerForm.java
   ```

2. Execute a classe `PongServer`:

   ```bash
   java com.mycompany.pong.PongServer
   ```

3. Insira a porta desejada na interface gráfica e clique em "Start Server".

### Cliente

1. Compile o arquivo Java:

   ```bash
   javac com/mycompany/pong/PongClient.java
   ```

2. Execute a classe `PongClient` para cada jogador:

   ```bash
   java com.mycompany.pong.PongClient
   ```

3. Insira o endereço IP do servidor, a porta e o identificador do jogador (`player1` ou `player2`) quando solicitado.

## Controles

- **Player 1**: Controla o paddle esquerdo usando as setas para cima e para baixo.
- **Player 2**: Controla o paddle direito usando as setas para cima e para baixo.

## Exemplo de Execução

1. Inicie o servidor no computador que atuará como servidor.
2. Conecte o primeiro cliente, definindo-o como `player1`.
3. Conecte o segundo cliente, definindo-o como `player2`.
4. O jogo começará automaticamente quando ambos os jogadores estiverem conectados.

## Problemas Conhecidos

- Nenhum problema conhecido no momento.

## Contribuição

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhorias, sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto está licenciado sob a MIT License. Veja o arquivo LICENSE para mais detalhes.

---

Desenvolvido para a matéria de Sistemas Distribuídos.
