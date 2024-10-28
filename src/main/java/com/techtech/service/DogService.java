package com.techtech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techtech.dao.DogRepository;
import com.techtech.dto.DogDTO;
import com.techtech.dto.PatchDTO;
import com.techtech.entity.DogEntity;
import com.techtech.exception.DogNotFoundException;

@Service
public class DogService {

	@Autowired //using constructor only is beat practice, but POSTMAN says we need this too.
	private DogRepository dogRepository;
	
	public DogDTO save(DogDTO dogDTO) {
		DogEntity dogEntity = new DogEntity(); //create DogEntity because dogRepository
		BeanUtils.copyProperties(dogDTO, dogEntity); //copy dogDTO to dogEntity(source, target)
		DogEntity entity = dogRepository.save(dogEntity); //entity here is from the DB (which has Primary key)
		dogDTO.setDid(entity.getDid()); //set PK in DTO and send it back
		return dogDTO; //this dogDTO has PK of dogDTO
	}
	
	public DogDTO findById(int did) {
		Optional<DogEntity> optional = dogRepository.findById(did);
		DogDTO dogDTO = new DogDTO(); 
		if(optional.isPresent()) {
			BeanUtils.copyProperties(optional.get(), dogDTO); //copying from (source,target)	
		} else {
			throw new DogNotFoundException("Hey, it seems like this dog does not exist."); //goes to GlobalExceptionAdvice
		}
		return dogDTO;
	}
	
	public List<DogDTO> findDogs() {
		List<DogDTO> results = new ArrayList<DogDTO>();
		List<DogEntity> dogEntityList = dogRepository.findAll();
		for (DogEntity entity : dogEntityList) {
			DogDTO dogDTO = new DogDTO(); 
			BeanUtils.copyProperties(entity, dogDTO); 
			results.add(dogDTO);
		}
		return results;
	}
	
	public boolean deleteDog(int did) {
		boolean status = false;
		if(dogRepository.existsById(did)) {
			dogRepository.deleteById(did); 
			status=true;
		}
		return status;
	}

	//logic for updating existing entity
	public void update(DogDTO dogDTO) {
		//firstly we have to find by ID. Use Optional class incase Null id (avoid NullPointer Exceptions)
		Optional<DogEntity> optional = dogRepository.findById(dogDTO.getDid());
		if(optional.isPresent()) {
			DogEntity dbdogEntity = optional.get();
			if(dogDTO.getColor()!=null) {
				dbdogEntity.setColor(dogDTO.getColor()); //only if color NOT NULL then then i setColor(update)
			}
			if(dogDTO.getName()!=null) {
				dbdogEntity.setName(dogDTO.getName());
			}
			dogRepository.save(dbdogEntity); //now save it in the Database
		} else {
			throw new DogNotFoundException("Hey, it seems like this dog does not exist."); //goes to GlobalExceptionAdvice
		}
	}
	
	//logic for partial update! 
	//same method name, different parameter(method OL)
	public void update(PatchDTO patchDTO) {
		Optional<DogEntity> optional = dogRepository.findById(Integer.parseInt(patchDTO.getId()));
		if(optional.isPresent()) {
			DogEntity dbdogEntity = optional.get();
			if("name".equalsIgnoreCase(patchDTO.getAttributeName())) {
				dbdogEntity.setName(patchDTO.getAttributeValue()); 
			}
			else if("color".equalsIgnoreCase(patchDTO.getAttributeName())) {
				dbdogEntity.setColor(patchDTO.getAttributeValue()); 
			}
			dogRepository.save(dbdogEntity); //now save it in the Database
		} else {
			throw new DogNotFoundException("Hey, it seems like this dog does not exist.");
		}
	}
	
}
