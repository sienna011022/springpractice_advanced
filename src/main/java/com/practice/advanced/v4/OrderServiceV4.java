package com.practice.advanced.v4;


import com.practice.advanced.logtrace.FieldLogTrace;
import com.practice.advanced.logtrace.ThreadLocalLogTrace;
import com.practice.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
 private final ThreadLocalLogTrace trace;

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