grammar Brainfuck;

main: com EOF;

com:  LT com                   #lt
    | GT com                   #gt
    | PLUS com                 #plus
    | MINUS com                #minus
    | DOT com                  #dot
    | COMMA com                #comma
    | LBRACK com RBRACK com    #loop
    |                          #nil
    ;

LT     : '<';
GT     : '>';
PLUS   : '+';
MINUS  : '-';
DOT    : '.';
COMMA  : ',';
LBRACK : '[';
RBRACK : ']';

EXTRA : . -> skip;