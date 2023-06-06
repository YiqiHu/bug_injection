import com.amazonaws.regions.RegionUtils;
    return RegionUtils.getRegion(accessPointRegionKey)
        .getServiceEndpoint("s3");
