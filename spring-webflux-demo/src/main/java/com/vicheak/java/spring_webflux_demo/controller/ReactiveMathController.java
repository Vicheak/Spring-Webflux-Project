package com.vicheak.java.spring_webflux_demo.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicheak.java.spring_webflux_demo.dto.MultiplyRequestDTO;
import com.vicheak.java.spring_webflux_demo.dto.ResponseDTO;
import com.vicheak.java.spring_webflux_demo.exception.InputFailedValidationException;
import com.vicheak.java.spring_webflux_demo.service.ReactiveMathService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {
	
	private final ReactiveMathService mathService; 
	
	@GetMapping("/square/{input}")
	public Mono<ResponseDTO> findSqaure(@PathVariable int input) {
		return mathService.findSquare(input); 
	}
	
	@GetMapping("/square/{input}/throw")
	public Mono<ResponseDTO> findSqaureWithException(@PathVariable int input) {
		//throw exception outside of pipeline (not a best practice)
		if(input < 10 || input > 20){
			throw new InputFailedValidationException(input); 
		}
		return mathService.findSquare(input); 
	}
	
	@GetMapping("/square/{input}/mono-error")
	public Mono<ResponseDTO> findSqaureWithExceptionErrorSignal(@PathVariable int input) {
		return mathService.findSquareWithException(input); 
	}

	@GetMapping("/square/{input}/assignment")
	public Mono<ResponseEntity<ResponseDTO>> assignment(@PathVariable int input) {
		return Mono.just(input)
			.filter(x -> x >= 10 && x <= 20)
			.flatMap(x -> mathService.findSquare(x))
			.map(x -> ResponseEntity.ok(x))
			.defaultIfEmpty(ResponseEntity.badRequest().build()); 
	}
	
	@GetMapping(value = "/table/{input}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	//@GetMapping(value = "/table/{input}")
	public Flux<ResponseDTO> table(@PathVariable int input) {
		return mathService.multiplicationTable(input); 
	}
	
	@PostMapping("/multiply")
	public Mono<ResponseDTO> multiply(@RequestBody Mono<MultiplyRequestDTO> dto, @RequestHeader Map<String, String> header){
		System.out.println(header);
		return mathService.multiply(dto); 
	}

}
