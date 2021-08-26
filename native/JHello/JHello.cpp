#include "pch.h"

#include "JHello.h"

using namespace winrt;
using namespace Windows::Foundation;
using namespace Windows::Security::Credentials::UI;

// Convert an UTF8 string to a wide Unicode String
static std::wstring utf8_decode(const std::string& str)
{
    if (str.empty()) return std::wstring();
    int size_needed = MultiByteToWideChar(CP_UTF8, 0, &str[0], (int)str.size(), NULL, 0);
    std::wstring wstrTo(size_needed, 0);
    MultiByteToWideChar(CP_UTF8, 0, &str[0], (int)str.size(), &wstrTo[0], size_needed);
    return wstrTo;
}

int user_consent_verifier_check_availability()
{
	UserConsentVerifierAvailability result
		= UserConsentVerifier::CheckAvailabilityAsync().get();
	return static_cast<int>(result);
}

int user_consent_verifier_request_verification(
	const char *msg,
	HWND hWND,
	request_verification_cb_t callback
)
{
    auto msg_w = utf8_decode(std::string(msg));
    hstring msg_h(msg_w);
    auto activation_factory = winrt::get_activation_factory<UserConsentVerifier>();
    winrt::com_ptr<IUserConsentVerifierInterop> interop{ activation_factory.as<IUserConsentVerifierInterop>() };
    winrt::guid iid_async_op_result{ winrt::guid_of<IAsyncOperation<UserConsentVerificationResult>>() };
    HSTRING msg_hs = static_cast<HSTRING>(get_abi(msg_h));
    winrt::com_ptr<IAsyncOperation<UserConsentVerificationResult>> com_async_op;
    HRESULT hr = interop->RequestVerificationForWindowAsync(
        hWND,
        msg_hs,
        iid_async_op_result,
        com_async_op.put_void());
    auto async_op = com_async_op.as<IAsyncOperation<UserConsentVerificationResult>>();
    async_op.Completed([callback](auto sender, auto status) {
        callback(static_cast<int>(sender.GetResults()));
        });
    return hr;


	callback(static_cast<int>(UserConsentVerificationResult::Verified));
	return S_OK;
}
