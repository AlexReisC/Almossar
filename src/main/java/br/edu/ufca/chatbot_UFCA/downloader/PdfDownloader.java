package br.edu.ufca.chatbot_UFCA.downloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PdfDownloader {
	private static final Logger logger = LogManager.getLogger(PdfDownloader.class);
	public static String ufcaSite = "https://www.ufca.edu.br/assuntos-estudantis/refeitorio-universitario/cardapios/";
	private static String nomeArquivo = "src\\main\\resources\\arquivos\\cardapio.pdf";
	
	public static void baixarPdf(String url) throws IOException, FileAlreadyExistsException {
		Document doc = Jsoup.connect(ufcaSite).get();
		Element pdfLink = doc.select("a.ui.teal.button").last();
		String pdfUrl = pdfLink.attr("href");
		logger.info("pdfurl: {}", pdfUrl);
		
		InputStream in = new URL(pdfUrl).openStream();
		Files.copy(in, Paths.get(nomeArquivo), StandardCopyOption.REPLACE_EXISTING);
		logger.info("PDF baixado com sucesso!");
	}
}
