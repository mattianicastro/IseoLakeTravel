package nicastro.iseolaketravel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Viaggio extends Battello {
    private final LocalDate data;
    private final ArrayList<Prenotazione> prenotazioni;

    public Viaggio(Battello battello, LocalDate data) {
        super(battello);
        this.data = data;
        this.prenotazioni = new ArrayList<>();
    }

    /**
     * Crea un viaggio da tastiera controllando che la data che non esista un viaggio con la stessa data e che essa sia
     * successiva alla data odierna.
     *
     * @param scanner  Uno scanner pronto a leggere
     * @param battello Il battello da associare al viaggio
     * @param viaggi   ArrayList di viaggi esistenti
     * @return Il viaggio creato
     */
    public static Viaggio creaViaggioInterattivo(Scanner scanner, Battello battello, ArrayList<Viaggio> viaggi) {
        boolean end = false;
        LocalDate data = null;
        while (!end) {
            data = Utils.getData(scanner, "Inserisci la data del viaggio (gg/MM/YYY): ");
            if (Utils.dataPresente(data, viaggi)) {
                System.out.println("Data già presente");
                continue;
            }
            if (data.isBefore(LocalDate.now())) {
                System.out.println("Data precedente alla data odierna");
                continue;
            }
            end = true;
        }
        System.out.printf("Viaggio per il battello %s in data %s creato\n", battello.getNome(), data);
        return new Viaggio(battello, data);
    }

    public ArrayList<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    /**
     * Ottiene tutte le prenotazioni che includono il passaggio per lo scalo specificato
     *
     * @param scalo   Scalo da controllare
     * @param coperti Se controllare i posti coperti o i posti scoperti
     * @return
     */
    private ArrayList<Prenotazione> getPrenotazioniPerScalo(Scalo scalo, boolean coperti) {
        ArrayList<Prenotazione> res = new ArrayList<>();
        int indexScalo = getScali().indexOf(scalo);

        for (Prenotazione p : prenotazioni) {
            if (p.isCoperto() == coperti) {
                int indexPartenza = getScali().indexOf(p.getPartenza().getScalo());
                int indexArrivo = getScali().indexOf(p.getArrivo().getScalo());
                if (indexPartenza <= indexScalo && indexArrivo >= indexScalo) {
                    res.add(p);
                }
            }
        }
        return res;
    }

    public boolean eliminaPrenotazione(Prenotazione prenotazione) {
        return prenotazioni.remove(prenotazione);
    }

    private int getPostiScopertiDisponibili(Scalo partenza) {
        ArrayList<Prenotazione> p = getPrenotazioniPerScalo(partenza, false);
        return getPostiScoperti() - p.size();
    }

    private int getPostiCopertiDisponibili(Scalo partenza) {
        ArrayList<Prenotazione> p = getPrenotazioniPerScalo(partenza, true);
        return getPostiCoperti() - p.size();
    }

    /**
     * Prenota un viaggio controllando che ci siano posti disponibili
     *
     * @param prenotazione Un oggetto prenotazione completo
     */
    public void prenota(Prenotazione prenotazione) {
        if (prenotazione.isCoperto()) {
            int postiCoperti = getPostiCopertiDisponibili(prenotazione.getPartenza().getScalo());
            System.out.printf("Posti coperti disponibili: %d\n", postiCoperti);
            if (postiCoperti > 0) {
                prenotazioni.add(prenotazione);
                System.out.println("Prenotazione confermata.");
                System.out.println(prenotazione);
            } else {
                System.out.println("Non ci sono posti coperti disponibili");
            }
        } else {
            int postiScoperti = getPostiScopertiDisponibili(prenotazione.getPartenza().getScalo());
            System.out.printf("Posti scoperti disponibili: %d\n", postiScoperti);
            if (postiScoperti > 0) {
                prenotazioni.add(prenotazione);
                System.out.println("Prenotazione confermata.");
                System.out.println(prenotazione);
            } else {
                System.out.println("Non ci sono posti scoperti disponibili");
            }
        }
    }

    public LocalDate getData() {
        return data;
    }

    public void stampaRiepilogoPrenotazioni() {
        System.out.printf("Prenotazioni per il viaggio del %s sul battello %s\n", data, getNome());
        System.out.println("Prenotazioni : " + prenotazioni.size());
        for (Prenotazione p : prenotazioni) {
            System.out.println(p);
        }
        System.out.println("Posti disponibili :");
        for (Scalo s : getScali()) {
            int postiCoperti = getPostiCopertiDisponibili(s);
            int postiScoperti = getPostiScopertiDisponibili(s);
            int postiCopertiTotali = getPostiCoperti();
            int postiScopertiTotali = getPostiScoperti();
            double percentualeCoperti = (double) postiCoperti / postiCopertiTotali * 100;
            double percentualeScoperti = (double) postiScoperti / postiScopertiTotali * 100;
            System.out.printf("Scalo %s : Posti coperti : %d/%d (%,.2f%%) | Posti scoperti : %d/%d (%,.2f%%)\n",
                    s, postiCoperti, postiCopertiTotali, percentualeCoperti, postiScoperti, postiScopertiTotali, percentualeScoperti);
        }
    }

    @Override
    public String toString() {
        return String.format("Viaggio in data %s per il seguente battello (%d prenotazioni) :\n" +
                "%s", data, prenotazioni.size(), super.toString());
    }
}
