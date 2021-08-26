package com.classycode.helloexample.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;

/**
 * See: https://docs.microsoft.com/en-us/uwp/api/windows.security.credentials.ui.userconsentverificationresult?view=winrt-20348
 */
public enum UserConsentVerificationResult implements NativeMapped {

    Canceled(6),
    DeviceBusy(4),
    DeviceNotPresent(1),
    DisabledByPolicy(3),
    NotConfiguredForUser(2),
    RetriesExhausted(5),
    Verified(0);

    private final int code;

    UserConsentVerificationResult(int code) {
        this.code = code;
    }

    static UserConsentVerificationResult fromResult(int result) {
        for (UserConsentVerificationResult value : values()) {
            if (value.code == result) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Object fromNative(Object nativeValue, FromNativeContext context) {
        for (UserConsentVerificationResult value : values()) {
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
