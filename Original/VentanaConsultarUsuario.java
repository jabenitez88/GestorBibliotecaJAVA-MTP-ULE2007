import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

public class VentanaConsultarUsuario extends JInternalFrame
{
    static final int xOffset = 30, yOffset = 30;
    static String NivelUsuario;

	public VentanaConsultarUsuario(String nivel) 
	{

		super("Consultar Usuario", 
		      true, //resizable
		      true, //closable
		      false, //maximizable
		      true);//iconifiable
		NivelUsuario = nivel;
			//...Create the GUI and put it in the window...
		

		ConsultorioUsuario consultorio = new ConsultorioUsuario(nivel);

		
		add(consultorio);


		//Display the window.
		//pack();
		setVisible(true);

		//...Then set the window size or call pack...
		setSize(1000,650);

		//Set the window's location.
		setLocation(xOffset, yOffset);
	}
	
	
	class ConsultorioUsuario extends JPanel implements ActionListener
	{

		/** Declaración de variables */
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String BUSCAR = "Buscar";
		
		/** Texto Modificar para el boton Modificar */
		protected static final String MODIFICAR = "Modificar";
		
		/** Texto Borrar para el boton Borrar */
		protected static final String BORRAR = "Borrar";
		
		/** Texto que muestra labelNombre */
	    protected static final String stringBusqueda	= "Busqueda: ";
	    
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
		
		DefaultTableModel modelo;

		
		String[] rolAnterior;
		/** Todos los criterios de búsqueda */
		String[] criterios ={"NSocio","Nombre","Apellidos","Direccion","DNI","Email","Titulacion","Todos"};
		String[] datosAModificar ={"Nombre","Apellidos","DNI","Direccion","Email","Password","Telefono","Rol","Titulacion"};
		
		/** Caja que contiene los criterios */
		JComboBox cajaCriterios = new JComboBox(criterios);
		
		public ConsultorioUsuario(String nivel) 
		{	

			
			setLayout(new BorderLayout());
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
			c.weightx = 1.0;
			
			//Establecemos la rejilla
			textControlsPane.setLayout(gridbag);

			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...
			JLabel[] labels = {labelBusqueda};
		    JTextField[] textFields = {textBusqueda};
			addLabelTextRows(labels, textFields, gridbag, textControlsPane);
			textControlsPane.add(cajaCriterios, c);

			
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
			scroll.setPreferredSize(new Dimension (975, 350));
			//tblResultados.setPreferredSize(new Dimension (200, 800));
			
			modelo.addColumn("Num Socio");
			modelo.addColumn("Nombre");
			modelo.addColumn("Apellidos");
			modelo.addColumn("DNI");
			modelo.addColumn("Email");
			modelo.addColumn("Password");
			modelo.addColumn("Direccion");
			modelo.addColumn("Rol");
			modelo.addColumn("Titulacion");
			
			setPreferredColumnWidths();
		    c.anchor = GridBagConstraints.FIRST_LINE_START;
			resultadosPanel.add(scroll,c);
			resultadosPanel.setBorder(
					BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder("Resultados"),
                            BorderFactory.createEmptyBorder(5,5,5,5))); 
			
	
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
			
			//Agrupamos los distintos paneles creados (uno en este caso)
			leftPane = new JPanel(new BorderLayout());

			leftPane.add(textControlsPane, BorderLayout.PAGE_START);
			leftPane.add(resultadosPanel, BorderLayout.CENTER);
			leftPane.add(botonesAccion, BorderLayout.SOUTH);
			//leftPane.add(areaScrollPane, BorderLayout.CENTER);

			add(leftPane, BorderLayout.LINE_START);
			//add(resultadosPanel, BorderLayout.CENTER);
			
