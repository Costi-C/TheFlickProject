package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import bo.Author;
import bo.Feed;
import bo.Image;
import utils.Constants;

public class DBConnection {
	private static Connection conn = connect();
	
	private static Logger log = Logger.getLogger(DBConnection.class.getName());
	public static Connection connect() {
		Properties prop = new Properties();
		InputStream input = null;
		String fileName = Constants.CONFIG_PROPERTIES_FILE;

		try {
			input = new FileInputStream(fileName);
			if (input != null) {
				prop.load(input);
			}

			String dbDriver = prop.getProperty(Constants.DB_DRIVER);
			String dbURL = prop.getProperty(Constants.DB_URL);
			String dbUser = prop.getProperty(Constants.DB_USER);
			String dbPassword = prop.getProperty(Constants.DB_PASSWORD);
			String table = prop.getProperty(Constants.DB_TABLE_NAME);

			if (conn == null) {
				try {
					Class.forName(dbDriver);
					conn = DriverManager.getConnection(dbURL, dbUser,
							dbPassword);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return conn;
	}

	
	public static void disconnect() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public static void insertRecords(Feed feedData) throws ParseException {
		PreparedStatement pstmt = null;

		String insertSQL = "INSERT INTO feed"
				+ " (image_title, image_url, image_date, author_name, author_url, author_icon) "
				+ "VALUES " + "(?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(insertSQL);
			Vector<Image> vector = feedData.getFeedData();
			for (Image image : vector) {
				pstmt.setString(1, image.getTitle());
				pstmt.setString(2, image.getUrl());
				//java.sql.Date sqlDate = new java.sql.Date(image.getImageDate()
						//.getTime());
				Timestamp ts = new Timestamp(image.getImageDate()
						.getTime());
				pstmt.setTimestamp(3, ts);
				pstmt.setString(4, image.getAuthor().getName());
				pstmt.setString(5, image.getAuthor().getUrl());
				pstmt.setString(6, image.getAuthor().getIcon());
				pstmt.executeUpdate();
				log.info("Record successfully added !!!!");
				
			}

		} catch (SQLException e) {
			e.printStackTrace();			
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				disconnect();
			}
		}

	}

	/**
	 * @author test
	 * @throws MyEx
	 * 
	 * @param feedData - obfsdf sd fdsfsd
	 * @param title
	 * @param authorName
	 * @param id
	 */
	public static void updateRecords(Feed feedData, String title,
			String authorName, int id) {
		PreparedStatement pstmt = null;

		String updateSQL = "UPDATE feed "
				+ "SET image_title = ?, author_name = ?" + " WHERE id = ?";
		try {
			//conn = connect();

			pstmt = conn.prepareStatement(updateSQL);
			
				pstmt.setString(1, title);				
				pstmt.setString(2, authorName);
				pstmt.setInt(3, id);
				pstmt.executeUpdate();
				log.info("Record is updated !!!!");
				//System.out.println("Record is updated !!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				disconnect();
			}
		}
	}
	
	public static void deleteRecords(int id){
		PreparedStatement pstmt = null;
		String deleteSQL = "DELETE FROM feed WHERE id = ?";
		
		try {
			//conn = connect();
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			System.out.println("Deleted successfully!!!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void selectedRecords(int id) {
		PreparedStatement pstmt = null;
		String selectSQL = "SELECT * FROM feed WHERE id = ?";
		try {
			//conn = connect();
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String imgTitle = rs.getString("image_title");
				String imgUrl = rs.getString("image_url");
				Timestamp imgDate = rs.getTimestamp("image_date");
				String authName = rs.getString("author_name");
				String authUrl = rs.getString("author_url");
				String authIcon = rs.getString("author_icon");
				System.out.println(imgTitle + Constants.SPACE + imgUrl + " " + imgDate
						+ " " + authName + " " + authUrl + " " + authIcon);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
					e.printStackTrace();
				}
				disconnect();
			}
		}

	}

}
