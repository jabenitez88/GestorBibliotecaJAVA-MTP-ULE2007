import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
/** Clase que muestra la ventana del buzón de mensajes */
public class VentanaBuzon extends JInternalFrame
{   
    static String NivelUsuario;
	public VentanaBuzon(String NSOCIO,String Nivel) 
	{
		super("Buzon de Correo", 
			      true, //resizable
			      true, //closable
			      false, //maximizable
			      true);//iconifiable
			
				//...Create the GUI and put it in the window...
			
			NivelUsuario = Nivel;
			SharedModelDemo buzon = new SharedModelDemo(NSOCIO,NivelUsuario);

			add(buzon);

			//Muestra la ventana
				pack();
			setVisible(true);

			// Asigno el tamaño de la ventana
			setSize(650,600);

			//Elijo la localización de la ventana.
			//setLocation(xOffset, yOffset);
	
	}
	class SharedModelDemo extends JPanel 
	 					implements ActionListener{
	    JList list; 
	    JTable table;
	    String newline = "\n";
	    ListSelectionModel listSelectionModel;
	    String NivelUsuario;
	    String cogeMensajesDe;
	    String Socio;
	    String SocioInit;
	    JButton borrarButton;
	    String queBuzon;
	    boolean esTecnico = false;
	    
	    public SharedModelDemo(String NSOCIO,String Nivel) {
	        super(new BorderLayout());
	        NivelUsuario = Nivel;
	        /* Creo un objeto MiMysql para poder llamar a devuelveMensajes
	         que me devolverá el modelo que necesitamos para la tabla donde 
	         veremos todos los mensajes.
	         */
	        Socio = NSOCIO;
	        SocioInit = NSOCIO;
	        queBuzon = "Buzon";
	        cogeMensajesDe = "id_receptor = " + Socio;
	        MiMysql devuelveMensajes = new MiMysql();
	        ModeloTablaBuzon modelo = devuelveMensajes.ConsultaMensajesBuzon(queBuzon,cogeMensajesDe,esTecnico);

	        list = new JList(modelo);
	        list.setCellRenderer(new DefaultListCellRenderer() {
	            public Component getListCellRendererComponent(JList l, 
	                                                          Object value,
	                                                          int i,
	                                                          boolean s,
	                                                          boolean f) {
	                String[] array = (String[])value;
	                return super.getListCellRendererComponent(l,
	                                                          array[0],
	                                                          i, s, f);
	            }
	        });
	        // Asigno el tipo de selección para la tabla.
	        listSelectionModel = list.getSelectionModel();
	        listSelectionModel.setSelectionMode(
	                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	

	        table = new JTable(modelo);
	        table.setSelectionModel(listSelectionModel);
	        // Agrego eventos de ratón para la tabla.
	        table.addMouseListener(new MouseAdapter()
	        {
	                public void mouseClicked(MouseEvent e)
	                {		int[] seleccionadas = table.getSelectedRows();
	                        if (e.getClickCount() == 2)	
	                        {
	                        boolean prestamo = false;
	                        if (table.getColumnCount() == 7)
	                        {
	                        	if (table.getValueAt(seleccionadas[0], 6).toString().compareTo("SI") == 0) prestamo = true;
	                        }
	                        VentanaMensajeEntrada  vMensaje = new VentanaMensajeEntrada((String)table.getValueAt(seleccionadas[0], 0),prestamo);
	                        if(queBuzon.compareTo("Buzon")==0) 
	                        {	
	                        		table.setValueAt("NO",seleccionadas[0], 5);
	    	                        MiMysql actualizarMens = new MiMysql();
	    	                        actualizarMens.LlamadaDB("UPDATE Buzon SET NoNuevo = true;");
	                        }

	                        }

	                }
	        }); 
	        JScrollPane tablePane = new JScrollPane(table);

	        //JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);;
	        JPanel panelPpal = new JPanel(new BorderLayout());
	        add(panelPpal);

	        JComponent 	buttonPanel = createToolBar();
	        
	        JPanel topHalf = new JPanel();
	        topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.X_AXIS));
	        JPanel tableContainer = new JPanel(new GridLayout(1,1));
	        tableContainer.setBorder(BorderFactory.createTitledBorder(
	                                                "Mensajes"));
	        tableContainer.add(tablePane);
	        tablePane.setPreferredSize(new Dimension(300, 100));
	        topHalf.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
	        topHalf.add(tableContainer);

	        topHalf.setMinimumSize(new Dimension(400, 50));
	        topHalf.setPreferredSize(new Dimension(400, 110));
	        panelPpal.add(buttonPanel, BorderLayout.PAGE_START);
	        panelPpal.add(topHalf, BorderLayout.CENTER);


	    }
	    /** Creo la barra de botones superior */
  		protected JComponent createToolBar() 
   		{
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
    		//JPanel p = new JPanel(new GridLayout(1,0));
  			JToolBar p = new JToolBar();
    		ImageIcon iconoActualizar 	= createImageIcon("images/iconoActualizar.png");
    		ImageIcon iconoBandeja 	  	= createImageIcon("images/001_12.png");
    		ImageIcon iconoEnviados		= createImageIcon("images/001_13.png");
    		ImageIcon iconoEnviar		= createImageIcon("images/001_01.png");
    		ImageIcon iconoCambiar		= createImageIcon("images/001_43.png");
    		ImageIcon iconoBorrar		= createImageIcon("images/001_05.png");

    		// Creo los 2 botones.
    		JButton actualizarButton = new JButton("Actualizar",iconoActualizar);
    		actualizarButton.setVerticalTextPosition(AbstractButton.BOTTOM);
    		actualizarButton.setHorizontalTextPosition(AbstractButton.CENTER);

    		JButton bandejaButton = new JButton("Bandeja de Entrada",iconoBandeja);
    		bandejaButton.setVerticalTextPosition(AbstractButton.BOTTOM);
    		bandejaButton.setHorizontalTextPosition(AbstractButton.CENTER);
    		
    		JButton enviadosButton = new JButton("Enviados",iconoEnviados);
    		enviadosButton.setVerticalTextPosition(AbstractButton.BOTTOM);
    		enviadosButton.setHorizontalTextPosition(AbstractButton.CENTER);
    		
    		JButton enviarButton = new JButton("Redactar Nuevo",iconoEnviar);
    		enviarButton.setVerticalTextPosition(AbstractButton.BOTTOM);
    		enviarButton.setHorizontalTextPosition(AbstractButton.CENTER);
    		
    		JButton cambiarButton = new JButton("Personal/Tecnico",iconoCambiar);
    		cambiarButton.setVerticalTextPosition(AbstractButton.BOTTOM);
    		cambiarButton.setHorizontalTextPosition(AbstractButton.CENTER);
    		
    		borrarButton = new JButton("Borrar mensajes",iconoBorrar);
    		borrarButton.setVerticalTextPosition(AbstractButton.BOTTOM);
    		borrarButton.setHorizontalTextPosition(AbstractButton.CENTER);

    		// Asigno un Titulo para los botones
    		actualizarButton.setActionCommand("ACTUALIZAR");
    		enviadosButton.setActionCommand("ENVIADOS");
    		bandejaButton.setActionCommand("BANDEJA");
    		enviarButton.setActionCommand("REDACTAR");
    		cambiarButton.setActionCommand("CAMBIAR");
    		borrarButton.setActionCommand("BORRAR");



    		// Asigno la acción para los botones Aceptar y Cancelar.
    		actualizarButton.addActionListener(this);
    		bandejaButton.addActionListener(this);
    		enviadosButton.addActionListener(this);
    		enviarButton.addActionListener(this);
    		cambiarButton.addActionListener(this);
    		borrarButton.addActionListener(this);

    		p.add(actualizarButton);
    		p.add(bandejaButton);
    		p.add(enviadosButton);
    		p.add(enviarButton);
    		p.add(borrarButton);
    		//System.out.println(NivelUsuario);
    		// Si el usuario es tecnico o administrador, puede cambiar de buzon
    		if ((NivelUsuario.compareTo("Administrador") == 0) || (NivelUsuario.compareTo("Tecnico") == 0)) p.add(cambiarButton);

    		return p;
		}// Fin createButtonPanel
  		/** Función que crea los iconos para los botones */
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
			MiMysql devuelveMensajes = new MiMysql();
			// Si pulsamos actualizar, recibe los mensajes
			if ("ACTUALIZAR".equals(e.getActionCommand()))
			{
				table.setModel(devuelveMensajes.ConsultaMensajesBuzon(queBuzon,cogeMensajesDe,esTecnico));
			}
			// Si pulsamos bandeja, muestra mensajes de la bandeja
			else if("BANDEJA".equals(e.getActionCommand()))
			{
				queBuzon = "Buzon";
				cogeMensajesDe = "id_receptor = " + Socio;
				table.setModel(devuelveMensajes.ConsultaMensajesBuzon(queBuzon,cogeMensajesDe,esTecnico));
				
			}
			// Si pulsamos enviados, veremos la carpeta enviados
			else if("ENVIADOS".equals(e.getActionCommand()))
			{	
				queBuzon = "Buzon2";
				cogeMensajesDe = "id_remitente = " + Socio;
				table.setModel(devuelveMensajes.ConsultaMensajesBuzon(queBuzon,cogeMensajesDe,esTecnico));
			}
			// Si pulsamos Redactar, nos mostrará una ventana para redactar un mensaje.
			else if("REDACTAR".equals(e.getActionCommand()))
			{
				VentanaMensajeSalida  vMensaje = new VentanaMensajeSalida(Socio);
			}
			// Al pulsar cambiar veremos el buzón común a tecnicos
			else if("CAMBIAR".equals(e.getActionCommand()))
			{
				if(Socio.compareTo("2") == 0) 
				{		esTecnico = false;
						Socio = SocioInit;
				}
				else 
				{
					Socio = "2";
					esTecnico = true;
				}
				cogeMensajesDe = "id_receptor = " + Socio;
				table.setModel(devuelveMensajes.ConsultaMensajesBuzon(queBuzon,cogeMensajesDe,esTecnico));
			}
			// Si pulsamos borrar, borrará los mensajes.
			else if("BORRAR".equals(e.getActionCommand()))
			{
				int[] msjsABorrar = table.getSelectedRows();
				String enviar = "";
				String enviado = "DELETE FROM " + queBuzon + " WHERE id_mensaje = ";
				MiMysql borrador = new MiMysql();
				//System.out.println(msjsABorrar.length);
				for(int i=0;i<msjsABorrar.length;i++)
				{
					enviar = enviado + table.getValueAt(msjsABorrar[i],0 ).toString() + " ;";
					borrador.LlamadaDB(enviar);
				}
			}
			
		}

	}// Fin class SharedModelDemo extends JPanel 
}