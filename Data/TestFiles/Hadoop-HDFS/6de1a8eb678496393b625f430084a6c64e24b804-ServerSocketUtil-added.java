import java.net.InetAddress;
      try (ServerSocket s = new ServerSocket(tryPort, 50,
          InetAddress.getLoopbackAddress())) {
