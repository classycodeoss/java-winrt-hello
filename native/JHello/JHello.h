#pragma once

#ifdef __cplusplus
extern "C" {
#endif
    typedef void (*request_verification_cb_t)(int value);

    // Export functions called through JNA
    __declspec(dllexport) int user_consent_verifier_check_availability();
    __declspec(dllexport) int user_consent_verifier_request_verification(
        const char* msg,
        HWND hWND,
        request_verification_cb_t callback
    );

#ifdef __cplusplus
}
#endif
