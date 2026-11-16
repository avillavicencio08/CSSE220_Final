# Wolfram Beta: A Derivative Calculator
by Teresa, Alex, Matthew, Nolan, & Arlo
Implemented as CSSE220 final project for section 02, Rose-Hulman Fall Quarter 2026.

Wolfram Beta is a derivative calculator which can calculate the derivative of any differentiable function written in elementary functions.
It can be used to check work in Calculus 1, or to speed up work on difficult math problems in other scenarios.
Wolfram Beta has features for differentiating, graphing, and evaluating functions.
It also contains Flappy Bird, so that students can focus, similar to Subway Surfers gameplay in a TikTok.


All differentiable expressions are represented via a generic interface, which can calculate its own derivative and simplify along the way.
In practical terms, the solver acts recursively, as a series of derivative calculations put together according to the basic derivative addition, chain, constant multiple, product, and quotient rules.


## Instructions:

Our parser is not perfectly tuned for all input syntaxes, so please use the following syntax rules:

Every operator should have all of its arguments in parenthesis if there are more operators or functions. Nested operators should have nested parenthesis, so that each layer has exactly one function, such as x+2sin(x) would be input as x+(2*(sin(x)))

Note that coefficients such as 2x are not supported – please use 2*x and likewise

Additionally, negatives are not supported – please use (0)-(f(x)) instead of -f(x)

Our inputs are NOT case nor space sensitive, feel free to use them as you like.


Our library of valid functions and their syntaxes:

Trig Functions: sin( ), cos( ), tan( ), arcsin( ), arccos( ), arctan( )

Logs: ln(), ( base )log( argument ) - base and argument are where to input the base and argument functions

Operators: ( )+( ) , ( )-( ) , ( )*( ) , ( )/( ) , ( )^( )

IMPORTANT: Operators are NOT communitive. This means that you can't chain pluses, minuses, or times.
For example, you cannot write 1*2*3. You must instead write either (1*2)*3 or 1*(2*3).

Variable of Differentiation: x

Named Constants: pi ≈ 3.14 , e ≈2.72 , g =9.81
