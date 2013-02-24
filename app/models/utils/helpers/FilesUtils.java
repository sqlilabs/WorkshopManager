package models.utils.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Helpers methods relative to Files
 * 
 * @author ychartois
 */
public class FilesUtils {

	/**
	 * Use nio to fast copy a file from a source to a specified target
	 * 
	 * @param source the source file
	 * @param target the target file
	 * 
	 * @return true if the file is copied, false if the operation encounters an exception
	 */
	public static boolean fastCopyFileCore( File source, File target ) {
        try {
            // Getting file channels
            FileChannel sourceChannel               =   new FileInputStream(source).getChannel();
            FileChannel targetChannel               =   new FileOutputStream(target).getChannel();

            // JavaVM does its best to do this as native I/O operations.
            targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

            targetChannel.close();
            sourceChannel.close();

            return true ;

        } catch (FileNotFoundException ex) {
            return false;

        } catch (IOException ex) {
            return false;

        }
    }
	
	
	/**
	 * <constructor
	 */
	private FilesUtils() {
		super();
	}

}
