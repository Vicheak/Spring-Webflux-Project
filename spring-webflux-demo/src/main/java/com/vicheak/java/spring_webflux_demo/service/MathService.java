package com.vicheak.java.spring_webflux_demo.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.vicheak.java.spring_webflux_demo.dto.ResponseDTO;
import com.vicheak.java.spring_webflux_demo.util.SleepUtil;

@Service
public class MathService {
	
	public ResponseDTO findSquare(int input) {
		return new ResponseDTO(input * input); 
	}
	
	public List<ResponseDTO> multiplicationTable(int input){
		return IntStream.rangeClosed(1, 10)
			.peek(x -> SleepUtil.sleepSecond(1))
			.peek(x -> System.out.println("Math Service is proccessing : " + x))
			.map(x -> x * input)
			.mapToObj(x -> new ResponseDTO(x))
			.toList(); 
	}

}
