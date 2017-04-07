// Generated from socs/network/util/parser/RouterCommand.g4 by ANTLR 4.6
package socs.network.util.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RouterCommandParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RouterCommandVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(RouterCommandParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdDetect}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdDetect(RouterCommandParser.CmdDetectContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdAttach}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdAttach(RouterCommandParser.CmdAttachContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdAttachFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdAttachFile(RouterCommandParser.CmdAttachFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdConnect}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdConnect(RouterCommandParser.CmdConnectContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdConnectFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdConnectFile(RouterCommandParser.CmdConnectFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdDisconnect}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdDisconnect(RouterCommandParser.CmdDisconnectContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdStart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdStart(RouterCommandParser.CmdStartContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdNeighbors}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdNeighbors(RouterCommandParser.CmdNeighborsContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdQuit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdQuit(RouterCommandParser.CmdQuitContext ctx);
	/**
	 * Visit a parse tree produced by {@link RouterCommandParser#cmdDebug}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdDebug(RouterCommandParser.CmdDebugContext ctx);
}