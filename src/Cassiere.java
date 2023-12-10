public class Cassiere extends Thread{
    private FastFood fastFood;
    private static int index = 1;

    public Cassiere(FastFood fastFood){
        this.fastFood = fastFood;
    }

    @Override
    public void run(){
        boolean b = true;
        do{
            synchronized(Cassiere.class){
                b = fastFood.inserisciOrdine(Integer.toString(index));
                index++;
            }
        }while(b);
        return;
    }   
}
