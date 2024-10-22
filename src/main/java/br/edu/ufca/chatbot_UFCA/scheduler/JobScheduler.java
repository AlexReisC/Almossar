package br.edu.ufca.chatbot_UFCA.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.edu.ufca.chatbot_UFCA.bot.SendDailyCardapio;
import br.edu.ufca.chatbot_UFCA.downloader.PdfDeleter;
import br.edu.ufca.chatbot_UFCA.downloader.PdfDownloader;

public class JobScheduler {

	public void agendarTarefas() throws SchedulerException, InterruptedException {
		// Inicializa o scheduler como uma variável de classe
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        
        // Job e Trigger de download 
        JobDetail downloadJob = JobBuilder.newJob(PdfDownloader.class)
                .withIdentity("pdfDownloadJob", "grupo")
                .build();
        Trigger downloadTrigger = TriggerBuilder.newTrigger()
                .withIdentity("pdfDownloadTrigger", "grupo")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/15 8-11 ? * MON-TUE"))
                .build();
        scheduler.scheduleJob(downloadJob, downloadTrigger);
        
        // Job e Trigger de delecao 
        JobDetail deleteJob = JobBuilder.newJob(PdfDeleter.class)
                .withIdentity("pdfDeletionJob", "grupo")
                .build();
        Trigger deleteTrigger = TriggerBuilder.newTrigger()
                .withIdentity("pdfDeletionTrigger", "grupo")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 ? * SAT"))
                .build();
        scheduler.scheduleJob(deleteJob, deleteTrigger);

        // Jobs e Triggers de envio
        JobDetail sendJob1 = JobBuilder.newJob(SendDailyCardapio.class)
                .withIdentity("sendDailyJob1", "grupo")
                .build();
        Trigger sendTrigger1 = TriggerBuilder.newTrigger()
                .withIdentity("dailyMenuTrigger", "grupo")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 9 ? * MON-FRI"))
                .build();
        scheduler.scheduleJob(sendJob1, sendTrigger1);
        
        JobDetail sendJob2 = JobBuilder.newJob(SendDailyCardapio.class)
                .withIdentity("sendDailyJob2", "grupo")
                .build();
        Trigger sendTrigger2 = TriggerBuilder.newTrigger()
                .withIdentity("dailyMenuTrigger2", "grupo")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 14 ? * MON-FRI"))
                .build();
        scheduler.scheduleJob(sendJob2, sendTrigger2);
        
        // Apenas inicia o scheduler
        scheduler.start();
    }
}
