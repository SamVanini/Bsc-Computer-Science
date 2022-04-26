import org.antlr.v4.runtime.tree.ErrorNode;
import value.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class IntHaveFun extends HaveFunBaseVisitor<Value> {

    //used as a memory that contains only global variables
    private final Conf conf;

    //map used for functions, indexed by the name of the function
    private final HashMap<String, FunValue> functions;

    //map that represents the local memory. It contains only local vars
    private HashMap<String, ExpValue<?>> localMemory;

    public IntHaveFun(Conf conf) {
        this.conf = conf;
        this.functions = new HashMap<>();
        this.localMemory = new HashMap<>();
    }

    @Override
    public Value visitErrorNode(ErrorNode node) {

        System.err.println("Error! Your program doesn't stick to the grammar of the interpreter!");
        System.exit(1);

        return null; // unreachable statement
    }

    private ComValue visitCom(HaveFunParser.ComContext ctx) {
        return (ComValue) visit(ctx);
    }

    private ExpValue<?> visitExp(HaveFunParser.ExpContext ctx) {
        return (ExpValue<?>) visit(ctx);
    }

    private int visitNatExp(HaveFunParser.ExpContext ctx) {
        try {
            return ((NatValue) visitExp(ctx)).toJavaValue();
        } catch (ClassCastException e) {
            System.err.println("Type mismatch exception!");
            System.err.println("@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>");
            System.err.println(ctx.getText());
            System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<");
            System.err.println("> Natural expression expected.");
            System.exit(1);
        }

        return 0; // unreachable statement
    }

    private boolean visitBoolExp(HaveFunParser.ExpContext ctx) {
        try {
            return ((BoolValue) visitExp(ctx)).toJavaValue();
        } catch (ClassCastException e) {
            System.err.println("Type mismatch exception!");
            System.err.println("@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>");
            System.err.println(ctx.getText());
            System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<");
            System.err.println("> Boolean expression expected.");
            System.exit(1);
        }

        return false; // unreachable statement
    }

    @Override
    public ComValue visitProg(HaveFunParser.ProgContext ctx) {
        for (HaveFunParser.FunContext fun : ctx.fun()){
            visitFun(fun);
        }
        return visitCom(ctx.com());
    }

    @Override
    public ComValue visitFun(HaveFunParser.FunContext ctx) {

        //Obtain the name of the function
        String name = ctx.ID(0).getText();

        //Check that the function name isn't already in use
        if (functions.containsKey(name)){
            System.err.println("Fun " + name + " already defined");
            System.err.println("@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine());
            System.exit(1);
        }

        //Create a new object fun
        functions.put(name, new FunValue(ctx));

        return ComValue.INSTANCE;
    }

    @Override
    public ExpValue<?> visitFuncall(HaveFunParser.FuncallContext ctx) {

        String name = ctx.ID().toString();
        List<HaveFunParser.ExpContext> argsCtx = ctx.exp();
        HashMap<String, ExpValue<?>> functionStack = new HashMap<>();

        if(!functions.containsKey(name)){
            System.err.println("Function " + name + " used but never declared");
            System.err.println("@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine());
            System.exit(1);
        }

        //Parameters of the function
        List<String> param = functions.get(name).getParameters();

        //Parameter passed to the function
        List<ExpValue<?>> args = argsCtx.stream().map((exp) -> ((ExpValue<?> )visit(exp))).collect(Collectors.toList());

        // Check if there is the  right number of inputs
        if(param.size() != args.size()){
            System.err.println("Function " + name + " called with the wrong number of arguments");
            System.err.println("@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine());
            System.exit(1);
        }

        for (int i = 0; i < args.size(); i++) {
            functionStack.put(param.get(i), args.get(i));
        }

        //Save the state of the memory before the function execution
        HashMap<String, ExpValue<?>> initialState = localMemory;

        //set the memory of the funtion as primary memory
        localMemory = functionStack;

        //Visit the body, if present
        if(functions.get(name).getBody() != null)
            visitCom(functions.get(name).getBody());

        //Visit context of the return statement
        ExpValue<?> ret = visitExp(functions.get(name).getRet());

        //Delete the changes made by the function execution
        localMemory = initialState;

        return ret;
    }

    @Override
    public ComValue visitIf(HaveFunParser.IfContext ctx) {
        return visitBoolExp(ctx.exp())
                ? visitCom(ctx.com(0))
                : visitCom(ctx.com(1));
    }

    @Override
    public ComValue visitAssign(HaveFunParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        ExpValue<?> v = visitExp(ctx.exp());

        localMemory.put(id, v);

        return ComValue.INSTANCE;
    }

    @Override
    public Value visitGlobalDecl(HaveFunParser.GlobalDeclContext ctx) {
        String id = ctx.ID().getText();
        ExpValue<?> v = visitExp(ctx.exp());

        conf.update(id, v);

        return ComValue.INSTANCE;
    }

    @Override
    public Value visitGlobalAssign(HaveFunParser.GlobalAssignContext ctx) {
        String id = ctx.ID().getText();
        ExpValue<?> v = visitExp(ctx.exp());

        conf.update(id, v);

        return ComValue.INSTANCE;
    }

    @Override
    public ComValue visitSkip(HaveFunParser.SkipContext ctx) {
        return ComValue.INSTANCE;
    }

    @Override
    public ComValue visitSeq(HaveFunParser.SeqContext ctx) {
        visitCom(ctx.com(0));
        return visitCom(ctx.com(1));
    }

    @Override
    public ComValue visitWhile(HaveFunParser.WhileContext ctx) {
        if (!visitBoolExp(ctx.exp()))
            return ComValue.INSTANCE;

        visitCom(ctx.com());

        return visitWhile(ctx);
    }

    @Override
    public ComValue visitOut(HaveFunParser.OutContext ctx) {
        System.out.println(visitExp(ctx.exp()));
        return ComValue.INSTANCE;
    }

    @Override
    public ComValue visitNd(HaveFunParser.NdContext ctx) {
        Random rand = new Random();

        if(rand.nextBoolean())
            return visitCom(ctx.com(0));
        else
            return visitCom(ctx.com(1));

    }

    @Override
    public NatValue visitNat(HaveFunParser.NatContext ctx) {
        return new NatValue(Integer.parseInt(ctx.NAT().getText()));
    }

    @Override
    public BoolValue visitBool(HaveFunParser.BoolContext ctx) {
        return new BoolValue(Boolean.parseBoolean(ctx.BOOL().getText()));
    }

    @Override
    public ExpValue<?> visitParExp(HaveFunParser.ParExpContext ctx) {
        return visitExp(ctx.exp());
    }

    @Override
    public NatValue visitPow(HaveFunParser.PowContext ctx) {
        int base = visitNatExp(ctx.exp(0));
        int exp = visitNatExp(ctx.exp(1));

        return new NatValue((int) Math.pow(base, exp));
    }

    @Override
    public BoolValue visitNot(HaveFunParser.NotContext ctx) {
        return new BoolValue(!visitBoolExp(ctx.exp()));
    }

    @Override
    public NatValue visitDivMulMod(HaveFunParser.DivMulModContext ctx) {
        int left = visitNatExp(ctx.exp(0));
        int right = visitNatExp(ctx.exp(1));

        return switch (ctx.op.getType()) {
            case HaveFunParser.DIV -> new NatValue(left / right);
            case HaveFunParser.MUL -> new NatValue(left * right);
            case HaveFunParser.MOD -> new NatValue(left % right);
            default -> null; //unreachable statement
        };
    }

    @Override
    public NatValue visitPlusMinus(HaveFunParser.PlusMinusContext ctx) {
        int left = visitNatExp(ctx.exp(0));
        int right = visitNatExp(ctx.exp(1));

        return switch (ctx.op.getType()) {
            case HaveFunParser.PLUS -> new NatValue(left + right);
            case HaveFunParser.MINUS -> new NatValue(Math.max(left - right, 0));
            default -> null; //unreachable statement
        };
    }

    @Override
    public BoolValue visitEqExp(HaveFunParser.EqExpContext ctx) {
        ExpValue<?> left = visitExp(ctx.exp(0));
        ExpValue<?> right = visitExp(ctx.exp(1));

        return switch (ctx.op.getType()) {
            case HaveFunParser.EQQ -> new BoolValue(left.equals(right));
            case HaveFunParser.NEQ -> new BoolValue(!left.equals(right));
            default -> null; // unreachable statement
        };
    }

    @Override
    public ExpValue<?> visitId(HaveFunParser.IdContext ctx) {
        String id = ctx.ID().getText();

        if (!localMemory.containsKey(id)) {
            System.err.println("Variable " + id + " used but never instantiated");
            System.err.println("@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine());

            System.exit(1);
        }

        return localMemory.get(id);
    }

    @Override
    public ExpValue<?> visitGlobalID(HaveFunParser.GlobalIDContext ctx) {
        String id = ctx.ID().getText();

        if (!conf.contains(id)) {
            System.err.println("Variable " + id + " used but never instantiated");
            System.err.println("@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine());

            System.exit(1);
        }

        return conf.get(id);
    }

    @Override
    public BoolValue visitCmpExp(HaveFunParser.CmpExpContext ctx) {
        int left = visitNatExp(ctx.exp(0));
        int right = visitNatExp(ctx.exp(1));

        return switch (ctx.op.getType()) {
            case HaveFunParser.GEQ -> new BoolValue(left >= right);
            case HaveFunParser.LEQ -> new BoolValue(left <= right);
            case HaveFunParser.LT  -> new BoolValue(left < right);
            case HaveFunParser.GT  -> new BoolValue(left > right);
            default -> null; //unreachable statement
        };
    }

    @Override
    public BoolValue visitLogicExp(HaveFunParser.LogicExpContext ctx) {
        boolean left = visitBoolExp(ctx.exp(0));
        boolean right = visitBoolExp(ctx.exp(1));

        return switch (ctx.op.getType()) {
            case HaveFunParser.AND -> new BoolValue(left && right);
            case HaveFunParser.OR -> new BoolValue(left || right);
            default -> null; //unreachable statement
        };
    }
}
