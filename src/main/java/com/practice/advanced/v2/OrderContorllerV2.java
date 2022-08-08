package com.practice.advanced.v2;

import com.practice.advanced.HelloTraceV2;
import com.practice.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderContorllerV2 {


  private final OrderServiceV2 orderService;
  private final HelloTraceV2 trace;

  @GetMapping("/v2/request")
  public String request(String itemId) {
    //전체 스콥 적용
    TraceStatus status = null;

    try {
      //log
      status = trace.begin("OrderController.request()");
      orderService.orderItem(status.getTraceId(), itemId);
      //log
      trace.end(status);
      return "ok";
    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }

  }


}
