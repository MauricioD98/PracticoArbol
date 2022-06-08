/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */

/**
 *
 * @author MAURICIO
 */
public class ExcepcionClaveNoExiste extends Exception {

    /**
     * Creates a new instance of <code>ExceptionClabeNoExiste</code> without
     * detail message.
     */
    public ExcepcionClaveNoExiste() {
    this("Clave no Existe");
    }

    /**
     * Constructs an instance of <code>ExceptionClabeNoExiste</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionClaveNoExiste(String msg) {
        super(msg);
    }
}
