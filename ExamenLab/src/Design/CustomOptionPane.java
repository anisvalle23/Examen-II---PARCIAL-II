package Design;

import javax.swing.*;
import java.awt.*;

public class CustomOptionPane {

    public static String showInputDialog(Component parent, String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);

        panel.add(label, BorderLayout.CENTER);

        UIManager.put("OptionPane.background", new Color(255, 182, 193));
        UIManager.put("Panel.background", new Color(255, 182, 193));
        UIManager.put("Button.background", new Color(200, 200, 200));
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));

        return JOptionPane.showInputDialog(parent, panel, "Ingrese el nombre de usuario", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showMessageDialog(Component parent, String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);

        panel.add(label, BorderLayout.CENTER);

        UIManager.put("OptionPane.background", new Color(255, 182, 193));
        UIManager.put("Panel.background", new Color(255, 182, 193));
        UIManager.put("Button.background", new Color(200, 200, 200));
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));

        JOptionPane.showMessageDialog(parent, panel, "Mensaje", JOptionPane.PLAIN_MESSAGE);
    }

    public static int showConfirmDialog(Component parent, String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);

        panel.add(label, BorderLayout.CENTER);

        UIManager.put("OptionPane.background", new Color(255, 182, 193));
        UIManager.put("Panel.background", new Color(255, 182, 193));
        UIManager.put("Button.background", new Color(200, 200, 200));
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));

        return JOptionPane.showConfirmDialog(parent, panel, "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
}
