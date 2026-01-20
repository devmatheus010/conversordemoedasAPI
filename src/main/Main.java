package main;

import service.ExchangeRateService;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExchangeRateService service = new ExchangeRateService();

        System.out.println("💱 Seja bem-vindo ao Conversor de Moedas\n");

        Map<String, Double> moedas = service.buscarMoedasFiltradas();

        if (moedas.isEmpty()) {
            System.out.println("Não foi possível carregar as moedas da API.");
            return;
        }

        while (true) {

            System.out.println("\nMoedas disponíveis:");
            moedas.keySet().forEach(codigo -> System.out.print(codigo + "  "));
            System.out.println();

            System.out.print("\nDigite a moeda BASE (ou 'sair'): ");
            String base = sc.next().toUpperCase();

            if (base.equals("SAIR")) {
                System.out.println("Encerrando o programa...");
                break;
            }

            System.out.print("Digite a moeda DESTINO: ");
            String destino = sc.next().toUpperCase();

            if (!moedas.containsKey(base) || !moedas.containsKey(destino)) {
                System.out.println("Moeda inválida. Tente novamente.");
                continue;
            }

            System.out.print("Digite o valor para converter: ");
            double valor = sc.nextDouble();

            double taxaBase = moedas.get(base);
            double taxaDestino = moedas.get(destino);

            double resultado = valor * (taxaDestino / taxaBase);

            System.out.printf(
                    "Resultado: %.2f %s = %.2f %s%n",
                    valor, base, resultado, destino
            );
        }

        sc.close();
    }
}
