package mx.unam.ciencias.edd;

/**
 * Clase para fabricar generadores de huellas digitales.
 */
public class FabricaHuellasDigitales {

    /**
     * Identificador para fabricar la huella digital de Bob
     * Jenkins para cadenas.
     */
    public static final int BJ_STRING   = 0;
    /**
     * Identificador para fabricar la huella digital de GLib para
     * cadenas.
     */
    public static final int GLIB_STRING = 1;
    /**
     * Identificador para fabricar la huella digital de XOR para
     * cadenas.
     */
    public static final int XOR_STRING  = 2;

    /**
     * Regresa una instancia de {@link HuellaDigital} para cadenas.
     * @param identificador el identificador del tipo de huella
     *        digital que se desea.
     * @return una instancia de {@link HuellaDigital} para cadenas.
     * @throws IllegalArgumentException si recibe un identificador
     *         no reconocido.
     */
    public static HuellaDigital<String> getInstanciaString(int identificador) {
        // Aquí va su código.
	if(identificador < 0 || identificador > 2)
	    throw new IllegalArgumentException();
	if(identificador == 2)
	    return (string) -> {
		byte[] k = string.getBytes();
		int r = 0;
		int i = 0;
		int l = k.length;
		for(; l >= 4; i += 4, l -= 4)
		    r ^= (k[i] << 24)|(k[i+1] << 16)|(k[i+2] << 8)|(k[i+3]);
		int t = 0;
		switch(l){
		case 3: t |= k[i+2] << 8;
		case 2: t |= k[i+1] << 16;
		case 1: t |= k[i] << 24;
		}
		r ^= t;
		return r;
	    };
	if(identificador == 1)
	    return (string) -> {
		byte[] k = string.getBytes();
		int h = 5381;
		for(int i = 0; i < k.length; i++)
		    h = h*33 + k[i];
		return h;
	    };

	//----------------------------------------------------------------------
	
	return (string) -> {
	    byte[] k = string.getBytes();
	    int a, b, c, l, i;
	    l = k.length;
	    a = b = 0x9E3779B9;
	    c = 0xFFFFFFFF;
	    for(i = 0; l >= 12; i += 12, l -= 12){
		a += k[i] | (k[i+1] << 8) | (k[i+2] << 16) | (k[i+3] << 24);
		b += k[i+4] | (k[i+5] << 8) | (k[i+6] << 16) | (k[i+7] << 24);
		c += k[i+8] | (k[i+9] << 8) | (k[i+10] << 16) | (k[i+11] << 24);
		//mezcla
		a -= b; a -= c; a ^= (c >>> 13);
		b -= c; b -= a; b ^= (a << 8);
		c -= a; c -= b; c ^= (b >>> 13);
		a -= b; a -= c; a ^= (c >>> 12);
		b -= c; b -= a; b ^= (a << 16);
		c -= a; c -= b; c ^= (b >>> 5);
		a -= b; a -= c; a ^= (c >>> 3);
		b -= c; b -= a; b ^= (a << 10);
		c -= a; c -= b; c ^= (b >>> 15);
	    }
	    c += k.length;
	    switch(l){
	    case 11: c += k[i+10] << 24;
	    case 10: c += k[i+9] << 16;
	    case 9: c += k[i+8] << 8;
	    case 8: b += k[i+7] << 24;
	    case 7: b += k[i+6] << 16;
	    case 6: b += k[i+5] << 8;
	    case 5: b += k[i+4];
	    case 4: a += k[i+3] << 24;
	    case 3: a += k[i+2] << 16;
	    case 2: a += k[i+1] << 8;
	    case 1: a += k[i];
	    }
	    //mezcla
	    a -= b; a -= c; a ^= (c >>> 13);
	    b -= c; b -= a; b ^= (a << 8);
	    c -= a; c -= b; c ^= (b >>> 13);
	    a -= b; a -= c; a ^= (c >>> 12);
	    b -= c; b -= a; b ^= (a << 16);
	    c -= a; c -= b; c ^= (b >>> 5);
	    a -= b; a -= c; a ^= (c >>> 3);
	    b -= c; b -= a; b ^= (a << 10);
	    c -= a; c -= b; c ^= (b >>> 15);
	    return c;
	};
    }
}
