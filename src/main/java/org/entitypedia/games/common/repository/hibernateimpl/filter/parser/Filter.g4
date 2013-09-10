/* java -cp antlr-4.0-complete.jar org.antlr.v4.Tool Filter.g4 -visitor -no-listener */
/* mvn antlr4:antlr4 */
grammar Filter;

@header {
package org.entitypedia.games.common.repository.hibernateimpl.filter.parser;
}


init: expr EOF;

expr:
        oper                            # op
    |   NOT expr                        # neg
    |   expr AND expr                   # con
    |   expr OR expr                    # dis
    |   LRB expr RRB                    # bkt
    ;
        
oper:    
        qualifiedName EQ literal                # eq
    |   qualifiedName EQP qualifiedName         # eqP
    |   qualifiedName GE literal                # ge
    |   qualifiedName GEP qualifiedName         # geP
    |   qualifiedName GT literal                # gt
    |   qualifiedName GTP qualifiedName         # gtP
    |   qualifiedName LIKE StringLiteral        # like
    |   qualifiedName ILIKE StringLiteral       # ilike
    |   qualifiedName LE literal                # le
    |   qualifiedName LEP qualifiedName         # leP
    |   qualifiedName LT literal                # lt
    |   qualifiedName LTP qualifiedName         # ltP
    |   qualifiedName NE literal                # ne
    |   qualifiedName NEP qualifiedName         # neP
    |   qualifiedName SEQ DecimalLiteral        # seq
    |   qualifiedName SGE DecimalLiteral        # sge
    |   qualifiedName SGT DecimalLiteral        # sgt
    |   qualifiedName SLE DecimalLiteral        # sle
    |   qualifiedName SLT DecimalLiteral        # slt
    |   qualifiedName SNE DecimalLiteral        # sne
    |   qualifiedName ISEMPTY                   # isEmpty
    |   qualifiedName ISNOTEMPTY                # isNotEmpty
    |   qualifiedName ISNOTNULL                # isNotNull
    |   qualifiedName ISNULL                    # isNull
    ;

literal:   
            DecimalLiteral          # decimal
        |   FloatingPointLiteral    # float
        |   StringLiteral           # string
        |   booleanLiteral          # boolean
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
ISNOTNULL: 'isNotNull';
ISNULL: 'isNull';


AND: 'and';
OR: 'or';
NOT: 'not';

qualifiedName
    :   Identifier ('.' Identifier)*
    ;

Identifier: [a-zA-Z] ([a-zA-Z]|[0-9])*;

DateLiteral:
    '\'' ('1'|'2')'0'..'9''0'..'9''0'..'9' ('0''1'..'9'|'1'('0'|'1'|'2')) ('0''1'..'9'|'1''0'..'9'|'2''0'..'9'|'3'('0'|'1')) '\''
    |
    '\'' ('1'|'2')'0'..'9''0'..'9''0'..'9' ('0''1'..'9'|'1'('0'|'1'|'2')) ('0''1'..'9'|'1''0'..'9'|'2''0'..'9'|'3'('0'|'1')) (('0'|'1')'0'..'9'|'2'('0'..'3')) '0'..'5''0'..'9' '0'..'5''0'..'9' '\'';
booleanLiteral: 'true' | 'false';
FloatingPointLiteral: ('0'..'9')+ '.' ('0'..'9')*;
DecimalLiteral: ('0' | '1'..'9' '0'..'9'*);
StringLiteral: '\'' ( EscapeSequence | ~('\\'|'\'') )* '\'';
fragment
EscapeSequence: '\\' ('\''|'\\');
WS: [ ]+ -> skip ; // toss out whitespace