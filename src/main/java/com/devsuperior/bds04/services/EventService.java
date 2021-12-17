package com.devsuperior.bds04.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exception.DatabaseException;
import com.devsuperior.bds04.services.exception.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;

	@Transactional(readOnly=true)
	public Page<EventDTO> findAllPaged(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new EventDTO(x));
	}
	
	@Transactional(readOnly = true)
	public EventDTO findById(Long id) {
		return repository.findById(id).map(x -> new EventDTO(x))
				.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
	}

	@Transactional
	public EventDTO save(EventDTO dto) {
		Event evt = new Event();
		evt.setName(dto.getName());
		evt.setUrl(dto.getUrl());
		evt.setDate(dto.getDate());
		evt.setCity(new City(dto.getCityId()));
		return new EventDTO(repository.save(evt));
	}

	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = repository.getOne(id);

			entity.setName(dto.getName());
			return new EventDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}


	public void deleteById(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
