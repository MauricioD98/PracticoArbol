/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

import java.util.List;

/**
 *
 * @author MAURICIO
 */
public interface IArbolBusqueda<K extends Comparable<K>,V> {
    void insertar(K claveInsertar,V valorInsertar)throws NullPointerException;
    V eliminar(K claveEliminar) throws NullPointerException,ExcepcionClaveNoExiste;
    V buscar(K claveBuscar) throws NullPointerException;

    int altura();

    //EJERCICIO 15
    //

    void vaciarArbol();

    List<K>recorridoPorNiveles();
    boolean esArbolVacio();
    List<K>recorridoInOrden();
    List<K>recorridoPostOrden();
    List<K>recorridoPostOrdenV2();

    int cantidadDeNodosConUnHIjoNoVacio();
    int cantidadDeHijosVaciosConInOrden();
    boolean verificarSiSonArbolesSimilares(ArbolBinario<K, V> nuevoArbol);

    boolean NodosCompletosEnNivel(int niv);

    boolean sonSimilares(ArbolMViasBusqueda<K, V> arbol2);
}
