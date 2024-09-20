package Design;

import Logic.PSNUsers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentanaPrincipal extends JFrame {

    public static PanelPremios panelPremios;
    public static PanelInfoUsuario panelInfoUsuario;
    public static AreaPrincipal areaPrincipal;

    public VentanaPrincipal(PSNUsers usuario) {
        iniciarVentanaPrincipal(usuario);
    }

    private void iniciarVentanaPrincipal(PSNUsers usuario) {
        setLayout(null);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("PSN Trophy Manager");

        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(245, 245, 220);
                Color color2 = new Color(255, 182, 193);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0, 0, 600, 600);

        areaPrincipal = new AreaPrincipal(usuario);
        panelPrincipal.add(areaPrincipal);

        panelPremios = new PanelPremios(usuario);
        panelPremios.setVisible(false);
        panelPrincipal.add(panelPremios);

        panelInfoUsuario = new PanelInfoUsuario(usuario);
        panelInfoUsuario.setVisible(false);
        panelPrincipal.add(panelInfoUsuario);

        add(panelPrincipal);
    }

    public static void agregarEncabezado(JPanel panel) {
        JLabel headerLabel = new JLabel("PSN Trophy Manager");
        headerLabel.setBounds(50, 20, 500, 50);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerLabel.setForeground(Color.DARK_GRAY);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(headerLabel);
    }
}

class AreaPrincipal extends JPanel {

    private final PSNUsers usuario;

    public AreaPrincipal(PSNUsers usuario) {
        this.usuario = usuario;
        setLayout(null);
        setBounds(0, 0, 600, 600);
        setOpaque(false);

        VentanaPrincipal.agregarEncabezado(this);
        crearBotones();
    }

    private void crearBotones() {

        JButton botonAgregarUsuario = crearBoton("Agregar Usuario", 100, 200, e -> {
            try {
                agregarUsuario();
            } catch (IOException ex) {
                Logger.getLogger(AreaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        add(botonAgregarUsuario);

        JButton botonDesactivarUsuario = crearBoton("Desactivar Usuario", 330, 200, e -> {
            try {
                desactivarUsuario();
            } catch (IOException ex) {
                Logger.getLogger(AreaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        add(botonDesactivarUsuario);

        JButton botonAgregarPremio = crearBoton("Agregar Trofeo", 100, 300, e -> cambiarAPanelPremios());
        add(botonAgregarPremio);

        JButton botonInfoUsuario = crearBoton("Información de Usuario", 330, 300, e -> cambiarAPanelInfoUsuario());
        add(botonInfoUsuario);
    }

    private JButton crearBoton(String texto, int xPos, int yPos, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(xPos, yPos, 200, 60);
        boton.setFocusable(false);
        boton.addActionListener(accion);
        boton.setFont(new Font("Arial", Font.PLAIN, 18));
        boton.setBackground(new Color(255, 182, 193));
        boton.setForeground(Color.DARK_GRAY);
        boton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        boton.setFocusPainted(false);
        return boton;
    }

    private void agregarUsuario() throws IOException {
        String nuevoUsuario = CustomOptionPane.showInputDialog(this, "Ingrese el nombre de usuario:");
        if (nuevoUsuario != null && !nuevoUsuario.isBlank()) {
            if (usuario.agregarUsuario(nuevoUsuario)) {
                CustomOptionPane.showMessageDialog(this, "Usuario agregado exitosamente!");
            }
        }
    }

    private void desactivarUsuario() throws IOException {
        String usuarioADesactivar = CustomOptionPane.showInputDialog(this, "Ingrese el nombre de usuario:");
        if (usuarioADesactivar != null && !usuarioADesactivar.isBlank()) {
            if (usuario.eliminarUsuario(usuarioADesactivar)) {
                CustomOptionPane.showMessageDialog(this, "Usuario desactivado exitosamente!");
            }
        }
    }

    private void cambiarAPanelPremios() {
        VentanaPrincipal.panelPremios.setVisible(true);
        VentanaPrincipal.areaPrincipal.setVisible(false);
        VentanaPrincipal.panelInfoUsuario.setVisible(false);
    }

    private void cambiarAPanelInfoUsuario() {
        VentanaPrincipal.panelPremios.setVisible(false);
        VentanaPrincipal.areaPrincipal.setVisible(false);
        VentanaPrincipal.panelInfoUsuario.setVisible(true);
    }
}


