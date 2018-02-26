package com.zeroexception.oauth2implementation.restApiController;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zeroexception.oauth2implementation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


@RestController
@RequestMapping("/v1")
public class RestApi {


    private UserService userService;

    @Autowired
    public RestApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public ResponseEntity<?> testApi() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("value", "Test Api");

        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @GetMapping("/secure/secret")
    public ResponseEntity<?> getProtectedString() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("secret", "Access Granted");

        return new ResponseEntity<>(node, HttpStatus.OK);
    }

}
