public class AVL<K extends Comparable<K>,V>extends ArbolBinario<K,V> {
 private static final byte DIFERENCIA_MAX=1;

//EJERCICIO 6
    @Override
    public void insertar(K claveInsertar, V valorInsertar) throws NullPointerException {
        if (claveInsertar == null) {
            throw new NullPointerException("Clave a insertar no puede ser nula");
        }
        if (valorInsertar == null) {
            throw new NullPointerException("Valor a insertar no puede ser nulo");
        }
        this.raiz=this.insertar(raiz,claveInsertar,valorInsertar);

    }
    private NodoBinario<K,V> insertar(NodoBinario<K,V>nodoActual,K claveInsertar,V valorInsertar){
        if(NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<K,V> nuevoNodo= new NodoBinario<>(claveInsertar,valorInsertar);
            return nuevoNodo;
        }
        K claveActual=nodoActual.getClave();
        if(claveInsertar.compareTo(claveActual)<0){
            NodoBinario<K,V>supuestoNuevoHijoIzquierdo=insertar(nodoActual.getHijoIzquierdo(),claveInsertar,valorInsertar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if(claveInsertar.compareTo(claveActual)>0){
            NodoBinario<K,V>supuestoNuevoHijoDerecho=insertar(nodoActual.getHijoDerecho(),claveInsertar,valorInsertar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return balancear(nodoActual);
        }
        nodoActual.setValor(valorInsertar);
        return nodoActual;
    }
    private NodoBinario<K,V> balancear(NodoBinario<K,V>nodoActual){
          int alturaPorIzquierda=altura(nodoActual.getHijoIzquierdo());
          int alturaPorDerecha= altura(nodoActual.getHijoDerecho());

          int diferencia=alturaPorIzquierda-alturaPorDerecha;

          if(diferencia> DIFERENCIA_MAX){
              NodoBinario<K,V>hijoIzquierdoDelNodoActual=nodoActual.getHijoIzquierdo();
              alturaPorIzquierda=altura(hijoIzquierdoDelNodoActual.getHijoIzquierdo());
              alturaPorDerecha=altura(hijoIzquierdoDelNodoActual.getHijoDerecho());
              if (alturaPorDerecha>alturaPorIzquierda){
                  return rotacionDobleDerecha(nodoActual);
              }
              return rotacionSimpleDerecha(nodoActual);
          } else if (diferencia<-DIFERENCIA_MAX) {
              NodoBinario<K,V>hijoDerechoDelNodoActual=nodoActual.getHijoDerecho();
              alturaPorIzquierda=altura(hijoDerechoDelNodoActual.getHijoIzquierdo());
              alturaPorDerecha=altura(hijoDerechoDelNodoActual.getHijoDerecho());
               if (alturaPorIzquierda>alturaPorDerecha){
                   return rotacionDobleIzquierda(nodoActual);
               }
               return rotacionSimpleIzquierda(nodoActual);
          }
          return nodoActual;
    }
    private NodoBinario<K,V> rotacionSimpleIzquierda(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoRotar=nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoRotar.getHijoIzquierdo());
        nodoRotar.setHijoIzquierdo(nodoActual);
        return nodoRotar;
    }
    private NodoBinario<K,V>rotacionDobleIzquierda(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V>primerNodoQueRota=rotacionSimpleDerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(primerNodoQueRota);
        return rotacionSimpleIzquierda(nodoActual);
    }
    private NodoBinario<K,V> rotacionSimpleDerecha(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoRotar=nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoRotar.getHijoDerecho());
        nodoRotar.setHijoDerecho(nodoActual);
        return nodoRotar;
    }
    private NodoBinario<K,V>rotacionDobleDerecha(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V>primerNodoQueRota=rotacionSimpleIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(primerNodoQueRota);
        return rotacionSimpleDerecha(nodoActual);
    }
    //EJERCICIO 7
    @Override
    public V eliminar(K claveEliminar) throws NullPointerException, ExcepcionClaveNoExiste {
        if(claveEliminar==null){
            throw new NullPointerException("Clave es nula no puede ser nula");
        }
        V valorEliminar=this.buscar(claveEliminar);
        if(valorEliminar==null){
            throw new ExcepcionClaveNoExiste();
        }
        this.raiz=eliminar(this.raiz,claveEliminar);
        return valorEliminar;
    }
    private NodoBinario<K,V>eliminar(NodoBinario<K,V>nodoActual,K claveEliminar){
        K claveActual=nodoActual.getClave();
        if(claveEliminar.compareTo(claveActual)<0){
            NodoBinario<K,V>supuestoNuevoHijoIzquierdo=eliminar(nodoActual.getHijoIzquierdo(),claveEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if(claveEliminar.compareTo(claveActual)>0){
            NodoBinario<K,V>supuestoNuevoHijoDerecho=eliminar(nodoActual.getHijoDerecho(),claveEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return balancear(nodoActual);
        }
        //caso1
        if(nodoActual.esHoja()){
            return NodoBinario.nodoVacio();
        }
        //caso 2 a
        if(nodoActual.esHijoIzquierdoVacio()&& !nodoActual.esHijoDerechoVacio()){
            return balancear(nodoActual.getHijoDerecho());
        }
        //caso 2 b
        if(!nodoActual.esHijoIzquierdoVacio()&& nodoActual.esHijoDerechoVacio()){
            return balancear(nodoActual.getHijoIzquierdo());
        }
        //caso3
        NodoBinario<K,V>nodoDelSucesor=this.obtenerNodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V>supuestoNuevoHijoDerecho=this.eliminar(nodoActual.getHijoDerecho(),nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return balancear(nodoActual);
    }
    public static void main(String ar[])throws NullPointerException, ExcepcionClaveNoExiste{
        //EJERCICIO 6
        IArbolBusqueda<Integer, String> arbol = new AVL<>();
        arbol.insertar(3, "dilker");
        arbol.insertar(2, "deglis");
        arbol.insertar(1, "delgar");
        arbol.insertar(4, "dilker");
        arbol.insertar(5, "deglis");
        arbol.insertar(6, "delgar");
        arbol.insertar(7, "dilker");
        arbol.insertar(16, "deglis");
        arbol.insertar(15, "delgar");
        arbol.insertar(14, "deglis");
        arbol.insertar(13, "delgar");
        arbol.insertar(12, "delgar");
        //EJERCICIO 7
        arbol.eliminar(13);
        arbol.eliminar(12);
    }
}
