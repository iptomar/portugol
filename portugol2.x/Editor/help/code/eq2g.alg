/*
eq2g.alg
(c) António Manso - 2-11-2005
Descrição:
Programa que calcula as raizes de uma equação de 2º grau
*/
inicio
    // coeficientes do polinómio  [ax^2 + bx + c = 0 ]
    real a , b , c
    escrever "introduza o valor de a :"
    ler a
    escrever "introduza o valor de b :"
    ler b
    escrever "introduza o valor de c :"
    ler c
    // equação do tipo [ bx + c = 0 ]
    se a = 0 entao
        escrever " não é uma equação de 2º grau"
    senao
        // calcular o delta => interior da raiz
        real delta
        delta <- b ^ 2 - 4 * a * c
        // não existem raizes  reais de números negativos
        se delta < 0 entao
            escrever " não tem raizes reais"
        senao
            // -----------  raiz dupla  ----------------
            se delta = 0 entao
                real x1
                x1 <- -b / 2 * a
                escrever " raiz dupla : " , x1
            senao
                // - ---------- duas raizes ---------------
                real x1 , x2
                x1 <- ( -b + raiz ( delta ) ) / 2 * a
                x2 <- ( -b - raiz ( delta ) ) / 2 * a
                escrever " x1 : " , x1
                escrever " x2 : " , x2
            fimse//raiz dupla
        fimse// delta >0
    fimse// a <> 0
fim

