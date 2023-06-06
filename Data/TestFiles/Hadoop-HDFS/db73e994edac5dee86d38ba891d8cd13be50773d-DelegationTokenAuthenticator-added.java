    HttpURLConnection conn = null;
      conn = aUrl.openConnection(url, token);
      if (conn != null) {
        conn.disconnect();
      }
