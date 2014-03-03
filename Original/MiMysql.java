import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
/** 
 * Esta clase es la que se encarga de hacer todo lo relacionado con la 
 * base de datos.
 */
public class MiMysql
{
	/** Variables a utilizar en la unidad */
	/** Nombre de la base de datos */
    static String bd = "";  
    /** Usuario de la base de datos */
    static String login = "root";
    /** Contraseña de la base de datos */
    static String password = "stop88";
    /** Localizacion de la base de datos */
    static String url = "jdbc:mysql://localhost:3306/"+bd;
    /** Conector */
    static Connection conn = null;
    
    /** Constantes para crear la base de datos y las tablas que están en ella */
    /** CreaDB crea la base de datos */
    final static String CreaDB = "CREATE DATABASE IF NOT EXISTS JABAGB;";
    /** Entra sirve para conectar a la base de datos JABAGB */
    final static String Entra  = "USE JABAGB;";
    /** TRoles crea la tabla de Roles */
    final static String TRoles = "CREATE TABLE IF NOT EXISTS Roles "
								+ "(id int,"
								+ "ROL VARCHAR(20),"
								+ "PRIMARY KEY (ROL)) ENGINE = INNODB;";
    /** InsertaRoles sirve para insertar roles */
    final static String InsertaRoles = "INSERT INTO Roles (ROL) VALUES ";
    /** RolesPrimarios crea los 4 roles que usaremos en la práctica */
    final static String RolesPrimarios = "('Administrador'),('Tecnico'),('Lector-Socio'),('Lector-Basico');";
    /** Tabla de titulaciones de los alumnos */
    final static String TTitulaciones = "CREATE TABLE IF NOT EXISTS Titulaciones"
								//+ "(ID int not null auto_increment,"
								+ "(Titulo VARCHAR(250),"
								+ "PRIMARY KEY (Titulo)) ENGINE = INNODB;";
    
    /** Constante para insertar las titulaciones que queramos */
    final static String InsertaTitulaciones = "INSERT INTO Titulaciones (Titulo) VALUES ";
    /** Insertamos 2 titulaciones iniciales */
    final static String TitulacionesPrimarias = "('Ingenieria Informatica'),('Administracion y Direccion de Empresas');";
    /** Crea la tabla de Usuarios */
    final static String TUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios"
								+ "(Nombre VARCHAR(250),"
								+ "Apellidos VARCHAR(250),"
								+ "Email VARCHAR(250),"
								+ "Telefono int(14),"
								+ "Direccion VARCHAR(250),"
								+ "Titulacion VARCHAR(250),"
								+ "NSocio int auto_increment," 
								+ "ROL VARCHAR(20),"
								+ "DNI int(8),"
								+ "Password VARCHAR(50) not null,"
								+ "index (ROL),"
								+ "foreign key (ROL) references Roles(ROL) on update cascade," 
								+ "index (Titulacion),"
								+ "foreign key (Titulacion) references Titulaciones(Titulo) on update cascade," 
								+ "PRIMARY KEY (Nsocio)) ENGINE = INNODB;";
    

