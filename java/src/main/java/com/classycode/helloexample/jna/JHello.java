package com.classycode.helloexample.jna;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

public interface JHello extends Library {

    interface RequestVerificationCallback extends Callback {

        void callback(UserConsentVerificationResult result);
    }

    UserConsentAvailability user_consent_verifier_check_availability();

    WinNT.HRESULT user_consent_verifier_request_verification(
            String message,
            WinDef.HWND hWND,
            RequestVerificationCallback callback
    );
}
