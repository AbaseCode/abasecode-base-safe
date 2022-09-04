package com.abasecode.opencode.base.safe.config;

import com.abasecode.opencode.base.safe.util.CodeRsaUtils;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
@Configuration
@Component
public class SafeConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.safe")
    public CodeSafe codeSafe() {
        return new CodeSafe();
    }

    @Setter
    public static class CodeSafe {
        private String key;
        private String iv;
        private String secret;
        private String privateKey;
        private boolean hasRsa;

        public Boolean getHasRsa() {
            return this.hasRsa;
        }

        public PrivateKey getPrivateKey() throws Exception {
            return CodeRsaUtils.getPrivateKey(this.privateKey);
        }

        public String getIv() throws Exception {
            if (!getHasRsa()) {
                return this.iv;
            } else {
                return CodeRsaUtils.decrypt(this.iv, getPrivateKey());
            }
        }

        public String getKey() throws Exception {
            if (!getHasRsa()) {
                return this.key;
            } else {
                return CodeRsaUtils.decrypt(this.key, getPrivateKey());
            }
        }

        public String getSecret() throws Exception {
            if (!getHasRsa()) {
                return this.secret;
            } else {
                return CodeRsaUtils.decrypt(this.secret, getPrivateKey());
            }
        }
    }
}