package com.vicheak.java.spring_webflux_demo.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.vicheak.java.spring_webflux_demo.dto.MultiplyRequestDTO;
import com.vicheak.java.spring_webflux_demo.dto.ResponseDTO;
import com.vicheak.java.spring_webflux_demo.exception.InputFailedValidationException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {
	
	public Mono<ResponseDTO> findSquare(int input){
		return Mono.fromSupplier(() -> input * input)
				.map(x -> new ResponseDTO(x)); 
	}
	
	//Emit Error Signal (build exception for error inside a pipeline)
	public Mono<ResponseDTO> findSquareWithException(int input){
		return Mono.fromSupplier(() -> input)
				.handle((number, sink) -> {
					if(number < 10 || number > 20) {
						sink.error(new InputFailedValidationException(input)); 
					}else {
						sink.next(new ResponseDTO(input * input)); 
					}
				}); 
	}
	
	public Flux<ResponseDTO> multiplicationTable(int input){
		return Flux.range(1, 10)
				//.doOnNext(x -> SleepUtil.sleepSecond(1)) //will block the thread from subscriber's signal
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(x -> System.out.println("Reactive Math Service is proccessing : " + x))
				.map(x -> new ResponseDTO(x * input));
	}
	
	//This is not reactive, bad practice!
//	public Flux<ResponseDTO> multiplicationTable2(int input){
//		//will block, due to not a reactive style
//		List<ResponseDTO> list = IntStream.rangeClosed(1, 10)
//				.peek(x -> SleepUtil.sleepSecond(1))
//				.peek(x -> System.out.println("Math Service is proccessing : " + x))
//				.map(x -> x * input)
//				.mapToObj(x -> new ResponseDTO(x))
//				.toList(); 
//		
//		//build pipeline instead of any data structures
//		return Flux.fromIterable(list); //bad practice
//	}
	
	public Mono<ResponseDTO> multiply(Mono<MultiplyRequestDTO> multiplyMono){
		return multiplyMono.map(x -> new ResponseDTO(x.getFirst() * x.getSecond())); 
	}

}
