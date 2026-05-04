package teladelogin; // Define o pacote da classe

import java.sql.Connection; // Importa a classe de conexão com o banco
import java.sql.PreparedStatement; // Importa a classe para comandos SQL com parâmetros
import java.sql.ResultSet; // Importa a classe que armazena resultados de consultas
import java.sql.SQLException; // Importa a classe para tratar erros SQL

public class UsuarioDAO { // Classe responsável por acessar os dados dos usuários no banco

    public Usuario buscarPorUsuario(String nomeUsuario) { // Método que busca um usuário pelo login informado

        String sql = "SELECT * FROM usuarios WHERE usuario = ?"; // Consulta SQL com parâmetro para evitar SQL Injection

        try (Connection conn = BancoDados.conectar(); // Abre conexão com o banco
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara o comando SQL

            stmt.setString(1, nomeUsuario); // Substitui o primeiro ? pelo nome do usuário digitado

            ResultSet rs = stmt.executeQuery(); // Executa a consulta e guarda o resultado

            if (rs.next()) { // Verifica se encontrou algum usuário

                Usuario usuario = new Usuario(); // Cria um objeto Usuario

                usuario.setId(rs.getInt("id")); // Preenche o id com o valor vindo do banco

                usuario.setUsuario(rs.getString("usuario")); // Preenche o login com o valor vindo do banco

                usuario.setSenha(rs.getString("senha")); // Preenche a senha com o valor vindo do banco

                return usuario; // Retorna o usuário encontrado
            }

        } catch (SQLException e) { // Captura erros de banco de dados

            System.out.println("Erro ao buscar usuário: " + e.getMessage()); // Mostra o erro no console
        }

        return null; // Retorna null caso o usuário não seja encontrado
    }

    public boolean validarLogin(String nomeUsuario, String senhaDigitada) { // Método que valida usuário e senha

        Usuario usuario = buscarPorUsuario(nomeUsuario); // Busca o usuário no banco de dados

        if (usuario == null) { // Verifica se o usuário não foi encontrado

            return false; // Retorna falso porque o usuário não existe
        }

        return usuario.getSenha().equals(senhaDigitada); // Compara a senha digitada com a senha salva no banco
    }
}