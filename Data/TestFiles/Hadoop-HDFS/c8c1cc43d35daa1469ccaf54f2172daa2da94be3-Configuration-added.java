import java.net.URI;
import java.net.URISyntaxException;
            File baseFile;

            try {
              baseFile = new File(new URI(name));
            } catch (IllegalArgumentException | URISyntaxException e) {
              baseFile = new File(name);
            }

            baseFile = baseFile.getParentFile();
