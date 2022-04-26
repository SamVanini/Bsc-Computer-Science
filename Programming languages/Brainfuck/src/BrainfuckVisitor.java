// Generated from /home/samantha/IdeaProjects/Brainfuck/src/Brainfuck.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BrainfuckParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BrainfuckVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BrainfuckParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(BrainfuckParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lt}
	 * labeled alternative in {@link BrainfuckParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLt(BrainfuckParser.LtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gt}
	 * labeled alternative in {@link BrainfuckParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGt(BrainfuckParser.GtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plus}
	 * labeled alternative in {@link BrainfuckParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlus(BrainfuckParser.PlusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code minus}
	 * labeled alternative in {@link BrainfuckParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinus(BrainfuckParser.MinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dot}
	 * labeled alternative in {@link BrainfuckParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDot(BrainfuckParser.DotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comma}
	 * labeled alternative in {@link BrainfuckParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComma(BrainfuckParser.CommaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loop}
	 * labeled alternative in {@link BrainfuckParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(BrainfuckParser.LoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nil}
	 * labeled alternative in {@link BrainfuckParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNil(BrainfuckParser.NilContext ctx);
}