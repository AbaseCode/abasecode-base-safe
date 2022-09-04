package com.abasecode.opencode.base.safe.handler;

import com.abasecode.opencode.base.code.CodeResult;
import com.abasecode.opencode.base.safe.SafeResult;
import com.abasecode.opencode.base.safe.annotation.Encrypt;
import com.abasecode.opencode.base.safe.config.SafeConfig;
import com.abasecode.opencode.base.safe.util.CodeCryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {
    @Autowired
    SafeConfig.CodeSafe codeSafe;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public SafeResult beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            String key = codeSafe.getKey();
            String iv = codeSafe.getIv();
            String secret = codeSafe.getSecret();
            if (body instanceof CodeResult) {
                CodeResult codeResult = (CodeResult) body;
                if (codeResult.getData() != null) {
                    SafeResult safeResult = CodeCryptUtils.encryptData(codeResult, key, iv, secret);
                    return safeResult;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}