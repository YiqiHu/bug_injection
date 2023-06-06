import java.util.regex.Pattern;

  private static final Pattern SLASHES = Pattern.compile("/+");


    // Remove duplicated slashes.
    path = SLASHES.matcher(path).replaceAll("/");
