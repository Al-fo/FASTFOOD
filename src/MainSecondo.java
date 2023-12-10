public class MainSecondo {
    public static void main(String[] args) {
        FastFoodSecondo fastFood = new FastFoodSecondo(50);

        for(int i = 0; i < 3; i++){
            new Cuoco(fastFood).start();
            new Cassiere(fastFood).start();
        }
    }
}
