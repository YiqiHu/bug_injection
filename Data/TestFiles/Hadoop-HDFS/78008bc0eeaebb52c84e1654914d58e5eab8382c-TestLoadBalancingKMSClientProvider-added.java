import java.net.SocketException;
import javax.net.ssl.SSLException;
        CommonConfigurationKeysPublic.KMS_CLIENT_FAILOVER_MAX_RETRIES_KEY, 5);
        .thenThrow(new SSLException("p1"))
        .thenThrow(new ConnectException("p2"))
        .thenThrow(new SocketException("p1"));
    verify(p1, Mockito.times(3)).createKey(Mockito.eq(keyName),
    verify(p2, Mockito.times(2)).createKey(Mockito.eq(keyName),
