package co.com.fomag.lambda.system.infraestructure.region;

import software.amazon.awssdk.regions.Region;

public class RegionProvider {

    private static final String DEFAULT_REGION = "us-east-1";

    public static Region getRegion() {
        String envRegion = System.getenv("AWS_REGION");
        if (envRegion == null || envRegion.isBlank()) {
            return Region.of(DEFAULT_REGION); // fallback
        }
        return Region.of(envRegion);
    }
}
