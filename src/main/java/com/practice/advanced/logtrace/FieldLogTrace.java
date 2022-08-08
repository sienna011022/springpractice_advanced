package com.practice.advanced.logtrace;

import com.practice.advanced.trace.TraceId;
import com.practice.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FieldLogTrace implements LogTrace {

  private static final String START_PREFIX = "-->";
  private static final String COMPLETE_PREFIX = "<--";
  private static final String EX_PREFIX = "<X-";
  private TraceId traceIdHolder; //보관해두고 들고 있는거 traceId 동기화, 동시성 이슈 발생

  @Override
  public TraceStatus begin(String message) {
    syncTraceId(); //싱크 맞추는거 호출
    TraceId traceId = traceIdHolder;
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
    if (traceIdHolder == null) {
      traceIdHolder = new TraceId();
    } else {
      traceIdHolder = traceIdHolder.createNextId();
    }
  }


  //complete 시 traceIdHolder가 첫번째 레벨이면 traceIdHolder를 초기화

  private void releaseTraceId() {
    if (traceIdHolder.isFirstLevel()) {
      traceIdHolder = null; //destroy
    } else {
      traceIdHolder = traceIdHolder.createPreviousId();
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

//공통 파라미터 -> 필드를 사용하기로
//