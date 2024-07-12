package com.dc.ufscar.compiladores.jander.gerador;

import java.util.HashMap;

public class TabelaDeSimbolos {
    public enum TipoJander {
        LITERAL,
        INTEIRO,
        REAL,
        LOGICO,
        REGISTRO,
        INVALIDO,
        VOID
    }

    private final HashMap<String, EntradaTabelaDeSimbolos> tabelaDeSimbolos;

    public TabelaDeSimbolos() {
        this.tabelaDeSimbolos = new HashMap<>();
    }

    public void adicionar(String nome, TipoJander tipo) {
        tabelaDeSimbolos.put(nome, new EntradaTabelaDeSimbolos(nome, tipo));
    }

    public boolean existe(String nome) {
        return tabelaDeSimbolos.containsKey(nome);
    }

    public TipoJander verificar(String nome) {
        return tabelaDeSimbolos.get(nome).tipo;
    }

    public Boolean verificarPonteiro(String nome) {
        return tabelaDeSimbolos.get(nome).ponteiro;
    }

    public TabelaDeSimbolos verificarRegistro(String nome) {
        return tabelaDeSimbolos.get(nome).tabelaRegistro;
    }

    public void adicionarRegistro(String nome, TabelaDeSimbolos tabelaRegistro) {
        tabelaDeSimbolos.get(nome).tabelaRegistro = tabelaRegistro;
    }

    public void adicionarParametro(String nome, String parametro) {
        tabelaDeSimbolos.get(nome).parametros.add(parametro);
    }

    public String pegarParametro(String nome, int posicao) {
        return tabelaDeSimbolos.get(nome).parametros.get(posicao);
    }

    public int pegarQuantidadeParametros(String nome) {
        return tabelaDeSimbolos.get(nome).parametros.size();
    }
}
