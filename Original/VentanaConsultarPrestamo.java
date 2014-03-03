import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

public class VentanaConsultarPrestamo extends JInternalFrame
{
    static final int xOffset = 30, yOffset = 30;
    static String NivelUsuario;
    static String Socio;
    
	public VentanaConsultarPrestamo(String nivel,String NSocio) 
	{

		super("Consultar Prestamos", 
		      true, //resizable
		      true, //closable
		      false, //maximizable
		      true);//iconifiable
		NivelUsuario = nivel;
		Socio = NSocio;
			//...Create the GUI and put it in the window...
		

		ConsultorioPrestamo consultorio = new ConsultorioPrestamo();

		
		add(consultorio);


		//Display the window.
			pack();
		setVisible(true);

		//...Then set the window size or call pack...
		setSize(920,550);
	

		//Set the window's location.
		setLocation(xOffset, yOffset);
	}
	
	
	class ConsultorioPrestamo extends JPanel implements ActionListener
	{

		/** Declaración de variables */
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String BUSCAR 			= "Buscar";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String CONSULTA 			= "Consulta";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String DEVUELTOS 		= "Devueltos";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String PENDIENTES		= "Pendientes";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String TODOS 			= "Todos";
		
		/** Texto que muestra labelNombre */
	    protected static final String stringBusqueda	= "Busqueda: ";

		/** Texto Modificar para el boton Modificar */
		protected static final String MODIFICAR 		= "Modificar";
		
		/** Texto Modificar para el boton Devolver */
		protected static final String DEVOLVER 		= "Devolver";
		
		/** Texto Borrar para el boton Borrar */
		protected static final String AMPLIAR = "Ampliar prestamo";
	    
		/** Campo de texto para introducir el Nombre */
		JTextField textBusqueda;
		
		/** Etiqueta Titulacion */
		JLabel labelBusqueda;
		
		/** Panel de control que contiene los botones */
		JComponent buttonPanel;
		
		/** Panel principal donde irá el cuadro de búsqueda y la tabla */
		JPanel leftPane;
		
		/** Panel donde irá la barra de búsqueda */
		JPanel textControlsPane;
		
		/** Panel donde irá la tabla de resultados */
		JPanel resultadosPanel;
		
		/** Tabla de resultados*/
		JTable tblResultados;
		
		//DefaultTableModel modelo;
		DefaultTableModel modelo;
		
		String[] devAnterior;
		/** Todos los criterios de búsqueda */
		
		String[] criterios = { "Todos","Revistas","Libros" };

		/** Caja que contiene los criterios */
		JComboBox cajaCriterios = new JComboBox(criterios);
		
		JRadioButton botonID  			= new JRadioButton("Id Usuario"  , false);
		JRadioButton botonTodos    		= new JRadioButton("Todos"   , true);
		JRadioButton botonADevolver   	= new JRadioButton("A devolver"  , false);
		
		String busqueda = null;
		String criterio = null;
		String busqTotal = null;
		
