package com.practice.advanced;

import static org.junit.jupiter.api.Assertions.*;

import com.practice.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV1Test {

  @Test
  void begin_end() {
    HelloTraceV1 trace = new HelloTraceV1();
    TraceStatus status = trace.begin("hello");
    trace.end(status);
  }

}