package nicastro.iseolaketravel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Utils {

    /**
     * Ottiene un intero da tastiera, controllando che sia maggiore di min
     *
     * @param scanner Uno scanner pronto a leggere
     * @param prompt  Una stringa che rappresenta il prompt da stampare
     * @param min     Il minimo valore accettato
     * @return Un intero sempre maggiore o uguale a 0
     */
    public static int getIntMaggioreDi(Scanner scanner, String prompt, int min) {
        int res = -1;
        do {
            System.out.println(prompt);
            try {
                res = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input non valido, riprova.");
            }
            scanner.nextLine();
        } while (res < min);

        return res;
    }

    /**
     * Ottiene un intero da tastiera, controllando che sia compreso tra min e max
     *
     * @param scanner Uno scanner pronto a leggere
     * @param prompt  Una stringa che rappresenta il prompt da stampare
     * @param min     Il minimo valore accettato
     * @param max     Il massimo valore accettato
     * @return un intero compreso tra min e max
     */
    public static int getIntInclusoTra(Scanner scanner, String prompt, int min, int max) {
        int res = -1;
        do {
            System.out.println(prompt);
            try {
                res = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input non valido, riprova.");
            }
            scanner.nextLine();
        } while (res < min || res > max); // se non trova il valore, restituisce -1

        return res;
    }

    /**
     * @param scanner Uno scanner pronto a leggere
     * @param prompt  Una stringa che rappresenta il prompt da stampare
     * @return Un LocalTime valido, non null
     */
    public static LocalTime getTime(Scanner scanner, String prompt) {
        LocalTime res = null;
        do {
            System.out.println(prompt);
            try {
                res = LocalTime.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Input non valido, riprova.");
            }
        } while (res == null);

        return res;
    }

    /**
     * @param scanner Uno scanner pronto a leggere
     * @param prompt  Una stringa che rappresenta il prompt da stampare
     * @return Un LocalDate valido, non null
     */
    public static LocalDate getData(Scanner scanner, String prompt) {
        LocalDate res = null;
        do {
            System.out.println(prompt);
            try {
                // formatta dd/MM/yyyy
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                res = LocalDate.parse(scanner.nextLine(), formatter);

            } catch (DateTimeParseException e) {
                System.out.println("Input non valido, riprova.");
            }
        } while (res == null);

        return res;
    }

    /**
     * Controlla se la data inserita è presente in un arraylist di viaggi
     *
     * @param data La data da controllare
     * @param date ArrayList di Viaggi
     * @return true se la data è presente, false altrimenti
     */
    public static boolean dataPresente(LocalDate data, ArrayList<Viaggio> date) {
        for (Viaggio v : date) {
            if (v.getData().equals(data))
                return true;
        }
        return false;
    }

    /**
     * @param scanner Uno scanner pronto a leggere
     * @param prompt  Una stringa che rappresenta il prompt da stampare
     * @return Uno Scalo valido
     */
    public static Scalo getScalo(Scanner scanner, String prompt) {
        Scalo res = null;
        do {
            System.out.println(prompt);
            try {
                res = Scalo.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Input non valido, riprova.");
            }
        } while (res == null);

        return res;
    }

    /**
     * Stampa un arrau numerando ogni elemento
     *
     * @param array Un array di oggetti T
     * @param <T>   Tipo degli oggetti nell'array
     */
    public static <T> void stampaArrayNumerato(ArrayList<T> array) {
        if (array.isEmpty()) {
            System.out.println("Non ci sono elementi da mostrare.");
            return;
        }
        for (T b : array) {
            System.out.println(array.indexOf(b) + ")\n" + b);
        }
    }

    /**
     * Ottiene un UUID valido da tastiera
     *
     * @param scanner Uno scanner pronto a leggere
     * @param prompt  Una stringa che rappresenta il prompt da stampare
     * @return un UUID valido
     */
    public static UUID getUUID(Scanner scanner, String prompt) {
        UUID res = null;
        boolean success = false;
        while (!success) {
            System.out.println(prompt);
            try {
                res = UUID.fromString(scanner.nextLine());
                success = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Input non valido. Prova ancora.");
            }
        }
        return res;
    }

    /**
     * Ottiene una prenotazione dal suo id chiesto da tastiera
     *
     * @param scanner      Uno scanner pronto a leggere
     * @param prenotazioni ArrayList di Prenotazioni
     * @return la prenotazione richiesta, null se non esiste
     */
    public static Prenotazione getPrenotazione(Scanner scanner, ArrayList<Prenotazione> prenotazioni) {
        UUID id = getUUID(scanner, "Inserisci l'id della prenotazione");
        Prenotazione res = null;
        for (Prenotazione p : prenotazioni) {
            if (p.getId().equals(id)) {
                res = p;
            }
        }

        return res;
    }


    /**
     * Stammpa l'array numerato e chiede all'utente di scegliere un elemento dal suo numero
     *
     * @param scanner Uno scanner pronto a leggere
     * @param prompt  Una stringa che rappresenta il prompt da stampare
     * @param array   Un array di oggetti T
     * @param <T>     Tipo degli oggetti nell'array
     * @return L'oggetto selezionato
     */
    public static <T> T scegliElemento(Scanner scanner, String prompt, ArrayList<T> array) {
        stampaArrayNumerato(array);
        int choice = Utils.getIntInclusoTra(scanner, prompt, 0, array.size() - 1);
        return array.get(choice);
    }


}
