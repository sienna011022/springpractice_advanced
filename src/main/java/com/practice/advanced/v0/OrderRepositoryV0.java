package com.practice.advanced.v0;


import static java.lang.Thread.sleep;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor

class OrderRepositoryV0 {

  public void save(String itemId){
    if(itemId.equals("ex")){
      throw new IllegalArgumentException("예외 발생");

    }
    sleep(1000);

  }

  private void sleep(int millis){
    try{
      Thread.sleep(millis);
    }
    catch(InterruptedException e){
        e.printStackTrace();
      }

  }

}
