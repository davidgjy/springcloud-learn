package com.itmuch.cloud.study.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.itmuch.cloud.study.entity.User;
import com.itmuch.cloud.study.repository.UserRepository;

/**
 * @author KG
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @ApiOperation(value = "Find User by Id")
//  @GetMapping("/{id}")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public @ResponseBody
  User findById(@PathVariable Long id) {
    User user = this.userRepository.findById(id).orElse(null);
    return user;
  }
}
