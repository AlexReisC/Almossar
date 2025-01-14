package br.edu.ufca.chatbot_UFCA.bot;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendDailyCardapio implements Job {
	private static CardapioBot bot;
	    
    public static void setBot(CardapioBot cardapioBot) {
        bot = cardapioBot;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	if (bot == null) {
            throw new JobExecutionException("Bot não inicializado!");
        }
        
        try {
            bot.enviarAutomaticamente();
            
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
    
}
