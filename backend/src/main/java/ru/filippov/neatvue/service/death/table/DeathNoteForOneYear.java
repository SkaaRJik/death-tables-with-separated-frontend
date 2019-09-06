package ru.filippov.neatvue.service.death.table;


import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class DeathNoteForOneYear implements DeathNote {

    private byte x;

    private int l;

    private int d;

    private double q;

    private double p;

    private int Lx;

    private long T;

    private double e;


    @Override
    public int getl() {
        return l;
    }

    @Override
    public void setl(int l) {
        this.l = l;
    }

    @Override
    public void setLx(int Lx) {
        this.Lx = Lx;
    }

    @Override
    public long getLx() {
        return Lx;
    }
}
