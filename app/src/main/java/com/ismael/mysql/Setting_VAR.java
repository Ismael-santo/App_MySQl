package com.ismael.mysql;

import com.ismael.mysql.ui.categoria.Categoria;

public class Setting_VAR {
    public static final String TAG = "InicioActivity";
    public static final String mensajex="";

    //IP SERVER DESTINO: 192.168.43.46
    public static final String URL_PRUEBA = "http://ismaelfalta/service2020/json1.php";
    public  static final String URL_PRUEBA_HOSTING = "http://hosting/json1.php";
    public static final String URL_PRUEBA_HOSTING1 = "http://hosting/json1.php";


   //URL DEL CRUD//

   //URL para registrar una nueva categoria de producto.
   public static  final String URL_registrar_Categoria = "http://192.168.43.46/service2020/guardar_categoria_php";
   //URL para cargar valores en formato de lista de las categorias en spinner
    //para mostrar en spinner del layout producto
    public static final String URL_consultaAllCategoria = "http://192.168.43.46/service2020/buscar:Categoria_php";
    //URL para registrar productos nuevos
    public static final String URL_registrarproducto = "http://192.168.43.46/service20207guardar_productos.php";

}
