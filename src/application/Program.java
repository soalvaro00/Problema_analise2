package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] agrs){
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String filePath = sc.nextLine();

        List<Sale> sales = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                Integer month = Integer.parseInt(fields[0].trim());
                Integer year = Integer.parseInt(fields[1].trim());
                String seller = fields[2].trim();
                Integer items = Integer.parseInt(fields[3].trim());
                Double total = Double.parseDouble(fields[4].trim());
                sales.add(new Sale(month, year, seller, items, total));
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Map<String, Double> totalBySeller = sales.stream()
                .collect(Collectors.groupingBy(Sale::getSeller, Collectors.summingDouble(Sale::getTotal)));

        System.out.println("\nTotal de vendas por vendedor:");
        totalBySeller.forEach((seller, total) -> System.out.printf("%s - R$ %.2f%n", seller, total));


        sc.close();
    }
}
