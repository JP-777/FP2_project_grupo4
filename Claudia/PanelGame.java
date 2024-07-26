import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelGame extends JPanel implements KeyListener {
    private JLabel player1, player2, player3;
    private JLabel questionLabel;
    private JLabel[] answerLabels;
    private JLabel[] finishLines;
    private int player1X, player2X, player3X;
    private final int playerWidth = 50;
    private final int playerHeight = 50;
    private Image imagen;
    private int correctAnswer;
    private Random rand;

    public PanelGame(String rutaImagen) {
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);

        this.imagen = new ImageIcon(rutaImagen).getImage();

        player1 = createPlayerLabel("images/player1.png");
        player2 = createPlayerLabel("images/player2.png");
        player3 = createPlayerLabel("images/player3.png");

        player1X = 0;
        player2X = getWidth() / 3;
        player3X = 2 * getWidth() / 3;

        int initialY = getHeight() - playerHeight - 20;
        player1.setBounds(player1X, initialY, playerWidth, playerHeight);
        player2.setBounds(player2X, initialY, playerWidth, playerHeight);
        player3.setBounds(player3X, initialY, playerWidth, playerHeight);

        add(player1);
        add(player2);
        add(player3);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setBounds(getWidth() / 2 - 50, 20, 200, 30);
        add(questionLabel);

        answerLabels = new JLabel[3];
        for (int i = 0; i < answerLabels.length; i++) {
            answerLabels[i] = new JLabel("", SwingConstants.CENTER);
            answerLabels[i].setFont(new Font("Arial", Font.BOLD, 20));
            answerLabels[i].setOpaque(true);
            answerLabels[i].setBackground(Color.WHITE);
            answerLabels[i].setBounds(getWidth() / 3 * i + 50, 60, 100, 30);
            add(answerLabels[i]);
        }

        finishLines = new JLabel[3];
        for (int i = 0; i < finishLines.length; i++) {
            finishLines[i] = new JLabel(new ImageIcon("images/finish.png"));
            finishLines[i].setBounds(getWidth() / 3 * i + getWidth() / 6, 10, 50, 50);
            add(finishLines[i]);
        }

        rand = new Random();
        generateQuestion();
    }

    private JLabel createPlayerLabel(String imagePath) {
        JLabel label = new JLabel(new ImageIcon(imagePath));
        label.setSize(playerWidth, playerHeight);
        return label;
    }

    private void generateQuestion() {
        int num1 = rand.nextInt(10) + 1;
        int num2 = rand.nextInt(10) + 1;
        correctAnswer = num1 + num2;
        questionLabel.setText(num1 + " + " + num2 + " = ?");

        int correctPosition = rand.nextInt(3);
        for (int i = 0; i < 3; i++) {
            if (i == correctPosition) {
                answerLabels[i].setText(String.valueOf(correctAnswer));
            } else {
                answerLabels[i].setText(String.valueOf(rand.nextInt(20) + 1));
            }
        }
    }

    private void checkAnswer(int player, int answer) {
        int thirdWidth = getWidth() / 3;
        int initialY = getHeight() - playerHeight - 20;

        if (answer == correctAnswer) {
            if (player == 1) {
                player1X += thirdWidth;
                player1.setLocation(player1X, initialY);
            } else if (player == 2) {
                player2X += thirdWidth;
                player2.setLocation(player2X, initialY);
            } else if (player == 3) {
                player3X += thirdWidth;
                player3.setLocation(player3X, initialY);
            }
        }
        generateQuestion();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int answer = -1;
        if (e.getKeyCode() == KeyEvent.VK_W) {
            answer = Integer.parseInt(answerLabels[0].getText());
            checkAnswer(1, answer);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            answer = Integer.parseInt(answerLabels[1].getText());
            checkAnswer(1, answer);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            answer = Integer.parseInt(answerLabels[2].getText());
            checkAnswer(1, answer);
        } else if (e.getKeyCode() == KeyEvent.VK_I) {
            answer = Integer.parseInt(answerLabels[0].getText());
            checkAnswer(2, answer);
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            answer = Integer.parseInt(answerLabels[1].getText());
            checkAnswer(2, answer);
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            answer = Integer.parseInt(answerLabels[2].getText());
            checkAnswer(2, answer);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            answer = Integer.parseInt(answerLabels[0].getText());
            checkAnswer(3, answer);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            answer = Integer.parseInt(answerLabels[1].getText());
            checkAnswer(3, answer);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            answer = Integer.parseInt(answerLabels[2].getText());
            checkAnswer(3, answer);
        }

        // Lógica para los jugadores controlados por AI
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_D) {
            int aiAnswer = Integer.parseInt(answerLabels[rand.nextInt(3)].getText());
            checkAnswer(2, aiAnswer);
            aiAnswer = Integer.parseInt(answerLabels[rand.nextInt(3)].getText());
            checkAnswer(3, aiAnswer);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Juego de Carreras Matemáticas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        PanelGame centralPanel = new PanelGame("images/pista.png");
        LayerPanel leftPanel = new LayerPanel("images/pista.png");
        LayerPanel rightPanel = new LayerPanel("images/pista.png");

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

       
        gbc.gridx = 0;
        gbc.weightx = 1;
        mainPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 2;
        mainPanel.add(centralPanel, gbc);

        gbc.gridx = 2;
        gbc.weightx = 1;
        mainPanel.add(rightPanel, gbc);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}

class LayerPanel extends JPanel {
    private Image imagen;

    public LayerPanel(String fondoImagen) {
        this.imagen = new ImageIcon(fondoImagen).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}
