package org.emp.gl.gui;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.Chronometre;
import org.emp.gl.watch.states.WatchContext;
import org.emp.gl.watch.states.WatchViewer;
import org.emp.gl.timer.service.TimerChangeListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * Interface graphique de la montre électronique.
 * Affiche l'heure/chronomètre avec 3 zones de texte + 1 indicateur de mode.
 *
 * Implémente le pattern State via WatchContext.
 *
 * NOTE: Classe renommée en WatchViewerGUI pour éviter le conflit avec
 * l'interface WatchViewer du package states.
 *
 * @author Amine - TP3
 */
public class WatchViewerGUI extends JFrame implements TimerChangeListener, WatchViewer {

    static int COUNT = 0;

    private final Horloge horloge;
    private final Chronometre chronometre;
    private final WatchContext watchContext;

    // Composants graphiques
    private final JLabel hh = new JLabel();
    private final JLabel mm = new JLabel();
    private final JLabel sep = new JLabel();
    private final JLabel mod = new JLabel();

    /**
     * Constructeur de la montre graphique.
     */
    public WatchViewerGUI() {
        // Créer l'horloge et le chronomètre
        horloge = new Horloge("Watch_" + COUNT);
        chronometre = new Chronometre("Chrono_" + COUNT);

        // Créer le contexte du State Pattern
        // this implémente l'interface WatchViewer
        watchContext = new WatchContext(horloge, chronometre, this);

        // Initialiser l'interface
        initComponents();

        // S'abonner au TimerService pour les ticks
        org.emp.gl.lookup.Lookup lookup = org.emp.gl.lookup.Lookup.getInstance();
        org.emp.gl.timer.service.TimerService service =
                lookup.getService(org.emp.gl.timer.service.TimerService.class);

        if (service != null) {
            service.addTimeChangeListener(this);
        }
    }

    /**
     * Initialise les composants Swing.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLocation(200 + COUNT++ * 300, 400);
        setVisible(true);
        setTitle("⌚ Montre Électronique");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(20, 20, 25));

        GridBagConstraints gbc;

        // Configuration de HH
        hh.setFont(new Font("Consolas", Font.BOLD, 48));
        hh.setText("00");
        hh.setForeground(new Color(0, 255, 150));
        getContentPane().add(hh, new GridBagConstraints());

        // Configuration du séparateur ":"
        sep.setFont(new Font("Consolas", Font.BOLD, 48));
        sep.setText(":");
        sep.setForeground(new Color(0, 255, 150));
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(14, 14, 14, 14);
        getContentPane().add(sep, gbc);

        // Configuration de MM
        mm.setFont(new Font("Consolas", Font.BOLD, 48));
        mm.setText("00");
        mm.setForeground(new Color(0, 255, 150));
        getContentPane().add(mm, new GridBagConstraints());

        // Configuration de l'indicateur de mode (T/C/S)
        mod.setFont(new Font("Consolas", Font.BOLD, 24));
        mod.setText("T");
        mod.setForeground(new Color(255, 200, 0));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 0);
        getContentPane().add(mod, gbc);

        pack();
    }

    // === MÉTHODES APPELÉES PAR LES BOUTONS ===

    /**
     * Action du bouton SET.
     */
    public void doSet() {
        watchContext.handleSet();
    }

    /**
     * Action du bouton MODE.
     */
    public void doMode() {
        watchContext.handleMode();
    }

    /**
     * Tick du timer (100ms).
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.DIXEME_DE_SECONDE_PROP.equals(evt.getPropertyName())) {
            ticHappened();
        }
    }

    /**
     * Méthode appelée à chaque tick.
     * Délègue au contexte State.
     */
    public void ticHappened() {
        SwingUtilities.invokeLater(() -> watchContext.handleTick());
    }

    // === IMPLÉMENTATION DE L'INTERFACE WatchViewer ===

    @Override
    public void setTextPosition1(String txt) {
        hh.setText(txt);
    }

    @Override
    public void setTextPosition2(String txt) {
        mm.setText(txt);
    }

    @Override
    public void setTextSeparator(String txt) {
        sep.setText(txt);
    }

    @Override
    public void setTextPosition3(String txt) {
        mod.setText(txt);
    }

    // === GETTERS ===

    public Horloge getHorloge() {
        return horloge;
    }

    public Chronometre getChronometre() {
        return chronometre;
    }

    public WatchContext getWatchContext() {
        return watchContext;
    }
}