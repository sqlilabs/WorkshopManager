package models.utils.helpers;

import org.fest.assertions.Assertions;
import org.junit.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/9/13
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilesUtilsTest {


    @Test
    public void testFastCopyFileCore() throws Exception {
        File source = new File("LICENSE");
        File target = new File("LICENSE_Copy");

        // before action
        Assertions.assertThat( source ).exists();
        Assertions.assertThat( target ).doesNotExist();

        // action
        FilesUtils.fastCopyFileCore( source, target);

        // after action
        Assertions.assertThat( target ).exists();

        //cleaning
        target.delete();
    }
}
