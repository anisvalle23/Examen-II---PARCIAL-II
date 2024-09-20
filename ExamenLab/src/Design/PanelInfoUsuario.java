package Design;

import Logic.PSNUsers;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class PanelInfoUsuario extends JPanel {

    private final PSNUsers usuario;
    private JTextArea areaInfoUsuario;
    private JLabel etiquetaInfoUsuario;
    private JTextField campoBuscarUsuario;

    public PanelInfoUsuario(PSNUsers usuario) {
        this.usuario = usuario;
        setLayout(null);
        setBounds(0, 0, 500, 500);
        setOpaque(false);

        VentanaPrincipal.agregarEncabezado(this);
        crearCampos();
        crearBotones();
    }

    private void crearCampos() {
        JLabel etiquetaBuscar = new JLabel("Buscar Usuario:");
        etiquetaBuscar.setBounds(50, 80, 150, 30);
        etiquetaBuscar.setFont(new Font("Arial", Font.PLAIN, 16));
        etiquetaBuscar.setForeground(Color.DARK_GRAY);
        add(etiquetaBuscar);

        campoBuscarUsuario = new JTextField();
        campoBuscarUsuario.setBounds(180, 80, 200, 30);
        campoBuscarUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        add(campoBuscarUsuario);

        areaInfoUsuario = new JTextArea();
        areaInfoUsuario.setEditable(false);
        areaInfoUsuario.setBounds(50, 150, 400, 200);
        areaInfoUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        areaInfoUsuario.setForeground(Color.BLACK);
        areaInfoUsuario.setBackground(Color.LIGHT_GRAY);
        areaInfoUsuario.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        JScrollPane scrollPane = new JScrollPane(areaInfoUsuario);
        scrollPane.setBounds(areaInfoUsuario.getBounds());
        add(scrollPane);

        etiquetaInfoUsuario = new JLabel();
        etiquetaInfoUsuario.setBounds(50, 370, 400, 40);
        etiquetaInfoUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetaInfoUsuario.setHorizontalAlignment(JLabel.CENTER);
        etiquetaInfoUsuario.setForeground(Color.BLACK);
        add(etiquetaInfoUsuario);
    }

    private void crearBotones() {
        JButton botonBuscar = crearBoton("Buscar", 400, 80, e -> {
            try {
                buscarUsuario();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(botonBuscar);

        JButton botonRegresar = crearBoton("Regresar", 200, 430, e -> cambiarAAreaPrincipal());
        add(botonRegresar);
    }

    private JButton crearBoton(String texto, int xPos, int yPos, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(xPos, yPos, 100, 30);
        boton.setFocusable(false);
        boton.addActionListener(accion);
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        boton.setFocusPainted(false);
        return boton;
    }

    private void cambiarAAreaPrincipal() {
        VentanaPrincipal.panelInfoUsuario.setVisible(false);
        VentanaPrincipal.areaPrincipal.setVisible(true);
    }

    public void buscarUsuario() throws IOException {
        String nombreUsuario = campoBuscarUsuario.getText();

        if (nombreUsuario != null && !nombreUsuario.isBlank()) {
            String infoUsuario = usuario.obtenerInfoJugador(nombreUsuario);

            if (infoUsuario.isEmpty()) {
                etiquetaInfoUsuario.setText("Usuario no tiene trofeos.");
            } else {

                String encabezado = "FECHA – TIPO - JUEGO – DESCRIPCIÓN\n";
                areaInfoUsuario.setText(encabezado + infoUsuario);
                etiquetaInfoUsuario.setText("");
            }
        } else {
            etiquetaInfoUsuario.setText("Por favor, ingrese un nombre de usuario.");
        }
    }
}
