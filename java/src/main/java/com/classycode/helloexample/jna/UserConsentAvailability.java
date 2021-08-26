package com.classycode.helloexample.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;

/**
 * See: https://docs.microsoft.com/en-us/uwp/api/windows.security.credentials.ui.userconsentverifieravailability?view=winrt-20348
 */
public enum UserConsentAvailability implements NativeMapped {

    DeviceBusy(4),
    DeviceNotPresent(1),
    DisabledByPolicy(3),
    NotConfiguredForUser(2),
    Available(0);

    private final int code;

    UserConsentAvailability(int code) {
        this.code = code;
    }

    @Override
    public Object fromNative(Object nativeValue, FromNativeContext context) {
        for (UserConsentAvailability value : values()) {
            if (value.code == (Integer)nativeValue) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Object toNative() {
        return code;
    }

    @Override
    public Class<?> nativeType() {
        return Integer.class;
    }
}
