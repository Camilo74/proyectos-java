//configuracion de la regla
[keyword][]Prioridad=salience
[keyword][]ALTA=100
[keyword][]MEDIA=50
[keyword][]BAJA=1

[keyword][]Habilitarla para que el motor la considere en la agenda de ejecución=enabled true
[keyword][]Deshabilitado=enabled false
[keyword][]Si la regla se ejecutó, no volver a ejecutar en este contexto=no-loop true
[keyword][]Utilizar el lenguaje JAVA para el modelado de esta regla=dialect "java"

//las condiciones
[condition][]Si tomamos dos filas y tres columnas de caramelos, luego las nombramos de izquierda a derecha, de abajo hacia arriba con las letras A hasta la F, y por ultimo nos posicionamos sobre el primer caramelo, verificando que:=$A:Position($B:get("right") != null, $C:get("right").get("right") != null, $D:get("up") != null, $E:get("up").get("right") != null, $F:get("up").get("right").get("right") != null)
[condition][]Si tomamos tres filas y dos columnas de caramelos, luego las nombramos de izquierda a derecha, de abajo hacia arriba con las letras A hasta la F, y por ultimo nos posicionamos sobre el primer caramelo, verificando que:=$A:Position($B:get("right") != null, $C:get("up") != null, $D:get("up").get("right") != null, $E:get("up").get("up") != null, $F:get("up").get("up").get("right") != null)
[condition][]Si tomamos una fila y cuatro columnas de caramelos, luego las nombramos de izquierda a derecha con las letras A hasta la D, y por ultimo nos posicionamos sobre el primer caramelo, verificando que:=$A:Position($B:get("right") != null, $C:get("right").get("right") != null, $D:get("right").get("right").get("right") != null)
[condition][]Si tomamos cuatro filas y una sola columna de caramelos, luego las nombramos de abajo hacia arriba con las letras A hasta la D, y por ultimo nos posicionamos sobre el primer caramelo, verificando que:=$A:Position($B:get("up") != null, $C:get("up").get("up") != null, $D:get("up").get("up").get("up") != null)

[condition][]o- no se modificara ninguna posicion de los caramelos en el tablero en el transcurso de los ultimos {seg} segundos=not Click(this after [-{seg}s,{seg}s] $A)

[condition][]o- el caramelo {caramelo1} es igual al caramelo {caramelo2}=eval(\${caramelo1!uc}.equals(\${caramelo2!uc}))
[condition][]o- el caramelo {caramelo1} es distinto al caramelo {caramelo2}=eval(!\${caramelo1!uc}.equals(\${caramelo2!uc}))

//consecuencia
[consequence][]Hacer un click en el caramelo {caramelo1} y caramelo {caramelo2}=insert(util.click(\${caramelo1!uc},\${caramelo2!uc}));

//salida en pantalla
[*][]Imprimir el nombre de esta Regla!!!=System.out.println("************* REGLA " + drools.getRule().getName() +  " EJECUTADA !!!!***************");
