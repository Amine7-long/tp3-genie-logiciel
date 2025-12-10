package org.emp.gl.watch.states;

import org.emp.gl.clients.Horloge;

/**
 * État: Mode Réglages (affiche S).
 *
 * Permet de régler l'heure: heures, minutes, secondes.
 *
 * - MODE: passe au paramètre suivant (H → M → S → sortie)
 * - SET: incrémente la valeur du paramètre courant
 *
 * @author Amine - TP3 (Partie c - bonus)
 */
public class SettingsState implements WatchState {

    // Sous-états pour les réglages
    private enum SettingMode {
        HOURS,    // Réglage des heures
        MINUTES,  // Réglage des minutes
        SECONDS   // Réglage des secondes
    }

    private SettingMode currentSetting = SettingMode.HOURS;

    @Override
    public void handleSet(WatchContext context) {
        Horloge h = context.getHorloge();

        // Incrémenter le paramètre courant
        switch (currentSetting) {
            case HOURS:
                h.incrementHours();
                System.out.println("⬆️  Heures: " + h.getHours());
                break;
            case MINUTES:
                h.incrementMinutes();
                System.out.println("⬆️  Minutes: " + h.getMinutes());
                break;
            case SECONDS:
                h.incrementSeconds();
                System.out.println("⬆️  Secondes: " + h.getSeconds());
                break;
        }
    }

    @Override
    public void handleMode(WatchContext context) {
        // Passer au paramètre suivant
        switch (currentSetting) {
            case HOURS:
                currentSetting = SettingMode.MINUTES;
                System.out.println("🔧 Réglage: Minutes");
                break;
            case MINUTES:
                currentSetting = SettingMode.SECONDS;
                System.out.println("🔧 Réglage: Secondes");
                break;
            case SECONDS:
                // Sortir du mode réglages
                System.out.println("✅ Réglages terminés");
                context.setState(new TimeHoursMinutesState());
                break;
        }
    }

    @Override
    public void handleTick(WatchContext context) {
        Horloge h = context.getHorloge();

        // Afficher avec clignotement sur le paramètre en cours de réglage
        boolean blink = context.toggleBlink();

        String hh = (currentSetting == SettingMode.HOURS && blink) ? "  " : format(h.getHours());
        String mm = (currentSetting == SettingMode.MINUTES && blink) ? "  " : format(h.getMinutes());
        String ss = (currentSetting == SettingMode.SECONDS && blink) ? "  " : format(h.getSeconds());

        // Afficher HH:mm ou :ss selon le paramètre
        if (currentSetting == SettingMode.SECONDS) {
            context.getViewer().setTextPosition1(" ");
            context.getViewer().setTextSeparator(":");
            context.getViewer().setTextPosition2(ss);
        } else {
            context.getViewer().setTextPosition1(hh);
            context.getViewer().setTextSeparator(":");
            context.getViewer().setTextPosition2(mm);
        }

        context.getViewer().setTextPosition3("S"); // S pour Settings
    }

    @Override
    public void onEnter(WatchContext context) {
        System.out.println("⚙️  Mode Réglages: Heures");
        currentSetting = SettingMode.HOURS;
        context.getViewer().setTextPosition3("S");
    }

    @Override
    public void onExit(WatchContext context) {
        System.out.println("⚙️  Sortie du mode Réglages");
    }

    @Override
    public String getStateName() {
        return "SETTINGS_" + currentSetting;
    }

    private String format(int n) {
        return (n < 10 ? "0" : "") + n;
    }
}