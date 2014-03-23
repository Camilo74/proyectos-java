package ar.com.qestudio.server.dao;

import java.util.List;

import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Interested;

public interface AttachedDAO {
	List<Attached> find(Interested interested, Integer registryId);
}