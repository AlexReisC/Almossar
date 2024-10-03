package br.edu.ufca.chatbot_UFCA.utils;

public class Comandos {
	
	public String exibirComandos(String comando) {
		switch (comando) {
		case "start":
			return "JÁ PODI ALMOSSAR?\n" + 
				"Olá! Eu sou o Al-mossar o bot do RU da UFCA! Aqui estão as opções do que posso fazer:\n" + 
				"🍽️ /cardapio - Ver o cardápio do dia\n" + 
				"⏰ /horarios - Ver os horários que o RU está funcionando\n" + 
				"ℹ️ /sobre - Informações sobre este projeto\n" +
				"❓ /ajuda - Listar os comandos disponíveis \n" +
				"📲 /contato - Entrar em contato com o criador";
		case "cardapio":
			return "";
		case "horarios":
			return "Horários de funcionamento do Restaurante Universitário da UFCA: \n" + 
					"☀️ Almoço: 11h - 14h (Juazeiro do Norte)\n" + 
					"🌑 Jantar: 17h - 19h (Juazeiro do Norte)";
		case "sobre":
			return "Este bot é um projeto feito (de forma totalmente independente) por um aluno da UFCA. \n" +
					"Sua função é baixar o pdf no site oficial da instituição e envia-lo, formatado em texto, para o usuário aqui no Telegram.\n" +
					"Quaisquer erros são ocasionados pela formatação da tabela do pdf original. \n\n" +
					"Aqui o código fonte do projeto: https://github.com/alexreisc/Almossar";
		case "ajuda":
			return "Estes são todos os comandos disponíveis:\n" +
					"▶️ /start - Iniciar o bot\n" +
					"🍽️ /cardapio - Ver o cardápio do dia\n" + 
					"⏰ /horarios - Ver os horários que o RU está funcionando\n" +
					"ℹ️ /sobre - Informações sobre este projeto\n" +
					"📲 /contato - Entrar em contato com o criador\n" +
					"❓ /ajuda - Listar os comandos disponíveis";
		case "contato":
			return "Este bot foi desenvolvido por Alex Reis. Se tiver dúvidas, sugestões ou quer reportar um problema use:\n" +
					"📷 Instagram: @c_alexreis \n" +
					"📧 Email: alex.reis@aluno.ufca.edu.br \n" + 
					"🌐 Linkedin: https://linkedin.com/in/alex-reis-cavalcante \n";
		default:
			return "Não entendi, este comando. Digite '/ajuda' para listar os comandos disponíveis.";
		}
	}
}
