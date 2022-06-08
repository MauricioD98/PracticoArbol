/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MAURICIO
 */
public class NodoBinario<K,V> {
    private NodoBinario<K, V> hijoIzquierdo;
    private K clave;
    private V valor;
    private NodoBinario<K, V>hijoDerecho;

    public NodoBinario() {
    
    }

    public NodoBinario(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public NodoBinario<K, V> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoBinario<K, V> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public K getClave() {
        return clave;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public NodoBinario<K, V> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoBinario<K, V> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
    
    public static NodoBinario nodoVacio(){
        return null;
    }
    public static boolean esNodoVacio(NodoBinario unNodo){
        return unNodo==NodoBinario.nodoVacio();
    }
    public boolean esHijoIzquierdoVacio(){
        return NodoBinario.esNodoVacio(this.getHijoIzquierdo());
    }
    public boolean esHijoDerechoVacio(){
        return NodoBinario.esNodoVacio(this.getHijoDerecho());
    }
    public boolean esHoja(){
        return this.esHijoIzquierdoVacio() && this.esHijoDerechoVacio();
    }
    
}  
