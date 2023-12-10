public class Cuoco extends Thread{
    private FastFood fastFood;

    public Cuoco(FastFood fastFood){
        this.fastFood = fastFood;
    }

    @Override
    public void run(){
        String s = "null";
        do{
            s = fastFood.cuoci();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
            }
        }while(s != null);
        return;
    }
}
