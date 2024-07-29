package Game;

import java.awt.*;
import javax.swing.*;

public class Menu extends javax.swing.JFrame {
    ImagenFondo gatoMenu = new ImagenFondo();
    public Menu() {
        this.setContentPane(gatoMenu);
        initComponents();
        setSize(768, 432);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    class ImagenFondo extends JPanel {
        private Image fondo;
        public void paint (Graphics grafico){
            fondo = new ImageIcon(getClass().getResource("/images/MENU.png")).getImage();
            grafico.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(grafico);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        gatoMarron = new javax.swing.JButton();
        gatoBlanco = new javax.swing.JButton();
        gatoNaranja = new javax.swing.JButton();
        botonInicio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setOpaque(false);

        gatoMarron.setBackground(new java.awt.Color(102, 51, 0));
        gatoMarron.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        gatoMarron.setForeground(new java.awt.Color(255, 255, 255));
        gatoMarron.setText("Gato Marrón");
        gatoMarron.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gatoMarronActionPerformed(evt);
            }
        });

        gatoBlanco.setBackground(new java.awt.Color(204, 204, 204));
        gatoBlanco.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        gatoBlanco.setText("Gato Blanco");

        gatoNaranja.setBackground(new java.awt.Color(255, 102, 0));
        gatoNaranja.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        gatoNaranja.setText("Gato Naranja");

        botonInicio.setBackground(new java.awt.Color(204, 204, 204));
        botonInicio.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        botonInicio.setForeground(new java.awt.Color(0, 0, 0));
        botonInicio.setText("< COMENZAR JUEGO >");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(198, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(gatoBlanco)
                        .addGap(50, 50, 50)
                        .addComponent(gatoMarron)
                        .addGap(39, 39, 39)
                        .addComponent(gatoNaranja)
                        .addGap(87, 87, 87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(botonInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(152, 152, 152))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gatoNaranja)
                    .addComponent(gatoMarron)
                    .addComponent(gatoBlanco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                .addComponent(botonInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(144, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gatoMarronActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gatoMarronActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gatoMarronActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonInicio;
    private javax.swing.JButton gatoBlanco;
    private javax.swing.JButton gatoMarron;
    private javax.swing.JButton gatoNaranja;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    
}
