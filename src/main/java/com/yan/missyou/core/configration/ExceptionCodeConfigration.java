/**
 * @ClassName ExceptionCodeConfigration
 * @description: TODO
 * @author Argus
 * @Date 2020/3/10 16:37
 * @Version V1.0
 */
package com.yan.missyou.core.configration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 需要加到ioc去帮你匹配
 * 关联类路径下的配置
 * classpath要小写 path from source root
 * codes对应配置文件的codes
 */
@ConfigurationProperties(prefix = "lin")
@PropertySource(value = "classpath:config/exception-code.properties")
@Component
@Getter
@Setter
public class ExceptionCodeConfigration {
    private Map<Integer,String> codes = new HashMap<>();

    /**
     * 根据code获取message
     * @param code
     * @return
     */
    public String getMessages(int code) {
        String message = codes.get(code);
        System.out.println(message);
        return message;
    }
}
