import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FastFoodPrimo extends FastFood{
    Map<String, Boolean> ordini;
    private enum Apertura{
        APERTO,
        CHIUSO,
        QUASI_CHIUSO
    };
    private Apertura apertura;
    private int maxOrdini;


    public FastFoodPrimo(int maxOrdini){
        ordini = new HashMap<>(maxOrdini);
        apertura = Apertura.APERTO; 
        this.maxOrdini = maxOrdini;
    }

    @Override
    public synchronized boolean inserisciOrdine(String ordine){
        if(apertura == Apertura.APERTO){
            System.out.println("Inserisco l'ordine " + ordine);
            while(ordini.containsKey(ordine))
                ordine = ordine + "i";

            ordini.put(ordine, false);
            if(pieno()) {
                apertura = Apertura.QUASI_CHIUSO;
                System.out.println("Capacità ordini finita, tra poco chiudiamo"); 
            }
            notifyAll();
            return true;
        }
        return false;
    }

    @Override
    public String cuoci(){
        String ordineCotto = null;
        synchronized(this){
            if(apertura != Apertura.CHIUSO){
                while(nienteDaCucinare()) {
                    if(apertura == Apertura.QUASI_CHIUSO){
                        System.out.println("FastFood Chiuso");
                        apertura = Apertura.CHIUSO;
                        return null;
                    }
                    try {
                        wait();
                    } catch (InterruptedException e) {

                    }
                }
                ordineCotto = findKey();
                ordini.put(ordineCotto,true);
                System.out.println("Ordine " + ordineCotto + " cucinato");
                notifyAll();
                return ordineCotto;
            }
        }
        return null;
    }

    private String findKey(){
        int i = 0;
        Set<String> chiavi = ordini.keySet();
        ArrayList<String> listaChiavi = new ArrayList<>(chiavi);
        while(ordini.get(listaChiavi.get(i))){
            i++;
        }
        return listaChiavi.get(i);
    }

    private boolean nienteDaCucinare(){
        return !(ordini.containsValue(false));
    }

    private boolean pieno(){
        return ordini.size() == maxOrdini;
    }
}
