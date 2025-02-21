package com.techtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techtech.dto.DogDTO;
import com.techtech.dto.PatchDTO;
import com.techtech.service.DogService;

@RestController
@RequestMapping("/v1") //not necessary but you can try
public class DogRestController {
	
	final private DogService dogService;
	
	//this is called constructor injection
	public DogRestController(final DogService dogService) {
		this.dogService=dogService;
	}
	
	//ENDPOINT = http://localhost:8080/v1/dogs
	@GetMapping("/dogs")
	public List<DogDTO> showDogs() {
		return dogService.findDogs();
	}
	
	//ENDPOINT = http://localhost:8080/v1/dogs/3
	@GetMapping("/dogs/{did}")
	public ResponseEntity<DogDTO> showDog(@PathVariable int did) {
		DogDTO dogDTO = dogService.findById(did);
		return new ResponseEntity<DogDTO>(dogDTO, HttpStatus.OK);
	}
	
	//HTTP STATUS -- 201 CREATED
	@PostMapping("/dogs")
	public ResponseEntity<DogDTO> createDog(@RequestBody DogDTO dogDTO) {
		//did must be null
		dogDTO = dogService.save(dogDTO);
		return new ResponseEntity<DogDTO>(dogDTO, HttpStatus.CREATED);
	}
	
	@PutMapping("/dogs")
	public ResponseEntity<DogDTO> updateDog(@RequestBody DogDTO dogDTO) {
		//did must not be null
		dogService.update(dogDTO);
		return new ResponseEntity<DogDTO>(dogDTO, HttpStatus.OK);
	}
	
	// http://localhost:8080/v1/dogs/1
	@DeleteMapping("/dogs/{did}")
	public ResponseEntity<Void> deleteDog(@PathVariable int did) {
		boolean status = dogService.deleteDog(did);
		return new ResponseEntity<Void>(status?HttpStatus.NO_CONTENT:HttpStatus.NOT_FOUND);
	}
	
	@PatchMapping("/dogs")
	public ResponseEntity<Map<String, Object>> updateDog(@RequestBody PatchDTO dogDTO) {
		//did must not be null
		dogService.update(dogDTO);
		Map map = new HashMap<String, Object>();
		map.put("message", dogDTO.getAttributeName()+" is updated successfully.");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
}