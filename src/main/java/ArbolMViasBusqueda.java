import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/*import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;*/

/**
 *
 * @author MAURICIO
 */
public class ArbolMViasBusqueda<K extends Comparable<K>,V>implements IArbolBusqueda<K,V>{

    protected NodoMvias<K,V> raiz;
    protected int orden;
    protected static final int POSICION_NO_VALIDA =-1;
    protected static final int ORDEN_MINIMO=3;

    public ArbolMViasBusqueda(){
        this.orden=ArbolMViasBusqueda.ORDEN_MINIMO;
    }

    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido{
        if(orden< ArbolMViasBusqueda.ORDEN_MINIMO){
            throw new ExcepcionOrdenInvalido();
        }
        this.orden=orden;
    }
  //EJERCICIO 10
    @Override
    public void insertar(K claveInsertar, V valorInsertar) throws NullPointerException {
        if(claveInsertar == null){
          throw new NullPointerException("Clave a insertar no puede ser nula");
       }
       if(valorInsertar == null){
          throw new NullPointerException("Valor a insertar no puede ser nulo");
       }
       if(this.esArbolVacio()){
          this.raiz = new NodoMvias<>(this.orden,claveInsertar,valorInsertar);
          return;
       }
       NodoMvias<K,V> nodoActual=this.raiz;
       while(!NodoMvias.esNodoVacio(nodoActual)){
         int posicionDeClaveAInsertar=this.obtenerPosicionDeClave(nodoActual,claveInsertar);
         if(posicionDeClaveAInsertar!= ArbolMViasBusqueda.POSICION_NO_VALIDA){
             nodoActual.setValor(posicionDeClaveAInsertar, valorInsertar);
             nodoActual=NodoMvias.nodoVacio();
             return;
         }
         if(nodoActual.esHoja()){
             if(!nodoActual.estanClavesLlenas()){
             this.insertarClaveValorEnNodo(nodoActual,claveInsertar,valorInsertar);
             }else{
                 NodoMvias<K,V> nuevoHijo=new NodoMvias<>(this.orden,claveInsertar,valorInsertar);
                 int posicionDeHijo=this.obtenerPosicionPorDondeBajar(nodoActual,claveInsertar);;
                 nodoActual.setHijos(posicionDeHijo, nuevoHijo);
             }
          nodoActual=NodoMvias.nodoVacio();
         }else{
         //en caso de que nodo actual no sea hoja
         int posicionPorDondeBajar=this.obtenerPosicionPorDondeBajar(nodoActual,claveInsertar);
         if(nodoActual.esHijoVacio(posicionPorDondeBajar)){
             NodoMvias<K,V> nuevoHijo=new NodoMvias<>(this.orden,claveInsertar,valorInsertar);
             nodoActual.setHijos(posicionPorDondeBajar, nuevoHijo);
           nodoActual=NodoMvias.nodoVacio();
         }else{
         nodoActual=nodoActual.getHijos(posicionPorDondeBajar);
          }
         }
       }
    }
    private void insertarClaveValorEnNodo(NodoMvias<K,V> nodoActual,K claveaInsertar,V valoraInsertar){
        int j=0;
        boolean claveaInsertadoEnNodoActual=false;
        while(j<nodoActual.cantidadDeClavesNoVacias() && claveaInsertadoEnNodoActual==false){
            K claveActual=nodoActual.getClave(j);
            if(claveaInsertar.compareTo(claveActual)<0){
                // recoremos las claves del nodo actual, para insertar una nueva clave
                for(int i=nodoActual.cantidadDeClavesNoVacias();i>0 && i>j;i--){
                    nodoActual.setClave(i, nodoActual.getClave(i-1));
                    nodoActual.setValor(i, nodoActual.getValor(i-1));
                }
                //insertar la nueva clave en la posicion correspondiente
                nodoActual.setClave(j, claveaInsertar);
                nodoActual.setValor(j, valoraInsertar);
                // cambiamos de estado si la clave se inserto
                claveaInsertadoEnNodoActual=true;
            }
            // si no es menor entonces incrementa
            j++;
        }
        // si todavia no se inserto entonces debemos insertar en la ultima posicion del nodoActual
        if(claveaInsertadoEnNodoActual==false){
            nodoActual.setClave(j, claveaInsertar);
            nodoActual.setValor(j, valoraInsertar);
        }
    }
    protected int obtenerPosicionDeClave(NodoMvias<K,V>nodoActual,K claveAbuscar){
      for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
          K claveActual=nodoActual.getClave(i);
          if(claveAbuscar.compareTo(claveActual)==0){
              return i;
          }
      }
      return ArbolMViasBusqueda.POSICION_NO_VALIDA;
    }
    protected int obtenerPosicionPorDondeBajar(NodoMvias<K,V> nodoActual,K claveAbuscar){
      for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
          K claveActual=nodoActual.getClave(i);
          if(claveAbuscar.compareTo(claveActual)<0){
              return i;
          }
      }
      return nodoActual.cantidadDeClavesNoVacias();

    }
    //EJERCICIO 11
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
     private NodoMvias<K,V> eliminar(NodoMvias<K,V> nodoActual,K claveEliminar){
      for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
        K claveActual = nodoActual.getClave(i);
        if(claveEliminar.compareTo(claveActual)==0){
            if(nodoActual.esHoja()){
                this.eliminarClaveValorDePosicion(nodoActual,i);
                if(nodoActual.cantidadDeClavesNoVacias()==0){
                    return NodoMvias.nodoVacio();
                }
                return nodoActual;
            }
            K claveReemplazo;
           if(this.hayHijosMasAdelanteDeLaPosicion(nodoActual,i)){
               claveReemplazo=this.obtenerClaveSucesoraInOrden(nodoActual,claveEliminar);
            }else{
               claveReemplazo=this.obtenerClavePredecesorInOrden(nodoActual,claveEliminar);
            }
            V valorAsociadoAClaveReemplazo=this.buscar(claveReemplazo);

            nodoActual=eliminar(nodoActual,claveReemplazo);
            nodoActual.setClave(i, claveReemplazo);
            nodoActual.setValor(i, valorAsociadoAClaveReemplazo);

            return nodoActual;
        }
        if (claveEliminar.compareTo(claveActual)<0){
            NodoMvias<K,V> supuestoNuevoHijo =eliminar(nodoActual.getHijos(i),claveEliminar);
            nodoActual.setHijos(i, supuestoNuevoHijo);
            return nodoActual;
        }
      }
      NodoMvias<K,V> supuestoNuevoHijo =eliminar(nodoActual.getHijos(orden-1),claveEliminar);
            nodoActual.setHijos((orden-1), supuestoNuevoHijo);
            return nodoActual;
     }
    private K obtenerClaveSucesoraInOrden(NodoMvias<K,V> nodoActual,K claveAEliminar){

        while(!NodoMvias.esNodoVacio(nodoActual)){
            boolean huboCambioDeNodoActual=false;
            for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
                if(nodoActual.getClave(i).compareTo(claveAEliminar)>0){
                    if(!nodoActual.esHijoVacio(i)){
                        nodoActual=nodoActual.getHijos(i);
                        huboCambioDeNodoActual=true;
                    }else{
                        return nodoActual.getClave(i);
                    }
                }
            }
            if(!huboCambioDeNodoActual){
                nodoActual=nodoActual.getHijos(orden-1);
            }

        }
        return null;
    }
    private K obtenerClavePredecesorInOrden(NodoMvias<K,V> nodoActual,K claveAEliminar){
        int posicionClaveActual=obtenerPosicionClave(nodoActual,claveAEliminar);
        if(!nodoActual.esHijoVacio(posicionClaveActual)){
            NodoMvias<K,V> nodoPredecesor=obtenerUltimoNodoPredecesor(nodoActual.getHijos(posicionClaveActual));
            return nodoPredecesor.getClave(nodoPredecesor.cantidadDeClavesNoVacias()-1);
        }else{
            return nodoActual.getClave(posicionClaveActual-1);
        }
    }

    private NodoMvias<K,V> obtenerUltimoNodoPredecesor(NodoMvias<K,V> nodoPredecesorActual){
        if(nodoPredecesorActual.esHoja()){
            return nodoPredecesorActual;
        }else{  // tiene hijos
            //preguntamos si el ultimo hijo es vacio
            //      si es entonces devolvemos el mismo nodo porque contiene
            //      la clave predecesor
            if(nodoPredecesorActual.esHijoVacio(orden-1)){
                return nodoPredecesorActual;
            }else{
                NodoMvias<K,V> supuestoNodoPredecesor=obtenerUltimoNodoPredecesor(nodoPredecesorActual.getHijos(orden-1));
                return supuestoNodoPredecesor;
            }
        }
    }
    private int obtenerPosicionClave(NodoMvias<K,V> nodoActual,K claveABuscar){
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            K claveActual=nodoActual.getClave(i);
            if(claveActual.compareTo(claveABuscar)==0){
                return i;
            }
        }
        return 0;
    }
    private void eliminarClaveValorDePosicion(NodoMvias<K,V> nodoActual, int posicionClaveAEliminar){
        // si la clave esta en el ultima posicion
        if(posicionClaveAEliminar==orden-2){
            nodoActual.setClave(posicionClaveAEliminar, null);
            nodoActual.setValor(posicionClaveAEliminar, null);
        }else{
            for(int i=posicionClaveAEliminar;i<nodoActual.cantidadDeClavesNoVacias()-1;i++){
                nodoActual.setClave(i,nodoActual.getClave(i+1));
                nodoActual.setValor(i,nodoActual.getValor(i+1));
            }
            nodoActual.setClave(nodoActual.cantidadDeClavesNoVacias()-1, null);
            nodoActual.setValor(nodoActual.cantidadDeClavesNoVacias()-1, null);
        }
    }
    private boolean hayHijosMasAdelanteDeLaPosicion(NodoMvias<K,V> nodoActual,int posicion){
        for(int i=posicion+1;i<=orden-1;i++){
            if(!nodoActual.esHijoVacio(i)){
                return true;
            }
        }
        return false;
    }
    @Override
    public V buscar(K claveBuscar) throws NullPointerException {
       if(claveBuscar == null){
          throw new NullPointerException("Clave a buscar no puede ser nula");
       }
       NodoMvias<K,V>nodoActual=this.raiz;
       while(!NodoMvias.esNodoVacio(nodoActual)){
           boolean huboCambioDeNodoActual=false;
           for(int i=0;i<nodoActual.cantidadDeClavesNoVacias() && !huboCambioDeNodoActual;i++){
           K claveNodoActual=nodoActual.getClave(i);

            if(claveBuscar.compareTo(claveNodoActual)==0){
              return nodoActual.getValor(i);
             }

           if(claveBuscar.compareTo(claveNodoActual)<0){
              nodoActual=nodoActual.getHijos(i);
              huboCambioDeNodoActual=true;
             }
           }
          if(huboCambioDeNodoActual){
              nodoActual=nodoActual.getHijos(orden-1);
          }
       }
       return null;
    }



    @Override
    public int altura() {
       return altura(this.raiz);
  }
  private int altura(NodoMvias<K,V>nodoActual){
      if(NodoMvias.esNodoVacio(nodoActual)){
          return 0;
      }
      int alturaMayorEntreLosHijos=0;
      for(int i=0;i<orden;i++){
      int alturaDeHijoActual=altura(nodoActual.getHijos(i));
      if(alturaDeHijoActual>alturaMayorEntreLosHijos){
           alturaMayorEntreLosHijos=alturaDeHijoActual;
        }
      }
       return alturaMayorEntreLosHijos+1;
    }
    //EJERCICIO 15
    //
    @Override
    public boolean NodosCompletosEnNivel(int niv){
        return NodosComletosEnNivel(this.raiz,niv,0);
    }
    //EJERCICIO 17
    @Override
    public boolean sonSimilares(ArbolMViasBusqueda<K, V> arbol2) {
        Queue<NodoMvias<K,V>>cola1=new LinkedList<>();
        Queue<NodoMvias<K,V>>cola2=new LinkedList<>();
        if(!this.esArbolVacio() && !arbol2.esArbolVacio()){
            cola1.offer(this.raiz);
            cola2.offer(arbol2.raiz);
            while(!cola1.isEmpty()&& !cola2.isEmpty()){
                NodoMvias<K,V>nodo1=cola1.poll();
                NodoMvias<K,V>nodo2=cola2.poll();
                int cantidad1=nodo1.cantidadDeClavesNoVacias();
                //int cantidad2=nodo2.cantidadDeClavesNoVacias();*/
                for(int i=0;i<orden-1;i++){
                    if(!NodoMvias.esNodoVacio(nodo1.getHijos(i))&& !NodoMvias.esNodoVacio(nodo2.getHijos(i)) ){
                        cola1.offer(nodo1.getHijos(i));
                        cola2.offer(nodo2.getHijos(i));
                    }else if(NodoMvias.esNodoVacio(nodo1.getHijos(i)) && NodoMvias.esNodoVacio(nodo2.getHijos(i))){
                    }else{
                        return false;
                    }
                }
            }
            if(cola1.isEmpty() && cola2.isEmpty()){
                return true;
            }
        }else{
            if(this.esArbolVacio() && arbol2.esArbolVacio()){
                return true;
            }
        }
        return false;
    }




    private boolean NodosComletosEnNivel(NodoMvias<K,V>nodoActual,int niv,int n){
        if(NodoMvias.esNodoVacio(nodoActual)){
            return false;
        }
       boolean valor=true;
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            NodosComletosEnNivel(nodoActual.getHijos(i),niv,n+1);
            if(niv==n){
                if(!nodoActual.esClaveVacia(i)){
                    valor=true;
                }else{
                    valor=false;
                }
            }
        }
        NodosComletosEnNivel(nodoActual.getHijos(nodoActual.cantidadDeClavesNoVacias()),niv,n+1);
        return true;
    }


    @Override
    public void vaciarArbol() {
       this.raiz=NodoMvias.nodoVacio();
    }

    @Override
    public List<K> recorridoPorNiveles() {
        return null;
    }

    @Override
    public boolean esArbolVacio() {
       return NodoMvias.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoInOrden() {
        return null;
    }

    @Override
    public List<K> recorridoPostOrden() {
        return null;
    }

    @Override
    public List<K> recorridoPostOrdenV2() {
        return null;
    }

    @Override
    public int cantidadDeNodosConUnHIjoNoVacio() {
        return 0;
    }

    @Override
    public int cantidadDeHijosVaciosConInOrden() {
        return 0;
    }

    @Override
    public boolean verificarSiSonArbolesSimilares(ArbolBinario<K,V>nuevoArbol) {
        return false;
    }



    public static void main(String ar[]) throws NullPointerException, ExcepcionOrdenInvalido, ExcepcionClaveNoExiste {
        IArbolBusqueda<Integer,String> arbol=new ArbolMViasBusqueda<>();
      //EJERCICIO 10
        arbol.insertar(3,"dilker");
        arbol.insertar(2,"deglis");
        arbol.insertar(1,"delgar");
        arbol.insertar(4,"dilker");
        arbol.insertar(5,"deglis");
        arbol.insertar(6,"delgar");
        arbol.insertar(7,"dilker");
        arbol.insertar(16,"deglis");
        arbol.insertar(15,"delgar");
        arbol.insertar(14,"deglis");
        arbol.insertar(13,"delgar");
        arbol.insertar(12,"delgar");
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        IArbolBusqueda<Integer,String> arbol2=new ArbolMViasBusqueda<>();
        arbol.insertar(3,"dilker");
        arbol.insertar(2,"deglis");
        arbol.insertar(1,"delgar");
        arbol.insertar(4,"dilker");
        arbol.insertar(5,"deglis");
        arbol.insertar(6,"delgar");
        arbol.insertar(7,"dilker");
        arbol.insertar(16,"deglis");
        arbol.insertar(15,"delgar");
        arbol.insertar(14,"deglis");
        arbol.insertar(13,"delgar");
        arbol.insertar(12,"delgar");
        //EJERCICIO 11
        arbol.eliminar(14);
        //EJERCICIO 15
        System.out.println(arbol.NodosCompletosEnNivel(2));
        //EJERCICIO 17
        System.out.println(arbol.sonSimilares((ArbolMViasBusqueda<Integer, String>) arbol2));
    }
    }
