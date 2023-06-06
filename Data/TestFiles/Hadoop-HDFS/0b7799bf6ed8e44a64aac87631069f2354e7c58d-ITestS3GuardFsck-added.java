    assumeTrue("FS needs to have a DynamoDB metadatastore.",
        fs.getMetadataStore() instanceof DynamoDBMetadataStore);
