package GUI;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Actividad;
import model.Solicitud;
import utils.JPAUtil;

import javax.swing.JTextArea;
import java.awt.Font;

public class DlgSolicitud extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblIdSolicitud;
	private JLabel lblArchivoAdjunto;
	private JLabel lblActividad;
	private JLabel lblEstado;
	private JLabel lblFechaRegistro;
	private JTextField txtIdSolicitud;
	private JTextField txtArchivoAdjunto;
	private JComboBox<Object> cboActividad;
	private JComboBox<String> cboEstado;
	private JTextField txtFechaRegistro;
	private JButton btnBuscar;
	private JButton btnOK;
	private JButton btnOpciones;
	private JButton btnAdicionar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnListar;
	private JScrollPane scrollPane;
	private JTextArea txtSalida;

	// Tipo de operaci�n a procesar: Adicionar, Consultar, Modificar o Eliminar
	private int tipoOperacion;

	// Constantes para los tipos de operaciones
	public final static int ADICIONAR = 0;
	public final static int CONSULTAR = 1;
	public final static int MODIFICAR = 2;
	public final static int ELIMINAR = 3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DlgSolicitud dialog = new DlgSolicitud();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public DlgSolicitud() {
		setResizable(false);
		setTitle("Mantenimiento | Solicitud");
		setBounds(100, 100, 810, 604);
		getContentPane().setLayout(null);

		lblIdSolicitud = new JLabel("Id Solicitud :");
		lblIdSolicitud.setBounds(10, 10, 149, 23);
		getContentPane().add(lblIdSolicitud);

		lblArchivoAdjunto = new JLabel("Archivo adjunto :");
		lblArchivoAdjunto.setBounds(10, 35, 149, 23);
		getContentPane().add(lblArchivoAdjunto);

		lblActividad = new JLabel("Actividad :");
		lblActividad.setBounds(10, 62, 149, 23);
		getContentPane().add(lblActividad);

		lblEstado = new JLabel("Estado :");
		lblEstado.setBounds(10, 88, 149, 23);
		getContentPane().add(lblEstado);

		txtIdSolicitud = new JTextField();
		txtIdSolicitud.setBounds(174, 10, 86, 23);
		getContentPane().add(txtIdSolicitud);
		txtIdSolicitud.setEditable(false);
		txtIdSolicitud.setColumns(10);

		txtArchivoAdjunto = new JTextField();
		txtArchivoAdjunto.setBounds(174, 35, 251, 23);
		getContentPane().add(txtArchivoAdjunto);
		txtArchivoAdjunto.setColumns(10);

		cboActividad = new JComboBox<Object>();
		cboActividad.setBounds(174, 60, 251, 26);
		getContentPane().add(cboActividad);

		String[] estados = { "PE", "AC", "AN" };
		cboEstado = new JComboBox<String>();
		cboEstado.setBounds(174, 88, 86, 23);
		getContentPane().add(cboEstado);
		for (String estado : estados) {
			cboEstado.addItem(estado);
		}

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setEnabled(false);
		btnBuscar.setBounds(324, 10, 101, 23);
		getContentPane().add(btnBuscar);

		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		btnOK.setBounds(325, 88, 100, 23);
		getContentPane().add(btnOK);

		btnOpciones = new JButton("Opciones");
		btnOpciones.addActionListener(this);
		btnOpciones.setBounds(555, 10, 100, 75);
		getContentPane().add(btnOpciones);

		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(this);
		btnAdicionar.setBounds(664, 10, 120, 23);
		getContentPane().add(btnAdicionar);

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		btnModificar.setBounds(664, 36, 120, 23);
		getContentPane().add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(664, 62, 120, 23);
		getContentPane().add(btnEliminar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 146, 775, 371);
		getContentPane().add(scrollPane);

		txtSalida = new JTextArea();
		txtSalida.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setViewportView(txtSalida);

		btnListar = new JButton("Listar");
		btnListar.addActionListener(this);
		btnListar.setBounds(345, 525, 115, 29);
		getContentPane().add(btnListar);

		lblFechaRegistro = new JLabel("Fecha registro :");
		lblFechaRegistro.setBounds(10, 115, 149, 20);
		getContentPane().add(lblFechaRegistro);

		txtFechaRegistro = new JTextField();
		txtFechaRegistro.setEditable(false);
		txtFechaRegistro.setBounds(174, 112, 146, 26);
		getContentPane().add(txtFechaRegistro);
		txtFechaRegistro.setColumns(10);

		habilitarEntradas(false);
		habilitarBotones(true);
		cargarActividades();
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnListar) {
			actionPerformedBtnListar(arg0);
		}
		if (arg0.getSource() == btnEliminar) {
			actionPerformedBtnEliminar(arg0);
		}
		if (arg0.getSource() == btnModificar) {
			actionPerformedBtnModificar(arg0);
		}
		if (arg0.getSource() == btnAdicionar) {
			actionPerformedBtnAdicionar(arg0);
		}
		if (arg0.getSource() == btnOpciones) {
			actionPerformedBtnOpciones(arg0);
		}
		if (arg0.getSource() == btnOK) {
			actionPerformedBtnOK(arg0);
		}
		if (arg0.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(arg0);
		}
	}

	protected void actionPerformedBtnBuscar(ActionEvent arg0) {
		buscar();
	}

	protected void actionPerformedBtnOK(ActionEvent arg0) {
		switch (tipoOperacion) {
		case ADICIONAR:
			adicionar();
			break;
		case MODIFICAR:
			modificar();
			break;
		case ELIMINAR:
			eliminar();
		}
	}

	protected void actionPerformedBtnOpciones(ActionEvent arg0) {
		limpiar();
	}

	protected void actionPerformedBtnListar(ActionEvent arg0) {
		listar();
	}

	protected void actionPerformedBtnAdicionar(ActionEvent arg0) {
		tipoOperacion = ADICIONAR;
		habilitarEntradas(true);
		habilitarBotones(false);
		txtArchivoAdjunto.requestFocus();
	}

	protected void actionPerformedBtnModificar(ActionEvent arg0) {
		tipoOperacion = MODIFICAR;
		txtIdSolicitud.setEditable(true);
		habilitarBotones(false);
		txtIdSolicitud.requestFocus();
	}

	protected void actionPerformedBtnEliminar(ActionEvent arg0) {
		tipoOperacion = ELIMINAR;
		txtIdSolicitud.setEditable(true);
		habilitarBotones(false);
		txtIdSolicitud.requestFocus();
	}

	void cargarActividades() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			List<Actividad> lista = em.createQuery("SELECT a FROM Actividad a", Actividad.class).getResultList();
			cboActividad.removeAllItems();
			for (Actividad a : lista) {
				cboActividad.addItem(a);
			}
		} finally {
			em.close();
		}
	}

	void listar() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			List<Solicitud> lista = em.createQuery("SELECT s FROM Solicitud s", Solicitud.class).getResultList();
			txtSalida.setText("");
			for (Solicitud s : lista) {
				String linea = String.format("ID: %d | Actividad: %s | Estado: %s | Archivo: %s | Fecha: %s",
					s.getId(),
					s.getActividad().getDescripcion(),
					s.getEstado(),
					s.getArchivo_adjunto(),
					s.getFecha_reg());
				imprimir(linea);
			}
		} finally {
			em.close();
		}
	}
	
	void adicionar() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Solicitud s = new Solicitud();
			s.setArchivo_adjunto(txtArchivoAdjunto.getText());
			s.setEstado(cboEstado.getSelectedItem().toString());
			s.setActividad((Actividad) cboActividad.getSelectedItem());
			s.setFecha_reg(LocalDate.now());

			em.getTransaction().begin();
			em.persist(s);
			em.getTransaction().commit();

			mensajeInfo("Solicitud registrada");
			limpiar();
		} catch (Exception e) {
			mensajeError("Error al registrar: " + e.getMessage());
		} finally {
			em.close();
		}
	}
	
	void buscar() {
		int id;
		try {
			id = Integer.parseInt(txtIdSolicitud.getText());
		} catch (NumberFormatException e) {
			mensajeError("ID inválido");
			return;
		}

		EntityManager em = JPAUtil.getEntityManager();
		try {
			Solicitud s = em.find(Solicitud.class, id);
			if (s == null) {
				mensajeError("Solicitud no encontrada");
			} else {
				txtArchivoAdjunto.setText(s.getArchivo_adjunto());
				cboActividad.setSelectedItem(s.getActividad());
				cboEstado.setSelectedItem(s.getEstado());
				txtFechaRegistro.setText(s.getFecha_reg().toString());
				habilitarOk();
			}
		} finally {
			em.close();
		}
	}

	void modificar() {
		int id = Integer.parseInt(txtIdSolicitud.getText());

		EntityManager em = JPAUtil.getEntityManager();
		try {
			Solicitud s = em.find(Solicitud.class, id);
			if (s == null) {
				mensajeError("Solicitud no encontrada");
				return;
			}

			em.getTransaction().begin();
			s.setArchivo_adjunto(txtArchivoAdjunto.getText());
			s.setEstado(cboEstado.getSelectedItem().toString());
			s.setActividad((Actividad) cboActividad.getSelectedItem());
			em.getTransaction().commit();

			mensajeInfo("Solicitud actualizada");
			limpiar();
		} catch (Exception e) {
			mensajeError("Error al modificar: " + e.getMessage());
		} finally {
			em.close();
		}
	}


	void eliminar() {
		int id = Integer.parseInt(txtIdSolicitud.getText());

		EntityManager em = JPAUtil.getEntityManager();
		try {
			Solicitud s = em.find(Solicitud.class, id);
			if (s == null) {
				mensajeError("Solicitud no encontrada");
				return;
			}

			em.getTransaction().begin();
			em.remove(s);
			em.getTransaction().commit();

			mensajeInfo("Solicitud eliminada");
			limpiar();
		} catch (Exception e) {
			mensajeError("Error al eliminar: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	// M�todos tipo void (con par�metros)
	void habilitarEntradas(boolean sino) {
		txtArchivoAdjunto.setEditable(sino);
		cboActividad.setEnabled(sino);
		cboEstado.setEnabled(sino);
	}

	void habilitarBotones(boolean sino) {
		if (tipoOperacion == ADICIONAR)
			btnOK.setEnabled(!sino);
		else {
			btnBuscar.setEnabled(!sino);
			btnOK.setEnabled(false);
		}
		btnAdicionar.setEnabled(sino);
		btnModificar.setEnabled(sino);
		btnEliminar.setEnabled(sino);
		btnOpciones.setEnabled(!sino);
	}

	void habilitarOk() {
		if (tipoOperacion == MODIFICAR) {
			habilitarEntradas(true);
			txtIdSolicitud.setEditable(false);
			btnBuscar.setEnabled(false);
			btnOK.setEnabled(true);
			txtArchivoAdjunto.requestFocus();
		}
		if (tipoOperacion == ELIMINAR) {
			txtIdSolicitud.setEditable(false);
			btnBuscar.setEnabled(false);
			btnOK.setEnabled(true);
		}
	}

	void mensajeInfo(String msj) {
		mensaje(msj, "INFO", JOptionPane.INFORMATION_MESSAGE);
	}

	void mensajeError(String msj) {
		mensaje(msj, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	void mensaje(String msj, String titulo, int tipo) {
		JOptionPane.showMessageDialog(this, msj, titulo, tipo);
	}

	void imprimir(String texto) {
		txtSalida.append(texto + "\n");
	}

	void imprimir() {
		imprimir("");
	}

	void limpiar() {
		txtIdSolicitud.setText("");
		txtArchivoAdjunto.setText("");
		if (cboActividad.getItemCount() > 0)
			cboActividad.setSelectedIndex(0);
		cboEstado.setSelectedIndex(0);
		txtFechaRegistro.setText("");
		txtIdSolicitud.setEditable(false);
		habilitarEntradas(false);
		habilitarBotones(true);
	}
}