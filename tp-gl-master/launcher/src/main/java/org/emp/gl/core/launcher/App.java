package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.HorlogeGUI;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;

import javax.swing.*;

/**
 * Application principale - TP1 Complet
 * Versions: Console + GUI + Compte à rebours
 */
public class App {

    public static void main(String[] args) {
        // Afficher le menu de choix
        afficherMenu();
    }

    /**
     * Affiche un menu pour choisir le mode d'exécution
     */
    private static void afficherMenu() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   Application Horloge - TP1                    ║");
        System.out.println("║   Choisissez un mode d'exécution               ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");
        System.out.println("1. Mode Console (horloges textuelles)");
        System.out.println("2. Mode GUI (horloges graphiques)");
        System.out.println("3. Compte à rebours");
        System.out.println("4. Mode Mixte (Console + GUI)");
        System.out.println("5. Démo Complète (tout en même temps)");
        System.out.print("\nVotre choix (1-5): ");

        // Lire le choix depuis l'entrée standard
        try {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    modeConsole();
                    break;
                case 2:
                    modeGUI();
                    break;
                case 3:
                    modeCompteARebours();
                    break;
                case 4:
                    modeMixte();
                    break;
                case 5:
                    demoComplete();
                    break;
                default:
                    System.out.println("Choix invalide. Démarrage du mode console par défaut...");
                    modeConsole();
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture. Démarrage du mode console...");
            modeConsole();
        }
    }

    /**
     * Mode 1: Horloges en console uniquement
     */
    private static void modeConsole() {
        System.out.println("\n=== Mode Console ===\n");

        TimerService timeService = new DummyTimeServiceImpl();
        System.out.println("✓ Service de temps créé\n");

        Horloge horloge1 = new Horloge("Paris");
        Horloge horloge2 = new Horloge("Londres");
        Horloge horloge3 = new Horloge("Tokyo");

        horloge1.setTimerService(timeService);
        horloge2.setTimerService(timeService);
        horloge3.setTimerService(timeService);

        System.out.println("\n=== Affichage initial ===\n");
        horloge1.afficherHeure();
        horloge2.afficherHeure();
        horloge3.afficherHeure();

        System.out.println("\n=== Mode temps réel ===");
        System.out.println("Les horloges affichent l'heure en temps réel.");
        System.out.println("Appuyez sur Ctrl+C pour arrêter.\n");
    }

    /**
     * Mode 2: Horloges graphiques uniquement
     */
    private static void modeGUI() {
        System.out.println("\n=== Mode GUI ===\n");

        TimerService timeService = new DummyTimeServiceImpl();
        System.out.println("✓ Service de temps créé");
        System.out.println("✓ Ouverture des fenêtres graphiques...\n");

        // Créer plusieurs fenêtres d'horloge
        SwingUtilities.invokeLater(() -> {
            HorlogeGUI horloge1 = new HorlogeGUI("Paris", timeService);
            horloge1.setLocation(100, 100);

            HorlogeGUI horloge2 = new HorlogeGUI("Londres", timeService);
            horloge2.setLocation(520, 100);

            HorlogeGUI horloge3 = new HorlogeGUI("Tokyo", timeService);
            horloge3.setLocation(100, 380);
        });

        System.out.println("💡 Les horloges graphiques sont affichées.");
        System.out.println("   Fermez les fenêtres pour arrêter.\n");
    }

    /**
     * Mode 3: Compte à rebours
     */
    private static void modeCompteARebours() {
        System.out.println("\n=== Mode Compte à Rebours ===\n");

        TimerService timeService = new DummyTimeServiceImpl();

        System.out.print("Entrez le nombre de secondes: ");
        try {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int secondes = scanner.nextInt();

            if (secondes > 0) {
                CompteARebours compte = new CompteARebours(secondes, timeService);
                System.out.println("\n⏰ Compte à rebours lancé!\n");
            } else {
                System.out.println("❌ Valeur invalide!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur de saisie!");
        }
    }

    /**
     * Mode 4: Console + GUI ensemble
     */
    private static void modeMixte() {
        System.out.println("\n=== Mode Mixte (Console + GUI) ===\n");

        TimerService timeService = new DummyTimeServiceImpl();
        System.out.println("✓ Service de temps créé\n");

        // Horloges console
        Horloge horlogeConsole1 = new Horloge("Console-Paris");
        Horloge horlogeConsole2 = new Horloge("Console-Londres");

        horlogeConsole1.setTimerService(timeService);
        horlogeConsole2.setTimerService(timeService);

        System.out.println("\n=== Horloges Console ===");
        horlogeConsole1.afficherHeure();
        horlogeConsole2.afficherHeure();

        // Horloges GUI
        SwingUtilities.invokeLater(() -> {
            HorlogeGUI horlogeGUI1 = new HorlogeGUI("GUI-Tokyo", timeService);
            horlogeGUI1.setLocation(100, 100);

            HorlogeGUI horlogeGUI2 = new HorlogeGUI("GUI-New York", timeService);
            horlogeGUI2.setLocation(520, 100);
        });

        System.out.println("\n✓ Horloges console et graphiques actives!");
        System.out.println("  Fermez les fenêtres ou appuyez sur Ctrl+C pour arrêter.\n");
    }

    /**
     * Mode 5: Démo complète avec tout
     */
    private static void demoComplete() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   DÉMO COMPLÈTE - Toutes les fonctionnalités  ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        TimerService timeService = new DummyTimeServiceImpl();
        System.out.println("✓ Service de temps créé\n");

        // 1. Horloges console
        System.out.println("=== 1. Horloges Console ===");
        Horloge h1 = new Horloge("Paris");
        Horloge h2 = new Horloge("Londres");
        h1.setTimerService(timeService);
        h2.setTimerService(timeService);
        h1.afficherHeure();
        h2.afficherHeure();

        // 2. Compte à rebours
        System.out.println("\n=== 2. Compte à Rebours (10 secondes) ===");
        CompteARebours compte = new CompteARebours(10, timeService);

        // 3. Horloges GUI
        System.out.println("\n=== 3. Horloges Graphiques ===");
        SwingUtilities.invokeLater(() -> {
            HorlogeGUI gui1 = new HorlogeGUI("Tokyo", timeService);
            gui1.setLocation(100, 100);

            HorlogeGUI gui2 = new HorlogeGUI("New York", timeService);
            gui2.setLocation(520, 100);

            HorlogeGUI gui3 = new HorlogeGUI("Sydney", timeService);
            gui3.setLocation(100, 380);
        });

        System.out.println("✓ Fenêtres graphiques ouvertes\n");
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   Tous les composants sont actifs!            ║");
        System.out.println("║   - Horloges console: 2                        ║");
        System.out.println("║   - Horloges graphiques: 3                     ║");
        System.out.println("║   - Compte à rebours: 1                        ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");
        System.out.println("Fermez les fenêtres ou Ctrl+C pour arrêter.\n");
    }

    /**
     * Méthode utilitaire pour effacer l'écran
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}