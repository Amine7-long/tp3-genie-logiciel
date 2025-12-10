package org.emp.gl.core.launcher;

import org.emp.gl.gui.WatchViewerGUI;
import org.emp.gl.gui.ButtonViewer;
import org.emp.gl.lookup.Lookup;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;

/**
 * Application principale pour le TP3.
 * Lance la montre électronique avec le pattern State.
 *
 * @author Amine - TP3
 */
public class App {

    static {
        // Initialisation du TimerService dans le Lookup
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║   TP3 : Montre Électronique (State Pattern)  ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");

        System.out.println("🔧 Initialisation du système...");

        TimerService timerService = new DummyTimeServiceImpl();
        Lookup lookup = Lookup.getInstance();
        lookup.subscribeService(TimerService.class, timerService);

        System.out.println("✅ TimerService enregistré dans le Lookup\n");
    }

    /**
     * Point d'entrée de l'application.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        // Lancer l'interface graphique sur le thread EDT
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("🚀 Lancement de l'interface graphique...\n");

                // Créer la montre
                WatchViewerGUI watchViewer = new WatchViewerGUI();

                // Créer les boutons
                ButtonViewer buttonViewer = new ButtonViewer(watchViewer);

                System.out.println("═══════════════════════════════════════════════");
                System.out.println("⌚ Montre électronique démarrée!");
                System.out.println("═══════════════════════════════════════════════");
                System.out.println("\n📖 Guide d'utilisation:");
                System.out.println("   Mode Horloge (T):");
                System.out.println("   • SET: Basculer entre HH:mm et :ss");
                System.out.println("   • MODE: Passer au chronomètre");
                System.out.println("\n   Mode Chronomètre (C):");
                System.out.println("   • SET: Démarrer/Arrêter");
                System.out.println("   • MODE: Reset (si en pause) / Retour (sinon)");
                System.out.println("\n   Mode Réglages (S) - [BONUS]:");
                System.out.println("   • SET: Incrémenter la valeur");
                System.out.println("   • MODE: Paramètre suivant / Sortir");
                System.out.println("═══════════════════════════════════════════════\n");
            }
        });
    }

    /**
     * Démonstration alternative: lancer plusieurs montres.
     */
    public static void demoMultipleWatches() {
        java.awt.EventQueue.invokeLater(() -> {
            System.out.println("🚀 Démonstration avec plusieurs montres...\n");

            // Montre 1
            WatchViewerGUI watch1 = new WatchViewerGUI();
            ButtonViewer buttons1 = new ButtonViewer(watch1);

            // Montre 2 (optionnel)
            // WatchViewerGUI watch2 = new WatchViewerGUI();
            // ButtonViewer buttons2 = new ButtonViewer(watch2);

            System.out.println("✅ Montres créées!");
        });
    }
}