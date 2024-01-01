package com.backend.tt.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Table(name="allotments")
@Entity
public class AllotmentEntity {
	
	@EmbeddedId
    private AllotmentId id;
	
	@SuppressWarnings("serial")
	@Embeddable
    public static class AllotmentId implements Serializable {
		
		@JoinColumn(name="sid")
        private String sid;

		@JoinColumn(name="cid")
        private String cid;

		@JoinColumn(name="sem")
        private String sem;

		@JoinColumn(name="batch")
        private String batch;
		
		public AllotmentId() {}
		
		public AllotmentId(String sid, String cid, String sem, String batch) {
			super();
			this.sid = sid;
			this.cid = cid;
			this.sem = sem;
			this.batch = batch;
		}
		
		public String getSid() {
			return sid;
		}

		public void setSid(String sid) {
			this.sid = sid;
		}

		public String getCid() {
			return cid;
		}

		public void setCid(String cid) {
			this.cid = cid;
		}

		public String getSem() {
			return sem;
		}

		public void setSem(String sem) {
			this.sem = sem;
		}

		public String getBatch() {
			return batch;
		}

		public void setBatch(String batch) {
			this.batch = batch;
		}
		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        AllotmentId that = (AllotmentId) o;
	        return Objects.equals(sid, that.sid) &&
	                Objects.equals(cid, that.cid) &&
	                Objects.equals(sem, that.sem) &&
	                Objects.equals(batch, that.batch);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(sid, cid, sem, batch);
	    }
		
    }

	@Column(name="tid")
	private String tid;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	@Column(name="type")
	private String type;
	@Column(name="placed")
	private int placed;
	@Column(name="verified")
	private int verified;
	
	public AllotmentEntity() {}
	public AllotmentEntity(AllotmentId id, String tid,String type, int placed, int verified) {
		super();
		this.id = id;
		this.tid = tid;
		this.type = type;
		this.placed = placed;
		this.verified = verified;
	}
	public AllotmentId getId() {
		return id;
	}
	public void setId(AllotmentId id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPlaced() {
		return placed;
	}
	public void setPlaced(int placed) {
		this.placed = placed;
	}
	public int getVerified() {
		return verified;
	}
	public void setVerified(int verified) {
		this.verified = verified;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, placed, tid, type, verified);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AllotmentEntity other = (AllotmentEntity) obj;
		return Objects.equals(id, other.id) && placed == other.placed && Objects.equals(tid, other.tid)
				&& Objects.equals(type, other.type) && verified == other.verified;
	}
	
}
