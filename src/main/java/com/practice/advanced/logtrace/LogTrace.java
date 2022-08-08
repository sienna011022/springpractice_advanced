package com.practice.advanced.logtrace;

import com.practice.advanced.trace.TraceStatus;

public interface LogTrace {

  TraceStatus begin(String message);

  void end(TraceStatus status);

  void exception(TraceStatus status,Exception e);

}
