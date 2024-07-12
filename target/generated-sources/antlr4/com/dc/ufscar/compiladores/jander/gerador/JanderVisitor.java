// Generated from java-escape by ANTLR 4.11.1
package com.dc.ufscar.compiladores.jander.gerador;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JanderParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JanderVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JanderParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(JanderParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#declaracoes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracoes(JanderParser.DeclaracoesContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#decl_local_global}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl_local_global(JanderParser.Decl_local_globalContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#declaracao_local}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracao_local(JanderParser.Declaracao_localContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#variavel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariavel(JanderParser.VariavelContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#identificador}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentificador(JanderParser.IdentificadorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#dimensao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimensao(JanderParser.DimensaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(JanderParser.TipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#tipo_basico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo_basico(JanderParser.Tipo_basicoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#tipo_basico_ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo_basico_ident(JanderParser.Tipo_basico_identContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#tipo_estendido}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo_estendido(JanderParser.Tipo_estendidoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#valor_constante}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValor_constante(JanderParser.Valor_constanteContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#registro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegistro(JanderParser.RegistroContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#declaracao_global}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracao_global(JanderParser.Declaracao_globalContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#parametro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametro(JanderParser.ParametroContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#parametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametros(JanderParser.ParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#corpo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCorpo(JanderParser.CorpoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmd(JanderParser.CmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdLeia}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdLeia(JanderParser.CmdLeiaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdEscreva}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdEscreva(JanderParser.CmdEscrevaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdSe}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdSe(JanderParser.CmdSeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdCaso}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdCaso(JanderParser.CmdCasoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdPara}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdPara(JanderParser.CmdParaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdEnquanto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdEnquanto(JanderParser.CmdEnquantoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdFaca}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdFaca(JanderParser.CmdFacaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdAtribuicao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdAtribuicao(JanderParser.CmdAtribuicaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdChamada}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdChamada(JanderParser.CmdChamadaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#cmdRetorne}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdRetorne(JanderParser.CmdRetorneContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#selecao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelecao(JanderParser.SelecaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#item_selecao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItem_selecao(JanderParser.Item_selecaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#constantes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantes(JanderParser.ConstantesContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#numero_intervalo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumero_intervalo(JanderParser.Numero_intervaloContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#op_unario}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_unario(JanderParser.Op_unarioContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#exp_aritmetica}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_aritmetica(JanderParser.Exp_aritmeticaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#termo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermo(JanderParser.TermoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#fator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFator(JanderParser.FatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#op1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp1(JanderParser.Op1Context ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#op2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp2(JanderParser.Op2Context ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#op3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp3(JanderParser.Op3Context ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#parcela}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParcela(JanderParser.ParcelaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#parcela_unario}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParcela_unario(JanderParser.Parcela_unarioContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#parcela_nao_unario}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParcela_nao_unario(JanderParser.Parcela_nao_unarioContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#exp_relacional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_relacional(JanderParser.Exp_relacionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#op_relacional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_relacional(JanderParser.Op_relacionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressao(JanderParser.ExpressaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#termo_logico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermo_logico(JanderParser.Termo_logicoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#fator_logico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFator_logico(JanderParser.Fator_logicoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#parcela_logica}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParcela_logica(JanderParser.Parcela_logicaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#op_logico_1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_logico_1(JanderParser.Op_logico_1Context ctx);
	/**
	 * Visit a parse tree produced by {@link JanderParser#op_logico_2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_logico_2(JanderParser.Op_logico_2Context ctx);
}