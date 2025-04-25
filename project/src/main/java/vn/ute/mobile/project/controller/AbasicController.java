package vn.ute.mobile.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.model.CustomUserPrincipal;

public class AbasicController {

  public String getCurrentUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)){
      throw new NotFoundException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    return principal.getUserId();
  }
}
