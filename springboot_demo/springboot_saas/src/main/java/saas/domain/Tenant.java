package saas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:07
 */
@Entity
public class Tenant {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String dbu;

    @Column
    private String edbpwd;

    private String businessName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDbu() {
        return dbu;
    }

    public void setDbu(String dbu) {
        this.dbu = dbu;
    }

    public String getEdbpwd() {
        return edbpwd;
    }

    public void setEdbpwd(String edbpwd) {
        this.edbpwd = edbpwd;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
