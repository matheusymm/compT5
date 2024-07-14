package com.dc.ufscar.compiladores.jander.gerador;

import java.util.ArrayList;
import java.util.List;

import com.dc.ufscar.compiladores.jander.gerador.TabelaDeSimbolos.TipoJander;

public class JanderGeradorC extends JanderBaseVisitor<Void> {
    Escopos escopos = new Escopos();
    StringBuilder saida = new StringBuilder();
    private String LITERALSIZE = "50";
    boolean cmdEscreva = false;

    @Override
    public Void visitPrograma(JanderParser.ProgramaContext ctx) {
        saida.append("#include <stdio.h>\n");
        saida.append("#include <stdlib.h>\n");
        saida.append("#include <string.h>\n");
        saida.append("#include <stdbool.h>\n");
        saida.append("\n");

        var retorno = super.visitPrograma(ctx);
        saida.append("}\n");
        return retorno;
    }

    @Override
    public Void visitCorpo(JanderParser.CorpoContext ctx) {
        saida.append("int main() {\n");

        return super.visitCorpo(ctx);
    }

    @Override
    public Void visitVariavel(JanderParser.VariavelContext ctx) {
        TabelaDeSimbolos tabela = escopos.obterEscopoAtual();
        String tipo = ctx.tipo().getText();
        TipoJander tipoJ = JanderSemanticoUtils.getTipo(tipo);
        System.out.println("TipoJ: " + tipoJ);
        String tipoC = tipoJ != null ? JanderSemanticoUtils.getTipoC(tipoJ)[0] : null;

        saida.append(tipoC);
        saida.append(" ");
        for (JanderParser.IdentificadorContext ident : ctx.identificador()) {
            String nome = ident.getText();
            saida.append(nome);
            if (tipoJ == TipoJander.LITERAL) {
                saida.append("[" + LITERALSIZE + "]");
            }
            saida.append(", ");
            tabela.adicionar(nome, tipoJ);
        }
        saida.delete(saida.length() - 2, saida.length());
        saida.append(";\n");

        return super.visitVariavel(ctx);
    }

    @Override
    public Void visitCmdLeia(JanderParser.CmdLeiaContext ctx) {
        TabelaDeSimbolos tabela = escopos.obterEscopoAtual();

        for (JanderParser.IdentificadorContext ident : ctx.identificador()) {
            String nome = ident.getText();
            TipoJander tipo = tabela.verificar(nome);
            String mask = JanderSemanticoUtils.getTipoC(tipo)[1];
            if (tipo == TipoJander.LITERAL) {
                saida.append("gets(" + nome + ");\n");
            } else {
                saida.append("scanf(\"" + mask + "\", &" + nome + ");\n");
            }
        }

        return super.visitCmdLeia(ctx);
    }

    // Escreva e leia vao precisar ser refatorados, precisamos apenas printar o
    // começo como "printf"
    // E chamar o super.visitCmdEscreva(ctx) para printar o resto
    // Quando "voltar" printamos o final do printf
    @Override
    public Void visitCmdEscreva(JanderParser.CmdEscrevaContext ctx) {
        TabelaDeSimbolos tabela = escopos.obterEscopoAtual();
        saida.append("printf(\"");
        List<String> vars = new ArrayList<String>();
        for (JanderParser.ExpressaoContext exp : ctx.expressao()) {
            TipoJander tipoExp = JanderSemanticoUtils.verificarTipo(tabela, exp);
            if (tipoExp != TipoJander.LITERAL) {
                String mask = JanderSemanticoUtils.getTipoC(tipoExp)[1];
                vars.add(exp.getText());
                saida.append(mask);
            } else {
                if (tabela.existe(exp.getText())) {
                    tipoExp = tabela.verificar(exp.getText());
                    String mask = JanderSemanticoUtils.getTipoC(tipoExp)[1];
                    vars.add(exp.getText());
                    saida.append(mask);
                } else
                    saida.append(exp.getText().replaceAll("\"", ""));
            }

        }
        saida.append("\",");
        for (String var : vars) {
            saida.append(var + ",");
        }

        saida.delete(saida.length() - 1, saida.length());

        saida.append(");\n");
        return super.visitCmdEscreva(ctx);
    }

