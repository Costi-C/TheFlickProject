package test;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import utils.ReadAndWriteUtils;
import bo.Feed;
import bo.Image;
import db.DBConnection;
 
public class InsertPhotoDB {
	
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException{
        insertImage();
    	//readImage();
    }
    
    public static void insertImage() throws IOException, ParserConfigurationException, SAXException{
    	Connection conn = null;
        PreparedStatement ps = null;
        InputStream is = null;        
        String SQL_INSERT_IMAGE = "UPDATE feed SET photo = ? WHERE image_url = ?";
        String imageURL = "";
        ReadAndWriteUtils rawu = new ReadAndWriteUtils();
        Feed feedData = rawu.readData("input.xml");        
        Vector<Image> vector = null;
        try {
        	conn = DBConnection.connect();           
            ps = conn.prepareStatement(SQL_INSERT_IMAGE);
            vector = feedData.getFeedData();
            for (Image image : vector) {
				imageURL = image.getUrl();
				try {
					URL url = new URL(imageURL);
					//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("cache.ase.ro", 8080));
					//is = url.openConnection(proxy).getInputStream();
//					is = new FileInputStream(new File(imageURL));					
					ps.setBinaryStream(1, is);
					ps.setString(2, imageURL);
					int count = ps.executeUpdate();
					System.out.println("Url: "+imageURL);
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try{
                if(is != null) is.close();
                if(ps != null) ps.close();
               DBConnection.disconnect();
            } catch(Exception ex){}
        }
    }
    
    public static void readImage(){
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        InputStream is = null;
        OutputStream os = null;
        String selectSQL = "select photo from feed where id=27";
        try {
        	conn = DBConnection.connect();
        	pstmt = conn.prepareStatement(selectSQL);    		
            rs = pstmt.executeQuery();
            if(rs.next()){
                is = rs.getBinaryStream("photo");
            }
  
            os = new FileOutputStream("images\\penguinsNew22.jpg");
            byte[] content = new byte[1024];
            int size = 0;
            while((size = is.read(content)) != -1){
                os.write(content, 0, size);
            }
            //os.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        
        } finally{
            try{
                if(is != null) is.close();
                if(os != null) os.close();
                if(pstmt != null) pstmt.close();
                DBConnection.disconnect();
            } catch(Exception ex){}
        }
    }
    
}

