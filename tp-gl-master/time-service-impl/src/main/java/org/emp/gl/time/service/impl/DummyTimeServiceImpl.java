/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emp.gl.time.service.impl;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

/**
 * Implémentation du service de temps
 * Génère des événements toutes les 100ms (1/10 de seconde)
 *
 * @author tina
 */
public class DummyTimeServiceImpl implements TimerService {

    int dixiemeDeSeconde;
    int minutes;
    int secondes;
    int heures;
    List<TimerChangeListener> listeners = new LinkedList<>();

    /**
     * Constructeur du DummyTimeServiceImpl: ici,
     * nous nous avons utilisé un objet Timer, qui permet de
     * réaliser des tics à chaque N millisecondes
     */
    public DummyTimeServiceImpl() {
        setTimeValues();
        // initialize schedular
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeChanged();
            }
        };
        timer.scheduleAtFixedRate(task, 100, 100);
    }

    /**
     * Récupère l'heure système actuelle et met à jour les valeurs
     */
    private void setTimeValues() {
        LocalTime localTime = LocalTime.now();

        setSecondes(localTime.getSecond());
        setMinutes(localTime.getMinute());
        setHeures(localTime.getHour());
        setDixiemeDeSeconde(localTime.getNano() / 100000000);
    }

    @Override
    public void addTimeChangeListener(TimerChangeListener pl) {
        listeners.add(pl);
    }

    @Override
    public void removeTimeChangeListener(TimerChangeListener pl) {
        listeners.remove(pl);
    }

    /**
     * Appelé toutes les 100ms par le Timer
     */
    private void timeChanged() {
        setTimeValues();
    }

    /**
     * Modifie les dixièmes de seconde
     */
    public void setDixiemeDeSeconde(int newDixiemeDeSeconde) {
        if (dixiemeDeSeconde == newDixiemeDeSeconde)
            return;

        int oldValue = dixiemeDeSeconde;
        dixiemeDeSeconde = newDixiemeDeSeconde;

        // informer les listeners !
        dixiemeDeSecondesChanged(oldValue, dixiemeDeSeconde);
    }

    /**
     * Notifie tous les listeners du changement de dixième de seconde
     */
    private void dixiemeDeSecondesChanged(int oldValue, int newValue) {
        for (TimerChangeListener l : listeners) {
            l.propertyChange(TimerChangeListener.DIXEME_DE_SECONDE_PROP,
                    oldValue, dixiemeDeSeconde);
        }
    }

    /**
     * Modifie les secondes
     */
    public void setSecondes(int newSecondes) {
        if (secondes == newSecondes)
            return;

        int oldValue = secondes;
        secondes = newSecondes;

        secondesChanged(oldValue, secondes);
    }

    /**
     * Notifie tous les listeners du changement de seconde
     */
    private void secondesChanged(int oldValue, int secondes) {
        for (TimerChangeListener l : listeners) {
            l.propertyChange(TimerChangeListener.SECONDE_PROP,
                    oldValue, secondes);
        }
    }

    /**
     * Modifie les minutes
     */
    public void setMinutes(int newMinutes) {
        if (minutes == newMinutes)
            return;

        int oldValue = minutes;
        minutes = newMinutes;

        minutesChanged(oldValue, minutes);
    }

    /**
     * Notifie tous les listeners du changement de minute
     * CORRECTION DU BUG: envoyer minutes au lieu de secondes
     */
    private void minutesChanged(int oldValue, int minutes) {
        for (TimerChangeListener l : listeners) {
            l.propertyChange(TimerChangeListener.MINUTE_PROP,
                    oldValue, minutes);  // ← CORRIGÉ (était "secondes")
        }
    }

    /**
     * Modifie les heures
     */
    public void setHeures(int newHeures) {
        if (heures == newHeures)
            return;

        int oldValue = heures;
        heures = newHeures;

        heuresChanged(oldValue, heures);
    }

    /**
     * Notifie tous les listeners du changement d'heure
     * CORRECTION DU BUG: envoyer heures au lieu de secondes
     */
    private void heuresChanged(int oldValue, int heures) {
        for (TimerChangeListener l : listeners) {
            l.propertyChange(TimerChangeListener.HEURE_PROP,
                    oldValue, heures);  // ← CORRIGÉ (était "secondes")
        }
    }

    @Override
    public int getDixiemeDeSeconde() {
        return dixiemeDeSeconde;
    }

    @Override
    public int getHeures() {
        return heures;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    @Override
    public int getSecondes() {
        return secondes;
    }
}