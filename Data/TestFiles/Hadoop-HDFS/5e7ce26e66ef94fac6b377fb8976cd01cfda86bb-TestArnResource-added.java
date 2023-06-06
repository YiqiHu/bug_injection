  private final static String MOCK_ACCOUNT = "123456789101";

        {Regions.EU_WEST_1.getName(), "aws"},
        {Regions.US_GOV_EAST_1.getName(), "aws-us-gov"},
        {Regions.CN_NORTH_1.getName(), "aws-cn"},
      ArnResource resource = getArnResourceFrom(partition, region, MOCK_ACCOUNT, accessPoint);
      assertEquals("Account Id does not match", MOCK_ACCOUNT, resource.getOwnerAccountId());
  @Test
  public void makeSureEndpointHasTheCorrectFormat() {
    // Access point (AP) endpoints are different from S3 bucket endpoints, thus when using APs the
    // endpoints for the client are modified. This test makes sure endpoint is set up correctly.
    ArnResource accessPoint = getArnResourceFrom("aws", "eu-west-1", MOCK_ACCOUNT,
        "test");
    String expected = "s3-accesspoint.eu-west-1.amazonaws.com";

    Assertions.assertThat(accessPoint.getEndpoint())
        .describedAs("Endpoint has invalid format. Access Point requests will not work")
        .isEqualTo(expected);
  }

  /**
   * Create an {@link ArnResource} from string components
   * @param partition - partition for ARN
   * @param region - region for ARN
   * @param accountId - accountId for ARN
   * @param resourceName - ARN resource name
   * @return ArnResource described by its properties
   */
  private ArnResource getArnResourceFrom(String partition, String region, String accountId,
      String resourceName) {
    // arn:partition:service:region:account-id:resource-type/resource-id
    String arn = String.format("arn:%s:s3:%s:%s:accesspoint/%s", partition, region, accountId,
        resourceName);

    return ArnResource.accessPointFromArn(arn);
  }

