package nicastro.iseolaketravel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Battello {
    private static final int MIN_SCALI = 2;
    // per ogni scalo sarà presente una lista di orari di arrivo disponibili
    private ArrayList<Tappa> rotta = new ArrayList<>();
    private int postiScoperti;
    private int postiCoperti;
    private String nome;
    private UUID id = UUID.randomUUID();

    public Battello() {
    }

    public Battello(Battello b) {
        this.rotta = b.rotta;
        this.postiScoperti = b.postiScoperti;
        this.postiCoperti = b.postiCoperti;
        this.nome = b.nome;
        this.id = b.id;
    }

    /**
     * Costruttore di test
     *
     * @param test nome del battello di test
     */
    public Battello(String test) {
        this.rotta = new ArrayList<>();
        this.rotta.add(new Tappa(Scalo.PISOGNE, LocalTime.of(12, 0)));
        this.rotta.add(new Tappa(Scalo.LOVERE, LocalTime.of(13, 0)));
        this.rotta.add(new Tappa(Scalo.SARNICO, LocalTime.of(14, 0)));
        this.rotta.add(new Tappa(Scalo.MONTEISOLA, LocalTime.of(15, 0)));
        this.postiScoperti = 2;
        this.postiCoperti = 5;
        this.nome = test;
    }

    /**
     * Crea un battello da tastiera
     *
     * @param scanner Uno scanner pronto a leggere
     * @return Il battello creato
     */
    public static Battello creaBattelloInterattivo(Scanner scanner) {
        Battello b = new Battello();
        System.out.println("--Creazione interattiva battello--");
        System.out.println("Inserisci il nome del battello");
        b.nome = scanner.nextLine();
        b.postiScoperti = Utils.getIntMaggioreDi(scanner, "Inserisci il numero di posti non coperti", 0);
        b.postiCoperti = Utils.getIntMaggioreDi(scanner, "Inserisci il numero di posti al coperto", 0);
        b.chiediRotta(scanner);
        return b;
    }

    /**
     * Chiede all'utente di inserire la rotta del battello
     *
     * @param scanner Uno scanner pronto a leggere
     */
    private void chiediRotta(Scanner scanner) {
        System.out.println("""
                --Creazione rotta--
                """);
        boolean end = false;
        while (!end) {
            rotta.add(Tappa.creaTappaInterattivo(scanner, rotta));
            if (rotta.size() >= MIN_SCALI) {
                System.out.println("Vuoi inserire un'altra tappa? (s/n)");
                String risposta = scanner.nextLine();
                if (risposta.equals("n")) {
                    end = true;
                }
            }
        }


    }

    /**
     * Stampa la rotta del battello
     */
    public String getRiepilogoRotta() {
        StringBuilder sb = new StringBuilder();
        for (Tappa tappa : rotta) {
            sb.append(tappa.toString());
            sb.append(" | ");
        }
        return sb.toString();
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Scalo> getScali() {
        ArrayList<Scalo> scali = new ArrayList<>();
        for (Tappa tappa : rotta) {
            scali.add(tappa.getScalo());
        }
        return scali;
    }

    /**
     * Ottiene una tappa da tastiera verificando che:
     * - si trovi nell'arraylist delle tappe
     * - non si parta dall'ultima tappa
     * - non si arrivi alla prima tappa
     *
     * @param scanner  Uno scanner pronto a leggere
     * @param prompt   messaggio di prompt
     * @param position -1 se si sta creando una tappa di partenza, 0 se di arrivo, 1 se si sta creando una tappa intermedia
     * @return tappa totalmente controllata
     */
    public Tappa getTappa(Scanner scanner, String prompt, int position) {
        Tappa t = null;
        Scalo s;
        ArrayList<Scalo> scali = getScali();
        do {
            s = Utils.getScalo(scanner, prompt);
            if (s != null) {
                if (scali.contains(s)) {
                    t = rotta.get(scali.indexOf(s));
                } else {
                    System.out.println("Lo scalo non è presente nella rotta del battello selezionato");
                    continue;
                }
                // Controllo che non sia l'ultimo scalo
                if (position == -1) {
                    if (scali.indexOf(s) != scali.size() - 1) {
                        t = rotta.get(scali.indexOf(s));
                    } else {
                        System.out.println("Non puoi partire dall'ultima tappa");
                        t = null;
                    }
                    // Controllo che non sia il primo scalo
                } else if (position == 0) {
                    if (scali.indexOf(s) != 0) {
                        t = rotta.get(scali.indexOf(s));
                    } else {
                        System.out.println("Non puoi arrivare alla prima tappa");
                        t = null;
                    }
                }
            }
        } while (t == null);
        return t;
    }

    /**
     * Ottiene una tappa da tastiera verificando che la tappa sia precedente alla tappa di partenza
     *
     * @param scanner  Uno scanner pronto a leggere
     * @param prompt   Una stringa che rappresenta il prompt da stampare
     * @param partenza La tappa di partenza
     * @return La tappa ottenuta
     */
    public Tappa getTappa(Scanner scanner, String prompt, Tappa partenza) {
        Tappa t;
        do {
            t = getTappa(scanner, prompt, 0);
            if (rotta.indexOf(t) < rotta.indexOf(partenza)) {
                System.out.println("La tappa selezionata è precedente alla tappa di partenza");
                t = null;
            }
        } while (t == null);
        return t;
    }

    public int getPostiScoperti() {
        return postiScoperti;
    }

    public int getPostiCoperti() {
        return postiCoperti;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("""
                ---Battello %s---
                - ID : %s
                - Posti non coperti : %d
                - Posti coperti : %d
                - Rotta : %s
                """, nome, id, postiScoperti, postiCoperti, getRiepilogoRotta());
    }
}
