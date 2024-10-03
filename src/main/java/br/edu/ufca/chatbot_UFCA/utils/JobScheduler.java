package br.edu.ufca.chatbot_UFCA.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import br.edu.ufca.chatbot_UFCA.downloader.PdfDownloader;

public class JobScheduler {
	private static final Logger logger = LogManager.getLogger(JobScheduler.class);
	
	public void agendarDownload() {
		JobDetail jobDetail = JobBuilder.newJob(PdfDownloader.class).withIdentity("pdfDownloadJob", "grupo").build();

		TimeOfDay inicio = TimeOfDay.hourAndMinuteOfDay(8, 0);
		TimeOfDay fim = TimeOfDay.hourAndMinuteOfDay(11, 30);

		DailyTimeIntervalScheduleBuilder dailySchedule = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
				.startingDailyAt(inicio).endingDailyAt(fim).withIntervalInMinutes(15)
				.onDaysOfTheWeek(DateBuilder.MONDAY, DateBuilder.TUESDAY);

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("pdfDownloadTrigger", "grupo")
				.withSchedule(dailySchedule).build();

		Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			logger.info("Job ou Trigger nao adicionado. {}", e);
		}
	}
}
