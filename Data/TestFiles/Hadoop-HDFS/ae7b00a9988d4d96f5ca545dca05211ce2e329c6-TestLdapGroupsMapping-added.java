        assertExceptionContains("LDAP response read timed out, timeout used",
            ne);
        assertExceptionContains("" + connectionTimeoutMs, ne);
        assertExceptionContains("LDAP response read timed out, timeout used",
            ne);
        assertExceptionContains(""+ readTimeoutMs, ne);
