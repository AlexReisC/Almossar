package br.edu.ufca.chatbot_UFCA.repository;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Connection {
	private final static String URL = "jdbc:postgresql://localhost:5432/cardapio_bot";
	private final static String USER = "seu_usuario";
	private final static String PASSWORD = "sua_senha";
	
	private static final Logger logger = LogManager.getLogger(Connection.class);
	
	public static java.sql.Connection createConnection() throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			logger.info("Driver nao encontrado {}", e);
		}
		java.sql.Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		return conn;
	}
	
	public static boolean usuarioNaoSalvo(Long chatId) {
		String sql = "SELECT '%d' FROM cardapio_bot.usuario;".formatted(chatId.longValue());
		boolean existe = false;
		try (java.sql.Connection conn = createConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			existe = rset.equals(null);
		} catch (SQLException e) {
			logger.info("Erro de acesso ao BD, {}", e);
		}
		return existe;
	}
	
	public static void salvarUsuario(Long chatId) {
		if(usuarioNaoSalvo(chatId)) {
			String sql = "INSERT INTO cardapio_bot.usuario (chatId) VALUES ('%d');".formatted(chatId.longValue());
			try (java.sql.Connection conn = createConnection()) {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				logger.info("Erro de acesso ao BD, {}", e);
			}
			logger.info("Usuario {} salvo", chatId);
		}
		logger.info("Usuario {} nao salvo, pois ja existe no BD.", chatId);
	}
	
	public static List<Long> obterUsuarios() {
		String sql = "SELECT * FROM cardapio_bot.usuario;";
		
		List<Long> idUsuarios = new ArrayList<>();
		
		try (java.sql.Connection connection = createConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()) {
				idUsuarios.add(result.getBigDecimal("chatId").longValue());
			}
		} catch (SQLException e) {
			logger.info("Erro de acesso ao BD. {}", e);
		}
		
		return idUsuarios;
	}
	
}
