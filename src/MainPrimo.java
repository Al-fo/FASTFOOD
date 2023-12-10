public class MainPrimo {
    public static void main(String[] args) {
        FastFoodPrimo fastFood = new FastFoodPrimo(50);

        Cassiere c1 = new Cassiere(fastFood);
        Cassiere c2 = new Cassiere(fastFood);
        Cassiere c3 = new Cassiere(fastFood);

        Cuoco cu1 = new Cuoco(fastFood);
        Cuoco cu2 = new Cuoco(fastFood);
        Cuoco cu3 = new Cuoco(fastFood);

        c1.start();
        c2.start();
        c3.start();

        cu1.start();
        cu2.start();

        try {
            c1.join();
            c2.join();
            c3.join();
        } catch (InterruptedException e) {
        }

        cu3.start();
    }
}
