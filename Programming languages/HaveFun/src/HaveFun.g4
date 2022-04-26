grammar HaveFun;

prog : (fun)* com EOF ;

fun : FUN ID LPAR (ID(COLON ID)*)? RPAR LBRACE (com SEMICOLON)? RET exp RBRACE;

com : IF LPAR exp RPAR THEN LBRACE com RBRACE ELSE LBRACE com RBRACE    # if
    | GLOBAL ID ASSIGN exp                                              # globalDecl
    | ID DOTG ASSIGN exp                                                # globalAssign
    | ID ASSIGN exp                                                     # assign
    | SKIPP                                                             # skip
    | com SEMICOLON com                                                 # seq
    | WHILE LPAR exp RPAR LBRACE com RBRACE                             # while
    | OUT LPAR exp RPAR                                                 # out
    | LBRACE com RBRACE ND LBRACE com RBRACE                            # nd
    ;

exp : NAT                                 # nat
    | BOOL                                # bool
    | LPAR exp RPAR                       # parExp
    | <assoc=right> exp POW exp           # pow
    | NOT exp                             # not
    | exp op=(DIV | MUL | MOD) exp        # divMulMod
    | exp op=(PLUS | MINUS) exp           # plusMinus
    | exp op=(LT | LEQ | GEQ | GT) exp    # cmpExp
    | exp op=(EQQ | NEQ) exp              # eqExp
    | exp op=(AND | OR) exp               # logicExp
    | ID LPAR (exp (COLON exp )*)? RPAR   # funcall
    | ID                                  # id
    | ID DOTG                             # globalID
    ;

FUN : 'fun';
RET: 'return';

DOTG: '.g';
GLOBAL : 'global';

NAT : '0' | [1-9][0-9]* ;
BOOL : 'true' | 'false' ;

PLUS  : '+' ;
MINUS : '-';
MUL   : '*' ;
DIV   : '/' ;
MOD   : 'mod' ;
POW   : '^' ;

AND : '&' ;
OR  : '|' ;

EQQ : '==' ;
NEQ : '!=' ;
LEQ : '<=' ;
GEQ : '>=' ;
LT  : '<' ;
GT  : '>' ;
NOT : '!' ;

IF     : 'if' ;
THEN   : 'then' ;
ELSE   : 'else' ;
WHILE  : 'while' ;
SKIPP  : 'skip' ;
ASSIGN : '=' ;
OUT    : 'out' ;
ND : 'nd';

LPAR      : '(' ;
RPAR      : ')';
LBRACE    : '{' ;
RBRACE    : '}' ;
SEMICOLON : ';' ;
COLON : ',';


ID : [a-z]+ ;

WS : [ \t\r\n]+ -> skip ;
