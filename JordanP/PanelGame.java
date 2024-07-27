import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelGame extends JPanel implements KeyListener {
    private JLabel player1, player2, player3;
    private int player1X, player2X, player3X;
    private final int playerWidth = 50;
    private final int playerHeight = 50;
    private Image imagen;

    public PanelGame(String rutaImagen) {
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);

        this.imagen = new ImageIcon(rutaImagen).getImage();

        player1 = createPlayerLabel("player1.png");
        player2 = createPlayerLabel("player2.png");
        player3 = createPlayerLabel("player3.png");

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
    }

    private JLabel createPlayerLabel(String imagePath) {
        JLabel label = new JLabel(new ImageIcon(imagePath));
        label.setSize(playerWidth, playerHeight);
        return label;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int panelWidth = getWidth();
        int thirdWidth = panelWidth / 3;
        int initialY = getHeight() - playerHeight - 20;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: player1X = 0; break;
            case KeyEvent.VK_S: player1X = thirdWidth; break;
            case KeyEvent.VK_D: player1X = 2 * thirdWidth; break;

            case KeyEvent.VK_I: player2X = 0; break;
            case KeyEvent.VK_J: player2X = thirdWidth; break;
            case KeyEvent.VK_L: player2X = 2 * thirdWidth; break;

            case KeyEvent.VK_UP: player3X = 0; break;
            case KeyEvent.VK_LEFT: player3X = thirdWidth; break;
            case KeyEvent.VK_RIGHT: player3X = 2 * thirdWidth; break;
        }

        player1.setLocation(player1X, initialY);
        player2.setLocation(player2X, initialY);
        player3.setLocation(player3X, initialY);
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
        PanelGame centralPanel = new PanelGame("image_test2.jpg");
        LayerPanel leftPanel = new LayerPanel("image_test1.jpg");
        LayerPanel rightPanel = new LayerPanel("image_test3.jpg");

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