			//if (NivelUsuario.compareTo("Tecnico") == 0) borrarButton.setEnabled(false);
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
   		/** Función que recibe los eventos */
	    public void actionPerformed(ActionEvent e) 
	    {

			if (BUSCAR.equals(e.getActionCommand()))
			{
				String criterioElegido = String.valueOf(cajaCriterios.getSelectedItem());
				String busquedaRealizada = textBusqueda.getText();
				String[] titulaciones;
				String[] roles;
				RolSuperior buscando = new RolSuperior();
				//tblResultados.setModel(buscando.getTablaBusquedaUsuario(busquedaRealizada,criterioElegido));
				rolAnterior = null;
				
				modelo = buscando.getTablaBusquedaUsuario(busquedaRealizada,criterioElegido,NivelUsuario);
				modelo.isCellEditable(0,0);
				tblResultados.setModel(modelo);
			    tblResultados.getModel().addTableModelListener(new TableModelListener() {

			        public void tableChanged(TableModelEvent e) {
						int[] seleccionadas = tblResultados.getSelectedRows();
						boolean lSocio = (((String) tblResultados.getValueAt(seleccionadas[0],8)).compareTo("Lector-Socio") == 0);
						boolean lBasico = (((String) tblResultados.getValueAt(seleccionadas[0],8)).compareTo("Lector-Basico") == 0);
						boolean lAdministrador = (((String) tblResultados.getValueAt(seleccionadas[0],8)).compareTo("Administrador") == 0);
						boolean lTecnico = (((String) tblResultados.getValueAt(seleccionadas[0],8)).compareTo("Tecnico") == 0);
						if(NivelUsuario.compareTo("Tecnico") == 0)
						{	// Si no es socio ni es básico, rol equivocado.
							if (!lSocio && !lBasico) 
							{
								JOptionPane.showMessageDialog(leftPane, "Rol equivocado.\nRoles Posibles:\n-Lector-Basico\n-Lector-Socio", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
								tblResultados.setValueAt(rolAnterior[seleccionadas[0]], seleccionadas[0], 8);
							}
							
						}
						else if(NivelUsuario.compareTo("Administrador") == 0)
						{	// Si no es lector-socio, lector-basico, tecnico o administrador, rol equivocado.
							if (!lSocio && !lBasico && !lTecnico && !lAdministrador) 
							{
								JOptionPane.showMessageDialog(leftPane, "Rol equivocado.\nRoles Posibles:"
																+ "\n-Administrador" 
																+ "\n-Tecnico"
																+ "\n-Lector-Socio"
																+ "\n-Lector-Basico", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
								tblResultados.setValueAt(rolAnterior[seleccionadas[0]], seleccionadas[0], 8);
							}
							
						}
							
			        }
			      });
			    rolAnterior = new String[tblResultados.getRowCount()];
			    for(int i=0;i<tblResultados.getRowCount();i++)
			    {
			    	rolAnterior[i] = (String) tblResultados.getValueAt(i, 8);
			    }
				//tblResultados.getValueAt(row, column)
			//	System.out.println(resl.get(1).toString());
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
	        	for(i=0;i<resl;i++)
	        	{	
	        		modificaEnDB = "UPDATE Usuarios SET ";

		        	for(j=0;j<8;j++)
		        	{	
		        		if((j == 2) || (j == 6)) modificaEnDB = modificaEnDB + datosAModificar[j] + " = " + tblResultados.getValueAt(i, j+1) + " , ";
		        		else  modificaEnDB = modificaEnDB + datosAModificar[j] + " = '" + tblResultados.getValueAt(i, j+1) + "', ";
		        	}
		        	modificaEnDB = modificaEnDB + datosAModificar[j] + " = '"  + tblResultados.getValueAt(i, 9)                                                                       
		        			             + "' WHERE Nsocio = " + tblResultados.getValueAt(i,0) + " ;" ; 
		        	//System.out.println(modificaEnDB);
		        	actualizador.LlamadaDB(modificaEnDB);

	        	}
			}
			else if(BORRAR.equals(e.getActionCommand()))
			{	int i=0;
				int[] seleccionadas = tblResultados.getSelectedRows();
				MiMysql borrando = new MiMysql();
				String borrado;
				for(i=0;i<seleccionadas.length;i++)
				{ 	
					borrado = "DELETE FROM Usuarios WHERE Nsocio = " + tblResultados.getValueAt(seleccionadas[i], 0);
					if(borrando.getNumCopiasFondo("SELECT * FROM Prestamos WHERE Nsocio = "+tblResultados.getValueAt(seleccionadas[i], 0))!=0)
						{
							JOptionPane.showMessageDialog(textControlsPane, "El usuario con ID "+tblResultados.getValueAt(seleccionadas[i], 0)+" no puede ser borrado por que tiene préstamos", "Fallo borrar", JOptionPane.ERROR_MESSAGE);
						}
					else 
						{	borrando.LlamadaDB("DELETE FROM Reservas WHERE Nsocio = "+tblResultados.getValueAt(seleccionadas[i], 0)+" AND Puede = FALSE");
							borrando.LlamadaDB(borrado);
						}
				}
				
			}
			else
			{
					//System.exit(0);
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