package nicastro.iseolaketravel;

import java.util.Scanner;

public class Persona {
    private final String nome;
    private final String cognome;

    public Persona(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    /**
     * Crea una persona da tastiera
     *
     * @param scanner Uno scanner pronto a leggere
     * @return la persona creata
     */
    public static Persona creaPersonaInterattivo(Scanner scanner) {
        System.out.println("Inserisci il nome dell'utente");
        String nome = scanner.nextLine();
        System.out.println("Inserisci il cognome dell'utente");
        String cognome = scanner.nextLine();
        return new Persona(nome, cognome);
    }

    @Override
    public String toString() {
        return nome + " " + cognome;
    }
}
