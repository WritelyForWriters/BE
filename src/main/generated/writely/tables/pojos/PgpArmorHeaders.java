/*
 * This file is generated by jOOQ.
 */
package writely.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class PgpArmorHeaders implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String key;
    private final String value;

    public PgpArmorHeaders(PgpArmorHeaders value) {
        this.key = value.key;
        this.value = value.value;
    }

    public PgpArmorHeaders(
        String key,
        String value
    ) {
        this.key = key;
        this.value = value;
    }

    /**
     * Getter for <code>public.pgp_armor_headers.key</code>.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Getter for <code>public.pgp_armor_headers.value</code>.
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PgpArmorHeaders other = (PgpArmorHeaders) obj;
        if (this.key == null) {
            if (other.key != null)
                return false;
        }
        else if (!this.key.equals(other.key))
            return false;
        if (this.value == null) {
            if (other.value != null)
                return false;
        }
        else if (!this.value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.key == null) ? 0 : this.key.hashCode());
        result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PgpArmorHeaders (");

        sb.append(key);
        sb.append(", ").append(value);

        sb.append(")");
        return sb.toString();
    }
}