    /** Constante para insertar usuarios */
    final static String InsertaUsuario = "INSERT INTO Usuarios (Nombre,Apellidos,Email,Telefono,Direccion,Titulacion,ROL,DNI,Password) VALUES (";
    /** Inserto el usuario primario Administrador a la DB con esta constante */
    final static String DatosAdmin =  "'Jose Alberto',"
								+ "'Benítez Andrades',"
								+ "'infjab02@estudiantes.unileon.es',"
								+ "606733265,'C/Moisés de León 48 5D'," 
								+ "'Ingenieria Informatica',"
								+ "'Administrador',"
								+ "71454586,"
								+ "'stop88');";
    /** Usuario tecnico, comun para todos los tecnicos */
    final static String DatosTecnico =  "'Tecnico',"
								+ "'Especial',"
								+ "'infjab02@estudiantes.unileon.es',"
								+ "606733265,'C/Moisés de León 48 5D'," 
								+ "'Ingenieria Informatica',"
								+ "'Tecnico',"
								+ "71454586,"
								+ "'stop88');";
    /** Datos de la tabla de Libros */
    final static String TLibros = "CREATE TABLE IF NOT EXISTS Libros"
								+ "(id int auto_increment,"
								+ "Titulo VARCHAR(250),"
								+ "Autores VARCHAR(250),"
								+ "Editorial VARCHAR(250),"
								+ "Edicion VARCHAR(250),"
								+ "Anio int(4),"
								+ "Paginas int,"
								+ "Signatura VARCHAR(20),"
								+ "ISBN bigint(13),"
								+ "CopiasSala int,"
								+ "CopiasPrestamo int,"
								+ "PrestadasSala int,"
								+ "PrestadasPrestamo int,"
								+ "CortoPlazo Boolean,"
								+ "PRIMARY KEY (id)) ENGINE = INNODB;";
    /** Datos de la tabla Revistas */
    final static String TRevistas = "CREATE TABLE IF NOT EXISTS Revistas"
								+ "(id int auto_increment,"
								+ "Titulo VARCHAR(250),"
								+ "Autores VARCHAR(250),"
								+ "Editorial VARCHAR(250),"
								+ "Volumen VARCHAR(250),"
								+ "VolumenCreado BOOLEAN,"
								+ "VolumenReal VARCHAR(250),"
								+ "Numero int,"
								+ "Anio int(4),"
								+ "Paginas int,"
								+ "Signatura VARCHAR(20),"
								+ "CopiasSala int,"
								+ "CopiasPrestamo int,"
								+ "PrestadasSala int,"
								+ "PrestadasPrestamo int,"
								+ "CortoPlazo Boolean,"
								+ "PRIMARY KEY (id)) ENGINE = INNODB;";
    /** Datos de la tabla Prestamos */
    final static String TPrestamos = "CREATE TABLE IF NOT EXISTS Prestamos"
								+ "(id_prestamo int NOT NULL AUTO_INCREMENT,"
								+ "id_revista int REFERENCES Revistas.id,"
								+ "id_libro int REFERENCES Libros.id,"
								+ "NSocio int REFERENCES Usuarios.NSocio,"
								+ "Eslibro BOOLEAN,"
								+ "Fecha_inicio DATE,"
								+ "Fecha_fin DATE,"
								+ "Fecha_devuelto DATE,"
								+ "Devuelto BOOLEAN,"
								+ "Consulta BOOLEAN,"
								+ "AvisoEnviado BOOLEAN,"
								+ "PRIMARY KEY (id_prestamo))ENGINE = InnoDB;"; 
    /** Datos de la tabla Reservas */
    final static String TReservas = "CREATE TABLE IF NOT EXISTS Reservas"
								+ "(id_reserva int NOT NULL AUTO_INCREMENT,"
								+ "id_libro int REFERENCES Libros.id,"
								+ "id_revista int REFERENCES Revistas.id,"
								+ "NSocio int REFERENCES Usuarios.NSocio,"
								+ "Eslibro BOOLEAN,"
								+ "Puede BOOLEAN,"
								+ "Fecha_reserva DATE,"
								+ "Fecha_puede DATE,"
								+ "PRIMARY KEY (id_reserva))ENGINE = InnoDB;";
    /** Datos de la tabla Buzon */
    final static String TBuzon = "CREATE TABLE IF NOT EXISTS Buzon"
								+ "(id_mensaje int NOT NULL AUTO_INCREMENT,"
								+ "id_remitente int REFERENCES Usuarios.NSocio,"
								+ "id_receptor int REFERENCES Usuarios.NSocio,"
								+ "Fecha_envio DATE,"
								+ "ParaPrestamo boolean,"
								+ "NoNuevo boolean,"
								+ "Asunto VARCHAR(250),"
								+ "Mensaje Text,"
								+ "PRIMARY KEY (id_mensaje))ENGINE = InnoDB;";
    /** Datos de la tabla Buzon2 */
    final static String TBuzon2 = "CREATE TABLE IF NOT EXISTS Buzon2"
								+ "(id_mensaje int NOT NULL AUTO_INCREMENT,"
								+ "id_remitente int REFERENCES Usuarios.NSocio,"
								+ "id_receptor int REFERENCES Usuarios.NSocio,"
								+ "Fecha_envio DATE,"
								+ "ParaPrestamo boolean,"
								+ "NoNuevo boolean,"
								+ "Asunto VARCHAR(250),"
								+ "Mensaje Text,"
								+ "PRIMARY KEY (id_mensaje))ENGINE = InnoDB;";
    /** Constantes para conocer la password de un usario y su nivel */
    final static String LoginDB = "SELECT password FROM Usuarios WHERE NSocio = ";
    final static String LoginNivel = "SELECT Rol FROM Usuarios WHERE NSocio = ";
    /** Constante para prestar un fondo */
    final static String prestaFondo = "INSERT INTO Prestamos (Devuelto,AvisoEnviado,id_libro,id_revista,NSocio,Eslibro,Fecha_inicio,Fecha_fin,Consulta) VALUES (FALSE,FALSE,";
    /** Constructor de la clase MiMysql */
    public MiMysql() 
    {
		
    }
    /** Método que conecta a la base de datos */
    public static void ConectaDB() throws Exception
    {
        try
        {   
        	FileReader fr = new FileReader("config.ini");
        	BufferedReader bf = new BufferedReader(fr);
        	login = bf.readLine();
        	password = bf.readLine();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url,login,password);
            conn.setAutoCommit(false);
            //System.out.println(login+ " " +password);
            EntraDB();
	        if (conn != null)
	            {
	                //System.out.println("Conexión a base de datos "+url+" ... Ok");
	                //conn.close();
	            }
        }
        catch(SQLException ex)
        {	
	    System.out.println("Ha habido un error de SQL "+ex);
	
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
  
    }
    /** Método que sirve para hacer querys a la base de datos, con esto
     * para hacer un simple UPDATE sólo tendremos que poner por ejemplo:
     * Llamada("UPDATE Libros SET id = 0 WHERE id = 1")
     */
    public static void LlamadaDB(String llamada) 
    {	
    	try
	{	 
	  	// Crear enunciado
		Statement stmt = conn.createStatement();
		// Ejecutar query
		stmt.executeUpdate(llamada);
		//System.out.println(llamada);
		//rs = stmt.executeQuery(llamada);
		conn.commit();
		//Cerrando el statement
		stmt.close();
	}
	catch(SQLException ex)
        {	
	    System.out.println("Ha habido un error de SQL "+ex);
	
        }
    }
    /** Función con la que hacemos un USE JABAGB para conectar a la 
     * base de datos de la biblioteca */
    public static void EntraDB()
	{	try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(Entra);
			conn.commit();
			stmt.close();
			//System.out.println(bd);
	        //LlamadaDB(TRoles);
			//LlamadaDB(TTitulaciones);
			//LlamadaDB(TUsuarios);
			//LlamadaDB(TLibros);
			//LlamadaDB(TRevistas);
			//LlamadaDB(TPrestamos);
			//LlamadaDB(TReservas);
			//LlamadaDB(TBuzon);
			//LlamadaDB(TBuzon2);
			//LlamadaDB(InsertaRoles+RolesPrimarios);
			//LlamadaDB(InsertaTitulaciones+TitulacionesPrimarias);
			//LlamadaDB(InsertaUsuario+DatosAdmin);
			//LlamadaDB(InsertaUsuario+DatosTecnico);
		}
		catch(SQLException ex)
		{		// Si no ha conectado significa que la DB no existe,
				// asío que con estas llamadas se crea todo.
				LlamadaDB(CreaDB);
	            LlamadaDB(Entra);
	            LlamadaDB(TRoles);
	            LlamadaDB(TTitulaciones);
	            LlamadaDB(TUsuarios);
	            LlamadaDB(TLibros);
	            LlamadaDB(TRevistas);
	            LlamadaDB(TPrestamos);
	            LlamadaDB(TReservas);
	            LlamadaDB(TBuzon);
	            LlamadaDB(TBuzon2);
	            LlamadaDB(InsertaRoles+RolesPrimarios);
	            LlamadaDB(InsertaTitulaciones+TitulacionesPrimarias);
	            LlamadaDB(InsertaUsuario+DatosAdmin);
	            LlamadaDB(InsertaUsuario+DatosTecnico);
		}
    }
    /** Esta función comprueba si los datos introducidos por el usuario son correctos */
    public static String Login(String DNI,boolean Ejecuta) 
    {   String pass = "";
        try
		{	Statement stmt = conn.createStatement();
			//ejecutar query
			//stmt.executeUpdate(LoginDB+DNI+";");
			ResultSet rs;
			if (Ejecuta) rs = stmt.executeQuery(LoginDB+DNI+";");
			else rs = stmt.executeQuery(LoginNivel+DNI+";");
			//System.out.println(LoginDB+DNI);
			conn.commit();
			// Ciclo para recorrer los resultados
			//cerrando el statement
		while (rs.next( ))  
			{
				pass = rs.getString(1);		
	         } 
	
			stmt.close();
			return(pass);
		}
		catch(SQLException ex)
		{
			System.out.println(LoginDB+DNI);
			System.out.println("Fallo en el query "+ex);
			return("");
		}
    }

    /** Función que devuelve un DefaultTableModel con los usuarios que ha consultado el 
     * técnico o el administrador.
     * @param Llamada
     * Llamada que hacemos
     * @param nivel
     * Nivel que tiene la persona que hace la llamada.
     * @return
     * Devuelve un defaulttablemodel con los usuarios.
     */
    public static DefaultTableModel ConsultaUsuarioDB(String Llamada,String nivel)
    {
		try
		{   // Creo el modelo, restringiendo la primera columna.
			DefaultTableModel modelo = new DefaultTableModel(){
	        	public boolean isCellEditable(int row, int column)
	        	 {
	        		if(column == 0) return false;
	        		else return true;
	        	 }
	        };
			Statement s = conn.createStatement();
			// Si es tecnico, no ve a los otros tecnicos ni administadores.
			if(nivel.compareTo("Tecnico") == 0) Llamada += " AND Rol != 'Administrador' AND Rol != 'Tecnico'";
			ResultSet rs = s.executeQuery("SELECT Nsocio,Nombre,Apellidos,DNI,Direccion,Email,Password,Telefono,Rol,Titulacion FROM Usuarios WHERE "+Llamada+ " AND Nsocio !=2");
			//System.out.println(Llamada);
			// Creamos las columnas.
			modelo.addColumn("Num Socio");
			modelo.addColumn("Nombre");
			modelo.addColumn("Apellidos");
			modelo.addColumn("DNI");
			modelo.addColumn("Direccion");
			modelo.addColumn("Email");
			modelo.addColumn("Password");
			modelo.addColumn("Telefono");
			modelo.addColumn("Rol");
			modelo.addColumn("Titulacion");
		
			// Bucle para cada resultado en la consulta
			while (rs.next())
			{	
			   // Se crea un array que será una de las filas de la tabla.
			   Object [] fila = new Object[10]; // Hay tres columnas en la tabla
			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<10;i++)
			      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila);
			}
			return(modelo);
		}
		catch (SQLException ex)
		{	DefaultTableModel modelo = new DefaultTableModel();
			System.out.println("Error SQL " + ex);
			//System.out.println(Llamada);
			return(modelo);
		}
    }
    /** Al igual que la función anterior, nos va a devolver el resultado de una llamada
     * en la consulta de unos libros o revistas en este caso.
     * @param Llamada
     * Llamada para seleccionar libros o revistas.
     * @param libro
     * Si es libro, estará a true, sino a false.
     * @return
     */
    public static  DefaultTableModel ConsultaFondoDB(String Llamada, boolean libro)
    {

		try
		{   DefaultTableModel  modelo = null;
			if(libro)
			{
				    modelo = new DefaultTableModel(){
		        	public boolean isCellEditable(int row, int column)
		        	 {
		        		if((column == 0) ||(column == 11) || (column == 12))
		        	     	return false;
		        		else return true;
		        	 }
		        };
			}
			else
			{
				     modelo = new DefaultTableModel(){
		        	public boolean isCellEditable(int row, int column)
		        	 {
		        		if((column == 0) ||(column == 12) || (column == 13))
		        	     	return false;
		        		else return true;
		        	 }
		        };
			}

			Statement s = conn.createStatement();
			ResultSet rs;
			int filaAr = 13;
			// Si es libro, selecciono una serie de parámetros y además trabajo con la tabla libros
			// sino, trabajo con revistas.
			if(libro) rs = s.executeQuery("SELECT ID, Titulo,Autores,Editorial,Edicion,Anio,Paginas,Signatura,ISBN,CopiasSala,CopiasPrestamo,PrestadasSala,PrestadasPrestamo,CortoPlazo FROM Libros WHERE "+Llamada);
			else rs = s.executeQuery("SELECT ID,Titulo,Autores,Editorial,Volumen,VolumenReal,Numero,Anio,Paginas,Signatura,CopiasSala,CopiasPrestamo,PrestadasSala,PrestadasPrestamo,CortoPlazo FROM Revistas WHERE "+Llamada);
			// Creamos las columnas.
			modelo.addColumn("ID");
			modelo.addColumn("Titulo");
			modelo.addColumn("Autores");
			modelo.addColumn("Editorial");
			if(libro)
			{
				modelo.addColumn("Edicion");
				modelo.addColumn("Anio");
				modelo.addColumn("Paginas");
				modelo.addColumn("Signatura");
				modelo.addColumn("ISBN");
				modelo.addColumn("Disp. Sala");
				modelo.addColumn("Disp. Prestamo");
				modelo.addColumn("Prestados Sala");
				modelo.addColumn("Prestados Prestamo");
			}
			else
			{
				modelo.addColumn("Volumen");
				modelo.addColumn("Volumen Real");
				modelo.addColumn("Numero");
				modelo.addColumn("Anio");
				modelo.addColumn("Paginas");
				modelo.addColumn("Signatura");
				modelo.addColumn("Disp. Sala");
				modelo.addColumn("Disp. Prestamo");
				modelo.addColumn("Prestadas Sala");
				modelo.addColumn("Prestadas Prestamo");
				filaAr = 14;
			}
			modelo.addColumn("Corto Plazo");
			// Bucle para cada resultado en la consulta
			int filasTotales = 0;
			while (rs.next())
			{
			   // Se crea un array que será una de las filas de la tabla.
			   Object [] fila = new Object[15]; // Hay tres columnas en la tabla
	
			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<filaAr;i++)
			      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			   if(rs.getInt(filaAr+1) == 0) fila[filaAr] = "NO";
			   else fila[filaAr] = "SI";
			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila);
			   filasTotales++;
			}
			return(modelo);
		}
		catch (SQLException ex)
		{	DefaultTableModel modelo = new DefaultTableModel();
			System.out.println("Error SQL " + ex);
			return(modelo);
		}
    }
    /** Función igual que la anterior, pero con restricciones de edición para los lectores */
    public static NonEditableTableModel ConsultaFondoDBLector(String Llamada, boolean libro)
    {

		try
		{
			NonEditableTableModel  modelo = new NonEditableTableModel();
			Statement s = conn.createStatement();
			ResultSet rs;
			int finalAr = 11;
			if(libro) rs = s.executeQuery("SELECT ID, Titulo,Autores,Editorial,Edicion,Anio,Paginas,Signatura,ISBN,CopiasSala,CopiasPrestamo,CortoPlazo FROM Libros WHERE "+Llamada);
			else rs = s.executeQuery("SELECT ID,Titulo,Autores,Editorial,Volumen,VolumenReal,Numero,Anio,Paginas,Signatura,CopiasSala,CopiasPrestamo,CortoPlazo FROM Revistas WHERE "+Llamada);
			// Creamos las columnas.
			modelo.addColumn("ID");
			modelo.addColumn("Titulo");
			modelo.addColumn("Autores");
			modelo.addColumn("Editorial");
			if(libro)
			{
				modelo.addColumn("Edicion");
				modelo.addColumn("Anio");
				modelo.addColumn("Paginas");
				modelo.addColumn("Signatura");
				modelo.addColumn("ISBN");
				modelo.addColumn("Disp. Sala");
				modelo.addColumn("Disp. Prestamo");
			}
			else
			{
				modelo.addColumn("Volumen");
				modelo.addColumn("Volumen Real");
				modelo.addColumn("Numero");
				modelo.addColumn("Anio");
				modelo.addColumn("Paginas");
				modelo.addColumn("Signatura");
				modelo.addColumn("Disp. Sala");
				modelo.addColumn("Disp. Prestamo");
				finalAr = 12;
			}
			modelo.addColumn("Corto Plazo");
			// Bucle para cada resultado en la consulta
			while (rs.next())
			{
			   // Se crea un array que será una de las filas de la tabla.
			   Object [] fila = new Object[13]; // Hay tres columnas en la tabla
	
			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<finalAr;i++)
			      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			   if(rs.getInt(finalAr+1) == 0) fila[finalAr] = "NO";
			   else fila[finalAr] = "SI";
			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila);
			}
			return(modelo);
		}
		catch (SQLException ex)
		{	NonEditableTableModel modelo = new NonEditableTableModel();
			System.out.println("Error SQL " + ex);
			return(modelo);
		}
    }
    /** Función que devuelve la tabla de un buzón
     * 
     * @param queBuzon
     * Nos dice si es el Buzón de entrada o de salida, ya que Buzon es para mensajes de entrada
     * y Buzon2 para salida.
     * @param Llamada
     * Llamada para ver qué mensajes deseo ver.
     * @param tecnico
     * Tecnico nos dice si es del buzón de técnicos general o de un usuario normal.
     * @return
     */
    public static ModeloTablaBuzon ConsultaMensajesBuzon(String queBuzon,String Llamada,boolean tecnico)
    {	 
    	String[] columnNames = null;
		String[] columnNames1 = {"ID Mensaje","Num.Socio","Nombre Remitente", "Asunto", "Fecha","Es Nuevo" };
		String[] columnNames2 = {"ID Mensaje","Num.Socio","Nombre Remitente", "Asunto", "Fecha","Es Nuevo","PRESTAMO" };
		String[] columnNames3  = {"ID Mensaje","Num.Socio","Nombre Receptor", "Asunto", "Fecha" };
    	if(queBuzon.compareTo("Buzon") == 0)
    		{
    			if (tecnico) 
    			{
    				columnNames = columnNames2;
    			}
    			else columnNames = columnNames1;
    		}
    	else columnNames = columnNames3;
    	ModeloTablaBuzon modelo = new ModeloTablaBuzon(columnNames);
		try
		{
			Statement s = conn.createStatement();
			ResultSet rs;
			if (queBuzon.compareTo("Buzon") == 0) rs = s.executeQuery("SELECT Buzon.id_mensaje,Buzon.ID_Remitente,Usuarios.Nombre,Usuarios.Apellidos,Buzon.Asunto,Buzon.Fecha_envio,Buzon.Mensaje,Buzon.NoNuevo,Buzon.ParaPrestamo "
										+ "FROM Buzon join Usuarios on Buzon.id_remitente = Usuarios.NSocio  WHERE "+Llamada);
			else rs = s.executeQuery("SELECT Buzon2.id_mensaje,Buzon2.id_receptor,Usuarios.Nombre,Usuarios.Apellidos,Buzon2.Asunto,Buzon2.Fecha_envio,Buzon2.Mensaje "
										+ "FROM Buzon2 join Usuarios on Buzon2.id_receptor = Usuarios.NSocio  WHERE "+Llamada);
			
	
			// Bucle para cada resultado en la consulta
			while (rs.next())
			{
			   // Se crea un array que será una de las filas de la tabla.
			   String [] fila = new String[7]; // Hay tres columnas en la tabla
			   if(queBuzon.compareTo("Buzon") == 0)
			   {
				   if(tecnico)
				   {
					   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
					   for (int i=0;i<6;i++)
					   {
						  // Titulo y apellidos.
						  if(i == 2) fila[i] = rs.getString(i+1) + " " + rs.getString(i+2);
						  else if (i > 2) fila[i] = rs.getString(i+2);
						  else fila[i] = rs.getString(i+1);
					       // El primer indice en rs es el 1, no el cero, por eso se suma 1.
					   }
					   if (rs.getInt(8) == 0) fila[5] = "SI";
					   else fila[5] = "NO";
					   if (rs.getInt(9) == 1) fila[6] = "SI";
					   else fila[6] = "NO";
					   // Se añade al modelo la fila completa.
				   }
				   else 
				   {
					   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
					   for (int i=0;i<5;i++)
					   {
						  // Titulo y apellidos.
						  if(i == 2) fila[i] = rs.getString(i+1) + " " + rs.getString(i+2);
						  else if (i > 2) fila[i] = rs.getString(i+2);
						  else fila[i] = rs.getString(i+1);
					       // El primer indice en rs es el 1, no el cero, por eso se suma 1.
					   }
					   if (rs.getInt(8) == 0) fila[5] = "SI";
					   else fila[5] = "NO";
					   // Se añade al modelo la fila completa.
				   }
			   }
			   else 
			   {
				   for (int i=0;i<5;i++)
				   {
					  // Titulo y apellidos.
					  if(i == 2) fila[i] = rs.getString(i+1) + " " + rs.getString(i+2);
					  else if (i > 2) fila[i] = rs.getString(i+2);
					  else fila[i] = rs.getString(i+1);
				       // El primer indice en rs es el 1, no el cero, por eso se suma 1.
				   }
				   // Se añade al modelo la fila completa.
			   }
			   modelo.addElement(fila);
			}
			
		}
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
		}
		finally
		{
			return(modelo);
		}
    }
    /** Función que nos sirve para consultar los préstamos para técnicos y administradores
     * @param Llamada
     * @return
     */
    public static DefaultTableModel ConsultaPrestamo(String Llamada)
    {

		try
		{
			DefaultTableModel  modelo = new DefaultTableModel(){
	        	public boolean isCellEditable(int row, int column)
	        	 {
	        		if(column == 6)
	        	     	return true;
	        		else return false;
	        	 }
	        };
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT id_prestamo, id_libro,id_revista,NSocio,Fecha_inicio,Fecha_fin,Devuelto,Consulta FROM Prestamos WHERE "+Llamada);
			//System.out.println(Llamada);
			// Creamos las columnas.
		    modelo.addColumn("ID Prestamo");
			modelo.addColumn("ID Libro");
			modelo.addColumn("ID Revista");
			modelo.addColumn("N Socio");
			modelo.addColumn("Fecha inicio");
			modelo.addColumn("Fecha fin");
			modelo.addColumn("Devuelto");
			modelo.addColumn("Consulta");
			
			// Bucle para cada resultado en la consulta
			while (rs.next())
			{
			   // Se crea un array que será una de las filas de la tabla.
			   Object [] fila = new Object[8]; // Hay tres columnas en la tabla
	
			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<7;i++)
			      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			   if(rs.getInt(7) == 0) fila[6] = "NO";
			   else fila[6] = "SI";
			   if(rs.getInt(8) == 0) fila[7] = "NO";
			   else fila[7] = "SI";
			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila);
			}
			return(modelo);
		}
		catch (SQLException ex)
		{	DefaultTableModel modelo = new DefaultTableModel();
			System.out.println("Error SQL " + ex);
			return(modelo);
		}
    }
    /** Función que nos sirve para consultar los préstamos para lectores
     * @param Llamada
     * @return
     */
    public static DefaultTableModel ConsultaPrestamoLector(String Llamada)
    {

		try
		{
			DefaultTableModel  modelo = new DefaultTableModel(){
	        	public boolean isCellEditable(int row, int column)
	        	 {
	        		if(column == 6)
	        	     	return true;
	        		else return false;
	        	 }
	        };
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT id_prestamo, id_libro,id_revista,Fecha_inicio,Fecha_fin,Devuelto FROM Prestamos WHERE "+Llamada);
			//System.out.println(Llamada);
			// Creamos las columnas.
		    modelo.addColumn("ID Prestamo");
			modelo.addColumn("ID Libro");
			modelo.addColumn("ID Revista");
			modelo.addColumn("Fecha inicio");
			modelo.addColumn("Fecha fin");
			modelo.addColumn("Devuelto");
			
			// Bucle para cada resultado en la consulta
			while (rs.next())
			{
			   // Se crea un array que será una de las filas de la tabla.
			   Object [] fila = new Object[8]; // Hay tres columnas en la tabla
	
			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<6;i++)
			      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			   if(rs.getInt(6) == 0) fila[5] = "NO";
			   else fila[5] = "SI";
			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila);
			}
			return(modelo);
		}
		catch (SQLException ex)
		{	DefaultTableModel modelo = new DefaultTableModel();
			System.out.println("Error SQL " + ex);
			return(modelo);
		}
    }
    /** Función que devuelve la cadena de un mensaje entero en concreto.
     * @param idmensaje
     * @return
     */
    public static String[] RecogeMensaje(String idmensaje)
    {	 
    	String[] datosMensaje = new String[4];

		try
		{

			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT Usuarios.Nombre,Usuarios.Apellidos,Buzon.Fecha_envio,Buzon.Asunto,Buzon.Mensaje FROM "
										+ "Buzon join Usuarios on Buzon.id_receptor = Usuarios.NSocio WHERE Buzon.id_mensaje = "+idmensaje);
	
			// Bucle para cada resultado en la consulta
			while (rs.next())
			{
			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<4;i++)
				  // Titulo y apellidos.
				  if(i == 0) datosMensaje[i] = rs.getString(i+1) + " " + rs.getString(i+2);
				  else datosMensaje[i] = rs.getString(i+2);
			       // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			}
			
		}
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
		}
		finally
		{
			return(datosMensaje);
		}
    }
    
    /** Función que devuelve las titulaciones que hay en la tabla Titulaciones 
     */

	public static String[] DameTitulaciones()
    {	 
    	String[] datosTitulacionesf = new String[1];
		try
		{

			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT Titulo FROM Titulaciones;");
		    int numTitulaciones = 0;
		    while(rs.next()) numTitulaciones++;
		    while(rs.previous());
	    	String[] datosTitulaciones = new String[numTitulaciones];
		    int i = 0;

			// Bucle para cada resultado en la consulta
			while (rs.next())
			{
				  datosTitulaciones[i] = rs.getString(1);
				  i++;
			}
			datosTitulacionesf = datosTitulaciones;
			return(datosTitulaciones);
		}
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
			return datosTitulacionesf;
		}

    }
	/** Función que devuelve la última id añadida */
	public int ultimaID()
	{	int id = 0;
		try
		{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT LAST_INSERT_ID();");
		    while(rs.next()) id = rs.getInt(1);
		}
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
		}
		return id;
	}
	/** Función que devuelve la fecha actual, o la fecha actual más 2 días o 21 días
	 * @param actual
	 * Si actual está vacío, devuelve actual, sino, devolverá actual + 2 o +21
	 * @param corto
	 * Si actual no está vacío y corto está a true , devolverá actual + 2, sino actual + 21
	 * @return
	 */
	public String dame_fecha(String actual,boolean corto)
	{	String id = null;
		try
		{
			Statement s = conn.createStatement();
			ResultSet rs;
			if (actual.compareTo("") == 0) rs = s.executeQuery("SELECT CURRENT_DATE();");
			else
				{
					if (corto) rs = s.executeQuery("SELECT DATE_ADD("+actual+", INTERVAL 2 DAY);");
					else rs = s.executeQuery("SELECT DATE_ADD("+actual+", INTERVAL 21 DAY);");
				}
			while(rs.next()) id = rs.getString(1);
			//System.out.println(id);
		}
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
		}
		return id;
	}
	/** Función que resta a la fecha 1 día o 2 días, sirve para dar avisos de los caducados*/
	public int resta_fecha(String fecha,boolean corto)
	{	int id = 0;
		try
		{
			Statement s = conn.createStatement();
			ResultSet rs = null;
			//System.out.println("SELECT "+fecha+ " >= CURRENT_DATE() AND DATE_SUB(CURRENT_DATE(),INTERVAL 1 DAY) >= DATE_SUB("+fecha+", INTERVAL 1 DAY);");
			if (corto) rs = s.executeQuery("SELECT "+fecha+ " >= CURRENT_DATE() AND DATE_SUB(CURRENT_DATE(),INTERVAL 1 DAY) >= DATE_SUB("+fecha+", INTERVAL 1 DAY);");
			else rs = s.executeQuery("SELECT "+fecha+ " >= CURRENT_DATE() AND DATE_SUB(CURRENT_DATE(),INTERVAL 2 DAY) >= DATE_SUB("+fecha+", INTERVAL 2 DAY);");
			//System.out.println("SELECT "+fecha+ " > DATE_SUB(CURRENT_DATE(),INTERVAL 2 DAY) AND DATE_SUB(CURRENT_DATE(),INTERVAL 2 DAY) > DATE_SUB("+fecha+", INTERVAL 3 DAY);");
			while(rs.next()) 
			{		if (rs.getString(1) == null) id = 0;
					else id = 1;
			}
		}
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
		
		}
		return id;
	}
	/** Función que sirve para recoger un único campo y que es un entero en cualquier llamada
	 * lo uso para más que coger las copias de un fondo....
	 * @param llamada
	 * @return
	 */
	public int getNumCopiasFondo(String llamada)
	{	int id = 0;
		try
		{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(llamada);
		    while(rs.next()) id = rs.getInt(1);
		}
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
		}
		return id;
	}
	/** Recoge la cadena en una llamada a la base de datos
	 * por ejemplo, si hacemos un SELECT VolumenReal FROM Revistas WHERE id = 1;
	 * devolverá el nombre del volumen de esa revista.
	 * @param llamada
	 * @return
	 */
	public String getCadena(String llamada)
	{	String cad = null;
		try
		{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(llamada);
		    while(rs.next()) cad = rs.getString(1);
		}
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
		}
		return cad;
	}
	/** Devuelve el número de préstamos de un usuario.
	 * @param idusuario
	 * @return
	 */
	public int getNumPrestamosLector(String idusuario)
	{	int id = 0;
		try
		{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM Prestamos WHERE NSocio = "+idusuario+ " AND Devuelto = FALSE AND Fecha_inicio != Fecha_fin");
		    while(rs.next()) id++;
		}
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
		}
		return id;
	}
	/** Función que devuelve las consultas una vez ha pasado el día */
	public int devolverConsultas()
	{	int id = 0;
		try
		{
			String tituloLibro = null;
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT EsLibro,Consulta,id_libro,id_revista,id_prestamo FROM Prestamos WHERE Devuelto = FALSE AND Fecha_inicio = Fecha_fin AND Fecha_inicio < CURRENT_DATE();");
		    while(rs.next()) 
		    {
		    	LlamadaDB("UPDATE Prestamos SET Devuelto = TRUE, Fecha_devuelto = CURRENT_DATE() WHERE id_prestamo = "+rs.getInt(5));
		    	// Si EsLibro es 1, significa que hay que modificar los libros
		    	if(rs.getInt(1) == 1)
		    		{
		    			// Si Consulta está a 1, significa que se cogió la copia de Consultas en lugar de Pr�stamos.
		    			if(rs.getInt(2) == 1) 
		    				{
		    					LlamadaDB("UPDATE Libros SET CopiasSala = CopiasSala+1, PrestadasSala = PrestadasSala-1 WHERE id = "+rs.getInt(3));
		    					// Si el libro es volumen, debo de volver las revistas también.
		    					tituloLibro = getCadena("SELECT Titulo FROM Libros WHERE id = "+rs.getInt(3));
		    					LlamadaDB("UPDATE Revistas SET CopiasSala = 1, PrestadasSala = 0 WHERE VolumenReal = '"+tituloLibro+"'");
		    				}
		    			else {
		    					LlamadaDB("UPDATE Libros SET CopiasPrestamo = CopiasPrestamo+1, PrestadasPrestamo = PrestadasPrestamo-1 WHERE id = "+rs.getInt(3));
		    					tituloLibro = getCadena("SELECT Titulo FROM Libros WHERE id = "+rs.getInt(3));
		    					LlamadaDB("UPDATE Revistas SET CopiasPrestamo = 1, PrestadasPrestamo = 0 WHERE VolumenReal = '"+tituloLibro+"'");
		    			}
		    			
		    		}
		    	else 
		    	{
	    			if(rs.getInt(2) == 1) 
	    				{
	    					LlamadaDB("UPDATE Revistas SET CopiasSala = CopiasSala+1, PrestadasSala = PrestadasSala-1 WHERE id = "+rs.getInt(3));
	    				}
	    			else LlamadaDB("UPDATE Revistas SET CopiasPrestamo = CopiasPrestamo+1, PrestadasPrestamo = PrestadasPrestamo-1 WHERE id = "+rs.getInt(3));	
		    	}
		    	
		    	// Sino, hay que modificar revistas.
		    }
		}
		
		catch (SQLException ex)
		{
			System.out.println("Error SQL " + ex);
		}
		return id;
	}
	/** Función que comprueba los prestamos que hay caducados y avisa a sus usuarios */
	public void comprobarCaducados()
	{	int id = 0;
		try
		{
			Statement s = conn.createStatement();
			String formato = "Libros";
			int idElegido = 0;
			ResultSet rs = s.executeQuery("SELECT id_libro,Nsocio,id_prestamo,AvisoEnviado,esLibro,id_revista FROM Prestamos WHERE Devuelto = FALSE AND Fecha_fin > CURRENT_DATE() AND CURRENT_DATE() = DATE_SUB(Fecha_fin,INTERVAL 2 DAY) AND Fecha_fin != DATE_ADD(Fecha_inicio, INTERVAL 2 DAY)");
		    while(rs.next()) 
		    {
			    if(rs.getInt(4)==0) // Si AvisoEnviado es falso, se envía.
			    {
			    	idElegido = rs.getInt(1);
			    	if(rs.getInt(5)==0) 
			    		{
			    			idElegido = rs.getInt(6);
			    			formato = "Revistas";
			    		}
			    	LlamadaDB("UPDATE Prestamos SET AvisoEnviado = TRUE WHERE id_prestamo = "+rs.getInt(3));
					LlamadaDB("INSERT INTO Buzon (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,2,"+rs.getInt(2)+",'Expiracion prestamo',"
							+ "'Debe devolver el libro "+getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" en 2 d�as','"+dame_fecha("",false)+"',FALSE)" );
					LlamadaDB("INSERT INTO Buzon2 (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,2,"+rs.getInt(2)+",'Expiracion prestamo',"
							+ "'Debe devolver el libro "+getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" en 2 d�as','"+dame_fecha("",false)+"',FALSE)" );
			    }
		    }
			rs = s.executeQuery("SELECT id_libro,Nsocio,id_prestamo,AvisoEnviado,esLibro,id_revista FROM Prestamos WHERE Devuelto = FALSE AND Fecha_fin > CURRENT_DATE() AND CURRENT_DATE() = DATE_SUB(Fecha_fin,INTERVAL 1 DAY) AND Fecha_fin = DATE_ADD(Fecha_inicio, INTERVAL 2 DAY)");
		    while(rs.next()) 
		    {
		    	if(rs.getInt(4)==0)
		    	{
		    		idElegido = rs.getInt(1);
		    		if(rs.getInt(5)==0) 
		    		{
		    			idElegido = rs.getInt(6);
		    			formato = "Revistas";
		    		}
			    	LlamadaDB("UPDATE Prestamos SET AvisoEnviado = TRUE WHERE id_prestamo = "+rs.getInt(3));
					LlamadaDB("INSERT INTO Buzon (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,2,"+rs.getInt(2)+",'Expiracion prestamo',"
							+ "'Debe devolver el libro "+getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" en 1 d�a','"+dame_fecha("",false)+"',FALSE)" );
					LlamadaDB("INSERT INTO Buzon2 (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,2,"+rs.getInt(2)+",'Expiracion prestamo',"
							+ "'Debe devolver el libro "+getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" en 1 d�a','"+dame_fecha("",false)+"',FALSE)" );
		    	}
		    }
		    
		    		    rs = s.executeQuery("SELECT id_libro,Nsocio,id_prestamo,AvisoEnviado,esLibro,id_revista FROM Prestamos WHERE Devuelto = FALSE AvisoEnviado = TRUE AND Fecha_fin = CURRENT_DATE()");
		    while(rs.next()) 
		    {
		    	if(rs.getInt(4)==1)
		    	{
		    		idElegido = rs.getInt(1);
		    		if(rs.getInt(5)==0) 
		    		{
		    			idElegido = rs.getInt(6);
		    			formato = "Revistas";
		    		}
			    	LlamadaDB("UPDATE Prestamos SET AvisoEnviado = FALSE WHERE id_prestamo = "+rs.getInt(3));
					LlamadaDB("INSERT INTO Buzon (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,2,"+rs.getInt(2)+",'Expiracion prestamo',"
							+ "'Debe devolver el libro "+getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" ya que expiró en el día de ayer','"+dame_fecha("",false)+"',FALSE)" );
					LlamadaDB("INSERT INTO Buzon2 (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,2,"+rs.getInt(2)+",'Expiracion prestamo',"
							+ "'Debe devolver el libro "+getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" ya que expiró en el día de ayer','"+dame_fecha("",false)+"',FALSE)" );
		    	}
		    }
		    
		    rs = s.executeQuery("SELECT id_libro,Nsocio,id_prestamo,AvisoEnviado,esLibro,id_revista FROM Prestamos WHERE Devuelto = FALSE AvisoEnviado = FALSE AND Fecha_fin = DATE_SUB(CURRENT_DATE(),INTERVAL 1 DAY)");
		    while(rs.next()) 
		    {
		    	if(rs.getInt(4)==0)
		    	{
		    		idElegido = rs.getInt(1);
		    		if(rs.getInt(5)==0) 
		    		{
		    			idElegido = rs.getInt(6);
		    			formato = "Revistas";
		    		}
			    	LlamadaDB("UPDATE Prestamos SET AvisoEnviado = TRUE WHERE id_prestamo = "+rs.getInt(3));
					LlamadaDB("INSERT INTO Buzon (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,2,"+rs.getInt(2)+",'Expiracion prestamo',"
							+ "'Debe devolver el libro "+getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" ya que expiró en el día de ayer','"+dame_fecha("",false)+"',FALSE)" );
					LlamadaDB("INSERT INTO Buzon2 (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,2,"+rs.getInt(2)+",'Expiracion prestamo',"
							+ "'Debe devolver el libro "+getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" ya que expiró en el día de ayer','"+dame_fecha("",false)+"',FALSE)" );
		    	}
		    }
		}

		catch(SQLException ex)
		{
			
		}
	}
	
	/** Función que comprueba si una reserva ya está caducada y en tal caso comprueba si hay más reservas, o devuelve el fondo
	 * definitivamente.
	 */
	public void comprobarReservas()
	{	
		try
		{			 
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT Eslibro,id_libro,id_revista,id_reserva FROM Reservas WHERE Puede = TRUE AND Fecha_puede < CURRENT_DATE();");
			int idReserva = 0;
			int reservado = 0;
			int idSocio = 0;
			String TituloVolumen = null;
			while(rs.next()) 
			{
				reservado = 0;
				idSocio = 0;
				LlamadaDB("DELETE FROM Reservas WHERE id_reserva = "+rs.getInt(4));
				// Si EsLibro....
				if(rs.getInt(1)==1)
				{	
					idSocio = getNumCopiasFondo("SELECT Nsocio FROM Reservas WHERE id_libro = "+rs.getInt(2));
					// Si hay alguna reserva pendiente estar� != a 0
					if (idSocio == 0)
					{
						reservado = getNumCopiasFondo("SELECT id_reserva FROM Reservas WHERE id_libro = "+rs.getInt(2)+" AND Puede = FALSE ORDER BY -id_reserva");

						// Si había reserva sobre ellas
						if(reservado == 0)
							{
								TituloVolumen = getCadena("SELECT titulo FROM Libros WHERE id = "+rs.getInt(2));
								LlamadaDB("UPDATE Libros SET CopiasPrestamo = CopiasPrestamo + 1, PrestadasPrestamo = PrestadasPrestamo - 1 WHERE id = "+rs.getInt(2));
								LlamadaDB("UPDATE Revistas SET CopiasPrestamo = CopiasPrestamo+1, PrestadasPrestamo = PrestadasPrestamo - 1 WHERE VolumenReal = '"+TituloVolumen+"'");
							}
						else
						{
							LlamadaDB("UPDATE Reservas SET Puede = TRUE, Fecha_puede = CURRENT_DATE() WHERE id_reserva = "+reservado);	
						}
					}
					else 
						{
							idReserva = getNumCopiasFondo("SELECT id_reserva FROM Reservas WHERE NSocio = "+idSocio+" AND id_libro = "+rs.getInt(2)+" ORDER BY -id_reserva");
							LlamadaDB("UPDATE Reservas SET Puede = TRUE, Fecha_puede = CURRENT_DATE() WHERE id_reserva = "+idReserva);
						}
				}
				else
				{
					idSocio = getNumCopiasFondo("SELECT Nsocio FROM Reservas WHERE id_revista = "+rs.getInt(3));
					if(idSocio == 0)
					{
						reservado = getNumCopiasFondo("SELECT id_reserva FROM Reservas WHERE id_revista = "+rs.getInt(3)+" AND Puede = FALSE ORDER BY -id_reserva");
						if(reservado == 0)LlamadaDB("UPDATE FROM Revistas SET CopiasPrestamo = CopiasPrestamo + 1, PrestadasPrestamo = PrestadasPrestamo - 1 WHERE id = "+rs.getInt(3)+")");
						else
						{
							LlamadaDB("UPDATE Reservas SET Puede = TRUE, Fecha_puede = CURRENT_DATE() WHERE id_reserva = "+reservado);	
						}
					}
					else 
					{
						idReserva = getNumCopiasFondo("SELECT id_reserva FROM Reservas WHERE NSocio = "+idSocio+" AND id_revista = "+rs.getInt(3)+" ORDER BY -id_reserva");
						LlamadaDB("UPDATE Reservas SET Puede = TRUE, Fecha_puede = CURRENT_DATE() WHERE id_reserva = "+idReserva);
					}
				}

			}

		}
		catch (SQLException ex)
		{
			
		}

	}
	/** Función que muestra al usuario si tiene o no reservas y libros por devolver */
	public static JPanel comprobarReservasLector(String id)
	{	JPanel auxiliar = new JPanel(); 
		try
		{	
			Object[] Opciones = {"Si","No"};
			int opcionN = -1;
			//System.out.println("ssss");
			String hazPrestamo = null;
			String Nivel = null;
			String fecha = null;
			int idFondo = 0;
			int cogidos = 0;
			boolean corto = false;
			MiMysql m = new MiMysql();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT Libros.titulo,Reservas.id_reserva,Reservas.id_libro FROM Reservas join Libros on Libros.id = Reservas.id_libro WHERE Puede = TRUE AND Nsocio = "+id);
			Nivel = m.getCadena("Select Rol from Usuarios where Nsocio = "+id);
			int tieneparacoger = m.getNumCopiasFondo("SELECT id_reserva FROM Reservas WHERE Puede = TRUE AND Nsocio = "+id);
			cogidos = m.getNumCopiasFondo("SELECT * FROM Prestamos where Nsocio = "+id+" AND Devuelto = FALSE AND Fecha_inicio != Fecha_fin");
			int caducados = m.getNumCopiasFondo("SELECT * FROM Prestamos WHERE Nsocio = "+id+" AND DEVUELTO = FALSE AND Fecha_fin > CURRENT_DATE()");
			//System.out.println(tieneparacoger);
			//System.out.println("HOlA");
			// Si el lector socio tiene 12, no puede coger más, o si el lector básico tiene 6, tampoco.
			if(tieneparacoger > 0)
			{
				if(((Nivel.compareTo("Lector-Socio")==0) && (cogidos ==12)) || 
						((Nivel.compareTo("Lector-Basico")==0) && (cogidos ==6))) JOptionPane.showMessageDialog(auxiliar, "Ya tiene "+cogidos+" préstamos.\n Si alguna de sus reservas está disponible, no podrá verla hasta la devolución de los libros", "Fondos a devolver", JOptionPane.INFORMATION_MESSAGE);
				else if(caducados > 0)JOptionPane.showMessageDialog(auxiliar, "Tiene libros o revistas a devolver.\n Si alguna de sus reservas está disponible, no podrá verla hasta la devolución de los libros", "Fondos a devolver", JOptionPane.INFORMATION_MESSAGE);
				else{
					while((rs.next()) && (((Nivel.compareTo("Lector-Socio")==0) && (cogidos <12)) || 
							((Nivel.compareTo("Lector-Basico")==0) && (cogidos <6))))
					{
						idFondo = m.getNumCopiasFondo("SELECT id from Libros where id = "+rs.getString(3));
						if (m.getNumCopiasFondo("SELECT * FROM Libros where id = "+idFondo+" AND CortoPlazo = TRUE")!=0) corto = true;
						opcionN = JOptionPane.showOptionDialog(auxiliar,"Ya puede realizar el prestamo de un libro reservado.\n¿Desea recoger el libro "+rs.getString(1)+"?" ,"Atención",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,Opciones,Opciones[1]);
						// Si acepta...
						if (opcionN == 0)
						{
							hazPrestamo = prestaFondo +
							  idFondo	+ "," +
							  "null," +
							  id   		+ "," +
							  "TRUE," +"CURRENT_DATE(),";
							String envioDatos = id 					+ ",2," +
												"'Prestamo fondo para socio: " + id + "','" +
												"El usuario número " +id + " con nivel de " + Nivel + 
												"\nha solicitado el préstamo para libro con ID " + idFondo;
							if (corto) 
							{
								hazPrestamo += "DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY),FALSE);";
								envioDatos += "',DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY),TRUE);";
								fecha = m.getCadena("SELECT date_add(current_date(),INTERVAL 2 day);");
							}
							else 
								{
									hazPrestamo += "DATE_ADD(CURRENT_DATE(), INTERVAL 21 DAY),FALSE);";
									envioDatos += "',DATE_ADD(CURRENT_DATE(), INTERVAL 21 DAY),TRUE);";
									fecha = m.getCadena("SELECT date_add(current_date(),INTERVAL 21 day);");
								}
							
							//System.out.println(hazPrestamo);
							m.LlamadaDB(hazPrestamo);
							m.LlamadaDB("INSERT INTO Buzon (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,"
									+ envioDatos);
							m.LlamadaDB("INSERT INTO Buzon2 (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,"
									+ envioDatos);
							//m.LlamadaDB("UPDATE Libros SET CopiasPrestamo = CopiasPrestamo-1 , PrestadasPrestamo = PrestadasPrestamo+1 WHERE id = "+idFondo+";");
							RolSuperior actualizandoTabla = new RolSuperior();
							if(m.getNumCopiasFondo("SELECT id FROM Revistas WHERE VolumenCreado = TRUE AND VolumenReal = '"+rs.getString(1)+"'")!=0) m.LlamadaDB("UPDATE Revistas SET CopiasPrestamo = 0 , PrestadasPrestamo = 1 WHERE VolumenReal = '"+rs.getString(1)+"'");
					    	m.LlamadaDB("DELETE FROM Reservas WHERE id_reserva = "+rs.getInt(2));
							JOptionPane.showMessageDialog(auxiliar, "El préstamo se ha realizado con éxito.\nDebe devolver el libro con título "+rs.getString(1)+" y con id "+id+" antes de que finalice el día "+fecha, "Prestamo exitoso", JOptionPane.INFORMATION_MESSAGE);
					    	cogidos++;
						}
					}
					
					if(Nivel.compareTo("Lector-Socio")==0) 
						{
							rs = s.executeQuery("SELECT Revistas.titulo,Reservas.id_reserva,Reservas.id_revista FROM Reservas join Revistas on Revistas.id = Reservas.id_revista WHERE Puede = TRUE AND Nsocio = "+id);
							while((rs.next()) && (cogidos <12))
							{
								idFondo = m.getNumCopiasFondo("SELECT id from Libros where id = "+rs.getString(3));
								if (m.getNumCopiasFondo("SELECT * FROM Revistas where id = "+idFondo+" AND CortoPlazo = TRUE")!=0) corto = true;
								opcionN = JOptionPane.showOptionDialog(auxiliar,"Ya puede realizar el prestamo de una revista reservada.\n¿Desea recoger la revista "+rs.getString(1)+"?" ,"Atención",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,Opciones,Opciones[1]);
								if (opcionN == 0)
								{
									hazPrestamo = prestaFondo +
									  "null," +
									  idFondo+"," +
									  id   		+ "," +
									  "FALSE," +"CURRENT_DATE(),";
									String envioDatos = id 					+ ",2," +
														"'Prestamo fondo para socio: " + id + "','" +
														"El usuario nñumero " +id + " con nivel de " + Nivel + 
														"\nha solicitado el préstamo para revista con ID " + idFondo;
									if (corto) 
									{
										hazPrestamo += "DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY),FALSE);";
										envioDatos += "',DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY),TRUE);";
										fecha = m.getCadena("SELECT date_add(current_date(),INTERVAL 2 day);");
									}
									else 
										{
											hazPrestamo += "DATE_ADD(CURRENT_DATE(), INTERVAL 21 DAY),FALSE);";
											envioDatos += "',DATE_ADD(CURRENT_DATE(), INTERVAL 21 DAY),TRUE);";
											fecha = m.getCadena("SELECT date_add(current_date(),INTERVAL 21 day);");
										}
									
									//System.out.println(hazPrestamo);
									m.LlamadaDB(hazPrestamo);
									m.LlamadaDB("INSERT INTO Buzon (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,"
											+ envioDatos);
									m.LlamadaDB("INSERT INTO Buzon2 (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,"
											+ envioDatos);
									//m.LlamadaDB("UPDATE Revistas SET CopiasPrestamo = CopiasPrestamo-1 , PrestadasPrestamo = PrestadasPrestamo+1 WHERE id = "+idFondo+";");
							    	m.LlamadaDB("DELETE FROM Reservas WHERE id_reserva = "+rs.getInt(2));
									JOptionPane.showMessageDialog(auxiliar, "El préstamo se ha realizado con éxito.\nDebe devolver el libro con título "+rs.getString(1)+" y con id "+id+" antes de que finalice el día "+fecha, "Prestamo exitoso", JOptionPane.INFORMATION_MESSAGE);
							    	cogidos++;
								}
							}
						}
					
				}
				
			}
	
		

		}
		catch (SQLException ex)
		{
			System.out.println(ex);
		}
		return auxiliar;
	}
    
}