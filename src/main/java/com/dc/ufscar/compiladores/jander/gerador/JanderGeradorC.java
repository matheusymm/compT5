package com.dc.ufscar.compiladores.jander.gerador;

import java.util.ArrayList;
import java.util.List;

import com.dc.ufscar.compiladores.jander.gerador.TabelaDeSimbolos.TipoJander;

public class JanderGeradorC extends JanderBaseVisitor<Void> {
    Escopos escopos = new Escopos();
    StringBuilder saida = new StringBuilder();
    private String LITERALSIZE = "50";
    boolean cmdEscreva = false;
    boolean ehFuncao = false;

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
    public Void visitDeclaracao_global(JanderParser.Declaracao_globalContext ctx) {
        TabelaDeSimbolos tabela = escopos.obterEscopoAtual();

        if(ctx.FUNCAO() != null) {
            ehFuncao = true;
            String nome = ctx.IDENT().getText();
            TipoJander tipoJ = JanderSemanticoUtils.getTipo(ctx.tipo_estendido().getText());
            String tipoC = JanderSemanticoUtils.getTipoC(tipoJ)[0];
            saida.append(tipoC + " " + nome + "(");
            tabela.adicionar(nome, tipoJ);
        } else if(ctx.PROCEDIMENTO() != null) {
            ehFuncao = true;
            String nome = ctx.IDENT().getText();
            saida.append("void " + nome + "(");
            tabela.adicionar(nome, TipoJander.VOID);
        }

        escopos.criarNovoEscopo();
        TabelaDeSimbolos tabelaFuncao = escopos.obterEscopoAtual();
        for(JanderParser.ParametroContext param : ctx.parametros().parametro()) {
            for(JanderParser.IdentificadorContext ident : param.identificador()) {
                String nomeParam = ident.getText();
                String tipoParam = param.tipo_estendido().getText();
                TipoJander tipoJParam = JanderSemanticoUtils.getTipo(tipoParam);
                String tipoCParam = JanderSemanticoUtils.getTipoC(tipoJParam)[0];
                if(tipoJParam == TipoJander.LITERAL) {
                    tipoCParam = tipoCParam + "*";
                }
                saida.append(tipoCParam + " " + nomeParam + ", ");
                tabelaFuncao.adicionar(nomeParam, tipoJParam);
                tabela.adicionarParametro(ctx.IDENT().getText(), nomeParam);
            }
        }
        if(ctx.parametros().parametro().size() > 0) {
            saida.delete(saida.length() - 2, saida.length());
        }
        saida.append(") {\n");

        var retorne = super.visitDeclaracao_global(ctx);
        ehFuncao = false;
        saida.append("}\n\n");
        return retorne;
    }

    @Override
    public Void visitDeclaracao_local(JanderParser.Declaracao_localContext ctx) {
        TabelaDeSimbolos tabela = escopos.obterEscopoAtual();

        if (ctx.const_ != null) {
            String tipo = ctx.tipo_basico().getText();
            String nome = ctx.IDENT().getText();
            String valor = ctx.valor_constante().getText();
            TipoJander tipoJ = JanderSemanticoUtils.getTipo(tipo);
            tabela.adicionar(nome, tipoJ);
            saida.append("#define " + nome + " " + valor + "\n");
            saida.append(";\n");
        } 

        return super.visitDeclaracao_local(ctx);
    }

    @Override
    public Void visitCorpo(JanderParser.CorpoContext ctx) {
        saida.append("int main() {\n");
        if(escopos.percorrerEscoposAninhados().size() > 1) {
            escopos.abandonarEscopo();
        }

        return super.visitCorpo(ctx);
    }

