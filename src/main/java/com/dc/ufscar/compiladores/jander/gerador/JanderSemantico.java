package com.dc.ufscar.compiladores.jander.gerador;

import com.dc.ufscar.compiladores.jander.gerador.TabelaDeSimbolos.TipoJander;

public class JanderSemantico extends JanderBaseVisitor<Void> {
    TabelaDeSimbolos tabelaGlobal;
    Escopos escoposAninhados;
    boolean ehFuncao = false;

    int qtdEscopos = 0;

    // aqui temos uma tabela, a global, precisamos ver
    // como faremos em relação a tabela de escopos
    @Override
    public Void visitPrograma(JanderParser.ProgramaContext ctx) {
        // tabela = new TabelaDeSimbolos();
        escoposAninhados = new Escopos();
        tabelaGlobal = escoposAninhados.obterEscopoAtual();
        System.out.println("TABELA GLOBAL:" + tabelaGlobal.toString());
        return super.visitPrograma(ctx);
    }

    @Override
    public Void visitDeclaracao_local(JanderParser.Declaracao_localContext ctx) {
        TabelaDeSimbolos tabela = escoposAninhados.obterEscopoAtual();
        if (ctx.variavel() != null) {
            return visitVariavel(ctx.variavel());
        } else if (ctx.const_ != null) {
            String nomeVar = ctx.IDENT().getText();
            String strTipoVar = ctx.tipo_basico().getText();
            TipoJander tipoVar = JanderSemanticoUtils.getTipo(strTipoVar);
            System.out.println("Declaracao Local: " + nomeVar + strTipoVar);
            if (tabela.existe(nomeVar)) {
                JanderSemanticoUtils.adicionarErroSemantico(ctx.IDENT().getSymbol(),
                        "Variável " + nomeVar + " já existe");
            } else {
                tabela.adicionar(nomeVar, tipoVar);
            }
        } else if (ctx.type != null) {
            String nomeVar = ctx.IDENT().getText();
            String strTipoVar = ctx.tipo().getText();
            System.out.println("Declaracao Local: " + nomeVar + strTipoVar);
            if (strTipoVar.contains("registro")) {
                if (tabela.existe(nomeVar)) {
                    JanderSemanticoUtils.adicionarErroSemantico(ctx.IDENT().getSymbol(),
                            "Variável " + nomeVar + " já existe");
                } else {
                    tabela.adicionar(nomeVar, TipoJander.REGISTRO);
                }
                TabelaDeSimbolos tabelaRegistro = new TabelaDeSimbolos();
                for (var v : ctx.tipo().registro().variavel()) {
                    String strTipoRegistro = v.tipo().getText();
                    TipoJander tipoRegistro = JanderSemanticoUtils
                            .getTipo(strTipoRegistro.startsWith("registro") ? "registro" : strTipoRegistro);
                    for (JanderParser.IdentificadorContext ident2 : v.identificador()) {
                        String nomeRegVar = ident2.getText();
                        if (tabelaRegistro.existe(nomeRegVar)) {
                            JanderSemanticoUtils.adicionarErroSemantico(ident2.start,
                                    "identificador " + nomeRegVar + " ja declarado anteriormente");
                        } else {
                            System.out.println("VisitVariavelRegistro: " + nomeRegVar + " " + tipoRegistro);
                            tabelaRegistro.adicionar(nomeRegVar, tipoRegistro);
                            if (tipoRegistro == TipoJander.INVALIDO) {
                                JanderSemanticoUtils.adicionarErroSemantico(ident2.start,
                                        "tipo " + strTipoRegistro + " nao declarado");
                            }
                        }
                    }
                }
                tabela.adicionarRegistro(nomeVar, tabelaRegistro);
            } else {
                TipoJander tipoVar = JanderSemanticoUtils.getTipo(strTipoVar);
                if (tabela.existe(nomeVar)) {
                    JanderSemanticoUtils.adicionarErroSemantico(ctx.IDENT().getSymbol(),
                            "Variável " + nomeVar + " já existe");
                } else {
                    tabela.adicionar(nomeVar, tipoVar);
                }
            }
        }
        return super.visitDeclaracao_local(ctx);
    }

