import java.net.SocketException;
import javax.net.ssl.SSLException;
        // SSLException can occur here because of lost connection
        if (ioe instanceof SSLException || ioe instanceof SocketException) {
