# MYP-Proyecto1
Modelado y Programación, Proyecto 1

Descipción del proyecto:

Deben modelar, diseñar e implementar un programa que grafique funciones dadas por las operaciones de suma, resta, multiplicación, división y exponenciación, junto con las funciones trigonométricas seno, coseno, tangente, cotangente, secante y cosecante, además de la función raíz cuadrado.

El programa debe funcionar con una una interfaz gráfica de usuario, donde sea posible especificar las dimensiones de la vista de la gráfica, y además debe poder salvar la gráfica en un archivo SVG usando la misma vista.

Además de las reglas de todos los proyectos en el curso, su programa debe cumplir lo siguiente:

Seguir buenas prácticas de programación; además de pruebas unitarias, se debe documentar el código, y seguir patrones de diseño. En el caso particular de este proyecto, se debe seguir el patrón MVC.

El analizar una función debe tomar a lo más <i>O(n)</i>, donde n es el número de caracteres en la cadena.

El evaluar la función para un valor de x determinado debe tomar a lo más <i>O(n)</i>, donde n en el número de símbolos en la ecuación, sin considerar espacios ni paréntesis.

El programa debe notificar al usuario de manera clara y concisa cuando una función esté mal formada.

El proyecto debe poder compilarse con un único comando después de haber sido descomprimido. Este comando deberá ser estándar para el lenguaje de programación utilizado. Este comando con diferentes opciones deberá ser capaz de generar documentación (si el lenguaje permite generar documentación), y de correr las pruebas unitarias del mismo.

El proyecto es individual. Código copiado, así sea un único método o función, resultará en que la calificación del proyecto sea dividida entre el número de estudiantes que se hayan copiado.

Múltiples puntos extras estarán disponibles para quien se anime a hacerlos, y todos son acumulables:

Punto extra si dentro del mismo programa se permite generar otros formatos para las gráficas: PNG, PDF, etc. (Programáticamente; no cuenta si ejecutan un binario externo a su programa).

Punto extra si el programa utiliza notación infija en lugar de prefija.

Punto extra si el programa está escrito en el lenguaje de programación C.

Punto extra si el programa está escrito en el lenguaje de programación Vala.

Punto extra si utilizan el sistema de control de versiones Git, y su código está disponible en GitHub. Esto sólo será válido si se puede ir revisando la historia de su proyecto; si nada más avientan el código en GitHub al haberlo terminado de escribir, esto no contará.

Gramática prefija

La gramática para notación prefija, expresada en la Forma Normal de Backus es la siguiente:

< expresión > ::= < número > |
                  < variable > |
                  "(" < expresión > ")" |
                  "(" < función > < expresión > ")" |
                  "(" < operador > < expresión > < expresión > ")"

< número > ::= "-" < decimal-o-dígitos > | < decimal-o-dígitos >

< decimal-o-dígitos > ::= < decimal > | < dígitos >

< decimal > ::= < dígitos > "." < dígitos > | < dígitos > "." | "." < dígitos >

< dígitos > ::= < dígito > | < dígito > < lista-dígitos >

< lista-dígitos > ::= "" | < dígito > < lista-dígitos >

< dígito > ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"

< variable > ::= "x" | "-x"

< función > ::= "sin" | "cos" | "tan" | "cot" | "sec" | "csc" | "sqr"

< operador > ::= "+" | "-" | "*" | "/" | "^"
    
Gramática infija

La gramática para notación infija, expresada en la Forma Normal de Backus es la siguiente:

< expresión > ::= < número > |
                  < variable > |
                  "(" < expresión > ")" |
                  "-" < expresión > |
                  < función > "(" < expresión > ")" |
                 < expresión > < operador > < expresión >

< número > ::= < decimal > | < dígitos >

< decimal > ::= < dígitos > "." < dígitos > | < dígitos > "." | "." < dígitos >

< dígitos > ::= < dígito > | < dígito > < lista-dígitos >

< lista-dígitos > ::= "" | < dígito > < lista-dígitos >

< dígito > ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"

< variable > ::= "x"

< función > ::= "sin" | "cos" | "tan" | "cot" | "sec" | "csc"

< operador > ::= "+" | "-" | "*" | "/" | "^"
