import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

public class VentanaConsultarFondo extends JInternalFrame
{
    static final int xOffset = 30, yOffset = 30;
    static String NivelUsuario;
    static String Socio;
    final static String prestaFondo = "INSERT INTO Prestamos (Devuelto,AvisoEnviado,id_libro,id_revista,NSocio,Eslibro,Fecha_inicio,Fecha_fin,Consulta) VALUES (FALSE,FALSE,";
    final static String reservarFondo = "INSERT INTO Reservas (Puede,id_libro,id_revista,NSocio,Eslibro,Fecha_reserva) VALUES (FALSE,";
	public VentanaConsultarFondo(String nivel,String NSocio) 
	{

		super("Consultar Fondo Bibliografico", 
		      true, //resizable
		      true, //closable
		      false, //maximizable
		      true);//iconifiable
		NivelUsuario = nivel;
		Socio = NSocio;
			
		ConsultorioFondo consultorio = new ConsultorioFondo();
		
		add(consultorio);
		
		pack();
		setVisible(true);
	
		if((NivelUsuario.compareTo("Administrador") == 0) || (NivelUsuario.compareTo("Tecnico") == 0)) setSize(950,650);
		else setSize(920,650);
		

		setLocation(xOffset, yOffset);
	}
	
	
	class ConsultorioFondo extends JPanel implements ActionListener
	{

		/** Declaración de variables */
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String BUSCAR 			= "Buscar";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String PRESTAMO 			= "Prestamo";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String CONSULTA 			= "Consulta";
		
		/** Texto que muestra labelNombre */
	    protected static final String stringBusqueda	= "Busqueda: ";

		/** Texto Modificar para el boton Modificar */
		protected static final String MODIFICAR 		= "Modificar";
		
		/** Texto Borrar para el boton Borrar */
		protected static final String BORRAR = "Borrar";
		
		/** Texto Volumen para el boton Volumen */
		protected static final String VOLUMEN 		= "Prestamo ";
		protected static final String VOLUMEN2 		= "Sala";
		protected static final String insertRevista = "INSERT INTO Revistas (PrestadasSala,PrestadasPrestamo,VolumenCreado,Titulo,Autores,Editorial,Volumen,VolumenReal,Numero,Anio,Paginas,Signatura,CopiasSala,CopiasPrestamo,CortoPlazo)"
														+ " VALUES (0,0,TRUE,'";
		protected static final String insertLibro =  "INSERT INTO Libros (PrestadasSala,PrestadasPrestamo,Titulo,Autores,Editorial,Anio,Paginas,Signatura,CopiasSala,CopiasPrestamo,CortoPlazo) VALUES (0,0,'";

		int finalColumna;
		String auxiliarCrit;
		String auxiliarBusq;
		boolean auxEsLibro;
	    
		/** Campo de texto para introducir el Nombre */
		JTextField textBusqueda;
		
		/** Campo de texto para introducir el Nombre */
		JTextField textVolumen;
		
		
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
		
		
		/** Todos los criterios de búsqueda */
		String[] criterios ={"Titulo","Autores","Editorial","Edicion","Anio","Paginas","Signatura","ISBN","Todos"};
		String[] criterios2 ={"Titulo","Autores","Editorial","Volumen","Anio","Paginas","Numero","Signatura","Todos"};
		String[] cortoPlazoAnterior;
		String[] datosAModificar1 ={"Titulo","Autores","Editorial","Volumen","VolumenReal","Numero","Anio","Paginas","Signatura","CopiasSala","CopiasPrestamo","CortoPlazo"};
		
		String[] datosAModificar2 ={"Titulo","Autores","Editorial","Edicion","Anio","Paginas","Signatura","ISBN","CopiasSala","CopiasPrestamo","CortoPlazo"};


		/** Caja que contiene los criterios */
		JComboBox cajaCriterios = new JComboBox(criterios);
		/** Radio Buttons para elegir a la hora de hacer búsquedas.*/
		JRadioButton botonLibros   = new JRadioButton("Libros"  , true);
		JRadioButton botonRevistas    = new JRadioButton("Revistas"   , false);
		JRadioButton botonPrestados   = new JRadioButton("Prestados"  , false);
		JRadioButton botonTodos    = new JRadioButton("Todos"   , true);

		JRadioButton botonCPlazo  = new JRadioButton("Corto Plazo"  , false);
		JRadioButton botonLPlazo    = new JRadioButton("Largo Plazo"   , true);
		
