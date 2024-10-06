package br.edu.ufca.chatbot_UFCA;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ufca.chatbot_UFCA.bot.Comandos;
import br.edu.ufca.chatbot_UFCA.utils.JobScheduler;

public class Main 
{
	private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main( String[] args )
    {
//    	JobScheduler jobScheduler = new JobScheduler();
//    	jobScheduler.agendarDownload();
    	Comandos cmd = new Comandos();
    	System.out.println(cmd.exibirComandos("cardapio"));
    }
}
