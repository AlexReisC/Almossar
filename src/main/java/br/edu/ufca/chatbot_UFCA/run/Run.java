package br.edu.ufca.chatbot_UFCA.run;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import br.edu.ufca.chatbot_UFCA.bot.CardapioBot;
import br.edu.ufca.chatbot_UFCA.utils.JobScheduler;

public class Run {
	private static final Logger logger = LogManager.getLogger(Run.class);
	public static void run() {
		String botToken = System.getenv("CARDAPIO_TOKEN_BOT");
		TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
		
		JobScheduler jobScheduler = new JobScheduler();
		try {
			jobScheduler.deletarPdf();
			jobScheduler.agendarDownload();

			botsApplication.registerBot(botToken, new CardapioBot(botToken));
			jobScheduler.agendarEnvioDiario();
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
