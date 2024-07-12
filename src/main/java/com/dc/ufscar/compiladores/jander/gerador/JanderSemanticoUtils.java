package com.dc.ufscar.compiladores.jander.gerador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.antlr.v4.runtime.Token;

import com.dc.ufscar.compiladores.jander.gerador.JanderParser.Exp_aritmeticaContext;
import com.dc.ufscar.compiladores.jander.gerador.JanderParser.ExpressaoContext;
import com.dc.ufscar.compiladores.jander.gerador.JanderParser.FatorContext;
import com.dc.ufscar.compiladores.jander.gerador.JanderParser.Fator_logicoContext;
import com.dc.ufscar.compiladores.jander.gerador.JanderParser.ParcelaContext;
import com.dc.ufscar.compiladores.jander.gerador.JanderParser.TermoContext;
import com.dc.ufscar.compiladores.jander.gerador.JanderParser.Termo_logicoContext;
import com.dc.ufscar.compiladores.jander.gerador.TabelaDeSimbolos.TipoJander;

public class JanderSemanticoUtils {
    public static List<String> errosSemanticos = new ArrayList<>();
    public static List<String> nomeVarAtrib = new ArrayList<>();
    public static HashMap<TipoJander, String[]> tipoMap = new HashMap<TipoJander, String[]>();


    public static String[] getTipoC(TipoJander tipo) {
        tipoMap.put(TipoJander.REAL, new String[]{"float","%f"});
        tipoMap.put(TipoJander.LITERAL,new String[]{"char", "%s"});
        tipoMap.put(TipoJander.LOGICO,new String[]{"int","%d"});
        tipoMap.put(TipoJander.INTEIRO, new String[]{"int","%d"});

        return tipoMap.get(tipo);
    }
    public static TipoJander getTipo(String tipo) {
        switch (tipo.replace("^", "").toUpperCase()) {
            case "INTEIRO":
                return TipoJander.INTEIRO;
            case "REAL":
                return TipoJander.REAL;
            case "LITERAL":
                return TipoJander.LITERAL;
            case "LOGICO":
                return TipoJander.LOGICO;
            case "REGISTRO":
                return TipoJander.REGISTRO;
            default:
                return TipoJander.INVALIDO;
        }
    }

    public static void setNomeVarAtrib(String nome) {
        nomeVarAtrib.add(String.format("%s", nome));
    }

    // Falta chamar a função pra cleanar a lista
    public static void voidRemoveVarAtrib() {
        nomeVarAtrib.clear();
    }

    public static void adicionarErroSemantico(Token t, String mensagem) {
        int linha = t.getLine();
        // int coluna = t.getCharPositionInLine();
        errosSemanticos.add(String.format("Linha %d: %s", linha, mensagem));
    }

