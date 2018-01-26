package model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "deleteFile",
                query = "DELETE FROM files file " +
                        "WHERE file.name LIKE :fileName " +
                        "AND file.owner LIKE :username "/* +
                        "AND file.owner.password LIKE :password"*/
        )
        ,
        @NamedQuery(
                name = "findOwnedFiles",
                query = "SELECT file FROM files file " +
                        "WHERE file.owner LIKE :username "/* +
                        "AND file.owner.password LIKE :password"*/,
                lockMode = LockModeType.OPTIMISTIC
        )
        ,
        @NamedQuery(
                name = "findAllFilesAvailable",
                query = "SELECT file FROM files file " +
                        "WHERE (file.privateAccess = FALSE " +
                        "OR (file.owner LIKE :username)) " /*+
                        "AND file.owner.password LIKE :password))"*/,
                lockMode = LockModeType.OPTIMISTIC
        )
        ,
        @NamedQuery(
                name = "freeFindAllFilesAvailable",
                query = "SELECT file FROM files file " +
                        "WHERE file.privateAccess = FALSE",
                lockMode = LockModeType.OPTIMISTIC
        )
        ,
        @NamedQuery(
                name = "retrieveFile",
                query = "SELECT file FROM files file " +
                        "WHERE file.name = :fileName " +
                        "AND file.owner = :username " +
                        "OR file.privateAccess = FALSE" /*+
                        "AND file.owner.password = :password "*/,
                lockMode = LockModeType.OPTIMISTIC
        )
        ,
        @NamedQuery(
                name = "updateFile",
                query = "UPDATE files file SET file.content = :file " +
                        "WHERE file.name = :fileName " +
                        "AND file.owner = :username " /*+
                        "AND file.owner.password = :password "*/ +
                        "AND file.writePermission <> FALSE"
        )
        ,
        @NamedQuery(
                name = "togglePrivate",
                query = "UPDATE files file SET file.privateAccess = " +
                        "CASE file.privateAccess WHEN TRUE THEN FALSE WHEN FALSE THEN TRUE ELSE file.privateAccess END " +
                        "WHERE file.name = :fileName " +
                        "AND file.owner = :username "/* +
                        "AND file.owner.password = :password "*/
        )
})

@Entity(name = "files")
public class Model {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size", nullable = false)
    private long fileSize;

    @JoinColumn(name = "owner", nullable = false)
    private String owner;

    @Column(name = "privateAccess", nullable = false)
    private boolean privateAccess;

    @Column(name = "writePermission", nullable = false)
    private boolean writePermission;

    @Column(name = "content", nullable = false)
    private java.io.File content;

    @Version
    @Column(name = "version")
    private int versionNum;

    public File() {}

    public File(String name, User user, java.io.File content) {
        this.name = name;
        this.fileSize = content.length();
        this.owner = user.getUsername();
        this.privateAccess = true;
        this.writePermission = true;
        this.content = content;
    }

    public File(String name, User user, boolean privateAccess, boolean writePermission, java.io.File content) {
        this.name = name;
        this.fileSize = content.length();
        this.owner = user.getUsername();
        this.privateAccess = privateAccess;
        this.writePermission = writePermission;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isPrivateAccess() {
        return privateAccess;
    }

    public boolean isWritePermission() {
        return writePermission;
    }

    public java.io.File getContent() {
        return content;
    }

    /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        return "File: [" +
                "File name: " + name +
                ", owner: " + owner +
                ", size: " + fileSize +
                ", is private: " + privateAccess +
                ", can write: " + writePermission +
                "]";
    }
}
