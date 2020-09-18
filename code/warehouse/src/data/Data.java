/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omarjcm
 */
public class Data {
    
    private String driver;
    private String url;
    private String server;
    private String port;
    private String database;
    private String user;
    private String password;
    private String url_complete;
    
    private Connection conn;
    private Statement stmt;
    private CallableStatement cstmt;
    
    public Data( ) {
        this.conn = null;
        this.stmt = null;
        this.cstmt = null;
    }
    
    public void conectar(String parametros) {
        try {
            this.cargar_parametros(parametros);
            
            Class.forName( this.driver );
            this.conn = DriverManager.getConnection(this.url_complete, this.user, this.password);
            this.conn.setAutoCommit(false);
            this.stmt = this.conn.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet leer(String sql) {
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery( sql );
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public int escribir(String sql) {
        int resultado = 0;
        try {
            resultado = this.stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public void invocar_funcion(String sql) {
        try {
            this.cstmt = this.conn.prepareCall(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void conn_commit() {
        try {
            this.conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stmt_cerrar() {
        try {
            this.stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void conn_cerrar() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargar_parametros(String parametros) {
        LecturaArchivo archivo = new LecturaArchivo(parametros);
        Properties objeto = archivo.cargar();
        
        this.driver = objeto.getProperty("driver");
        this.url = objeto.getProperty("url");
        this.server = objeto.getProperty("server");
        this.port = objeto.getProperty("port");
        this.database = objeto.getProperty("database");
        this.user = objeto.getProperty("user");
        this.password = objeto.getProperty("password");
        
        this.url_complete = this.url+"//"+this.server+":"+this.port+"/"+this.database;
    }

    public Connection getConn() {
        return conn;
    }
    
    public CallableStatement getCstmt() {
        return cstmt;
    }
}