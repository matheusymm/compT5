// Generated from java-escape by ANTLR 4.11.1
package com.dc.ufscar.compiladores.jander.gerador;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JanderParser}.
 */
public interface JanderListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JanderParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(JanderParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(JanderParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#declaracoes}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracoes(JanderParser.DeclaracoesContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#declaracoes}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracoes(JanderParser.DeclaracoesContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#decl_local_global}.
	 * @param ctx the parse tree
	 */
	void enterDecl_local_global(JanderParser.Decl_local_globalContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#decl_local_global}.
	 * @param ctx the parse tree
	 */
	void exitDecl_local_global(JanderParser.Decl_local_globalContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#declaracao_local}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracao_local(JanderParser.Declaracao_localContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#declaracao_local}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracao_local(JanderParser.Declaracao_localContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#variavel}.
	 * @param ctx the parse tree
	 */
	void enterVariavel(JanderParser.VariavelContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#variavel}.
	 * @param ctx the parse tree
	 */
	void exitVariavel(JanderParser.VariavelContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#identificador}.
	 * @param ctx the parse tree
	 */
	void enterIdentificador(JanderParser.IdentificadorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#identificador}.
	 * @param ctx the parse tree
	 */
	void exitIdentificador(JanderParser.IdentificadorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#dimensao}.
	 * @param ctx the parse tree
	 */
	void enterDimensao(JanderParser.DimensaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#dimensao}.
	 * @param ctx the parse tree
	 */
	void exitDimensao(JanderParser.DimensaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(JanderParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(JanderParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#tipo_basico}.
	 * @param ctx the parse tree
	 */
	void enterTipo_basico(JanderParser.Tipo_basicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#tipo_basico}.
	 * @param ctx the parse tree
	 */
	void exitTipo_basico(JanderParser.Tipo_basicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#tipo_basico_ident}.
	 * @param ctx the parse tree
	 */
	void enterTipo_basico_ident(JanderParser.Tipo_basico_identContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#tipo_basico_ident}.
	 * @param ctx the parse tree
	 */
	void exitTipo_basico_ident(JanderParser.Tipo_basico_identContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#tipo_estendido}.
	 * @param ctx the parse tree
	 */
	void enterTipo_estendido(JanderParser.Tipo_estendidoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#tipo_estendido}.
	 * @param ctx the parse tree
	 */
	void exitTipo_estendido(JanderParser.Tipo_estendidoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#valor_constante}.
	 * @param ctx the parse tree
	 */
	void enterValor_constante(JanderParser.Valor_constanteContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#valor_constante}.
	 * @param ctx the parse tree
	 */
	void exitValor_constante(JanderParser.Valor_constanteContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#registro}.
	 * @param ctx the parse tree
	 */
	void enterRegistro(JanderParser.RegistroContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#registro}.
	 * @param ctx the parse tree
	 */
	void exitRegistro(JanderParser.RegistroContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#declaracao_global}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracao_global(JanderParser.Declaracao_globalContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#declaracao_global}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracao_global(JanderParser.Declaracao_globalContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(JanderParser.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(JanderParser.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(JanderParser.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(JanderParser.ParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#corpo}.
	 * @param ctx the parse tree
	 */
	void enterCorpo(JanderParser.CorpoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#corpo}.
	 * @param ctx the parse tree
	 */
	void exitCorpo(JanderParser.CorpoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterCmd(JanderParser.CmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitCmd(JanderParser.CmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdLeia}.
	 * @param ctx the parse tree
	 */
	void enterCmdLeia(JanderParser.CmdLeiaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdLeia}.
	 * @param ctx the parse tree
	 */
	void exitCmdLeia(JanderParser.CmdLeiaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdEscreva}.
	 * @param ctx the parse tree
	 */
	void enterCmdEscreva(JanderParser.CmdEscrevaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdEscreva}.
	 * @param ctx the parse tree
	 */
	void exitCmdEscreva(JanderParser.CmdEscrevaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdSe}.
	 * @param ctx the parse tree
	 */
	void enterCmdSe(JanderParser.CmdSeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdSe}.
	 * @param ctx the parse tree
	 */
	void exitCmdSe(JanderParser.CmdSeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdCaso}.
	 * @param ctx the parse tree
	 */
	void enterCmdCaso(JanderParser.CmdCasoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdCaso}.
	 * @param ctx the parse tree
	 */
	void exitCmdCaso(JanderParser.CmdCasoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdPara}.
	 * @param ctx the parse tree
	 */
	void enterCmdPara(JanderParser.CmdParaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdPara}.
	 * @param ctx the parse tree
	 */
	void exitCmdPara(JanderParser.CmdParaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdEnquanto}.
	 * @param ctx the parse tree
	 */
	void enterCmdEnquanto(JanderParser.CmdEnquantoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdEnquanto}.
	 * @param ctx the parse tree
	 */
	void exitCmdEnquanto(JanderParser.CmdEnquantoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdFaca}.
	 * @param ctx the parse tree
	 */
	void enterCmdFaca(JanderParser.CmdFacaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdFaca}.
	 * @param ctx the parse tree
	 */
	void exitCmdFaca(JanderParser.CmdFacaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdAtribuicao}.
	 * @param ctx the parse tree
	 */
	void enterCmdAtribuicao(JanderParser.CmdAtribuicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdAtribuicao}.
	 * @param ctx the parse tree
	 */
	void exitCmdAtribuicao(JanderParser.CmdAtribuicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdChamada}.
	 * @param ctx the parse tree
	 */
	void enterCmdChamada(JanderParser.CmdChamadaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdChamada}.
	 * @param ctx the parse tree
	 */
	void exitCmdChamada(JanderParser.CmdChamadaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#cmdRetorne}.
	 * @param ctx the parse tree
	 */
	void enterCmdRetorne(JanderParser.CmdRetorneContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#cmdRetorne}.
	 * @param ctx the parse tree
	 */
	void exitCmdRetorne(JanderParser.CmdRetorneContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#selecao}.
	 * @param ctx the parse tree
	 */
	void enterSelecao(JanderParser.SelecaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#selecao}.
	 * @param ctx the parse tree
	 */
	void exitSelecao(JanderParser.SelecaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#item_selecao}.
	 * @param ctx the parse tree
	 */
	void enterItem_selecao(JanderParser.Item_selecaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#item_selecao}.
	 * @param ctx the parse tree
	 */
	void exitItem_selecao(JanderParser.Item_selecaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#constantes}.
	 * @param ctx the parse tree
	 */
	void enterConstantes(JanderParser.ConstantesContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#constantes}.
	 * @param ctx the parse tree
	 */
	void exitConstantes(JanderParser.ConstantesContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#numero_intervalo}.
	 * @param ctx the parse tree
	 */
	void enterNumero_intervalo(JanderParser.Numero_intervaloContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#numero_intervalo}.
	 * @param ctx the parse tree
	 */
	void exitNumero_intervalo(JanderParser.Numero_intervaloContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#op_unario}.
	 * @param ctx the parse tree
	 */
	void enterOp_unario(JanderParser.Op_unarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#op_unario}.
	 * @param ctx the parse tree
	 */
	void exitOp_unario(JanderParser.Op_unarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#exp_aritmetica}.
	 * @param ctx the parse tree
	 */
	void enterExp_aritmetica(JanderParser.Exp_aritmeticaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#exp_aritmetica}.
	 * @param ctx the parse tree
	 */
	void exitExp_aritmetica(JanderParser.Exp_aritmeticaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#termo}.
	 * @param ctx the parse tree
	 */
	void enterTermo(JanderParser.TermoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#termo}.
	 * @param ctx the parse tree
	 */
	void exitTermo(JanderParser.TermoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#fator}.
	 * @param ctx the parse tree
	 */
	void enterFator(JanderParser.FatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#fator}.
	 * @param ctx the parse tree
	 */
	void exitFator(JanderParser.FatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#op1}.
	 * @param ctx the parse tree
	 */
	void enterOp1(JanderParser.Op1Context ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#op1}.
	 * @param ctx the parse tree
	 */
	void exitOp1(JanderParser.Op1Context ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#op2}.
	 * @param ctx the parse tree
	 */
	void enterOp2(JanderParser.Op2Context ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#op2}.
	 * @param ctx the parse tree
	 */
	void exitOp2(JanderParser.Op2Context ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#op3}.
	 * @param ctx the parse tree
	 */
	void enterOp3(JanderParser.Op3Context ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#op3}.
	 * @param ctx the parse tree
	 */
	void exitOp3(JanderParser.Op3Context ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#parcela}.
	 * @param ctx the parse tree
	 */
	void enterParcela(JanderParser.ParcelaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#parcela}.
	 * @param ctx the parse tree
	 */
	void exitParcela(JanderParser.ParcelaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#parcela_unario}.
	 * @param ctx the parse tree
	 */
	void enterParcela_unario(JanderParser.Parcela_unarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#parcela_unario}.
	 * @param ctx the parse tree
	 */
	void exitParcela_unario(JanderParser.Parcela_unarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#parcela_nao_unario}.
	 * @param ctx the parse tree
	 */
	void enterParcela_nao_unario(JanderParser.Parcela_nao_unarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#parcela_nao_unario}.
	 * @param ctx the parse tree
	 */
	void exitParcela_nao_unario(JanderParser.Parcela_nao_unarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#exp_relacional}.
	 * @param ctx the parse tree
	 */
	void enterExp_relacional(JanderParser.Exp_relacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#exp_relacional}.
	 * @param ctx the parse tree
	 */
	void exitExp_relacional(JanderParser.Exp_relacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#op_relacional}.
	 * @param ctx the parse tree
	 */
	void enterOp_relacional(JanderParser.Op_relacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#op_relacional}.
	 * @param ctx the parse tree
	 */
	void exitOp_relacional(JanderParser.Op_relacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#expressao}.
	 * @param ctx the parse tree
	 */
	void enterExpressao(JanderParser.ExpressaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#expressao}.
	 * @param ctx the parse tree
	 */
	void exitExpressao(JanderParser.ExpressaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#termo_logico}.
	 * @param ctx the parse tree
	 */
	void enterTermo_logico(JanderParser.Termo_logicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#termo_logico}.
	 * @param ctx the parse tree
	 */
	void exitTermo_logico(JanderParser.Termo_logicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#fator_logico}.
	 * @param ctx the parse tree
	 */
	void enterFator_logico(JanderParser.Fator_logicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#fator_logico}.
	 * @param ctx the parse tree
	 */
	void exitFator_logico(JanderParser.Fator_logicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#parcela_logica}.
	 * @param ctx the parse tree
	 */
	void enterParcela_logica(JanderParser.Parcela_logicaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#parcela_logica}.
	 * @param ctx the parse tree
	 */
	void exitParcela_logica(JanderParser.Parcela_logicaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#op_logico_1}.
	 * @param ctx the parse tree
	 */
	void enterOp_logico_1(JanderParser.Op_logico_1Context ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#op_logico_1}.
	 * @param ctx the parse tree
	 */
	void exitOp_logico_1(JanderParser.Op_logico_1Context ctx);
	/**
	 * Enter a parse tree produced by {@link JanderParser#op_logico_2}.
	 * @param ctx the parse tree
	 */
	void enterOp_logico_2(JanderParser.Op_logico_2Context ctx);
	/**
	 * Exit a parse tree produced by {@link JanderParser#op_logico_2}.
	 * @param ctx the parse tree
	 */
	void exitOp_logico_2(JanderParser.Op_logico_2Context ctx);
}