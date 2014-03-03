//package dbutils;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.event.*;
import java.awt.*;



/** 
 * Clase que crea la ventana principal del programa
 */
public class VentanaPrincipal extends JFrame
                               implements ActionListener {
    MainDesktopPane desktop;
    static String IDUsuario;
    static String Nivel;
    
    public VentanaPrincipal(String ID,String NivelUsuario) {
        super("Gestor de Biblioteca - JABAGB 5.27");
        IDUsuario = ID;
        Nivel = NivelUsuario;

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);

        //Set up the GUI.
        desktop = new MainDesktopPane(); //a specialized layered pane
        setContentPane(desktop);
        /* Dependiendo del nivel del usuario muestra una barra u otra*/
		if (NivelUsuario.compareTo("Administrador")	 == 0) setJMenuBar(createMenuBarAdmin());
		else if (NivelUsuario.compareTo("Tecnico") 	 == 0) setJMenuBar(createMenuBarTecnico());
		else if (NivelUsuario.compareTo("Lector-Socio")  == 0) setJMenuBar(createMenuBarLectorS());
		else if (NivelUsuario.compareTo("Lector-Basico") == 0) setJMenuBar(createMenuBarLectorB());

        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        
    }
    /** Funci�n que crea la barra de men� del administrador*/
    protected JMenuBar createMenuBarAdmin() {
        JMenuBar menuBar = new JMenuBar();

        //Crea los men�s de arriba y sus submen�s
        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menuArchivo);

        JMenuItem menuItem = new JMenuItem("Nuevo Usuario");
        menuItem.setMnemonic(KeyEvent.VK_U);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_U, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("nuevoUsuario");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);

        //Crea el men� item
        menuItem = new JMenuItem("Nuevo Fondo Bibliografico");
        menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("nuevoFondo");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Autentificarse como...");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("relogin");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Enviar SMS");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_B, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("sms");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Enviar Correo");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("email");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Salir");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);

        JMenu menuConsulta = new JMenu("Consulta");
        menuConsulta.setMnemonic(KeyEvent.VK_C);
        menuBar.add(menuConsulta);

 
        menuItem = new JMenuItem("Consultar Usuarios");
        menuItem.setMnemonic(KeyEvent.VK_U);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_U, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarUsuario");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);



        menuItem = new JMenuItem("Consultar Fondos");
        menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarFondo");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);
        

        menuItem = new JMenuItem("Consultar Prestamos");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarPrestamo");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);
        

        JMenu menuBuzon = new JMenu("Buzon");
        menuBuzon.setMnemonic(KeyEvent.VK_B);
        menuBar.add(menuBuzon);


        menuItem = new JMenuItem("Abrir Buzon");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("abrirBuzon");
        menuItem.addActionListener(this);
        menuBuzon.add(menuItem);
        

        JMenu menuComprobaciones = new JMenu("Comprobaciones");
        menuComprobaciones.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuComprobaciones);


        menuItem = new JMenuItem("Comprobar Reservas");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("comprobarReservas");
        menuItem.addActionListener(this);
        menuComprobaciones.add(menuItem);
 

        menuItem = new JMenuItem("Comprobar Caducados");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("comprobarCaducados");
        menuItem.addActionListener(this);
        menuComprobaciones.add(menuItem);
        
        
        JMenu menuEstadisticas = new JMenu("Estadisticas");
        menuEstadisticas.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuEstadisticas);

        
        menuItem = new JMenuItem("Estadisticas");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Estadisticas");
        menuItem.addActionListener(this);
        menuEstadisticas.add(menuItem);
        
        JMenu menuAcercaDe = new JMenu("Acerca de");
        menuAcercaDe.setMnemonic(KeyEvent.VK_I);
        menuBar.add(menuAcercaDe);

        
        menuItem = new JMenuItem("Sobre...");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Sobre");
        menuItem.addActionListener(this);
        menuAcercaDe.add(menuItem);
        
        return menuBar;
    }

    /** Funci�n que crea la barra de men� del tecnico*/
    protected JMenuBar createMenuBarTecnico() {
        JMenuBar menuBar = new JMenuBar();

        //Crea los men�s de arriba y sus submen�s
        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menuArchivo);
        

        JMenuItem menuItem = new JMenuItem("Nuevo Usuario");
        menuItem.setMnemonic(KeyEvent.VK_U);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_U, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("nuevoUsuario");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);

        menuItem = new JMenuItem("Nuevo Fondo Bibliografico");
        menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("nuevoFondo");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Autentificarse como...");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("relogin");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Enviar SMS");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_B, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("sms");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Enviar Correo");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("email");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);

        menuItem = new JMenuItem("Salir");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);


        JMenu menuConsulta = new JMenu("Consulta");
        menuConsulta.setMnemonic(KeyEvent.VK_C);
        menuBar.add(menuConsulta);

        menuItem = new JMenuItem("Consultar Usuarios");
        menuItem.setMnemonic(KeyEvent.VK_U);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_U, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarUsuario");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);


        menuItem = new JMenuItem("Consultar Fondos");
        menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarFondo");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);
 
        menuItem = new JMenuItem("Consultar Prestamos");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarPrestamo");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);
        
        JMenu menuBuzon = new JMenu("Buzon");
        menuBuzon.setMnemonic(KeyEvent.VK_B);
        menuBar.add(menuBuzon);

        menuItem = new JMenuItem("Abrir Buzon");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("abrirBuzon");
        menuItem.addActionListener(this);
        menuBuzon.add(menuItem);
        
        JMenu menuComprobaciones = new JMenu("Comprobaciones");
        menuComprobaciones.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuComprobaciones);

        menuItem = new JMenuItem("Comprobar Reservas");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("comprobarReservas");
        menuItem.addActionListener(this);
        menuComprobaciones.add(menuItem);
        
        menuItem = new JMenuItem("Comprobar Caducados");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("comprobarCaducados");
        menuItem.addActionListener(this);
        menuComprobaciones.add(menuItem);
        
        JMenu menuEstadisticas = new JMenu("Estadisticas");
        menuEstadisticas.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuEstadisticas);

        
        menuItem = new JMenuItem("Estadisticas");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Estadisticas");
        menuItem.addActionListener(this);
        menuEstadisticas.add(menuItem);
        
        JMenu menuAcercaDe = new JMenu("Acerca de");
        menuAcercaDe.setMnemonic(KeyEvent.VK_I);
        menuBar.add(menuAcercaDe);

        
        menuItem = new JMenuItem("Sobre...");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Sobre");
        menuItem.addActionListener(this);
        menuAcercaDe.add(menuItem);
        return menuBar;
	}


    /** Funcion que crea la barra de men� para lectores socios */
    protected JMenuBar createMenuBarLectorS() {
        JMenuBar menuBar = new JMenuBar();
        
        //Crea los men�s de arriba y sus submen�s
        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menuArchivo);
        
        JMenuItem menuItem = new JMenuItem("Autentificarse como...");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("relogin");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Enviar SMS");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_B, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("sms");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Enviar Correo");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("email");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);

        menuItem = new JMenuItem("Salir");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);


        JMenu menuConsulta = new JMenu("Consulta");
        menuConsulta.setMnemonic(KeyEvent.VK_C);
        menuBar.add(menuConsulta);


        menuItem = new JMenuItem("Consultar Fondos");
        menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarFondo");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);
        
        menuItem = new JMenuItem("Consultar Prestamos");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarPrestamo");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);
        
        JMenu menuBuzon = new JMenu("Buzon");
        menuBuzon.setMnemonic(KeyEvent.VK_B);
        menuBar.add(menuBuzon);

        menuItem = new JMenuItem("Abrir Buzon");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("abrirBuzon");
        menuItem.addActionListener(this);
        menuBuzon.add(menuItem);
        
        
        JMenu menuComprobaciones = new JMenu("Comprobaciones");
        menuComprobaciones.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuComprobaciones);

        menuItem = new JMenuItem("Comprobar Reservas");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("comprobarReservasLector");
        menuItem.addActionListener(this);
        menuComprobaciones.add(menuItem);
        
        JMenu menuEstadisticas = new JMenu("Estadisticas");
        menuEstadisticas.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuEstadisticas);

        
        menuItem = new JMenuItem("Estadisticas");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Estadisticas");
        menuItem.addActionListener(this);
        menuEstadisticas.add(menuItem);
        
        JMenu menuAcercaDe = new JMenu("Acerca de");
        menuAcercaDe.setMnemonic(KeyEvent.VK_I);
        menuBar.add(menuAcercaDe);

        
        menuItem = new JMenuItem("Sobre...");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Sobre");
        menuItem.addActionListener(this);
        menuAcercaDe.add(menuItem);
        
        return menuBar;
    }
    /** Funcion que crea la barra de men� para lectores b�sicos */
    protected JMenuBar createMenuBarLectorB() {
        JMenuBar menuBar = new JMenuBar();

        //Crea los men�s de arriba y sus submen�s
        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menuArchivo);

        JMenuItem menuItem = new JMenuItem("Autentificarse como...");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("relogin");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Enviar SMS");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_B, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("sms");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Enviar Correo");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("email");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);
        
        menuItem = new JMenuItem("Salir");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menuArchivo.add(menuItem);


        JMenu menuConsulta = new JMenu("Consulta");
        menuConsulta.setMnemonic(KeyEvent.VK_C);
        menuBar.add(menuConsulta);


        menuItem = new JMenuItem("Consultar Fondos");
        menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarFondo");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);
 
        menuItem = new JMenuItem("Consultar Prestamos");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("consultarPrestamo");
        menuItem.addActionListener(this);
        menuConsulta.add(menuItem);
        
        JMenu menuBuzon = new JMenu("Buzon");
        menuBuzon.setMnemonic(KeyEvent.VK_B);
        menuBar.add(menuBuzon);

        menuItem = new JMenuItem("Abrir Buzon");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("abrirBuzon");
        menuItem.addActionListener(this);
        menuBuzon.add(menuItem);
        
        JMenu menuComprobaciones = new JMenu("Comprobaciones");
        menuComprobaciones.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuComprobaciones);

        menuItem = new JMenuItem("Comprobar Reservas");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("comprobarReservasLector");
        menuItem.addActionListener(this);
        menuComprobaciones.add(menuItem);
        
        JMenu menuEstadisticas = new JMenu("Estadisticas");
        menuEstadisticas.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menuEstadisticas);

        
        menuItem = new JMenuItem("Estadisticas");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Estadisticas");
        menuItem.addActionListener(this);
        menuEstadisticas.add(menuItem);
        
        JMenu menuAcercaDe = new JMenu("Acerca de");
        menuAcercaDe.setMnemonic(KeyEvent.VK_I);
        menuBar.add(menuAcercaDe);

        
        menuItem = new JMenuItem("Sobre...");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Sobre");
        menuItem.addActionListener(this);
        menuAcercaDe.add(menuItem);

        return menuBar;
    }

    /** Funci�n que recoge los eventos pulsados */
    public void actionPerformed(ActionEvent e) {
    	
        if ( "nuevoUsuario".equals( e.getActionCommand() ) )
		{ 
        	VentanaInsertarUsuario frame = new VentanaInsertarUsuario(Nivel);
        	frame.setVisible(true);
        	desktop.add(frame);
        } 
        else if ("consultarUsuario".equals(e.getActionCommand() ) )
        {
        	VentanaConsultarUsuario frame = new VentanaConsultarUsuario(Nivel);
        	frame.setVisible(true);
        	desktop.add(frame);
        }
        else if ("nuevoFondo".equals(e.getActionCommand() ) )
        {
        	VentanaInsertarFondo frame = new VentanaInsertarFondo();
        	frame.setVisible(true);
        	desktop.add(frame);
        }
        else if ("consultarFondo".equals(e.getActionCommand() ) )
        {
        	VentanaConsultarFondo frame = new VentanaConsultarFondo(Nivel,IDUsuario);
        	frame.setVisible(true);
        	desktop.add(frame);
        }
        else if ("consultarPrestamo".equals(e.getActionCommand() ) )
        {
        	VentanaConsultarPrestamo frame = new VentanaConsultarPrestamo(Nivel,IDUsuario);
        	frame.setVisible(true);
        	desktop.add(frame);
        }
        else if ("abrirBuzon".equals(e.getActionCommand() ) )
        {
        	VentanaBuzon frame = new VentanaBuzon(IDUsuario,Nivel);
        	frame.setVisible(true);
        	desktop.add(frame);
        }
        else if("comprobarCaducados".equals(e.getActionCommand() ))
        {
        	MiMysql comp = new MiMysql();
        	comp.comprobarReservas();
        }
        else if("comprobarReservas".equals(e.getActionCommand() ))
        {
        	MiMysql comp = new MiMysql();
        	comp.comprobarCaducados();
        }
        else if("comprobarReservasLector".equals(e.getActionCommand() ))
        {
            MiMysql.comprobarReservasLector(IDUsuario);
        }
        else if("Estadisticas".equals(e.getActionCommand() ))
        {
            VentanaEstadisticas frame = new VentanaEstadisticas(Nivel,IDUsuario);
        	frame.setVisible(true);
        	desktop.add(frame);
        }
        else if("sms".equals(e.getActionCommand()))
        { 
        	 VentanaSMS frame = new VentanaSMS(IDUsuario);
        	frame.setVisible(true);
        	desktop.add(frame);
        }
        else if("email".equals(e.getActionCommand()))
        { 
        	VentanaEmail frame = new VentanaEmail(IDUsuario);
        	frame.setVisible(true);
        	desktop.add(frame);
        }
        else if("Sobre".equals(e.getActionCommand()))
        { //quit
        	JPanel textControlsPane = new JPanel();
        	JOptionPane.showMessageDialog(textControlsPane, "Autor: Jose Alberto Benitez Andrades\n"
					+"Asignatura: Metodología y Tecnología de la Programación\n"
					+"Programa: Gestor de una Biblioteca - Junio 2008", "Gestor Biblioteca", JOptionPane.INFORMATION_MESSAGE);
        }
        else if("relogin".equals(e.getActionCommand()))
        { //quit
        	dispose();
        }
        else if("quit".equals(e.getActionCommand()))
        { //quit
        	System.exit(0);
        }

    }

    protected void createFrame1() {

    	VentanaInsertarUsuario frame = new VentanaInsertarUsuario(Nivel);
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }

    //Quit the application.
    protected void quit() {
        System.exit(0);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    /*public static void abrirVentanaPrincipal(String Nivel) {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        VentanaPrincipal frame = new VentanaPrincipal();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
    }*/

    /*public static void main(String[] args) throws Exception {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
	MyBiblioDB biblioteca =  new MyBiblioDB();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //createAndShowGUI();
            }
        });
    }*/
    class MainDesktopPane extends JDesktopPane{ 

        private Image image; 
         
        public MainDesktopPane() { 
            super(); 
            image = new ImageIcon(getClass().getResource("/images/fondo.png")).getImage(); 
            setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
            setDoubleBuffered(true);
            setBackground(new Color(255, 255, 255)); 
        } 

        protected void paintComponent(Graphics g) { 
            super.paintComponent(g); 
            int h = (int)image.getHeight(null)/2; 
            int w = (int)(image.getWidth(null)/2); 
            g.drawImage(image, (int)getWidth()/2 - w, (int)getHeight()/2 - h, null);
            
        } 
    } 
}
