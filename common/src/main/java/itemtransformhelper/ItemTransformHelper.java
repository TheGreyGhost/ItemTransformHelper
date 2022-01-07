package itemtransformhelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemTransformHelper {

    public static final String MODID = "itemtransformhelper";

    public static final Logger logger = LogManager.getLogger(MODID);

    public static void init() {
        StartupCommon.init();
    }

}
