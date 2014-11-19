//(c) m@nso 2008
//este programa testa a chamada de procedimentos


//procecimento
vazio escreveOla ( )
    escrever " OLAAAAAAA "
fim escreveOla 

//procecimento que chama outro procedimento                
vazio escreveOle ( )
    escrever " OLEEEEEE « "
    //chamada de procedimento
    escreveOla ( )
    escrever "» OLEEEEEE"
fim escreveOle"
                
//teste de procedimentos                
inicio programa
    escreveOla ( )
    escreveOle ( )
    escreveOla ( )
fim programa