    @Override
    public Void visitDeclaracao_global(JanderParser.Declaracao_globalContext ctx) {
        if(escoposAninhados.percorrerEscoposAninhados().size() > 1) { // Se tiver mais de um escopo, abandonar o escopo atual que seria referente a ultima funcao/procedimento
            escoposAninhados.abandonarEscopo();
        }
        TabelaDeSimbolos tabela = escoposAninhados.obterEscopoAtual();
        if (ctx.FUNCAO() != null) {
            ehFuncao = true;
            String nomeFuncao = ctx.IDENT().getText();
            // TipoJander tipoFuncao = JanderSemanticoUtils.verificarTipo(tabela,
            // ctx.tipo_estendido());
            TipoJander tipoFuncao = JanderSemanticoUtils.getTipo(ctx.tipo_estendido().getText());
            tabela.adicionar(nomeFuncao, tipoFuncao);
            System.out.println(nomeFuncao + " " + tipoFuncao);
        } else if (ctx.PROCEDIMENTO() != null) {
            String nomeProcedimento = ctx.IDENT().getText();
            tabela.adicionar(nomeProcedimento, TipoJander.VOID);
        }
        escoposAninhados.criarNovoEscopo();
        TabelaDeSimbolos tabelaFuncao = escoposAninhados.obterEscopoAtual();
        for (JanderParser.ParametroContext param : ctx.parametros().parametro()) {
            for (JanderParser.IdentificadorContext ident : param.identificador()) {
                String nomeParam = ident.getText();
                String strTipoParam = param.tipo_estendido().getText();
                TipoJander tipoParam = JanderSemanticoUtils.getTipo(strTipoParam);
                Boolean tipoVarRegistro = false;
                if (tipoParam == TipoJander.INVALIDO) {
                    if (tabela.existe(strTipoParam) && tabela.verificar(strTipoParam) == TipoJander.REGISTRO) {
                        tipoParam = TipoJander.REGISTRO;
                        tipoVarRegistro = true;
                    }
                }
                System.out.println("Tipo e nome: " + strTipoParam + " " + nomeParam + " " + tipoParam);
                if (tabelaFuncao.existe(nomeParam)) {
                    JanderSemanticoUtils.adicionarErroSemantico(ident.start, "Parâmetro " + nomeParam + " já existe");
                } else {
                    if (tipoVarRegistro) {
                        TabelaDeSimbolos tabelaRegistro = tabela.verificarRegistro(strTipoParam);
                        tabelaFuncao.adicionar(nomeParam, tipoParam);
                        tabelaFuncao.adicionarRegistro(nomeParam, tabelaRegistro);
                    }else {
                        tabelaFuncao.adicionar(nomeParam, tipoParam);
                    }
                    tabela.adicionarParametro(ctx.IDENT().getText(), nomeParam);
                }
            }
        }
        tabela.adicionarRegistro(ctx.IDENT().getText(), tabelaFuncao);
        //Quando tirar essa tabela de "cima"?
        var retorne = super.visitDeclaracao_global(ctx);
        ehFuncao = false;
        return retorne;
    }

    @Override
    public Void visitCorpo(JanderParser.CorpoContext ctx) {
        if(escoposAninhados.percorrerEscoposAninhados().size() > 1) { // Se tiver mais de um escopo, abandonar o escopo atual que seria referente a ultima funcao/procedimento
            escoposAninhados.abandonarEscopo();
        }
        return super.visitCorpo(ctx);
    }

