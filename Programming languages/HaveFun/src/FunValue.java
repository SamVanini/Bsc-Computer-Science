import org.antlr.v4.runtime.tree.ParseTree;
import value.Value;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


public class FunValue extends Value {

    //Data structure chosen for the order of the elements
    private final LinkedHashSet<String> parameters;
    private final HaveFunParser.ComContext body;
    private final HaveFunParser.ExpContext ret;

    public FunValue(HaveFunParser.FunContext funContext) {

        //Get the list of the parameter, excluding the function name [ctx.ID(0)]
        List<String> param = funContext.ID().subList(1, funContext.ID().size())
                .stream().map(ParseTree::getText).toList();

        this.parameters = new LinkedHashSet<>();

        for (String p:param)
            if(!this.parameters.add(p)){
                System.err.println("Parameter name " + p + " clashes with previous parameters");
                System.err.println("@" + funContext.start.getLine() + ":" + funContext.start.getCharPositionInLine());
                System.exit(1);
            }

        this.body = funContext.com();
        this.ret = funContext.exp();
    }

    public List<String> getParameters() {
        return new ArrayList<>(parameters);
    }

    public HaveFunParser.ComContext getBody() {
        return body;
    }

    public HaveFunParser.ExpContext getRet() {
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        //It isn't possible that fun1.equals(fun2) == true because
        //two different functions can't have the same name
        //(check done in IntImp.visitFuncall)
        return false;
    }

    @Override
    public int hashCode() {
        return getParameters().hashCode();
    }
}
