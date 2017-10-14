package io.fdlessard.codebites.caching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

@Slf4j
public class CustomerKeyGenerator implements KeyGenerator {


    @Override
    public Object generate(Object o, Method method, Object... params) {

        LOGGER.info("CustomerKeyGenerator.generate() - {}, {},{}", o, method, params);

        StringBuilder sb = new StringBuilder();
        sb.append("Prefix-");


        for (Object param : params) {
            sb.append(param.toString());
        }

        LOGGER.info("CustomerKeyGenerator.generate() - {}", sb.toString());

        return sb.toString();
    }
}
