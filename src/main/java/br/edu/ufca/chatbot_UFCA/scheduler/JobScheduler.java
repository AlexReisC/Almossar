package br.edu.ufca.chatbot_UFCA.scheduler;

import org.quartz.CronScheduleBuilder;
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
		JobDetail jobDetail = JobBuilder.newJob(PdfDownloader.class).withIdentity("pdfDownloadJob", "grupo").build();

		TimeOfDay inicio = TimeOfDay.hourAndMinuteOfDay(8, 0);
		TimeOfDay fim = TimeOfDay.hourAndMinuteOfDay(22, 00);

		DailyTimeIntervalScheduleBuilder dailySchedule = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
				.startingDailyAt(inicio).endingDailyAt(fim).withIntervalInMinutes(15)
				.onDaysOfTheWeek(DateBuilder.MONDAY, DateBuilder.TUESDAY);

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("pdfDownloadTrigger", "grupo")
				.withSchedule(dailySchedule).build();

		Scheduler scheduler;
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.scheduleJob(jobDetail, trigger);

		JobDetail deletionJobDetail = JobBuilder.newJob(PdfDeleter.class).withIdentity("pdfDeletionJob", "grupo").build();

		Trigger deletionTrigger = TriggerBuilder.newTrigger().withIdentity("pdfDeletionTrigger", "grupo")
				.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.SATURDAY, 0, 0)).build();

		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.scheduleJob(deletionJobDetail, deletionTrigger);

        jobDetail = JobBuilder.newJob(SendDailyCardapio.class)
                                        .withIdentity("sendDailyMenuJob", "grupo")
                                        .build();
        
        TimeOfDay inicio2 = TimeOfDay.hourAndMinuteOfDay(8, 1);
        TimeOfDay fim2 = TimeOfDay.hourAndMinuteOfDay(14, 2);
        DailyTimeIntervalScheduleBuilder dailyTime = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
        								.startingDailyAt(inicio2)
        								.endingDailyAt(fim2)
        								.withIntervalInHours(6)
        								.onEveryDay();
        
        trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("dailyMenuTrigger", "grupo")
                                        .withSchedule(dailyTime)
                                        .build();

		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.scheduleJob(jobDetail, trigger);
		
		scheduler.start();
		Thread.sleep(300L * 1000L);
		scheduler.shutdown();
    }
}
