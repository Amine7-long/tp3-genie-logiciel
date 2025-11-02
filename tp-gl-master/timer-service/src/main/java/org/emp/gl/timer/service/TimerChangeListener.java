/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emp.gl.timer.service;

/**
 * Interface pour écouter les changements de temps
 * Pattern Observer: les objets implémentant cette interface seront notifiés
 *
 * @author tina
 */
public interface TimerChangeListener {

    /**
     * Constante pour la propriété "dixième de seconde"
     */
    final static String DIXEME_DE_SECONDE_PROP = "dixième";

    /**
     * Constante pour la propriété "seconde"
     */
    final static String SECONDE_PROP = "seconde";

    /**
     * Constante pour la propriété "minute"
     */
    final static String MINUTE_PROP = "minute";

    /**
     * Constante pour la propriété "heure"
     */
    final static String HEURE_PROP = "heure";

    /**
     * Méthode appelée par le TimeChangeProvider à chaque fois
     * qu'il y a un changement sur l'une des propriétés de l'heure
     *
     * @param prop le nom de la propriété qui a changé (DIXEME_DE_SECONDE_PROP, SECONDE_PROP, etc.)
     * @param oldValue l'ancienne valeur
     * @param newValue la nouvelle valeur
     */
    void propertyChange(String prop, Object oldValue, Object newValue);
}