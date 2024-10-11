package br.edu.ufca.chatbot_UFCA.bot;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import br.edu.ufca.chatbot_UFCA.downloader.PdfDownloader;
import br.edu.ufca.chatbot_UFCA.extractor.PdfExtractor;

public class Comandos {
	
	protected static String exibirComandos(String comando) {
		switch (comando) {
		case "/start":
			return "JÁ PODI ALMOSSAR?\n" + 
				"Olá! Eu sou o Al-mossar o bot do RU da UFCA! Aqui estão as opções do que posso fazer:\n" + 
				"🍽️ /cardapio - Ver o cardápio do dia\n" + 
				"⏰ /horarios - Ver os horários que o RU está funcionando\n" + 
				"ℹ️ /sobre - Informações sobre este projeto\n" +
				"❓ /ajuda - Listar os comandos disponíveis \n" +
				"📲 /contato - Entrar em contato com o criador";
		case "/cardapio":
			return obterCardapio(); 
		case "/horarios":
			return "Horários de funcionamento do Restaurante Universitário da UFCA: \n" + 
					"☀️ Almoço: 11h - 14h (Juazeiro do Norte)\n" + 
					"🌑 Jantar: 17h - 19h (Juazeiro do Norte)";
		case "/sobre":
			return "Este bot é um projeto feito (de forma totalmente independente) por um aluno da UFCA. \n" +
					"Sua função é baixar o pdf no site oficial da instituição e envia-lo, formatado em texto, para o usuário aqui no Telegram.\n" +
					"Quaisquer erros são ocasionados pela formatação da tabela do pdf original. \n\n" +
					"Aqui o código fonte do projeto: https://github.com/alexreisc/Almossar";
		case "/ajuda":
			return "Estes são todos os comandos disponíveis:\n" +
					"▶️ /start - Iniciar o bot\n" +
					"🍽️ /cardapio - Ver o cardápio do dia\n" + 
					"⏰ /horarios - Ver os horários que o RU está funcionando\n" +
					"ℹ️ /sobre - Informações sobre este projeto\n" +
					"📲 /contato - Entrar em contato com o criador\n" +
					"❓ /ajuda - Listar os comandos disponíveis";
		case "/contato":
			return "Este bot foi desenvolvido por Alex Reis. Se tiver dúvidas, sugestões de melhorias ou quer reportar um problema:\n" +
					"📷 Instagram: @c_alexreis \n" +
					"📧 Email: alex.reis@aluno.ufca.edu.br \n" + 
					"🌐 Linkedin: https://linkedin.com/in/alex-reis-cavalcante \n";
		default:
			return "Não entendi, este comando. Digite '/ajuda' para listar os comandos disponíveis.";
		}
	}
	
	public static String obterCardapio(){
		LocalDate hoje = LocalDate.now();
		int dia = hoje.getDayOfWeek().getValue();
		dia = 5; 
		if(dia > 5) {
			return "Sem cardápio para hoje.";
		}
		
		int diaDoMes = hoje.getDayOfMonth();
		hoje.getDayOfWeek().toString();
		int mes = hoje.getMonthValue();
		int ano = hoje.getYear();
		
		try {
			PdfExtractor.extrairTextoDia(PdfDownloader.NOME_ARQUIVO, dia);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String cabecalho = "Cardápio de " + diaDoMes + "/" + mes + "/" + ano;
		
		StringBuilder refeicao = new StringBuilder();		
		
		boolean isAlmoco = LocalTime.now().getHour() < 14 ? true : false;
		if(!isAlmoco) {
			for(StringBuilder stringBuilder : PdfExtractor.jantarDoDia) {
				refeicao.append(stringBuilder.toString());
			}
			return cabecalho + "\nJantar\n" + refeicao.toString() + 
					"\n\n* Contém leite/lactose/glúten\n" + 
					"OBS: O cardápio pode sofrer alterações.\n" +
					"(O CARDAPIO DO JANTAR É DISPONIBILIZADO AQUI APÓS ÀS 14:00 HORAS)";
		}
		for(StringBuilder stringBuilder : PdfExtractor.almocoDoDia) {
			refeicao.append(stringBuilder.toString());
		}
		return cabecalho + "\nAlmoço\n" + refeicao.toString() + 
				"\n\n* Contém leite/lactose/glúten\n" + 
				"OBS: O cardápio pode sofrer alterações.\n" +
				"(O CARDAPIO DO JANTAR É DISPONIBILIZADO AQUI APÓS ÀS 14:00 HORAS)";		
	}
}
