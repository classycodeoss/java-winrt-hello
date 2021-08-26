package com.classycode.helloexample.jna;

import com.sun.jna.Library;

public interface JHello extends Library {

    UserConsentAvailability user_consent_verifier_check_availability();
}
