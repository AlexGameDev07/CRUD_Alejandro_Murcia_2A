package alejandro.murcia.crud_alejandro_murcia_2a

import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.DataClassProductos

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mandar a llamar todos los elementos de la pantalla
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtPrecio = findViewById<EditText>(R.id.txtPrecio)
        val txtCantidad = findViewById<EditText>(R.id.txtCantidad)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)


        //2- Programar el boton
        btnAgregar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                //Hola profe :D
                //1- Crear un objeto de la clase conexion
                val claseConexion = ClaseConexion().cadenaConexion()

                //2-creo una variable que contenga un PreparedStatement
                val addProducto = claseConexion?.prepareStatement("INSERT INTO TB_PRODUCTOS(" +
                        "nombreProducto, " +
                        "precio, " +
                        "cantidad) " +
                        "VALUES(?,?,?)")!!
                addProducto.setString(1, txtNombre.text.toString())
                addProducto.setInt(2, txtPrecio.text.toString().toInt())
                addProducto.setInt(3,txtCantidad.text.toString().toInt())
                addProducto.executeUpdate()
            }
        }

    /***** -Agregar item card- ***********************************************************************************/

        val rcvProductos = findViewById<RecyclerView>(R.id.rcvProductos)

        //Asignar un layout al RecyclerView

        rcvProductos.layoutManager = LinearLayoutManager(this)

        //Funcion para obtener datos
        fun ObtenerDatos():List<DataClassProductos>{
            val objConexion = ClaseConexion().cadenaConexion()

            val Statement = objConexion?.createStatement()
            val resultSet = Statement?.executeQuery("SELECT * FROM TB_PRODUCTOS")!!

            val productos = mutableListOf<DataClassProductos>()
            while (resultSet.next()){
                val nombre = resultSet.getString("nombreProducto")
                val producto = DataClassProductos(nombre)
                productos.add(producto)
            }

            return productos

        }

        //Asignar un adaptador
        CoroutineScope(Dispatchers.IO).launch {
            val productosDB = ObtenerDatos()
            withContext(Dispatchers.Main){
                val myAdapter = Adaptador(productosDB)
                rcvProductos.adapter = myAdapter
            }
        }
    }
}