    public static boolean verificarTipoCompativeL(TabelaDeSimbolos tabela, TabelaDeSimbolos.TipoJander tipo1,
            TabelaDeSimbolos.TipoJander tipo2) {
        TabelaDeSimbolos.TipoJander aux1 = tipo1;
        TabelaDeSimbolos.TipoJander aux2 = tipo2;
        // Talvez refatorar essa condição, vai ser dificil fazer manuntentação dela
        if (aux1 == aux2 ||
                aux1 == TabelaDeSimbolos.TipoJander.REAL && aux2 == TabelaDeSimbolos.TipoJander.INTEIRO ||
                aux1 == TabelaDeSimbolos.TipoJander.INTEIRO && aux2 == TabelaDeSimbolos.TipoJander.REAL ||
                aux1 == TabelaDeSimbolos.TipoJander.LOGICO && aux2 == TabelaDeSimbolos.TipoJander.REAL ||
                aux1 == TabelaDeSimbolos.TipoJander.REAL && aux2 == TabelaDeSimbolos.TipoJander.LOGICO) {
            return false;
        } else {
            return true;
        }
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.Exp_aritmeticaContext ctx) {
        TabelaDeSimbolos.TipoJander ret = null;

        for (TermoContext ta : ctx.termo()) {
            TabelaDeSimbolos.TipoJander aux = verificarTipo(tabela, ta);

            if (ret == null) {
                ret = aux;
            } else if (aux != TabelaDeSimbolos.TipoJander.INVALIDO && verificarTipoCompativeL(tabela, aux, ret)) {
                String nomeVar = nomeVarAtrib.isEmpty() ? "" : nomeVarAtrib.get(nomeVarAtrib.size() - 1);
                adicionarErroSemantico(ctx.getStart(),
                        "atribuicao nao compativel para " + nomeVar);
                ret = TabelaDeSimbolos.TipoJander.INVALIDO;
            }
        }
        return ret;
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela, JanderParser.TermoContext ctx) {
        TabelaDeSimbolos.TipoJander ret = null;
        for (FatorContext fa : ctx.fator()) {
            TabelaDeSimbolos.TipoJander aux = verificarTipo(tabela, fa);
            if (ret == null) {
                ret = aux;
            } else if (aux != TabelaDeSimbolos.TipoJander.INVALIDO && verificarTipoCompativeL(tabela, aux, ret)) {
                adicionarErroSemantico(ctx.getStart(), "TermoT " + ctx.getText() + "contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoJander.INVALIDO;
            }
        }
        return ret;
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela, JanderParser.FatorContext ctx) {
        TabelaDeSimbolos.TipoJander ret = null;
        for (ParcelaContext pa : ctx.parcela()) {
            TabelaDeSimbolos.TipoJander aux = verificarTipo(tabela, pa);
            if (ret == null) {
                ret = aux;
            } else if (aux != TabelaDeSimbolos.TipoJander.INVALIDO && verificarTipoCompativeL(tabela, aux, ret)) {
                adicionarErroSemantico(ctx.getStart(), "Fator " + ctx.getText() + "contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoJander.INVALIDO;
            }
        }
        return ret;
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela, JanderParser.ParcelaContext ctx) {
        if (ctx.op_unario() != null) {
            return verificarTipo(tabela, ctx.op_unario());
        } else if (ctx.parcela_unario() != null) {
            return verificarTipo(tabela, ctx.parcela_unario());
        } else if (ctx.parcela_nao_unario() != null) {
            return verificarTipo(tabela, ctx.parcela_nao_unario());
        } else {
            return TabelaDeSimbolos.TipoJander.INVALIDO;
        }
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.Op_unarioContext ctx) {
        if (ctx.getText().equals("-")) {
            return TabelaDeSimbolos.TipoJander.REAL;
        } else {
            return TabelaDeSimbolos.TipoJander.INVALIDO;
        }
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.Parcela_unarioContext ctx) {

        if (ctx.identificador() != null) {
            String nome = ctx.identificador().getText();
            nome = nome.substring(0, nome.indexOf("[") == -1 ? nome.length(): nome.indexOf("["));
            // Verifica se é um registro e pega o ultimo atributo(vai ser 1, mas se tivesse registros aninhados, funcionaria mais ou menos assim)
            // Se for um registro entao o tamanho do array vai ser mais de um campo, logo preciso pegar a tabela do registro pra verificar o tipo da variavel deste
            // O problema daqui ocorre devido ao fato de variaveis definidas no parametro da função/procedimentos nao estarem sendo adicionadas na tabela da função
            if(ctx.identificador().getText().contains(".")){
                String[] structsAttribs = nome.split(nome.contains(".") ? "\\." : "");
                nome = structsAttribs[structsAttribs.length - 1];
                TabelaDeSimbolos tabRegistro = tabela.verificarRegistro(structsAttribs[0]);
                tabela = tabRegistro;
            }
            return verificarTipo(tabela, nome);
        } else if (ctx.IDENT() != null) {
            return tabela.verificar(ctx.IDENT().getText());
        } else if (ctx.NUM_INT() != null) {
            return TabelaDeSimbolos.TipoJander.INTEIRO;
        } else if (ctx.NUM_REAL() != null) {
            return TabelaDeSimbolos.TipoJander.REAL;
        } else if (ctx.expressao() != null) {
            TabelaDeSimbolos.TipoJander ret = null;
            for (ExpressaoContext ta : ctx.expressao()) {
                TabelaDeSimbolos.TipoJander aux = verificarTipo(tabela, ta);
                if (ret == null) {
                    ret = aux;
                } else if (aux != TabelaDeSimbolos.TipoJander.INVALIDO && verificarTipoCompativeL(tabela, aux, ret)) {
                    adicionarErroSemantico(ctx.getStart(), "atribuicaoParUnit nao compativel para " + ta.getText());
                    ret = TabelaDeSimbolos.TipoJander.INVALIDO;
                }
            }
            return ret;
        } else {
            return TabelaDeSimbolos.TipoJander.INVALIDO;
        }
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.Parcela_nao_unarioContext ctx) {
        if (ctx.identificador() != null) {
            return verificarTipo(tabela, ctx.identificador().getText());
        } else if (ctx.CADEIA() != null) {

            return TabelaDeSimbolos.TipoJander.LITERAL;
        } else {
            return TabelaDeSimbolos.TipoJander.INVALIDO;
        }
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela, String nome) {
        if (!tabela.existe(nome)) {
            //adicionarErroSemantico(null, "identificador " + nome + " nao declarado");
            return TabelaDeSimbolos.TipoJander.INVALIDO;
        }
        return tabela.verificar(nome);
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.ExpressaoContext ctx) {
        TabelaDeSimbolos.TipoJander ret = null;
        for (Termo_logicoContext ta : ctx.termo_logico()) {
            TabelaDeSimbolos.TipoJander aux = verificarTipo(tabela, ta);

            if (ret == null) {
                ret = aux;
            } else if (aux != TabelaDeSimbolos.TipoJander.INVALIDO && verificarTipoCompativeL(tabela, aux, ret)) {
                adicionarErroSemantico(ctx.getStart(), "atribuicaoExp nao compativel para " + ta.getText());
                ret = TabelaDeSimbolos.TipoJander.INVALIDO;
            }
        }
        return ret;
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.Termo_logicoContext ctx) {
        TabelaDeSimbolos.TipoJander ret = null;
        for (Fator_logicoContext fa : ctx.fator_logico()) {
            TabelaDeSimbolos.TipoJander aux = verificarTipo(tabela, fa);
            if (ret == null) {
                ret = aux;
            } else if (aux != TabelaDeSimbolos.TipoJander.INVALIDO && verificarTipoCompativeL(tabela, aux, ret)) {
                adicionarErroSemantico(ctx.getStart(), "TermoLogico " + ctx.getText() + "contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoJander.INVALIDO;
            }
        }
        return ret;
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.Fator_logicoContext ctx) {
        if (ctx.parcela_logica() != null) {
            return verificarTipo(tabela, ctx.parcela_logica());
        }

        return TabelaDeSimbolos.TipoJander.INVALIDO;
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.Parcela_logicaContext ctx) {
        if (ctx.exp_relacional() != null) {
            return verificarTipo(tabela, ctx.exp_relacional());
        } else if (ctx.FALSO() != null || ctx.VERDADEIRO() != null) {
            return TabelaDeSimbolos.TipoJander.LOGICO;
        } else {
            return TabelaDeSimbolos.TipoJander.INVALIDO;
        }
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.Exp_relacionalContext ctx) {
        TabelaDeSimbolos.TipoJander ret = null;
        for (Exp_aritmeticaContext ea : ctx.exp_aritmetica()) {
            TabelaDeSimbolos.TipoJander aux = verificarTipo(tabela, ea);
            if (ret == null) {
                ret = aux;
            } else if (aux != TabelaDeSimbolos.TipoJander.INVALIDO && verificarTipoCompativeL(tabela, aux, ret)) {
                adicionarErroSemantico(ctx.getStart(), "atribuicaoRel nao compativel para " + ea.getText());
                ret = TabelaDeSimbolos.TipoJander.INVALIDO;
            }
        }
        return ret;
    }

    // public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos
    // tabela, JanderParser.Tipo_estendidoContext ctx) {
    // if(ctx.tipo_basico_ident() != null) {
    // return verificarTipo(tabela, ctx.tipo_basico_ident());
    // } else {
    // return TabelaDeSimbolos.TipoJander.INVALIDO;
    // }
    // }

    public static TabelaDeSimbolos.TipoJander verficarTipo(TabelaDeSimbolos tabela,
            JanderParser.Tipo_basico_identContext ctx) {
        if (ctx.tipo_basico() != null) {
            return verificarTipo(tabela, ctx.tipo_basico());
        } else {
            return tabela.verificar(ctx.IDENT().getText());
        }
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.Tipo_basicoContext ctx) {
        if (ctx.LITERAL() != null) {
            return TabelaDeSimbolos.TipoJander.LITERAL;
        } else if (ctx.INTEIRO() != null) {
            return TabelaDeSimbolos.TipoJander.INTEIRO;
        } else if (ctx.REAL() != null) {
            return TabelaDeSimbolos.TipoJander.REAL;
        } else {
            return TabelaDeSimbolos.TipoJander.LOGICO;
        }
    }

    public static TabelaDeSimbolos.TipoJander verificarTipo(TabelaDeSimbolos tabela,
            JanderParser.IdentificadorContext ctx) {
        String nome = ctx.getText();
        if (!tabela.existe(nome)) {
            adicionarErroSemantico(ctx.getStart(), "identificador " + nome + " nao declarado");
            return TabelaDeSimbolos.TipoJander.INVALIDO;
        }
        return tabela.verificar(nome);
    }
}
