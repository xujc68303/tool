package com.xjc.tool.mysql;

/**
 * SqlQueryBuilder.java
 *
 * @author Xujc
 * @date 2024/8/3
 */
public class SqlQueryBuilder {
    private final StringBuilder SQL = new StringBuilder("SELECT ");

    private boolean columnAdded = false;

    private boolean whereAdded = false;

    private boolean lastWasAnd = false;

    private boolean tableAdded = false;

    private boolean andAdded = false;

    @Override
    public String toString() {
        return SQL.toString();
    }

    public SqlQueryBuilder column(String condition){
        if (columnAdded) {
            SQL.append(", ").append(condition);
        } else {
            SQL.append(condition);
            columnAdded = true;
        }
        return this;
    }

    public SqlQueryBuilder table(String condition){
        SQL.append(" FROM ").append(condition);
        tableAdded = true;
        return this;
    }

    public SqlQueryBuilder where() {
        if (!columnAdded) {
            throw new IllegalStateException("column is null");
        }
        if (!tableAdded) {
            throw new IllegalStateException("table is null");
        }
        if (whereAdded) {
            throw new IllegalStateException("where is exist");
        }
        SQL.append(" WHERE ");
        whereAdded = true;
        return this;
    }

    public SqlQueryBuilder and(String condition) {
        if (!whereAdded && !andAdded) {
            throw new IllegalStateException("Cannot add AND without a WHERE clause first.");
        }
        if (!andAdded) {
            SQL.append(condition);
        } else {
            SQL.append(" AND ").append(condition);
        }
        andAdded = true;
        lastWasAnd = false;
        return this;
    }

    public SqlQueryBuilder or(String condition) {
        if (!whereAdded && !andAdded) {
            throw new IllegalStateException("Cannot add OR without a WHERE clause first.");
        }
        SQL.append(" OR ").append(condition);
        lastWasAnd = false;
        return this;
    }


    /**
     * 添加OR条件，如果前一个条件是AND，则添加括号
     * @param condition
     * @return
     */
    public SqlQueryBuilder andOr(String condition) {
        if (!whereAdded && !andAdded) {
            throw new IllegalStateException("Cannot add OR without a WHERE clause first.");
        }
        if (lastWasAnd) {
            int lastSpaceIndex = SQL.lastIndexOf(" AND");
            if (lastSpaceIndex != -1) {
                SQL.insert(lastSpaceIndex + 4, " (");
            }
            SQL.append(" OR ").append(condition).append(" )");
        }
        lastWasAnd = true;
        return this;
    }

    /**
     * GROUP BY子句
     * @param columns
     * @return
     */
    public SqlQueryBuilder groupBy(String... columns) {
        SQL.append(" GROUP BY ").append(String.join(", ", columns));
        return this;
    }

    /**
     * ORDER BY子句
     * @param columns
     * @return
     */
    public SqlQueryBuilder orderBy(String... columns) {
        SQL.append(" ORDER BY ").append(String.join(", ", columns));
        return this;
    }

    /**
     * LIMIT子句
     * @param limit
     * @return
     */
    public SqlQueryBuilder limit(int limit) {
        SQL.append(" LIMIT ").append(limit);
        return this;
    }

    /**
     * LIMIT子句
     *
     * @param offset
     * @param limit
     * @return
     */
    public SqlQueryBuilder limit(int offset, int limit) {
        SQL.append(" LIMIT ").append(offset).append(", ").append(limit);
        return this;
    }

    public static void main(String[] args) {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.column("id").column("name").column("age").column("class");
        builder.table("t_test");
        builder.where()
                .and("age >= '20'")
                .and("class" + " not in ( " + "1" + ", 2" + " )")
                .andOr("class is null")
                .orderBy("id")
                .limit(0, 20);
        String s = builder.toString();
        System.out.println(s);
    }
}



