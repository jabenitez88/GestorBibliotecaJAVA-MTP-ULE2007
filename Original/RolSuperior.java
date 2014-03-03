
import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;

/** Esta clase es la que contendrá las acciones que añaden, borran o modifican usuarios o libros de la base de datos.*/
public class RolSuperior extends JInternalFrame
{	final static String InsertarUsuario = "INSERT INTO Usuarios (Nombre,Apellidos,Email,Telefono,Direccion,Titulacion,ROL,DNI,Password) VALUES (";
	final static String InsertarLibro = "INSERT INTO Libros (Titulo,Autores,Editorial,Edicion,Anio,Paginas,Signatura,ISBN,CopiasSala,CopiasPrestamo,PrestadasSala,PrestadasPrestamo,CortoPlazo) VALUES (";
	final static String InsertarRevista = "INSERT INTO Revistas (VolumenCreado,Titulo,Autores,Editorial,Volumen,Anio,Paginas,Signatura,Numero,CopiasSala,CopiasPrestamo,PrestadasSala,PrestadasPrestamo,CortoPlazo) VALUES (FALSE,";

	public RolSuperior()
	{

	}
	/** Esta función recibe un array de cadenas con los datos para insertar usuarios */
	protected int setDatosUsuarios(String datos[])
	{	
		int i = 0;
		boolean hayTitulacion = true;
		String datosInsercion;
		datosInsercion = InsertarUsuario;

		/* Recorremos el array de cadenas para crear la cadena de inserción */
		for(i=0;i<datos.length-1;i++) 
		{	// El 3 y el 7 son enteros, con lo que no se les pone comillas simples.
			if((i != 3) && (i != 7)) datosInsercion = datosInsercion + "'" + datos[i] + "',";
			else datosInsercion = datosInsercion + datos[i] + ",";
			
		}
		// Insertamos el último elemento, que es un caso especial.
		datosInsercion = datosInsercion + "'" + datos[8] + "');";
		
		//System.out.println(datosInsercion);	
		/* Creamos el objeto de Mysql para hacer la llamada a la db */
		MiMysql insertando =  new MiMysql();
		insertando.LlamadaDB("INSERT INTO Titulaciones (Titulo) VALUES ('"+datos[5]+"')");
		insertando.LlamadaDB(datosInsercion);	
		return(insertando.ultimaID());
	}
	/** Función que inserta fondos en la tabla libros o revistas */
	protected void setDatosFondos(String datos[],boolean esLibro)
	{	int i = 0;
		String datosInsercion;
		if (esLibro) datosInsercion = InsertarLibro;
		else datosInsercion = InsertarRevista;
		/* Recorremos el array de cadenas para crear la cadena de inserción */

		for(i=0;i<datos.length-1;i++) 
		{	// El 3 y el 7 son enteros, con lo que no se les pone comillas simples.

			if((i != 4) && (i != 5) && (i != 7) && (i != 8)) datosInsercion = datosInsercion + "'" + datos[i] + "',";
			else datosInsercion = datosInsercion + datos[i] + ",";
			
		}

		// Insertamos el último elemento, que es un caso especial.
		if(datos[10].compareTo("TRUE")==0) datosInsercion = datosInsercion + "0,0,TRUE);";
		else datosInsercion = datosInsercion + "0,0,FALSE);";
		//System.out.println(datosInsercion);
		//System.out.println(datosInsercion);	
		/* Creamos el objeto de Mysql para hacer la llamada a la db */
		MiMysql insertando =  new MiMysql();
		insertando.LlamadaDB(datosInsercion);	
	}
	/** Función que devuelve la tabla de usuarios  a mostrar en pantalla*/
	protected DefaultTableModel getTablaBusquedaUsuario(String busqueda, String criterio,String nivel)
	{
		MiMysql buscando = new MiMysql();
		if(criterio.compareTo("Todos")==0)
		{
			busqueda = " NSocio > 0 ";
		}
		else{
			if((criterio.compareTo("DNI") == 0) || (criterio.compareTo("Telefono") == 0) || (criterio.compareTo("NSocio") == 0)) busqueda = criterio + " = " + busqueda;
			else busqueda = criterio + " RLIKE '" + busqueda + "'";
		}
	
		//System.out.println(busqueda);
		DefaultTableModel modelo = buscando.ConsultaUsuarioDB(busqueda,nivel);
		return(modelo);
	}
	/** Función que devuelve la tabla de fondos  a mostrar en pantalla*/
	protected DefaultTableModel getTablaBusquedaFondo(String busqueda, String criterio,boolean esLibro)
	{
		MiMysql buscando = new MiMysql();
		if(criterio.compareTo("Todos")==0)
		{
			busqueda = " id > 0 ";
		}
		else
		{
			if((criterio.compareTo("Anio") == 0) || (criterio.compareTo("Paginas") == 0) || (criterio.compareTo("Numero") == 0)|| (criterio.compareTo("ISBN") == 0)) busqueda = criterio + " = " + busqueda;
			else busqueda = criterio + " RLIKE '" + busqueda + "'";
		}
		//System.out.println(busqueda);
		DefaultTableModel modelo = buscando.ConsultaFondoDB(busqueda,esLibro);
		return(modelo);
	}
	/** Función que devuelve la tabla de fondos a mostrar en pantalla, para lectores*/
	protected NonEditableTableModel getTablaBusquedaFondoLector(String busqueda, String criterio,boolean esLibro)
	{
		MiMysql buscando = new MiMysql();
		if(criterio.compareTo("Todos")==0)
		{
			busqueda = " id > 0 ";
		}
		else
		{
			if((criterio.compareTo("Anio") == 0) || (criterio.compareTo("Paginas") == 0) || (criterio.compareTo("Numero") == 0)|| (criterio.compareTo("ISBN") == 0)) busqueda = criterio + " = " + busqueda;
			else busqueda = criterio + " RLIKE '" + busqueda + "'";
		}
		
		NonEditableTableModel modelo = buscando.ConsultaFondoDBLector(busqueda,esLibro);
		return(modelo);
	}
	/** Función que devuelve la tabla de mensajes  a mostrar en pantalla*/
	protected String[] getDatosMensaje(String idmensaje)
	{
		MiMysql cogeDatos = new MiMysql();
		return(cogeDatos.RecogeMensaje(idmensaje));
		
	}
	/** Función que devuelve la tabla de titulaciones*/
	protected String[] getTitulaciones()
	{
		MiMysql cogeDatos = new MiMysql();
		return(cogeDatos.DameTitulaciones());
		
	}
	/** Función que hace las comprobaciones iniciales*/
	static public void comprobacionesIniciales()
	{
		MiMysql cogeConsultas = new MiMysql();
		cogeConsultas.devolverConsultas();
		cogeConsultas.comprobarCaducados();
		cogeConsultas.comprobarReservas();
	}
}