package br.com.casasbahia.consultador.principal;

import br.com.casasbahia.consultador.model.Dados;
import br.com.casasbahia.consultador.model.DadosAnos;
import br.com.casasbahia.consultador.model.DadosModelo;
import br.com.casasbahia.consultador.model.Veiculo;
import br.com.casasbahia.consultador.service.ConsumoApi;
import br.com.casasbahia.consultador.service.ConverteDados;

import java.util.ArrayList;
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
        String resposta = leitura.nextLine();
        url_base = ENDERECO + resposta + MARCAS;
        String json = consumo.obterDados(url_base);
//        System.out.println(json);

//        Convertendo para veículo
        List<Dados> automoveis = conversor.obterList(json, Dados.class);
        automoveis.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

//        Fase 2: Solicitando o modelo
        System.out.println("\nInforme pelo código a marca que deseja consultar");
        int codigoMarca = leitura.nextInt();
        leitura.nextLine();
        url_base = url_base + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(url_base);
        DadosModelo modeloLista = conversor.obterDados(json, DadosModelo.class);
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

//        Filtrando os veículos por busca para facilitar na escolha do usuário
        System.out.println("Informe o trecho do veículo que deseja pesquisar: ");
        String veiculoFiltro = leitura.nextLine();
        modeloLista.modelos().stream()
                .filter(e -> e.nome().toLowerCase().contains(veiculoFiltro.toLowerCase()))
                        .sorted(Comparator.comparing(Dados::codigo))
                                .forEach(System.out::println);

//        Fase 3: Solicitando o ano que deseja saber o veículo ao ter o código do modelo informado.
        System.out.println("\nQual modelo você deseja selecionar? Informe pelo código: ");
        int codigoModelo = leitura.nextInt();
        leitura.nextLine();
        url_base = url_base + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(url_base);
        String endereco = url_base;
        Veiculo veiculo = null;
//        Convertendo para DadosAnos
        List<DadosAnos> anos = conversor.obterList(json, DadosAnos.class);
        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            String anosListados = anos.get(i).codigo();
            url_base = endereco + "/" + anosListados;
            json = consumo.obterDados(url_base);
            veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        veiculos.stream()
                .forEach(System.out::println);

    }
}
