package ar.com.qestudio.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.qestudio.core.model.Interested;
import ar.com.qestudio.core.model.Note;
import ar.com.qestudio.core.model.Registry;
import ar.com.qestudio.server.dao.RegistryDAO;

public class RegistryDAOImpl implements RegistryDAO {

	@Override
	public Registry create(Registry registry) {
		registry.setId(1);
		registry.setCreationDate(new Date());
		registry.setModifyDate(new Date());
		
		//creo el interesado
		Interested interested = new Interested();
		interested.setName("Empresa1");
		registry.setInterested(interested);
		
		//creo las notas
		List<Note> notes = new ArrayList<Note>();
		Note note = new Note();
		note.setDescription("la descripcion 1");
		notes.add(note);
		
		note = new Note();
		note.setDescription("la descripcion 2");
		notes.add(note);

		registry.setNotes(notes);
		
		return registry;
	}

	@Override
	public Registry find(int id) {
		Registry registry = create(new Registry());
		
		//creo el interesado
		Interested interested = new Interested();
		interested.setName("Empresa1");
		registry.setInterested(interested);

		registry.setId(id);
		return registry;
	}

	@Override
	public List<Registry> findAll() {
		List<Registry> registries = new ArrayList<Registry>();
		registries.add(find(1));
		Registry reg2 = find(2);
		reg2.getInterested().setName("Empresa2");
		registries.add(reg2);
		return registries;
	}
}