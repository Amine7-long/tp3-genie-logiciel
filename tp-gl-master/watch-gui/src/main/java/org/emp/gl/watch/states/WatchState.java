package org.emp.gl.watch.states;

/**
 * Interface du pattern State pour la montre électronique.
 *
 * Chaque état concret implémente cette interface et définit
 * le comportement des boutons SET et MODE dans ce contexte.
 *
 * @author Amine - TP3
 */
public interface WatchState {

    /**
     * Action déclenchée par le bouton SET.
     * Le comportement varie selon l'état actuel.
     *
     * @param context le contexte de la montre
     */
    void handleSet(WatchContext context);

    /**
     * Action déclenchée par le bouton MODE.
     * Le comportement varie selon l'état actuel.
     *
     * @param context le contexte de la montre
     */
    void handleMode(WatchContext context);

    /**
     * Action déclenchée à chaque tick du timer.
     * Permet de mettre à jour l'affichage selon l'état.
     *
     * @param context le contexte de la montre
     */
    void handleTick(WatchContext context);

    /**
     * Appelée lors de l'entrée dans cet état.
     * Permet d'initialiser l'affichage.
     *
     * @param context le contexte de la montre
     */
    void onEnter(WatchContext context);

    /**
     * Appelée lors de la sortie de cet état.
     * Permet de nettoyer les ressources.
     *
     * @param context le contexte de la montre
     */
    void onExit(WatchContext context);

    /**
     * Retourne le nom de l'état (pour debug).
     *
     * @return le nom de l'état
     */
    String getStateName();
}