package org.emp.gl.lookup;

import java.util.HashMap;
import java.util.Map;

/**
 * Annuaire (Service Locator Pattern) implémenté en Singleton.
 * Permet l'enregistrement et la récupération de services avec type-safety.
 *
 * TP2 - Question (b) : Version avancée avec gestion des types
 *
 * @author Amine
 */
public class Lookup {

    // Instance unique du Singleton
    private static Lookup instance;

    // Stockage des services : Class<?> → Object
    private final Map<Class<?>, Object> services = new HashMap<>();

    /**
     * Constructeur privé pour empêcher l'instanciation externe.
     */
    private Lookup() {
        System.out.println("📋 Annuaire Lookup initialisé");
    }

    /**
     * Retourne l'instance unique du Lookup (pattern Singleton).
     * Thread-safe avec synchronisation.
     *
     * @return l'instance unique
     */
    public static synchronized Lookup getInstance() {
        if (instance == null) {
            instance = new Lookup();
        }
        return instance;
    }

    /**
     * Enregistre un service dans l'annuaire.
     * Version avec généricité pour garantir la cohérence des types.
     *
     * @param <T>      le type du service
     * @param service  la classe ou l'interface du service
     * @param instance l'instance concrète du service
     */
    public <T> void subscribeService(Class<? super T> service, T instance) {
        if (service == null) {
            throw new IllegalArgumentException("❌ La classe du service ne peut pas être null");
        }
        if (instance == null) {
            throw new IllegalArgumentException("❌ L'instance du service ne peut pas être null");
        }

        services.put(service, instance);
        System.out.println("✅ Service enregistré : " + service.getSimpleName() +
                " -> " + instance.getClass().getSimpleName());
    }

    /**
     * Récupère un service depuis l'annuaire.
     * Version avec généricité : pas besoin de cast, type-safe.
     *
     * @param <T>     le type attendu du service
     * @param service la classe du service à récupérer
     * @return l'instance du service, ou null si non trouvé
     */
    public <T> T getService(Class<T> service) {
        if (service == null) {
            throw new IllegalArgumentException("❌ La classe du service ne peut pas être null");
        }

        Object obj = services.get(service);

        if (obj == null) {
            System.err.println("⚠️  Service non trouvé dans l'annuaire : " + service.getSimpleName());
            return null;
        }

        // Le cast est sûr grâce à la vérification dans subscribeService
        return service.cast(obj);
    }

    /**
     * Retire un service de l'annuaire.
     *
     * @param service la classe du service à retirer
     * @return true si le service a été retiré, false sinon
     */
    public boolean unsubscribeService(Class<?> service) {
        if (service == null) {
            return false;
        }

        Object removed = services.remove(service);
        if (removed != null) {
            System.out.println("🗑️  Service retiré : " + service.getSimpleName());
            return true;
        }
        return false;
    }

    /**
     * Vérifie si un service est enregistré.
     *
     * @param service la classe du service
     * @return true si le service existe
     */
    public boolean hasService(Class<?> service) {
        return services.containsKey(service);
    }

    /**
     * Affiche tous les services enregistrés (utile pour le debug).
     */
    public void displayServices() {
        System.out.println("\n📋 Services enregistrés dans l'annuaire :");
        if (services.isEmpty()) {
            System.out.println("   (aucun service)");
        } else {
            services.forEach((key, value) ->
                    System.out.println("   - " + key.getSimpleName() +
                            " → " + value.getClass().getSimpleName())
            );
        }
        System.out.println();
    }

    /**
     * Vide complètement l'annuaire (utile pour les tests).
     */
    public void clear() {
        services.clear();
        System.out.println("🧹 Annuaire vidé");
    }

    /**
     * Retourne le nombre de services enregistrés.
     *
     * @return le nombre de services
     */
    public int size() {
        return services.size();
    }
}