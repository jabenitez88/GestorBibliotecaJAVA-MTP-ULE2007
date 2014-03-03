import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;


public class VentanaMensajeEntrada extends JFrame 
{
	static final int xOffset = 30, yOffset = 30;
	static final String ACEPTAR = "ACEPTAR PRESTAMO";
	static final String DENEGAR = "DENEGAR PRESTAMO";
	boolean paraPrestamo;
	public VentanaMensajeEntrada(String idmensaje,boolean esprestamo)
	{
		super("Mensaje");
		paraPrestamo = esprestamo;
			//...Create the GUI and put it in the window...
			MensajeUsuario mensaje = new MensajeUsuario(idmensaje);
			//System.out.println(idmensaje);
			add(mensaje);
			
			//Display the window.
			//pack();
			setVisible(true);

			//...Then set the window size or call pack...
			setSize(755,508);

			//Set the window's location.
			setLocation(xOffset, yOffset);
	}
	class MensajeUsuario extends JPanel implements ActionListener
	{

		/** Declaración de variables */
		
		/** Texto que muestra labelRemitente */
	    protected static final String stringRemitente	= "Remitente: ";

		/** Texto que muestra labelFecha*/
		protected static final String stringFecha		= "Fecha: ";

		/** Texto que muestra labelAsunto */
		protected static final String stringAsunto		= "Asunto: ";

		/** Texto que muestra labelMensaje */
		protected static final String stringMensaje		= "Mensaje: ";

		/** Campo de texto para introducir el Remitente */
		JTextField textRemitente;

		/** Campo de texto para introducir los Fecha */
		JTextField textFecha;

		/** Campo de texto para introducir el Asunto */
		JTextField textAsunto;

		/** Campo de texto para introducir el Mensaje */
		JTextArea textMensaje;

		/** Etiqueta Nombre */
		JLabel labelRemitente;

		/** Etiqueta Apellidos */
		JLabel labelFecha;

		/** Etiqueta DNI */
		JLabel labelAsunto;

		/** Etiqueta Password */
		JLabel labelMensaje;

		/** Método que crea el formulario de Insertar Usuario */
		public  MensajeUsuario(String idmensaje) 
		{
			//Establecemos el tipo de layout
			setLayout(new BorderLayout());
			/* Creación de lo campos de texto */


			
			/* Creación de las etiquetas... */
			RolSuperior cogeDatosMensaje = new RolSuperior();
			String[] losDatos = cogeDatosMensaje.getDatosMensaje(idmensaje);
			
			//Campo de texto de Remitente
			textRemitente = new JTextField(losDatos[0]);
			textRemitente.setEditable(false);
			//Campo de texto de Fecha
			textFecha = new JTextField(losDatos[1],10);
			textFecha.setEditable(false);
			
			//Campo de texto de Asunto
			textAsunto = new JTextField(losDatos[2]);
			textAsunto.setEditable(false);
			
			//Campo de texto de Mensaje
			textMensaje = new JTextArea(losDatos[3],20,60);
			textMensaje.setWrapStyleWord(true);
			textMensaje.setEditable(false);
			textMensaje.setLineWrap(true);
			
			//Etiqueta para Remitente
			labelRemitente = new JLabel(stringRemitente);
	        labelRemitente.setLabelFor(textRemitente);

			//Etiqueta para Fecha
			labelFecha = new JLabel(stringFecha);
	        labelFecha.setLabelFor(textFecha);
			
			//Etiqueta de Asunto
			labelAsunto = new JLabel(stringAsunto);
	        labelAsunto.setLabelFor(textAsunto);

			//Etiqueta de Mensaje
			labelMensaje = new JLabel(stringMensaje);
	        labelMensaje.setLabelFor(textMensaje);

		    /* Creamos un layout propio para los controles anteriores */
			JPanel textControlsPane = new JPanel();
	        GridBagLayout gridbag = new GridBagLayout();
		    GridBagConstraints c = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane.setLayout(gridbag);

			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...
			JLabel[] labels = {labelRemitente, labelFecha, labelAsunto};
		    JTextField[] textFields = {textRemitente, textFecha, textAsunto};
			addLabelTextRows(labels, textFields, gridbag, textControlsPane);
			
			//Establecemos la rejilla

			c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
			c.fill = GridBagConstraints.NONE;      //reset to default
			c.weightx = 0.0;                       //reset to default
			textControlsPane.add(labelMensaje, c);

			c.gridwidth = GridBagConstraints.REMAINDER;     //end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			textControlsPane.add(textMensaje, c);
			
			//Propiedades de la rejilla
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
		    c.anchor = GridBagConstraints.EAST;
			c.weightx = 10.0;
			
			//Situados los demás elementos, colocamos la etiqueta informativa
		    textControlsPane.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Mensaje"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));

			//Agrupamos los distintos paneles creados (uno en este caso)
			JPanel leftPane = new JPanel(new BorderLayout());

			leftPane.add(textControlsPane, BorderLayout.PAGE_START);
		    //##########################################################//
		    //##########################################################//
			/*if(paraPrestamo)
			{

				JPanel botonesAccion = new JPanel();
	    		// Creo el bot�n de Modificar.
	    		JButton aceptarButton = new JButton(ACEPTAR);
	    		JButton denegarButton = new JButton(DENEGAR);

	    		// Asigno un nombre para los botones
	    		aceptarButton.setActionCommand(ACEPTAR);
	    		denegarButton.setActionCommand(DENEGAR);

	    		// Asigno la acción para los botones Aceptar y Cancelar.
	    		aceptarButton.addActionListener(this);
	    		denegarButton.addActionListener(this);
	    		
				botonesAccion.add(aceptarButton,c);
				botonesAccion.add(denegarButton,c);
				botonesAccion.setBorder(
						BorderFactory.createCompoundBorder(
	                            BorderFactory.createTitledBorder(""),
	                            BorderFactory.createEmptyBorder(5,5,5,5))); 
				leftPane.add(botonesAccion,BorderLayout.PAGE_END);
				
			}*/

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

			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
	}
}
