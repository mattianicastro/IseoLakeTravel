package nicastro.iseolaketravel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Battello> flotta = new ArrayList<>();
    private final ArrayList<Viaggio> viaggi = new ArrayList<>();

    public static void main(String[] args) {
        new App().run();
    }

    private void pianificaViaggio() {
        if (flotta.isEmpty()) {
            System.out.println("Impossibile pianificare un viaggio, non ci sono battelli");
            return;
        }
        Battello b = Utils.scegliElemento(scanner, "Scegli un battello con il suo numero per pianificare un viaggio", flotta);
        viaggi.add(Viaggio.creaViaggioInterattivo(scanner, b, viaggi));
    }

    private void prenotaCorsa() {
        if (viaggi.isEmpty()) {
            System.out.println("Non ci sono viaggi da prenotare");
            return;
        }
        Viaggio v = Utils.scegliElemento(scanner, "Scegli un viaggio per prenotare una corsa", viaggi);
        Prenotazione p = Prenotazione.creaPrenotazioneInterattivo(scanner, v);
        System.out.println("Prenotazione creata, controllo disponibilità...");
        v.prenota(p);
    }

    private void stampaViaggiBattello(Battello b) {
        for (Viaggio v : viaggi) {
            if (v.getId().equals(b.getId())) {
                System.out.println(v);
            }
        }
    }

    private void stampaRiepilogo() {
        for (Viaggio v : viaggi) {
            v.stampaRiepilogoPrenotazioni();
        }
    }

    public void run() {
        while (true) {
            int choice = Utils.getIntInclusoTra(scanner, """
                    1. Aggiungi un nuovo battello
                    2. Pianifica un nuovo viaggio
                    3. Visualizza i battelli
                    4. Visualizza i viaggi
                    5. Visualizza i viaggi di un battello
                    6. Prenota un viaggio
                    7. Cancella una prenotazione
                    8. Stampa riepilogo prenotazioni
                    9. Esci
                    """, 0, 9);
            switch (choice) {
                case 0: // scelta di debug
                    Battello test = new Battello("Test");
                    flotta.add(test);
                    Viaggio viaggio = new Viaggio(test, LocalDate.of(2022, 5, 18));
                    viaggi.add(viaggio);
                    viaggio.prenota(new Prenotazione(new Persona("Mario", "Rossi"), viaggio, viaggio.getTappa(scanner, "partenza", -1), viaggio.getTappa(scanner, "arrivo", 0), false));
                    break;
                case 1:
                    flotta.add(Battello.creaBattelloInterattivo(scanner));
                    break;
                case 2:
                    pianificaViaggio();
                    break;
                case 3:
                    Utils.stampaArrayNumerato(flotta);
                    break;
                case 4:
                    Utils.stampaArrayNumerato(viaggi);
                    break;
                case 5:
                    Battello b = Utils.scegliElemento(scanner, "Scegli un battello per visualizzarne i viaggi programmati", flotta);
                    stampaViaggiBattello(b);
                    break;
                case 6:
                    prenotaCorsa();
                    break;
                case 7:
                    Viaggio v = Utils.scegliElemento(scanner, "Scegli un viaggio per cancellare una prenotazione", viaggi);
                    Prenotazione p = Utils.getPrenotazione(scanner, v.getPrenotazioni());
                    p.elimina();
                    break;

                case 8:
                    stampaRiepilogo();
                    break;

                case 9:
                    System.out.println("Arrivederci");
                    return;
            }
        }
    }
}