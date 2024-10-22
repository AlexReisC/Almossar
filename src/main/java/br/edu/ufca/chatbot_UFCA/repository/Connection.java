package br.edu.ufca.chatbot_UFCA.repository;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Connection {
	private final static String URL = "jdbc:postgresql://localhost:5432/cardapio_bot";
	private final static String USER = "postgres";
	private final static String PASSWORD = "postgres";
	
	private static final Logger logger = LogManager.getLogger(Connection.class);
	
	public static java.sql.Connection createConnection() throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("Driver nao encontrado {}", e);
		}
		java.sql.Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		return conn;
	}
	
	public static boolean usuarioSalvo(Long chatId) {
		String sql = "SELECT '%d' FROM cardapio_bot.usuario;".formatted(chatId.longValue());
		try (java.sql.Connection conn = createConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			return rset.next();
		} catch (SQLException e) {
			logger.error("Erro de acesso ao BD, {}", e.getMessage(), e);
			return false;
		}
	}
	
	public static void salvarUsuario(Long chatId) {
		if(!usuarioSalvo(chatId)) {
			String sql = "INSERT INTO cardapio_bot.usuario (chatId) VALUES (?);";
			try (java.sql.Connection conn = createConnection()) {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, chatId);
				stmt.executeUpdate();
			} catch (SQLException e) {
				logger.error("Erro de acesso ao BD, {}", e.getMessage(), e);
				return;
			}
			logger.info("Usuario {} salvo", chatId);
		} else {
			logger.info("Usuario {} nao salvo, pois ja existe no BD.", chatId);
		}
	}
	
	public static List<Long> obterUsuarios() {
		String sql = "SELECT * FROM cardapio_bot.usuario;";
		
		List<Long> idUsuarios = new ArrayList<>();
		
		try (java.sql.Connection connection = createConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()) {
				long chatId = result.getBigDecimal("chatId").longValue();
				idUsuarios.add(chatId);
			}
		} catch (SQLException e) {
			logger.error("Erro de acesso ao BD. {}", e);
		}
		
		return idUsuarios;
	}
	
}
