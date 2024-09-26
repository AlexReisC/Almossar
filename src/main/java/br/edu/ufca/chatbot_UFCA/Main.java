package br.edu.ufca.chatbot_UFCA;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ufca.chatbot_UFCA.downloader.PdfDownloader;
import br.edu.ufca.chatbot_UFCA.extractor.PdfExtractor;

public class Main 
{
	private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main( String[] args )
    {
    	try {
//			 PdfDownloader.baixarPdf(PdfDownloader.ufcaSite);
    		PdfExtractor.extrairTexto(PdfDownloader.nomeArquivo);
    	} catch (FileAlreadyExistsException e) {
    		logger.info("Pdf nao foi sobrescrito. {}", e);
		} catch (IOException e2) {
			logger.info("Erro ao tentar ler pdf. {}", e2);
		}
    }
}
