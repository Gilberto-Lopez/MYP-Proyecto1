package mx.unam.ciencias.edd;

/**
 * Clase para manipular arreglos genéricos de elementos comparables.
 */
public class Arreglos {

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] a) {
	intercambia(a, 0, a.length/2);
	quickSort(a, 0, a.length - 1);
	/* Si se quiere usar la implementación iterativa de QuickSort
	 * puede hacerse con la siguiente llamada: */
	//quickSortIterativo(a);
    }

    /* Metodo auxiliar para quickSort(). */
    private static <T extends Comparable<T>> void quickSort(T[] a, int ini,
							    int fin){
	if(ini >= fin) return;
	int i = ini + 1, j = fin;
	while(i < j)
	    if(a[i].compareTo(a[ini]) > 0 && a[j].compareTo(a[ini]) < 0)
		intercambia(a, i++, j--);
	    else if(a[i].compareTo(a[ini]) <= 0)
		i++;
	    else
		j--;
	if(a[i].compareTo(a[ini]) > 0)
	   i--;
	intercambia(a, ini, i);
	quickSort(a, ini, i - 1);
	quickSort(a, i + 1, fin);
    }

    /* Implementación iterativa de QuickSort. */
    private static <T extends Comparable<T>> void quickSortIterativo(T[] a){
	Pila<Integer> stack = new Pila<>();
	intercambia(a, 0, a.length/2);
	stack.mete(0);
	stack.mete(a.length -1);
	while(!stack.esVacia()){
	    int fin = stack.saca().intValue();
	    int ini = stack.saca().intValue();
	    if(ini >= fin) continue;
	    int i = ini + 1, j = fin;
	    while(i < j)
	        if(a[i].compareTo(a[ini]) > 0 && a[j].compareTo(a[ini]) < 0)
	            intercambia(a, i++, j--);
	        else if(a[i].compareTo(a[ini]) <= 0)
		    i++;
	        else
		    j--;
	    if(a[i].compareTo(a[ini]) > 0)
	       i--;
	    intercambia(a, ini, i);
	    stack.mete(ini);
	    stack.mete(i - 1);
	    stack.mete(i + 1);
	    stack.mete(fin);
	}
    }

    /* Metodo auxiliar para intercambiar el pivote con un
     * elemento del arreglo. */
    private static <T> void intercambia(T[] a, int p, int i){
	T pivote = a[p];
	a[p] = a[i];
	a[i] = pivote;
    }
    
    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] a) {
	int min, n = a.length;
	for(int i = 0; i < n - 1; i++){
	    min = i;
	    for(int j = i + 1; j < n; j++)
		if(a[j].compareTo(a[min]) < 0)
		    min = j;
	    if(min != i)
		intercambia(a, i, min);
	}       
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a el arreglo dónde buscar.
     * @param e el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int busquedaBinaria(T[] a, T e) {
	return busquedaBinaria(a, e, 0, a.length - 1);
    }

    /* Metodo auxiliar para busquedaBinaria(). */
    private static <T extends Comparable<T>> int
	busquedaBinaria(T[] a, T e, int min, int max){
	if(max < min)
	    return -1;
	else{
	    int m = (min + max)/2;
	    if(a[m].compareTo(e) > 0)
		return busquedaBinaria(a, e, min, m - 1);
	    else if(a[m].compareTo(e) < 0)
		return busquedaBinaria(a, e, m + 1, max);
	    else
		return m;
	}
    }
}
