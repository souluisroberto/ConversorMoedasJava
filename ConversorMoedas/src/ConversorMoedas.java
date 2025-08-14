import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConversorMoedas {
    private static final String API_KEY = "SUA_CHAVE_API"; // Substitua pela sua chave da API
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD"; // Exemplo de URL

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha a conversão:");
        System.out.println("1. Dólar para Real");
        System.out.println("2. Real para Dólar");
        // Add mais opções conforme necessário

        int opcao = scanner.nextInt();
        System.out.print("Digite o valor: ");
        double valor = scanner.nextDouble();

        double resultado = 0;
        switch (opcao) {
            case 1:
                resultado = converterDolarParaReal(valor);
                System.out.println(valor + " USD = " + resultado + " BRL");
                break;
            case 2:
                resultado = converterRealParaDolar(valor);
                System.out.println(valor + " BRL = " + resultado + " USD");
                break;
            // Add mais casos conforme necessário
            default:
                System.out.println("Opção inválida.");
        }
        scanner.close();
    }

    private static double converterDolarParaReal(double valor) {
        double taxa = obterTaxaCambio("BRL");
        return valor * taxa;
    }

    private static double converterRealParaDolar(double valor) {
        double taxa = obterTaxaCambio("BRL");
        return valor / taxa;
    }

    private static double obterTaxaCambio(String moeda) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + API_KEY);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            return jsonObject.getAsJsonObject("rates").get(moeda).getAsDouble();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
