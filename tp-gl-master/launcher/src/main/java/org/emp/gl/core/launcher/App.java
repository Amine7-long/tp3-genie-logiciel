package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.lookup.Lookup;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;

/**
 * Application principale pour le TP2 : Injection de dépendance avec Lookup
 *
 * @author Amine
 */
public class App {

    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║   TP2 : Injection de dépendance avec Lookup    ║");
        System.out.println("╚═════════════════════════════════════════════════╝\n");

        // Afficher le menu
        afficherMenu();
    }

    /**
     * Menu de sélection des démonstrations TP2
     */
    private static void afficherMenu() {
        System.out.println("Choisissez une démonstration :\n");
        System.out.println("1. TP2(a) - Annuaire basique (version Object)");
        System.out.println("2. TP2(b) - Annuaire avancé (version générique)");
        System.out.println("3. Démonstration complète avec plusieurs horloges");
        System.out.println("4. Test avec compte à rebours");
        System.out.print("\nVotre choix (1-4) : ");

        try {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    demoAnnuaireBasique();
                    break;
                case 2:
                    demoAnnuaireAvance();
                    break;
                case 3:
                    demoComplete();
                    break;
                case 4:
                    demoCompteARebours();
                    break;
                default:
                    System.out.println("Choix invalide. Lancement de la démo par défaut...");
                    demoAnnuaireAvance();
            }
        } catch (Exception e) {
            System.out.println("Erreur de saisie. Lancement de la démo par défaut...");
            demoAnnuaireAvance();
        }
    }

    /**
     * TP2(a) : Démonstration de l'annuaire basique
     */
    private static void demoAnnuaireBasique() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   TP2(a) : Annuaire Basique                    ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        // Étape 1 : Créer et enregistrer le TimerService dans le Lookup
        System.out.println("--- Étape 1 : Enregistrement du service ---");
        TimerService timerService = new DummyTimeServiceImpl();
        Lookup lookup = Lookup.getInstance();
        lookup.subscribeService(TimerService.class, timerService);

        // Étape 2 : Créer des horloges (elles récupèrent le service via Lookup)
        System.out.println("\n--- Étape 2 : Création des horloges ---");
        Horloge horloge1 = new Horloge("Paris");
        Horloge horloge2 = new Horloge("Londres");
        Horloge horloge3 = new Horloge("Tokyo");

        // Étape 3 : Afficher les informations
        System.out.println("\n--- Étape 3 : Affichage ---");
        lookup.displayServices();

        System.out.println("✅ Les horloges fonctionnent en temps réel!");
        System.out.println("   Appuyez sur Ctrl+C pour arrêter.\n");
    }

    /**
     * TP2(b) : Démonstration de l'annuaire avancé avec généricité
     */
    private static void demoAnnuaireAvance() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   TP2(b) : Annuaire Avancé (Générique)        ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        // Étape 1 : Créer le Lookup et enregistrer le service
        System.out.println("--- Étape 1 : Enregistrement avec type-safety ---");
        Lookup lookup = Lookup.getInstance();
        TimerService timerService = new DummyTimeServiceImpl();

        // ✅ Enregistrement type-safe avec généricité
        lookup.subscribeService(TimerService.class, timerService);

        // Étape 2 : Récupération type-safe (pas de cast nécessaire)
        System.out.println("\n--- Étape 2 : Récupération type-safe ---");
        TimerService retrievedService = lookup.getService(TimerService.class);

        if (retrievedService != null) {
            System.out.println("✅ Service récupéré sans cast !");
            System.out.println("   Type : " + retrievedService.getClass().getSimpleName());
            System.out.println("   Heure actuelle : " +
                    retrievedService.getHeures() + ":" +
                    retrievedService.getMinutes() + ":" +
                    retrievedService.getSecondes());
        }

        // Étape 3 : Créer des horloges
        System.out.println("\n--- Étape 3 : Création des horloges ---");
        Horloge h1 = new Horloge("Berlin");
        Horloge h2 = new Horloge("Madrid");

        // Afficher l'état du Lookup
        lookup.displayServices();

        System.out.println("✅ Démonstration terminée!");
        System.out.println("   Les horloges continuent de fonctionner...\n");
    }

    /**
     * Démonstration complète avec plusieurs horloges
     */
    private static void demoComplete() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   Démonstration Complète TP2                   ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        // Configuration du Lookup
        System.out.println("=== Configuration du système ===");
        Lookup lookup = Lookup.getInstance();
        TimerService service = new DummyTimeServiceImpl();
        lookup.subscribeService(TimerService.class, service);

        // Création de plusieurs horloges
        System.out.println("\n=== Création de 5 horloges ===");
        String[] villes = {"Paris", "Londres", "Tokyo", "New York", "Sydney"};
        Horloge[] horloges = new Horloge[5];

        for (int i = 0; i < villes.length; i++) {
            horloges[i] = new Horloge(villes[i]);
            try {
                Thread.sleep(200); // Petit délai pour voir l'affichage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Afficher l'état du système
        System.out.println("\n=== État du système ===");
        lookup.displayServices();
        System.out.println("Nombre d'horloges créées : " + horloges.length);

        System.out.println("\n✅ Toutes les horloges sont synchronisées!");
        System.out.println("   Elles utilisent toutes le même TimerService via le Lookup.\n");
    }

    /**
     * Démonstration avec compte à rebours
     */
    private static void demoCompteARebours() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   TP2 avec Compte à Rebours                    ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        // Configuration
        Lookup lookup = Lookup.getInstance();
        TimerService service = new DummyTimeServiceImpl();
        lookup.subscribeService(TimerService.class, service);

        // Créer une horloge normale
        System.out.println("=== Création d'une horloge normale ===");
        Horloge horloge = new Horloge("Horloge Principale");

        // Créer un compte à rebours
        System.out.println("\n=== Création d'un compte à rebours ===");
        System.out.print("Entrez le nombre de secondes : ");

        try {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int secondes = scanner.nextInt();

            if (secondes > 0) {
                CompteARebours compte = new CompteARebours(secondes, service);
                System.out.println("\n✅ Système démarré!");
                System.out.println("   - Horloge normale affiche l'heure");
                System.out.println("   - Compte à rebours : " + secondes + " secondes\n");
            } else {
                System.out.println("❌ Valeur invalide!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur de saisie!");
        }
    }

    /**
     * Méthode utilitaire pour effacer l'écran
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}