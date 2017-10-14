package com.test.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * POJO Product
 *
 * @author Tumi
 * 日期：2012-10-10
 */
public class GenEntity {
    private String tablename = "u_postcard";//表名
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*
    private List<String> colName_list = new ArrayList<String>();
    private List<String> colType_list = new ArrayList<String>();
    private List<String> coRemark_list = new ArrayList<String>();
    //数据库连接
    private static final String URL = "jdbc:mysql://139.129.108.135:3306/yk_user?useUnicode=true&characterEncoding=UTF-8";
    private static final String NAME = "myuser";
    private static final String PASS = "Wuliu!@1";
    private static final String DRIVER = "com.mysql.jdbc.Driver";


    /**
     * 功能：生成实体类主体代码
     *
     * @param colnames
     * @param colTypes
     * @return
     */
    private String parse(List<String> colnames, List<String> colTypes, List<String> colRemarks) {
        StringBuffer sb = new StringBuffer();
        //判断是否导入工具包
        sb.append("package " + this.getClass().getName().substring(0, this.getClass().getName().lastIndexOf(".")) + ";\r\n");
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("import javax.persistence.Column;\n" +
                "import javax.persistence.Entity;\n" +
                "import javax.persistence.Id;");
        sb.append("import java.sql.Timestamp;\r\n");
        sb.append("\r\n");
        //注释部分
        sb.append("   /**\r\n");
        sb.append("    * " + tablename + " 实体类\r\n");
        sb.append("    * " + new Date() + "Lu\r\n");
        sb.append("    */ \r\n");
        //实体部分
        sb.append("\r\n\r\npublic class " + initcap(tablename) + "{\r\n\n");
        processAllAttrs(sb);//属性
        processAllMethod(sb);//get set方法
        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * 功能：生成所有属性
     *
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < colName_list.size(); i++) {
            sb.append("\t/**" + coRemark_list.get(i) + "*/" + "\r\n");
            sb.append("\tprivate " + sqlType2JavaType(colType_list.get(i)) + " " + colName_list.get(i) + ";\r\n\n");
        }

    }

    /**
     * 功能：生成所有方法
     *
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colName_list.size(); i++) {
            if (colName_list.get(i).equals("id")) {
                sb.append("@Id\n");
            }
            sb.append("@Column(name =\" " + colName_list.get(i) + "\")\n");
            sb.append("\tpublic " + sqlType2JavaType(colType_list.get(i)) + " get" + initcap(colName_list.get(i)) + "(){\r\n");
            sb.append("\t\treturn " + colName_list.get(i) + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\n\tpublic void set" + initcap(colName_list.get(i)) + "(" + sqlType2JavaType(colType_list.get(i)) + " " +
                    colName_list.get(i) + "){\r\n");
            sb.append("\tthis." + colName_list.get(i) + "=" + colName_list.get(i) + ";\r\n");
            sb.append("\t}\r\n\n");

        }

    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private String initcap(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {

        if (sqlType.equalsIgnoreCase("bit")) {
            return "boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("timestamp")) {
            return "Timestamp";
        }
        return null;
    }

    /**
     * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
     *
     * @return Map集合
     */
    public GenEntity() {
        String ss = this.getClass().getName().substring(0, this.getClass().getName().lastIndexOf("."));
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        try {
            conn = getConnections(DRIVER, URL, NAME, PASS);

            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", tablename, new String[]{"TABLE"});

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (tableName.equals(tablename)) {
                    ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn), tableName.toUpperCase(), "%");
                    while (rs.next()) {
                        String colName = rs.getString("COLUMN_NAME");
                        String remarks = rs.getString("REMARKS");
                        if (remarks == null || remarks.equals("")) {
                            remarks = colName;
                        }
                        String dbType = rs.getString("TYPE_NAME");
                        colName_list.add(colName);
                        colType_list.add(dbType);
                        coRemark_list.add(remarks);
                        String content = parse(colName_list, colType_list, coRemark_list);
                        try {
                            File directory = new File("");
                            String path = this.getClass().getResource("").getPath();
                            FileWriter fw = new FileWriter(directory.getAbsolutePath() + "/src/" + path.substring(path.lastIndexOf("/com/", path.length()), path.length()) + initcap(tablename) + ".java");
                            PrintWriter pw = new PrintWriter(fw);
                            pw.println(content);
                            pw.flush();
                            pw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //获取连接
    private static Connection getConnections(String driver, String url, String user, String pwd) throws Exception {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user", user);
            props.put("password", pwd);
            Class.forName(driver);
            conn = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return conn;
    }

    //其他数据库不需要这个方法 oracle和db2需要
    private static String getSchema(Connection conn) throws Exception {
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase().toString();

    }

    public static void main(String[] args) {
        new GenEntity();
    }

}