    @Override
    public Void visitVariavel(JanderParser.VariavelContext ctx) {
        TabelaDeSimbolos tabela = escoposAninhados.obterEscopoAtual();
        System.out.println("TABELA ATUALriavel:" + tabela.toString());

        String strTipoVar = ctx.tipo().getText();
        TipoJander tipoVar = JanderSemanticoUtils.getTipo(strTipoVar.startsWith("registro") ? "registro" : strTipoVar);
        Boolean tipoVarRegistro = false;

        if (tipoVar == TipoJander.INVALIDO) {
            if (tabela.existe(strTipoVar) && tabela.verificar(strTipoVar) == TipoJander.REGISTRO) {
                tipoVar = TipoJander.REGISTRO;
                tipoVarRegistro = true;
            }
        }

        System.out.println("tipoVar: " + strTipoVar );
        System.out.println(tabela.existe(strTipoVar) );
        for (JanderParser.IdentificadorContext ident : ctx.identificador()) {
            String nomeVar = ident.getText();
            if (tabela.existe(nomeVar)) {
                JanderSemanticoUtils.adicionarErroSemantico(ident.start,
                        "identificador " + nomeVar + " ja declarado anteriormente");
            } else {
                System.out.println("VisitVariavel: " + nomeVar + " " + tipoVar);
                if (nomeVar.contains("[")) {
                    nomeVar = nomeVar.substring(0, nomeVar.indexOf("["));
                }
                tabela.adicionar(nomeVar, tipoVar);
                if (tipoVar == TipoJander.INVALIDO) {
                    JanderSemanticoUtils.adicionarErroSemantico(ident.start, "tipo " + strTipoVar + " nao declarado");
                } else if (tipoVar == TipoJander.REGISTRO) {
                    if (tipoVarRegistro) {
                        TabelaDeSimbolos tabelaRegistro = tabela.verificarRegistro(strTipoVar);
                        System.out.println("VisitVariavelRegistro323: " + nomeVar + " " + tabelaRegistro);
                        tabela.adicionarRegistro(nomeVar, tabelaRegistro);
                    } else {
                        TabelaDeSimbolos tabelaRegistro = new TabelaDeSimbolos();
                        for (var v : ctx.tipo().registro().variavel()) {
                            String strTipoRegistro = v.tipo().getText();
                            TipoJander tipoRegistro = JanderSemanticoUtils
                                    .getTipo(strTipoRegistro.startsWith("registro") ? "registro" : strTipoRegistro);
                            for (JanderParser.IdentificadorContext ident2 : v.identificador()) {
                                String nomeRegVar = ident2.getText();
                                if (tabelaRegistro.existe(nomeRegVar)) {
                                    JanderSemanticoUtils.adicionarErroSemantico(ident2.start,
                                            "identificador " + nomeRegVar + " ja declarado anteriormente");
                                } else {
                                    System.out.println("VisitVariavelRegistro: " + nomeRegVar + " " + tipoRegistro);
                                    tabelaRegistro.adicionar(nomeRegVar, tipoRegistro);
                                    if (tipoRegistro == TipoJander.INVALIDO) {
                                        JanderSemanticoUtils.adicionarErroSemantico(ident2.start,
                                                "tipo " + strTipoRegistro + " nao declarado");
                                    }
                                }
                            }
                        }
                        tabela.adicionarRegistro(nomeVar, tabelaRegistro);
                    }
                }
            }
        }

        // na duvida tira (:
        return super.visitVariavel(ctx);
    }

