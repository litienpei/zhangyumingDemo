package org.opens.jpa.vo;

import java.util.HashMap;

public class SuccessOrFail extends HashMap<String, Object> {

    private static final int FAIL_CODE = 300;

    private static final int SUCCESS_CODE = 200;

    private SuccessOrFail() {}

    public static SuccessOrFail success() {
        return new SuccessOrFail().put("code", 200);
    }

    @Override
    public SuccessOrFail put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
