Pasos para iniciar el ejemplo
=============================

1) Una ves descomprimido el proyecto, ingresamos a la carpeta guvnor y ejecutamos el siguiente comando

mvn clean install -Dmaven.test.skip=true jetty:run-exploded

y esperamos a que nos aparezca lo siguiente:

 | [INFO] Started Jetty Server
 | [INFO] Starting scanner at interval of 10 seconds.

en caso de no aparecer, consulte con el administrador!!




2) Abrimos un navegador e ingresamos a la siguiente url: http://localhost:9080/drools-guvnor/

puede ser que nos aparezca la siguente leyenda:

 | This looks like a brand new repository.
 | Would you like to install a sample repository?

hagan click en "No thanks".




3) Hacemos click en "Administration" / "Import Export"

Elejimos el archivo "repository_export.xml" que esta al mismo nivel que la carpetas de candy-app y guvnor.

Oprimimos en "Import" y esperamos a que nos muestre el mensaje de confirmacion!

De esta forma importamos las reglas, dsl y configuraciones que estaban expuestos en la charla.




4) Ingresamos al proyecto de candy crush, ejecutamos el siguente comando: 

 | mvn eclipse:eclipse

Luego entramos al eclipse e importamos el proyecto, una vez finalizado solamente tenemos que ejecutar el Main de la clase "ar.com.epidataconsulting.drools.CandyCrushTest"

Si esta todo bien, en la cosola del eclipse no debe figurar ninguna exception y nos va a visualizar una ventana con un botón denominado "capturar"

Oprimimos el boton "capturar" y seleccionamos (haciendo doble click y arrastrando) el área del tablero del juego y oprimimos "enter".



REFERENCIAS
===========

o- [PLUGIN ECLIPSE (guvnor tools)] http://download.jboss.org/drools/release/5.5.0.Final/org.drools.updatesite/