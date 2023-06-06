    ScanResult result = execute(
        new ScanArgsBuilder()
            .withSourceFS(fs)
            .withPath(path)
            .withDoPurge(clean)
            .withMinMarkerCount(expectedMin)
            .withMaxMarkerCount(expectedMax)
            .withLimit(limit)
            .withNonAuth(nonAuth)
            .build());
