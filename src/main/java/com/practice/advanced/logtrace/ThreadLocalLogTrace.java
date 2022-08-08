package com.practice.advanced.logtrace;

import com.practice.advanced.trace.TraceId;
import com.practice.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ThreadLocalLogTrace implements LogTrace {
  //thread local 추가
  private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();


  private static final String START_PREFIX = "-->";
  private static final String COMPLETE_PREFIX = "<--";
  private static final String EX_PREFIX = "<X-";

  @Override
  public TraceStatus begin(String message) {
    syncTraceId(); //싱크 맞추는거 호출
    TraceId traceId = traceIdHolder.get();
    Long startTimeMs = System.currentTimeMillis();
    log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX,
        traceId.getLevel()), message);
    return new TraceStatus(traceId, startTimeMs, message);
  }

  @Override
  public void end(TraceStatus status) {
    complete(status, null);
  }

  @Override
  public void exception(TraceStatus status, Exception e) {
    complete(status, e);
  }

  private void complete(TraceStatus status, Exception e) {
    Long stopTimeMs = System.currentTimeMillis();
    long resultTimeMs = stopTimeMs - status.getStartTimeMs();
    TraceId traceId = status.getTraceId();
    if (e == null) {
      log.info("[{}] {}{} time={}ms", traceId.getId(),
          addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
          resultTimeMs);
    } else {
      log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
          addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
          e.toString());
    }
    releaseTraceId(); //
  }


  //traceIdHolder가 null인 경우는 새로 만들고
  //값이 있다면 원래 루틴대로
  private void syncTraceId() {
    //trace안에서 꺼내고
    TraceId traceId = traceIdHolder.get();
    if (traceId == null) {

      traceIdHolder.set(new TraceId());

    } else {
      traceIdHolder.set(traceId.createNextId());
    }
  }

  //complete 시 traceIdHolder가 첫번째 레벨이면 traceIdHolder를 초기화

  private void releaseTraceId() {
    TraceId traceId = traceIdHolder.get();
    if (traceId.isFirstLevel()) {
      //초기화 -  q보관하고
      traceIdHolder.remove();
    } else {
      traceIdHolder.set(traceId.createNextId());
    }
  }

  private static String addSpace(String prefix, int level) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < level; i++) {
      sb.append((i == level - 1) ? "|" + prefix : "| ");
    }
    return sb.toString();
  }
}

