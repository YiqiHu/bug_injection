import java.nio.channels.UnresolvedAddressException;
    }  catch (UnresolvedAddressException uae) {
      throw new UnknownHostException(uae.getMessage());
