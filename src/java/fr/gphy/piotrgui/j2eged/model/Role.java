package fr.gphy.piotrgui.j2eged.model;
// Generated 12 oct. 2013 14:58:54 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Role generated by hbm2java
 */
public class Role  implements java.io.Serializable {


     private Integer idRole;
     private String title;
     private int canRead;
     private int canWrite;
     private int canCreate;
     private Set<User> users = new HashSet<User>(0);

    public Role() {
    }

	
    public Role(String title, int canRead, int canWrite, int canCreate) {
        this.title = title;
        this.canRead = canRead;
        this.canWrite = canWrite;
        this.canCreate = canCreate;
    }
    public Role(String title, int canRead, int canWrite, int canCreate, Set<User> users) {
       this.title = title;
       this.canRead = canRead;
       this.canWrite = canWrite;
       this.canCreate = canCreate;
       this.users = users;
    }
   
    public Integer getIdRole() {
        return this.idRole;
    }
    
    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public int getCanRead() {
        return this.canRead;
    }
    
    public void setCanRead(int canRead) {
        this.canRead = canRead;
    }
    public int getCanWrite() {
        return this.canWrite;
    }
    
    public void setCanWrite(int canWrite) {
        this.canWrite = canWrite;
    }
    public int getCanCreate() {
        return this.canCreate;
    }
    
    public void setCanCreate(int canCreate) {
        this.canCreate = canCreate;
    }
    public Set<User> getUsers() {
        return this.users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }




}