    @Override
    public Void visitCmdAtribuicao(JanderParser.CmdAtribuicaoContext ctx) {
        System.out.println("QTDTABELAS: " + escoposAninhados.percorrerEscoposAninhados().size());
        TabelaDeSimbolos tabela = escoposAninhados.obterEscopoAtual();
        System.out.println("TABELA ATUAL:" + tabela.toString());

        JanderSemanticoUtils.setNomeVarAtrib(ctx.identificador().getText());
        System.out.println("CmdAtribuicao: " + ctx.identificador().getText() + " " + ctx.expressao().getText());
        TipoJander tipoExpressao = JanderSemanticoUtils.verificarTipo(tabela, ctx.expressao());

        if (tipoExpressao != TipoJander.INVALIDO) {
            String nomeVar = ctx.identificador().getText();
            String nomeVar2;
            if (nomeVar.contains(".")) {
                String[] partes = nomeVar.split("\\.");
                nomeVar = partes[0];
                nomeVar2 = partes[1];
                if (!tabela.existe(nomeVar)) {
                    JanderSemanticoUtils.adicionarErroSemantico(ctx.identificador().start,
                            "identificador " + nomeVar + " nao declarado");
                } else {
                    TabelaDeSimbolos tabelaRegistro = tabela.verificarRegistro(nomeVar);
                    if (!tabelaRegistro.existe(nomeVar2)) {
                        JanderSemanticoUtils.adicionarErroSemantico(ctx.identificador().start,
                                "identificador " + nomeVar2 + " nao declarado");
                    } else {
                        TipoJander tipoVar = tabelaRegistro.verificar(nomeVar2);
                        if (JanderSemanticoUtils.verificarTipoCompativeL(tabela, tipoVar, tipoExpressao)
                                || tabela.verificarPonteiro(nomeVar) && !ctx.expressao().getText().contains("&")) {
                            JanderSemanticoUtils.adicionarErroSemantico(ctx.identificador().start,
                                    "atribuicao nao compativel para " + (ctx.circ != null ? "^" : "")
                                            + ctx.identificador().getText());
                        }
                    }
                }
            } else if (!tabela.existe(nomeVar.substring(0, nomeVar.indexOf("[") == -1 ? nomeVar.length()
                    : nomeVar.indexOf("[")))) {
                JanderSemanticoUtils.adicionarErroSemantico(ctx.identificador().start,
                        "identificador " + ctx.identificador().getText() + " nao declarado");
            } else {
                nomeVar = nomeVar.substring(0, nomeVar.indexOf("[") == -1 ? nomeVar.length()
                        : nomeVar.indexOf("["));
                TipoJander tipoVar = tabela.verificar(nomeVar);
                if (JanderSemanticoUtils.verificarTipoCompativeL(tabela, tipoVar, tipoExpressao)
                        || tabela.verificarPonteiro(nomeVar) && !ctx.expressao().getText().contains("&")) {
                    JanderSemanticoUtils.adicionarErroSemantico(ctx.identificador().start,
                            "atribuicao nao compativel para " + (ctx.circ != null ? "^" : "")
                                    + ctx.identificador().getText());
                }
            }
        }

        return super.visitCmdAtribuicao(ctx);
    }

    @Override
    public Void visitCmdLeia(JanderParser.CmdLeiaContext ctx) {
        TabelaDeSimbolos tabela = escoposAninhados.obterEscopoAtual();
        for (JanderParser.IdentificadorContext ident : ctx.identificador()) {
            String nomeVar = ident.getText();
            String nomeVar2;
            if (nomeVar.contains(".")) {
                System.out.println(nomeVar);
                String[] partes = nomeVar.split("\\.");
                nomeVar = partes[0];
                nomeVar2 = partes[1];
                System.out.println("nomeVar: " + nomeVar + " " + nomeVar2);
                if (!tabela.existe(nomeVar)) {
                    JanderSemanticoUtils.adicionarErroSemantico(ident.start,
                            "identificador " + ident.getText() + " nao declarado");
                }
                TabelaDeSimbolos tabelaRegistro = tabela.verificarRegistro(nomeVar);
                if (!tabelaRegistro.existe(nomeVar2)) {
                    JanderSemanticoUtils.adicionarErroSemantico(ident.start,
                            "identificador " + ident.getText() + " nao declarado");
                }
            }
            if (!tabela.existe(nomeVar.substring(0, nomeVar.indexOf("[") == -1 ? nomeVar.length()
                    : nomeVar.indexOf("[")))) {
                JanderSemanticoUtils.adicionarErroSemantico(ident.start,
                        "identificador " + ident.getText() + " nao declarado");
            }
        }
        return super.visitCmdLeia(ctx);
    }

