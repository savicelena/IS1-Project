/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author HP
 */
@Embeddable
public class OcenaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private int iDKor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDVid")
    private int iDVid;

    public OcenaPK() {
    }

    public OcenaPK(int iDKor, int iDVid) {
        this.iDKor = iDKor;
        this.iDVid = iDVid;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }

    public int getIDVid() {
        return iDVid;
    }

    public void setIDVid(int iDVid) {
        this.iDVid = iDVid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDKor;
        hash += (int) iDVid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcenaPK)) {
            return false;
        }
        OcenaPK other = (OcenaPK) object;
        if (this.iDKor != other.iDKor) {
            return false;
        }
        if (this.iDVid != other.iDVid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OcenaPK[ iDKor=" + iDKor + ", iDVid=" + iDVid + " ]";
    }
    
}
