    LOG.info(
        "Login successful for user {} using keytab file {}. Keytab auto"
            + " renewal enabled : {}",
        user, new File(path).getName(), isKerberosKeyTabLoginRenewalEnabled());
