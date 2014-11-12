/*
mes_repete.alg
(c) António Manso - 2-11-2005
Descrição:
Programa que solicita a introdução do número do mes
com a validação que o mes tem de estar no intervalo [1, 12]
*/
inicio
    inteiro mes
    //inicio do ciclo
    repete
        escrever "introduza um mes :"
        ler mes
        // até que o mes entre [1..12] - NOTA: repare que as condições não têm o sinal =
    ate mes > 0 e mes < 13
    escrever "\nmes introduzido :" , mes
fim

