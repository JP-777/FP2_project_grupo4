package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.util.List;

public class Game extends javax.swing.JFrame implements KeyListener {
    private static final int NUM_COLUMNS = 3;
    private static final int GAME_DURATION_MS = 30000; // TIEMPO DE LA CARRERA EN MILISEGUNDOS (30 SEGUNDOS)
    private static final int NUM_QUESTIONS = 5; // NUMERO DE PREGUNTAS EN EL REPASO
    //REPASO
    private int currentQuestion = 0; 
    private int reviewScore = 0;
    private List<JButton> answerButtons;
    private JDialog reviewDialog;
    private JPanel reviewPanel; //REPASO
    private int columnWidth;
    private int correctAnswer;
    private Hitbox hitboxPlayer1;
    private Hitbox hitboxPlayer2;
    private Hitbox hitboxPlayer3;
    private Hitbox hitboxPanel4;
    private Hitbox hitboxPanel5;
    private Hitbox hitboxPanel6;
    private Timer timer, player1Timer, player2Timer, player3Timer, gameTimer;
    
    private ImageIcon[] player1Sprites, player2Sprites, player3Sprites;
    private int player1Frame, player2Frame, player3Frame;
    
    private int player1Score = 0;
    private int player2Score = 0;
    private int player3Score = 0;

