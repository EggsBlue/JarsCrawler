package com.yixin;

import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;

@SetupBy(value = MainSetup.class)
@Modules(scanPackage = true, packages = { "com.yixin" })
@Encoding(input = org.nutz.lang.Encoding.UTF8, output = org.nutz.lang.Encoding.UTF8)
public class MainModule {
}
