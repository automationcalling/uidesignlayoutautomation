package automationcalling.commonutil;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static automationcalling.commonutil.Constant.SPECPATH;
import static com.galenframework.api.Galen.checkLayout;

public class GalenUtil {
    private LayoutReport layoutReport;

    public void verifyLayout(WebDriver driver, String specFile, List deviceType) {
        try {
            layoutReport=Galen.checkLayout(driver, SPECPATH+specFile, deviceType);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void reportUpdate(String testInfo,String layoutVerificationInfo) throws IOException, LayoutComaprisonException {
        List<GalenTestInfo> tests = new LinkedList<>();
        GalenTestInfo test = GalenTestInfo.fromString(testInfo);
        test.getReport().layout(layoutReport, layoutVerificationInfo);
        tests.add(test);
        new HtmlReportBuilder().build(tests, "target/galen-html-reports");
        if(layoutReport.errors()>0)
        {
            throw new LayoutComaprisonException("Image Comparison Failed");
        }
    }
}
