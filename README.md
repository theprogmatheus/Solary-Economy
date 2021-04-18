<img src="https://avatars.githubusercontent.com/u/30272840?s=96&v=4" alt="Sr_Edition" title="Sr_Edition" align="right" height="96" width="96"/>

# Solary-Economy

[![GitHub All Releases](https://img.shields.io/github/downloads/sredition/Solary-Economy/total.svg?logoColor=fff)](https://github.com/sredition/Solary-Economy/releases/latest)

Solary-Economy é um plugin leve e completo de economia criado para servidores Minecraft (Spigot) na versão 1.8.


![Preview](https://prnt.sc/11mbwob)
![Preview](https://prnt.sc/11mc0x3)
![Preview](https://prnt.sc/11mc3e1)

## Comandos

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

## Permissões  

  - `solaryeconomy.commands.money.other` - Permissão para usar o comando /money <player>
  - `solaryeconomy.commands.top` - Permissão para usar o comando /money top
  - `solaryeconomy.commands.criar` - Permissão para usar o comando /money create
  - `solaryeconomy.commands.deletar` - Permissão para usar o comando /money delete
  - `solaryeconomy.commands.add` - Permissão para usar o comando /money add
  - `solaryeconomy.commands.remove` - Permissão para usar o comando /money remove
  - `solaryeconomy.commands.set` - Permissão para usar o comando /money set
  - `solaryeconomy.commands.pay` - Permissão para usar o comando /money pay
  - `solaryeconomy.commands.toggle` - Permissão para usar o comando /money toggle
  - `solaryeconomy.commands.reload` - Permissão para usar o comando /money reload
  - `solaryeconomy.commands.magnata` - Permissão para usar o comando /money magnata
  

## config.yml

```yaml
#########################[ Solary-Economy ]#########################
##                                                                ##
##                   Encontrou algum bug? Reporte!                ##
##                                                                ##
##                  Email: editionsenpai@gmail.com                ##
##                     Discord: Sr_Edition#4765                   ##
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
```

## mensagens.yml

```yaml
#########################[ Solary-Economy ]#########################
##                                                                ##
##                   Encontrou algum bug? Reporte!                ##
##                                                                ##
##                  Email: editionsenpai@gmail.com                ##
##                     Discord: Sr_Edition#4765                   ##
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

## API
Conecte-se ao Solary-Economy usando essa API

```java

	public void yourPlugin()
	{
		// Pegue o magnata atual do servidor em objeto RankAccount
		RankAccount magnata = SolaryEconomy.getMagnata();

		// Pegue o ranking de jogadores mais ricos do servidor
		List<RankAccount> ranking = SolaryEconomy.getMoneyTop();
		
		// Pegue o gerenciador de economia
		Economia economia = SolaryEconomy.getInstance().economia;
	}
	
```