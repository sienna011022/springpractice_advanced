package com.practice.advanced.v0;


import com.practice.advanced.HelloTraceV1;
import com.practice.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderContorllerV0 {


  private final OrderServiceV0 orderServiceV0;


  @GetMapping("/v0/request")
  public String request(String itemId){

    orderServiceV0.orderItem(itemId);

    return "ok";
  }


}
