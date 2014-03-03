
import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;

/* Used by InternalFrameDemo.java. */
public class VentanaInsertarUsuario extends JInternalFrame
			 {
    static final int xOffset = 30, yOffset = 30;


	public VentanaInsertarUsuario(String nivel) 
	{

		super("Insertar Usuario", 
		      true, //resizable
		      true, //closable
		      false, //maximizable
		      true);//iconifiable
		
			//...Create the GUI and put it in the window...
		

		FormularioInsertarUsuario formulario = new FormularioInsertarUsuario(nivel);
		add(formulario);

		//Display the window.
		pack();
		setVisible(true);

		//...Then set the window size or call pack...
		setSize(650,300);

		//Set the window's location.
		setLocation(xOffset, yOffset);
    	}

	/** class FormularioInsertarUsuario */
	class FormularioInsertarUsuario extends JPanel implements ActionListener
	{
	    
		/** Declaración de variables */
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String ACEPTAR = "Aceptar";
		
		/** Texto Cancelar para el boton cancelar */
		protected static final String CANCELAR = "Cancelar";
		
		/** Texto que muestra labelNombre */
	    protected static final String stringNombre		= "Nombre: ";

		/** Texto que muestra labelApellidos*/
		protected static final String stringApellidos	= "Apellidos: ";

		/** Texto que muestra labelDNI */
		protected static final String stringDNI			= "DNI: ";

		/** Texto que muestra labelPassword */
		protected static final String stringPassword	= "Password: ";

		/** Texto que muestra labelEmail */
		protected static final String stringEmail		= "Email: ";

		/** Texto que muestra labelDireccion */
		protected static final String stringDireccion	= "Direccion: ";

		/** Texto que muestra labelTelefono */
		protected static final String stringTelefono	= "Telefono: ";

		/** Texto que muestra labelTitulacion */
		protected static final String stringTitulacion	= "Titulacion: ";

		/** Texto que muestra labelRol */
		protected static final String stringRol			= "Rol: ";
		JPanel textControlsPane;

		/** Campo de texto para introducir el Nombre */
		JTextField textNombre;

		/** Campo de texto para introducir los Apellidos */
		JTextField textApellidos;

		/** Campo de texto para introducir el DNI */
		JTextField textDNI;

		/** Campo de texto para introducir el Password */
		JTextField textPassword;

		/** Campo de texto para introducir el Email */
		JTextField textEmail;
		 
		/** Campo de texto para introducir la Direccion */
		JTextField textDireccion;

		/** Campo de texto para introducir el Telefono */
		JTextField textTelefono;

		/** Campo de texto para introducir la Titulacion */
		JComboBox textTitulacion;

		/** Campo de texto para introducir el Rol */
		JComboBox textRol;
		 
		/** Etiqueta Nombre */
		JLabel labelNombre;

		/** Etiqueta Apellidos */
		JLabel labelApellidos;

		/** Etiqueta DNI */
		JLabel labelDNI;

		/** Etiqueta Password */
		JLabel labelPassword;

		/** Etiqueta Email */
		JLabel labelEmail;

		/** Etiqueta Direccion */
		JLabel labelDireccion;

		/** Etiqueta Telefono */
		JLabel labelTelefono;
		 
		/** Etiqueta Titulacion */
		JLabel labelTitulacion;

		/** Etiqueta Rol */
		JLabel labelRol;


		/** Panel de control que contiene los botones */
		JComponent buttonPanel;

		/** Método que crea el formulario de Insertar Usuario */
		public FormularioInsertarUsuario(String nivel) 
		{
			//Establecemos el tipo de layout
			setLayout(new BorderLayout());


			/* Creación de lo campos de texto */

			//Campo de texto de Nombre
			textNombre = new JTextField(50);
			
			//Campo de texto de Apellidos
			textApellidos = new JTextField(50);

			//Campo de texto de Nombre
			textDNI = new JTextField(8);
			textDNI.setDocument(new controlarLontigud(8,true)); 

			//Campo de texto de Password
			textPassword = new JTextField(50);
			
			//Campo de texto de Email
			textEmail = new JTextField(50);

			//Campo de texto de Direccion
			textDireccion = new JTextField(50);
			
			//Campo de texto de Telefono
			textTelefono = new JTextField(50);
			textTelefono.setDocument(new controlarLontigud(13,true)); 
			


			//Campo de texto de Telefono
			textTitulacion = new JComboBox();
			textTitulacion.setEditable(true);
			RolSuperior titulaciones = new RolSuperior();
			String[] titulacionesActuales;
			titulacionesActuales = titulaciones.getTitulaciones();
			for(int i=0;i<titulacionesActuales.length;i++) textTitulacion.addItem(titulacionesActuales[i]);
			
			
			//Campo de texto de Rol
			textRol = new JComboBox();
			if (nivel.compareTo("Administrador") == 0)
				{	
					textRol.addItem("Administrador");
					textRol.addItem("Tecnico");
				}

			textRol.addItem("Lector-Socio");
			textRol.addItem("Lector-Basico");
			
			/* Creación de las etiquetas... */

			//Etiqueta para Usuario
			labelNombre = new JLabel(stringNombre);
	        labelNombre.setLabelFor(textNombre);

			//Etiqueta para Apellidos
			labelApellidos = new JLabel(stringApellidos);
	        labelApellidos.setLabelFor(textApellidos);
			
			//Etiqueta de Password
			labelDNI = new JLabel(stringDNI);
	        labelDNI.setLabelFor(textDNI);

			//Etiqueta de Password
			labelPassword = new JLabel(stringPassword);
	        labelPassword.setLabelFor(textPassword);

			//Etiqueta para Email
			labelEmail = new JLabel(stringEmail);
	        labelEmail.setLabelFor(textEmail);

			//Etiqueta para Direccion
			labelDireccion = new JLabel(stringDireccion);
	        labelDireccion.setLabelFor(textDireccion);

			//Etiqueta para Telefono
			labelTelefono = new JLabel(stringTelefono);
	        labelTelefono.setLabelFor(textTelefono);

			//Etiqueta para Titulacion
			labelTitulacion = new JLabel(stringTitulacion);
			labelTitulacion.setLabelFor(textTitulacion);

			//Etiqueta para Rol
			labelRol = new JLabel(stringRol);
	        labelRol.setLabelFor(textRol);


		    /* Creamos un layout propio para los controles anteriores */
			textControlsPane = new JPanel();
	        GridBagLayout gridbag = new GridBagLayout();
		    GridBagConstraints c = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane.setLayout(gridbag);

			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...
			JLabel[] labels = {labelNombre, labelApellidos, labelDNI, labelPassword, labelEmail, labelDireccion, labelTelefono};
		    JTextField[] textFields = {textNombre, textApellidos, textDNI, textPassword, textEmail, textDireccion, textTelefono};
			addLabelTextRows(labels, textFields, gridbag, textControlsPane);
			c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
			c.fill = GridBagConstraints.NONE;      
			c.weightx = 0.0;                       
			textControlsPane.add(labelTitulacion, c);
			c.gridwidth = GridBagConstraints.REMAINDER; 
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			textControlsPane.add(textTitulacion, c);
			c.gridwidth = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.NONE;      
			c.weightx = 0.0;   
			textControlsPane.add(labelRol, c);
			c.gridwidth = GridBagConstraints.REMAINDER; 
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			textControlsPane.add(textRol, c);

			//Propiedades de la rejilla
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
			c.fill = GridBagConstraints.NONE;  
		    c.anchor = GridBagConstraints.EAST;
			c.weightx = 1.0;
			
			//Situados los demás elementos, colocamos la etiqueta informativa
			buttonPanel =  createButtonPanel();
	        textControlsPane.add(buttonPanel, c);
		    textControlsPane.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Inserción de Usuario"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));  

			//Agrupamos los distintos paneles creados (uno en este caso)
			JPanel leftPane = new JPanel(new BorderLayout());

			leftPane.add(textControlsPane, BorderLayout.PAGE_START);
			//leftPane.add(areaScrollPane, BorderLayout.CENTER);

			add(leftPane, BorderLayout.LINE_START);
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

					for (int i = 0; i < numLabels; i++) {
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
    		protected JComponent createButtonPanel() {
    				// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
            		JPanel p = new JPanel(new GridLayout(1,0));

            		// Creo los 2 botones.
               		ImageIcon iconoAceptar 	= createImageIcon("images/001_06.png");
            		ImageIcon iconoCancelar = createImageIcon("images/001_05.png");
            		JButton aceptarButton = new JButton(ACEPTAR,iconoAceptar);
            		JButton cancelarButton = new JButton(CANCELAR,iconoCancelar);

            		// Asigno un nombre para los botones
            		aceptarButton.setActionCommand(ACEPTAR);
            		cancelarButton.setActionCommand(CANCELAR);

            		// Asigno la acción para los botones Aceptar y Cancelar.
            		aceptarButton.addActionListener(this);
            		cancelarButton.addActionListener(this);

            		p.add(aceptarButton);
            		p.add(cancelarButton);

            		return p;
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
		    public void actionPerformed(ActionEvent e) 
		    {
		    	boolean faltanDatos = false;
				if (ACEPTAR.equals(e.getActionCommand()))
				{	
					/** Datos es un array que posee todos los datos a insertar del usuario */
					String[] Datos 	 = {textNombre.getText(),
										textApellidos.getText(),
										textEmail.getText(),
										textTelefono.getText(),
										textDireccion.getText(),
										(String)textTitulacion.getSelectedItem(),
										(String)textRol.getSelectedItem(),
										textDNI.getText(),
										textPassword.getText(),
										};
					for(int i=0;i<Datos.length;i++) if(Datos[i].compareTo("") == 0) faltanDatos = true; 
					
					/** Creo el objeto de tipo RolSuperior al que le enviaré los Datos*/
					if(!faltanDatos)
					{
							RolSuperior dejarDatos = new RolSuperior();
							int idSocio = dejarDatos.setDatosUsuarios(Datos);
							JOptionPane.showMessageDialog(textControlsPane, "El usuario se añadió correctamente.\n" +
																	"El número de socio asignado a "+Datos[0]+ " "+Datos[1]+
																	" es "+idSocio, "Gestor Biblioteca", JOptionPane.INFORMATION_MESSAGE);
					}
					else JOptionPane.showMessageDialog(textControlsPane, "Debe rellenar todos los campos", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
		 
				}
				else if(CANCELAR.equals(e.getActionCommand()))
				{ //quit
					dispose();
				}

		    }
	}

}