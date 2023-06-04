package main.java;

import java.io.File;
import java.io.IOException;
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

        ConfigSettings settings = new ConfigSettings(new File("shop.xml"));
        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.saveFile);
        File logFile = new File(settings.logFile);
        Basket basket = createBasket(loadFile, settings.enabledLoad, settings.loadFormat);
        ClientLog clientLog = new ClientLog();
        if (file.exists()) {
            basket = Basket.loadFromJSONFile(jsonFile);
        } else {
            basket = new Basket(products, prices);
        }
        System.out.println("Список возможных товаров для покупки:");
        IntStream
                .range(0, products.length)
                .forEach(i1 -> System.out.println((i1 + 1) + " " + products[i1] + ":  " + prices[i1] + " " + " руб/шт "));

        while (true) {
            System.out.println("Выберите необходимый товар и количество через пробел или введите 'end'");
            String line = scanner.nextLine();
            if ("end".equals(line)) {
                if (settings.enabledLog) {
                    clientLog.exportAsCSV(logFile);
                }
                break;
            }
            String[] parts = line.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCont = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCont);
            if (settings.enabledLog) {
                clientLog.log(productNumber + 1, productCont);
            }
            if (settings.enabledSave) {
                switch (settings.saveFormat) {
                    case "json" -> basket.saveJSON(saveFile);
                    case "txt" -> basket.saveTxt(saveFile);
                }
            }
        }
        basket.printCart();
    }

    private static Basket createBasket(File loadFile, boolean enabledLoad, String loadFormat) throws IOException {
        Basket basket;
        if (enabledLoad && loadFile.exists()) {
            basket = switch (loadFormat) {
                case "json" -> Basket.loadFromJSONFile(loadFile);
                case "txt" -> Basket.loadFromTxtFile(loadFile);
                default -> new Basket();
            };
        } else {
            basket = new Basket();
        }
        return basket;
    }
}



