package NATrain.utils;

import NATrain.trackSideObjects.Signal;

import java.util.Objects;

public class SignalPare {
    Signal from;
    Signal to;

    public SignalPare(Signal from, Signal to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignalPare that = (SignalPare) o;
        return from.equals(that.from) &&
                to.equals(that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
