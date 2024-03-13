package lawnking.domain.landscape;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import lawnking.domain.Visitor;

public abstract class Square {
    public abstract void accept(Visitor v);
    public abstract String serialize();

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

