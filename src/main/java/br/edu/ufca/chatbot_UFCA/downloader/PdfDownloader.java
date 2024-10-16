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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PdfDownloader implements Job {
	private static final Logger logger = LogManager.getLogger(PdfDownloader.class);
	private final String UFCA_SITE = "https://www.ufca.edu.br/assuntos-estudantis/refeitorio-universitario/cardapios/";
	public static final String NOME_ARQUIVO = "src\\main\\resources\\arquivos\\cardapio.pdf";

	public void baixarPdf(){
		Document doc;
		long bytes = 0;
		try {
			doc = Jsoup.connect(UFCA_SITE).get();
			Element pdfLink = doc.select("a.ui.teal.button").last();
			String pdfUrl = pdfLink.attr("href");
			logger.info("URL do pdf: {}", pdfUrl);
			
			URI uri = new URI(pdfUrl.replace(" ", "%20"));
			URL url = uri.toURL();
			InputStream in = url.openStream();
			bytes = Files.copy(in, Paths.get(NOME_ARQUIVO), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.info("Erro ao acessar o arquivo pdf! {}", e);
		} catch (URISyntaxException e2) {
			logger.info("Erro na URI: {}", e2);
		}
		
		if(bytes != 0) {
			logger.info("PDF baixado com sucesso!");
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		baixarPdf();
	}
}
