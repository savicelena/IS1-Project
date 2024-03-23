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
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIDPre", query = "SELECT p FROM Pretplata p WHERE p.iDPre = :iDPre"),
    @NamedQuery(name = "Pretplata.findByDatumVremePoc", query = "SELECT p FROM Pretplata p WHERE p.datumVremePoc = :datumVremePoc"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPre")
    private Integer iDPre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremePoc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePoc;
    @Column(name = "Cena")
    private Integer cena;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik iDKor;
    @JoinColumn(name = "IDPak", referencedColumnName = "IDPak")
    @ManyToOne(optional = false)
    private Paket iDPak;

    public Pretplata() {
    }

    public Pretplata(Integer iDPre) {
        this.iDPre = iDPre;
    }

    public Pretplata(Integer iDPre, Date datumVremePoc) {
        this.iDPre = iDPre;
        this.datumVremePoc = datumVremePoc;
    }

    public Integer getIDPre() {
        return iDPre;
    }

    public void setIDPre(Integer iDPre) {
        this.iDPre = iDPre;
    }

    public Date getDatumVremePoc() {
        return datumVremePoc;
    }

    public void setDatumVremePoc(Date datumVremePoc) {
        this.datumVremePoc = datumVremePoc;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Korisnik getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik iDKor) {
        this.iDKor = iDKor;
    }

    public Paket getIDPak() {
        return iDPak;
    }

    public void setIDPak(Paket iDPak) {
        this.iDPak = iDPak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPre != null ? iDPre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.iDPre == null && other.iDPre != null) || (this.iDPre != null && !this.iDPre.equals(other.iDPre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pretplata[ iDPre=" + iDPre + " ]";
    }
    
}
