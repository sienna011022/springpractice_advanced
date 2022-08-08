package com.practice.advanced.v3;


import com.practice.advanced.HelloTraceV2;
import com.practice.advanced.logtrace.LogTrace;
import com.practice.advanced.trace.TraceId;
import com.practice.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class OrderRepositoryV3 {

  private final LogTrace trace;

  public void save(String itemId) {

    TraceStatus status = null;
    try {
      status = trace.begin("OrderRepository.save()");
      //저장 로직
      if (itemId.equals("ex")) {
        throw new IllegalStateException("예외 발생!");
      }
      sleep(1000);
      trace.end(status);
    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
