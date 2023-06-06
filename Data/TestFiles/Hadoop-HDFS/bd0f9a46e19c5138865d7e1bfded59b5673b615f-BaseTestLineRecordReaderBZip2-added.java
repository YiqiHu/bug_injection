import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.hadoop.io.compress.bzip2.BZip2Utils;

import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.IO_FILE_BUFFER_SIZE_KEY;
import static org.apache.hadoop.io.compress.bzip2.BZip2TextFileWriter.BLOCK_SIZE;
import static org.junit.Assert.assertEquals;
