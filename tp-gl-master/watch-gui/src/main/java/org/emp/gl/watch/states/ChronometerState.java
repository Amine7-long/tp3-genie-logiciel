package org.emp.gl.watch.states;

import org.emp.gl.clients.Chronometre;

/**
 * État: Mode Chronomètre (affiche ss:d).
 *
 * - SET: démarre/arrête le chronomètre
 * - MODE:
 *    * Si chrono en pause et != 0 → remet à zéro
 *    * Sinon → retour au mode Heure
 *
 * @author Amine - TP3
 */
public class ChronometerState implements WatchState {

    @Override
    public void handleSet(WatchContext context) {
        Chronometre chrono = context.getChronometre();

        // Démarrer/Arrêter le chronomètre
        if (chrono.isRunning()) {
            chrono.stop();
            System.out.println("⏸️  Chronomètre en pause");
        } else {
            chrono.start();
            System.out.println("▶️  Chronomètre démarré");
        }
    }

    @Override
    public void handleMode(WatchContext context) {
        Chronometre chrono = context.getChronometre();

        // Si en pause et valeur != 0 → reset
        if (!chrono.isRunning() && (chrono.getSeconds() > 0 || chrono.getDixiemes() > 0)) {
            chrono.reset();
            System.out.println("🔄 Chronomètre remis à zéro");
        } else {
            // Sinon → retour au mode Heure
            context.setState(new TimeHoursMinutesState());
        }
    }

    @Override
    public void handleTick(WatchContext context) {
        Chronometre chrono = context.getChronometre();

        // Afficher ss:d avec clignotement si en pause
        String ss = format(chrono.getSeconds());
        String d = String.valueOf(chrono.getDixiemes());
        String sep = chrono.isRunning() ? ":" : (context.toggleBlink() ? ":" : " ");

        context.getViewer().setTextPosition1(ss);
        context.getViewer().setTextSeparator(sep);
        context.getViewer().setTextPosition2(d);
        context.getViewer().setTextPosition3("C"); // C pour Chronomètre
    }

    @Override
    public void onEnter(WatchContext context) {
        System.out.println("⏱️  Mode Chronomètre");
        context.getViewer().setTextPosition3("C");
    }

    @Override
    public void onExit(WatchContext context) {
        // Arrêter le chronomètre si en cours
        Chronometre chrono = context.getChronometre();
        if (chrono.isRunning()) {
            chrono.stop();
        }
    }

    @Override
    public String getStateName() {
        return "CHRONOMETER";
    }

    private String format(int n) {
        return (n < 10 ? "0" : "") + n;
    }
}