/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIDGle", query = "SELECT g FROM Gledanje g WHERE g.iDGle = :iDGle"),
    @NamedQuery(name = "Gledanje.findByDatumVremePoc", query = "SELECT g FROM Gledanje g WHERE g.datumVremePoc = :datumVremePoc"),
    @NamedQuery(name = "Gledanje.findBySekundPoc", query = "SELECT g FROM Gledanje g WHERE g.sekundPoc = :sekundPoc"),
    @NamedQuery(name = "Gledanje.findByOdgledanoSek", query = "SELECT g FROM Gledanje g WHERE g.odgledanoSek = :odgledanoSek")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDGle")
    private Integer iDGle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremePoc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePoc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SekundPoc")
    private int sekundPoc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OdgledanoSek")
    private int odgledanoSek;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik iDKor;
    @JoinColumn(name = "IDVid", referencedColumnName = "IDVid")
    @ManyToOne(optional = false)
    private Video iDVid;

    public Gledanje() {
    }

    public Gledanje(Integer iDGle) {
        this.iDGle = iDGle;
    }

    public Gledanje(Integer iDGle, Date datumVremePoc, int sekundPoc, int odgledanoSek) {
        this.iDGle = iDGle;
        this.datumVremePoc = datumVremePoc;
        this.sekundPoc = sekundPoc;
        this.odgledanoSek = odgledanoSek;
    }

    public Integer getIDGle() {
        return iDGle;
    }

    public void setIDGle(Integer iDGle) {
        this.iDGle = iDGle;
    }

    public Date getDatumVremePoc() {
        return datumVremePoc;
    }

    public void setDatumVremePoc(Date datumVremePoc) {
        this.datumVremePoc = datumVremePoc;
    }

    public int getSekundPoc() {
        return sekundPoc;
    }

    public void setSekundPoc(int sekundPoc) {
        this.sekundPoc = sekundPoc;
    }

    public int getOdgledanoSek() {
        return odgledanoSek;
    }

    public void setOdgledanoSek(int odgledanoSek) {
        this.odgledanoSek = odgledanoSek;
    }

    public Korisnik getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik iDKor) {
        this.iDKor = iDKor;
    }

    public Video getIDVid() {
        return iDVid;
    }

    public void setIDVid(Video iDVid) {
        this.iDVid = iDVid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDGle != null ? iDGle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.iDGle == null && other.iDGle != null) || (this.iDGle != null && !this.iDGle.equals(other.iDGle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Gledanje[ iDGle=" + iDGle + " ]";
    }
    
}
