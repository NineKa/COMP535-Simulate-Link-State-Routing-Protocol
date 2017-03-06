// Generated from socs/network/util/parser/RouterCommand.g4 by ANTLR 4.6
package socs.network.util.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RouterCommandParser}.
 */
public interface RouterCommandListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RouterCommandParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(RouterCommandParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouterCommandParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(RouterCommandParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouterCommandParser#cmdDetect}.
	 * @param ctx the parse tree
	 */
	void enterCmdDetect(RouterCommandParser.CmdDetectContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouterCommandParser#cmdDetect}.
	 * @param ctx the parse tree
	 */
	void exitCmdDetect(RouterCommandParser.CmdDetectContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouterCommandParser#cmdAttach}.
	 * @param ctx the parse tree
	 */
	void enterCmdAttach(RouterCommandParser.CmdAttachContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouterCommandParser#cmdAttach}.
	 * @param ctx the parse tree
	 */
	void exitCmdAttach(RouterCommandParser.CmdAttachContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouterCommandParser#cmdAttachFile}.
	 * @param ctx the parse tree
	 */
	void enterCmdAttachFile(RouterCommandParser.CmdAttachFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouterCommandParser#cmdAttachFile}.
	 * @param ctx the parse tree
	 */
	void exitCmdAttachFile(RouterCommandParser.CmdAttachFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouterCommandParser#cmdStart}.
	 * @param ctx the parse tree
	 */
	void enterCmdStart(RouterCommandParser.CmdStartContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouterCommandParser#cmdStart}.
	 * @param ctx the parse tree
	 */
	void exitCmdStart(RouterCommandParser.CmdStartContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouterCommandParser#cmdNeighbors}.
	 * @param ctx the parse tree
	 */
	void enterCmdNeighbors(RouterCommandParser.CmdNeighborsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouterCommandParser#cmdNeighbors}.
	 * @param ctx the parse tree
	 */
	void exitCmdNeighbors(RouterCommandParser.CmdNeighborsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouterCommandParser#cmdQuit}.
	 * @param ctx the parse tree
	 */
	void enterCmdQuit(RouterCommandParser.CmdQuitContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouterCommandParser#cmdQuit}.
	 * @param ctx the parse tree
	 */
	void exitCmdQuit(RouterCommandParser.CmdQuitContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouterCommandParser#cmdDebug}.
	 * @param ctx the parse tree
	 */
	void enterCmdDebug(RouterCommandParser.CmdDebugContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouterCommandParser#cmdDebug}.
	 * @param ctx the parse tree
	 */
	void exitCmdDebug(RouterCommandParser.CmdDebugContext ctx);
}