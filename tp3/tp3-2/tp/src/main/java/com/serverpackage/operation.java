package com.serverpackage;

import java.io.Serializable;

public class operation implements Serializable {
    private double op1;
    private double op2;
    private String operator;

    private Double result;

    private String error;

    public operation(double op1, String operator, double op2) {
        this.op1 = op1;
        this.operator = operator;
        this.op2 = op2;
        this.result = null;
        this.error = null;
    }

    public double getOp1() { return op1; }
    public double getOp2() { return op2; }
    public String getOperator() { return operator; }

    public Double getResult() { return result; }
    public void setResult(Double result) { this.result = result; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    @Override
    public String toString() {
        if (error != null) return op1 + " " + operator + " " + op2 + " → erreur: " + error;
        if (result != null) return op1 + " " + operator + " " + op2 + " = " + result;
        return op1 + " " + operator + " " + op2;
    }
}
