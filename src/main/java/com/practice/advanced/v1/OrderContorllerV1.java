package com.practice.advanced.v1;


import com.practice.advanced.HelloTraceV1;
import com.practice.advanced.trace.TraceStatus;
import com.practice.advanced.v0.OrderServiceV0;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderContorllerV1 {


  private final OrderServiceV1 orderServiceV1;
  private final HelloTraceV1 trace;

  @GetMapping("/v1/request")
  public String request(String itemId) {

    TraceStatus status = null;
    try {
      status = trace.begin("OrderContorller.request()");
      orderServiceV1.orderItem(itemId);
      trace.end(status);
      return "ok";

    } catch (Exception e) {
      trace.exception(status, e);
      throw e; //예외를 꼭 던져야함
    }


  }


}
