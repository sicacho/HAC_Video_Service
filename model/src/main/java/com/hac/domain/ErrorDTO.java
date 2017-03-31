package com.hac.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by khang on 3/31/2017.
 */
@Getter @Setter
public class ErrorDTO {
  private Object object;
  private Exception exception;
  private Date date;

  public ErrorDTO(Object object, Exception exception) {
    this.object = object;
    this.exception = exception;
    date = new Date();
  }
}
