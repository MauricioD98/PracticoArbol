/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author MAURICIO
 */
public class NodoMvias<K,V> {
    private List<K>listaDeClaves;
    private List<V>listaDeValores;
    private List<NodoMvias<K,V>>listaDeHijos;
    
    public NodoMvias(int orden){
        this.listaDeClaves =new LinkedList<>();
        this.listaDeValores =new LinkedList<>();
        this.listaDeHijos= new LinkedList<>();
        for(int i=0;i<orden-1;i++){
            this.listaDeClaves.add((K) NodoMvias.datoVacio());
            this.listaDeValores.add((V) NodoMvias.datoVacio());
            this.listaDeHijos.add(NodoMvias.nodoVacio());
        }
        this.listaDeHijos.add(NodoMvias.nodoVacio());
    }
    
    public NodoMvias(int orden, K primerClave, V primerValor){
     this(orden);
     this.listaDeClaves.set(0, primerClave);
     this.listaDeValores.set(0, primerValor);
    }
    public static NodoMvias nodoVacio(){
        return null;
    }
    
    public static Object datoVacio(){
        return null;
    }
    
    public static boolean esNodoVacio(NodoMvias unNodo){
        return unNodo== NodoMvias.nodoVacio();
    }
    
    //retorna una clave de la lista de claves ubicado en el indice especificado por el parametro posicion
    //Precondicion:El valor del parametro esta dentro del rango de la lista de claves
    public K getClave(int posicion){
     return this.listaDeClaves.get(posicion);
    }
    public void setClave(int posicion,K clave){
        this.listaDeClaves.set(posicion, clave);
    }
    public V getValor(int posicion){
        return this.listaDeValores.get(posicion);
    }
    public void setValor(int posicion, V valor){
        this.listaDeValores.set(posicion,valor);
    }
    public NodoMvias<K,V> getHijos(int posicion){
        return this.listaDeHijos.get(posicion);
    }
    public void setHijos(int posicion, NodoMvias<K,V> nodoHijo){
        this.listaDeHijos.set(posicion, nodoHijo);
    }
    public boolean esHijoVacio(int posicion){
        return NodoMvias.esNodoVacio(this.listaDeHijos.get(posicion));
    }
    
    public boolean esClaveVacia(int posicion){
        return this.listaDeClaves.get(posicion)== NodoMvias.datoVacio();
    }
    
    public boolean esHoja(){
        for (int i=0;i<this.listaDeHijos.size();i++){
            if(!this.esHijoVacio(i)){
                return false;
            }
        }
        return true;
    }
    
    public boolean estanClavesLlenas(){
        for (int i=0;i<this.listaDeClaves.size();i++){
            if(this.esClaveVacia(i)){
                return false;
            }
        }
        return true; 
    }
    //return !this.esClaveVacia(this.listaDeClaves.size()-1);
    public int cantidadDeHijosNoVacios(){
        int cantidadDeHijosNoVacios=0; 
        for (int i=0;i<this.listaDeHijos.size();i++){
            if(!this.esHijoVacio(i)){
                cantidadDeHijosNoVacios++;
            }
        }
        return cantidadDeHijosNoVacios;
    }    
    
     public int cantidadDeClavesNoVacias(){
        int cantidadDeClavesNoVacias=0; 
        for (int i=0;i<this.listaDeClaves.size();i++){
            if(!this.esHijoVacio(i)){
                cantidadDeClavesNoVacias++;
            }
        }
        return cantidadDeClavesNoVacias;
    } 
    public int cantidadDeHijosVacios(){
        return this.listaDeHijos.size()-this.cantidadDeHijosNoVacios();
    }
    
    
}
