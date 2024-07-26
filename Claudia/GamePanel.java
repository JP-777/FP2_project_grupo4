import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel implements KeyListener {
    private JLabel player1, player2, player3;
    private JLabel questionLabel;
    private JLabel[] answerLabels;
    private JLabel[] finishLines;
    private int player1X, player2X, player3X;
    private final int playerWidth = 50;
    private final int playerHeight = 50;
    private Image trackImage;
    private Image player1Image, player2Image, player3Image;
    private int correctAnswer;
    private Random rand;

    public GamePanel(String trackImagePath, String player1Path, String player2Path, String player3Path) {
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);

        this.trackImage = new ImageIcon(trackImagePath).getImage();
        this.player1Image = new ImageIcon(player1Path).getImage();
        this.player2Image = new ImageIcon(player2Path).getImage();
        this.player3Image = new ImageIcon(player3Path).getImage();

        // Crear etiquetas para jugadores
        player1 = createPlayerLabel(player1Image);
        player2 = createPlayerLabel(player2Image);
        player3 = createPlayerLabel(player3Image);

        // Añadir los jugadores al panel
        add(player1);
        add(player2);
        add(player3);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(questionLabel);

        answerLabels = new JLabel[3];
        for (int i = 0; i < answerLabels.length; i++) {
            answerLabels[i] = new JLabel("", SwingConstants.CENTER);
            answerLabels[i].setFont(new Font("Arial", Font.BOLD, 20));
            answerLabels[i].setOpaque(true);
            answerLabels[i].setBackground(Color.WHITE);
            add(answerLabels[i]);
        }

        finishLines = new JLabel[3];
        for (int i = 0; i < finishLines.length; i++) {
            finishLines[i] = new JLabel(new ImageIcon("imagenes/finish.png"));
            add(finishLines[i]);
        }

        rand = new Random();
        generateQuestion();

        // Ajustar las posiciones de los componentes cuando se cambia el tamaño del panel
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateComponentPositions();
            }
        });

        // Inicializar posiciones de los jugadores
        updateComponentPositions();
    }

    private JLabel createPlayerLabel(Image image) {
        JLabel label = new JLabel(new ImageIcon(image));
        label.setSize(playerWidth, playerHeight);
        return label;
    }

    private void updateComponentPositions() {
        int width = getWidth();
        int height = getHeight();
        int thirdWidth = width / 3;

        // Actualizar las posiciones de los jugadores
        player1X = 0;
        player2X = thirdWidth;
        player3X = 2 * thirdWidth;
        int initialY = height - playerHeight - 20;
        player1.setBounds(player1X, initialY, playerWidth, playerHeight);
        player2.setBounds(player2X, initialY, playerWidth, playerHeight);
        player3.setBounds(player3X, initialY, playerWidth, playerHeight);

        // Actualizar las posiciones de las etiquetas de respuestas
        questionLabel.setBounds(width / 2 - 100, 20, 200, 30);
        for (int i = 0; i < answerLabels.length; i++) {
            answerLabels[i].setBounds(thirdWidth * i + 50, 60, 100, 30);
        }

        // Actualizar las posiciones de las líneas de meta
        for (int i = 0; i < finishLines.length; i++) {
            finishLines[i].setBounds(thirdWidth * i + thirdWidth / 2 - 25, 10, 50, 50);
        }
    }

    private void generateQuestion() {
        int num1 = rand.nextInt(10) + 1;
        int num2 = rand.nextInt(10) + 1;
        correctAnswer = num1 + num2;
        questionLabel.setText(num1 + " + " + num2 + " = ?");
    
        // Crear respuestas incorrectas únicas
        int[] incorrectAnswers = new int[2];
        int incorrectAnswer1, incorrectAnswer2;
        do {
            incorrectAnswer1 = rand.nextInt(20) + 1;
        } while (incorrectAnswer1 == correctAnswer);
    
        do {
            incorrectAnswer2 = rand.nextInt(20) + 1;
        } while (incorrectAnswer2 == correctAnswer || incorrectAnswer2 == incorrectAnswer1);
    
        incorrectAnswers[0] = incorrectAnswer1;
        incorrectAnswers[1] = incorrectAnswer2;
    
        // Asignar respuestas a las etiquetas
        int correctPosition = rand.nextInt(3);
        for (int i = 0; i < 3; i++) {
            if (i == correctPosition) {
                answerLabels[i].setText(String.valueOf(correctAnswer));
            } else {
                answerLabels[i].setText(String.valueOf(incorrectAnswers[i < correctPosition ? i : i - 1]));
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
            repaint(); // Redibuja el panel para mostrar la nueva posición del jugador
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(trackImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Juego de Carreras Matemáticas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

               // Continuación del método main
               GamePanel gamePanel = new GamePanel("Claudia\\imagenes\\pista.jpg", 
               "Claudia\\imagenes\\jug1.jpg", 
               "Claudia\\imagenes\\jug2.jpg", 
               "Claudia\\imagenes\\jug3.jpg");
frame.add(gamePanel);
frame.setVisible(true);
}
}

