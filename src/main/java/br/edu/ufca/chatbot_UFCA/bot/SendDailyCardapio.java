package br.edu.ufca.chatbot_UFCA.bot;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;
import br.edu.ufca.chatbot_UFCA.repository.Connection;
import br.edu.ufca.chatbot_UFCA.utils.CheckPdf;

public class SendDailyCardapio implements Job {

    private CardapioBot bot;

    public SendDailyCardapio() {
        this.bot = new CardapioBot(System.getenv("CARDAPIO_TOKEN_BOT"));
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Long> usuarios = Connection.obterUsuarios();
        
        String cardapio = Comandos.obterCardapio(); 
		if(CheckPdf.pdfExiste()) {
			for (Long chatId : usuarios) {
				SendMessage mensagem = SendMessage
						.builder()
						.chatId(chatId)
						.text(cardapio)
						.build();
				
				try {
					bot.getTelegramClient().execute(mensagem);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
}
