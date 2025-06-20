package com.rakesh.emailsender.config;

import com.rakesh.emailsender.util.AESUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${app.mail.encrypted.password}")
    private String encryptedPassword;

    @Value("${app.mail.secret.key}")
    private String secretKey;

    @Value("${app.mail.host}")
    private String host;

    @Value("${app.mail.port}")
    private int port;

    @Value("${app.mail.encrypted.username}")
    private String encryptedUsername;

    @Value("${app.mail.protocol}")
    private String protocol;

    @Value("${app.mail.auth}")
    private boolean auth;

    @Value("${app.mail.starttls.enable}")
    private boolean starttlsEnable;

    /**
     * Configures the JavaMailSender bean with SMTP settings.
     * The password is decrypted at runtime using AES encryption.
     *
     * @return JavaMailSender configured with SMTP settings
     * @throws Exception if decryption fails
     */

    @Bean
    public JavaMailSender mailSender() throws Exception {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(AESUtil.decrypt(secretKey, encryptedUsername));

        // Decrypt password at runtime
        String decryptedPassword = AESUtil.decrypt(secretKey, encryptedPassword);
        mailSender.setPassword(decryptedPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        return mailSender;
    }
}