		JButton volumenButton;
		JButton volumen2Button;
		boolean buscasteLibros = false;
		public ConsultorioFondo() 
		{	

			setLayout(new BorderLayout());
			
			/* Aquí creo los 2 paneles con los checkbuttons, tanto para elegir
			 * si queremos buscar libros o revistas, como para elegir si queremos
			 * ver prestados o todos.
			 */

			ButtonGroup bgroupLoR = new ButtonGroup();
			bgroupLoR.add(botonLibros);
			botonLibros.addActionListener(this);
			botonLibros.setActionCommand("LIBROS");
			bgroupLoR.add(botonRevistas);
			botonRevistas.addActionListener(this);
			botonRevistas.setActionCommand("REVISTAS");
			
			JPanel radioPanel = new JPanel();
			radioPanel.setLayout(new GridLayout(1, 2));
			if((NivelUsuario.compareTo("Lector-Socio") == 0) || (NivelUsuario.compareTo("Tecnico") == 0)
			|| (NivelUsuario.compareTo("Administrador") == 0) )
					{
						radioPanel.add(botonLibros);
						radioPanel.add(botonRevistas);
					}

			
			JPanel radio2Panel = new JPanel();
			if((NivelUsuario.compareTo("Tecnico") == 0)
					|| (NivelUsuario.compareTo("Administrador") == 0)) 
			{
				ButtonGroup bgroupPoT = new ButtonGroup();
				bgroupPoT.add(botonPrestados);
				bgroupPoT.add(botonTodos);
				radioPanel.add(botonPrestados);
				radioPanel.add(botonTodos);
				
				ButtonGroup bgroupCoL = new ButtonGroup();
				bgroupCoL.add(botonCPlazo);
				bgroupCoL.add(botonLPlazo);
				radio2Panel.add(botonCPlazo);
				radio2Panel.add(botonLPlazo);
				
			}

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
			resultadosPanel = new JPanel(new GridBagLayout());
			modelo = new DefaultTableModel();
			tblResultados = new JTable(modelo);
			tblResultados.setGridColor(Color.BLACK);

			//tblResultados.showHorizontalLines(true);
			JScrollPane scroll = new JScrollPane(tblResultados);
			scroll.setPreferredSize(new Dimension (875, 250));
			//tblResultados.setPreferredSize(new Dimension (875, 200);

			
			modelo.addColumn("ID");
			modelo.addColumn("Titulo");
			modelo.addColumn("Autores");
			modelo.addColumn("Editorial");
			modelo.addColumn("Edicion");
			modelo.addColumn("Anio");
			modelo.addColumn("Paginas");
			modelo.addColumn("Signatura");
			modelo.addColumn("ISBN");
			modelo.addColumn("Disp. Sala");
			modelo.addColumn("Disp. Prestamo");
			/*modelo.addColumn("Prestados Sala");
			modelo.addColumn("Prestados Prestamo");*/
			
			
			setPreferredColumnWidths();
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
		    c.anchor = GridBagConstraints.EAST;
		    c.fill = GridBagConstraints.HORIZONTAL;
		    c.ipady = 280;
		    // c.weighty = 10.0;
		    //c.weighty = 200;
		    //c.gridheight = 200;
			resultadosPanel.add(scroll,c);
			resultadosPanel.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder(""),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
			
		    //##########################################################//
		    //##########################################################//
			
			JPanel botonesAccion = new JPanel();
    		// Creo el botón de Modificar.
    		ImageIcon iconoModificar 	= createImageIcon("images/iconoActualizar.png");
    		ImageIcon iconoBorrar		= createImageIcon("images/001_05.png");
    		JButton modificarButton = new JButton(MODIFICAR,iconoModificar);
    		JButton borrarButton = new JButton(BORRAR,iconoBorrar);
    		
    		// Asigno un nombre para los botones
    		modificarButton.setActionCommand(MODIFICAR);
    		borrarButton.setActionCommand(BORRAR);

    		// Asigno la acción para los botones Aceptar y Cancelar.
    		modificarButton.addActionListener(this);
    		borrarButton.addActionListener(this);
    		
			botonesAccion.add(borrarButton,c);
			botonesAccion.add(modificarButton,c);
			botonesAccion.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder(""),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
			
			 //##########################################################//
		    //##########################################################//
			
			JPanel panelVolumenes = new JPanel();
			JPanel panelPVolumenes = new JPanel(new GridLayout(2,1));
    		// Creo el botón de Modificar.
    		ImageIcon iconoPrestamo 	= createImageIcon("images/001_20.png");
    		ImageIcon iconoSala		= createImageIcon("images/001_51.png");
    		volumenButton = new JButton(VOLUMEN,iconoPrestamo);
    		volumen2Button = new JButton(VOLUMEN2,iconoSala);

			textVolumen = new JTextField(50);
			JLabel labelVolumen = new JLabel("Nombre de Volumen:");
			labelVolumen.setLabelFor(textVolumen);
    		// Asigno un nombre para los botones
    		volumenButton.setActionCommand(VOLUMEN);
    		volumen2Button.setActionCommand(VOLUMEN2);
    		
    		// Asigno la acción para los botones Aceptar y Cancelar.
    		volumenButton.addActionListener(this);
    		volumen2Button.addActionListener(this);


    		panelVolumenes.add(labelVolumen,c);
    		panelVolumenes.add(textVolumen,c);
    		panelVolumenes.add(volumenButton,c);
    		panelVolumenes.add(volumen2Button,c);
    		panelPVolumenes.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder("Crear Volumenes"),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
    		panelPVolumenes.add(panelVolumenes);
    		panelPVolumenes.add(radio2Panel);
			
			
		    //##########################################################//
		    //##########################################################//
			
			JPanel botonesAccion2 = new JPanel();
    		// Creo el botón de Modificar.

    		JButton prestamoButton = new JButton(PRESTAMO,iconoPrestamo);
    		JButton consultaButton = new JButton(CONSULTA,iconoSala);

    		// Asigno un nombre para los botones
    		prestamoButton.setActionCommand(PRESTAMO);
    		consultaButton.setActionCommand(CONSULTA);

    		// Asigno la acción para los botones Aceptar y Cancelar.
    		prestamoButton.addActionListener(this);
    		consultaButton.addActionListener(this);
    		
			botonesAccion2.add(prestamoButton,c);
			botonesAccion2.add(consultaButton,c);
			botonesAccion2.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder(""),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
			
			
		    //##########################################################//
		    //##########################################################//
			//Agrupamos los distintos paneles creados (uno en este caso)

			leftPane = new JPanel(new BorderLayout());

			leftPane.add(textControlsPane, BorderLayout.PAGE_START);
			leftPane.add(resultadosPanel, BorderLayout.CENTER);
			if((NivelUsuario.compareTo("Tecnico") == 0) || (NivelUsuario.compareTo("Administrador") == 0)) 
				{	c.ipady = 15;
					//resultadosPanel.add(panelVolumenes, c);
					resultadosPanel.add(panelPVolumenes,c);
					leftPane.add(botonesAccion, BorderLayout.PAGE_END);
				}
			else if ((NivelUsuario.compareTo("Lector-Socio")==0) || (NivelUsuario.compareTo("Lector-Basico") == 0)) leftPane.add(botonesAccion2, BorderLayout.SOUTH);
			//leftPane.add(areaScrollPane, BorderLayout.CENTER);

			add(leftPane, BorderLayout.LINE_START);
			//add(resultadosPanel, BorderLayout.CENTER);
			//add(rightPane, BorderLayout.LINE_END);
		}//fin_FormularioInsertarUsuario()
		protected ImageIcon createImageIcon(String path) {
  	        java.net.URL imgURL = ModeloTablaBuzon.class.getResource(path);
  	        if (imgURL != null) {
  	            return new ImageIcon(imgURL);
  	        } else {
  	            System.err.println("Couldn't find file: " + path);
  	            return null;
  	        }
  	    }
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
				boolean esLibro = false;
				finalColumna = 13;
				cortoPlazoAnterior = null;
				String criterioElegido = String.valueOf(cajaCriterios.getSelectedItem());
				auxiliarCrit = criterioElegido;
				String busquedaRealizada = textBusqueda.getText();
				auxiliarBusq = busquedaRealizada;
				RolSuperior buscando = new RolSuperior();
				if (botonLibros.isSelected()) esLibro = true;
				if (!esLibro) finalColumna = 14;
				if (botonPrestados.isSelected()) busquedaRealizada += " AND PrestadasSala > 0 OR PrestadasPrestamo > 0";
				buscasteLibros = esLibro;
				if((NivelUsuario.compareTo("Tecnico") == 0) || (NivelUsuario.compareTo("Administrador") == 0))
				{	
					 tblResultados.setModel(buscando.getTablaBusquedaFondo(busquedaRealizada,criterioElegido,esLibro));
					 tblResultados.getModel().addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
						int[] seleccionadas = tblResultados.getSelectedRows();
						boolean lSi = (((String) tblResultados.getValueAt(seleccionadas[0],finalColumna)).compareTo("SI") == 0);
						boolean lNo = (((String) tblResultados.getValueAt(seleccionadas[0],finalColumna)).compareTo("NO") == 0);
							if (!lSi && !lNo) 
							{
								JOptionPane.showMessageDialog(leftPane, "Corto plazo equivocado.\nValores posibles:"
																+ "\n\t-SI" 
																+ "\n\t-NO", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
								tblResultados.setValueAt(cortoPlazoAnterior[seleccionadas[0]], seleccionadas[0], finalColumna);
							}				
			        }
			      });	
					 
