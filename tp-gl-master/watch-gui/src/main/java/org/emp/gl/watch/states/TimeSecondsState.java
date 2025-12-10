package org.emp.gl.watch.states;

import org.emp.gl.clients.Horloge;

/**
 * État: Affichage des secondes au format :ss.
 *
 * - SET: retour vers HH:mm
 * - MODE: passe au chronomètre
 *
 * @author Amine - TP3
 */
public class TimeSecondsState implements WatchState {

    @Override
    public void handleSet(WatchContext context) {
        // SET: retour vers HH:mm
        context.setState(new TimeHoursMinutesState());
    }

    @Override
    public void handleMode(WatchContext context) {
        // MODE: passer en mode Chronomètre
        context.setState(new ChronometerState());
    }

    @Override
    public void handleTick(WatchContext context) {
        Horloge h = context.getHorloge();

        // Afficher :ss avec clignotement
        String ss = format(h.getSeconds());
        String sep = context.toggleBlink() ? ":" : " ";

        context.getViewer().setTextPosition1(" ");
        context.getViewer().setTextSeparator(sep);
        context.getViewer().setTextPosition2(ss);
        context.getViewer().setTextPosition3("T");
    }

    @Override
    public void onEnter(WatchContext context) {
        System.out.println("🕐 Mode Heure: :ss");
        context.getViewer().setTextPosition3("T");
    }

    @Override
    public void onExit(WatchContext context) {
        // Rien de spécial
    }

    @Override
    public String getStateName() {
        return "TIME_:SS";
    }

    private String format(int n) {
        return (n < 10 ? "0" : "") + n;
    }
}