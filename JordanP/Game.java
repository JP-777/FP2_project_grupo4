import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PanelGame extends JPanel implements KeyListener {
    private JLabel player1, player2, player3;
    private int player1X, player1Y, player2X, player2Y, player3X, player3Y;
    private final int playerWidth = 50;
    private final int playerHeight = 50;
    private final int divisions = 3; // Número de divisiones en cada carril
    private Image imagen;

    public PanelGame(String rutaImagen) {
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);

        this.imagen = new ImageIcon(rutaImagen).getImage();

        player1 = createPlayerLabel("pato.png");
        player2 = createPlayerLabel("player2.png");
        player3 = createPlayerLabel("player3.png");

        int panelHeight = getHeight();
        player1X = 0;
        player2X = 0;
        player3X = 0;

        player1Y = (panelHeight / divisions) - (playerHeight / 2);
        player2Y = (panelHeight / divisions) - (playerHeight / 2);
        player3Y = (panelHeight / divisions) - (playerHeight / 2);

        player1.setBounds(player1X, player1Y, playerWidth, playerHeight);
        player2.setBounds(player2X, player2Y, playerWidth, playerHeight);
        player3.setBounds(player3X, player3Y, playerWidth, playerHeight);

        add(player1);
        add(player2);
        add(player3);
    }

    private JLabel createPlayerLabel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(playerWidth, playerHeight, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        label.setSize(playerWidth, playerHeight);
        return label;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int panelWidth = getWidth();
        int thirdWidth = panelWidth / 3;
        int panelHeight = getHeight();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A: // Mover player 1 a la izquierda
                if (player1X > 0) player1X -= thirdWidth;
                break;
            case KeyEvent.VK_D: // Mover player 1 a la derecha
                if (player1X < 2 * thirdWidth) player1X += thirdWidth;
                break;

            case KeyEvent.VK_J: // Mover player 2 a la izquierda
                if (player2X > 0) player2X -= (thirdWidth+200);
                break;
            case KeyEvent.VK_L: // Mover player 2 a la derecha
                if (player2X < 2 * thirdWidth) player2X += (thirdWidth+200);
                break;

            case KeyEvent.VK_LEFT: // Mover player 3 a la izquierda
                if (player3X > 0) player3X -= (thirdWidth+200);
                break;
            case KeyEvent.VK_RIGHT: // Mover player 3 a la derecha
                if (player3X < 2 * thirdWidth) player3X += (thirdWidth+400);
                break;
        }

        // Mantener a los jugadores en sus divisiones respectivas
        player1Y = (panelHeight / divisions) - (playerHeight / 2);
        player2Y = (panelHeight / divisions) - (playerHeight / 2);
        player3Y = (panelHeight / divisions) - (playerHeight / 2);

        player1.setLocation(player1X, player1Y);
        player2.setLocation(player2X, player2Y);
        player3.setLocation(player3X, player3Y);
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        PanelGame centralPanel = new PanelGame("image_test2.jpg");
        LayerPanel leftPanel = new LayerPanel("image_test1.jpg");
        JPanel rightPanel = createRightPanel();

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

    private static JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel para la suma
        JPanel sumaPanel = new JPanel();
        sumaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sumaPanel.add(new JLabel("Suma a resolver:"));

        // Panel para el puesto
        JPanel puestoPanel = new JPanel();
        puestoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        puestoPanel.add(new JLabel("Puesto del jugador:"));

        // Panel para el puntaje
        JPanel puntajePanel = new JPanel();
        puntajePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        puntajePanel.add(new JLabel("Puntaje del jugador:"));

        // Panel para el mapa
        JPanel mapaPanel = new JPanel();
        mapaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mapaPanel.add(new JLabel("Mapa de la carrera:"));
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        rightPanel.add(sumaPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        rightPanel.add(puestoPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.1;
        rightPanel.add(puntajePanel, gbc);

        gbc.gridy = 3;
        gbc.weighty = 0.7;
        rightPanel.add(mapaPanel, gbc);

        return rightPanel;
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
