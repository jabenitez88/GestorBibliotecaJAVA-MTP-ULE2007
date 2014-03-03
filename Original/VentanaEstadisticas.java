import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

public class VentanaEstadisticas extends JInternalFrame
{
    static final int xOffset = 30, yOffset = 30;
    static String NivelUsuario;
    static String Socio;
    
	public VentanaEstadisticas(String nivel,String NSocio) 
	{

		super("Estadisticas", 
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
		setSize(750,270);
	

		//Set the window's location.
		setLocation(xOffset, yOffset);
	}
	
	
	class ConsultorioPrestamo extends JPanel implements ActionListener
	{

		/** Declaración de variables */
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String CALCULAR 			= "Calcular";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String DIA 				= "Dia:";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String MES	 			= "Mes:";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String ANIO				= "Año:";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String TODOS 			= "Todos";
		
		/** Texto que muestra labelNombre */
	    protected static final String stringID	= "ID:";

		/** Texto Modificar para el boton Modificar */
		protected static final String MODIFICAR 		= "Modificar";
		
		/** Texto Borrar para el boton Borrar */
		protected static final String AMPLIAR = "Ampliar prestamo";
	    
		/** Campo de texto para introducir el ID */
		JTextField textID;
		
		/** Campo de texto para introducir el DIA */
		JTextField textDIA;
		
		/** Campo de texto para introducir el MES */
		JTextField textMES;
		
		/** Campo de texto para introducir el ANIO */
		JTextField textANIO;
		
		/** Campo de texto para introducir el DIA */
		JTextField textDIA2;
		
		/** Campo de texto para introducir el MES */
		JTextField textMES2;
		
		/** Campo de texto para introducir el ANIO */
		JTextField textANIO2;
		
		/** Etiqueta ID */
		JLabel labelID;
		
		/** Etiqueta DIA*/
		JLabel labelDIA;
		
		/** Etiqueta MES*/
		JLabel labelMES;
		
		/** Etiqueta ANIO*/
		JLabel labelANIO;
		
		/** Etiqueta DIA*/
		JLabel labelDIA2;
		
		/** Etiqueta MES*/
		JLabel labelMES2;
		
		/** Etiqueta ANIO*/
		JLabel labelANIO2;
		
		/** Etiqueta ANIO*/
		JLabel labelVACIO;
		
		/** Etiqueta ANIO*/
		JLabel labelRESULTADO;
		JLabel labelRESULTADO1;
		JLabel labelRESULTADO2;
		
		/** Panel de control que contiene los botones */
		JComponent buttonPanel;
		
		/** Panel principal donde irá el cuadro de búsqueda y la tabla */
		JPanel leftPane;
		
		/** Panel donde irá la barra de búsqueda */
		JPanel textControlsPane;
		
		String llamada = "SELECT Count(*) FROM Prestamos WHERE Nsocio = ";
		String llamada2 = " AND Fecha_inicio != Fecha_fin";
		final String resultado = "El resultado es el siguiente: ";
		String resultadofinal = null;
		String resulaux1 = "El número de préstamos realizados por el usuario ";
		String resulaux2 = " ";
		String resulaux3 = " ";
		
		JRadioButton botonUsuario  			= new JRadioButton("Usuario"  , true);
		JRadioButton botonVolumen  			= new JRadioButton("Volumen"   , false);
		JRadioButton botonBiblioteca  		= new JRadioButton("Biblioteca"   , false);
		JRadioButton botonNoDev				= new JRadioButton("No Devueltos"  , false);
		JRadioButton botonTodos    			= new JRadioButton("Todos"   , true);
		JRadioButton botonSiPeriodo			= new JRadioButton("Periodo"  , false);
		JRadioButton botonNoPeriodo    		= new JRadioButton("Sin Periodo"   , true);
		
		String busqueda = null;
		String criterio = null;
		String busqTotal = null;
		
		int bandera = 0;
		boolean bandera2 = false;
		boolean hayperiodo = false;
		
		public ConsultorioPrestamo() 
		{	
			busqueda = " id_prestamo  > 0";
			setLayout(new BorderLayout());
			
			/* Aquí creo los 2 paneles con los checkbuttons, tanto para elegir
			 * si queremos buscar libros o revistas, como para elegir si queremos
			 * ver prestados o todos.
			 */
			ButtonGroup bgroupPoS = new ButtonGroup();
			bgroupPoS.add(botonSiPeriodo);
			botonSiPeriodo.addActionListener(this);
			botonSiPeriodo.setActionCommand("SIPERIODO");
			bgroupPoS.add(botonNoPeriodo);
			botonNoPeriodo.addActionListener(this);
			botonNoPeriodo.setActionCommand("NOPERIODO");
			
			ButtonGroup bgroupUoV = new ButtonGroup();
			bgroupUoV.add(botonUsuario);
			botonUsuario.addActionListener(this);
			botonUsuario.setActionCommand("USUARIO");
			bgroupUoV.add(botonVolumen);
			botonVolumen.addActionListener(this);
			botonVolumen.setActionCommand("VOLUMEN");
			bgroupUoV.add(botonBiblioteca);
			botonBiblioteca.addActionListener(this);
			botonBiblioteca.setActionCommand("BIBLIOTECA");
			
			ButtonGroup bgroupNoT = new ButtonGroup();
			bgroupNoT.add(botonNoDev);
			botonNoDev.addActionListener(this);
			botonNoDev.setActionCommand("NODEV");
			bgroupNoT.add(botonTodos);
			botonTodos.addActionListener(this);
			botonTodos.setActionCommand("TODOS");
			
			JPanel radioPanel = new JPanel();
			radioPanel.setLayout(new GridLayout(1, 5));
			radioPanel.add(botonUsuario);
			radioPanel.add(botonVolumen);
			radioPanel.add(botonBiblioteca);
			radioPanel.add(botonNoDev);
			radioPanel.add(botonTodos);
			radioPanel.add(botonSiPeriodo);
			radioPanel.add(botonNoPeriodo);
			
			radioPanel.setBorder(BorderFactory.createTitledBorder(
			           BorderFactory.createEtchedBorder(), ""));
			
			/* Creación de lo campos de texto */

			//Campo de texto de ID
			textID = new JTextField(10);
			textID.setActionCommand(stringID);
			textID.addActionListener(this);
			
			//Campo de texto de ID
			textDIA = new JTextField(2);
			textDIA.setActionCommand(DIA);
			textDIA.addActionListener(this);
			textDIA.setDocument(new controlarLontigud(2,true)); 
			
			//Campo de texto de ID
			textMES = new JTextField(2);
			textMES.setActionCommand(MES);
			textMES.addActionListener(this);
			textMES.setDocument(new controlarLontigud(2,true)); 
			
			//Campo de texto de ID
			textANIO = new JTextField(4);
			textANIO.setActionCommand(ANIO);
			textANIO.addActionListener(this);
			textANIO.setDocument(new controlarLontigud(4,true)); 
			
			//Campo de texto de ID
			textDIA2 = new JTextField(2);
			textDIA2.setActionCommand(DIA);
			textDIA2.addActionListener(this);
			textDIA2.setDocument(new controlarLontigud(2,true)); 
			
			//Campo de texto de ID
			textMES2 = new JTextField(2);
			textMES2.setActionCommand(MES);
			textMES2.addActionListener(this);
			textMES2.setDocument(new controlarLontigud(2,true)); 
			
			//Campo de texto de ID
			textANIO2 = new JTextField(4);
			textANIO2.setActionCommand(ANIO);
			textANIO2.addActionListener(this);
			textANIO2.setDocument(new controlarLontigud(4,true)); 
			
    		textDIA.setEnabled(false);
    		textMES.setEnabled(false);
    		textANIO.setEnabled(false);
    		textDIA2.setEnabled(false);
    		textMES2.setEnabled(false);
    		textANIO2.setEnabled(false);
			
			/* Creación de las etiquetas... */
			
			//Etiqueta para Usuario
			labelID = new JLabel(stringID);
	        labelID.setLabelFor(textID);
	        
			//Etiqueta para Usuario
			labelDIA = new JLabel(DIA);
	        labelDIA.setLabelFor(textDIA);
	        
			//Etiqueta para Usuario
			labelMES = new JLabel(MES);
	        labelMES.setLabelFor(textMES);
	        
			//Etiqueta para Usuario
			labelANIO = new JLabel(ANIO);
	        labelANIO.setLabelFor(textANIO);
	        
			//Etiqueta para Usuario
			labelDIA2 = new JLabel(DIA);
	        labelDIA2.setLabelFor(textDIA2);
	        
			//Etiqueta para Usuario
			labelMES2 = new JLabel(MES);
	        labelMES2.setLabelFor(textMES2);
	        
			//Etiqueta para Usuario
			labelANIO2 = new JLabel(ANIO);
	        labelANIO2.setLabelFor(textANIO2);
	        
			labelVACIO = new JLabel("");
			labelRESULTADO = new JLabel("");
			labelRESULTADO1 = new JLabel("");
			labelRESULTADO2 = new JLabel("");
	        
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
			JLabel[] labels = {labelID};
		    JTextField[] textFields = {textID};
			addLabelTextRows(labels, textFields, gridbag, textControlsPane);
			textControlsPane.add(labelVACIO, c);
		    //##########################################################//
		    //##########################################################//
			
			JPanel panelInicio = new JPanel();
			JLabel[] labels2 = {labelDIA,labelMES,labelANIO};
		    JTextField[] textFields2 = {textDIA,textMES,textANIO};
			addLabelTextRows(labels2, textFields2, gridbag, panelInicio);
			panelInicio.setLayout(gridbag);
    		panelInicio.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder("Fecha Inicio"),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
			
    		JPanel panelFin = new JPanel();
    		JLabel[] labels3 = {labelDIA2,labelMES2,labelANIO2};
    		JTextField[] textFields3 = {textDIA2,textMES2,textANIO2};
			addLabelTextRows(labels3, textFields3, gridbag, panelFin);
			panelFin.setLayout(gridbag);
    		panelFin.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder("Fecha Fin"),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
    		c.gridwidth = GridBagConstraints.RELATIVE; //last
    		c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.WEST;
    		textControlsPane.add(panelInicio, c);
    		c.gridwidth = GridBagConstraints.REMAINDER; //last
    		textControlsPane.add(panelFin, c);
    		c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.EAST;	
		
			
		    //##########################################################//
		    //##########################################################//
			textControlsPane.add(radioPanel,c);
			c.fill = GridBagConstraints.NONE;

			//Situados los demás elementos, colocamos la etiqueta informativa
			buttonPanel =  createButtonPanel();
	        textControlsPane.add(buttonPanel, c);
		    textControlsPane.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Estadisticas"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));  

		    //##########################################################//
		    //##########################################################//
		    

			//Agrupamos los distintos paneles creados (uno en este caso)
			c.anchor = GridBagConstraints.WEST;
		    textControlsPane.add(labelRESULTADO,c);
		    textControlsPane.add(labelRESULTADO1,c);
		    textControlsPane.add(labelRESULTADO2,c);
			leftPane = new JPanel(new BorderLayout());
			leftPane.add(textControlsPane, BorderLayout.PAGE_START);

			add(leftPane, BorderLayout.LINE_START);
			leftPane.setSize(600,270);
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
				//c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
				c.fill = GridBagConstraints.NONE;      //reset to default
				c.weightx = 0.0;                       //reset to default
				container.add(labels[i], c);
			
				c.anchor = GridBagConstraints.WEST;
				//c.gridwidth = GridBagConstraints.REMAINDER;     //end row
				c.fill = GridBagConstraints.NONE;
				c.weightx = 1.0;
				container.add(textFields[i], c);
			}//fin_del_for
		
		}//fin_addLabelTextRows
		
   		protected JComponent createButtonPanel() 
   		{
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
    		JPanel p = new JPanel(new GridLayout(1,0));

    		// Creo los 2 botones.
    		JButton busquedaButton = new JButton(CALCULAR);

    		// Asigno un nombre para los botones
    		busquedaButton.setActionCommand(CALCULAR);

    		// Asigno la acción para los botones Aceptar y Cancelar.
    		busquedaButton.addActionListener(this);

    		p.add(busquedaButton);

    		return p;
		}// Fin createButtonPanel
   		
	    public void actionPerformed(ActionEvent e) 
	    {
	    	
	    	if ("USUARIO".equals(e.getActionCommand()))
	    	{	bandera = 0;
	    		resulaux1 = "El número de préstamos realizados por el usuario ";
	    		llamada = "SELECT Count(*) FROM Prestamos WHERE Nsocio = ";
	    		textID.setEnabled(true);
	    	}
	    	else if ("VOLUMEN".equals(e.getActionCommand()))
			{	bandera = 1;
	    		resulaux1 = "El núumero de préstamos realizados sobre el volumen ";
	    		llamada = "SELECT Count(*) FROM Prestamos WHERE id_libro = ";
	    		textID.setEnabled(true);
			}
	    	else if ("BIBLIOTECA".equals(e.getActionCommand()))
			{	bandera = 2;
	    		resulaux1 = "El número de préstamos realizados en la biblioteca ";
	    		llamada = "SELECT Count(*) FROM Prestamos WHERE id_prestamo > 0 ";
	    		textID.setEnabled(false);
	    		textID.setText("");
			}
	    	else if ("NODEV".equals(e.getActionCommand()))
			{
	    		bandera2 = true;
	    		resulaux2 = " que no han sido devueltos  ";
	    		llamada2 = " AND Fecha_inicio != Fecha_fin AND Fecha_Devuelto > Fecha_fin ";
			}
	    	else if ("TODOS".equals(e.getActionCommand()))
			{
	    		bandera2 = false;
	    		resulaux2 = " ";
	    		llamada2 = " AND Fecha_inicio != Fecha_fin ";
			}
	    	else if ("SIPERIODO".equals(e.getActionCommand()))
			{
	    		hayperiodo = true;
	    		resulaux3 = " durante el periodo ";
	    		textDIA.setEnabled(true);
	    		textMES.setEnabled(true);
	    		textANIO.setEnabled(true);
	    		textDIA2.setEnabled(true);
	    		textMES2.setEnabled(true);
	    		textANIO2.setEnabled(true);
	    		
			}
	    	else if ("NOPERIODO".equals(e.getActionCommand()))
			{
	    		hayperiodo = false;
	    		resulaux3 = " ";
	    		textDIA.setEnabled(false);
	    		textMES.setEnabled(false);
	    		textANIO.setEnabled(false);
	    		textDIA2.setEnabled(false);
	    		textMES2.setEnabled(false);
	    		textANIO2.setEnabled(false);	    		
	    		textDIA.setText("");
	    		textMES.setText("");
	    		textANIO.setText("");
	    		textDIA2.setText("");
	    		textMES2.setText("");
	    		textANIO2.setText("");
			}
	    	else if (CALCULAR.equals(e.getActionCommand()))
			{
	    		String fechaInic = "'"+textANIO.getText() +"-"+textMES.getText()+"-"+textDIA.getText()+"'";
	    		String fechaFin  = "'"+textANIO2.getText() +"-"+textMES2.getText()+"-"+textDIA2.getText()+"'";
	    		String calculo;
	    		String nombre;
	    		String llamadaDefinitiva = null;
	    		boolean fechascorrectas = false;
	    		if(hayperiodo)
	    		{
		    		int mesElegidoInic = 0; 
		    		mesElegidoInic = Integer.parseInt(textMES.getText());
		    		int mesElegidoFin = 0;
		    		mesElegidoFin = Integer.parseInt(textMES2.getText());
		    		if((textANIO.getText().length() != 4) && (textANIO2.getText().length() == 4))
		    		{
		    			JOptionPane.showMessageDialog(textControlsPane, "El año es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
		    			fechascorrectas = false;
		    		}
		    		else
		    		{
			    		if(Integer.parseInt(textANIO.getText()) >= Integer.parseInt(textANIO2.getText()))
			    		{
			    			if(Integer.parseInt(textANIO.getText()) > Integer.parseInt(textANIO2.getText())) JOptionPane.showMessageDialog(textControlsPane, "El año de Fin debe ser mayor o igual, no menor", "Error", JOptionPane.ERROR_MESSAGE);
			    			else if((Integer.parseInt(textMES.getText()) <1 || Integer.parseInt(textMES.getText()) >12 ) || (Integer.parseInt(textMES2.getText()) <0 || Integer.parseInt(textMES2.getText()) >12 ))
				    			JOptionPane.showMessageDialog(textControlsPane, "El mes de inicio o de fin es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
			    			else if((Integer.parseInt(textDIA.getText()) <1 || Integer.parseInt(textDIA.getText()) >31 ) || (Integer.parseInt(textMES2.getText()) <1 || Integer.parseInt(textMES2.getText()) >31 ))
				    			JOptionPane.showMessageDialog(textControlsPane, "El dia es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
			    			else if(Integer.parseInt(textMES.getText()) > Integer.parseInt(textMES2.getText())) JOptionPane.showMessageDialog(textControlsPane, "El mes de Fin debe ser mayor o igual, no menor", "Error", JOptionPane.ERROR_MESSAGE);
			    			else if(Integer.parseInt(textMES.getText()) == Integer.parseInt(textMES2.getText()))
			    			{
			    				if(Integer.parseInt(textDIA.getText()) > Integer.parseInt(textDIA2.getText())) JOptionPane.showMessageDialog(textControlsPane, "El dia de Fin debe ser mayor o igual, no menor", "Error", JOptionPane.ERROR_MESSAGE);
			    			}
			    			else 
			    			{
			    				if((Integer.parseInt(textMES.getText())==2) && (Integer.parseInt(textDIA.getText())>28))	JOptionPane.showMessageDialog(textControlsPane, "El dia es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
			    				else if((Integer.parseInt(textMES2.getText())==2) && (Integer.parseInt(textDIA2.getText())>28))	JOptionPane.showMessageDialog(textControlsPane, "El dia es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
			    				else if((mesElegidoInic == 4) || (mesElegidoInic == 6) || (mesElegidoInic == 9) || (mesElegidoInic == 11) )
			    				{
			    					if (Integer.parseInt(textDIA.getText())>30)	JOptionPane.showMessageDialog(textControlsPane, "El dia de inicio es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
			    				}
			    				else if((mesElegidoFin == 4) || (mesElegidoFin == 6) || (mesElegidoFin == 9) || (mesElegidoFin == 11) )
			    				{
			    					if (Integer.parseInt(textDIA2.getText())>30)	JOptionPane.showMessageDialog(textControlsPane, "El dia de inicio es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
			    				}
			    				else fechascorrectas = true;
			    					
			    			}
			    		}
			    		else fechascorrectas = true;
		    		}
	    		}
		    		
	    		if(((hayperiodo) && (fechascorrectas)) || (!hayperiodo))
	    		{
	    			if(hayperiodo) llamadaDefinitiva = llamada+textID.getText()+llamada2+" AND Fecha_inicio >= "+fechaInic+" AND Fecha_inicio <= "+fechaFin;
		    		else llamadaDefinitiva = llamada+textID.getText()+llamada2; 
		    		MiMysql calcula = new MiMysql();
		    		//System.out.println(llamadaDefinitiva);
		    		calculo = calcula.getCadena(llamadaDefinitiva);
		    		if(bandera ==0) nombre = calcula.getCadena("SELECT Nombre FROM Usuarios WHERE Nsocio = "+textID.getText()) + " con Numero de Socio "+textID.getText();
		    		else if(bandera ==1) nombre = calcula.getCadena("SELECT Titulo FROM Libros WHERE id = "+textID.getText()) + " con ID "+textID.getText();
		    		else nombre = "";
		    		labelRESULTADO.setText(resultado);
		    		resultadofinal = resulaux1 + nombre + resulaux2 ;
		    		labelRESULTADO1.setText(resultadofinal);
		    		if(hayperiodo) resultadofinal = "desde el " +textDIA.getText() +" del "+textMES.getText()+ " de "+textANIO.getText()+" hasta el " +textDIA2.getText() +" del "+textMES2.getText()+ " de "+textANIO2.getText()+ " es de " + calculo + " unidades.";
		    		else resultadofinal = " es de " + calculo + " unidades.";
		    		labelRESULTADO2.setText(resultadofinal);
		    		//System.out.println(resultadofinal);
	    		}
	    		
			}
	

			/*else if (CONSULTA.equals(e.getActionCommand()))
			{

			}
			else if ("TODOS".equals(e.getActionCommand()))
			{	
				textBusqueda.setEnabled(true);
				busqueda = " id_prestamo  > 0";
			}
			else if ("ID".equals(e.getActionCommand()))
			{
				textBusqueda.setEnabled(false);
				
			}
			else if ("DEVOLVER".equals(e.getActionCommand()))
			{
				textBusqueda.setEnabled(true);
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
			}*/

	    }
	   
		
	}

}