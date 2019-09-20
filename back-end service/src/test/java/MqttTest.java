import com.unimelb.studypartner.common.MqttPushClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by xiyang on 2019/9/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqttPushClient.class)
public class MqttTest {

    @Test
    public void test(){
        MqttPushClient client = MqttPushClient.getInstance();
        client.publish("aaaa","bbbbbbbbbbbbbbb cccccccccccccccc");
    }
}
