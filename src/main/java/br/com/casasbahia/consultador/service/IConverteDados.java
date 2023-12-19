package br.com.casasbahia.consultador.service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> tClass);

    <T> List<T> obterList(String json, Class<T> tList);
}
