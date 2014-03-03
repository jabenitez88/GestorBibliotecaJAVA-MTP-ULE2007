/**
 * Programa : Gestor de una Biblioteca
 * Autor 	: Jose Alberto Benitez Andrades
 * Asignatura: Metodolog�a y Tecnolog�a de la Progamaci�n
 * Fecha 	: 25/06/2008
 * Versi�n  : 5.27
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/** class MiLogin 
 * Esta clase se encarga de mostrar la ventana de Login
 * para que el usuario introduzca usuario y contraseña.
 * */
public class MiLogin extends JPanel 
	{

		/** Declaracion de variables */
	    /** framePpal es el frame principal */
		static JFrame framePpal;
		/** Texto que muestra labelUsuario */
	    protected static final String stringUsuario	= "Usuario: ";

		/** Texto que muestra labelPassword */
		protected static final String stringPassword	= "Contrasena: ";
		/** Vacio es una constante que guarda el carácter vacío */
		protected static final String VACIO	= "";

		/** Texto Aceptar para el boton aceptar */
		protected static String ACEPTAR = "Aceptar";
		/** Texto Cancelar para el boton cancelar */
		protected static String CANCELAR = "Cancelar";

		/** Campo de texto para introducir el usuario */
		 JTextField textUsuario;
		
		/** Campo de texto para introducir el password */
		 JPasswordField textPassword;

		/** Etiqueta Usuario */
		 JLabel labelUsuario;

		 /** Etiqueta Password */
		 JLabel labelPassword;

		/** Etiqueta de Informacion */
		 JLabel labelInfo;

		/** Etiqueta accion realizada */
		 JLabel labelAccion;

		/** Panel que contiene los botones Aceptar y Cancelar */
        JComponent buttonPanel;
        	
        /** Panel que contiene los TextField de usuario y contraseña y el botón de aceptar */
		JPanel textControlsPane = new JPanel();

		public MiLogin() 
		{	
			//setMinimumSize(new java.awt.Dimension(200,400));  
			//setMaximumSize(new java.awt.Dimension(200,400));  
			setPreferredSize(new java.awt.Dimension(250,200));
			//Establecemos el tipo de layout
			setLayout(new BorderLayout());

			//Etiqueta de informacion
	        labelInfo = new JLabel("Introduzca su usuario y contraseña.");

			// Etiqueta de accion
	        labelAccion = new JLabel("");
		   	// labelAccion.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

			/* Creacion de lo campos de texto */

			//Campo de texto de Usuario
			textUsuario = new JTextField(8);
			textUsuario.setActionCommand(stringUsuario);
			
			//Campo de texto de Password
			textPassword = new JPasswordField(10);
		    textPassword.setActionCommand(stringPassword);

			/* Creacion de las etiquetas... */

			//Etiqueta para Usuario
			labelUsuario = new JLabel(stringUsuario);
	        labelUsuario.setLabelFor(textUsuario);

			//Etiqueta de Password
			labelPassword = new JLabel(stringPassword);
	        labelPassword.setLabelFor(textPassword);

			// Crea el panel de botones
			buttonPanel =  createButtonPanel();

		    /* Creamos un layout propio para los controles anteriores */
	       	GridBagLayout gridbag = new GridBagLayout();
		    GridBagConstraints c = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane.setLayout(gridbag);

			//Por comodidad, creamos una funcion que aniada los controles a la rejilla
			//y al panel que la contiene...
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
		    c.anchor = GridBagConstraints.EAST;
			c.weightx = 10.0;
			
			//Agregamos la etiqueta labelInfo al panel.
	        textControlsPane.add(labelInfo,c);
			JLabel[] labels = {labelUsuario, labelPassword};
		    JTextField[] textFields = {textUsuario, textPassword};
		    //Colocamos en el panel las etiquetas y los textfields.
			addLabelTextRows(labels, textFields, gridbag, textControlsPane);
        			//Agregamos al panel los botones.
			textControlsPane.add(buttonPanel,c);
			textControlsPane.add(labelAccion,c);
			//Situados los demas elementos, colocamos la etiqueta informativa

		    textControlsPane.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Indentificación usuario"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));  
		    // A�adimos el panel en la zona "LINE_START" al principio...
			add(textControlsPane, BorderLayout.LINE_START);
            setLocation(200, 200);

		}//fin_MiLogin()
		/** Con este procedimiento cre el panel de botones "Aceptar y Cancelar" */
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
        		aceptarButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{	// Si ha sido presionado el OK entonces entra aquí.
						if (ACEPTAR.equals(e.getActionCommand())) 
						{ 	// Creo un Objeto de tipo Mysql, para poder autentificarme en la base
							// de datos.
							MiMysql autentificando =  new MiMysql();

							// Asigno a DNI el valor que ha introducido el usuario en Usuario.
							String ID = textUsuario.getText();
 
							// Asigno a pass la contrasena que introdujo el usuario en Contrasena.
							String pass = new String(textPassword.getPassword());

							// Ahora hago la búsqueda en la base de datos de la password del
							// Usuario con el DNI que ha introducido y lo guardo en pass2.
							String pass2 = new String(autentificando.Login(ID,true));

							// Creo la String que almacenará el nivel del usuario
							String Nivel = new String(autentificando.Login(ID,false));


							// Y aqui las comparo, si son correctas, abrira una ventana nueva, sino
							// informara al usuario de lo que ha escrito mal.
							//System.out.println(pass + " " + pass2);
							if((pass.compareTo(pass2) == 0) && (pass.compareTo(VACIO) != 0))
							{	
								//System.out.println("Correcto!!!!");
								//VentanaPrincipal vPrincipal = new VentanaPrincipal(Nivel);

        							//Make sure we have nice window decorations.
        							JFrame.setDefaultLookAndFeelDecorated(true);

        							//Create and set up the window.
        							VentanaPrincipal frame = new VentanaPrincipal(ID,Nivel);
        							frame.addWindowListener(new WindowListener(){
        								public void windowClosed(WindowEvent arg0) {
        									framePpal.setVisible(true);
        						            textUsuario.setText("");
        						            textPassword.setText("");
        								}
        								public void windowActivated(WindowEvent arg0){}
        						        public void windowClosing(WindowEvent arg0) {
        						           	framePpal.setVisible(true);
          						            textUsuario.setText("");
        						            textPassword.setText("");
        						            }
        						        public void windowDeactivated(WindowEvent arg0) {}
        						        public void windowDeiconified(WindowEvent arg0) {}
        						        public void windowIconified(WindowEvent arg0) {}
        						        public void windowOpened(WindowEvent arg0){}
        							});
        							//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        							//Display the window.
        							frame.setVisible(true);
        							if ((Nivel.compareTo("Administrador")	 == 0)
        					        		|| (Nivel.compareTo("Tecnico") 	 == 0))RolSuperior.comprobacionesIniciales();
        					        else {
        					        	 RolSuperior.comprobacionesIniciales();
        					           	 MiMysql.comprobarReservasLector(ID);
        					        }
        							framePpal.setVisible(false);
							}
							else if (pass2.compareTo(VACIO) == 0) 	JOptionPane.showMessageDialog(textControlsPane, "No existe ese usuario en la base de datos", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
								//labelAccion.setText("No existe usuario con DNI "+ DNI + " en la db");
							else JOptionPane.showMessageDialog(textControlsPane, "Contraseña incorrecta", "Gestor Biblioteca", JOptionPane.ERROR_MESSAGE);
							
						}

					}//fin_actionPerformed
				});
        		cancelarButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (CANCELAR.equals(e.getActionCommand())) 
						{
							System.exit(0);
						}

					}//fin_actionPerformed
				});
        		// Agrego los botones al panel.
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
    	/** Esta función sirve para colocar en un panel las etiquetas y los textfield de forma ordenada */
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
		 /** Esta función es la que crea la interfaz gráfica realmente*/
		  private static void createAndShowGUI() {
				//Create and set up the window.
				framePpal = new JFrame("Gestor de Biblioteca - JABAGB 0.4b");
				framePpal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				//Add content to the window.
				framePpal.add(new MiLogin());

				//Display the window.
				framePpal.pack();
				framePpal.setVisible(true);
			}

		/** Función principal */
		public static void main(String[] args) throws Exception
		{	  // Creo el objeto MiMysql que usaré para conectar a la base de datos 
			  MiMysql biblioteca =  new MiMysql();
			  biblioteca.ConectaDB();
			  SwingUtilities.invokeLater(new Runnable() {
			     public void run() {
					UIManager.put("swing.boldMetal", Boolean.FALSE);
					createAndShowGUI();
				 }
			  });
			
		}
}
