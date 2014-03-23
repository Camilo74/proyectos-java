package ar.com.qestudio.server.bo;

import java.util.List;

import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Interested;

public interface AttachedBO {
	List<Attached> find(Interested interested, Integer registryId);
}