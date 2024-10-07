package br.edu.ufca.chatbot_UFCA.bot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CardapioBot implements LongPollingSingleThreadUpdateConsumer {
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
			
			try {
				telegramClient.execute(mensagem);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
	    }
	}
	
	
}

