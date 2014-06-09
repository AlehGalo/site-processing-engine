package com.jdev.ngui.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.jdev.ngui.domain.util.CustomLocalDateSerializer;

/**
 * A Test.
 */
@Entity
@Table(name = "T_TEST")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Test implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Size(min = 1, max = 50)
    @Column(name = "sample_text_attribute")
    private String sampleTextAttribute;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @Column(name = "sample_date_attribute")
    private LocalDate sampleDateAttribute;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getSampleTextAttribute() {
        return sampleTextAttribute;
    }

    public void setSampleTextAttribute(final String sampleTextAttribute) {
        this.sampleTextAttribute = sampleTextAttribute;
    }

    public LocalDate getSampleDateAttribute() {
        return sampleDateAttribute;
    }

    public void setSampleDateAttribute(final LocalDate sampleDateAttribute) {
        this.sampleDateAttribute = sampleDateAttribute;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Test test = (Test) o;

        if (id != test.id) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Test{" + "id=" + id + ", sampleTextAttribute='" + sampleTextAttribute + '\''
                + ", sampleDateAttribute=" + sampleDateAttribute + '}';
    }
}