    @Override
    public Void visitVariavel(JanderParser.VariavelContext ctx) {
        TabelaDeSimbolos tabela = escopos.obterEscopoAtual();
        Boolean ehPonteiro = false;
        String tipo = ctx.tipo().getText();

        if (tipo.contains("^")) {
            tipo = tipo.substring(tipo.indexOf("^"));
            ehPonteiro = true;
        }

        TipoJander tipoJ = JanderSemanticoUtils.getTipo(tipo.startsWith("registro") ? "registro" : tipo);
        String tipoC = tipoJ != null && tipoJ != TipoJander.INVALIDO ? JanderSemanticoUtils.getTipoC(tipoJ)[0]
                : null;
        if (ehPonteiro) {
            tipoC = tipoC + "*";
        }
        saida.append("\t" + tipoC);
        saida.append(" ");

        for (JanderParser.IdentificadorContext ident : ctx.identificador()) {
            String nome = ident.getText();
            saida.append(nome);
            if (tipoJ == TipoJander.LITERAL) {
                saida.append("[" + LITERALSIZE + "]");
            }
            saida.append(", ");
            tabela.adicionar(nome.contains("[") ? nome.substring(0, nome.indexOf("[")) : nome, tipoJ);
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

            // Depois precisa?
            // if (tipo == TipoJander.LITERAL) {
            // saida.append("gets(" + nome + ");\n");
            // } else {
            // }

            saida.append("\tscanf(\"" + mask + "\", &" + nome + ");\n");
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
        saida.append("\tprintf(\"");
        List<String> vars = new ArrayList<String>();
        for (JanderParser.ExpressaoContext exp : ctx.expressao()) {
            TipoJander tipoExp = JanderSemanticoUtils.verificarTipo(tabela, exp);
            if (tipoExp != TipoJander.LITERAL && tipoExp != TipoJander.INVALIDO) {
                String mask = JanderSemanticoUtils.getTipoC(tipoExp)[1];
                vars.add(exp.getText());
                saida.append(mask);
            } else {
                if (tabela.existe(exp.getText().contains("[") ? exp.getText().substring(0, exp.getText().indexOf("[")) : exp.getText())) {
                    tipoExp = tabela.verificar(exp.getText());
                    String mask = JanderSemanticoUtils.getTipoC(tipoExp)[1];
                    vars.add(exp.getText());
                    saida.append(mask);
                } else
                    saida.append(exp.getText().replaceAll("\"", "").replaceAll("\\\\", "\\\\n"));
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

        saida.append("\tif(" + expFormatted + ") {\n");
        ctx.cmd().forEach(cmd -> {
            super.visitCmd(cmd);
        });
        saida.append("\t}\n");
        if (ctx.comandosElse() != null) {
            saida.append("\telse {\n");
            ctx.comandosElse().forEach(cmd -> {
                super.visitComandosElse(cmd);
            });
            saida.append("\t}\n");
        }
        return null;
    }

    @Override
    public Void visitCmdCaso(JanderParser.CmdCasoContext ctx) {
        saida.append("\tswitch(" + ctx.exp_aritmetica().getText() + ") {\n");
        for (var sel : ctx.selecao().item_selecao()) {
            for (var num : sel.constantes().numero_intervalo()) {
                saida.append("\t\tcase " + num.NUM_INT(0).getText() + ":\n");
                if (num.num2 != null) {
                    int init = Integer.parseInt(num.NUM_INT(0).getText());
                    int end = Integer.parseInt(num.num2.getText());
                    for (int i = init; i < end; i++) {
                        saida.append("\t\tcase " + (i + 1) + ":\n");
                    }
                }
                for (var cmd : sel.cmd()) {
                    super.visitCmd(cmd);
                }
                saida.append("\t\t\tbreak;\n");
            }
        }
        if (ctx.SENAO() != null) {
            saida.append("\t\tdefault:\n");
            for (var cmd : ctx.comandosElse()) {
                super.visitComandosElse(cmd);
            }
        }

        saida.append("\t}\n");
        return null;
    }

    @Override
    public Void visitCmdPara(JanderParser.CmdParaContext ctx) {
        String nome = ctx.IDENT().getText();
        System.out.println(ctx.exp_aritmetica(0).getText());
        saida.append("\tfor(" + nome + " = " + ctx.exp_aritmetica(0).getText() + "; " + nome + "<="
                + ctx.exp_aritmetica(1).getText()
                + "; " + ctx.IDENT().getText() + "++) {\n");
        for (var cmd : ctx.cmd()) {
            super.visitCmd(cmd);
        }
        saida.append("\t}\n");
        return null;
    }

    @Override
    public Void visitCmdEnquanto(JanderParser.CmdEnquantoContext ctx) {
        saida.append("\twhile(" + ctx.expressao().getText() + ") {\n");
        for (var cmd : ctx.cmd()) {
            super.visitCmd(cmd);
        }
        saida.append("\t}\n");
        return null;
    }

    @Override
    public Void visitCmdFaca(JanderParser.CmdFacaContext ctx) {
        saida.append("\tdo {\n");
        for (var cmd : ctx.cmd()) {
            super.visitCmd(cmd);
        }
        String exp = ctx.expressao().getText().replaceAll("=", "==");
        exp = exp.replaceAll("<>", "!=");
        exp = exp.replaceAll("e", "&&");
        exp = exp.replaceAll("ou", "||");
        exp = exp.replaceAll("nao", "!");
        saida.append("\t} while(" + exp + ");\n");
        return null;
    }

    @Override
    public Void visitCmdChamada(JanderParser.CmdChamadaContext ctx) {
        TabelaDeSimbolos tabela = escopos.obterEscopoAtual();
        String nome = ctx.IDENT().getText();
        saida.append("\t" + nome + "(\"");
        List<String> vars = new ArrayList<String>();
        for (JanderParser.ExpressaoContext exp : ctx.expressao()) {
            System.out.println(exp.getText());
            TipoJander tipoExp = JanderSemanticoUtils.verificarTipo(tabela, exp);
            if (tipoExp != TipoJander.LITERAL && tipoExp != TipoJander.INVALIDO) {
                System.out.println(tipoExp.toString());
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
                    saida.append(exp.getText().replaceAll("\"", "").replaceAll("\\\\", "\\\\n"));
            }

        }
        saida.append("\",");
        for (String var : vars) {
            saida.append(var + ",");
        }

        saida.delete(saida.length() - 1, saida.length());

        saida.append(");\n");
        return null;
    }

    @Override
    public Void visitCmdRetorne(JanderParser.CmdRetorneContext ctx) {
        saida.append("\treturn " + ctx.expressao().getText() + ";\n");
        return null;
    }
}
