package com.spata14.trelloproject.workspace.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorkspaceException extends RuntimeException {
  private final WorkspaceErrorCode workspaceErrorCode;

  @Override
  public String getMessage() {
    return workspaceErrorCode.getMessage();
  }
}
