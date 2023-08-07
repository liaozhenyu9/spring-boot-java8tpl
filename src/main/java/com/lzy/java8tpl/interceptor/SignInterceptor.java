package com.lzy.java8tpl.interceptor;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzy.java8tpl.api.ApiException;
import com.lzy.java8tpl.api.ErrorCode;
import com.lzy.java8tpl.api.ParamAssert;
import com.lzy.java8tpl.api.ParamErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class SignInterceptor implements HandlerInterceptor {

    private static final String APP_ID = "app_id";
    private static final String BIZ_CONTENT = "biz_content";
    private static final String TIMESTAMP = "timestamp";
    private static final String SIGN_TYPE = "sign_type";
    private static final String SIGN = "sign";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String key;

    public SignInterceptor(String key) {
        this.key = key;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String appId = request.getParameter(APP_ID);
        String bizContent = request.getParameter(BIZ_CONTENT);
        String sign = request.getParameter(SIGN);
        String signType = request.getParameter(SIGN_TYPE);
        String timestamp = request.getParameter(TIMESTAMP);

        ParamAssert.hasText(appId, "app_id不能为空");
        ParamAssert.hasText(timestamp, "时间戳不能为空");
        ParamAssert.hasText(signType, "签名算法类型不能为空");
        ParamAssert.hasText(sign, "签名不能为空");

        //校验时间戳
//        long timeDelta = Math.abs(System.currentTimeMillis() / 1000 - Long.parseLong(timestamp));
//        ParamAssert.checkBetween(timeDelta, 0, 60 * 5, "时间戳无效");

        log.info("appId        : {}", appId);
        log.info("sign         : {}", sign);
        log.info("signType     : {}", signType);
        log.info("timestamp    : {}", timestamp);

        //验签
        Map<String, String> paramMap = new TreeMap<String, String>() {{
            put(APP_ID, appId);
            put(BIZ_CONTENT, bizContent);
            put(TIMESTAMP, timestamp);
            put(SIGN_TYPE, signType);
        }};
        String dataToSign = MapUtil.sortJoin(paramMap, "", "=", true);
        String computedSign = SecureUtil.hmacSha256(key).digestHex(dataToSign);
        log.info("computed sign: {}", computedSign);
        if (!computedSign.equalsIgnoreCase(sign)) {
            throw new ApiException(ErrorCode.SIGN_ERROR);
        }

        if (bizContent != null) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
            for (MethodParameter methodParameter : methodParameters) {
                if (methodParameter.hasParameterAnnotation(RequestAttribute.class)) {
                    // 参数被@RequestAttribute("biz_content")修饰
                    RequestAttribute requestAttributeAnnotation = methodParameter.getParameterAnnotation(RequestAttribute.class);
                    if (BIZ_CONTENT.equals(requestAttributeAnnotation.name())) {
                        // 找到了标注了@RequestAttribute("biz_content")注解的参数
                        try {
                            Object param = objectMapper.readValue(bizContent, methodParameter.getParameterType());
                            request.setAttribute(BIZ_CONTENT, param);
                        } catch (Exception e) {
                            log.error("Failed to parse biz_content parameter: {}", e.getMessage());
                            throw new ParamErrorException("参数格式错误", e);
                        }
                        break;
                    }
                }
            }
        } else {
            log.info("biz_content is null");
        }
        return true;
    }

    public static void main(String[] args) {
        String key = "xxxxxxxx";
        String timestamp = System.currentTimeMillis() / 1000 + "";
        Map<String, String> paramMap = new TreeMap<String, String>() {{
            put(APP_ID, "12345678");
            put(BIZ_CONTENT, "{\"id\":1,\"name\":\"name\",\"u\":{\"name\":\"22\"}}");
            put(TIMESTAMP, timestamp);
            put(SIGN_TYPE, "HmacSHA256");
        }};
        String dataToSign = MapUtil.sortJoin(paramMap, "", "=", true);
        System.out.println("timestamp   : " + timestamp);
        System.out.println("data to sign: " + dataToSign);
        String sign = SecureUtil.hmacSha256(key).digestHex(dataToSign);
        System.out.println("sign        : " + sign);
    }
}
