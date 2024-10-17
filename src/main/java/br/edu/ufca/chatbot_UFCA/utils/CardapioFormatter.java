package br.edu.ufca.chatbot_UFCA.utils;

public class CardapioFormatter {

	public static String editarCardapio(String cardapio) {
		return cardapio.replace("Prato Principal", "🍗 Prato Principal")
				.replace("Sopas","\n🍲 Sopas")
				.replace("Vegetariano","\n🥗 Vegetariano")
				.replace("Guarnição","\n🍝 Guarnição")
				.replace("Saladas","\n🥦 Saladas")
				.replace("Acompanhamentos","\n🍚 Acompanhamentos")
				.replace("Suco", "\n🥤 Suco")
				.replace("Sobremesa", "\n🍫 Sobremesa");
		
	}
}
