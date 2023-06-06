    String accountId = "123456789101";
        {Regions.EU_WEST_1.getName(), "aws", "eu-west-1.amazonaws.com"},
        {Regions.US_GOV_EAST_1.getName(), "aws-us-gov",
            "us-gov-east-1.amazonaws.com"},
        {Regions.CN_NORTH_1.getName(), "aws-cn", "cn-north-1.amazonaws.com"},
      String endpoint = testPair[2];

      // arn:partition:service:region:account-id:resource-type/resource-id
      String arn = String.format("arn:%s:s3:%s:%s:accesspoint/%s", partition, region, accountId,
          accessPoint);
      ArnResource resource = ArnResource.accessPointFromArn(arn);
      assertEquals("Arn does not match", arn, resource.getFullArn());
      assertEquals("Account Id does not match", accountId, resource.getOwnerAccountId());
      Assertions.assertThat(resource.getEndpoint())
          .describedAs("Endpoint does not match")
          .contains(endpoint);
