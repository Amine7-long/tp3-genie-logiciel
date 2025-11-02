package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

/**
 * Classe Horloge qui affiche l'heure en temps réel
 * Implémente TimerChangeListener pour être notifiée des changements
 */
public class Horloge implements TimerChangeListener {

    String name;
    TimerService timerService;

    /**
     * Constructeur de l'horloge
     * @param name le nom de l'horloge
     */
    public Horloge(String name) {
        this.name = name;
        System.out.println("Horloge " + name + " initialized!");
    }

    /**
     * Définit le service de temps et s'inscrit comme listener
     * @param service le service de temps
     */
    public void setTimerService(TimerService service) {
        // Se désinscrire de l'ancien service si existant
        if (this.timerService != null) {
            this.timerService.removeTimeChangeListener(this);
        }

        // Affecter le nouveau service
        this.timerService = service;

        // S'inscrire au nouveau service
        if (service != null) {
            service.addTimeChangeListener(this);
        }
    }

    /**
     * Affiche l'heure actuelle au format HH:MM:SS
     */
    public void afficherHeure() {
        if (timerService != null) {
            System.out.println(name + " affiche " +
                    formatNumber(timerService.getHeures()) + ":" +
                    formatNumber(timerService.getMinutes()) + ":" +
                    formatNumber(timerService.getSecondes()));
        } else {
            System.out.println(name + " : Service de temps non disponible");
        }
    }

    /**
     * Formate un nombre sur 2 chiffres (ajoute un 0 devant si < 10)
     * @param number le nombre à formater
     * @return le nombre formaté (ex: 9 → "09", 15 → "15")
     */
    private String formatNumber(int number) {
        return (number < 10 ? "0" : "") + number;
    }

    /**
     * Méthode appelée par le TimerService lors d'un changement
     * Implémentation de TimerChangeListener
     *
     * @param prop le nom de la propriété qui a changé
     * @param oldValue l'ancienne valeur
     * @param newValue la nouvelle valeur
     */
    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // Afficher l'heure uniquement quand les secondes changent
        // (pour éviter trop d'affichages avec les dixièmes de seconde)
        if (SECONDE_PROP.equals(prop)) {
            afficherHeure();
        }
    }

    /**
     * Obtient le nom de l'horloge
     * @return le nom
     */
    public String getName() {
        return name;
    }
}