		public ConsultorioPrestamo() 
		{	
			busqueda = " id_prestamo  > 0";
			setLayout(new BorderLayout());
			
			/* Aquí creo los 2 paneles con los checkbuttons, tanto para elegir
			 * si queremos buscar libros o revistas, como para elegir si queremos
			 * ver prestados o todos.
			 */

			ButtonGroup bgroupLoR = new ButtonGroup();
			bgroupLoR.add(botonID);
			botonID.addActionListener(this);
			botonID.setActionCommand("ID");
			bgroupLoR.add(botonTodos);
			botonTodos.addActionListener(this);
			botonTodos.setActionCommand("TODOS");
			bgroupLoR.add(botonADevolver);
			botonADevolver.addActionListener(this);
			botonADevolver.setActionCommand("DEVOLVER");
			
			JPanel radioPanel = new JPanel();
			radioPanel.setLayout(new GridLayout(1, 3));
			radioPanel.add(botonTodos);
			radioPanel.add(botonID);
			radioPanel.add(botonADevolver);
			
			radioPanel.setBorder(BorderFactory.createTitledBorder(
			           BorderFactory.createEtchedBorder(), ""));
			
			/* Creación de lo campos de texto */

			//Campo de texto de Búsqueda
			textBusqueda = new JTextField(50);
			textBusqueda.setActionCommand(stringBusqueda);
			textBusqueda.addActionListener(this);
			
			/* Creación de las etiquetas... */

			//Etiqueta para Usuario
			labelBusqueda = new JLabel(stringBusqueda);
	        labelBusqueda.setLabelFor(textBusqueda);
	        
		    /* Creamos un layout propio para los controles anteriores */
			textControlsPane = new JPanel();
	        GridBagLayout gridbag = new GridBagLayout();
		    GridBagConstraints c = new GridBagConstraints();
			//Propiedades de la rejilla
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
		    c.anchor = GridBagConstraints.EAST;
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 100.0;
			
			//Establecemos la rejilla
			textControlsPane.setLayout(gridbag);

			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...
			JLabel[] labels = {labelBusqueda};
		    JTextField[] textFields = {textBusqueda};
			addLabelTextRows(labels, textFields, gridbag, textControlsPane);
			textControlsPane.add(cajaCriterios, c);
			textControlsPane.add(radioPanel,c);

			c.fill = GridBagConstraints.NONE;
			//Situados los demás elementos, colocamos la etiqueta informativa
			buttonPanel =  createButtonPanel();
	        textControlsPane.add(buttonPanel, c);
		    textControlsPane.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Búsqueda"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));  

		    //##########################################################//
		    //##########################################################//
		    
			/* Creamos el panel donde meteremos la tabla de la consulta*/
			resultadosPanel = new JPanel();
			modelo = new DefaultTableModel();
			tblResultados = new JTable(modelo);
			tblResultados.setGridColor(Color.BLACK);

			//tblResultados.showHorizontalLines(true);
			JScrollPane scroll = new JScrollPane(tblResultados);
			scroll.setPreferredSize(new Dimension (875, 270));
			//tblResultados.setPreferredSize(new Dimension (200, 800));

			modelo.addColumn("ID Prestamo");
			modelo.addColumn("ID Libro");
			modelo.addColumn("ID Revista");
			modelo.addColumn("N Socio");
			modelo.addColumn("Fecha inicio");
			modelo.addColumn("Fecha fin");
			modelo.addColumn("Devuelto");

			/*modelo.addColumn("Prestados Sala");
			modelo.addColumn("Prestados Prestamo");*/
			
			
			setPreferredColumnWidths();
			
			resultadosPanel.add(scroll,c);
			resultadosPanel.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder("Resultados"),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
			
		    //##########################################################//
		    //##########################################################//
			
			JPanel botonesAccion = new JPanel();
    		// Creo el botón de Modificar.
			ImageIcon iconoAmpliar = createImageIcon("images/001_03.png");
    		JButton ampliarButton = new JButton(AMPLIAR,iconoAmpliar);
    		
    		
    		// Asigno un nombre para los botones
    		ampliarButton.setActionCommand(AMPLIAR);

    		// Asigno la acción para los botones Aceptar y Cancelar.
    		ampliarButton.addActionListener(this);
    		
			botonesAccion.add(ampliarButton,c);
			botonesAccion.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder(""),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
			
		
			
		    //##########################################################//
		    //##########################################################//
			
			JPanel botonesAccion1 = new JPanel();
    		// Creo el botón de Modificar.
      		ImageIcon iconoDevueltos = createImageIcon("images/001_52.png");
      		ImageIcon iconoPendientes = createImageIcon("images/001_51.png");
      		ImageIcon iconoTodos = createImageIcon("images/001_40.png");
    		JButton devueltosButton = new JButton(DEVUELTOS,iconoDevueltos);
    		JButton pendientesButton = new JButton(PENDIENTES,iconoPendientes);
    		JButton todosButton = new JButton(TODOS,iconoTodos);
    		
    		
    		
    		// Asigno un nombre para los botones
    		devueltosButton.setActionCommand(DEVUELTOS);
    		pendientesButton.setActionCommand(PENDIENTES);
    		todosButton.setActionCommand(TODOS);

    		// Asigno la acción para los botones Aceptar y Cancelar.
    		devueltosButton.addActionListener(this);
    		pendientesButton.addActionListener(this);
    		todosButton.addActionListener(this);
    		
			botonesAccion1.add(devueltosButton,c);
			botonesAccion1.add(pendientesButton,c);
			botonesAccion1.add(todosButton,c);
			botonesAccion1.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder("Prestamos"),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
			
			//##########################################################//
		    //##########################################################//
			
			JPanel botonesAccion2 = new JPanel();
    		// Creo el botón de Modificar.
			ImageIcon iconoModificar = createImageIcon("images/iconoActualizar.png");
			JButton modificarBoton = new JButton(MODIFICAR,iconoModificar);
			modificarBoton.setActionCommand(MODIFICAR);
			modificarBoton.addActionListener(this);
			
			JButton devolverBoton = new JButton(DEVOLVER,iconoPendientes);
			devolverBoton.setActionCommand(DEVOLVER);
			devolverBoton.addActionListener(this);
    		
			botonesAccion2.add(modificarBoton,c);
			botonesAccion2.add(devolverBoton,c);
			botonesAccion2.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder(""),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
			
		    //##########################################################//
		    //##########################################################//
			//Agrupamos los distintos paneles creados (uno en este caso)

			leftPane = new JPanel(new BorderLayout());

			
			MiMysql prestamosLector = new MiMysql();
			
			if((NivelUsuario.compareTo("Tecnico") == 0) || (NivelUsuario.compareTo("Administrador") == 0)) 
				{
					leftPane.add(textControlsPane, BorderLayout.PAGE_START);

					leftPane.add(resultadosPanel, BorderLayout.CENTER);
					leftPane.add(botonesAccion2,BorderLayout.SOUTH);
				}
		
			if((NivelUsuario.compareTo("Tecnico") != 0) && (NivelUsuario.compareTo("Administrador") != 0)) 
				{
					leftPane.add(botonesAccion1, BorderLayout.NORTH);
					leftPane.add(resultadosPanel, BorderLayout.CENTER);
					tblResultados.setModel(prestamosLector.ConsultaPrestamoLector("NSocio = "+Socio));
					tblResultados.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					leftPane.add(botonesAccion, BorderLayout.SOUTH);
				}
			
			//leftPane.add(areaScrollPane, BorderLayout.CENTER);

			add(leftPane, BorderLayout.LINE_START);
			//add(resultadosPanel, BorderLayout.CENTER);
			//add(rightPane, BorderLayout.LINE_END);
		}//fin_FormularioInsertarUsuario()
		private void addLabelTextRows(JLabel[] labels,
				  JTextField[] textFields,
				  GridBagLayout gridbag,
				  Container container) 
		{
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;
		
			for (int i = 0; i < numLabels; i++) 
			{
				c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
				c.fill = GridBagConstraints.NONE;      //reset to default
				c.weightx = 0.0;                       //reset to default
				container.add(labels[i], c);
			
				c.gridwidth = GridBagConstraints.REMAINDER;     //end row
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 1.0;
				container.add(textFields[i], c);
			}//fin_del_for
		
		}//fin_addLabelTextRows
		
