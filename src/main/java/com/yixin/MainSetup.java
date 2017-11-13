package com.yixin;

import com.yixin.domain.Laucher;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MainSetup implements Setup{
    public void init(NutConfig nc) {
        new Laucher().run();
    }

    public void destroy(NutConfig nc) {

    }
}
