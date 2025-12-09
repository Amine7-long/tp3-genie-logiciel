package org.emp.gl.clients;

import org.emp.gl.lookup.Lookup;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

import java.beans.PropertyChangeEvent;

/**
 * Classe Horloge modifiée pour le TP2.
 * Utilise l'annuaire Lookup pour récupérer le TimerService
 * au lieu de l'injection par constructeur.
 *
 * @author Amine
 */
public class Horloge implements TimerChangeListener {

    private final String name;
    private TimerService timerService;

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
            // S'inscrire comme observateur
            timerService.addTimeChangeListener(this);
            System.out.println("🕐 Horloge '" + name + "' créée et connectée au TimerService");
        } else {
            System.err.println("⚠️  Horloge '" + name + "' : TimerService non disponible dans le Lookup!");
        }
    }

    /**
     * Définit manuellement le service de temps (optionnel, pour compatibilité).
     *
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
     * Affiche l'heure actuelle au format HH:MM:SS.
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
     * Formate un nombre sur 2 chiffres (ajoute un 0 devant si < 10).
     *
     * @param number le nombre à formater
     * @return le nombre formaté (ex: 9 → "09", 15 → "15")
     */
    private String formatNumber(int number) {
        return (number < 10 ? "0" : "") + number;
    }

    /**
     * Méthode appelée par le TimerService lorsqu'une propriété change.
     * Affiche l'heure uniquement quand la seconde change.
     *
     * @param evt événement contenant le nom de la propriété et les valeurs
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            afficherHeure();
        }
    }

    /**
     * Obtient le nom de l'horloge.
     *
     * @return le nom de cette horloge
     */
    public String getName() {
        return name;
    }

    /**
     * Nettoie les ressources (se désinscrit du service).
     */
    public void dispose() {
        if (timerService != null) {
            timerService.removeTimeChangeListener(this);
            System.out.println("🗑️  Horloge '" + name + "' désinscrite");
        }
    }
}