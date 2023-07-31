package com.lzy.java8tpl.client;

import com.lzy.java8tpl.param.UnrecognizedPageParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "lotus")
public interface LotusClient {

    @PostMapping(value = "pre/customerService/trade/unrecognizedPage")
    Object unrecognizedPage(@RequestBody UnrecognizedPageParam unrecognizedPageParam);
}
