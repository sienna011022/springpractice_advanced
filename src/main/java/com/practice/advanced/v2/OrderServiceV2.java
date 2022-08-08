package com.practice.advanced.v2;


import com.practice.advanced.HelloTraceV2;
import com.practice.advanced.trace.TraceId;
import com.practice.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {
      TraceStatus status = null;
      try {
        status = trace.beginSync(traceId, "OrderService.orderItem()");
        orderRepository.save(status.getTraceId(), itemId);
        trace.end(status);
      } catch (Exception e) {
        trace.exception(status, e);
        throw e;
      }
    }
  }