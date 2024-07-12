package com.dc.ufscar.compiladores.jander.gerador;

import com.dc.ufscar.compiladores.jander.gerador.TabelaDeSimbolos.TipoJander;

public class JanderGeradorC extends JanderBaseVisitor<Void> {
    Escopos escopos = new Escopos();
    StringBuilder saida = new StringBuilder();

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
        if(tipoJ == TipoJander.INTEIRO)
            saida.append("int" + " ");
        for(JanderParser.IdentificadorContext ident : ctx.identificador()) {
            String nome = ident.getText();
            saida.append(nome + ", ");
            tabela.adicionar(nome, tipoJ);
        }
        saida.delete(saida.length() - 2, saida.length());
        saida.append(";\n");

        return super.visitVariavel(ctx);
    }

    @Override
    public Void visitCmdLeia(JanderParser.CmdLeiaContext ctx) {
        TabelaDeSimbolos tabela = escopos.obterEscopoAtual();

        for(JanderParser.IdentificadorContext ident : ctx.identificador()) {
            String nome = ident.getText();
            TipoJander tipo = tabela.verificar(nome);
            if(tipo == TipoJander.INTEIRO)
                saida.append("scanf(\"%d\", &" + nome + ");\n");
        }

        return super.visitCmdLeia(ctx);
    }

    @Override
    public Void visitCmdEscreva(JanderParser.CmdEscrevaContext ctx) {
        TabelaDeSimbolos tabela = escopos.obterEscopoAtual();
        for(JanderParser.ExpressaoContext exp : ctx.expressao()) {
            TipoJander tipoExp = JanderSemanticoUtils.verificarTipo(tabela, exp);
            if(tipoExp == TipoJander.INTEIRO)
                saida.append("printf(\"%d\", " + exp.getText());
            saida.append(");\n");
        }

        return super.visitCmdEscreva(ctx);
    }
}
