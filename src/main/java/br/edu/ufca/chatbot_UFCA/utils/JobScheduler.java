package br.edu.ufca.chatbot_UFCA.utils;

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

	public void agendarDownload() throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob(PdfDownloader.class).withIdentity("pdfDownloadJob", "grupo").build();

		TimeOfDay inicio = TimeOfDay.hourAndMinuteOfDay(8, 0);
		TimeOfDay fim = TimeOfDay.hourAndMinuteOfDay(11, 0);

		DailyTimeIntervalScheduleBuilder dailySchedule = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
				.startingDailyAt(inicio).endingDailyAt(fim).withIntervalInMinutes(15)
				.onDaysOfTheWeek(DateBuilder.MONDAY, DateBuilder.TUESDAY);

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("pdfDownloadTrigger", "grupo")
				.withSchedule(dailySchedule).build();

		Scheduler scheduler;
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
		scheduler.pauseAll();
	}
	
	public void deletarPdf() throws SchedulerException {
		JobDetail deletionJobDetail = JobBuilder.newJob(PdfDeleter.class).withIdentity("pdfDeletionJob", "grupo").build();

		Trigger deletionTrigger = TriggerBuilder.newTrigger().withIdentity("pdfDeletionTrigger", "grupo")
				.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.SATURDAY, 0, 0)).build();

		Scheduler scheduler;
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.scheduleJob(deletionJobDetail, deletionTrigger);
		scheduler.start();
	}
	
	public void agendarEnvioDiario() throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(SendDailyCardapio.class)
                                        .withIdentity("sendDailyMenuJob", "grupo")
                                        .build();
        
        TimeOfDay inicio = TimeOfDay.hourAndMinuteOfDay(8, 1);
        TimeOfDay fim = TimeOfDay.hourAndMinuteOfDay(14, 2);
        DailyTimeIntervalScheduleBuilder dailyTime = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
        								.startingDailyAt(inicio)
        								.endingDailyAt(fim)
        								.withIntervalInHours(6)
        								.onEveryDay();
        
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("dailyMenuTrigger", "grupo")
                                        .withSchedule(dailyTime)
                                        .build();

        Scheduler scheduler;
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
    }
}
