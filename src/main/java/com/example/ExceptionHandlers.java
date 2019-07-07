/*
 * Copyright 2019 Shinya Mochida
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice(basePackages = {"com.example.users.api", "com.example.teams.api"})
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    Map<String, Object> validationException(ValidationException e) {
        return Map.of("success", false, "message", e.getMessage(), "time", Instant.now());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConditionException.class)
    @ResponseBody
    Map<String, Object> conditionException(ConditionException e) {
        return Map.of("success", false, "message", e.getMessage(), "time", Instant.now());
    }
}
