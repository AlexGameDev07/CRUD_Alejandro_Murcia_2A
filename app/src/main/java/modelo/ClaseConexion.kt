package modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
     public fun cadenaConexion(): Connection?{

        try{
            val url = "jdbc:oracle:thin:@10.10.0.79:1521:xe"
            val usuario = "system"
            val password = "desarrollo"

            val connection = DriverManager.getConnection(url, usuario, password)

            return connection
        }catch (ex: Exception){
            println("Este es el error: $ex")
            return null
        }
    }
}