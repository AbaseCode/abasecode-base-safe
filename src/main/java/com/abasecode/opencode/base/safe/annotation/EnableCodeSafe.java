package com.abasecode.opencode.base.safe.annotation;

import com.abasecode.opencode.base.safe.config.SafeAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(SafeAutoConfiguration.class)
@Documented
@Inherited
public @interface EnableCodeSafe {
}

