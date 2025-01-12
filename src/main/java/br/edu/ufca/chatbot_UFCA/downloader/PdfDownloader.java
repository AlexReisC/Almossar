package br.edu.ufca.chatbot_UFCA.downloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class PdfDownloader implements Job {
	private static final Logger logger = LogManager.getLogger(PdfDownloader.class);
	private final String UFCA_SITE = "https://www.ufca.edu.br/assuntos-estudantis/refeitorio-universitario/cardapios/";
	public static final String NOME_ARQUIVO = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "arquivos" + File.separator + "cardapio.pdf";

	public void baixarPdf(){
		logger.info("Iniciando download do PDF");
		Document doc;
		long bytes = 0;
		try {
			doc = Jsoup.connect(UFCA_SITE).get();
			
			String semanaAtual = doc.select("div.title").last().lastChild().toString();
			logger.info(semanaAtual);

			int indice = semanaAtual.indexOf("o");
			Integer inicioSemana = Integer.valueOf(semanaAtual.substring(indice+2,indice+4));
			Integer fimSemana = Integer.valueOf(semanaAtual.substring(indice+15,indice+17));
			logger.info("Inicio {} e Fim {}", inicioSemana, fimSemana);
			
			int diaDoMes = LocalDate.now().getDayOfMonth();
			logger.info(diaDoMes);
			if(!(diaDoMes >= inicioSemana.intValue() && diaDoMes <= fimSemana.intValue())){
				logger.info("Cardapio desta semana ainda nao postado!");
				return;
			}

			Element pdfLink = doc.select("a.ui.teal.button").last();
			if(pdfLink == null) {
				logger.error("Link do PDF nao encontrado na pagina");
				return;
			}
			
			String pdfUrl = pdfLink.attr("href");
			logger.info("URL original do PDF: {}", pdfUrl);
	        
			URI uri = new URI(pdfUrl);
			URL url = uri.toURL();
			try (InputStream in = url.openStream()){
				bytes = Files.copy(in, Paths.get(NOME_ARQUIVO), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			logger.error("Erro ao acessar ou baixar o arquivo PDF: {}", e.getMessage(), e);
		} catch (URISyntaxException e) {
			logger.error("Erro na URI: {}", e.getMessage(), e);
		}
		
		if(bytes != 0) {
			logger.info("PDF baixado com sucesso! Tamanho: {} bytes", bytes);
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		baixarPdf();
	}
}
