/* java -cp antlr-4.0-complete.jar org.antlr.v4.Tool Filter.g4 -visitor -no-listener */
grammar Filter;

@header {
package org.entitypedia.games.common.service.filter.parser;
}


init: expr;

expr:
        oper                            # op
    |   NOT expr                        # neg
    |   expr AND expr                   # con
    |   expr OR expr                    # dis
    |   LRB expr RRB                    # bkt
    ;
        
oper:    
        Identifier EQ literal           # eq
    |   Identifier EQP Identifier       # eqP
    |   Identifier GE literal           # ge
    |   Identifier GEP Identifier       # geP
    |   Identifier GT literal           # gt
    |   Identifier GTP Identifier       # gtP
    |   Identifier LIKE StringLiteral   # like
    |   Identifier ILIKE StringLiteral  # ilike
    |   Identifier LE literal           # le
    |   Identifier LEP Identifier       # leP
    |   Identifier LT literal           # lt
    |   Identifier LTP Identifier       # ltP
    |   Identifier NE literal           # ne
    |   Identifier NEP Identifier       # neP
    |   Identifier SEQ DecimalLiteral   # seq
    |   Identifier SGE DecimalLiteral   # sge
    |   Identifier SGT DecimalLiteral   # sgt
    |   Identifier SLE DecimalLiteral   # sle
    |   Identifier SLT DecimalLiteral   # slt
    |   Identifier SNE DecimalLiteral   # sne
    |   Identifier ISEMPTY              # isEmpty
    |   Identifier ISNOTEMPTY           # isNotEmpty
    |   Identifier ISNOTNNULL           # isNotNull
    |   Identifier ISNULL               # isNull
    ;

literal:   
            DecimalLiteral          # decimal
        |   FloatingPointLiteral    # float
        |   StringLiteral           # string
        |   BooleanLiteral          # boolean
        |   DateLiteral             # date
        ;

LRB: '(';
RRB: ')';

EQ: 'eq';
EQP: 'eqP';
GE: 'ge';
GEP: 'geP';
GT: 'gt';
GTP: 'gtP';
LIKE: 'like';
ILIKE: 'ilike';
// in
LE: 'le';
LEP: 'leP';
LT: 'lt';
LTP: 'ltP';
NE: 'ne';
NEP: 'neP';
SEQ: 'sizeEq';
SGE: 'sizeGe';
SGT: 'sizeGt';
SLE: 'sizeLe';
SLT: 'sizeLt';
SNE: 'sizeNe';

ISEMPTY: 'isEmpty';
ISNOTEMPTY: 'isNotEmpty';
ISNOTNNULL: 'isNotNull';
ISNULL: 'isNull';


AND: 'and';
OR: 'or';
NOT: 'not';

Identifier: [a-zA-Z] ([a-zA-Z]|[0-9])*;

DateLiteral: '\'' ('1'|'2')'0'..'9''0'..'9''0'..'9' ('0''1'..'9'|'1'('0'|'1'|'2')) ('0''1'..'9'|'1''0'..'9'|'2''0'..'9'|'3'('0'|'1')) '\'';
BooleanLiteral: '\'true\'' | '\'false\'';
FloatingPointLiteral: ('0'..'9')+ '.' ('0'..'9')*;
DecimalLiteral: ('0' | '1'..'9' '0'..'9'*);
StringLiteral: '\'' ( EscapeSequence | ~('\\'|'\'') )* '\'';
fragment
EscapeSequence: '\\' ('\''|'\\');
WS: [ ]+ -> skip ; // toss out whitespace