    @Override
    public Void visitParcela_nao_unario(JanderParser.Parcela_nao_unarioContext ctx) {
        TabelaDeSimbolos tabela = escoposAninhados.obterEscopoAtual();
        if (ctx.identificador() != null) {
            JanderSemanticoUtils.verificarTipo(tabela, ctx);
        } else if (ctx.CADEIA() != null) {

        }

        return super.visitParcela_nao_unario(ctx);
    }

    @Override
    public Void visitParcela_unario(JanderParser.Parcela_unarioContext ctx) {
        TabelaDeSimbolos tabela = escoposAninhados.obterEscopoAtual();
        if (ctx.identificador() != null) {
            System.out.println("Parcela_unario: " + ctx.identificador().getText());
            String nomeVar = ctx.identificador().getText();
            if (nomeVar.contains(".")) {
                String[] partes = nomeVar.split("\\.");
                nomeVar = partes[0];
                String nomeVar2 = partes[1];
                if (!tabela.existe(nomeVar)) {
                    JanderSemanticoUtils.adicionarErroSemantico(ctx.identificador().start,
                            "identificador " + ctx.identificador().getText() + " nao declarado");
                } else {
                    TabelaDeSimbolos tabelaRegistro = tabela.verificarRegistro(nomeVar);
                    if (!tabelaRegistro.existe(nomeVar2)) {
                        JanderSemanticoUtils.adicionarErroSemantico(ctx.identificador().start,
                                "identificador " + ctx.identificador().getText() + " nao declarado");
                    }
                }
            } else if (!tabela.existe(nomeVar.substring(0, nomeVar.indexOf("[") == -1 ? nomeVar.length()
                    : nomeVar.indexOf("[")))) {
                JanderSemanticoUtils.adicionarErroSemantico(ctx.identificador().start,
                        "identificador " + ctx.identificador().getText() + " nao declarado");
            }
        } else if (ctx.IDENT() != null) {
            String nomeIDENT = ctx.IDENT().getText();
            System.out.println("Parcela_unario: " + nomeIDENT);
            System.out.println("Tabela: " + tabela.toString());
            if (!tabela.existe(nomeIDENT)) {
                JanderSemanticoUtils.adicionarErroSemantico(ctx.IDENT().getSymbol(),
                        "identificador " + nomeIDENT + " nao declarado");
            }
            int count = 0;
            for(JanderParser.ExpressaoContext exp : ctx.expressao()) {
                TipoJander tipoExp = JanderSemanticoUtils.verificarTipo(tabela, exp);
                if(tipoExp == TipoJander.INVALIDO) {
                    JanderSemanticoUtils.adicionarErroSemantico(exp.start, "incompatibilidadeINVALIDO de parametros na chamada de " + nomeIDENT);
                }
                else {
                    String nomePar = tabela.pegarParametro(nomeIDENT, count);
                    System.out.println("Puna: " + nomeIDENT + " " + nomePar);
                    TipoJander tipoPar = tabela.verificarRegistro(nomeIDENT).verificar(nomePar);
                    if(tipoExp != tipoPar) {
                        JanderSemanticoUtils.adicionarErroSemantico(exp.start, "incompatibilidade de parametros na chamada de " + nomeIDENT);
                    }
                }
                count++;
            }
            if(count != tabela.pegarQuantidadeParametros(nomeIDENT)) {
                JanderSemanticoUtils.adicionarErroSemantico(ctx.IDENT().getSymbol(), "incompatibilidade de parametros na chamada de " + nomeIDENT);
            }
        } 

        return super.visitParcela_unario(ctx);
    }

    @Override
    public Void visitCmdRetorne(JanderParser.CmdRetorneContext ctx) {
        if(ehFuncao == false) {
            JanderSemanticoUtils.adicionarErroSemantico(ctx.start, "comando retorne nao permitido nesse escopo");
        }
        return super.visitCmdRetorne(ctx);
    }
}