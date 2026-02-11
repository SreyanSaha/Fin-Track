package com.fin.mail;

public final class MailText {
    private final String clientOtpMailSubject="Your OTP for Account Verification";
    private final String clientOtpMail="""
		Dear User,

			We received a request to verify your identity.

			Your One-Time Password (OTP) is:

			👉 OTP: ${otp}

			This code is valid for the next 1 minute.

			For your security:
				• Do not share this OTP with anyone
				• If you did not request this OTP, please ignore this message  

		Regards,  
			FinTrack Team
	""";

    private final String clientRegistrationMailSubject="Your ByteFilms Account Username";
    private final String clientRegistrationMail="""
		Dear User,

			Your identity has been successfully verified.

			As requested, here is your FinTrack account username:

			👉 Username: ${username}
			(Use this username to log in to your account)
	
			For your convenience, once you log in, your session will remain active for 48 hours,
			so you won’t need to log in again during this period.

			If you did not request this information, please contact us immediately.

		Regards,
			FinTrack Team
	""";

    public final String getClientOtpMailSubject() {
        return clientOtpMailSubject;
    }
    public final String getClientOtpMail() {
        return clientOtpMail;
    }
    public final String getClientRegistrationMailSubject() {
        return clientRegistrationMailSubject;
    }
    public final String getClientRegistrationMail() {
        return clientRegistrationMail;
    }
}
