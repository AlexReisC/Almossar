package br.edu.ufca.chatbot_UFCA.extractor;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfExtractor {
	
	public static Map<String, StringBuilder[]> almocoPorDia = new HashMap<>();
    public static Map<String, StringBuilder[]> jantarPorDia = new HashMap<>();

    public static void extrairTexto(String arquivo) throws IOException {
        PDDocument doc = new PDDocument();
        doc = Loader.loadPDF(new File(arquivo));

        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        Page page = new ObjectExtractor(doc).extract(1);

        List<Table> table = sea.extract(page);


        String[] diasSemana = {"Segunda", "Terça", "Quarta", "Quinta", "Sexta"};
        
        String ultimoTipoPrato = "";
        boolean isAlmoco = true;
        
        List<List<RectangularTextContainer>> rows = table.get(0).getRows();
        // Transformar o Stringbuilder em string normal e verifica o size
        for (String dia : diasSemana) {
            almocoPorDia.put(dia, new StringBuilder[] {
                new StringBuilder("Prato Principal: "), 
                new StringBuilder("Sopas: "), 
                new StringBuilder("Vegetariano: "),
                new StringBuilder("Guarnição: "),
                new StringBuilder("Saladas: "),
                new StringBuilder("Acompanhamentos: "),
                new StringBuilder("Suco: "),
                new StringBuilder("Sobremesa: ")
            });
            jantarPorDia.put(dia, new StringBuilder[] {
        		new StringBuilder("Prato Principal: "), 
        		new StringBuilder("Sopas: "), 
        		new StringBuilder("Vegetariano: "),
        		new StringBuilder("Guarnição: "),
        		new StringBuilder("Saladas: "),
        		new StringBuilder("Acompanhamentos: "),
        		new StringBuilder("Suco: "),
        		new StringBuilder("Sobremesa: ")
            });
        }

        for (List<RectangularTextContainer> cells : rows) {
            String tipoPrato = cells.get(0).getText().replace("\r", " ").trim();
            
            if (tipoPrato.equalsIgnoreCase("ALMOÇO")) {
                isAlmoco = true;
                continue;
            } else if (tipoPrato.equalsIgnoreCase("JANTAR")) {
                isAlmoco = false;
                continue;
            }
            
            if (!tipoPrato.isEmpty()) {
                ultimoTipoPrato = tipoPrato.toLowerCase(); // Salva o tipo de prato (para continuidade)
            }
            
            for (int i = 1; i < cells.size(); i++) {
                String prato = cells.get(i).getText().replace("\r", " ").trim();
                prato = prato.contains("SEGUNDA") ? "" : prato;
                if (!prato.isEmpty()) {
                	Map<String, StringBuilder[]> refeicoesPorDia = isAlmoco ? almocoPorDia : jantarPorDia;
                	
                	boolean vegetarianoVazio = almocoPorDia.get(diasSemana[i - 1])[2].toString().equals("Vegetariano: ");
					if(ultimoTipoPrato.equals("vegetariano") && !vegetarianoVazio && isAlmoco) {
						ultimoTipoPrato = "saladas";
                	}
                	
					if(ultimoTipoPrato.equals("sobremesa") && !isAlmoco && !prato.contains("Doce")) {
                		ultimoTipoPrato = "principal";
                	}
                    
                	switch (ultimoTipoPrato) {
                        case "principal":
                            refeicoesPorDia.get(diasSemana[i - 1])[0].append(prato).append(", ");
                            break;
                        case "sopa":
                        case "sopas":
                        	refeicoesPorDia.get(diasSemana[i - 1])[1].append(prato);
                        	break;
                        case "vegetariano":
                    		refeicoesPorDia.get(diasSemana[i - 1])[2].append(prato);
                    		break;
                        case "guarnição":
                            refeicoesPorDia.get(diasSemana[i - 1])[3].append(prato);
                            break;
                        case "salada":
                        case "saladas":
                            refeicoesPorDia.get(diasSemana[i - 1])[4].append(prato).append(", ");
                            break;
                        case "acompanhament os":
                        	refeicoesPorDia.get(diasSemana[i - 1])[5].append(prato);
                        	break;
                        case "suco":
                        	refeicoesPorDia.get(diasSemana[i - 1])[6].append(prato);
                        	break;
                        case "sobremesa":
                        	prato = prato.replace(" ", ", ");
                        	if(!prato.contains("SEGUNDA")) {
	                            refeicoesPorDia.get(diasSemana[i - 1])[7].append(prato);
	                            break;
                        	}
                    }
                }
            }
        }

        doc.close();
    }

}
