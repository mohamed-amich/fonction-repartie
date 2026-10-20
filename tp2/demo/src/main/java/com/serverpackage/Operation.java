package com.serverpackage;

import java.io.Serializable;

public class Operation implements Serializable {
    private double op1;
    private double op2;
    private String operator;

    public Operation(double op1, String operator, double op2) {
        this.op1 = op1;
        this.operator = operator;
        this.op2 = op2;
    }

    public double getOp1() { return op1; }
    public double getOp2() { return op2; }
    public String getOperator() { return operator; }

    @Override
    public String toString() {
        return op1 + " " + operator + " " + op2;
    }
}
