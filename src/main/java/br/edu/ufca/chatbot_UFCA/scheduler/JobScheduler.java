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
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();		
		
		// Job e Trigger de download 
		JobDetail job = JobBuilder.newJob(PdfDownloader.class).withIdentity("pdfDownloadJob", "grupo").build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("pdfDownloadTrigger", "grupo")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0/15 8-11 ? * MON-TUE")).build();

		scheduler.scheduleJob(job, trigger);
		
		// Job e Trigger de delecao 
		job = JobBuilder.newJob(PdfDeleter.class).withIdentity("pdfDeletionJob", "grupo").build();

		trigger = TriggerBuilder.newTrigger().withIdentity("pdfDeletionTrigger", "grupo")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 ? * SAT")).build();

		scheduler.scheduleJob(job, trigger);

		// Job e Trigger de envio 
        job = JobBuilder.newJob(SendDailyCardapio.class)
                                        .withIdentity("sendDailyJob1", "grupo")
                                        .build();
        
        trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("dailyMenuTrigger", "grupo")
                                        .withSchedule(CronScheduleBuilder.cronSchedule("0 0 9 ? * MON-FRI"))
                                        .build();

        scheduler.scheduleJob(job, trigger);
        
        job = JobBuilder.newJob(SendDailyCardapio.class)
                .withIdentity("sendDailyJob2", "grupo")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("dailyMenuTrigger2", "grupo")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 14 ? * MON-FRI"))
                .build();

        scheduler.scheduleJob(job, trigger);
        
        // agendar e iniciar
		scheduler.start();
		Thread.sleep(300L * 1000L);
		scheduler.shutdown(true);
    }
}