    @Override
    public Void visitCmdAtribuicao(JanderParser.CmdAtribuicaoContext ctx) {

        if (ctx.circ != null) {
            saida.append("*");
        }
        saida.append(ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");

        return super.visitCmdAtribuicao(ctx);
    }

    @Override
    public Void visitCmdSe(JanderParser.CmdSeContext ctx) {
        String expFormatted = ctx.expressao().getText().replaceAll("=", "==");
        expFormatted = expFormatted.replaceAll("<>", "!=");
        expFormatted = expFormatted.replaceAll("e", "&&");
        expFormatted = expFormatted.replaceAll("ou", "||");
        ctx.cmd().forEach(cmd -> {
            System.out.println("Cmds " + cmd.getText());
        });

        saida.append("if(" + expFormatted + ") {\n");
        ctx.cmd().forEach(cmd -> {
            super.visitCmd(cmd);
        });
        saida.append("}\n");
        if (ctx.comandosElse() != null) {
            saida.append("else {\n");
            ctx.comandosElse().forEach(cmd -> {
                super.visitComandosElse(cmd);
            });
            saida.append("}\n");
        }
        return null;
    }

    @Override
    public Void visitCmdCaso(JanderParser.CmdCasoContext ctx) {

        saida.append("switch(" + ctx.exp_aritmetica().getText() + ") {\n");
        for (var sel : ctx.selecao().item_selecao()) {
            for (var num : sel.constantes().numero_intervalo()) {
                saida.append("case " + num.NUM_INT(0).getText() + ":\n");
                if (num.num2 != null) {
                    int init = Integer.parseInt(num.NUM_INT(0).getText());
                    int end = Integer.parseInt(num.num2.getText());
                    for (int i = init; i < end; i++) {
                        saida.append("case " + (i + 1) + ":\n");
                    }
                }
                for (var cmd : sel.cmd()) {
                    super.visitCmd(cmd);
                }
                saida.append("break;\n");
            }
        }
        if (ctx.SENAO() != null) {
            saida.append("default:\n");
            for (var cmd : ctx.comandosElse()) {
                super.visitComandosElse(cmd);
            }
        }

        saida.append("}\n");
        return null;
    }

    // @Override
    // public Void visitCmdEscreva(JanderParser.CmdEscrevaContext ctx) {
    // TabelaDeSimbolos tabela = escopos.obterEscopoAtual();
    // saida.append("printf(\"");
    // List<String> vars = new ArrayList<String>();
    // cmdEscreva = true;
    // for(JanderParser.ExpressaoContext exp : ctx.expressao()) {
    // TipoJander tipoExp = JanderSemanticoUtils.verificarTipo(tabela, exp);
    // if(tabela.existe(exp.getText())) {
    // tipoExp = tabela.verificar(exp.getText());
    // String mask = JanderSemanticoUtils.getTipoC(tipoExp)[1];
    // vars.add(exp.getText());

    // }
    // }
    // var ret = super.visitCmdEscreva(ctx);
    // saida.append("\",");
    // for(String var : vars) {
    // saida.append(var + ",");
    // }
    // if(!vars.isEmpty()){
    // saida.delete(saida.length() - 1, saida.length());
    // }
    // saida.append(");\n");
    // cmdEscreva = false;
    // return ret;
    // }

    // @Override
    // public Void visitParcela_nao_unario(JanderParser.Parcela_nao_unarioContext
    // ctx) {
    // TabelaDeSimbolos tabela = escopos.obterEscopoAtual();
    // if(ctx.identificador() != null) {
    // String nome = ctx.identificador().getText();
    // TipoJander tipo = tabela.verificar(nome);
    // System.out.println("nome?NUnario: "+ nome);

    // saida.append("&" + nome);

    // }
    // else{
    // saida.append(ctx.CADEIA().getText().replaceAll("\"", ""));
    // }

    // return super.visitParcela_nao_unario(ctx);
    // }

    // @Override
    // public Void visitParcela_unario(JanderParser.Parcela_unarioContext ctx) {
    // TabelaDeSimbolos tabela = escopos.obterEscopoAtual();
    // if(ctx.identificador() != null) {
    // String nome = ctx.identificador().getText();
    // TipoJander tipo = tabela.verificar(nome);
    // String mask = JanderSemanticoUtils.getTipoC(tipo)[1];
    // if(cmdEscreva){
    // saida.append(mask);
    // }else{
    // saida.append(nome);
    // }
    // System.out.println("nome?Unario: "+ nome);
    // }

    // return super.visitParcela_unario(ctx);
    // }

}
