package com.abasecode.opencode.base.safe.handler;

import com.abasecode.opencode.base.code.CodeResult;
import com.abasecode.opencode.base.safe.SafeResult;
import com.abasecode.opencode.base.safe.annotation.Decrypt;
import com.abasecode.opencode.base.safe.config.SafeConfig;
import com.abasecode.opencode.base.safe.util.CodeCryptUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
@ControllerAdvice
public class RequestHandler extends RequestBodyAdviceAdapter {

    @Autowired
    SafeConfig.CodeSafe codeSafe;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(Decrypt.class) || methodParameter.hasParameterAnnotation(Decrypt.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        byte[] body = new byte[inputMessage.getBody().available()];
        inputMessage.getBody().read(body);
        try {
            String key = codeSafe.getKey();
            String iv = codeSafe.getIv();

            SafeResult safeResult = JSONObject.parseObject(new String(body), SafeResult.class);
            CodeResult codeResult = CodeCryptUtils.decryptData(safeResult, key, iv);
            String jsonString = JSONObject.toJSONString(codeResult.getData());

            final ByteArrayInputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() throws IOException {
                    return stream;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }
}