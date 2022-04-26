import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Brainfuck extends BrainfuckBaseVisitor<Integer>{

    private final Map<Integer, Integer> values = new HashMap<>();
    private int pointer = 0;

    @Override
    public Integer visitMain(BrainfuckParser.MainContext ctx) {
        return visit(ctx.com());
    }

    @Override
    public Integer visitNil(BrainfuckParser.NilContext ctx) {
        return null;
    }

    @Override
    public Integer visitMinus(BrainfuckParser.MinusContext ctx) {
        int old = values.getOrDefault(pointer, 0);
        values.put(pointer, old - 1);
        return visit(ctx.com());
    }

    @Override
    public Integer visitLt(BrainfuckParser.LtContext ctx) {
        --pointer;
        return visit(ctx.com());
    }

    @Override
    public Integer visitLoop(BrainfuckParser.LoopContext ctx) {

        while(values.get(pointer) != 0)
            visit(ctx.com(0));

        return visit(ctx.com(1));
    }

    @Override
    public Integer visitGt(BrainfuckParser.GtContext ctx) {
        ++pointer;
        return visit(ctx.com());
    }

    @Override
    public Integer visitDot(BrainfuckParser.DotContext ctx) {
        System.out.print(values.getOrDefault(pointer, 0) + " ");
        return visit(ctx.com());
    }

    @Override
    public Integer visitComma(BrainfuckParser.CommaContext ctx) {
        Scanner keyboard = new Scanner(System.in);

        int n;
        System.out.print("Inserisci un numero: ");
        n = keyboard.nextInt();

        values.put(pointer, n);

        return visit(ctx.com());
    }

    @Override
    public Integer visitPlus(BrainfuckParser.PlusContext ctx) {
        int old = values.getOrDefault(pointer, 0);
        values.put(pointer, old + 1);
        return super.visitPlus(ctx);
    }
}
