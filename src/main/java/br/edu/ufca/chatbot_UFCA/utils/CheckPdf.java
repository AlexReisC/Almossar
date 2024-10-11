package br.edu.ufca.chatbot_UFCA.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

import br.edu.ufca.chatbot_UFCA.downloader.PdfDownloader;

public class CheckPdf {
	public static boolean pdfExiste() {
		return Files.exists(Paths.get(PdfDownloader.NOME_ARQUIVO));
	}
}
