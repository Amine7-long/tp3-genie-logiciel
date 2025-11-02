/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emp.gl.timer.service;

/**
 * Interface du service de temps
 * Fournit l'heure actuelle et notifie les changements
 *
 * @author tina
 */
public interface TimerService extends TimeChangeProvider {

    /**
     * Obtenir les minutes
     * @return les minutes (0-59)
     */
    int getMinutes();

    /**
     * Obtenir les heures
     * @return les heures (0-23)
     */
    int getHeures();

    /**
     * Obtenir les secondes
     * @return les secondes (0-59)
     */
    int getSecondes();

    /**
     * Obtenir les dixièmes de seconde
     * @return les dixièmes de seconde (0-9)
     */
    int getDixiemeDeSeconde();
}