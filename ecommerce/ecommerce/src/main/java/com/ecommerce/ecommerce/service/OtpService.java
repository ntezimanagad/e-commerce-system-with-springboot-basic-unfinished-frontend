package com.ecommerce.ecommerce.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.model.Otp;
import com.ecommerce.ecommerce.repository.OtpRepository;

@Service
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private EmailService emailService;

    private static final int EXPIRE_IN = 5;
    public void generateOtp(String email, String purpose){
        String otpCode = String.format("%06d", new Random().nextInt(999999));
        Instant now = Instant.now();
        Instant expire = now.plus(EXPIRE_IN, ChronoUnit.MINUTES);

        Otp otp = new Otp(email, otpCode, now, expire, purpose);
        otpRepository.save(otp);

        emailService.sendEmail(email, "Your OTP Code", "Your OTP is " + otpCode);
    }

    public boolean validateOtp(String email, String otpCode, String purpose){
        Optional<Otp> optionalOtp = otpRepository.findByEmailAndPurpose(email, purpose);
        if (optionalOtp.isEmpty()) {
            return false;
        }
        Otp otp = optionalOtp.get();
        if (otp.getExpiredAt().isBefore(Instant.now())) {
            deleteOtp(email, purpose);
            return false;
        }

        return otp.getOtpCode().equals(otpCode);
    }

    public void deleteOtp(String email, String purpose){
        otpRepository.findByEmailAndPurpose(email, purpose)
            .ifPresent(otpRepository::delete);
    }
}