   		protected JComponent createButtonPanel() 
   		{
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
    		JPanel p = new JPanel(new GridLayout(1,0));

    		// Creo los 2 botones.
       		ImageIcon iconoBuscar = createImageIcon("images/001_38.png");
    		JButton busquedaButton = new JButton(BUSCAR,iconoBuscar);

    		// Asigno un nombre para los botones
    		busquedaButton.setActionCommand(BUSCAR);

    		// Asigno la acción para los botones Aceptar y Cancelar.
    		busquedaButton.addActionListener(this);

    		p.add(busquedaButton);

    		return p;
		}// Fin createButtonPanel
   		
	    public void actionPerformed(ActionEvent e) 
	    {
	    	
			if (BUSCAR.equals(e.getActionCommand()))
			{
			   MiMysql buscador = new MiMysql();
			   String criterioElegido = String.valueOf(cajaCriterios.getSelectedItem());
			   String crit = null;
			   if(criterioElegido.compareTo("Todos") == 0) crit = ";";
			   else if (criterioElegido.compareTo("Libros") == 0) crit = " AND id_revista = null;";
			   else crit = " AND id_libro = null;";
				   
			   if (textBusqueda.isEnabled()) busqueda = " id_prestamo  = "+textBusqueda.getText();
			   tblResultados.setModel(buscador.ConsultaPrestamo(busqueda+crit));
			   busqTotal = busqueda + crit;
			   tblResultados.getModel().addTableModelListener(new TableModelListener() {
				   	public void tableChanged(TableModelEvent e) 
				   	{
					int[] seleccionadas = tblResultados.getSelectedRows();
					boolean lSi = (((String) tblResultados.getValueAt(seleccionadas[0],6)).compareTo("SI") == 0);
					boolean lNo = (((String) tblResultados.getValueAt(seleccionadas[0],6)).compareTo("NO") == 0);
						if (!lSi && !lNo) 
						{
							JOptionPane.showMessageDialog(leftPane, "Valor equivocado.\nEstados posibles de 'Devuelto':\n-SI\n-NO", "Error modificar", JOptionPane.ERROR_MESSAGE);
							tblResultados.setValueAt(devAnterior[seleccionadas[0]], seleccionadas[0], 6);
						}
					}
		      });
			  devAnterior = new String[tblResultados.getRowCount()];
			  for(int i=0;i<tblResultados.getRowCount();i++)
			   {
				  devAnterior[i] = (String) tblResultados.getValueAt(i, 6);
			   }
			   
			}
			else if(MODIFICAR.equals(e.getActionCommand()))
			{ 
				int i=0,j=0;
				/* Almacenamos en resl todas las filas de la tabla */
				int resl = tblResultados.getRowCount();
				/* Creamos el objeto MiMysql que actualizará la DB */
				MiMysql actualizador = new MiMysql();
				String estado;
				String formato;
				String deDonde;
				String aDonde;
				String idElegido = null;
				String modificaEnDB = null;
				String modificaEnDB2 = null;
				String modificaEnDB3 = null;
				int CopiasDisp;
				int CopiasPrest;
				/* Con estos 2 for anidados tratamos cada fila individualmente y actualizamos en la DB cada fila */
	        	for(i=0;i<resl;i++)
	        	{	//System.out.println("1");
	        	
	        	
	        		if((tblResultados.getValueAt(i, 6).toString().compareTo("SI") == 0) && (tblResultados.getValueAt(i, 6).toString().compareTo(devAnterior[i]) != 0)) 
	        		{
	        			deDonde = "CopiasPrestamo";
						aDonde = "PrestadasPrestamo";
	        			formato = "Revistas";
	            		modificaEnDB = "UPDATE Prestamos SET Devuelto = TRUE, Fecha_devuelto = CURRENT_DATE() WHERE id_prestamo = "+tblResultados.getValueAt(i,0)+";";
	            		int reservado = 0;
			        	actualizador.LlamadaDB(modificaEnDB);
			        	if(tblResultados.getValueAt(i, 1)  != null) 
	            		{
	            			formato = "Libros";
	            			idElegido = tblResultados.getValueAt(i, 1).toString();
	            		}
	            		else idElegido = tblResultados.getValueAt(i, 2).toString();
			        	if(formato.compareTo("Revistas")==0) reservado = actualizador.getNumCopiasFondo("SELECT id_reserva FROM Reservas WHERE id_revista = "+idElegido+" ORDER BY -id_reserva;");
			        	else reservado =  actualizador.getNumCopiasFondo("SELECT id_reserva FROM Reservas WHERE Puede = FALSE AND id_libro = "+idElegido+" ORDER BY -id_reserva;");
			        	if(reservado == 0) // Si no hay reservas de este libro.
			        	{
			        		if(tblResultados.getValueAt(i, 7).toString().compareTo("SI") == 0)
		            		{
			        			deDonde = "CopiasSala";
								aDonde = "PrestadasSala";
		            		}
							CopiasDisp = actualizador.getNumCopiasFondo("SELECT "+deDonde+" FROM "+formato+" WHERE ID = "+idElegido+" ;");
							CopiasPrest = actualizador.getNumCopiasFondo("SELECT "+aDonde+" FROM "+formato+" WHERE ID = "+idElegido+" ;");
							CopiasDisp++;
							CopiasPrest--;
							modificaEnDB2 = "UPDATE "+formato+" SET "+aDonde+" = "+CopiasPrest+" , "+deDonde+" = "+CopiasDisp+" WHERE ID = "+ idElegido +" ;";
							
							modificaEnDB3 = "UPDATE Revistas SET "+aDonde+" = 0 ,"+deDonde+" = 1 WHERE VolumenReal = '"+actualizador.getCadena("Select Titulo FROM Libros WHERE id = "+idElegido)+"'";
				        	
				        	//System.out.println("MODIFICA 1 "+modificaEnDB);
				        	//System.out.println("MODIFICA 2 "+modificaEnDB2);

				        	actualizador.LlamadaDB(modificaEnDB2);
				        	actualizador.LlamadaDB(modificaEnDB3);

			        	}
			        	else 
			        	{
			        		actualizador.LlamadaDB("UPDATE Reservas SET Puede = TRUE, Fecha_puede = CURRENT_DATE() WHERE id_reserva = "+reservado);
			        	}
	            		
	        			
	        		}
	
	        		
	        		

	        	}
	        	//System.out.println(modificaEnDB);
	        	tblResultados.setModel(actualizador.ConsultaPrestamo(busqTotal));
				   tblResultados.getModel().addTableModelListener(new TableModelListener() {
					   	public void tableChanged(TableModelEvent e) 
					   	{
						int[] seleccionadas = tblResultados.getSelectedRows();
						boolean lSi = (((String) tblResultados.getValueAt(seleccionadas[0],6)).compareTo("SI") == 0);
						boolean lNo = (((String) tblResultados.getValueAt(seleccionadas[0],6)).compareTo("NO") == 0);
							if (!lSi && !lNo) 
							{
								JOptionPane.showMessageDialog(leftPane, "Valor equivocado.\nEstados posibles de 'Devuelto':\n-SI\n-No", "Error modificar", JOptionPane.ERROR_MESSAGE);
								tblResultados.setValueAt(devAnterior[seleccionadas[0]], seleccionadas[0], 6);
							}
						}
			      });
				  devAnterior = new String[tblResultados.getRowCount()];
				  for(int l=0;l<tblResultados.getRowCount();l++)
				   {
					  devAnterior[l] = (String) tblResultados.getValueAt(l, 6);
				   }
				   
			}
			else if(AMPLIAR.equals(e.getActionCommand()))
			{	
				int i=0;
				int[] seleccionadas = tblResultados.getSelectedRows();
				MiMysql consultando = new MiMysql();
				String reserva;
				String fondo;
				String actualiza;
				int fechaActual = 0;
				boolean consulta = tblResultados.getValueAt(seleccionadas[0], 4).toString().compareTo(tblResultados.getValueAt(seleccionadas[0], 3).toString()) == 0 ;
				//System.out.println(tblResultados.getValueAt(seleccionadas[0], 4) + "" + tblResultados.getValueAt(seleccionadas[0], 3) + consulta);
				boolean corto = consultando.dame_fecha("'"+tblResultados.getValueAt(seleccionadas[0], 3).toString()+"'", true).compareTo(tblResultados.getValueAt(seleccionadas[0], 4).toString())==0 ; 
				//System.out.println(corto);
				if(!consulta) // Si no es consulta....
				{
					fechaActual = consultando.resta_fecha("'"+tblResultados.getValueAt(seleccionadas[0], 4).toString()+"'",corto);
					//System.out.println(fechaActual);
					if(tblResultados.getValueAt(seleccionadas[0], 5).toString().compareTo("SI") == 0)
					{
						JOptionPane.showMessageDialog(leftPane, "El libro está devuelto, no se puede ampliar préstamo", "Error ampliar prestamo", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						if(consultando.getCadena("SELECT AvisoEnviado FROM Prestamos WHERE id_prestamo = "+tblResultados.getValueAt(seleccionadas[0], 0).toString()).compareTo("1")==0) // Si es verdad fechaActual.... te deja ampliar
						{
							if(tblResultados.getValueAt(seleccionadas[0], 1)==null)
							{
								fondo = " id_revista = " + tblResultados.getValueAt(seleccionadas[0], 2);
									
							}
							else fondo = " id_libro = " + tblResultados.getValueAt(seleccionadas[0], 1);
							reserva = "SELECT * FROM Reservas WHERE "+ fondo;
							i = consultando.getNumCopiasFondo(reserva);
							if(i==0)
							{
								if(corto) actualiza = "UPDATE Prestamos SET AvisoEnviado = FALSE, Fecha_fin = DATE_ADD(Fecha_fin, INTERVAL 2 DAY) WHERE id_prestamo = " + tblResultados.getValueAt(seleccionadas[0], 0);
								else actualiza = "UPDATE Prestamos SET AvisoEnviado = FALSE, Fecha_fin = DATE_ADD(Fecha_fin, INTERVAL 31 DAY) WHERE id_prestamo = " + tblResultados.getValueAt(seleccionadas[0], 0);
								consultando.LlamadaDB(actualiza);
								JOptionPane.showMessageDialog(leftPane, "Ampliación de libro realizada", "Ampliacián exitosa", JOptionPane.INFORMATION_MESSAGE);
							}
							else JOptionPane.showMessageDialog(leftPane, "El libro está reservado, no puedes ampliar el préstamo", "Error ampliar prestamo", JOptionPane.ERROR_MESSAGE);
					
						}
						else JOptionPane.showMessageDialog(leftPane, "No puedes ampliar el préstamo", "Error ampliar prestamo", JOptionPane.ERROR_MESSAGE);
					}
				}
				else JOptionPane.showMessageDialog(leftPane, "No puedes ampliar una consulta", "Error ampliar prestamo", JOptionPane.ERROR_MESSAGE);
				
			
			}
			else if(DEVOLVER.equals(e.getActionCommand()))
			{
				int i=0,j=0;
				/* Almacenamos en resl todas las filas de la tabla */
				int[] selected = tblResultados.getSelectedRows();
				/* Creamos el objeto MiMysql que actualizará la DB */
				MiMysql actualizador = new MiMysql();
				String estado;
				String formato;
				String deDonde;
				String aDonde;
				String idElegido = null;
				String modificaEnDB = null;
				String modificaEnDB2 = null;
				String modificaEnDB3 = null;
				int CopiasDisp;
				int CopiasPrest;
				/* Con estos 2 for anidados tratamos cada fila individualmente y actualizamos en la DB cada fila */
				
		        	for(i=0;i<selected.length;i++)
		        	{	
		        			
						if(tblResultados.getValueAt(selected[i], 6).toString().compareTo("NO") == 0) 
						{   //System.out.println(selected.length);
							deDonde = "CopiasPrestamo";
							aDonde = "PrestadasPrestamo";
		        			formato = "Revistas";
		            		modificaEnDB = "UPDATE Prestamos SET Devuelto = TRUE, Fecha_devuelto = CURRENT_DATE() WHERE id_prestamo = "+tblResultados.getValueAt(selected[i],0)+";";
		            		int reservado = 0;
				        	actualizador.LlamadaDB(modificaEnDB);
				        	if(tblResultados.getValueAt(selected[i], 1)  != null) 
		            		{
		            			formato = "Libros";
		            			idElegido = tblResultados.getValueAt(selected[i], 1).toString();
		            		}
		            		else idElegido = tblResultados.getValueAt(selected[i], 2).toString();
				        	if(formato.compareTo("Revistas")==0) reservado = actualizador.getNumCopiasFondo("SELECT id_reserva FROM Reservas WHERE id_revista = "+idElegido+" ORDER BY -id_reserva;");
				        	else reservado =  actualizador.getNumCopiasFondo("SELECT id_reserva FROM Reservas WHERE Puede = FALSE AND id_libro = "+idElegido+" ORDER BY -id_reserva;");
				        	if(reservado == 0) // Si no hay reservas de este libro.
				        	{
				        		if(tblResultados.getValueAt(selected[i], 7).toString().compareTo("SI") == 0)
			            		{
				        			deDonde = "CopiasSala";
									aDonde = "PrestadasSala";
			            		}
								CopiasDisp = actualizador.getNumCopiasFondo("SELECT "+deDonde+" FROM "+formato+" WHERE ID = "+idElegido+" ;");
								CopiasPrest = actualizador.getNumCopiasFondo("SELECT "+aDonde+" FROM "+formato+" WHERE ID = "+idElegido+" ;");
								CopiasDisp++;
								CopiasPrest--;
								modificaEnDB2 = "UPDATE "+formato+" SET "+aDonde+" = "+CopiasPrest+" , "+deDonde+" = "+CopiasDisp+" WHERE ID = "+ idElegido +" ;";
								
								modificaEnDB3 = "UPDATE Revistas SET "+aDonde+" = 0 ,"+deDonde+" = 1 WHERE VolumenReal = '"+actualizador.getCadena("Select Titulo FROM Libros WHERE id = "+idElegido)+"'";
					        	
					        	//System.out.println("MODIFICA 1 "+modificaEnDB);
					        	//System.out.println("MODIFICA 2 "+modificaEnDB2);
	
					        	actualizador.LlamadaDB(modificaEnDB2);
					        	actualizador.LlamadaDB(modificaEnDB3);
	
				        	}
				        	else 
				        	{
				        		actualizador.LlamadaDB("UPDATE Reservas SET Puede = TRUE, Fecha_puede = CURRENT_DATE() WHERE id_reserva = "+reservado);
				        	}
		            		
		        			
		        		}
		        	//System.out.println(modificaEnDB);

				}
		        tblResultados.setModel(actualizador.ConsultaPrestamo(busqTotal));
					  tblResultados.getModel().addTableModelListener(new TableModelListener() {
						   	public void tableChanged(TableModelEvent e) 
						   	{
							int[] seleccionadas = tblResultados.getSelectedRows();
							boolean lSi = (((String) tblResultados.getValueAt(seleccionadas[0],6)).compareTo("SI") == 0);
							boolean lNo = (((String) tblResultados.getValueAt(seleccionadas[0],6)).compareTo("NO") == 0);
								if (!lSi && !lNo) 
								{
									JOptionPane.showMessageDialog(leftPane, "Valor equivocado.\nEstados posibles de 'Devuelto':\n-SI\n-No", "Error modificar", JOptionPane.ERROR_MESSAGE);
									tblResultados.setValueAt(devAnterior[seleccionadas[0]], seleccionadas[0], 6);
								}
							}
				      });
					  devAnterior = new String[tblResultados.getRowCount()];
					  for(int l=0;l<tblResultados.getRowCount();l++)
					   {
						  devAnterior[l] = (String) tblResultados.getValueAt(l, 6);
					   }

	        }

			
			else if (CONSULTA.equals(e.getActionCommand()))
			{

			}
			else if ("TODOS".equals(e.getActionCommand()))
			{	
				textBusqueda.setEnabled(false);
				busqueda = " id_prestamo  > 0";
			}
			else if ("ID".equals(e.getActionCommand()))
			{
				textBusqueda.setEnabled(true);
				
			}
			else if ("DEVOLVER".equals(e.getActionCommand()))
			{
				textBusqueda.setEnabled(false);
				busqueda = " id_prestamo > 0 AND Devuelto = FALSE";
			}
			else if(DEVUELTOS.equals(e.getActionCommand()))
			{ 
				MiMysql prestamosLector = new MiMysql();
				tblResultados.setModel(prestamosLector.ConsultaPrestamoLector("Nsocio = "+Socio+" AND Devuelto = TRUE;"));
				tblResultados.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
			else if(TODOS.equals(e.getActionCommand()))
			{ 
				MiMysql prestamosLector = new MiMysql();
				tblResultados.setModel(prestamosLector.ConsultaPrestamoLector("Nsocio = "+Socio));
				tblResultados.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
			else if(PENDIENTES.equals(e.getActionCommand()))
			{ 
				MiMysql prestamosLector = new MiMysql();
				tblResultados.setModel(prestamosLector.ConsultaPrestamoLector("Nsocio = "+Socio+" AND Devuelto = FALSE;"));
				tblResultados.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}

	    }
		protected ImageIcon createImageIcon(String path) {
  	        java.net.URL imgURL = ModeloTablaBuzon.class.getResource(path);
  	        if (imgURL != null) {
  	            return new ImageIcon(imgURL);
  	        } else {
  	            System.err.println("Couldn't find file: " + path);
  	            return null;
  	        }
  	    }

	    public void setPreferredColumnWidths() 
	    { 
	    	TableColumn column = null;
	    	for (int i = 0; i < 7; i++) {
	    	    column = tblResultados.getColumnModel().getColumn(i);
	    	    
	    	    if (i == 2) {
	    	        column.setPreferredWidth(200); //third column is bigger
	    	    } else {
	    	    	column.setPreferredWidth(100);
	    	    }
	    	}
	    }
		
	}

}