package org.emp.gl.watch.states;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.Chronometre;

/**
 * Contexte du pattern State.
 *
 * Maintient une référence vers l'état courant et délègue
 * les actions aux états concrets. Contient aussi les données
 * partagées (Horloge, Chronomètre, GUI).
 *
 * @author Amine - TP3
 */
public class WatchContext {

    private WatchState currentState;
    private final Horloge horloge;
    private final Chronometre chronometre;
    private final WatchViewer viewer;

    // Indicateur de clignotement pour les deux points
    private boolean blinkState = false;

    /**
     * Constructeur du contexte.
     *
     * @param horloge l'instance de l'horloge
     * @param chronometre l'instance du chronomètre
     * @param viewer l'interface graphique
     */
    public WatchContext(Horloge horloge, Chronometre chronometre, WatchViewer viewer) {
        this.horloge = horloge;
        this.chronometre = chronometre;
        this.viewer = viewer;

        // État initial: affichage de l'heure HH:mm
        this.currentState = new TimeHoursMinutesState();
        this.currentState.onEnter(this);
    }

    /**
     * Change l'état courant de la montre.
     *
     * @param newState le nouvel état
     */
    public void setState(WatchState newState) {
        System.out.println("🔄 Transition: " + currentState.getStateName() +
                " → " + newState.getStateName());

        currentState.onExit(this);
        currentState = newState;
        currentState.onEnter(this);
    }

    /**
     * Délègue l'action SET à l'état courant.
     */
    public void handleSet() {
        System.out.println("🔘 Bouton SET pressé dans l'état: " + currentState.getStateName());
        currentState.handleSet(this);
    }

    /**
     * Délègue l'action MODE à l'état courant.
     */
    public void handleMode() {
        System.out.println("🔘 Bouton MODE pressé dans l'état: " + currentState.getStateName());
        currentState.handleMode(this);
    }

    /**
     * Délègue le tick à l'état courant.
     */
    public void handleTick() {
        currentState.handleTick(this);
    }

    /**
     * Bascule l'état de clignotement.
     *
     * @return le nouvel état de clignotement
     */
    public boolean toggleBlink() {
        blinkState = !blinkState;
        return blinkState;
    }

    // === GETTERS ===

    public Horloge getHorloge() {
        return horloge;
    }

    public Chronometre getChronometre() {
        return chronometre;
    }

    public WatchViewer getViewer() {
        return viewer;
    }

    public WatchState getCurrentState() {
        return currentState;
    }

    public boolean getBlinkState() {
        return blinkState;
    }
}

