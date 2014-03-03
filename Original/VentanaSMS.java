import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;


public class VentanaSMS extends JFrame
{
	static final int xOffset = 30, yOffset = 30;
	public VentanaSMS(String id)
	{
		super("SMS Nuevo");
			
			//...Create the GUI and put it in the window...
			MensajeUsuario mensaje = new MensajeUsuario(id);
			//System.out.println(id);
			add(mensaje);
			
			//Display the window.
			//pack();
			setVisible(true);

			//...Then set the window size or call pack...
			setSize(790,500);

			//Set the window's location.
			setLocation(xOffset, yOffset);
	}
	class MensajeUsuario extends JPanel implements ActionListener
	{

		/** Declaración de variables */
		
		/** Texto que muestra labelRemitente */
	    protected static final String stringRemitente	= "Remitente: ";

		/** Texto que muestra labelFecha*/
		protected static final String stringReceptor		= "Receptor: ";


		/** Texto que muestra labelMensaje */
		protected static final String stringMensaje		= "Mensaje: ";

		/** Campo de texto para introducir el Remitente */
		JTextField textRemitente;

		/** Campo de texto para introducir los Fecha */
		JTextField textReceptor;


		/** Campo de texto para introducir el Mensaje */
		JTextArea textMensaje;

		/** Etiqueta Nombre */
		JLabel labelReceptor;

		/** Etiqueta Apellidos */
		JLabel labelFecha;

		/** Etiqueta Password */
		JLabel labelMensaje;
		
		/** Etiqueta Tecnico */
		JLabel labelCaracteres;
		
		JPanel textControlsPane;
		

		/** Método que crea el formulario de Insertar Usuario */
		public  MensajeUsuario(String id) 
		{
			//Establecemos el tipo de layout
			setLayout(new BorderLayout());
			/* Creación de lo campos de texto */


			MiMysql cogeCorreo = new MiMysql();
			String correo = cogeCorreo.getCadena("SELECT Telefono from Usuarios where Nsocio = "+id);
			/* Creación de las etiquetas... */
			//RolSuperior cogeDatosMensaje = new RolSuperior();
			//String[] losDatos = cogeDatosMensaje.getDatosMensaje(idmensaje);
			
			//Campo de texto de Remitente
			textRemitente = new JTextField(correo,25);
			textRemitente.setEditable(false);
			//Campo de texto de Fecha
			textReceptor = new JTextField(25);
			textReceptor.setEditable(true);
			textReceptor.setDocument(new controlarLontigud(14,true)); 
			
			
			//Campo de texto de Mensaje
			textMensaje = new JTextArea(20,60);
			textMensaje.setWrapStyleWord(true);
			textMensaje.setLineWrap(true);
			textMensaje.setEditable(true);
      		ImageIcon iconoEnviar	= createImageIcon("images/001_06.png");
			JButton enviarMensaje = new JButton("Enviar",iconoEnviar);
			//enviarMensaje.addActionListener();
			enviarMensaje.addActionListener(this);
			enviarMensaje.setActionCommand("ENVIAR");
			
			//Etiqueta para Remitente
			JLabel labelRemitente = new JLabel(stringRemitente);
	        labelRemitente.setLabelFor(textRemitente);

			//Etiqueta para Fecha
			labelReceptor = new JLabel(stringReceptor);
	        labelReceptor.setLabelFor(textReceptor);
	        

			//Etiqueta de Mensaje
			labelMensaje = new JLabel(stringMensaje);
	        labelMensaje.setLabelFor(textMensaje);
	        labelCaracteres = new JLabel("0 caracteres escritos / 1 sms");
	        textMensaje.getDocument().addDocumentListener(
        			new javax.swing.event.DocumentListener() {
        				public void changedUpdate(javax.swing.event.DocumentEvent e) {
        					textMensaje.getText().length();
        					int smsEscritos = (textMensaje.getText().length() / 160)+1;
        					labelCaracteres.setText(textMensaje.getText().length() + " caracteres escritos / "+smsEscritos+" sms escritos");
        				}
        				public void removeUpdate(javax.swing.event.DocumentEvent e) {
        					textMensaje.getText().length();
        					int smsEscritos = (textMensaje.getText().length() / 160)+1;
        					labelCaracteres.setText(textMensaje.getText().length() + " caracteres escritos / "+smsEscritos+" sms escritos");
        				}
        				public void insertUpdate(javax.swing.event.DocumentEvent e) {
        					textMensaje.getText().length();	
        					int smsEscritos = (textMensaje.getText().length() / 160)+1;
        					labelCaracteres.setText(textMensaje.getText().length() + " caracteres escritos / "+smsEscritos+" sms escritos");

        				}
        			}); 

		    /* Creamos un layout propio para los controles anteriores */
			textControlsPane = new JPanel();
	        GridBagLayout gridbag = new GridBagLayout();
		    GridBagConstraints c = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane.setLayout(gridbag);

			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...
			JLabel[] labels = {labelRemitente, labelReceptor};
		    JTextField[] textFields = {textRemitente, textReceptor};
			addLabelTextRows(labels, textFields, gridbag, textControlsPane);
			
			//Establecemos la rejilla
			c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
			c.fill = GridBagConstraints.NONE;      //reset to default
			c.weightx = 0.0;                       //reset to default

		    c.anchor = GridBagConstraints.WEST;
			c.gridwidth = GridBagConstraints.REMAINDER;     //end row
			c.fill = GridBagConstraints.NONE;
			c.weightx = 1.0;

		    c.anchor = GridBagConstraints.CENTER;
			c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
			c.fill = GridBagConstraints.NONE;      //reset to default
			c.weightx = 0.0;                       //reset to default
			textControlsPane.add(labelMensaje, c);

			c.gridwidth = GridBagConstraints.REMAINDER;     //end row
			c.fill = GridBagConstraints.NONE;
			c.weightx = 1.0;
			textControlsPane.add(textMensaje, c);
			
			//Propiedades de la rejilla
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
		    c.anchor = GridBagConstraints.EAST;
			c.weightx = 10.0;
			textControlsPane.add(labelCaracteres,c);
			textControlsPane.add(enviarMensaje,c);
			//Situados los demás elementos, colocamos la etiqueta informativa
		    textControlsPane.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Mensaje de Salida"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
			


			//Agrupamos los distintos paneles creados (uno en este caso)
			JPanel leftPane = new JPanel(new BorderLayout());

			leftPane.add(textControlsPane, BorderLayout.PAGE_START);
			

			add(leftPane, BorderLayout.LINE_START);
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
			/** Funci�n que recibe los eventos */
			public void actionPerformed(ActionEvent e) 
			{
				
				
				if ("ENVIAR".equals(e.getActionCommand()))
				{


				}
			}
	}
}
