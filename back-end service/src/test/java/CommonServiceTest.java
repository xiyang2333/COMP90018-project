import com.unimelb.studypartner.common.CommonService;
import com.unimelb.studypartner.common.GeoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * Created by xiyang on 2019/9/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonService.class)
public class CommonServiceTest {
    @Autowired
    CommonService commonService;

    @Test
    public void geoTest(){
        BigDecimal longitude = new BigDecimal("144.58");
        BigDecimal latitude = new BigDecimal("-37.5");

        GeoEntity geoEntity = commonService.calculateRoundGeoEntity(longitude,latitude, new BigDecimal(50000));

        System.out.println(geoEntity.getLongitudeLeft());
        System.out.println(geoEntity.getLongitudeRight());
        System.out.println(geoEntity.getLatitudeUp());
        System.out.println(geoEntity.getLatitudeDown());
    }
}
