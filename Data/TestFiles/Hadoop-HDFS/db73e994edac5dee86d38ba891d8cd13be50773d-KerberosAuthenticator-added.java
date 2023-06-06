      HttpURLConnection conn = null;
        conn = token.openConnection(url, connConfigurator);
      } finally {
        if (conn != null) {
          conn.disconnect();
        }
