package org.openjfx;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TestPseudo {

    @Test
    public void testSetPseudo() {
        Pseudo.setPseudo("Nathan");
        assertEquals("Nathan", Pseudo.getPseudo(), "Le pseudo devrait être 'Nathan'");
    }

    @Test
    public void testGetPseudo() {
        Pseudo.setPseudo("Alice");
        String pseudo = Pseudo.getPseudo();
        assertEquals("Alice", pseudo, "Le pseudo récupéré devrait être 'Alice'");
    }

    @Test
    public void testResetPseudo() {
        Pseudo.setPseudo("Bob");
        Pseudo.resetPseudo();
        assertEquals("", Pseudo.getPseudo(), "Après réinitialisation, le pseudo devrait être une chaîne vide");
    }

    @Test
    public void testEstDefPseudoDefini() {
        Pseudo.setPseudo("Charlie");
        assertTrue(Pseudo.estDef(), "La méthode estDef() devrait retourner true quand un pseudo est défini");
    }

    @Test
    public void testEstDefPseudoNonDefini() {
        Pseudo.resetPseudo(); // Réinitialise pour éviter un pseudo défini
        assertTrue(Pseudo.estDef(), "La méthode estDef() devrait retourner true même si le pseudo est vide");
    }

    @Test
    public void testPseudoNull() {
        Pseudo.setPseudo(null);
        assertFalse(Pseudo.estDef(), "La méthode estDef() devrait retourner false si le pseudo est null");
    }
}
