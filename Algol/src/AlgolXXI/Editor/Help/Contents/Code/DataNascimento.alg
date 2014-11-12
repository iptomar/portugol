/*
mes_repete.alg
(c) António Manso - 2-11-2005
Descrição:
Programa que solicita a introdução do número do ano do mes e do dia
com a validação
ano : 1900 - 2015
mes : 1 - 12
dia : 1 - ultimo dia do mes
*/
inicio
    inteiro ano <- 2001 , mes , dia , ultimodia
    caracter continua
    repete
        //ler o ano
        repete
            escrever "ano:"
            ler ano
        ate ano > 1900 e ano < 2015
        //ler o mes
        repete
            escrever "mes:"
            ler mes
        ate mes > 0 e mes < 13
        //calcular o ultimo dia do mes
        ultimodia <- 31
        se mes = 4 ou mes = 5 ou mes = 9 ou mes = 11 entao
            ultimodia <- 30
        senao
            se mes = 2 entao
                se ano % 400 = 0 ou ano % 4 = 0 e ano % 100 =/= 0 entao
                    ultimodia <- 29
                senao
                    ultimodia <- 28
                fimse
            fimse
            
        fimse
        //ler o dia
        repete
            escrever "dia:"
            ler dia
        ate dia > 0 e dia < ultimodia + 1
        escrever "\n\n Data: "
        se dia < 10 entao
            escrever "0"
        fimse
        escrever dia , "-"
        
        se mes < 10 entao
            escrever "0"
        fimse
        escrever mes , "-"
        se ano % 100 < 10 entao
            escrever "0"
        fimse
        escrever ano % 100
        
        repete
            escrever "\ndeseja continuar (s\\n) ?"
            ler continua
        ate continua = "s" ou continua = "S" ou continua = "n" ou continua = "N"
    ate continua = "n" ou continua = "N"
    
fim

