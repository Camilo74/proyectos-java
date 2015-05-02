package ar.com.qestudio.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Registry implements Serializable {

	private static final long serialVersionUID = 6726747475003039605L;

	private Integer id;
	private Interested interested;
	private Date creationDate;
	private List<Note> notes;
	private Date modifyDate;
	private List<Attached> attachments;

	//------------------------------------------------
        
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Interested getInterested() {
		return interested;
	}

	public void setInterested(Interested interested) {
		this.interested = interested;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Registry) {
        	Registry registro = (Registry) obj;
            return (registro.getId().equals(this.getId()));
        }
        return false;
    }
	
	@Override
	public int hashCode(){
		return 0;
	}

	public List<Attached> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attached> attachments) {
		this.attachments = attachments;
	}

}