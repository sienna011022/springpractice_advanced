package com.practice.advanced.v3;


import com.practice.advanced.HelloTraceV2;
import com.practice.advanced.logtrace.FieldLogTrace;
import com.practice.advanced.logtrace.LogTrace;
import com.practice.advanced.trace.TraceId;
import com.practice.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
 private final FieldLogTrace trace;

    public void orderItem(String itemId) {
      TraceStatus status = null;
      try {
        status = trace.begin("OrderService.orderItem()");
        orderRepository.save(itemId);
        trace.end(status);
      } catch (Exception e) {
        trace.exception(status, e);
        throw e;
      }
    }
  }