package Logic;

import Design.CustomOptionPane;
import java.io.File;
import javax.swing.JOptionPane;

public class HashTable {

    private boolean reActivar = false;
    private Entry nodo;

    public HashTable() {
        nodo = null;
    }

    public boolean agregar(String username) {
        if (username.isBlank()) {
            return false;
        }

        int position = 0;

        if (nodo == null) {
            nodo = new Entry(username, position);
            return true;
        } else {
            if (nodo.getUsername().equals(username)) {
                CustomOptionPane.showMessageDialog(null, "Este nombre de usuario ya está tomado, por favor ingrese otro");
                return false;
            }

            Entry currentNode = this.nodo;
            if ((existeEnArchivo(username) && !PSNUsers.inicioSistema)) {
                CustomOptionPane.showMessageDialog(null, "La cuenta que intenta registrar ya existe");
                return false;
            }

            while (currentNode != null) {
                position++;
                if (currentNode.getNext() == null) {
                    currentNode.setNext(new Entry(username, position));
                    return true;
                }
                if (currentNode.getNext().getUsername().equals(username)) {
                    CustomOptionPane.showMessageDialog(null, "¡Este nombre de usuario ya está tomado!");
                    return false;
                }
                currentNode = currentNode.getNext();
            }
        }

        return false;
    }

    public boolean existeEnArchivo(String nombre) {
        File cuentas = new File("CuentasPSN\\" + nombre + ".psn");
        return cuentas.exists();
    }

    public boolean eliminar(String username) {
        if (username.isBlank()) {
            return false;
        }

        if (nodo != null) {
            if (nodo.getUsername().equals(username)) {
                nodo = nodo.getNext();
                return true;
            }

            Entry currentNode = nodo;
            while (currentNode.getNext() != null) {
                if (currentNode.getNext().getUsername().equals(username)) {
                    currentNode.setNext(currentNode.getNext().getNext());
                    return true;
                }

                currentNode = currentNode.getNext();
            }
        }

        if (CustomOptionPane.showConfirmDialog(null, "Esta cuenta ya se encuentra desactivada\n¿Desea reactivarla?") == JOptionPane.YES_OPTION) {
            reActivar = true;
            buscar(username);
            return false;
        }

        CustomOptionPane.showMessageDialog(null, "¡No se ha encontrado a este usuario!");
        return false;
    }

    public int buscar(String username) {
        if (username.isBlank() || nodo == null) {
            return -1;
        }

        if (nodo.getUsername().equals(username)) {
            return nodo.getPosition();
        }

        if (reActivar) {
            PSNUsers.inicioSistema = true;
            agregar(username);
            PSNUsers.reactivarCuenta(username);
            reActivar = false;
            PSNUsers.inicioSistema = false;
            CustomOptionPane.showMessageDialog(null, "¡Se ha reactivado la cuenta exitosamente!");
            return -1;
        }

        Entry currentNode = nodo;
        while (currentNode != null) {
            if (currentNode.getUsername().equals(username)) {
                return currentNode.getPosition();
            }
            currentNode = currentNode.getNext();
        }

        return -1;
    }
}
