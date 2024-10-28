package br.edu.ufca.chatbot_UFCA.bot;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import br.edu.ufca.chatbot_UFCA.repository.Connection;
import br.edu.ufca.chatbot_UFCA.utils.CheckPdf;


public class CardapioBot implements LongPollingSingleThreadUpdateConsumer {
	private static final Logger logger = LogManager.getLogger(CardapioBot.class);
	private TelegramClient telegramClient;
	
	public CardapioBot(String botToken) {
		telegramClient = new OkHttpTelegramClient(botToken);
	}
	
	@Override
	public void consume(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String textoMensagem = update.getMessage().getText();			
			String comandoRecebido = Comandos.exibirComandos(textoMensagem);
			
			Long chatId = update.getMessage().getChatId();
			SendMessage mensagem = SendMessage
					.builder()
					.chatId(chatId)
					.text(comandoRecebido)
					.build();
			
			Connection.salvarUsuario(chatId);
			
			try {
				telegramClient.execute(mensagem);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
	    }
	}

	public TelegramClient getTelegramClient() {
		return telegramClient;
	}
	
	public void enviarAutomaticamente() {
		List<Long> usuarios = Connection.obterUsuarios();
        
        if(!CheckPdf.pdfExiste()) {
        	logger.info("Envio programado nao feito, pois PDF nao existe!");
        	return;
        }
        
        String cardapio = Comandos.obterCardapio(); 
		for (Long chatId : usuarios) {
			SendMessage mensagem = SendMessage
					.builder()
					.chatId(chatId)
					.text(cardapio)
					.build();
			
			try {
				telegramClient.execute(mensagem);
			} catch (TelegramApiException e) {
				logger.error("Erro no envio automatico, exception na TelegramApi: {}", e.getMessage(), e);
			}
		}
	}
}

