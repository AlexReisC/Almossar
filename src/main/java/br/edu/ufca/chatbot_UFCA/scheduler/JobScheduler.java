package br.edu.ufca.chatbot_UFCA.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TimeOfDay;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.edu.ufca.chatbot_UFCA.bot.SendDailyCardapio;
import br.edu.ufca.chatbot_UFCA.downloader.PdfDeleter;
import br.edu.ufca.chatbot_UFCA.downloader.PdfDownloader;

public class JobScheduler {

	public void agendarTarefas() throws SchedulerException, InterruptedException {
		Scheduler scheduler;
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		
		JobDetail jobDetail = JobBuilder.newJob(PdfDownloader.class).withIdentity("pdfDownloadJob", "grupo").build();

		TimeOfDay inicio = TimeOfDay.hourAndMinuteOfDay(8, 0);
		TimeOfDay fim = TimeOfDay.hourAndMinuteOfDay(11, 00);

		DailyTimeIntervalScheduleBuilder dailySchedule = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
				.startingDailyAt(inicio).endingDailyAt(fim).withIntervalInMinutes(15)
				.onDaysOfTheWeek(DateBuilder.MONDAY, DateBuilder.TUESDAY);

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("pdfDownloadTrigger", "grupo")
				.withSchedule(dailySchedule).build();

		scheduler.scheduleJob(jobDetail, trigger);

		JobDetail deletionJobDetail = JobBuilder.newJob(PdfDeleter.class).withIdentity("pdfDeletionJob", "grupo").build();

		Trigger deletionTrigger = TriggerBuilder.newTrigger().withIdentity("pdfDeletionTrigger", "grupo")

				.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.SATURDAY, 0, 0)).build();
		scheduler.scheduleJob(deletionJobDetail, deletionTrigger);

        jobDetail = JobBuilder.newJob(SendDailyCardapio.class)
                                        .withIdentity("sendDailyMenuJob", "grupo")
                                        .build();
        
        CronTrigger trigger8h = TriggerBuilder.newTrigger()
            .withIdentity("trigger8h", "group1")
            .withSchedule(CronScheduleBuilder.cronSchedule("0 1 8 ? * MON-FRI"))
            .build();
        
        CronTrigger trigger14h = TriggerBuilder.newTrigger()
            .withIdentity("trigger14h", "group1")
            .withSchedule(CronScheduleBuilder.cronSchedule("0 0 14 ? * MON-FRI"))
            .build();

		scheduler.scheduleJob(jobDetail, trigger8h);
		scheduler.scheduleJob(jobDetail, trigger14h);
		
		scheduler.start();
		Thread.sleep(300L * 1000L);
		scheduler.shutdown();
    }
}