    public Game() {
        initComponents();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        customizeComponents();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        calcularColumnas();
        
        // MOSTRAR LA VENTANA PRINCIPAL
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
                mensajeInicio();
            }
        });
    }

    private void mensajeInicio() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "¡LA CARRERA VA A COMENZAR!"
                        + "\nJUGADOR 1 : Gato Marrón (A/D)"
                        + "\nJUGADOR 2 : Gato Naranja (J/L)"
                        + "\nJUGADOR 3 : Gato Blanco (Derecha/Izquierda)" ,
                "😺 CARRERA DE GATOS 😺",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );
        if (response == JOptionPane.OK_OPTION) {
            comenzarJuego();
        }
    }

    private void comenzarJuego() {
        comenzarAnimacion();

        hitboxPlayer1 = new Hitbox(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight());
        hitboxPlayer2 = new Hitbox(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight());
        hitboxPlayer3 = new Hitbox(player3.getX(), player3.getY(), player3.getWidth(), player3.getHeight());

        hitboxPanel4 = new Hitbox(answerPanel.getX(), answerPanel.getY(), answerPanel.getWidth(), answerPanel.getHeight());
        hitboxPanel5 = new Hitbox(answerPanel1.getX(), answerPanel1.getY(), answerPanel1.getWidth(), answerPanel1.getHeight());
        hitboxPanel6 = new Hitbox(answerPanel2.getX(), answerPanel2.getY(), answerPanel2.getWidth(), answerPanel2.getHeight());

        generarSumasRespuestas();

        timer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverPanel(answerPanel, hitboxPanel4, 0, -2);
                moverPanel(answerPanel1, hitboxPanel5, 0, -2);
                moverPanel(answerPanel2, hitboxPanel6, 0, -2);
                colisionGato();
            }
        });
        timer.start();

        // Temporizador para el juego
        gameTimer = new Timer(GAME_DURATION_MS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finJuego();
            }
        });
        gameTimer.setRepeats(false); // El temporizador solo debe ejecutarse una vez
        gameTimer.start();
    }
    
    // TERMINAR EL JUEGO LUEGO DE QUE SE HAYA TERMINADO EL TIEMPO
    private void finJuego() {
        // DETENER TODAS LAS ANIMACIONES
        player1Timer.stop();
        player2Timer.stop();
        player3Timer.stop();
        timer.stop();
        gameTimer.stop();

        // DETERMINAR EL JUGADOR CON LA PUNTUACIÓN MAS ALTA
        String winner = "EMPATE ENTRE GATOS";
        if (player1Score > player2Score && player1Score > player3Score) {
            winner = "JUGADOR1";
        } else if (player2Score > player1Score && player2Score > player3Score) {
            winner = "JUGADOR2";
        } else if (player3Score > player1Score && player3Score > player2Score) {
            winner = "JUGADOR3";
        }

        // MOSTRAR EL GANADOR
        JOptionPane.showMessageDialog(this, "EL GANADOR ES : " + winner, "😺 FIN DEL JUEGO 😺", JOptionPane.INFORMATION_MESSAGE);

        // Mostrar opción para repasar
        int response = JOptionPane.showConfirmDialog(
                this,
                "Eso fue divertido. ¡Repasemos lo aprendido!",
                "😺 REPASEMOS LO APRENDIDO 😺",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );

        if (response == JOptionPane.OK_OPTION) {
            formularioExamen();
        }
    }
    
    //FORMULARIO TIPO EXAMEN CON (5) PREGUNTAS, PARA REPASAR LO APRENDIDO
    private void formularioExamen() {
        currentQuestion = 0;
        reviewScore = 0;
        
        reviewDialog = new JDialog(this, "😺 REPASO 😺", true);
        reviewDialog.setSize(400, 300);
        reviewDialog.setLocationRelativeTo(this);
        
        reviewPanel = new JPanel();
        reviewPanel.setLayout(new BorderLayout());
        
        reviewDialog.add(reviewPanel);
        proximaPregunta();
        
        reviewDialog.setVisible(true);
    }

    private void proximaPregunta() {
        if (currentQuestion >= NUM_QUESTIONS) {
            finExamen();
            return;
        }

        reviewPanel.removeAll();
        
        Random r = new Random();
        int num1 = r.nextInt(21);
        int num2 = r.nextInt(21);
        int correctAnswer = num1 + num2;

        // CREA UN PANEL CON 3 BOTONES DE RESPUESTA
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new GridLayout(4, 1));

        JLabel questionLabel = new JLabel(num1 + " + " + num2 + " = ?");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionPanel.add(questionLabel);

        answerButtons = new ArrayList<>();
        int correctButtonIndex = r.nextInt(3);
        int[] answers = new int[3];
        answers[correctButtonIndex] = correctAnswer;
        for (int i = 0; i < 3; i++) {
            if (i != correctButtonIndex) {
                answers[i] = correctAnswer + r.nextInt(5) + 1;
            }
        }
        
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        for (int i = 0; i < 3; i++) {
            JButton button = new JButton(String.valueOf(answers[i]));
            button.setFont(buttonFont);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Integer.parseInt(button.getText()) == correctAnswer) {
                        reviewScore++;
                    }
                    currentQuestion++;
                    proximaPregunta();
                }
            });
            answerButtons.add(button);
            questionPanel.add(button);
        }

        reviewPanel.add(questionPanel, BorderLayout.CENTER);
        reviewPanel.revalidate();
        reviewPanel.repaint();
    }

    private void finExamen() {
    reviewDialog.dispose(); 

    //VERIFICA EL PUNTAJE DEL EXAMEN
    String resultMessage;
    if (reviewScore >= 3) {
        resultMessage = "Wow, ¡BIEN HECHO!";
    } else {
        resultMessage = "Oh... Más suerte a la proxima.";
    }

    // Muestra el resultado final
    JOptionPane.showMessageDialog(this,
            "Veamos tu resultado... " + reviewScore + "/" + NUM_QUESTIONS + "\n" + resultMessage,
            "😺 RESULTADOS 😺 ",
            JOptionPane.INFORMATION_MESSAGE
    );
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

        rightPanel.setBackground(new java.awt.Color(152, 205, 54));
        rightPanel.setForeground(new java.awt.Color(204, 255, 204));
        rightPanel.setEnabled(false);

        suma.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        suma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        suma.setText("SUMA");

        puesto.setForeground(new java.awt.Color(0, 0, 0));
        puesto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puesto.setText("PUESTOS");
        puesto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        scoreLabel.setForeground(new java.awt.Color(0, 0, 0));
        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel.setText("PUNTAJES");
        scoreLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        map.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        map.setText("TIEMPO : ");
        map.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scoreLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(suma, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
            .addComponent(puesto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(map, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(suma, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(puesto, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(map, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addContainerGap())
        );

        bg.add(rightPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 290, 520));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.png"))); // NOI18N

        player1.setBackground(new java.awt.Color(255, 255, 255));
        player1.setForeground(new java.awt.Color(0, 0, 0));
        player1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player1.png"))); // NOI18N
        player1.setText("j1");
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

        answer.setText("0");
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

        answer1.setText("0");
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

        answer2.setText("0");
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
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(answerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(player3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(answerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(player2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(answerPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))))
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
                    .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(player2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(player3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 318, Short.MAX_VALUE)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(answerPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(answerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(answerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
            .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(gamePanelLayout.createSequentialGroup()
                    .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        player1.getAccessibleContext().setAccessibleName("player");

        bg.add(gamePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void customizeComponents() {
        // Redimensionar la imagen y establecerla en jLabel1
        
        player1Sprites = cargarSprites("/images/player1/1.", 4); // 4 frames de animación
        player2Sprites = cargarSprites("/images/player2/2.", 4);
        player3Sprites = cargarSprites("/images/player3/3.", 4);
        
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
    
    private ImageIcon[] cargarSprites(String pathPrefix, int frameCount) {
        
        ImageIcon[] sprites = new ImageIcon[frameCount];
        for (int i = 0; i < frameCount; i++) {
            ImageIcon frame = new ImageIcon(getClass().getResource(pathPrefix + (i+1) + ".png"));
            ImageIcon resizedFrame = ImageUtils.resizeImageIcon(frame, 150, 150);
            sprites[i] = resizedFrame;
        }
        return sprites;
    }
    
    private void comenzarAnimacion() {
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
    
    private void generarSumasRespuestas() {
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

    private void moverGato(javax.swing.JLabel player, Hitbox hitbox, int dx, int dy) {
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
    
        private void moverPanel(javax.swing.JPanel panel, Hitbox hitbox, int dx, int dy) {
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

    private void colisionGato() {
        if (hitboxPlayer1.intersects(hitboxPanel4) && answer.getText().equals(String.valueOf(correctAnswer))) {
            player1Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generarSumasRespuestas();
        } else if (hitboxPlayer1.intersects(hitboxPanel5) && answer1.getText().equals(String.valueOf(correctAnswer))) {
            player1Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generarSumasRespuestas();
        } else if (hitboxPlayer1.intersects(hitboxPanel6) && answer2.getText().equals(String.valueOf(correctAnswer))) {
            player1Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generarSumasRespuestas();
        }

        if (hitboxPlayer2.intersects(hitboxPanel4) && answer.getText().equals(String.valueOf(correctAnswer))) {
            player2Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generarSumasRespuestas();
        } else if (hitboxPlayer2.intersects(hitboxPanel5) && answer1.getText().equals(String.valueOf(correctAnswer))) {
            player2Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generarSumasRespuestas();
        } else if (hitboxPlayer2.intersects(hitboxPanel6) && answer2.getText().equals(String.valueOf(correctAnswer))) {
            player2Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generarSumasRespuestas();
        }

        if (hitboxPlayer3.intersects(hitboxPanel4) && answer.getText().equals(String.valueOf(correctAnswer))) {
            player3Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generarSumasRespuestas();
        } else if (hitboxPlayer3.intersects(hitboxPanel5) && answer1.getText().equals(String.valueOf(correctAnswer))) {
            player3Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generarSumasRespuestas();
        } else if (hitboxPlayer3.intersects(hitboxPanel6) && answer2.getText().equals(String.valueOf(correctAnswer))) {
            player3Score++;
            actualizarPuntajes();
            actualizarPuestos();
            generarSumasRespuestas();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // JUGADOR 1 - A Y D
        if (key == KeyEvent.VK_A) {
            moverGato(player1, hitboxPlayer1, -columnWidth, 0);
        } else if (key == KeyEvent.VK_D) {
            moverGato(player1, hitboxPlayer1, +columnWidth, 0);
        }

        // JUGADOR 2 - J Y L
        if (key == KeyEvent.VK_J) {
            moverGato(player2, hitboxPlayer2, -columnWidth, 0);
        } else if (key == KeyEvent.VK_L) {
            moverGato(player2, hitboxPlayer2, +columnWidth, 0);
        }

        // JUGADOR 3 - IZQUIERDA Y DERECHA
        if (key == KeyEvent.VK_LEFT) {
            moverGato(player3, hitboxPlayer3, -columnWidth, 0);
        } else if (key == KeyEvent.VK_RIGHT) {
            moverGato(player3, hitboxPlayer3, +columnWidth, 0);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    
    private void calcularColumnas() {
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
    private javax.swing.JLabel player1;
    private javax.swing.JLabel player2;
    private javax.swing.JLabel player3;
    private javax.swing.JLabel puesto;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel suma;
    // End of variables declaration//GEN-END:variables
}
