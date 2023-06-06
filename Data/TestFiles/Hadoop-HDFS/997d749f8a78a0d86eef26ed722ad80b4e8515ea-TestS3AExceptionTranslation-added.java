    // Verifying that the translated exception have the correct error message.
    IOException ioe = translateException("test", "/", exception);
    assertExceptionContains(exception.getMessage(), ioe,
        "Translated Exception should contain the error message of the "
            + "actual exception");
    return verifyExceptionClass(clazz, ioe);
