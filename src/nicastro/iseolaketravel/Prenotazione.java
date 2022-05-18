package nicastro.iseolaketravel;

import java.util.Scanner;
import java.util.UUID;

public class Prenotazione {
    private final Persona persona;
    private final Viaggio viaggio;
    private final Tappa partenza;
    private final Tappa arrivo;
    private final boolean coperto;
    private final UUID id = UUID.randomUUID();


    public Prenotazione(Persona persona, Viaggio viaggio, Tappa partenza, Tappa arrivo, boolean coperto) {
        this.persona = persona;
        this.viaggio = viaggio;
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.coperto = coperto;
    }

    /**
     * Creazione di una prenotazione da tastiera
     *
     * @param scanner Uno scanner pronto a leggere
     * @param viaggio Il viaggio da prenotare
     * @return La prenotazione creata
     */
    public static Prenotazione creaPrenotazioneInterattivo(Scanner scanner, Viaggio viaggio) {
        Persona persona = Persona.creaPersonaInterattivo(scanner);
        Tappa partenza = viaggio.getTappa(scanner, "Inserisci lo scalo di partenza", -1);
        Tappa arrivo = viaggio.getTappa(scanner, "Inserisci lo scalo di arrivo", partenza);
        System.out.println("Il posto deve essere coperto? (s/n)");
        boolean coperto = scanner.nextLine().equals("s");
        return new Prenotazione(persona, viaggio, partenza, arrivo, coperto);

    }

    public void elimina() {
        if (viaggio.eliminaPrenotazione(this)) {
            System.out.println("Prenotazione eliminata con successo");
        } else {
            System.out.println("Prenotazione non trovata");
        }
    }

    public boolean isCoperto() {
        return coperto;
    }

    public Tappa getPartenza() {
        return partenza;
    }

    public Tappa getArrivo() {
        return arrivo;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("""
                ---
                ID PRENOTAZIONE: %s
                UTENTE: %s
                DATA : %s
                BATTELLO : %s
                ORA PARTENZA: %s - %s
                ORA ARRIVO: %s - %s
                ---
                            """, id, persona, viaggio.getData(), viaggio.getNome(), partenza.getOrario(), partenza.getScalo(), arrivo.getOrario(), arrivo.getScalo());
    }

}
