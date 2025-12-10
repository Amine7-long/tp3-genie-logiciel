package org.emp.gl.watch.states;

import org.emp.gl.clients.Horloge;

/**
 * État: Affichage de l'heure au format HH:mm.
 *
 * - SET: bascule vers :ss
 * - MODE: passe au chronomètre
 *
 * @author Amine - TP3
 */
public class TimeHoursMinutesState implements WatchState {

    @Override
    public void handleSet(WatchContext context) {
        // SET: basculer vers l'affichage :ss
        context.setState(new TimeSecondsState());
    }

    @Override
    public void handleMode(WatchContext context) {
        // MODE: passer en mode Chronomètre
        context.setState(new ChronometerState());
    }

    @Override
    public void handleTick(WatchContext context) {
        Horloge h = context.getHorloge();

        // Formater HH:mm avec clignotement des deux points
        String hh = format(h.getHours());
        String mm = format(h.getMinutes());
        String sep = context.toggleBlink() ? ":" : " ";

        context.getViewer().setTextPosition1(hh);
        context.getViewer().setTextSeparator(sep);
        context.getViewer().setTextPosition2(mm);
        context.getViewer().setTextPosition3("T"); // T pour Time
    }

    @Override
    public void onEnter(WatchContext context) {
        System.out.println("🕐 Mode Heure: HH:mm");
        context.getViewer().setTextPosition3("T");
    }

    @Override
    public void onExit(WatchContext context) {
        // Rien de spécial à faire
    }

    @Override
    public String getStateName() {
        return "TIME_HH:MM";
    }

    /**
     * Formate un nombre sur 2 chiffres.
     */
    private String format(int n) {
        return (n < 10 ? "0" : "") + n;
    }
}