package com.pythonteam.compilador.AST;

public class IfStmt implements Sentencia{
    public final Expresion condition;
    public final Sentencia thenClause;
    public final Sentencia elseClause;

    public IfStmt(Expresion condition, Sentencia thenClause, Sentencia elseClause) {
        this.condition = condition;
        this.thenClause = thenClause;
        this.elseClause = elseClause;
    }

    public IfStmt(Expresion condition, Sentencia thenClause) {
        this.condition = condition;
        this.thenClause = thenClause;
        this.elseClause = new EmptyStatement();
    }
    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
    public boolean hasElseClause() {
        return !(elseClause instanceof EmptyStatement);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IfStmt) {
            IfStmt that = (IfStmt) obj;
            return
                    this.condition.equals(that.condition) &&
                            this.thenClause.equals(that.thenClause) &&
                            this.elseClause.equals(that.elseClause);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (condition.hashCode() << 16) | (thenClause.hashCode() ^ elseClause.hashCode());
    }

    @Override
    public String toString() {
        if (hasElseClause()) {
            return "if " + condition + " then " + thenClause + " else " + elseClause;
        } else {
            return "if " + condition + " then " + thenClause;
        }
    }
}
