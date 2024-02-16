package dsi.esprit.tn.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "reclamationFile")
public class reclamationFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fileId;

    String fileName;

    String filePath;
    @Temporal(TemporalType.DATE)
    Date uploadDate;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "picByte", length = 65535)
    private byte[] picByte;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    Reclamation reclamation;

    public reclamationFile(String name, byte[] picByte) {
        this.fileName = name;
        this.picByte = picByte;
    }
}
