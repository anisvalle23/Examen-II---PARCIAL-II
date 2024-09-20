package Design;

import Logic.PSNUsers;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new VentanaPrincipal(new PSNUsers()).setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
