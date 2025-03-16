<img src="https://avatars.githubusercontent.com/u/30272840?s=96&v=4" alt="Sr_Edition" title="Sr_Edition" align="right" height="96" width="96"/>

# 🎮 Solary-Economy 💸
## O Sistema de Economia Definitivo para Seu Servidor Minecraft

[![GitHub All Releases](https://img.shields.io/github/downloads/sredition/Solary-Economy/total.svg?logoColor=fff)](https://github.com/sredition/Solary-Economy/releases/latest)

---

O **Solary-Economy** é um plugin de economia robusto, leve e altamente personalizável, desenvolvido especialmente para servidores Minecraft na versão **1.8**. Ele foi projetado para oferecer uma experiência completa de gerenciamento de economia, desde operações básicas como saldo e transferências até funcionalidades avançadas como rankings, integração com outros plugins e suporte a múltiplos bancos de dados. Se você busca um sistema de economia confiável, eficiente e fácil de usar, o **Solary-Economy** é a escolha ideal.

### Recursos Principais

**1. Gerenciamento Completo de Economia**:
   - Crie, delete e gerencie contas de jogadores diretamente pelo console ou comandos in-game.
   - Defina, adicione ou remova saldos de jogadores com comandos simples e intuitivos.
   - Transfira dinheiro entre jogadores de forma segura e rápida.

**2. Ranking de Jogadores Mais Ricos**:
   - Exiba um ranking dos jogadores mais ricos do servidor com o comando `/money top`.
   - Personalize o número de jogadores exibidos e o intervalo de atualização do ranking.
   - Destaque o **magnata** do servidor (o jogador mais rico) com uma tag personalizada no chat.

**3. Integração com Vault**:
   - Compatível com a API **Vault**, permitindo que outros plugins interajam diretamente com o **Solary-Economy**.
   - Oferece suporte a placeholders para exibir saldos e outras informações em plugins de chat ou scoreboards.

**4. Abreviações de Valores**:
   - Simplifique a exibição de valores grandes (milhares, milhões, bilhões) com abreviações personalizáveis (ex: 1k, 1M, 1B).
   - Configure o número de casas decimais e os divisores para cada abreviação.

**5. Suporte a Múltiplos Bancos de Dados**:
   - Escolha entre **SQLite** (padrão) ou **MySQL** para armazenar os dados de economia.
   - Configure facilmente o banco de dados diretamente no arquivo `config.yml`.

**6. Personalização Total**:
   - Edite todas as mensagens do plugin no arquivo `mensagens.yml` para adaptar o plugin ao estilo do seu servidor.
   - Defina o nome da moeda (singular e plural) para criar uma economia única.

**7. Comandos e Permissões**:
   - Comandos intuitivos como `/money`, `/money pay`, `/money top`, `/money magnata` e muito mais.
   - Sistema de permissões detalhado para controlar o acesso a cada funcionalidade.

**8. API para Desenvolvedores**:
   - Integre o **Solary-Economy** em seus próprios plugins com uma API simples e poderosa.
   - Acesse dados como o magnata atual, o ranking de jogadores mais ricos e o saldo de qualquer jogador.

**9. Leve e Eficiente**:
   - Desenvolvido para ser leve e de alto desempenho, sem sobrecarregar o servidor.
   - Ideal para servidores pequenos, médios e grandes.

### Vantagens de Usar o Solary-Economy

- **Facilidade de Uso**: Comandos simples e configuração intuitiva permitem que até mesmo administradores iniciantes configurem o plugin rapidamente.
- **Personalização**: Adapte o plugin ao tema do seu servidor com nomes de moedas personalizados, mensagens editáveis e muito mais.
- **Compatibilidade**: Funciona perfeitamente com outros plugins populares como **Vault**, **PlaceholderAPI** e **LegendChat**.
- **Confiabilidade**: Um sistema de economia estável e testado, garantindo que os dados dos jogadores estejam sempre seguros.
- **Suporte a Desenvolvedores**: Uma API simples que permitam que desenvolvedores criem integrações personalizadas.

### Por Que Escolher o Solary-Economy?

Se você quer um sistema de economia que combine **facilidade de uso**, **personalização** e **desempenho**, o **Solary-Economy** é a escolha certa. Ele foi projetado para atender às necessidades de servidores de todos os tamanhos, desde pequenas comunidades até grandes redes. Com recursos avançados, integração com outros plugins e suporte a múltiplos bancos de dados, o **Solary-Economy** oferece tudo o que você precisa para gerenciar a economia do seu servidor de forma eficiente e profissional.

Experimente o **Solary-Economy** hoje mesmo e leve a economia do seu servidor para o próximo nível!

---

## ⚙️ Comandos

  - `/money` - Comando para ver seu saldo atual.
  - `/money <player>` - Comando para ver o saldo atual de um jogador.
  - `/money create <name> <amount>` - Comando para criar uma conta.
  - `/money delete <name>` - Comando para deletar uma conta.
  - `/money pay <player> <amount>` - Comando para enviar saldo para um jogador.
  - `/money set <player> <amount>` - Comando para definir o saldo de um jogador.
  - `/money add <player> <amount>` - Comando para adicionar saldo de um jogador.
  - `/money remove <player> <amount>` - Comando para remover saldo de um jogador.
  - `/money toggle` - Comando para habilitar/desabilitar o recebimento de saldo.
  - `/money top` - Comando para visualizar o ranking de jogadores mais ricos.
  - `/money magnata` - Comando para ver o atual magnata do servidor.
  - `/money reload` - Comando para recarregar os arquivos de configuração e mensagens.
  - `/money help` - Comando para ver os comandos disponíveis.

## 🔒 Permissões  

  - `solaryeconomy.command.balance` - Permissão para usar o comando /money
  - `solaryeconomy.command.balance.other` - Permissão para usar o comando /money <player>
  - `solaryeconomy.command.rank` - Permissão para usar o comando /money top
  - `solaryeconomy.commands.criar` - Permissão para usar o comando /money create
  - `solaryeconomy.commands.deletar` - Permissão para usar o comando /money delete
  - `solaryeconomy.command.add` - Permissão para usar o comando /money add
  - `solaryeconomy.command.remove` - Permissão para usar o comando /money remove
  - `solaryeconomy.command.set` - Permissão para usar o comando /money set
  - `solaryeconomy.command.pay` - Permissão para usar o comando /money pay
  - `solaryeconomy.commands.toggle` - Permissão para usar o comando /money toggle
  - `solaryeconomy.commands.reload` - Permissão para usar o comando /money reload
  - `solaryeconomy.commands.magnata` - Permissão para usar o comando /money magnata
  

## 📜 config.yml

```yaml
#########################[ Solary-Economy ]#########################
##                                                                ##
##                 Encontrou algum bug? Reporte!                  ##
##                                                                ##
##                Email: theprog.matheus@gmail.com                ##
##                     Discord: sr_edition                        ##
##                                                                ##
####################################################################



#/------------------------------------------------------------------/
#     Autor: ${project.author}
#     Versão: ${project.version}
#/------------------------------------------------------------------/




#Configuração do banco de dados

mysql:
  #coloque em true para usar o MySQL, caso esteja desativado será usado SQLite no arquivo "storage.db"
  enable: false
  #coloque o endereço do seu banco de dados ex: "localhost".
  #se seu MySQL estiver rodando em uma porta diferente, especifique a porta. ex: "localhost:2789"
  hostname: "localhost"
  #coloque o nome do seu banco de dados ex: "minecraft"
  database: "minecraft"
  #coloque o nome de usuário do seu banco de dados ex: "root"
  username: "root"
  #coloque a senha do seu usuário do seu banco de dados ex: "pass123"
  password: ""
  #coloque o nome da tabela que vai ser usada pelo plugin
  table: "solaryeconomy"
  
  
#Configurações gerais do plugin

#coloque aqui o mundo principal do seu servidor
world: "world"

#coloque true para usar a API Vault, (é recomendado usar, para que os demais plugins tenha acesso ao Solary-Economy)
use_vault: true

#coloque aqui o money que o jogador vai começar quando logar no servidor pela primeira vez
start_value: 0
  
#configurações gerais do money top
economy_top:

  #coloque aqui a quantidade de jogadores que vai aparecer no money top
  size: 10
  
  #coloque aqui o tempo para ficar atualizando o money top (em segundos) ex: (300 segundos = 5 minutos)
  refresh_time: 300
  
  #coloque em true para usar prefix's no money top (precisa do Vault)
  prefix: true
  
  #coloque aqui o tamanho máximo do nome da conta que vai aparecer no money top Dica: (use para remover as factions no money top :D)
  #se o nome da conta for maior que esse valor, ela não aparecerá no money top e nem será dada a tag magnata
  name_size: 16

#configurações gerais da economia do servidor
currency_name:

  #coloque aqui o nome da economia em modo plural ex: coins
  plural: coins
  
  #coloque aqui o nome da economia em modo singular ex: coin
  singular: coin
  
#coloque true para usar a tag magnata no moneytop/chat (precisa do legendchat para mostrar no chat)
# use a tag {solary_economy_magnata} no legendchat
magnata_tag: true

#coloque true se você quer que aparece uma mensagem a todos os jogadores quando o magnata mudar
magnata_broadcast: true

#aqui você configura as abreviações dos valores (útil em casos de economia OP)
abbreviations:

  #aqui você ativa ou desativa o recurso de abreviação
  enable:
    #coloque true se você quer que o saldo seja abreviado nas mensagens
    messages: true
    #coloque true se você quer que o saldo seja abreviado nos comandos
    commands: true
    
  #define quantas casas decimais serão exibidas nas mensagens (ex: 1.5k ao invés de 1k)
  decimal_places: 1
  
  #Aqui você cria o dicionário das abreviações, você pode adicionar quantas abreviações quiser.
  dictionary:

    #aqui você define a abreviação usada em comandos. Ex: definido 'k' então: /money pay fulano 32k = /money pay fulano 32000
    #Mil
    k:
      #aqui você define como a abreviação será exibida nas mensagens. Ex: "Você acaba de receber 32k de fulano"
      display: "k"
      #aqui você define o divisor para essa abreviação. Ex: cada k vale 1000 coins. (32000coins / 1000)= 32k coins e (32k coins * 1000) = 32000 coins
      divider: 1000    
      
    #Milhão
    m:
      display: "m"
      divider: 1000000
      
    #Bilhão
    b:
      display: "b"
      divider: 1000000000
      
    #Trilhão
    t:
      display: "t"
      divider: 1000000000000

#Permissões:
#
###################[Comandos]#######################
#
#/money - sem permissão :D
#/money ajuda - sem permissão :D
#/money [jogador] - solaryeconomy.commands.money.other
#/money top - solaryeconomy.commands.top
#/money criar - solaryeconomy.commands.criar
#/money deletar - solaryeconomy.commands.deletar
#/money add - solaryeconomy.commands.add
#/money remove - solaryeconomy.commands.remove
#/money set - solaryeconomy.commands.set
#/money pay - solaryeconomy.commands.pay
#/money toggle - solaryeconomy.commands.toggle
#/money reload - solaryeconomy.commands.reload
#/money magnata - solaryeconomy.commands.magnata
#
###################[Comandos]#######################  

###################[Placeholders]#######################
#
# %solaryeconomy_balance% - Saldo atual do jogador.
# %solaryeconomy_balance_formatted% - Saldo atual do jogador formatado.
#
###################[Placeholders]#######################

```

## 📜 mensagens.yml

```yaml
#########################[ Solary-Economy ]#########################
##                                                                ##
##                 Encontrou algum bug? Reporte!                  ##
##                                                                ##
##                Email: theprog.matheus@gmail.com                ##
##                     Discord: sr_edition                        ##
##                                                                ##
####################################################################



#/------------------------------------------------------------------/
#     Autor: ${project.author}
#     Versão: ${project.version}
#/------------------------------------------------------------------/




#configurações de todas as mensagens do Solary-Economy

NO_PERMISSION: "&cVocê não tem permissão para isso."
PLAYER_NOTFOUND: "&cJogador não encontrado em nosso banco de dados."
MONEY: "&aMoney: {valor}"
NO_MONEY: "&cVocê não tem money suficiente para isso."
MONEY_TOGGLE: "&aRecebimento de coins: {toggle}"
MONEY_TOGGLED: "&cEste jogador está com o recebimento de coins desativado."
MONEY_OTHER: "&aMoney de {player}: {valor}"
MONEY_TOP_NULL: "&cNão existe jogadores cadastrados ainda."
MONEY_TOP_TITLE: "&2Top 10 Mais Ricos &7(Atualizado de 5 em 5 minutos)"
MONEY_TOP_FORMAT: "&a{i}. &2{player}: &7({valor})"
NUMBER_NULL: "&cValor incorreto, por favor insira um valor válido."
ACCOUNT_EXISTS: "&cJá existe uma conta com o nome '{nome}'!"
ACCOUNT_CREATE: "&aConta '{nome}' criada com sucesso!"
ACCOUNT_DELETE: "&aConta '{nome}' deletada com sucesso!"
ACCOUNT_NOFOUND: "&cConta '{nome}' não encontrada em nosso banco de dados."
MONEY_SET: "&aFoi setado a quantia de {valor} na conta de {player} "
MONEY_ADD: "&aFoi adicionado a conta de {player} a quantia de {valor}"
MONEY_REMOVE: "&aFoi removido a quantia de {valor} da conta de {player} "
MONEY_PAY_SENDER: "&aVocê enviou {valor} para {player}"
MONEY_PAY_RECEIVER: "&aVocê acaba de receber {valor} de {player}"
MONEY_PAY_ERRO: "&cVocê não pode enviar coins a si mesmo."
MAGNATA_TAG: "&2[$] "
MAGNATA_VIEW: "&2[$] &aAtual magnata do servidor: &7{player} &7com a quantia de {valor}"
MAGNATA_NEW: "&2[$] &aNovo magnata: &7{player} &7com a quantia de {valor}"
MAGNATA_NOT_FOUND: "&2[$] &cNenhum magnata ainda."
```

## 💻 Como Adicionar Solary-Economy ao Seu Projeto Maven

Se você deseja utilizar o Solary-Economy como uma dependência em seu projeto Maven, siga os passos abaixo:

### 1. Adicionar o Repositório Maven

Adicione a seguinte configuração dentro da tag `<repositories>` no seu `pom.xml`:

```xml
<repositories>
    <repository>
        <id>github-theprogmatheus-maven-repository</id>
        <url>https://raw.githubusercontent.com/theprogmatheus/maven-repository/master/</url>
    </repository>
</repositories>
```

### 2. Adicionar a Dependência

Em seguida, adicione a dependência do Solary-Economy na seção `<dependencies>` do seu `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.redeskyller.bukkit.solaryeconomy</groupId>
        <artifactId>Solary-Economy</artifactId>
        <version>1.5.3</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### 3. Utilizando a API do Solary-Economy
Agora você pode utilizar a API do Solary-Economy em seu projeto. Aqui está um exemplo básico de como acessar o magnata e o ranking de jogadores mais ricos:

```java
import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
import com.redeskyller.bukkit.solaryeconomy.manager.Economia;
import com.redeskyller.bukkit.solaryeconomy.objects.RankAccount;

import java.util.List;

public class YourPlugin {

    public void yourMethod() {
        // Pegue o magnata atual do servidor
        RankAccount magnata = SolaryEconomy.getMagnata();
        System.out.println("Magnata atual: " + magnata.getName() + " com " + magnata.getBalance());

        // Pegue o ranking de jogadores mais ricos
        List<RankAccount> ranking = SolaryEconomy.getMoneyTop();
        for (RankAccount account : ranking) {
            System.out.println(account.getName() + ": " + account.getBalance());
        }

        // Acesse o gerenciador de economia
        Economia economia = SolaryEconomy.economia;
        // Exemplo: Verificar saldo de um jogador
        BigDecimal saldo = economia.getBalance("NomeDoJogador");
        System.out.println("Saldo do jogador: " + saldo);
    }
}
```

###4. Pronto!
Agora você pode utilizar todas as funcionalidades do Solary-Economy em seu projeto.

---

## 💙 Sobre o Projeto

O **Solary-Economy** é um projeto **open source** desenvolvido com muito carinho e dedicação para a comunidade Minecraft. Ao longo de mais de **8 anos de existência**, ele tem sido aprimorado graças ao apoio e feedback de inúmeros servidores e jogadores. 

### 🤝 Colaboração é Bem-Vinda!
Este projeto é mantido pela comunidade e para a comunidade. Se você deseja contribuir, sinta-se à vontade para:
- Enviar **pull requests** com melhorias ou correções.
- Reportar **issues** para ajudar a identificar problemas ou sugerir novas funcionalidades.
- Compartilhar suas ideias e feedback para tornar o **Solary-Economy** ainda melhor.

Toda contribuição, grande ou pequena, é extremamente valiosa e ajuda a manter o projeto vivo e em constante evolução.

### 🙏 Agradecimentos Especiais
Gostaríamos de agradecer a todos que utilizam e apoiam o **Solary-Economy** há tantos anos. Vocês são a razão pela qual este projeto continua crescendo e se tornando cada vez mais robusto. Obrigado por fazer parte dessa jornada!

Vamos juntos continuar construindo um sistema de economia incrível para servidores Minecraft! 💰
