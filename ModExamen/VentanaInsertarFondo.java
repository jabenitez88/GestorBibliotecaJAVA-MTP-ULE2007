
import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;

/* Used by InternalFrameDemo.java. */
public class VentanaInsertarFondo extends JInternalFrame
			 {
    static final int xOffset = 30, yOffset = 30;

	public VentanaInsertarFondo() 
	{

		super("Insertar Fondo", 
		      true, //resizable
		      true, //closable
		      false, //maximizable
		      true);//iconifiable
		
			//...Create the GUI and put it in the window...
		

		FormularioInsertarFondo formulario = new FormularioInsertarFondo();
		add(formulario);

		//Display the window.
		pack();
		setVisible(true);

		//...Then set the window size or call pack...
		setSize(705,398);

		//Set the window's location.
		setLocation(xOffset, yOffset);
    	}

	/** class FormularioInsertarUsuario */
	class FormularioInsertarFondo extends JPanel implements ActionListener
	{
		/** Declaración de variables */


		/** Texto Aceptar para el boton aceptar */
		protected static final String ACEPTAR = "Aceptar";
		
		/** Texto Cancelar para el boton cancelar */
		protected static final String CANCELAR = "Cancelar";
		
		/** Texto que muestra labelTitulo */
	    protected static final String stringTitulo		= "Titulo: ";

		/** Texto que muestra labelAutores*/
		protected static final String stringAutores	= "Autores: ";

		/** Texto que muestra labelEditorial */
		protected static final String stringEditorial			= "Editorial: ";

		/** Texto que muestra labelEdicion */
		protected static final String stringEdicion	= "Edicion: ";
		
		/** Texto que muestra labelEdicion */
		protected static final String stringVolumen	= "Volumen: ";

		/** Texto que muestra labelAnio */
		protected static final String stringAnio		= "Anio: ";

		/** Texto que muestra labelPaginas */
		protected static final String stringPaginas	= "Paginas: ";

		/** Texto que muestra labelSignatura */
		protected static final String stringSignatura	= "Signatura: ";

		/** Texto que muestra labelISBN */
		protected static final String stringISBN	= "ISBN: ";
		
		/** Texto que muestra labelEdicion */
		protected static final String stringNumero	= "Numero: ";

		/** Texto que muestra labelRol */
		protected static final String stringCopiasSala	= "Copias en Sala: ";
		
		/** Texto que muestra labelRol */
		protected static final String stringCopiasPrestamo	= "Copias en Prestamo: ";

		/** Campo de texto para introducir el Titulo */
		JTextField textTitulo;

		/** Campo de texto para introducir los Autores */
		JTextField textAutores;

		/** Campo de texto para introducir el Editorial */
		JTextField textEditorial;

		/** Campo de texto para introducir el Edicion */
		JTextField textEdicion;

		/** Campo de texto para introducir el Anio */
		JTextField textAnio;
		 
		/** Campo de texto para introducir la Paginas */
		JTextField textPaginas;

		/** Campo de texto para introducir el Signatura */
		JTextField textSignatura;

		/** Campo de texto para introducir la ISBN */
		JTextField textISBN;

		/** Campo de texto para introducir el CopiasSala */
		JTextField textCopiasSala;
		
		/** Campo de texto para introducir el CopiasPrestamo */
		JTextField textNumero;
		
		/** Campo de texto para introducir el CopiasPrestamo */
		JTextField textVolumen;
		
		/** Campo de texto para introducir el CopiasPrestamo */
		JTextField textCopiasPrestamo;

		/** Etiqueta Titulo */
		JLabel labelTitulo;

		/** Etiqueta Autores */
		JLabel labelAutores;

		/** Etiqueta Editorial */
		JLabel labelEditorial;
		
		/** Etiqueta Volumen */
		JLabel labelVolumen;
		
		/** Etiqueta Numero */
		JLabel labelNumero;

		/** Etiqueta Edicion */
		JLabel labelEdicion;

		/** Etiqueta Anio */
		JLabel labelAnio;

		/** Etiqueta Paginas */
		JLabel labelPaginas;

		/** Etiqueta Signatura */
		JLabel labelSignatura;
		 
		/** Etiqueta ISBN */
		JLabel labelISBN;

		/** Etiqueta CopiasSala */
		JLabel labelCopiasSala;
		
		/** Etiqueta CopiasPrestamo */
		JLabel labelCopiasPrestamo;
		
		/** Etiqueta CopiasPrestamo */
		JLabel labelCortoPlazo;

		JPanel textControlsPane;

		/** Panel de control que contiene los botones */
		JComponent buttonPanel;
		
		JRadioButton botonLibros   = new JRadioButton("Libros"  , true);
		JRadioButton botonRevistas    = new JRadioButton("Revistas"   , false);
		String[] criterios = {"SI","NO"};
		JComboBox cortoPlazo = new JComboBox(criterios);

		/** Método que crea el formulario de Insertar Usuario */
		public FormularioInsertarFondo() 
		{	
			setLayout(new BorderLayout());
			
			/* Aquí creo el panel para elegir entre libro o revista */

			botonLibros.addActionListener(this);
			botonLibros.setActionCommand("LIBROS");
			botonRevistas.addActionListener(this);
			botonRevistas.setActionCommand("REVISTAS");
			ButtonGroup bgroupLoR = new ButtonGroup();
			bgroupLoR.add(botonLibros);
			bgroupLoR.add(botonRevistas);

			JPanel radioPanel = new JPanel();
			radioPanel.setLayout(new GridLayout(1, 2));
			radioPanel.add(botonLibros); 
			radioPanel.add(botonRevistas);

			radioPanel.setBorder(BorderFactory.createTitledBorder(
			           BorderFactory.createEtchedBorder(), ""));
			
			//Establecemos el tipo de layout

			/* Creación de lo campos de texto */

			//Campo de texto de Titulo
			textTitulo = new JTextField(50);
			
			//Campo de texto de Autores
			textAutores = new JTextField(50);

			//Campo de texto de Titulo
			textEditorial = new JTextField(8);

			//Campo de texto de Volumen REVISTAS
			textVolumen  = new JTextField(50);
			textVolumen.setEnabled(false);
			//Campo de texto de Edicion LIBROS
			textEdicion = new JTextField(50);
			
			//Campo de texto de Numero REVISTAS
			textNumero  = new JTextField(50);
			textNumero.setEnabled(false);
			textNumero.setDocument(new controlarLontigud(10,true)); 
			
			//Campo de texto de Anio
			textAnio = new JTextField(50);
			textAnio.setDocument(new controlarLontigud(4,true)); 

			//Campo de texto de Paginas
			textPaginas = new JTextField(50);
			
			//Campo de texto de Signatura
			textSignatura = new JTextField(50);

			//Campo de texto de ISBN LIBROS
			textISBN = new JTextField(50);
			textISBN.setDocument(new controlarLontigud(13,true)); 
		
			//Campo de texto de CopiasSala
			textCopiasSala = new JTextField(50);
			textCopiasSala.setDocument(new controlarLontigud(10,true)); 

			//Campo de texto de CopiasPrestamo
			textCopiasPrestamo = new JTextField(50);
			textCopiasPrestamo.setDocument(new controlarLontigud(10,true)); 
			
			/* Creación de las etiquetas... */

			//Etiqueta para Usuario
			labelTitulo = new JLabel(stringTitulo);
	        labelTitulo.setLabelFor(textTitulo);

			//Etiqueta para Autores
			labelAutores = new JLabel(stringAutores);
	        labelAutores.setLabelFor(textAutores);
			
			//Etiqueta de Edicion
			labelEditorial = new JLabel(stringEditorial);
	        labelEditorial.setLabelFor(textEditorial);
	        
			//Etiqueta de Volumen
			labelVolumen = new JLabel(stringVolumen);
	        labelVolumen.setLabelFor(textVolumen);
	        
			//Etiqueta de Numero
			labelNumero = new JLabel(stringNumero);
	        labelNumero.setLabelFor(textNumero);

			//Etiqueta de Edicion
			labelEdicion = new JLabel(stringEdicion);
	        labelEdicion.setLabelFor(textEdicion);

			//Etiqueta para Anio
			labelAnio = new JLabel(stringAnio);
	        labelAnio.setLabelFor(textAnio);

			//Etiqueta para Paginas
			labelPaginas = new JLabel(stringPaginas);
	        labelPaginas.setLabelFor(textPaginas);

			//Etiqueta para Signatura
			labelSignatura = new JLabel(stringSignatura);
	        labelSignatura.setLabelFor(textSignatura);

			//Etiqueta para ISBN
			labelISBN = new JLabel(stringISBN);
			labelISBN.setLabelFor(textISBN);

			//Etiqueta para CopiasSala
			labelCopiasSala = new JLabel(stringCopiasSala);
	        labelCopiasSala.setLabelFor(textCopiasSala);
	        
	      //Etiqueta para CopiasSala
			labelCopiasPrestamo = new JLabel(stringCopiasPrestamo);
	        labelCopiasPrestamo.setLabelFor(textCopiasPrestamo);
	        
	        labelCortoPlazo = new JLabel("Corto plazo");


		    /* Creamos un layout propio para los controles anteriores */
			textControlsPane = new JPanel();
	        GridBagLayout gridbag = new GridBagLayout();
		    GridBagConstraints c = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane.setLayout(gridbag);
			
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
		    c.anchor = GridBagConstraints.WEST;
			c.weightx = 10.0;
			
			textControlsPane.add(radioPanel,c);
			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...
			JLabel[] labels = {labelTitulo, labelAutores, labelEditorial, labelEdicion,labelVolumen, labelAnio, labelPaginas, labelSignatura, labelISBN, labelNumero, labelCopiasSala,labelCopiasPrestamo};
		    JTextField[] textFields = {textTitulo, textAutores, textEditorial, textEdicion, textVolumen, textAnio, textPaginas, textSignatura, textISBN, textNumero,textCopiasSala,textCopiasPrestamo};
			addLabelTextRows(labels, textFields, gridbag, textControlsPane);
			c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
			c.fill = GridBagConstraints.NONE;      //reset to default
			c.weightx = 0.0;                       //reset to default
			textControlsPane.add(labelCortoPlazo,c);

			c.gridwidth = GridBagConstraints.REMAINDER;     //end row
			c.fill = GridBagConstraints.NONE;
			c.weightx = 1.0;
			textControlsPane.add(cortoPlazo,c);


			//Propiedades de la rejilla
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
		    c.anchor = GridBagConstraints.EAST;
			c.weightx = 10.0;
			
			//Situados los demás elementos, colocamos la etiqueta informativa
			buttonPanel =  createButtonPanel();
	        textControlsPane.add(buttonPanel, c);
		    textControlsPane.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Inserción de Fondo"),
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

            		// Asigno un Titulo para los botones
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

				if (ACEPTAR.equals(e.getActionCommand()))
				{
					/** Datos es un array que posee todos los datos a insertar del usuario */
					boolean faltanDatos = false;
					String[] Datos ;
					String corto = (String)cortoPlazo.getSelectedItem();
					if(corto.compareTo("SI") == 0) corto = "TRUE";
					else corto = "FALSE";
					if (botonLibros.isSelected())
					{
						String [] Datos1	 = {textTitulo.getText(),
												textAutores.getText(),
												textEditorial.getText(),
												textEdicion.getText(),
												textAnio.getText(),
												textPaginas.getText(),
												textSignatura.getText(),
												textISBN.getText(),
												textCopiasSala.getText(),
												textCopiasPrestamo.getText(),
												corto,
												};
						Datos = Datos1;
					}
					else
					{
						String [] Datos2 	 = {textTitulo.getText(),
												textAutores.getText(),
												textEditorial.getText(),
												textVolumen.getText(),
												textAnio.getText(),
												textPaginas.getText(),
												textSignatura.getText(),
												textNumero.getText(),
												textCopiasSala.getText(),
												textCopiasPrestamo.getText(),
												corto,
												};
						Datos = Datos2;
						
					}
					/** Creo el objeto de tipo RolSuperior al que le enviaré los Datos*/
					for(int i=0;i<Datos.length;i++) if(Datos[i].compareTo("") == 0) faltanDatos = true; 
					
					/** Creo el objeto de tipo RolSuperior al que le enviaré los Datos*/
					if(!faltanDatos)
					{
						RolSuperior dejarDatos = new RolSuperior();
						dejarDatos.setDatosFondos(Datos,botonLibros.isSelected());
						JOptionPane.showMessageDialog(textControlsPane, "El fondo se añadió correctamente.", "Gestor Biblioteca", JOptionPane.INFORMATION_MESSAGE);
					}
					else JOptionPane.showMessageDialog(textControlsPane, "Debe rellenar todos los campos", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
					
		 
				}
				else if ("LIBROS".equals(e.getActionCommand()))
				{ //quit
		        	    textISBN.setEnabled(true);
		        	    textEdicion.setEnabled(true);
		        	    textVolumen.setEnabled(false);
		        	    textVolumen.setText("");
		        	    textNumero.setEnabled(false);
		        	    textNumero.setText("");
				}
				else if ("REVISTAS".equals(e.getActionCommand()))
				{ //quit
		        	    textISBN.setEnabled(false);
		        	    textISBN.setText("");
		        	    textEdicion.setEnabled(false);
		        	    textEdicion.setText("");
		        	    textVolumen.setEnabled(true);
		        	    textNumero.setEnabled(true);
				}
				else if(CANCELAR.equals(e.getActionCommand()))
				{ //quit
					dispose();
				}

		    }
	}

}
