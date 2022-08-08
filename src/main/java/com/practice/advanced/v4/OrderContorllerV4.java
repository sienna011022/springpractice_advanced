package com.practice.advanced.v4;

import com.practice.advanced.logtrace.FieldLogTrace;
import com.practice.advanced.logtrace.ThreadLocalLogTrace;
import com.practice.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderContorllerV4 {


  private final OrderServiceV4 orderService;
  private final ThreadLocalLogTrace trace;

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
