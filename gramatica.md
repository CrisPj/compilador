programa = 'main', '(', ')', sentencia;
sentencia = bloque | if | while | read | while | print 
                   | asignacion | declaracion | expresion;

read = 'read', '(', ( var | numero ), ')', ';' ;
print = 'print', '(', ( var | cadena ), ')', ';' ;
while = 'while','(', expresion, ')', sentencia ;
if = 'if', '(', expresion, ')', sentencia, ['else',sentencia] ;
asignacion = id, '=', expresion, ';' ;
declaracion = tipo, id, '=', expresion, ';' ;
expresion = exprMat, {( '>',exprMat) | ('<',exprMat) | ('=',exprMat) | ('!',exprMat) | ('<=',exprMat) | ('>=',exprMat) | ('==',exprMat) | ('!=',exprMat) | ('&&',exprMat) | ('||',exprMat)}

exprMat = termino, {( '+',termino) | ('-',termino)} ;
termino = factor, {( '*',factor ) | ('/',factor)} ;
factor = '(', expresion, ')'
         | '-', factor
         | '!', factor
         | numero
         | cadena
         | booleano
         | id ;

id = letra, { letra | digito } ;
tipo = 'int'|'boolean'|'string' ;
booleano = 'true'|'false' ;
cadena = '"', ? all characters ? , "'" ;
letra  = "A" | "B" | "C" | "D" | "E" | "F" | "G"
       | "H" | "I" | "J" | "K" | "L" | "M" | "N"
       | "O" | "P" | "Q" | "R" | "S" | "T" | "U"
       | "V" | "W" | "X" | "Y" | "Z" | "a" | "b"
       | "c" | "d" | "e" | "f" | "g" | "h" | "i"
       | "j" | "k" | "l" | "m" | "n" | "o" | "p"
       | "q" | "r" | "s" | "t" | "u" | "v" | "w"
       | "x" | "y" | "z" ;

numero = digito, { digito } ;
digito = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" ;
