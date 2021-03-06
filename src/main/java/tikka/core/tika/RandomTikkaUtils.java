package tikka.core.tika;

import org.apache.tika.Tika;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomTikkaUtils {


    private static final Tika TIKA = new Tika();
        private static final Logger LOGGER = Logger.getLogger("RandomTikkaUtils");

    public static Map<String, String> getFileMetaData(File file) throws Exception {
        Map<String, String> metaDataPairs = new HashMap<>();
        Metadata metadata = new Metadata();
        TIKA.parse(file, metadata);
        for (String metadataKey : metadata.names()) {
            metaDataPairs.put(metadataKey, metadata.get(metadataKey));
        }
        return metaDataPairs;
    }

    public static String getSpecificMetadata(File file, String metaDataName) throws Exception {
        Metadata metadata = new Metadata();
        TIKA.parse(file, metadata);
        return metadata.get(metaDataName);
    }

    public static String getFileContentType(File file) throws Exception {
        return TIKA.detect(file);
    }

    public static String getFileContent(File file) throws Exception {
        Parser parser = new AutoDetectParser();
        InputStream stream = TikaInputStream.get(Paths.get(file.toURI()));
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();

        parser.parse(stream, handler, metadata, parseContext);

        return handler.toString();
    }

    public static Metadata getMetaData(File file) {
        Metadata metadata = new Metadata();
        try {
                LOGGER.log(Level.INFO, "Initiating parsing process for [ " + file.getName() + " ] ");
            TIKA.parse(file, metadata);
                LOGGER.log(Level.INFO, "Parsing Process for [ " + file.getName() + " ] has been complete");
        } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error", e);
        }
        return metadata;
    }
}
