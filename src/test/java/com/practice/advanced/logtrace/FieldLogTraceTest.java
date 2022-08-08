package com.practice.advanced.logtrace;

import static org.junit.jupiter.api.Assertions.*;

import com.practice.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class FieldLogTraceTest {


  FieldLogTrace trace = new FieldLogTrace();

  @Test
  void begin_and_level2(){
    TraceStatus status1 = trace.begin("hello");
    TraceStatus status2  = trace.begin("hello");

    trace.end(status2);
    trace.end(status1);
  }

}