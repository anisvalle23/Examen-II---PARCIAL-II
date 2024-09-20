package Design;

import Logic.PSNUsers;
import Logic.Trophy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class PanelPremios extends JPanel {
    private final PSNUsers usuario;
    private JTextField campoJuego, campoNombrePremio;
    private JComboBox<String> tipoPremioBox;

    public PanelPremios(PSNUsers usuario) {
        this.usuario = usuario;
        setLayout(null);
        setBounds(0, 0, 500, 500);
        setOpaque(false);

        VentanaPrincipal.agregarEncabezado(this);
        crearCampos();
        crearBotones();
    }

    private void crearCampos() {
        campoJuego = crearCampoTexto("Nombre del Juego: ", 100);
        campoNombrePremio = crearCampoTexto("Nombre del Trofeo: ", 180);

        String[] tiposPremios = {"Platino", "Oro", "Plata", "Bronce"};
        tipoPremioBox = new JComboBox<>(tiposPremios);
        tipoPremioBox.setBounds(250, 260, 160, 40);
        JLabel etiquetaTipo = new JLabel("Tipo de Trofeo: ");
        etiquetaTipo.setBounds(50, 260, 200, 40);
        etiquetaTipo.setForeground(Color.DARK_GRAY);
        etiquetaTipo.setFont(new Font("Arial", Font.PLAIN, 16));
        add(etiquetaTipo);
        add(tipoPremioBox);
    }

    private JTextField crearCampoTexto(String etiqueta, int yPos) {
        JTextField campoTexto = new JTextField();
        campoTexto.setBounds(250, yPos + 30, 200, 40);
        campoTexto.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel etiquetaCampo = new JLabel(etiqueta);
        etiquetaCampo.setBounds(50, yPos, 200, 30);
        etiquetaCampo.setForeground(Color.DARK_GRAY);
        etiquetaCampo.setFont(new Font("Arial", Font.PLAIN, 16));
        add(etiquetaCampo);
        add(campoTexto);
        return campoTexto;
    }

    private void crearBotones() {
        JButton botonRegresar = crearBoton("Regresar", 400, 100, e -> cambiarAAreaPrincipal());
        add(botonRegresar);

        JButton botonAgregarPremio = crearBoton("Agregar Trofeo", 400, 300, e -> {
            try {
                agregarPremio();
            } catch (IOException ex) {
                Logger.getLogger(PanelPremios.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        add(botonAgregarPremio);
    }

    private JButton crearBoton(String texto, int yPos, int xPos, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(xPos, yPos, 180, 40);
        boton.setFocusable(false);
        boton.addActionListener(accion);
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setBackground(new Color(255, 182, 193));
        boton.setForeground(Color.DARK_GRAY);
        boton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        boton.setFocusPainted(false);
        return boton;
    }

    private void cambiarAAreaPrincipal() {
        VentanaPrincipal.panelPremios.setVisible(false);
        VentanaPrincipal.areaPrincipal.setVisible(true);
    }

    private void agregarPremio() throws IOException {
        String juego = campoJuego.getText();
        String nombrePremio = campoNombrePremio.getText();
        if (!juego.isBlank() && !nombrePremio.isBlank()) {
            String nombreUsuario = CustomOptionPane.showInputDialog(this, "Ingrese el nombre de usuario:");
            if (nombreUsuario != null && !nombreUsuario.isBlank()) {
                Trophy tipoPremio = obtenerTipoPremio(tipoPremioBox.getSelectedIndex());
                if (usuario.agregarTrofeoA(nombreUsuario, juego, nombrePremio, tipoPremio)) {
                    CustomOptionPane.showMessageDialog(this, "Trofeo agregado exitosamente!");
                    resetearCampos();
                } else {
                    CustomOptionPane.showMessageDialog(this, "No se pudo agregar el trofeo");
                }
            }
        }
    }

    private Trophy obtenerTipoPremio(int indice) {
        switch (indice) {
            case 0:
                return Trophy.PLATINO;
            case 1:
                return Trophy.ORO;
            case 2:
                return Trophy.PLATA;
            default:
                return Trophy.BRONCE;
        }
    }

    private void resetearCampos() {
        campoJuego.setText("");
        campoNombrePremio.setText("");
        tipoPremioBox.setSelectedIndex(0);
    }
}
