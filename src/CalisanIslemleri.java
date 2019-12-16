
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalisanIslemleri {
    
    private Connection con = null;
    
    private Statement statement = null;
    
    private PreparedStatement preparedStatement = null;
    
    
    public CalisanIslemleri(){
        
        // jdbc:mysql://localhost:3306/demo"
        String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.db_ismi;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı....");
        }
    
        
        try {
            con = DriverManager.getConnection(url, Database.kullanici_adi, Database.parola);
            System.out.println("Bağlantı Başarılı");
        
        } catch (SQLException ex) {
            System.out.println("Bağlantı Başarısız..");
        }
        
    }
    
    
    public boolean girisYap(String kullanici_ismi,String sifre){
        
        String sorgu = "SELECT * FROM adminler WHERE username = ? AND password = ?";
        
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, kullanici_ismi);
            preparedStatement.setString(2, sifre);
            
            
            ResultSet rs = preparedStatement.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        
        
        
        
    }
    
    public void calisanEkle(String ad,String soyad, String departman, String maas){
        
        String sorgu = "INSERT INTO calisanlar (ad,soyad,departman,maas) VALUES(?,?,?,?)";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    public void calisanGuncelle(int id, String ad, String soyad, String departman, String maas){
        
        String sorgu = "UPDATE calisanlar SET ad = ?, soyad = ? , departman = ? , maas = ? WHERE id = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            
            preparedStatement.setInt(5, id);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void calisanSil(int id){
        
        String sorgu = "DELETE FROM calisanlar WHERE id = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setInt(1, id);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    
    
    public ArrayList<Calisan> calisanlariGetir(){
        
        ArrayList<Calisan> cikti = new ArrayList<Calisan>();
        
        try {
            statement = con.createStatement();
            String sorgu = "SELECT * FROM calisanlar";
            
            ResultSet rs = statement.executeQuery(sorgu);
            
            while(rs.next()){
                
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String departman = rs.getString("departman");
                String maas = rs.getString("maas");
                
                cikti.add(new Calisan(id, ad, soyad, departman, maas));
                
                
            }
            
            return cikti;
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    
    
    
    
    
}
