/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emp.gl.timer.service;

/**
 * Interface pour les fournisseurs de notifications de changement de temps
 * Pattern Observer: permet aux objets de s'abonner aux notifications
 *
 * @author tina
 */
public interface TimeChangeProvider {

    /**
     * Ajouter un listener pour être notifié des changements
     * @param pl le listener à ajouter
     */
    public void addTimeChangeListener(TimerChangeListener pl);

    /**
     * Retirer un listener
     * @param pl le listener à retirer
     */
    public void removeTimeChangeListener(TimerChangeListener pl);
}