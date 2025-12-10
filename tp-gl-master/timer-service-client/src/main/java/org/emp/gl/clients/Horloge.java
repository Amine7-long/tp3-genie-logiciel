package org.emp.gl.clients;

import org.emp.gl.lookup.Lookup;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

import java.beans.PropertyChangeEvent;

/**
 * Classe Horloge mise à jour pour le TP3.
 * Permet le réglage manuel de l'heure.
 *
 * @author Amine - TP2/TP3
 */
public class Horloge implements TimerChangeListener {

    private final String name;
    private TimerService timerService;

    // Valeurs de temps internes (pour le mode réglage)
    private int seconds;
    private int minutes;
    private int hours;

    // Mode de synchronisation
    private boolean syncWithService = true;

    /**
     * Constructeur de l'horloge.
     * Récupère automatiquement le TimerService depuis le Lookup.
     *
     * @param name le nom de l'horloge
     */
    public Horloge(String name) {
        this.name = name;

        // ✅ Récupération du service via l'annuaire Lookup
        Lookup lookup = Lookup.getInstance();
        this.timerService = lookup.getService(TimerService.class);

        if (timerService != null) {
            // Initialiser avec l'heure du service
            seconds = timerService.getSecondes();
            minutes = timerService.getMinutes();
            hours = timerService.getHeures();

            // S'inscrire comme observateur
            timerService.addTimeChangeListener(this);
            System.out.println("🕐 Horloge '" + name + "' créée et connectée");
        } else {
            System.err.println("⚠️  Horloge '" + name + "' : TimerService non disponible!");
        }
    }

    /**
     * Définit manuellement le service de temps.
     *
     * @param service le service de temps
     */
    public void setTimerService(TimerService service) {
        if (this.timerService != null) {
            this.timerService.removeTimeChangeListener(this);
        }

        this.timerService = service;

        if (service != null) {
            service.addTimeChangeListener(this);
        }
    }

    /**
     * Affiche l'heure actuelle au format HH:MM:SS.
     */
    public void afficherHeure() {
        if (timerService != null) {
            System.out.println(name + " affiche " +
                    formatNumber(hours) + ":" +
                    formatNumber(minutes) + ":" +
                    formatNumber(seconds));
        } else {
            System.out.println(name + " : Service de temps non disponible");
        }
    }

    /**
     * Formate un nombre sur 2 chiffres.
     */
    private String formatNumber(int number) {
        return (number < 10 ? "0" : "") + number;
    }

    /**
     * Réagit aux changements du service de temps.
     * Met à jour les valeurs internes si en mode synchro.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!syncWithService) return;

        String prop = evt.getPropertyName();

        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            seconds = (int) evt.getNewValue();
        } else if (TimerChangeListener.MINUTE_PROP.equals(prop)) {
            minutes = (int) evt.getNewValue();
        } else if (TimerChangeListener.HEURE_PROP.equals(prop)) {
            hours = (int) evt.getNewValue();
        }
    }

    // === MÉTHODES DE RÉGLAGE MANUEL (TP3) ===

    /**
     * Incrémente les secondes.
     */
    public void incrementSeconds() {
        syncWithService = false;
        seconds = (seconds + 1) % 60;
        if (seconds == 0) {
            incrementMinutes();
        }
    }

    /**
     * Incrémente les minutes.
     */
    public void incrementMinutes() {
        syncWithService = false;
        minutes = (minutes + 1) % 60;
        if (minutes == 0) {
            incrementHours();
        }
    }

    /**
     * Incrémente les heures.
     */
    public void incrementHours() {
        syncWithService = false;
        hours = (hours + 1) % 24;
    }

    /**
     * Réactive la synchronisation avec le service.
     */
    public void enableSync() {
        syncWithService = true;
        if (timerService != null) {
            seconds = timerService.getSecondes();
            minutes = timerService.getMinutes();
            hours = timerService.getHeures();
        }
    }

    // === GETTERS ===

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    /**
     * Nettoie les ressources.
     */
    public void dispose() {
        if (timerService != null) {
            timerService.removeTimeChangeListener(this);
            System.out.println("🗑️  Horloge '" + name + "' désinscrite");
        }
    }
}