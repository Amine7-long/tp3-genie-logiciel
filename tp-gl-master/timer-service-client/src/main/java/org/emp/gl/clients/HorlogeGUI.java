package org.emp.gl.clients;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

/**
 * Horloge graphique simple qui affiche l'heure en temps réel
 * Utilise Swing pour l'interface graphique
 */
public class HorlogeGUI extends JFrame implements TimerChangeListener {

    private final JLabel timeLabel;
    private final JLabel dateLabel;
    private final JLabel nameLabel;
    private final SimpleDateFormat timeFormat;
    private final SimpleDateFormat dateFormat;
    private TimerService timerService;
    private String name;

    /**
     * Constructeur avec nom personnalisé
     * @param name le nom de l'horloge
     * @param timerService le service de temps
     */
    public HorlogeGUI(String name, TimerService timerService) {
        this.name = name;
        this.timerService = timerService;
        this.timeFormat = new SimpleDateFormat("HH:mm:ss");
        this.dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy");

        // Labels
        this.nameLabel = new JLabel(name, SwingConstants.CENTER);
        this.timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        this.dateLabel = new JLabel("", SwingConstants.CENTER);

        // Configuration de l'interface
        initializeGUI();

        // S'abonner au service de temps
        if (timerService != null) {
            timerService.addTimeChangeListener(this);
            updateTime(); // Mise à jour initiale
        }
    }

    /**
     * Constructeur simple (pour compatibilité)
     * @param timerService le service de temps
     */
    public HorlogeGUI(TimerService timerService) {
        this("Horloge", timerService);
    }

    /**
     * Initialise l'interface graphique
     */
    private void initializeGUI() {
        // Configuration de la fenêtre
        setTitle("🕒 " + name);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(30, 30, 40));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nom de l'horloge en haut
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(new Color(150, 150, 160));
        mainPanel.add(nameLabel, BorderLayout.NORTH);

        // Panel central pour l'heure et la date
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        centerPanel.setOpaque(false);

        // Label de l'heure (grand et lumineux)
        timeLabel.setFont(new Font("Consolas", Font.BOLD, 56));
        timeLabel.setForeground(new Color(0, 255, 150));
        centerPanel.add(timeLabel);

        // Label de la date
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dateLabel.setForeground(new Color(120, 180, 150));
        centerPanel.add(dateLabel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Indicateur de statut en bas
        JLabel statusLabel = new JLabel("● EN LIGNE", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(0, 255, 100));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Met à jour l'affichage de l'heure
     */
    private void updateTime() {
        Date now = new Date();
        timeLabel.setText(timeFormat.format(now));
        dateLabel.setText(dateFormat.format(now));
    }

    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // Mettre à jour l'affichage à chaque changement de seconde
        if (SECONDE_PROP.equals(prop)) {
            SwingUtilities.invokeLater(this::updateTime);
        }
    }

    /**
     * Définit le service de temps
     * @param service le nouveau service
     */
    public void setTimerService(TimerService service) {
        // Se désinscrire de l'ancien service
        if (this.timerService != null) {
            this.timerService.removeTimeChangeListener(this);
        }

        // S'inscrire au nouveau service
        this.timerService = service;
        if (service != null) {
            service.addTimeChangeListener(this);
            updateTime();
        }
    }

    /**
     * Obtient le nom de l'horloge
     * @return le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Ferme proprement la fenêtre
     */
    @Override
    public void dispose() {
        if (timerService != null) {
            timerService.removeTimeChangeListener(this);
        }
        super.dispose();
    }
}