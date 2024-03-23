/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "video")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v"),
    @NamedQuery(name = "Video.findByIDVid", query = "SELECT v FROM Video v WHERE v.iDVid = :iDVid"),
    @NamedQuery(name = "Video.findByNaziv", query = "SELECT v FROM Video v WHERE v.naziv = :naziv"),
    @NamedQuery(name = "Video.findByTrajanje", query = "SELECT v FROM Video v WHERE v.trajanje = :trajanje"),
    @NamedQuery(name = "Video.findByDatumVremePost", query = "SELECT v FROM Video v WHERE v.datumVremePost = :datumVremePost")})
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDVid")
    private Integer iDVid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremePost")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePost;
    @ManyToMany(mappedBy = "videoList")
    private List<Kategorija> kategorijaList;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik iDKor;

    public Video() {
    }

    public Video(Integer iDVid) {
        this.iDVid = iDVid;
    }

    public Video(Integer iDVid, String naziv, int trajanje, Date datumVremePost) {
        this.iDVid = iDVid;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumVremePost = datumVremePost;
    }

    public Integer getIDVid() {
        return iDVid;
    }

    public void setIDVid(Integer iDVid) {
        this.iDVid = iDVid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumVremePost() {
        return datumVremePost;
    }

    public void setDatumVremePost(Date datumVremePost) {
        this.datumVremePost = datumVremePost;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    @XmlTransient
    public Korisnik getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik iDKor) {
        this.iDKor = iDKor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDVid != null ? iDVid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Video)) {
            return false;
        }
        Video other = (Video) object;
        if ((this.iDVid == null && other.iDVid != null) || (this.iDVid != null && !this.iDVid.equals(other.iDVid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Video[ iDVid=" + iDVid + " ]";
    }
    
}
