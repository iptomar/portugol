/*
--------------------------------------------------------
par_impar.alg
(c) António Manso - 2-11-2005
--------------------------------------------------------
Descrição:
Programa que verifica se um numero é par ou impar
*/
inicio
    inteiro numero , r
    escrever " introduza um numero"
    ler numero
    // resto da divisao inteiro do número por 2
    r := resto( numero , 2 )
    se r = 0 entao
        escrever "numero par"
    senao
        escrever "numero impar"
    fimse
fim

