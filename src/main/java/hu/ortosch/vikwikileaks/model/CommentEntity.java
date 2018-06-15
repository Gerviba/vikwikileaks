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
@Table(name = "Comments")
public class CommentEntity implements Serializable {
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String subject;
    
    @Column
    private String owner;
    
    @Column
    private String comment;
    
    @Column
    private long date;
    
    @Column
    private Long parentId;

    public CommentEntity() {}
    
    public CommentEntity(String owner, String message, String subject, long date) {
        this.owner = owner;
        this.comment = message;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
 
    public boolean isSubcomment() {
        return id.compareTo(parentId) != 0;
    }
    
    public String getOwnerDisplay() {
        return "<span>" + owner.substring(0, 6) + "</span>" + owner.substring(6);
    }
    
}
