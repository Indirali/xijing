package com.showtime.xijing.common.entity;

public class ObfuscatedId {
    private long id;

    public ObfuscatedId(long id) {
        this.id = id;
    }

    public ObfuscatedId(String obfuscatedId) throws IllegalArgumentException {
        this.id = IdObfuscator.decode(obfuscatedId);
    }

    public long getId() {
        return id;
    }

    public String decFormat() {
        return IdObfuscator.encodeDecFormat(id);
    }

    @Override
    public String toString() {
        return IdObfuscator.encode(id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ObfuscatedId other = (ObfuscatedId) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
