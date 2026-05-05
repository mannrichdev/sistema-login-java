package teladelogin;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

public class TelaLogin extends JFrame {

    private JPanel painelPrincipal;

    private JLabel lblTitulo;

    private JLabel lblUsuario;

    private JLabel lblSenha;

    private JTextField txtUsuario;

    private JPasswordField txtSenha;

    private JButton btnOk;

    private JButton btnCancelar;

    private UsuarioDAO usuarioDAO;

    // CONTROLE DE TENTATIVAS
    private int tentativas = 0;

    // HORÁRIO DO BLOQUEIO
    private long tempoBloqueio = 0;

    // CONTROLE DE BLOQUEIO
    private boolean bloqueado = false;

    // TEMPO TOTAL DE BLOQUEIO (3 minutos)
    private final long TEMPO_BLOQUEIO = 180000;

    public TelaLogin() {

        usuarioDAO = new UsuarioDAO();

        inicializarComponentes();

        setLocationRelativeTo(null);

        setTitle("Tela de Login");
    }

    private void inicializarComponentes() {

        painelPrincipal = new JPanel();

        lblTitulo = new JLabel();

        lblUsuario = new JLabel();

        lblSenha = new JLabel();

        txtUsuario = new JTextField();

        txtSenha = new JPasswordField();

        btnOk = new JButton();

        btnCancelar = new JButton();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painelPrincipal.setBorder(
                javax.swing.BorderFactory.createTitledBorder("Login do Sistema")
        );

        lblTitulo.setFont(
                new java.awt.Font("Arial", java.awt.Font.BOLD, 18)
        );

        lblTitulo.setText("Acesso ao Sistema");

        lblUsuario.setText("Usuário:");

        lblSenha.setText("Senha:");

        btnOk.setText("OK");

        btnCancelar.setText("Cancelar");

        btnOk.addActionListener(evento -> realizarLogin());

        btnCancelar.addActionListener(evento -> System.exit(0));

        GroupLayout layout = new GroupLayout(painelPrincipal);

        painelPrincipal.setLayout(layout);

        layout.setAutoCreateGaps(true);

        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblTitulo)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblUsuario)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtUsuario, 160, 160, 160))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblSenha)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtSenha, 160, 160, 160))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnOk, 90, 90, 90)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnCancelar, 90, 90, 90))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblTitulo)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, 30, 30, 30))
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenha)
                    .addComponent(txtSenha, 30, 30, 30))
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancelar))
        );

        add(painelPrincipal);

        pack();
    }

    private void realizarLogin() {

        // VERIFICA SE O SISTEMA ESTÁ BLOQUEADO
        if (bloqueado) {

            long tempoAtual = System.currentTimeMillis();

            long tempoDecorrido = tempoAtual - tempoBloqueio;

            // Se ainda estiver dentro do tempo de bloqueio
            if (tempoDecorrido < TEMPO_BLOQUEIO) {

                long tempoRestante = TEMPO_BLOQUEIO - tempoDecorrido;

                long minutos = tempoRestante / 60000;

                long segundos = (tempoRestante % 60000) / 1000;

                JOptionPane.showMessageDialog(
                        this,
                        "Login bloqueado.\n" +
                        "Nova tentativa em: " +
                        minutos + " minuto(s) e " +
                        segundos + " segundo(s)."
                );

                return;

            } else {
                // Libera novamente após o tempo acabar
                bloqueado = false;
                tentativas = 0;
            }
        }

        String usuarioDigitado = txtUsuario.getText();

        String senhaDigitada = new String(txtSenha.getPassword());

        // CAMPOS VAZIOS
        if (usuarioDigitado.isEmpty() || senhaDigitada.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha usuário e senha."
            );

            return;
        }

        boolean loginValido = usuarioDAO.validarLogin(
                usuarioDigitado,
                senhaDigitada
        );

        // LOGIN CORRETO
        if (loginValido) {

            tentativas = 0;

            JOptionPane.showMessageDialog(
                    this,
                    "Login realizado com sucesso!"
            );

        } else {

            // LOGIN ERRADO
            tentativas++;

            if (tentativas >= 3) {

                bloqueado = true;

                tempoBloqueio = System.currentTimeMillis();

                JOptionPane.showMessageDialog(
                        this,
                        "Login bloqueado por 3 minutos."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário ou senha incorretos.\n" +
                        "Tentativa " + tentativas + " de 3."
                );
            }
        }
    }
}
