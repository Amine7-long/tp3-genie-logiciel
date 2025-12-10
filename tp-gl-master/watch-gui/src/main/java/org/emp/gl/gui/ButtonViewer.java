package org.emp.gl.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interface graphique contenant les boutons SET et MODE.
 * Communique avec WatchViewer pour déclencher les actions.
 *
 * @author Amine - TP3
 */
public class ButtonViewer extends JFrame {

    private final WatchViewerGUI watchViewer;
    private JButton jButton1;
    private JButton jButton2;

    /**
     * Constructeur avec référence vers le viewer de la montre.
     *
     * @param watchViewer l'interface de la montre
     */
    public ButtonViewer(WatchViewerGUI watchViewer) {
        this.watchViewer = watchViewer;
        initComponents();
    }

    /**
     * Initialise les composants Swing.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLocation(200, 200);
        setVisible(true);
        setTitle("🎮 Contrôles");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(30, 30, 35));

        GridBagConstraints gbc;

        // Bouton SET
        jButton1 = new JButton("SET");
        jButton1.setFont(new Font("Consolas", Font.BOLD, 32));
        jButton1.setBackground(new Color(70, 130, 180));
        jButton1.setForeground(Color.WHITE);
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Action du bouton SET
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                watchViewer.doSet();

                // Animation visuelle
                jButton1.setBackground(new Color(50, 110, 160));
                Timer timer = new Timer(100, e ->
                        jButton1.setBackground(new Color(70, 130, 180)));
                timer.setRepeats(false);
                timer.start();
            }
        });

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(21, 2, 21, 2);
        getContentPane().add(jButton1, gbc);

        // Bouton MODE
        jButton2 = new JButton("MODE");
        jButton2.setFont(new Font("Consolas", Font.BOLD, 32));
        jButton2.setBackground(new Color(220, 100, 70));
        jButton2.setForeground(Color.WHITE);
        jButton2.setFocusPainted(false);
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Action du bouton MODE
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                watchViewer.doMode();

                // Animation visuelle
                jButton2.setBackground(new Color(200, 80, 50));
                Timer timer = new Timer(100, e ->
                        jButton2.setBackground(new Color(220, 100, 70)));
                timer.setRepeats(false);
                timer.start();
            }
        });

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(21, 2, 21, 2);
        getContentPane().add(jButton2, gbc);

        // Instructions
        JLabel instructions = new JLabel("<html><center>" +
                "SET: Basculer affichage / Start-Stop<br>" +
                "MODE: Changer de mode / Reset</center></html>");
        instructions.setFont(new Font("Arial", Font.PLAIN, 12));
        instructions.setForeground(new Color(200, 200, 200));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(instructions, gbc);

        pack();
    }
}