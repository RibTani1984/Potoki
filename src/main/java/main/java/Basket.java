package main.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Basket {
    public String[] products;
    public int[] prices;
    protected int[] basket;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.basket = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        basket[productNum] += amount;
    }

    public void printCart() {
        int sumProducts = 0;
        System.out.println("\n" + "Список ваших покупок: ");
        for (int i = 0; i < products.length; i++) {
            int total = prices[i] * basket[i];
            sumProducts += total;
            if (basket[i] != 0)
                System.out.println(products[i] + " - " + basket[i] + " шт. " + "(" + prices[i] + " руб./шт.) " +
                        (" в сумме: " + basket[i] * prices[i]) + " рублей");
        }
        System.out.println("\n" + "Итоговая сумма покупки: " + sumProducts + " рублей");
    }

    public void saveTxt(File textFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (var purchase = 0; purchase < products.length; purchase++) {
                out.print(products[purchase] + "," + basket[purchase] + "," + prices[purchase] + "\n");
            }
            System.out.println("Информация успешно сохранена");
        } catch (IOException e) {
            System.out.println("Сохранить информацию о покупке в файл \"basket.txt\" не удалось!");
        }
    }

    public Basket() {
    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String productsS = bufferedReader.readLine();
            String pricesS = bufferedReader.readLine();
            String basketS = bufferedReader.readLine();
            basket.products = productsS.split(" ");
            basket.prices = Arrays.stream(pricesS.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            basket.basket = Arrays.stream(basketS.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            return basket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveJSON(File file) {
        try (PrintWriter x = new PrintWriter(file)) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            String json = gson.toJson(this);
            x.print(json);
            System.out.println("Информация о покупках сохранена");
        } catch (IOException e) {
            System.out.println("Информация о покупке в \"basket.json\" не сохранена!");
        }
    }

    public static Basket loadFromJSONFile(File file) throws IOException {
        Basket basket;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(), Basket.class);
        }
        return basket;
    }
}
