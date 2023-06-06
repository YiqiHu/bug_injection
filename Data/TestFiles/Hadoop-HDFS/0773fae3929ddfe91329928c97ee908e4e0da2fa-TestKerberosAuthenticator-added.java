import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.reflect.FieldUtils;
import org.mockito.Mockito;
import java.util.Arrays;
  @Test(timeout = 60000)
  public void testNegotiate() throws NoSuchMethodException, InvocationTargetException,
          IllegalAccessException, IOException {
    KerberosAuthenticator kerberosAuthenticator = new KerberosAuthenticator();

    HttpURLConnection conn = Mockito.mock(HttpURLConnection.class);
    Mockito.when(conn.getHeaderField(KerberosAuthenticator.WWW_AUTHENTICATE)).
            thenReturn(KerberosAuthenticator.NEGOTIATE);
    Mockito.when(conn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED);

    Method method = KerberosAuthenticator.class.getDeclaredMethod("isNegotiate",
            HttpURLConnection.class);
    method.setAccessible(true);

    Assert.assertTrue((boolean)method.invoke(kerberosAuthenticator, conn));
  }

  @Test(timeout = 60000)
  public void testNegotiateLowerCase() throws NoSuchMethodException, InvocationTargetException,
          IllegalAccessException, IOException {
    KerberosAuthenticator kerberosAuthenticator = new KerberosAuthenticator();

    HttpURLConnection conn = Mockito.mock(HttpURLConnection.class);
    Mockito.when(conn.getHeaderField("www-authenticate"))
            .thenReturn(KerberosAuthenticator.NEGOTIATE);
    Mockito.when(conn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED);

    Method method = KerberosAuthenticator.class.getDeclaredMethod("isNegotiate",
            HttpURLConnection.class);
    method.setAccessible(true);

    Assert.assertTrue((boolean)method.invoke(kerberosAuthenticator, conn));
  }

  @Test(timeout = 60000)
  public void testReadToken() throws NoSuchMethodException, IOException, IllegalAccessException,
          InvocationTargetException {
    KerberosAuthenticator kerberosAuthenticator = new KerberosAuthenticator();
    FieldUtils.writeField(kerberosAuthenticator, "base64", new Base64(), true);

    Base64 base64 = new Base64();

    HttpURLConnection conn = Mockito.mock(HttpURLConnection.class);
    Mockito.when(conn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED);
    Mockito.when(conn.getHeaderField(KerberosAuthenticator.WWW_AUTHENTICATE))
            .thenReturn(KerberosAuthenticator.NEGOTIATE + " " +
                    Arrays.toString(base64.encode("foobar".getBytes())));

    Method method = KerberosAuthenticator.class.getDeclaredMethod("readToken",
            HttpURLConnection.class);
    method.setAccessible(true);

    method.invoke(kerberosAuthenticator, conn); // expecting this not to throw an exception
  }

  @Test(timeout = 60000)
  public void testReadTokenLowerCase() throws NoSuchMethodException, IOException,
          IllegalAccessException, InvocationTargetException {
    KerberosAuthenticator kerberosAuthenticator = new KerberosAuthenticator();
    FieldUtils.writeField(kerberosAuthenticator, "base64", new Base64(), true);

    Base64 base64 = new Base64();

    HttpURLConnection conn = Mockito.mock(HttpURLConnection.class);
    Mockito.when(conn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED);
    Mockito.when(conn.getHeaderField("www-authenticate"))
            .thenReturn(KerberosAuthenticator.NEGOTIATE +
                    Arrays.toString(base64.encode("foobar".getBytes())));

    Method method = KerberosAuthenticator.class.getDeclaredMethod("readToken",
            HttpURLConnection.class);
    method.setAccessible(true);

    method.invoke(kerberosAuthenticator, conn); // expecting this not to throw an exception
  }
