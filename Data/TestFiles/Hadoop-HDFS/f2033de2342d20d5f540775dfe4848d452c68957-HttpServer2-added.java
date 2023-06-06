      } catch (IOException ex) {
        if (!(ex instanceof BindException)
            && !(ex.getCause() instanceof BindException)) {
          throw ex;
        }
