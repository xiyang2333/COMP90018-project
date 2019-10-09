import com.unimelb.studypartner.common.PictureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by xiyang on 2019/10/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PictureService.class)
public class PictureTest {
    private static final Base64.Encoder encoder = Base64.getEncoder();

    @Autowired
    PictureService pictureService;

    @Test
    public void test(){
        File file=new File("C:\\Users\\xiyang\\Pictures\\icon.png");

        int length = (int) file.length();
        byte[] data = new byte[length];
        try {
            new FileInputStream(file).read(data);
            String photo = encoder.encodeToString(data);
            String path = pictureService.uploadPic(photo);
            System.out.println(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void test1(){
        try {
            System.out.println(pictureService.getPic("./images/a31f97e1-7830-46d6-8752-10a7e16436dc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
