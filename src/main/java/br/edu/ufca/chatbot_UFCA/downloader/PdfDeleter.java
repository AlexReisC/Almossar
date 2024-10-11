package br.edu.ufca.chatbot_UFCA.downloader;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PdfDeleter implements Job{
	private static final Logger logger = LogManager.getLogger(PdfDeleter.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			boolean deleted = Files.deleteIfExists(Paths.get(PdfDownloader.NOME_ARQUIVO));
			logger.info("PDF deletado? {}", deleted);
		} catch (DirectoryNotEmptyException e) {
			logger.info("O arquivo é um diretorio nao vazio");
		} catch (IOException e2) {
			logger.info("Erro de I/O");
		}
	}
	
}
