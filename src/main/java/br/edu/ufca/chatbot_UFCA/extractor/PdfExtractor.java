package br.edu.ufca.chatbot_UFCA.extractor;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import br.edu.ufca.chatbot_UFCA.model.Cardapio;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfExtractor {
	
	public static StringBuilder[] almocoDoDia;
	public static StringBuilder[] jantarDoDia;
    
    public static void extrairTextoDia(String arquivo, int dia) throws IOException {
    	PDDocument doc = new PDDocument();
		doc = Loader.loadPDF(new File(arquivo));
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        Page page = new ObjectExtractor(doc).extract(1);

        List<Table> table = sea.extract(page);
        List<List<RectangularTextContainer>> rows = table.get(0).getRows();
        
        boolean isAlmoco = true;
        
    	almocoDoDia = new StringBuilder[] {
    		new StringBuilder("Prato Principal: \n"), 
    		new StringBuilder("Sopas: \n"), 
    		new StringBuilder("Vegetariano: \n"),
    		new StringBuilder("Guarnição: \n"),
    		new StringBuilder("Saladas: \n"),
    		new StringBuilder("Acompanhamentos: \n"),
    		new StringBuilder("Suco: \n"),
    		new StringBuilder("Sobremesa: \n")
    	};
        
        jantarDoDia = new StringBuilder[] {
        		new StringBuilder("Prato Principal: \n"), 
        		new StringBuilder("Sopas: \n"), 
        		new StringBuilder("Vegetariano: \n"),
        		new StringBuilder("Guarnição: \n"),
        		new StringBuilder("Saladas: \n"),
        		new StringBuilder("Acompanhamentos: \n"),
        		new StringBuilder("Suco: \n"),
        		new StringBuilder("Sobremesa: \n")
        };
        
        String ultimoTipoPrato = "";
        
        for(List<RectangularTextContainer> cells : rows) {
        	String tipoPrato = cells.get(0).getText().replace("\n", " ").trim();
        	
        	if (tipoPrato.equalsIgnoreCase("ALMOÇO")) {
                isAlmoco = true;
                continue;
            } else if (tipoPrato.equalsIgnoreCase("JANTAR")) {
                isAlmoco = false;
                continue;
            }
        	
        	if (!tipoPrato.isEmpty()) {
                ultimoTipoPrato = tipoPrato.toLowerCase();
                if(ultimoTipoPrato.contains("ompanham")) {
                	ultimoTipoPrato = "acompanhamentos";
                }
            }        
        	
        	String prato = cells.get(dia).getText().replace("\n", " ");
            prato = prato.contains("FEIRA") || prato.contains("feira")? "" : prato;
            
            if(!prato.isEmpty()) {
            	StringBuilder[] refeicoesDia = isAlmoco ? almocoDoDia : jantarDoDia;
            	
            	boolean vegetarianoVazio = almocoDoDia[2].toString().contentEquals("Vegetariano: \n");
				if(ultimoTipoPrato.equals("vegetariano") && !vegetarianoVazio && isAlmoco) {
					ultimoTipoPrato = "saladas";
            	}
            	
				if(ultimoTipoPrato.equals("sobremesa") && !isAlmoco && !prato.contains("Doce")) {
            		ultimoTipoPrato = "principal";
            	}
				
				switch (ultimoTipoPrato) {
	                case "principal":
	                    refeicoesDia[0].append("- " + prato).append("\n");
	                    break;
	                case "sopa":
	                case "sopas":	                	
	                	refeicoesDia[1].append("- " + prato).append("\n");
	                	break;
	                case "vegetariano":
	                	refeicoesDia[2].append("- " + prato).append("\n");
	            		break;
	                case "guarnição":
	                	refeicoesDia[3].append("- " + prato).append("\n");
	                    break;
	                case "salada":
	                case "saladas":
	                	refeicoesDia[4].append("- " + prato).append("\n");
	                    break;
	                case "acompanhamentos" :
	                	refeicoesDia[5].append("- " + prato).append("\n");	                	
	                	break;
	                case "suco":
	                	refeicoesDia[6].append("- " + prato).append("\n");
	                	break;
	                case "sobremesa":
	                	prato = prato.replace(" ", " \n");
	                	prato = "- " + prato;
	                	prato = prato.replace("D", "- D");                        	
	                    refeicoesDia[7].append(prato);
	                    break;
				}
            }
        }
        doc.close();
        
    }
    
}