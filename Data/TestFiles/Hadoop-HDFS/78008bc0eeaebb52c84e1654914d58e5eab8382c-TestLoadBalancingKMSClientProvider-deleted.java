        CommonConfigurationKeysPublic.KMS_CLIENT_FAILOVER_MAX_RETRIES_KEY, 3);
        .thenThrow(new ConnectException("p2"));
    verify(p1, Mockito.times(2)).createKey(Mockito.eq(keyName),
    verify(p2, Mockito.times(1)).createKey(Mockito.eq(keyName),
