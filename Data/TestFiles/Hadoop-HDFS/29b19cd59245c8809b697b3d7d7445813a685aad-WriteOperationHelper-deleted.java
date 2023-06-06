      Long offset) {
    checkArgument(partNumber > 0 && partNumber <= 10000,
        "partNumber must be between 1 and 10000 inclusive, but is %s",
        partNumber);
