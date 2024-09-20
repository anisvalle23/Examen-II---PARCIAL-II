package Logic;

import Design.CustomOptionPane;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;

public class PSNUsers {

    private RandomAccessFile archivoLectura;
    private final HashTable usuarios;
    private final File carpetaUsuarios;

    private int totalTrofeos;
    private int puntosTotales;

    protected static boolean inicioSistema = true;

    public PSNUsers() throws FileNotFoundException, IOException {
        carpetaUsuarios = new File("CuentasPSN");
        if (!carpetaUsuarios.exists()) {
            carpetaUsuarios.mkdir();
        }

        usuarios = new HashTable();
        cargarUsuariosEnTabla();
        inicioSistema = false;
    }

    private void cargarUsuariosEnTabla() throws FileNotFoundException, IOException {
        String nombreUsuario;
        for (String nombreArchivo : carpetaUsuarios.list()) {
            archivoLectura = new RandomAccessFile("CuentasPSN\\" + nombreArchivo, "rw");
            archivoLectura.seek(0);
            try {
                nombreUsuario = archivoLectura.readUTF();
                if (archivoLectura.readBoolean()) {
                    usuarios.agregar(nombreUsuario);
                }
            } catch (EOFException e) {
                System.out.println("Archivo incompleto o corrupto: " + nombreArchivo);
            } finally {
                archivoLectura.close();
            }
        }
    }

    public boolean agregarUsuario(String nombreUsuario) throws FileNotFoundException, IOException {
        if (usuarios.agregar(nombreUsuario)) {
            archivoLectura = new RandomAccessFile("CuentasPSN\\" + nombreUsuario + ".psn", "rw");
            archivoLectura.seek(0);
            archivoLectura.writeUTF(nombreUsuario);
            archivoLectura.writeBoolean(true);
            return true;
        }
        return false;
    }

    public boolean eliminarUsuario(String nombreUsuario) throws FileNotFoundException, IOException {
        if (usuarios.eliminar(nombreUsuario)) {
            archivoLectura = new RandomAccessFile("CuentasPSN\\" + nombreUsuario + ".psn", "rw");
            archivoLectura.seek(0);
            archivoLectura.readUTF();
            archivoLectura.writeBoolean(false);
            return true;
        }
        return false;
    }

    public static void reactivarCuenta(String nombreUsuario) {
        try {

            RandomAccessFile archivo = new RandomAccessFile("CuentasPSN\\" + nombreUsuario + ".psn", "rw");
            archivo.seek(0);
            archivo.readUTF();
            archivo.writeBoolean(true);
            archivo.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean agregarTrofeoA(String nombreUsuario, String juegoTrofeo, String nombreTrofeo, Trophy tipoTrofeo) throws IOException {
        int posicion = usuarios.buscar(nombreUsuario);
        if (posicion != -1) {
            archivoLectura = new RandomAccessFile("CuentasPSN\\" + nombreUsuario + ".psn", "rw");
            archivoLectura.seek(archivoLectura.length());
            archivoLectura.writeUTF(Calendar.getInstance().getTime().toString());
            archivoLectura.writeUTF(tipoTrofeo.getType());
            archivoLectura.writeInt(tipoTrofeo.getPoints());
            archivoLectura.writeUTF(juegoTrofeo);
            archivoLectura.writeUTF(nombreTrofeo);
            return true;
        }
        return false;
    }

    public String obtenerInfoJugador(String nombreUsuario) throws FileNotFoundException, IOException {
        puntosTotales = 0;
        totalTrofeos = 0;
        StringBuilder datos = new StringBuilder();

        if (usuarios.buscar(nombreUsuario) != -1) {
            archivoLectura = new RandomAccessFile("CuentasPSN\\" + nombreUsuario + ".psn", "rw");
            archivoLectura.seek(0);
            archivoLectura.readUTF();
            if (archivoLectura.readBoolean()) {
                datos.append("Usuario: ").append(nombreUsuario).append("\nTrofeos:\n");

                boolean tieneTrofeos = false;
                try {
                    while (archivoLectura.getFilePointer() < archivoLectura.length()) {
                        String fechaTrofeo = archivoLectura.readUTF();
                        String tipoTrofeo = archivoLectura.readUTF();
                        int puntosTrofeo = archivoLectura.readInt();
                        String juegoTrofeo = archivoLectura.readUTF();
                        String nombreTrofeo = archivoLectura.readUTF();

                        datos.append(fechaTrofeo)
                                .append(" - ").append(tipoTrofeo)
                                .append(" - ").append(juegoTrofeo)
                                .append(" - ").append(nombreTrofeo)
                                .append("\n");

                        totalTrofeos++;
                        puntosTotales += puntosTrofeo;
                        tieneTrofeos = true;
                    }
                } catch (EOFException e) {
                    System.out.println("Fin del archivo alcanzado o archivo incompleto.");
                }

                if (!tieneTrofeos) {
                    datos.append("El usuario no tiene trofeos.");
                } else {
                    datos.append("\nTotal de trofeos: ").append(totalTrofeos);
                    datos.append("\nPuntos totales: ").append(puntosTotales);
                }

                return datos.toString();
            }
        } else {
            CustomOptionPane.showMessageDialog(null, "Usuario no encontrado");
        }
        return "";
    }

    public int obtenerCantidadTrofeos() {
        return totalTrofeos;
    }

    public int obtenerPuntosUsuario() {
        return puntosTotales;
    }
}
