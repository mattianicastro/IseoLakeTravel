package nicastro.iseolaketravel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Tappa {
    private final Scalo scalo;
    private final LocalTime orario;

    public Tappa(Scalo scalo, LocalTime orari) {
        this.scalo = scalo;
        this.orario = orari;
    }

    /**
     * Crea una tappa da tastiera controllando che sia diversa dall'ultima inserita
     *
     * @param scanner Uno scanner pronto a leggere
     * @param rotta   Arraylist di tappe, per controllare l'ultima tappa
     * @return La tappa creata
     */
    public static Tappa creaTappaInterattivo(Scanner scanner, ArrayList<Tappa> rotta) {
        Scalo scalo;
        Scalo ultimoScalo;
        do {
            scalo = Utils.getScalo(scanner, "Inserisci il nome dello scalo: ");
            if (!rotta.isEmpty()) {
                ultimoScalo = rotta.get(rotta.size() - 1).getScalo();
                if (ultimoScalo.equals(scalo)) {
                    System.out.println("Lo scalo deve essere diverso dall'ultimo inserito");
                    continue;
                }
            }
            break;
        } while (true);

        boolean end = false;
        LocalTime orario = null;
        // bisogna controllare se l'orario è maggiore di quello delle altre tappe
        while (!end) {
            orario = Utils.getTime(scanner, String.format("Inserisci l'orario di arrivo a %s", scalo));
            if (!rotta.isEmpty()) {
                Tappa ultimaTappa = rotta.get(rotta.size() - 1);
                if (orario.compareTo(ultimaTappa.getOrario()) <= 0) {
                    System.out.printf("L'orario inserito è precedente a quello della scorsa tappa %s\n", ultimaTappa.getScalo());
                    continue;
                }
            }
            end = true;
        }
        return new Tappa(scalo, orario);
    }

    public Scalo getScalo() {
        return scalo;
    }

    public LocalTime getOrario() {
        return orario;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", scalo, orario);
    }
}
