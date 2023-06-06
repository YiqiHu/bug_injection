        assertExceptionContains("LDAP response read timed out, timeout used:" +
            connectionTimeoutMs + "ms", ne);
        assertExceptionContains("LDAP response read timed out, timeout used:" +
            readTimeoutMs + "ms", ne);
