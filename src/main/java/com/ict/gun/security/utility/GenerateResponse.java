package com.ict.gun.security.utility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.gun.security.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GenerateResponse {
    public static ResponseEntity<String> success(String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<String>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 200)), HttpStatus.OK);
    }

    public static ResponseEntity<String> created(String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<String>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 201)), HttpStatus.CREATED);
    }

    public static ResponseEntity<String> error(String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<String>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 500)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<String> notFound(String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<String>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 404)), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<String> unauthorized(String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<String>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 401)), HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<String> badRequest(String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<String>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 400)), HttpStatus.BAD_REQUEST);
    }

}
