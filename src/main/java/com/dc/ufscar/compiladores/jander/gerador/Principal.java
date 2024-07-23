package com.dc.ufscar.compiladores.jander.gerador;

import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.v4.runtime.CommonTokenStream;

import com.dc.ufscar.compiladores.jander.gerador.JanderParser.ProgramaContext;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

public class Principal {
    public static void main(String[] args) {
        try {
            CharStream cs = CharStreams.fromFileName("./Entrada/17.registro_tipo_impressao.alg");
            JanderLexer lex = new JanderLexer(cs);
            PrintWriter pw = new PrintWriter("aaa");
            CommonTokenStream tokens = new CommonTokenStream(lex);
            JanderParser parser = new JanderParser(tokens);
            MyCustomErrorListener mcel = new MyCustomErrorListener(pw);
            parser.addErrorListener(mcel);
            ProgramaContext arvore = parser.programa();
            JanderSemantico js = new JanderSemantico();
            js.visitPrograma(arvore);
            JanderSemanticoUtils.errosSemanticos.forEach(pw::println);
            pw.println("Fim da compilacao");
            pw.close();
            if(JanderSemanticoUtils.errosSemanticos.isEmpty()) {
                JanderGeradorC jgc = new JanderGeradorC();
                jgc.visitPrograma(arvore);
                PrintWriter pw2 = new PrintWriter("17.out");
                pw2.println(jgc.saida.toString());
                pw2.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}