package org.emp.gl.clients;

import org.emp.gl.lookup.Lookup;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

import java.beans.PropertyChangeEvent;

/**
 * Chronomètre qui compte en dixièmes de seconde.
 * Affiche le format: ss:d (secondes : dixièmes)
 *
 * Peut être démarré, arrêté et remis à zéro.
 *
 * @author Amine - TP3
 */
public class Chronometre implements TimerChangeListener {

    private final String name;
    private TimerService timerService;

    private int secondes = 0;
    private int dixiemes = 0;
    private boolean running = false;

    /**
     * Constructeur du chronomètre.
     *
     * @param name le nom du chronomètre
     */
    public Chronometre(String name) {
        this.name = name;

        // Récupération du service via Lookup
        Lookup lookup = Lookup.getInstance();
        this.timerService = lookup.getService(TimerService.class);

        if (timerService != null) {
            timerService.addTimeChangeListener(this);
            System.out.println("⏱️  Chronomètre '" + name + "' créé");
        } else {
            System.err.println("⚠️  Chronomètre '" + name + "' : TimerService non disponible!");
        }
    }

    /**
     * Démarre le chronomètre.
     */
    public void start() {
        if (!running) {
            running = true;
            System.out.println("▶️  Chronomètre démarré");
        }
    }

    /**
     * Arrête le chronomètre (pause).
     */
    public void stop() {
        if (running) {
            running = false;
            System.out.println("⏸️  Chronomètre en pause");
        }
    }

    /**
     * Remet le chronomètre à zéro.
     */
    public void reset() {
        secondes = 0;
        dixiemes = 0;
        System.out.println("🔄 Chronomètre remis à zéro");
    }

    /**
     * Incrémente le chronomètre (appelé par le timer).
     */
    private void increment() {
        if (!running) return;

        dixiemes++;
        if (dixiemes >= 10) {
            dixiemes = 0;
            secondes++;
            if (secondes >= 60) {
                // Limite à 59:9 pour simplifier
                secondes = 59;
                dixiemes = 9;
                stop();
            }
        }
    }

    /**
     * Réagit aux changements du service de temps.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.DIXEME_DE_SECONDE_PROP.equals(evt.getPropertyName())) {
            increment();
        }
    }

    // === GETTERS ===

    public int getSeconds() {
        return secondes;
    }

    public int getDixiemes() {
        return dixiemes;
    }

    public boolean isRunning() {
        return running;
    }

    public String getName() {
        return name;
    }

    /**
     * Affiche le temps au format ss:d
     */
    public String getFormattedTime() {
        return String.format("%02d:%d", secondes, dixiemes);
    }

    /**
     * Nettoie les ressources.
     */
    public void dispose() {
        if (timerService != null) {
            timerService.removeTimeChangeListener(this);
            System.out.println("🗑️  Chronomètre '" + name + "' désinscrit");
        }
    }
}