package org.emp.gl.core.launcher;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.emp.gl.clients.Horloge;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;
import org.junit.Test;
import org.junit.Before;

/**
 * Tests unitaires pour l'application
 */
public class AppTest {

    private TimerService timerService;

    @Before
    public void setUp() {
        // Initialiser le service avant chaque test
        timerService = new DummyTimeServiceImpl();
    }

    /**
     * Test basique (fourni initialement)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    /**
     * Test de création du service de temps
     */
    @Test
    public void testCreationTimerService() {
        assertNotNull("Le service ne doit pas être null", timerService);
    }

    /**
     * Test de création d'une horloge
     */
    @Test
    public void testCreationHorloge() {
        Horloge horloge = new Horloge("Test");
        assertNotNull("L'horloge ne doit pas être null", horloge);
        assertEquals("Le nom doit correspondre", "Test", horloge.getName());
    }

    /**
     * Test de connexion horloge-service
     */
    @Test
    public void testConnexionHorlogeService() {
        Horloge horloge = new Horloge("Test");

        // Ne doit pas lever d'exception
        horloge.setTimerService(timerService);

        // L'affichage ne doit pas crasher
        horloge.afficherHeure();
    }

    /**
     * Test des getters du service
     */
    @Test
    public void testTimerServiceGetters() {
        int heures = timerService.getHeures();
        int minutes = timerService.getMinutes();
        int secondes = timerService.getSecondes();
        int dixiemes = timerService.getDixiemeDeSeconde();

        // Vérifier que les valeurs sont dans les plages correctes
        assertTrue("Heures entre 0 et 23", heures >= 0 && heures <= 23);
        assertTrue("Minutes entre 0 et 59", minutes >= 0 && minutes <= 59);
        assertTrue("Secondes entre 0 et 59", secondes >= 0 && secondes <= 59);
        assertTrue("Dixièmes entre 0 et 9", dixiemes >= 0 && dixiemes <= 9);
    }

    /**
     * Test avec plusieurs horloges
     */
    @Test
    public void testPlusieursHorloges() {
        Horloge h1 = new Horloge("H1");
        Horloge h2 = new Horloge("H2");
        Horloge h3 = new Horloge("H3");

        h1.setTimerService(timerService);
        h2.setTimerService(timerService);
        h3.setTimerService(timerService);

        // Ne doit pas lever d'exception
        h1.afficherHeure();
        h2.afficherHeure();
        h3.afficherHeure();
    }

    /**
     * Test de changement de service
     */
    @Test
    public void testChangementService() {
        Horloge horloge = new Horloge("Test");

        // Premier service
        TimerService service1 = new DummyTimeServiceImpl();
        horloge.setTimerService(service1);

        // Changer de service
        TimerService service2 = new DummyTimeServiceImpl();
        horloge.setTimerService(service2);

        // Ne doit pas crasher
        horloge.afficherHeure();
    }

    /**
     * Test d'affichage sans service
     */
    @Test
    public void testAffichageSansService() {
        Horloge horloge = new Horloge("Test");

        // Afficher sans avoir défini le service
        // Ne doit pas crasher, juste afficher un message d'erreur
        horloge.afficherHeure();
    }
}