					 cortoPlazoAnterior = new String[tblResultados.getRowCount()];
				    for(int k=0;k<tblResultados.getRowCount();k++)
				    {
				    	cortoPlazoAnterior[k] = tblResultados.getValueAt(k, finalColumna).toString();
				    }
				}
	    
					
				else tblResultados.setModel(buscando.getTablaBusquedaFondoLector(busquedaRealizada,criterioElegido,esLibro));
				auxEsLibro = esLibro;
	        	if (esLibro) 
	        		{
	        			volumenButton.setEnabled(false);
	        			volumen2Button.setEnabled(false);
	        		}
	        	else 
	        		{
	        			volumenButton.setEnabled(true);
	        			volumen2Button.setEnabled(true);
	        		}
	 
			}
			else if(MODIFICAR.equals(e.getActionCommand()))
			{ 
				int i=0,j=0;
				/* Almacenamos en resl todas las filas de la tabla */
				int resl = tblResultados.getRowCount();
				/* Creamos el objeto MiMysql que actualizar� la DB */
				MiMysql actualizador = new MiMysql();
				String modificaEnDB;
				/* Con estos 2 for anidados tratamos cada fila individualmente y actualizamos en la DB cada fila */
				//String[] datosAModificar1 ={"Titulo","Autores","Editorial","Volumen","Numero","Anio","Paginas","Signatura","CopiasSala","CopiasPrestamo","CortoPlazo"};
				//String[] datosAModificar2 ={"Titulo","Autores","Editorial","Edicion","Anio","Paginas","Signatura","ISBN","CopiasSala","CopiasPrestamo","CortoPlazo"};
	        	for(i=0;i<resl;i++)
	        	{	String corPlazo = "TRUE";
	        		if (!buscasteLibros) 
	        			{	if(tblResultados.getValueAt(i, 13).toString().compareTo("NO") == 0) corPlazo = "FALSE";
	        				modificaEnDB = "UPDATE Revistas SET ";
	        				for(j=0;j<11;j++)
	    	        		{	
	        					if((j == 5) || (j == 6) || (j == 7) || (j == 9) || (j == 10)) modificaEnDB = modificaEnDB + datosAModificar1[j] + " = " + tblResultados.getValueAt(i, j+1) + " , ";
	    	        			else  modificaEnDB = modificaEnDB + datosAModificar1[j] + " = '" + tblResultados.getValueAt(i, j+1) + "', ";
	    	        		}
	    	        			modificaEnDB = modificaEnDB + " " + datosAModificar1[11] + " = " + corPlazo
	    	        			              + " WHERE Id = " + tblResultados.getValueAt(i,0) + " ;" ; 
	        			}
	        		else 
	        		{	if(tblResultados.getValueAt(i, 12).toString().compareTo("NO") == 0) corPlazo = "FALSE";
	        			modificaEnDB = "UPDATE Libros SET ";
	        			for(j=0;j<10;j++)
		        		{	
		        			if((j == 4) || (j == 5) || (j == 7) || (j == 8)|| (j == 9) ) modificaEnDB = modificaEnDB + datosAModificar2[j] + " = " + tblResultados.getValueAt(i, j+1) + " , ";
		        			else  modificaEnDB = modificaEnDB + datosAModificar2[j] + " = '" + tblResultados.getValueAt(i, j+1) + "', ";
		        		}
		        			modificaEnDB = modificaEnDB + " " + datosAModificar2[10] + " = " + corPlazo
		        			              + " WHERE Id = " + tblResultados.getValueAt(i,0) + " ;" ; 
	        		}
	        		//System.out.println(modificaEnDB);
	        		actualizador.LlamadaDB(modificaEnDB);
	        	}
	        	

			}
			else if(BORRAR.equals(e.getActionCommand()))
			{	int i=0;
				int[] seleccionadas = tblResultados.getSelectedRows();
				MiMysql borrando = new MiMysql();
				String borrado;
				String borrado2;
				boolean estaPrestado = false;
				String queborro = "Revistas";
				if (auxEsLibro) queborro = "Libros";
				for(i=0;i<seleccionadas.length;i++)
				{ 	
					borrado = "DELETE FROM "+queborro+" WHERE id = " + tblResultados.getValueAt(seleccionadas[i], 0);
					borrado2 = "DELETE FROM Revistas WHERE VolumenReal = '"+borrando.getCadena("SELECT Titulo FROM Libros WHERE id = "+tblResultados.getValueAt(seleccionadas[i],0))+"'";
					estaPrestado = false;
					if(auxEsLibro)
					{
						if((tblResultados.getValueAt(seleccionadas[i], 11).toString().compareTo("0"))!=0) 
						{
							JOptionPane.showMessageDialog(textControlsPane, "No puede borrar este libro porque está en consulta.", "Error borrado", JOptionPane.ERROR_MESSAGE);
							estaPrestado = true;
						}
						else if((tblResultados.getValueAt(seleccionadas[i], 12).toString().compareTo("0"))!=0)  
						{
							JOptionPane.showMessageDialog(textControlsPane, "No puede borrar este libro porque está en préstamo.", "Error borrado", JOptionPane.ERROR_MESSAGE);					
							estaPrestado = true;
						}
					}
					else 
					{
						if((tblResultados.getValueAt(seleccionadas[i], 12).toString().compareTo("0"))!=0) 
						{
							JOptionPane.showMessageDialog(textControlsPane, "No puede borrar este libro porque está en consulta.", "Error borrado", JOptionPane.ERROR_MESSAGE);
							estaPrestado = true;
						}
						else if((tblResultados.getValueAt(seleccionadas[i], 13).toString().compareTo("0"))!=0)  
						{
							JOptionPane.showMessageDialog(textControlsPane, "No puede borrar este libro porque está en préstamo.", "Error borrado", JOptionPane.ERROR_MESSAGE);					
							estaPrestado = true;
						}
					}
					if(!estaPrestado)
						{
							//System.out.println(borrado);
							//System.out.println(borrado2);
							borrando.LlamadaDB(borrado);
							if(auxEsLibro) borrando.LlamadaDB(borrado2);
						}
				}
				
			}
			else if (PRESTAMO.equals(e.getActionCommand()))
			{ 
				MiMysql hacePrestamo = new MiMysql();
				int bandera = hacePrestamo.getNumCopiasFondo("SELECT * FROM Prestamos WHERE Fecha_fin < Current_date AND Devuelto = FALSE;");
				int totalCogidos = hacePrestamo.getNumPrestamosLector(Socio);
				//System.out.println(totalCogidos);
				if (((NivelUsuario.compareTo("Lector-Socio")==0) && totalCogidos ==12) 
								|| ((NivelUsuario.compareTo("Lector-Basico")==0) && totalCogidos ==6)) JOptionPane.showMessageDialog(textControlsPane, "No puedes tomar prestados más de "+totalCogidos+" prestamos", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
				else
				{
					if(bandera == 0) // Si no tiene prestamos caducados
					{
						int [] seleccionadas = tblResultados.getSelectedRows();
						int CopiasDisp = 0;
						int CopiasPrest = 0;
						Object[] Opciones = {"Si","No"};
						String idFondo = null;
						String idFondo2 = null;
						String idElegido;
						String hazPrestamo = null;
						String Fecha_inicio = null;
						int dondeEstaCortoPlazo = 11;
						String Fecha_fin = null;
						String formato = "Revistas";
						String nombreLibro = tblResultados.getValueAt(seleccionadas[0], 1).toString();
						String nombreVolumen = null;
						boolean corto = false;
						boolean EsLibro = false;
						boolean restaRevistasVolumen = false;
						int opcionN = -1;
						String titulo = "la revista";
						if (buscasteLibros) 
							{
								EsLibro = true;
								titulo = "el libro";
								formato = "Libros";
								idFondo = tblResultados.getValueAt(seleccionadas[0],0).toString();
								idElegido = idFondo;
								if(hacePrestamo.getNumCopiasFondo("SELECT id FROM Revistas WHERE VolumenCreado = TRUE AND VolumenReal = '"+nombreLibro+"'")!=0)
								{
									restaRevistasVolumen = true;
								}
							}
						else 
							{
								idFondo2 = tblResultados.getValueAt(seleccionadas[0],0).toString();
								idElegido = idFondo2;
								dondeEstaCortoPlazo = 12;
								if(tblResultados.getValueAt(seleccionadas[0], 5) != null) 
									{
										nombreVolumen = tblResultados.getValueAt(seleccionadas[0], 5).toString();
										if(hacePrestamo.getNumCopiasFondo("SELECT id FROM Revistas WHERE VolumenCreado = TRUE AND VolumenReal = '"+nombreVolumen+"'")!=0)
										{
											restaRevistasVolumen = true;
											EsLibro = true;
											formato = "Libros";
											titulo = "el libro";
											idElegido = Integer.toString(hacePrestamo.getNumCopiasFondo("SELECT id FROM Libros WHERE Titulo = '"+nombreVolumen+"'"));
											idFondo = idElegido;
											idFondo2 = null;
											nombreLibro = nombreVolumen;
											//System.out.println(idElegido);
										}
									}
								
							}
						if(seleccionadas.length != 0)
						{
							if(tblResultados.getValueAt(seleccionadas[0], dondeEstaCortoPlazo).toString().compareTo("SI") == 0) corto = true;
							CopiasDisp = hacePrestamo.getNumCopiasFondo("SELECT CopiasPrestamo FROM "+formato+" WHERE ID = "+idElegido);
							CopiasPrest = hacePrestamo.getNumCopiasFondo("SELECT PrestadasPrestamo FROM "+formato+" WHERE ID = "+idElegido);
							Fecha_inicio = hacePrestamo.dame_fecha("",false);
							if(corto)Fecha_fin = hacePrestamo.getCadena("SELECT DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY)");
							else Fecha_fin = hacePrestamo.getCadena("SELECT DATE_ADD(CURRENT_DATE(), INTERVAL 21 DAY)");
							if(CopiasDisp > 0)
							{
								CopiasDisp--;
								CopiasPrest++;
								hazPrestamo = prestaFondo 		+
											  idFondo 			+ "," +
											  idFondo2 			+ "," +
											  Socio   			+ "," +
											  EsLibro 			+ ",'" +
											  Fecha_inicio 		+ "','"+
											  Fecha_fin			+ "',FALSE);";
								String envioDatos = Socio 					+ ",2," +
													"'Prestamo fondo para socio: " + Socio + "','" +
													"El usuario número " + Socio + " con nivel de " + NivelUsuario + 
													"\nha solicitado el préstamo para " + titulo + " con ID " + idElegido + "','" +
													hacePrestamo.dame_fecha("",false) + "',TRUE);";
								//System.out.println(hazPrestamo);
								hacePrestamo.LlamadaDB(hazPrestamo);
								hacePrestamo.LlamadaDB("INSERT INTO Buzon (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,"
										+ envioDatos);
								hacePrestamo.LlamadaDB("INSERT INTO Buzon2 (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,"
										+ envioDatos);
								hacePrestamo.LlamadaDB("UPDATE "+formato+" SET CopiasPrestamo = "+CopiasDisp+" , PrestadasPrestamo = "+CopiasPrest+" WHERE id = "+idElegido+";");
								RolSuperior actualizandoTabla = new RolSuperior();
								if(restaRevistasVolumen)hacePrestamo.LlamadaDB("UPDATE Revistas SET CopiasPrestamo = 0 , PrestadasPrestamo = 1 WHERE VolumenReal = '"+nombreLibro+"'");
								JOptionPane.showMessageDialog(textControlsPane, "El préstamo se ha realizado con éxito.\nDebe devolver "+titulo+" con título "+hacePrestamo.getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" y con id "+idElegido+" antes de que finalice el día "+Fecha_fin, "Prestamo exitoso", JOptionPane.INFORMATION_MESSAGE);
								tblResultados.setModel(actualizandoTabla.getTablaBusquedaFondoLector(auxiliarBusq,auxiliarCrit,auxEsLibro));		
							}	
							else  
								{   int hayparareservar = 0;
									if(EsLibro) hayparareservar = hacePrestamo.getNumCopiasFondo("SELECT * FROM Prestamos WHERE id_libro = "+idElegido+" AND Fecha_inicio != Fecha_fin");
									else  hayparareservar = hacePrestamo.getNumCopiasFondo("SELECT * FROM Prestamos WHERE id_revista = "+idElegido+" AND Fecha_inicio != Fecha_fin");
									if(hayparareservar != 0)
									{
										opcionN = JOptionPane.showOptionDialog(textControlsPane,"No quedan libros de este tipo para su préstamo.\n¿Deseas realizar una reserva de este libro?","Atención",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,Opciones,Opciones[1]);
										if (opcionN == 0)
										{
											hazPrestamo = 		  reservarFondo 		+
											  					  idFondo 			+ "," +
																  idFondo2 			+ "," +
																  Socio   			+ "," +
																  EsLibro 			+ ",'" +
																  Fecha_inicio 		+ "');";
											hacePrestamo.LlamadaDB(hazPrestamo);
											//System.out.println(hazPrestamo);
									    	JOptionPane.showMessageDialog(textControlsPane,"La reserva se ha realizado correctamente.\nCuando "+titulo+" se encuentre disponible será informado.");
									    }
									}
									else JOptionPane.showMessageDialog(textControlsPane, "No hay fondos de este tipo para préstamo", "No hay unidades", JOptionPane.INFORMATION_MESSAGE);

								}
							
						}// FIN DE IF SELECCIONADAS.LENGTH == 0
					} // FIN DE IF BANDERA == 0
					else JOptionPane.showMessageDialog(textControlsPane, "Tienes libros/revistas a devolver", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
					
				}
				RolSuperior buscando = new RolSuperior();
				tblResultados.setModel(buscando.getTablaBusquedaFondoLector(auxiliarBusq,auxiliarCrit,buscasteLibros));
			
			}
			else if (CONSULTA.equals(e.getActionCommand()))
			{ 
				MiMysql hacePrestamo = new MiMysql();
				int bandera = hacePrestamo.getNumCopiasFondo("SELECT * FROM Prestamos WHERE Fecha_fin < Current_date AND Devuelto = FALSE;");
				int totalCogidos = hacePrestamo.getNumPrestamosLector(Socio);
				
				if(bandera == 0)
				{
					int [] seleccionadas = tblResultados.getSelectedRows();
					int CopiasDisp = 0;
					int CopiasPrest = 0;
					String idFondo = null;
					String idFondo2 = null;
					String idElegido;
					String hazPrestamo = null;
					String Fecha_inicio = null;
					String Fecha_fin = null;
					String formato = "Revistas";
					String deDonde = "CopiasSala";
					String aDonde = "PrestadasSala";
					String nombreLibro = tblResultados.getValueAt(seleccionadas[0], 1).toString();
					String nombreVolumen = tblResultados.getValueAt(seleccionadas[0], 5).toString();
					boolean restaRevistasVolumen = false;
					boolean EsLibro = false;
					String titulo = "la revista";
					if (buscasteLibros) 
					{
						EsLibro = true;
						titulo = "el libro";
						formato = "Libros";
						idFondo = tblResultados.getValueAt(seleccionadas[0],0).toString();
						idElegido = idFondo;
						if(hacePrestamo.getNumCopiasFondo("SELECT id FROM Revistas WHERE VolumenCreado = TRUE AND VolumenReal = '"+nombreLibro+"'")!=0)
						{
							restaRevistasVolumen = true;
						}
					}
				else 
					{
						idFondo2 = tblResultados.getValueAt(seleccionadas[0],0).toString();
						idElegido = idFondo2;
						if(hacePrestamo.getNumCopiasFondo("SELECT id FROM Revistas WHERE VolumenCreado = TRUE AND VolumenReal = '"+nombreVolumen+"'")!=0)
						{
							restaRevistasVolumen = true;
							EsLibro = true;
							formato = "Libros";
							titulo = "el libro";
							idElegido = Integer.toString(hacePrestamo.getNumCopiasFondo("SELECT id FROM Libros WHERE Titulo = '"+nombreVolumen+"'"));
							idFondo2 = null;
							idFondo = idElegido;
							nombreLibro = nombreVolumen;
						}
					}
					if(seleccionadas.length != 0)
					{
						
						CopiasDisp = hacePrestamo.getNumCopiasFondo("SELECT CopiasSala FROM "+formato+" WHERE ID = "+idElegido);
						CopiasPrest = hacePrestamo.getNumCopiasFondo("SELECT PrestadasSala FROM "+formato+" WHERE ID = "+idElegido);
						if(CopiasDisp == 0)
						{
							deDonde = "CopiasPrestamo";
							aDonde = "PrestadasPrestamo";
							CopiasDisp = hacePrestamo.getNumCopiasFondo("SELECT CopiasPrestamo FROM "+formato+" WHERE ID = "+idElegido);
							CopiasPrest = hacePrestamo.getNumCopiasFondo("SELECT PrestadasPrestamo FROM "+formato+" WHERE ID = "+idElegido); 
						}
						if(CopiasDisp > 0)
						{
							CopiasDisp--;
							CopiasPrest++;
							Fecha_inicio = hacePrestamo.dame_fecha("",false);
							Fecha_fin 	= Fecha_inicio;
							//Fecha_fin = hacePrestamo.dame_fecha(Fecha_inicio,corto);
							hazPrestamo = prestaFondo 		+
										  idFondo 			+ "," +
										  idFondo2 			+ "," +
										  Socio   			+ "," +
										  EsLibro 			+ ",'" +
										  Fecha_inicio 		+ "','"+
										  Fecha_fin			+ "'";
							if(aDonde.compareTo("PrestadasPrestamo")==0)hazPrestamo += ",FALSE);";
							else hazPrestamo += ",TRUE);";
							String envioDatos = Socio 					+ ",2," +
												"'Prestamo fondo para socio: " + Socio + "','" +
												"El usuario número " + Socio + " con nivel de " + NivelUsuario + 
												"\nha solicitado el préstamo para " + titulo + " con ID " + idElegido + "','" +
												hacePrestamo.dame_fecha("",false) + "',TRUE);";
							//System.out.println(hazPrestamo);
							hacePrestamo.LlamadaDB(hazPrestamo);
							hacePrestamo.LlamadaDB("INSERT INTO Buzon (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,"
									+ envioDatos);
							hacePrestamo.LlamadaDB("INSERT INTO Buzon2 (NoNuevo,id_remitente,id_receptor,Asunto,Mensaje,Fecha_Envio,ParaPrestamo) VALUES (FALSE,"
									+ envioDatos);
							hacePrestamo.LlamadaDB("UPDATE "+formato+" SET "+deDonde+"=" + CopiasDisp+" , "+aDonde+" = "+CopiasPrest+" WHERE id = "+idElegido+";");
							RolSuperior actualizandoTabla = new RolSuperior();
							tblResultados.setModel(actualizandoTabla.getTablaBusquedaFondoLector(auxiliarBusq,auxiliarCrit,auxEsLibro));		
							if(restaRevistasVolumen)hacePrestamo.LlamadaDB("UPDATE Revistas SET "+deDonde+" = 0 "+aDonde+" = 1 WHERE VolumenReal = '"+nombreLibro+"'");
							JOptionPane.showMessageDialog(textControlsPane, "El préstamo se ha realizado con éxito.\nDebe devolver "+titulo+" con titulo "+hacePrestamo.getCadena("SELECT titulo FROM "+formato+" WHERE ID = "+idElegido)+" y con id "+idElegido+" antes de que finalice el día "+Fecha_fin, "Prestamo exitoso", JOptionPane.INFORMATION_MESSAGE);
						}	
						else JOptionPane.showMessageDialog(textControlsPane, "No hay copias disponibles de ese libro", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
					}// FIN DE IF SELECCIONADAS.LENGTH == 0
				} // FIN DE IF BANDERA == 0
				else JOptionPane.showMessageDialog(textControlsPane, "Tienes libros/revistas a devolver", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
				
			}

			else if((VOLUMEN.equals(e.getActionCommand())) || (VOLUMEN2.equals(e.getActionCommand())))
			{	int i=0;
				int[] seleccionadas = tblResultados.getSelectedRows();
				MiMysql creando = new MiMysql();
				String dondeCrear = "CopiasPrestamo";
				String crearvolumen;
				String crearvolumen2;
				String insVolumen;
				String copSala = "0";
				String copPrestamo = "1";
				String cP = "FALSE";
				int totalPaginas = 0;
				boolean error = false;
				if(VOLUMEN2.equals(e.getActionCommand())) 
					{
						dondeCrear = "CopiasSala";
						copPrestamo = "0";
						copSala = "1";
					}
				
				while((i<seleccionadas.length) && (!error))
				{
					if(creando.getNumCopiasFondo("SELECT * FROM Revistas WHERE id = "+tblResultados.getValueAt(seleccionadas[i],0).toString()+" AND ("+dondeCrear+" = 0 OR VolumenCreado = TRUE);")!=0) error = true;
					i++;
				}

				if(!error)
				{
					if(textVolumen.getText().compareTo("")!=0)
					{		
						for(i=0;i<seleccionadas.length;i++)
						{ 	
							//textVolumen.getText()
							cP = "FALSE";
							if(botonCPlazo.isSelected()) cP = "TRUE";
							crearvolumen = "UPDATE Revistas SET "+dondeCrear+" = "+dondeCrear+"-1 WHERE id = " + tblResultados.getValueAt(seleccionadas[i], 0);
							String datosNuevos = tblResultados.getValueAt(seleccionadas[i], 1).toString() + "','" +
										  tblResultados.getValueAt(seleccionadas[i], 2).toString()+ "','" +
										  tblResultados.getValueAt(seleccionadas[i], 3).toString()+ "','" +
										  tblResultados.getValueAt(seleccionadas[i], 4).toString()+ "','" +
										  textVolumen.getText()+ "'," +
										  tblResultados.getValueAt(seleccionadas[i], 6).toString()+ "," +
										  tblResultados.getValueAt(seleccionadas[i], 7).toString()+ "," +
										  tblResultados.getValueAt(seleccionadas[i], 8).toString()+ ",'" +
										  tblResultados.getValueAt(seleccionadas[i], 9).toString()+ "'," +
										  copSala+ "," +copPrestamo+ "," +cP+");";
							totalPaginas = totalPaginas + Integer.parseInt(tblResultados.getValueAt(seleccionadas[i], 8).toString());
											
							crearvolumen2 = insertRevista + datosNuevos;
							//System.out.println(crearvolumen);
							//System.out.println(crearvolumen2);
							creando.LlamadaDB(crearvolumen);
							creando.LlamadaDB(crearvolumen2);
						}
						//creando.LlamadaDB();

						insVolumen = insertLibro + textVolumen.getText()+ "','" +
			 			 tblResultados.getValueAt(seleccionadas[0], 2).toString()+ "','" +
						 tblResultados.getValueAt(seleccionadas[0], 3).toString()+ "'," +
						 tblResultados.getValueAt(seleccionadas[0], 7).toString()+ "," +
						 totalPaginas + ",'"+ tblResultados.getValueAt(seleccionadas[0], 9).toString()+ "'," + copSala + "," +copPrestamo+ "," +cP+");";
						creando.LlamadaDB(insVolumen);
						//System.out.println(insVolumen);
						JOptionPane.showMessageDialog(textControlsPane, "El volumen se ha creado correctamente", "Prestamo exitoso", JOptionPane.INFORMATION_MESSAGE);
						//creando.LlamadaDB("DELETE FROM Revistas WHERE CopiasSala < 1");
						//creando.LlamadaDB("DELETE FROM Revistas WHERE CopiasPrestamo < 1");

					}
					else JOptionPane.showMessageDialog(resultadosPanel, "Debe elegir un nombre para el volumen", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
				}
				else JOptionPane.showMessageDialog(resultadosPanel, "Alguna de las revistas seleccionadas ya están en un volumen", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
				
			}
			else if ("LIBROS".equals(e.getActionCommand()))
			{	
				cajaCriterios.removeItemAt(3);
				cajaCriterios.insertItemAt("Edicion",3);
				cajaCriterios.removeItemAt(7);
				cajaCriterios.insertItemAt("ISBN",7);
			}
			else if ("REVISTAS".equals(e.getActionCommand()))
			{
				
				cajaCriterios.removeItemAt(3);
				cajaCriterios.insertItemAt("Volumen",3);
				cajaCriterios.removeItemAt(7);
				cajaCriterios.insertItemAt("Numero",7);

			}

			else 
			{ //quit
	        	    //System.exit(0);
			}

	    }
	   

	    public void setPreferredColumnWidths() 
	    { 
	    	TableColumn column = null;
	    	for (int i = 0; i < 8; i++) {
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