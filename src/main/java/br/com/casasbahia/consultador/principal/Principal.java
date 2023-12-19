package br.com.casasbahia.consultador.principal;

import br.com.casasbahia.consultador.model.DadosAnos;
import br.com.casasbahia.consultador.model.DadosFinal;
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
    private String url_base;

    private ConsumoApi consumo = new ConsumoApi();
    private Scanner leitura = new Scanner(System.in);
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        System.out.println("Informe o veículo que deseja consultar o preço: [motos/carros/caminhoes]");
        String veiculo = leitura.nextLine();
        url_base = ENDERECO + veiculo + MARCAS;
        String json = consumo.obterDados(url_base);
//        System.out.println(json);

//        Convertendo para veículo
        List<DadosVeiculo> automoveis = conversor.obterList(json, DadosVeiculo.class);
        automoveis.stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

//        Fase 2: Solicitando o modelo
        System.out.println("\nInforme pelo código a marca que deseja consultar");
        int codigoMarca = leitura.nextInt();
        leitura.nextLine();
        url_base = url_base + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(url_base);
//        System.out.println(json);

        DadosModelo modeloLista = conversor.obterDados(json, DadosModelo.class);
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

//        Fase 3: Solicitando o ano que deseja saber o veículo ao ter o código do modelo informado.
        System.out.println("\nQual modelo você deseja selecionar? Informe pelo código: ");
        int codigoModelo = leitura.nextInt();
        leitura.nextLine();
        url_base = url_base + "/" + codigoModelo + "/anos";

        json = consumo.obterDados(url_base);
//        System.out.println(json);
//        Convertendo para DadosAnos

        List<DadosAnos> anos = conversor.obterList(json, DadosAnos.class);
        anos.stream()
                .sorted(Comparator.comparing(DadosAnos::codigo))
                .forEach(System.out::println);

//        Fase 4: Imprimindo o veículo selecionado junto com o valor respectivo dele.
        System.out.println("\nQual ano deseja selecionar? Informe pelo código: ");
        String codigoAno = leitura.nextLine();
        url_base = url_base + "/" + codigoAno;
        json = consumo.obterDados(url_base);
//        System.out.println(json);

        DadosFinal resultado = conversor.obterDados(json, DadosFinal.class);
        System.out.println(resultado);

    }
}
