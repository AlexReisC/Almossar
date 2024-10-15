package br.edu.ufca.chatbot_UFCA;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import br.edu.ufca.chatbot_UFCA.bot.CardapioBot;
import br.edu.ufca.chatbot_UFCA.bot.SendDailyCardapio;
import br.edu.ufca.chatbot_UFCA.scheduler.JobScheduler;

public class Main 
{
	private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main( String[] args ) {
    	String botToken = System.getenv("CARDAPIO_TOKEN_BOT");
		TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
		
		JobScheduler jobScheduler = new JobScheduler();
		try {
			CardapioBot meuBot = new CardapioBot(botToken);
			SendDailyCardapio send = new SendDailyCardapio(meuBot);
			botsApplication.registerBot(botToken, meuBot);
			jobScheduler.agendarTarefas();
			Thread.currentThread().join();
		} catch (SchedulerException e) {
			logger.info("Erro ao agendar jobs na classe JobScheduler. {}", e);
		} catch (TelegramApiException e2) {
			logger.info("Erro ao registrar bot {}", e2);
		} catch (InterruptedException e3) {
			logger.info("Thread atual interrompida {}", e3);
		}
    }
}
