package br.edu.ufca.chatbot_UFCA.downloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PdfDownloader implements Job {
	private static final Logger logger = LogManager.getLogger(PdfDownloader.class);
	private final String UFCA_SITE = "https://www.ufca.edu.br/assuntos-estudantis/refeitorio-universitario/cardapios/";
	public static final String NOME_ARQUIVO = "src\\main\\resources\\arquivos\\cardapio.pdf";

	public void baixarPdf(String url){
		Document doc;
		try {
			doc = Jsoup.connect(UFCA_SITE).get();
			Element pdfLink = doc.select("a.ui.teal.button").last();
			String pdfUrl = pdfLink.attr("href");
			logger.info("URL do pdf: {}", pdfUrl);
			InputStream in = new URL(pdfUrl).openStream();
			Files.copy(in, Paths.get(NOME_ARQUIVO), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.info("Erro ao tentar baixar o pdf! {}", e);
		}
		
		logger.info("PDF baixado com sucesso!");
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		baixarPdf(NOME_ARQUIVO);
	}
}
