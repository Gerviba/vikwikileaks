package hu.ortosch.vikwikileaks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@SuppressWarnings("serial")
@Table(name = "Resources")
public class ResourceEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /** 2nd type of hash **/
    @Column
    private String owner;

    @Column
    private String displayName;
    
    @Column
    private String storedName;

    @Column
    private String extention;
    
    @Column
    private String subject;
    
    @Column
    private long date;

    public ResourceEntity() {}
    
    public ResourceEntity(String owner, String displayName, String storedName, 
            String extention, String subject, long date) {
        
        this.owner = owner;
        this.displayName = displayName;
        this.storedName = storedName;
        this.extention = extention;
        this.subject = subject;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
    
}
