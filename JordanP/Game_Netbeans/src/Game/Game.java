package Game;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Game extends javax.swing.JFrame {

    public Game() {
        initComponents();
        customizeComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        player1 = new javax.swing.JLabel();
        player2 = new javax.swing.JLabel();
        player3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(204, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        player1.setBackground(new java.awt.Color(255, 255, 255));
        player1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player1.png"))); // NOI18N
        player1.setText("jLabel1");
        player1.setMaximumSize(new java.awt.Dimension(242, 200));
        player1.setMinimumSize(new java.awt.Dimension(242, 200));
        player1.setName(""); // NOI18N
        player1.setPreferredSize(new java.awt.Dimension(242, 200));
        player1.setRequestFocusEnabled(false);
        player1.setVerifyInputWhenFocusTarget(false);
        bg.add(player1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 70, 70));
        player1.getAccessibleContext().setAccessibleName("player");

        player2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player2.png"))); // NOI18N
        player2.setText("jLabel1");
        bg.add(player2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 90));

        player3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player3.png"))); // NOI18N
        player3.setText("jLabel2");
        bg.add(player3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 90, 90));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 500));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        bg.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 130, 500));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.png"))); // NOI18N
        bg.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 520, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void customizeComponents() {
        // Redimensionar la imagen y establecerla en jLabel1
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
    private javax.swing.JLabel background;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel player1;
    private javax.swing.JLabel player2;
    private javax.swing.JLabel player3;
    // End of variables declaration//GEN-END:variables
}
