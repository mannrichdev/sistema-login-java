package teladelogin; // Define o pacote da classe

public class Usuario { // Classe modelo que representa um usuário do sistema

    private int id; // Armazena o código identificador do usuário

    private String usuario; // Armazena o nome de login do usuário

    private String senha; // Armazena a senha do usuário

    public Usuario() { // Construtor vazio da classe Usuario
    }

    public Usuario(int id, String usuario, String senha) { // Construtor com todos os dados do usuário

        this.id = id; // Recebe e armazena o id do usuário

        this.usuario = usuario; // Recebe e armazena o nome do usuário

        this.senha = senha; // Recebe e armazena a senha do usuário
    }

    public int getId() { // Método que retorna o id do usuário

        return id; // Retorna o id armazenado
    }

    public void setId(int id) { // Método que altera o id do usuário

        this.id = id; // Atualiza o valor do id
    }

    public String getUsuario() { // Método que retorna o nome do usuário

        return usuario; // Retorna o usuário armazenado
    }

    public void setUsuario(String usuario) { // Método que altera o nome do usuário

        this.usuario = usuario; // Atualiza o valor do usuário
    }

    public String getSenha() { // Método que retorna a senha do usuário

        return senha; // Retorna a senha armazenada
    }

    public void setSenha(String senha) { // Método que altera a senha do usuário

        this.senha = senha; // Atualiza o valor da senha
    }
}