package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.AuctionImage;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class AuctionImageDAO {

    public static boolean insertAuctionImage(AuctionImage ai, Connection con){
        try{
            FileInputStream fis=new FileInputStream(ai.getImageFile());
            String query = "INSERT INTO auctionImage VALUES (?,?,?,?)";

            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, ai.getAuction());
            statement.setInt(2, ai.getImageNumber());
            statement.setBinaryStream(3, (InputStream)fis, ai.getImageFile().length());
            statement.setInt(4, boolToBit(ai.isDefaultPhoto()));
            statement.executeUpdate();
            return true;
        }
        catch(SQLException e){
            System.out.println("SQL error inserting auction image");
        }
        catch(FileNotFoundException e){
            System.out.println("Error creating input stream");
        }

        return false;
    }

    public static ArrayList<AuctionImage> getAuctionImages(int auctionID){
        Connection con = DBConnection.getConnection();
        ArrayList<AuctionImage> auctionImages = new ArrayList<>();
        try{
            String query = "SELECT * FROM auctionImage WHERE auctionID=" + auctionID;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                AuctionImage ai = new AuctionImage();
                ai.setAuction(rs.getInt("auctionID"));
                ai.setImageNumber(rs.getInt("imageNumber"));
                ai.setDefaultPhoto(bitToBool(rs.getInt("defaultPhoto")));
                File file = new File("src/Temp/image" + ai.getAuction() + ai.getImageNumber());
                InputStream is = rs.getBinaryStream("image");
                OutputStream os = new FileOutputStream(file);
                byte[] content = new byte[1024];
                int size = 0;
                while ((size = is.read(content)) != -1){
                    os.write(content,0,size);
                }
                os.close();
                is.close();

                ai.setImage(file);
                auctionImages.add(ai);
            }

        }catch (SQLException e){
            System.out.println("SQL error retrieving auctionImage");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auctionImages;
    }

    private static boolean bitToBool(int i){
        return i != 0;
    }
    private static int boolToBit(boolean b) {return b ? 1 : 0; }
}
