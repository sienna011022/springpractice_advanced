package com.practice.advanced.v3;

import com.practice.advanced.HelloTraceV2;
import com.practice.advanced.logtrace.LogTrace;
import com.practice.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderContorllerV3 {


  private final OrderServiceV3 orderService;
  private final LogTrace trace;

  @GetMapping("/v3/request")
  public String request(String itemId) {

    //전체 스콥 적용
    TraceStatus status = null;

    try {

      trace.begin("OrderContorller.request()");
      //log
      status = trace.begin("OrderController.request()");
      orderService.orderItem(itemId);
      //log
      trace.end(status);
      return "ok";
    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }

  }


}
