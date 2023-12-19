package br.com.casasbahia.consultador.principal;

import br.com.casasbahia.consultador.model.DadosModelo;
import br.com.casasbahia.consultador.model.DadosVeiculo;
import br.com.casasbahia.consultador.service.ConsumoApi;
import br.com.casasbahia.consultador.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private final String MARCAS = "/marcas";

    private ConsumoApi consumo = new ConsumoApi();
    private Scanner leitura = new Scanner(System.in);
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        System.out.println("Informe o veículo que deseja consultar o preço: [motos/carros/caminhoes]");
        String veiculo = leitura.nextLine();
        String json = consumo.obterDados(ENDERECO + veiculo + MARCAS);
        System.out.println(json);

//        Convertendo para veículo
        List<DadosVeiculo> automoveis = conversor.obterList(json, DadosVeiculo.class);
        automoveis.stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

//        Fase 2: Solicitando o modelo
        System.out.println("\nInforme pelo código o modelo que deseja consultar");
        int codigoModelo = leitura.nextInt();
        leitura.nextLine();
        json = consumo.obterDados(ENDERECO + veiculo + "/marcas/" + codigoModelo + "/modelos");
        System.out.println(json);

        DadosModelo modeloLista = conversor.obterDados(json, DadosModelo.class);
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

    }
}
