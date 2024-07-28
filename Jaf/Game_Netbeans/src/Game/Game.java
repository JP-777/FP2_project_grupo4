package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.util.*;
import javax.swing.SwingConstants;
import javax.swing.Timer; 
import javax.swing.JOptionPane; 
import java.util.List;

public class Game extends javax.swing.JFrame implements KeyListener {
    private static final int NUM_COLUMNS = 3;
    private static final int duracionJuego = 30000; //TIEMPO DE JUEGO EN MILISEGUNDOS (30 SEGUNDOS)
    private int columnWidth;
    private int correctAnswer;
    private Hitbox hitboxPlayer1;
    private Hitbox hitboxPlayer2;
    private Hitbox hitboxPlayer3;
    private Hitbox hitboxPanel4;
    private Hitbox hitboxPanel5;
    private Hitbox hitboxPanel6;
    private Timer timer, player1Timer, player2Timer, player3Timer, tiempoGame;
    
    private ImageIcon[] player1Sprites, player2Sprites, player3Sprites;
    private int player1Frame, player2Frame, player3Frame;
    
    private int player1Score = 0;
    private int player2Score = 0;
    private int player3Score = 0;

    public Game() {
        initComponents();
        customizeComponents();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        calculateColumns();
        startAnimation();
        
        hitboxPlayer1 = new Hitbox(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight());
        hitboxPlayer2 = new Hitbox(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight());
        hitboxPlayer3 = new Hitbox(player3.getX(), player3.getY(), player3.getWidth(), player3.getHeight());
        
        hitboxPanel4 = new Hitbox(answerPanel.getX(), answerPanel.getY(), answerPanel.getWidth(), answerPanel.getHeight());
        hitboxPanel5 = new Hitbox(answerPanel1.getX(), answerPanel1.getY(), answerPanel1.getWidth(), answerPanel1.getHeight());
        hitboxPanel6 = new Hitbox(answerPanel2.getX(), answerPanel2.getY(), answerPanel2.getWidth(), answerPanel2.getHeight());
        
        generateRandomSumAndAnswers();
        
        timer = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePanel(answerPanel, hitboxPanel4, 0, -2);
                movePanel(answerPanel1, hitboxPanel5, 0, -2);
                movePanel(answerPanel2, hitboxPanel6, 0, -2);
                checkCollisions();
            }
        });
        timer.start();
        
        // TEMPORIZADOR
        tiempoGame = new Timer(duracionJuego, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finJuego();
            }
        });
        tiempoGame.setRepeats(false);
        tiempoGame.start();
    }
    
    private void finJuego() {
        player1Timer.stop();
        player2Timer.stop();
        player3Timer.stop();
        timer.stop();
        
        String winner = "EMPATE";
        if (player1Score > player2Score && player1Score > player3Score) {
            winner = "JUGADOR1";
        } else if (player2Score > player1Score && player2Score > player3Score) {
            winner = "JUGADOR2";
        } else if (player3Score > player1Score && player3Score > player2Score) {
            winner = "JUGADOR3";
        }

        // Mostrar mensaje de ganador
        JOptionPane.showMessageDialog(this, "> EL GANADOR ES : " + winner, "SE ACABÓ EL TIEMPO", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        leftPanel = new javax.swing.JPanel();
        bg = new javax.swing.JPanel();
        rightPanel = new javax.swing.JPanel();
        suma = new javax.swing.JLabel();
        puesto = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        mapRacers = new javax.swing.JLabel();
        map = new javax.swing.JLabel();
        gamePanel = new javax.swing.JPanel();
        background = new javax.swing.JLabel();
        player1 = new javax.swing.JLabel();
        player2 = new javax.swing.JLabel();
        player3 = new javax.swing.JLabel();
        answerPanel = new javax.swing.JPanel();
        answer = new javax.swing.JLabel();
        answerPanel1 = new javax.swing.JPanel();
        answer1 = new javax.swing.JLabel();
        answerPanel2 = new javax.swing.JPanel();
        answer2 = new javax.swing.JLabel();

        jLabel5.setText("Aqui aun no se que puede ir");

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(204, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        suma.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        suma.setText("                             SUMA");
        suma.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        puesto.setText("PUESTOS");
        puesto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        scoreLabel.setText("PUNTAJES");
        scoreLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        mapRacers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minigatos.png"))); // NOI18N
        mapRacers.setMinimumSize(new java.awt.Dimension(100, 300));

        map.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/meta.png"))); // NOI18N
        map.setAlignmentX(0.5F);
        map.setMinimumSize(new java.awt.Dimension(200, 100));
        map.setPreferredSize(new java.awt.Dimension(696, 100));

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scoreLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(suma, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(puesto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(map, javax.swing.GroupLayout.PREFERRED_SIZE, 95, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mapRacers, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(suma, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(puesto, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(map, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(mapRacers, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        bg.add(rightPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 280, 520));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.png"))); // NOI18N

        player1.setBackground(new java.awt.Color(255, 255, 255));
        player1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player1.png"))); // NOI18N
        player1.setText("jLabel1");
        player1.setMaximumSize(new java.awt.Dimension(200, 200));
        player1.setMinimumSize(new java.awt.Dimension(200, 200));
        player1.setName(""); // NOI18N
        player1.setPreferredSize(new java.awt.Dimension(200, 200));
        player1.setRequestFocusEnabled(false);
        player1.setVerifyInputWhenFocusTarget(false);

        player2.setBackground(new java.awt.Color(255, 255, 255));
        player2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player2.png"))); // NOI18N
        player2.setText("jLabel1");
        player2.setMaximumSize(new java.awt.Dimension(242, 200));
        player2.setMinimumSize(new java.awt.Dimension(242, 200));
        player2.setName(""); // NOI18N
        player2.setPreferredSize(new java.awt.Dimension(242, 200));
        player2.setRequestFocusEnabled(false);
        player2.setVerifyInputWhenFocusTarget(false);

        player3.setBackground(new java.awt.Color(255, 255, 255));
        player3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player3.png"))); // NOI18N
        player3.setText("jLabel1");
        player3.setMaximumSize(new java.awt.Dimension(242, 200));
        player3.setMinimumSize(new java.awt.Dimension(100, 200));
        player3.setName(""); // NOI18N
        player3.setPreferredSize(new java.awt.Dimension(242, 200));
        player3.setRequestFocusEnabled(false);
        player3.setVerifyInputWhenFocusTarget(false);

        answerPanel.setBackground(new java.awt.Color(255, 0, 102));
        answerPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        answerPanel.setToolTipText("RESPUESTA");

        answer.setText("RESPUESTA");
        answer.setMaximumSize(new java.awt.Dimension(242, 200));
        answer.setMinimumSize(new java.awt.Dimension(242, 200));
        answer.setName(""); // NOI18N
        answer.setPreferredSize(new java.awt.Dimension(242, 200));
        answer.setRequestFocusEnabled(false);
        answer.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout answerPanelLayout = new javax.swing.GroupLayout(answerPanel);
        answerPanel.setLayout(answerPanelLayout);
        answerPanelLayout.setHorizontalGroup(
            answerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, answerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(answer, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        answerPanelLayout.setVerticalGroup(
            answerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, answerPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(answer, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        answerPanel1.setBackground(new java.awt.Color(255, 0, 0));
        answerPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        answerPanel1.setToolTipText("RESPUESTA");

        answer1.setText("RESPUESTA");
        answer1.setMaximumSize(new java.awt.Dimension(242, 200));
        answer1.setMinimumSize(new java.awt.Dimension(242, 200));
        answer1.setName(""); // NOI18N
        answer1.setPreferredSize(new java.awt.Dimension(242, 200));
        answer1.setRequestFocusEnabled(false);
        answer1.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout answerPanel1Layout = new javax.swing.GroupLayout(answerPanel1);
        answerPanel1.setLayout(answerPanel1Layout);
        answerPanel1Layout.setHorizontalGroup(
            answerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, answerPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(answer1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        answerPanel1Layout.setVerticalGroup(
            answerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, answerPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(answer1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        answerPanel2.setBackground(new java.awt.Color(255, 51, 51));
        answerPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        answerPanel2.setToolTipText("RESPUESTA");

        answer2.setText("RESPUESTA");
        answer2.setMaximumSize(new java.awt.Dimension(242, 200));
        answer2.setMinimumSize(new java.awt.Dimension(242, 200));
        answer2.setName(""); // NOI18N
        answer2.setPreferredSize(new java.awt.Dimension(242, 200));
        answer2.setRequestFocusEnabled(false);
        answer2.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout answerPanel2Layout = new javax.swing.GroupLayout(answerPanel2);
        answerPanel2.setLayout(answerPanel2Layout);
        answerPanel2Layout.setHorizontalGroup(
            answerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, answerPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(answer2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        answerPanel2Layout.setVerticalGroup(
            answerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, answerPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(answer2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(answerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(player3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(answerPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(player2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(player1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(answerPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
            .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(gamePanelLayout.createSequentialGroup()
                    .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(player2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(player3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 303, Short.MAX_VALUE)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(answerPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(answerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(answerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
            .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(gamePanelLayout.createSequentialGroup()
                    .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        player1.getAccessibleContext().setAccessibleName("player");

        bg.add(gamePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void customizeComponents() {
        // Redimensionar la imagen y establecerla en jLabel1
        player1Sprites = loadSprites("/images/player1/1.", 4); // 4 frames de animación
        player2Sprites = loadSprites("/images/player2/2.", 4);
        player3Sprites = loadSprites("/images/player3/3.", 4);
        
        player1Frame = 0;
        player2Frame = 0;
        player3Frame = 0;
        
        player1.setIcon(player1Sprites[player1Frame]);
        player2.setIcon(player2Sprites[player2Frame]);
        player3.setIcon(player3Sprites[player3Frame]);
        
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/background.png"));
        Image backgroundImage = backgroundIcon.getImage();
        Image scaledImagebg = backgroundImage.getScaledInstance(background.getWidth(), background.getHeight(), Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(scaledImagebg));

        
        // JP777: En caso de que las animaciones de arriba no funcionen por alguna razon usar este codigo de imagenes estaticas
        /*
        ImageIcon player1Icon = new ImageIcon(getClass().getResource("/images/player1.png"));
        ImageIcon player2Icon = new ImageIcon(getClass().getResource("/images/player2.png"));
        ImageIcon player3Icon = new ImageIcon(getClass().getResource("/images/player3.png"));
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/background.png"));
        Image player1Image = player1Icon.getImage();
        Image player2Image = player2Icon.getImage();
        Image player3Image = player3Icon.getImage();
        Image backgroundImage = backgroundIcon.getImage();
        Image scaledImagep1 = player1Image.getScaledInstance(player1.getWidth(), player1.getHeight(), Image.SCALE_SMOOTH);
        Image scaledImagep2 = player2Image.getScaledInstance(player2.getWidth(), player2.getHeight(), Image.SCALE_SMOOTH);
        Image scaledImagep3 = player3Image.getScaledInstance(player3.getWidth(), player3.getHeight(), Image.SCALE_SMOOTH);
        Image scaledImagebg = backgroundImage.getScaledInstance(background.getWidth(), background.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIconp1 = new ImageIcon(scaledImagep1);
        ImageIcon scaledIconp2 = new ImageIcon(scaledImagep2);
        ImageIcon scaledIconp3 = new ImageIcon(scaledImagep3);
        ImageIcon scaledIconbg = new ImageIcon(scaledImagebg);
        player1.setIcon(scaledIconp1);
        player2.setIcon(scaledIconp2);
        player3.setIcon(scaledIconp3);
        background.setIcon(scaledIconbg);
*/
    Font letra = new Font("Arial", Font.BOLD, 18); 
    Font titulo = new Font("Arial", Font.BOLD, 30); 
    
    answer.setFont(titulo);
    answer.setHorizontalAlignment(SwingConstants.CENTER);
    
    answer1.setFont(titulo);
    answer1.setHorizontalAlignment(SwingConstants.CENTER);
    
    answer2.setFont(titulo);
    answer2.setHorizontalAlignment(SwingConstants.CENTER);
    
    suma.setFont(titulo);
    suma.setHorizontalAlignment(SwingConstants.CENTER);
    
    scoreLabel.setFont(letra);
    scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
    puesto.setFont(letra);
    puesto.setHorizontalAlignment(SwingConstants.CENTER);
    
    map.setFont(letra);
    map.setHorizontalAlignment(SwingConstants.CENTER); 
    }
    
    private ImageIcon[] loadSprites(String pathPrefix, int frameCount) {
        
        ImageIcon[] sprites = new ImageIcon[frameCount];
        for (int i = 0; i < frameCount; i++) {
            ImageIcon frame = new ImageIcon(getClass().getResource(pathPrefix + (i+1) + ".png"));
            ImageIcon resizedFrame = ImageUtils.resizeImageIcon(frame, 150, 150);
            sprites[i] = resizedFrame;
        }
        return sprites;
    }
    
    private void startAnimation() {
        player1Timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1Frame = (player1Frame + 1) % player1Sprites.length;
                player1.setIcon(player1Sprites[player1Frame]);
            }
        });
        player2Timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player2Frame = (player2Frame + 1) % player2Sprites.length;
                player2.setIcon(player2Sprites[player2Frame]);
            }
        });
        player3Timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player3Frame = (player3Frame + 1) % player3Sprites.length;
                player3.setIcon(player3Sprites[player3Frame]);
            }
        });

        player1Timer.start();
        player2Timer.start();
        player3Timer.start();
    }
    
    private void generateRandomSumAndAnswers() {
        Random rand = new Random();
        int num1 = rand.nextInt(21);
        int num2 = rand.nextInt(21);
        correctAnswer = num1 + num2;

        suma.setText(num1 + " + " + num2);

        int correctPanel = rand.nextInt(3);
        int wrongAnswer1 = correctAnswer + rand.nextInt(5) + 1;
        int wrongAnswer2 = correctAnswer - (rand.nextInt(5) + 1);

        switch (correctPanel) {
            case 0:
                answer.setText(String.valueOf(correctAnswer));
                answer1.setText(String.valueOf(wrongAnswer1));
                answer2.setText(String.valueOf(wrongAnswer2));
                break;
            case 1:
                answer.setText(String.valueOf(wrongAnswer1));
                answer1.setText(String.valueOf(correctAnswer));
                answer2.setText(String.valueOf(wrongAnswer2));
                break;
            case 2:
                answer.setText(String.valueOf(wrongAnswer2));
                answer1.setText(String.valueOf(wrongAnswer1));
                answer2.setText(String.valueOf(correctAnswer));
                break;
        }
    }
    
    private void actualizarPuntajes() {
        scoreLabel.setText("<html><div style='text-align: center;'>▸ PUNTAJES: ◂<br>"
                + "◦ JUGADOR1: " + player1Score + "<br>"
                + "◦ JUGADOR2: " + player2Score + "<br>"
                + "◦ JUGADOR3: " + player3Score + "</div></html>");
    }
    
    private void actualizarPuestos() {
        // Crear listas de jugadores y puntuaciones
        List<Player> players = new ArrayList<>();
        players.add(new Player("JUGADOR1", player1Score));
        players.add(new Player("JUGADOR2", player2Score));
        players.add(new Player("JUGADOR3", player3Score));

        // Ordenar la lista en función de las puntuaciones, de mayor a menor
        players.sort((p1, p2) -> Integer.compare(p2.score, p1.score));

        // Crear el texto de la tabla de puestos
        StringBuilder puestosText = new StringBuilder("<html><div style='text-align: center;'>▸ PUESTOS: ◂<br>");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            puestosText.append("").append(i + 1).append("° : ").append(player.name)
                .append(" (").append(player.score).append(" puntos)<br>");
        }
        puestosText.append("</div></html>");

        // Actualizar el texto en la etiqueta de la tabla de puestos
        puesto.setText(puestosText.toString());
    }

    private static class Player {
        String name;
        int score;

        Player(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    private void movePlayer(javax.swing.JLabel player, Hitbox hitbox, int dx, int dy) {
        int auxX = player.getX();
        int auxY = player.getY();

        int x = player.getX() + dx;
        int y = player.getY() + dy;

        if (x < 0) x = auxX;
        if (x + player.getWidth() > 650) x = auxX;
        if (y < 0) y = auxY;
        if (y + player.getHeight() > bg.getHeight()) y = auxY;

        player.setLocation(x, y);
        hitbox.update(x, y);
    }
    
        private void movePanel(javax.swing.JPanel panel, Hitbox hitbox, int dx, int dy) {
        int auxX = panel.getX();
        int auxY = panel.getY();

        int x = panel.getX() + dx;
        int y = panel.getY() + dy;

        if (x < 0) x = auxX;
        if (x + panel.getWidth() > bg.getWidth()) x = auxX;
        if (y < 0) y = bg.getHeight();  // Reset panel to bottom if it moves off the top
        if (y + panel.getHeight() > bg.getHeight()) y = auxY;

        panel.setLocation(x, y);
        hitbox.update(x, y);
    }

    private void checkCollisions() {
        if (hitboxPlayer1.intersects(hitboxPanel4) && answer.getText().equals(String.valueOf(correctAnswer))) {
            player1Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generateRandomSumAndAnswers();
        } else if (hitboxPlayer1.intersects(hitboxPanel5) && answer1.getText().equals(String.valueOf(correctAnswer))) {
            player1Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generateRandomSumAndAnswers();
        } else if (hitboxPlayer1.intersects(hitboxPanel6) && answer2.getText().equals(String.valueOf(correctAnswer))) {
            player1Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generateRandomSumAndAnswers();
        }

        if (hitboxPlayer2.intersects(hitboxPanel4) && answer.getText().equals(String.valueOf(correctAnswer))) {
            player2Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generateRandomSumAndAnswers();
        } else if (hitboxPlayer2.intersects(hitboxPanel5) && answer1.getText().equals(String.valueOf(correctAnswer))) {
            player2Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generateRandomSumAndAnswers();
        } else if (hitboxPlayer2.intersects(hitboxPanel6) && answer2.getText().equals(String.valueOf(correctAnswer))) {
            player2Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generateRandomSumAndAnswers();
        }

        if (hitboxPlayer3.intersects(hitboxPanel4) && answer.getText().equals(String.valueOf(correctAnswer))) {
            player3Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generateRandomSumAndAnswers();
        } else if (hitboxPlayer3.intersects(hitboxPanel5) && answer1.getText().equals(String.valueOf(correctAnswer))) {
            player3Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generateRandomSumAndAnswers();
        } else if (hitboxPlayer3.intersects(hitboxPanel6) && answer2.getText().equals(String.valueOf(correctAnswer))) {
            player3Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generateRandomSumAndAnswers();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // JUGADOR 1 - A Y D
        if (key == KeyEvent.VK_A) {
            movePlayer(player1, hitboxPlayer1, -columnWidth, 0);
        } else if (key == KeyEvent.VK_D) {
            movePlayer(player1, hitboxPlayer1, +columnWidth, 0);
        }

        // JUGADOR 2 - J Y L
        if (key == KeyEvent.VK_J) {
            movePlayer(player2, hitboxPlayer2, -columnWidth, 0);
        } else if (key == KeyEvent.VK_L) {
            movePlayer(player2, hitboxPlayer2, +columnWidth, 0);
        }

        // JUGADOR 3 - IZQUIERDA Y DERECHA
        if (key == KeyEvent.VK_LEFT) {
            movePlayer(player3, hitboxPlayer3, -columnWidth, 0);
        } else if (key == KeyEvent.VK_RIGHT) {
            movePlayer(player3, hitboxPlayer3, +columnWidth, 0);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    
    private void calculateColumns() {
        int panelWidth = gamePanel.getWidth();
        columnWidth = panelWidth / NUM_COLUMNS;
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel answer;
    private javax.swing.JLabel answer1;
    private javax.swing.JLabel answer2;
    private javax.swing.JPanel answerPanel;
    private javax.swing.JPanel answerPanel1;
    private javax.swing.JPanel answerPanel2;
    private javax.swing.JLabel background;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JLabel map;
    private javax.swing.JLabel mapRacers;
    private javax.swing.JLabel player1;
    private javax.swing.JLabel player2;
    private javax.swing.JLabel player3;
    private javax.swing.JLabel puesto;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel suma;
    // End of variables declaration//GEN-END:variables
}
