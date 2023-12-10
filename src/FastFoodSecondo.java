public class FastFoodSecondo extends FastFood{
    String[] ordini;
    private static final String ANSII_RED = "\u001B[31m";
    private static final String ANSII_BLUE = "\u001B[34m";
    private static final String ANSII_RESET = "\u001B[37m";
    int nextCucinato = 0;

    public FastFoodSecondo(int maxOrdini){
        ordini = new String[maxOrdini];
    }

    @Override
    public boolean inserisciOrdine(String s){
        synchronized(this){
            while(pieno()){
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
            try{
                ordini[findIndexLibero()] = s;
                System.out.println(ANSII_BLUE + "Ordine " + s + " aggiunto" + ANSII_RESET);
            }catch(IndexOutOfBoundsException e){
                System.err.println("ERROR: [-1 INDEX 22]");
            }
            notifyAll();
        }
        return true;
    }

    @Override
    public String cuoci(){
        String ordine = "";
        synchronized(this){
            while(vuoto()){
                try {
                    wait();
                } catch (InterruptedException e) {
                    
                }
            }
            try{
                do{
                    ordine = ordini[nextCucinato];
                    ordini[nextCucinato] = null;
                    nextCucinato = (nextCucinato + 1) % ordini.length;
                }while(ordine == null);
                System.err.println(ANSII_RED + "Ordine " + ordine + " cucinato" + ANSII_RESET);
            }catch(IndexOutOfBoundsException e){
                System.err.println("ERROR: [-1 INDEX 43]");
            }
            notifyAll();
        }
        return ordine;
    }

    private int findIndexLibero(){
        int i = 0;
        for(String s: ordini){
            if(s == null) return i;
            i++;
        }
        return -1;
    }

    private boolean pieno(){
        for(int i = 0; i < ordini.length; i++){
            if(ordini[i] == null) return false;
        }
        return true;
    }

    private boolean vuoto(){
        for(String s: ordini){
            if(s != null) return false;
        }
        return true;
    }
}