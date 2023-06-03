package main.java;

import java.io.File;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        File file = new File("basket.txt");
        File csvFile = new File("log.csv");
        File jsonFile = new File("basket.json");
        String[] products = {"Батон", "Сырники", "Пельмени", "Мука", "Творог"};
        int[] prices = {50, 220, 280, 80, 90};

        Basket basket = null;
        if (file.exists()) {
            basket = Basket.loadFromJSONFile(jsonFile);
        } else {
            basket = new Basket(products, prices);
        }
        System.out.println("Список возможных товаров для покупки:");
        IntStream
                .range(0, products.length)
                .forEach(i1 -> System.out.println((i1 + 1) + " " + products[i1] + ":  " + prices[i1] + " " + " руб/шт "));

        ClientLog clientLog = new ClientLog();

        while (true) {
            System.out.println("Выберите необходимый товар и количество через пробел или введите 'end'");
            String line = scanner.nextLine();
            if ("end".equals(line)) {
                clientLog.exportAsCSV(csvFile);
                break;
            }
            String[] parts = line.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCont = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCont);
            clientLog.log(productNumber + 1, productCont);
            basket.saveJSON(jsonFile);
        }
        basket.printCart();
    }
}

